package com.example.myapplication;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;


import org.xml.sax.InputSource;


// SOURCE FOR CLASS
/* https://mkyong.com/java/how-to-create-xml-file-in-java-dom/ */


public class WriteXML
{
    public WriteXML() {}



    public void readXML(String filename)
    {
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();

            /*
            FileInputStream fis = new FileInputStream(filename);
            InputSource is = new InputSource(fis);
            Document doc = builder.parse(is); */


            dom = db.parse(new InputSource(new StringReader(filename)));

            // get the first element
            Element element = dom.getDocumentElement();

            // get all child nodes
            NodeList nodes = element.getChildNodes();

            // print the text content of each child
            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println("" + nodes.item(i).getTextContent());
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }



    public void writeXml() throws ParserConfigurationException, TransformerException
    {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("root");
        doc.appendChild(rootElement);



        // add xml elements
        Element staff = doc.createElement("child");

        // add staff to root
        rootElement.appendChild(staff);

        // add xml attribute
        staff.setAttribute("title", "movieTitle");



        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
    }

}

