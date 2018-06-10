package json2xml.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.w3c.dom.Element;

import com.google.gson.Gson;

import json2xml.container.Container;
import json2xml.context.Context;
import json2xml.specification.Specification;
import json2xml.values.Values;




public class Json2Xml {

	public static void main(String[] args) {
		String fileJson = "C:/Users/bomb-/Desktop/FromXMLtoJson/Final.json";
	
			
			try {
				Gson gson = new Gson();
				BufferedReader reader = new BufferedReader(new FileReader(fileJson));
				Container result = gson.fromJson(reader, Container.class);
				
				String filename = "./final.xml";
				System.out.println("Generating message file");
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				doc.setXmlStandalone(true);
				
				
				Element UD = doc.createElement("UrbanDataset");
				
				//// Urban Dataset attributes definition
				Attr xmlnsxsi = doc.createAttribute("xmlns:xsi");
				xmlnsxsi.setValue("http://www.w3.org/2001/XMLSchema-instance");
				UD.setAttributeNode(xmlnsxsi);
				
				Attr schemaLocation = doc.createAttribute("xsi:schemaLocation");
				schemaLocation.setValue("smartcityplatform:enea:information:xml:schemas:main:urbandataset http://smartcityplatform.enea.it/specification/information/1.0/xml/schemas/scps-urbandataset-schema-1.0.xsd");
				UD.setAttributeNode(schemaLocation);
				
				Attr xmlnsEl = doc.createAttribute("xmlns");
				xmlnsEl.setValue("smartcityplatform:enea:information:xml:schemas:main:urbandataset");
				UD.setAttributeNode(xmlnsEl);
				
				doc.appendChild(UD);
				Specification spec = result.getUrbanDataset().getSpecification();
				Element specNode = spec.text(doc);
				
				Context cont = result.getUrbanDataset().getContext();
				Element contextNode = cont.text(doc);
				
				Values val = result.getUrbanDataset().getValues();
				Element valuesNode = val.text(doc);
				
				
				UD.appendChild(specNode);
				UD.appendChild(contextNode);
				UD.appendChild(valuesNode);				
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
				DOMSource source = new DOMSource(doc);
				
				StreamResult r = new StreamResult(new File(filename));
				transformer.transform(source, r);
				System.out.println("Message template file saved");


				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		
		

	}

}
