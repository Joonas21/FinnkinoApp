package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;


interface MainInterface
{
    void addSpinner(ArrayList theater_array);
    void addShows(ArrayList arrayList, String startTime, String endTime, String showName);
    void getArray(ArrayList<Theater> arrayList);

}

public class MainActivity extends AppCompatActivity implements MainInterface {

    EditText startTimeInput;
    EditText endTimeInput;
    EditText dateInput;
    EditText nameInput;
    Spinner theaterSpinner;
    ListView movies;
    Button search;

    ArrayList<Theater> theater_array = new ArrayList<>();
    ArrayList<Show> show_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        startTimeInput = (EditText) findViewById(R.id.startTime);
        endTimeInput = (EditText) findViewById(R.id.endTime);
        dateInput = (EditText) findViewById(R.id.date);
        nameInput = (EditText) findViewById(R.id.name);
        theaterSpinner = (Spinner) findViewById(R.id.theater);
        movies = (ListView) findViewById(R.id.movies);
        search = (Button) findViewById(R.id.search);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new ReaderXML_Theater(this).execute();


    }

    @Override
    public void getArray(ArrayList<Theater> arrayList)
    {
        theater_array = arrayList;
    }

    @Override
    public void addSpinner(ArrayList theater_array)
    {
        ArrayAdapter<Theater> adapter = new ArrayAdapter<Theater>(this, R.layout.spinner_style, theater_array);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        theaterSpinner.setAdapter(adapter);
    }

    @Override
    public void addShows(ArrayList arrayList, String s, String e, String n)
    {
        show_array = arrayList;
        ArrayList<Show> tempArr = new ArrayList<>();
        String startingTime = s;
        String endingTime = e;
        String deliminator = "[:]";
        String showName = n;

        String[] startTime = startingTime.split(deliminator);
        String parsedStartTime = startTime[0] + startTime[1];

        String[] endTime = endingTime.split(deliminator);
        String parsedEndTime = endTime[0] + endTime[1];

        for (int i = 0; i < show_array.size(); i++)
        {
            Show curr = show_array.get(i);

            String showTime = curr.getStartTime();
            String[] showStartTime = showTime.split(deliminator);

            String showStartHours = showStartTime[0];
            String showStartMins = showStartTime[1];

            showTime = curr.getEndTime();
            String[] showEnd = showTime.split(deliminator);

            String showEndHours = showEnd[0];
            String showEndMins = showEnd[1];

            int show_startTime = Integer.parseInt(showStartHours + showStartMins);
            int show_endTime = Integer.parseInt(showEndHours + showEndMins);

            if (curr.getTitle().toLowerCase().contains(showName.toLowerCase()))
            {
                if (Integer.parseInt(parsedStartTime) <= show_startTime)
                {
                    if (Integer.parseInt(parsedEndTime) >= show_endTime)
                    {
                        String startingtime = showStartHours + ":" + showStartMins + ":00";
                        String endingtime = showEndHours + ":" + showEndMins + ":00";

                        tempArr.add(new Show(curr.getTitle(), curr.getID(), startingtime, endingtime, curr.getTheater(), curr.getStatus()));
                    }
                }
            }
        }

        ArrayAdapter<Show> adapter = new ArrayAdapter<Show>(this, R.layout.list_text, tempArr);
        movies.setAdapter(adapter);

    }

    public void getShow(View v)
    {
        Context context = getApplicationContext();
        String id_single;
        ArrayList<Integer> id = new ArrayList<>();
        Toast toast;
        String date = dateInput.getText().toString();
        String startTime;
        String endTime;
        boolean status = true;


        if (startTimeInput.getText().toString().matches(""))
        {
            startTime = "00:00:00";
        } else
        {
            startTime = startTimeInput.getText().toString();
        }

        if (endTimeInput.getText().toString().matches(""))
        {
            endTime = "23:59:00";
        } else
        {
            endTime = endTimeInput.getText().toString();
        }


        String name = nameInput.getText().toString();

        int arrayID = theaterSpinner.getSelectedItemPosition();
        id_single = theater_array.get(arrayID).getID();

        if (id_single.equals("1029"))
        {
            id.add(1012);
            id.add(1002);
            id.add(1015);
            id.add(1016);
            id.add(1017);
            id.add(1041);
            id.add(1018);
            id.add(1019);
            id.add(1021);
            id.add(1022);
        }

        if(id.equals("1029") && !name.matches(""))
        {
            status = false;
        }

        if(!id.equals("1029"))
        {
            status = true;
        } else if (date.matches(""))
        {
            Date dateNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            date = sdf.format(dateNow);
        }

        new ReaderXML_Shows(this, date, id, startTime, endTime, status, name).execute();
    }



    class ReaderXML_Theater extends AsyncTask<Void, ArrayList<Theater>, ArrayList<Theater>>
    {
        MainInterface mainInterface;

        public ReaderXML_Theater(MainInterface MainIF) { mainInterface = MainIF; }

        protected ArrayList<Theater> doInBackground(Void... voids)
        {
            ArrayList<Theater> theater_array = new ArrayList<>();

            try
            {
                String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(urlString);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("TheatreArea");

                for (int i = 0; i < nList.getLength(); i++)
                {
                    Node node = nList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element element = (Element) node;
                        String id = (element.getElementsByTagName("ID").item(0).getTextContent());
                        String name = (element.getElementsByTagName("Name").item(0).getTextContent());
                        theater_array.add(new Theater(name, id));
                    }
                }
            } catch (IOException e) { e.printStackTrace();
            } catch (SAXException e) { e.printStackTrace();
            } catch (ParserConfigurationException e) { e.printStackTrace(); }


            return theater_array;
        }

        @Override
        protected void onPostExecute(ArrayList<Theater> theater_array)
        {
            super.onPostExecute(theater_array);
            mainInterface.addSpinner(theater_array);
            mainInterface.getArray(theater_array);
        }

    }


    class ReaderXML_Shows extends AsyncTask<Void, ArrayList<Show>, ArrayList<Show>>
    {
        MainInterface mainInterface;
        String date;
        ArrayList<Integer> id;
        String startTime;
        String endTime;
        boolean status;
        String showName;


        public ReaderXML_Shows(MainInterface mainIF, String d, ArrayList<Integer> i, String s, String e, boolean b, String n)
        {
            date = d;
            mainInterface = mainIF;
            id = i;
            startTime = s;
            endTime = e;
            status = b;
            showName = n;
        }


        @Override
        protected ArrayList<Show> doInBackground(Void... voids)
        {
            ArrayList<Show> show_array = new ArrayList<>();

            try
            {
                for (Integer id_single : id)
                {
                    String urlString = "http://www.finnkino.fi/xml/Schedule/?area=" + id_single + "&dt=" + date;
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.parse(urlString);
                    doc.getDocumentElement().normalize();

                    NodeList nList = doc.getElementsByTagName("Show");

                    for (int j = 0; j < nList.getLength(); j++)
                    {
                        Node node = nList.item(j);

                        if (node.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element element = (Element) node;
                            String id = (element.getElementsByTagName("ID").item(0).getTextContent());
                            String title = (element.getElementsByTagName("Title").item(0).getTextContent());
                            String fullStartDate = (element.getElementsByTagName("dttmShowStart").item(0).getTextContent());
                            String deliminator = "[T]";
                            String[] startTime = fullStartDate.split(deliminator);
                            String fullEndDate = (element.getElementsByTagName("dttmShowEnd").item(0).getTextContent());
                            String theater = (element.getElementsByTagName("Theatre").item(0).getTextContent());
                            String[] endTime = fullEndDate.split(deliminator);
                            show_array.add(new Show(title, id, startTime[1], endTime[1], theater, status));
                        }
                    }
                }

            } catch (IOException err) { err.printStackTrace();
            } catch (SAXException err) { err.printStackTrace();
            } catch (ParserConfigurationException err) { err.printStackTrace(); }

            return show_array;
        }

        @Override
        protected void onPostExecute(ArrayList<Show> show_array)
        {
            mainInterface.addShows(show_array, startTime, endTime, showName);
        }

    }
}
