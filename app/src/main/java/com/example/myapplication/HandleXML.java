package com.example.myapplication;

import android.content.Context;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Locale;


import org.xml.sax.InputSource;


// SOURCE FOR CLASS
/* https://mkyong.com/java/how-to-create-xml-file-in-java-dom/ */


public class HandleXML {
    Context context;

    public HandleXML(Context context) {
        this.context = context;
    }

    //public HandleXML() {}


    public ArrayList readXML(String movieTitle)
    {
        Document dom;

        ArrayList userReviews = new ArrayList();

        try
        {
            InputStream in = context.getResources().openRawResource(R.raw.reviews);
            //InputStream in = context.getResources().openRawResource(R.raw.empty);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            dom = db.parse(in);


            Element rootElement = dom.getDocumentElement();

            //System.out.println("  Root = " + rootElement.getNodeName());

            if (dom.getElementById(movieTitle) != null)
            {
                Node movie = dom.getElementById(movieTitle);
                NodeList reviews = movie.getChildNodes();


                for (int i = 0; i < reviews.getLength(); i++) {
                    Node review = reviews.item(i);

                    NodeList data = review.getChildNodes();

                    String result = "";

                    for (int j = 0; j < data.getLength(); j++) {

                        Node curr = data.item(j);

                        if (curr.getNodeType() == Node.ELEMENT_NODE) {


                            /* NOTE: HACKY IMPLEMENTATION; DO NOT JUDGE ME >:( */

                            if (curr.getNodeName().equals("nickname")) {
                                String user = curr.getTextContent();
                                System.out.println(" user found: " + user);

                                result = result + user + ": ";
                                //userReviews.add(user);
                            }

                            if (curr.getNodeName().equals("rating")) {
                                String rating = curr.getTextContent();
                                System.out.println(" rating found: " + rating);

                                result = result + rating + " stars";

                                userReviews.add(result);
                                result = "";
                            }

                            //userReviews.add(user);
                        }
                    }
                }
            }

        } catch (Exception ex) { ex.printStackTrace(); }

        return userReviews;
    }



    public void writeXml(String movieTitle, String userName, String movieRating)
    {
        Document dom;
        Document newDoc;
        Boolean movieFound = false;

        try
        {
            /*
            InputStream in = context.getResources().openRawResource(R.raw.reviews);


             */



            /*
            FileInputStream fis = context.openFileInput("reviews.xml");


            */

            InputStream in = context.getResources().openRawResource(R.raw.reviews);


            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            dom = db.parse(in);


            //FileOutputStream fos = context.openFileOutput("reviews.xml", Context.MODE_APPEND);


            //newDoc = db.newDocument();


            System.out.println("Document successfully opened!");


            System.out.println("    PARAMETERS:");
            System.out.println("    movieTitle = " + movieTitle);
            System.out.println("    userName = " + userName);
            System.out.println("    movieRating = " + movieRating);



            Element rootElement = dom.getElementById("root");

            //System.out.println(" debg: root element = " + rootElement);


            NodeList movieList = rootElement.getChildNodes();


            //System.out.println(" debg: movieList formed");


            /* https://stackoverflow.com/questions/14939667/how-to-get-xml-node-by-id-and-namednodemap-java-dom-xml */

           // System.out.println(" debg: before for");

            for (int i = 0; i < movieList.getLength(); i++)
            {
                Node curr = movieList.item(i);

                //System.out.println("  -- debug: curr = " + curr + "  |  textContent = " + curr.getTextContent().trim());

                if (curr.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element tempElem = (Element) curr;

                    //System.out.println(" temp #ID: " + tempElem.getAttribute("id"));

                    if (tempElem.getAttribute("id").equals(movieTitle)) { movieFound = true; }
                }
            }


            if (movieFound)
            {
                System.out.println("  - movie '" + movieTitle + "' found!");

                Element existingMovie = dom.getElementById(movieTitle);

                //System.out.println(" || Existing movie = '" + existingMovie.toString() + "'");

                Element newReview = dom.createElement("review");
                newReview.setAttribute("id", "review");
                //existingMovie.appendChild(newReview);

                Element nickname = dom.createElement("nickname");
                nickname.setAttribute("id", "userName");
                nickname.setTextContent(userName);

                Element rating = dom.createElement("rating");
                rating.setAttribute("id", "starsAmount");
                rating.setTextContent(movieRating);

                newReview.appendChild(nickname);
                newReview.appendChild(rating);
                existingMovie.appendChild(newReview);

                NodeList children = existingMovie.getChildNodes();
                NodeList gChildren = newReview.getChildNodes();
                for (int k = 0; k < gChildren.getLength(); k++)
                {
                    System.out.println(" >> child = " + gChildren.item(k).getTextContent().trim());
                }


            } else
            {
                System.out.println("  - movie '" + movieTitle + "' doesn't exist in XML");

                Element newMovie = dom.createElement("movie");
                newMovie.setAttribute("id", movieTitle);
                //rootElement.appendChild(newMovie);

                Element newReview = dom.createElement("review");
                newReview.setAttribute("id", "review");
                //newMovie.appendChild(newReview);

                Element nickname = dom.createElement("nickname");
                nickname.setAttribute("id", "userName");
                nickname.setTextContent(userName);

                Element rating = dom.createElement("rating");
                rating.setAttribute("id", "starsAmount");
                rating.setTextContent(movieRating);

                newReview.appendChild(nickname);
                newReview.appendChild(rating);
                newMovie.appendChild(newReview);
                rootElement.appendChild(newMovie);

                NodeList gC = newReview.getChildNodes();
                for (int h = 0; h < gC.getLength(); h++)
                {
                    System.out.println(" >> child = " + gC.item(h).getTextContent().trim());
                }
            }


            // https://stackoverflow.com/questions/10962267/how-to-write-to-an-existing-xml-file-using-java


            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(dom);
            StreamResult result = new StreamResult(new StringWriter());
            //StreamResult output = new StreamResult(fos);

            transformer.transform(source, result);

            String xmlString = result.getWriter().toString();
            //System.out.println(xmlString);


            //FileOutputStream fos = context.openFileOutput("reviews.xml", Context.MODE_APPEND);
            //FileInputStream fis = context.openFileInput("reviews.xml");

            String filename = context.getFilesDir() + "reviews.xml";
            File file = new File(filename);

            FileOutputStream fos = context.openFileOutput("reviews.xml", Context.MODE_APPEND);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            System.out.println(" -- oos formed");

            oos.writeObject(xmlString);

            System.out.println(" -- oos.writeObject done");

            oos.close();

            System.out.println(" -- oos closed");

            /*
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xmlString);

            bw.flush();
            bw.close();
            */


            System.out.println(" Successfully added review!");

        } catch (Exception ex) { ex.printStackTrace();
        }

    }

}

