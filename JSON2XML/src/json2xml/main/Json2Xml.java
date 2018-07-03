package json2xml.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import json2xml.container.Container;
import json2xml.container.UrbanDataset;
import json2xml.context.Context;
import json2xml.specification.Specification;
import json2xml.values.Values;




public class Json2Xml {

	public static void main(String[] args) {
		// Setting options that allows us to convert file from XML to JSON and vice-versa
		CommandLineParser parser = new GnuParser();
		Options options = new Options();
		
		// We set C=0 if we want to parse from XML to JSON also 1
		Option convert = new Option("C","parser", true, "allows users to parse from XML to JSON (C=0) and vice-versa (C=1)");
		convert.setRequired(true);
		convert.setArgName("MODE OF PARSING");
		options.addOption(convert);
		
		Option inputFile = new Option("FI", "file input", true, "path of input file that we want to convert");
		inputFile.setRequired(true);
		inputFile.setArgName("FILE INPUT NAME");
		options.addOption(inputFile);
		
		Option outputFile = new Option("FO", "file output", true, "path of output file that we want to convert");
		outputFile.setRequired(true);
		outputFile.setArgName("FILE OUTPUT NAME");
		options.addOption(outputFile);
		
		
		
		CommandLine commandLine = null;
		try {
			commandLine = parser.parse( options, args );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("Menu", options);
		
		if(commandLine.hasOption("C")) {
			int modeOfParsing = Integer.parseInt(commandLine.getOptionValue("C"));
			if(modeOfParsing == 0) {
				String pathIN = commandLine.getOptionValue("FI");
				String pathOUT = commandLine.getOptionValue("FO");
				System.out.println("Parsing From XML: " + pathIN +  " to JSON: " + pathOUT);
				try {
					
					InputStream inputStream= new FileInputStream(pathIN);
			        Reader reader = new InputStreamReader(inputStream,"UTF-8");
			        InputSource is = new InputSource(reader);
			        is.setEncoding("UTF-8");
			        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			        Document doc = dBuilder.parse(is);
					Container container = new Container();
					UrbanDataset urban = container.getUrbanDataset();
					NodeList nList = doc.getChildNodes();
					NodeList mainTag = nList.item(1).getChildNodes();
					int len = mainTag.getLength();
					
					for(int i=1;i<len; i+=2) {
						Node node = mainTag.item(i);
						String nodeName = node.getNodeName();
						if (nodeName.equals("specification")) {
							Specification s = container.specification(node);
							urban.setSpecification(s);					      
						}else if(nodeName.equals("context")) {
							Context c = container.context(node);
							urban.setContext(c);
							
						}else {
							Values v = container.values(node);
							urban.setValues(v);
							
						}
					}
					GsonBuilder builder = new GsonBuilder();
					builder.disableHtmlEscaping();
					Gson gson = builder.setPrettyPrinting().create();
					
					JsonParser Jparser = new JsonParser();
			        String jsonString = gson.toJson(container);
			        JsonObject json = Jparser.parse(jsonString).getAsJsonObject();
			        String prettyJson = gson.toJson(json);
			        OutputStream outputStream = new FileOutputStream(pathOUT);
			        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
			        writer.write(prettyJson);
					writer.close();
					System.out.println("File json formed...");
					
				 } catch (Exception e) {
					e.printStackTrace();
			     }

			}else {
					String pathIN = commandLine.getOptionValue("FI");
					String pathOUT = commandLine.getOptionValue("FO");
					System.out.println("Parsing From JSON: " + pathIN +  " to XML: " + pathOUT);
					
					try {
						GsonBuilder builder = new GsonBuilder();
						builder.disableHtmlEscaping();
						Gson gson = builder.create();
						BufferedReader reader = new BufferedReader(
								   new InputStreamReader(
						                      new FileInputStream(pathIN), "UTF8"));
						
						Container result = gson.fromJson(reader, Container.class);
						
						
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						Document doc = docBuilder.newDocument();
						doc.setXmlStandalone(true);
						
						
						Element UD = doc.createElement("UrbanDataset");
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
						
						StreamResult r = new StreamResult(new File(pathOUT));
						transformer.transform(source, r);
						System.out.println("File XML generated. . .");
	
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
				}
		}
			
			
	}
			
		
		

	

}
