package json2xml.specification;

import java.net.URI;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class Specification {
	
	public Specification() {}
	private Double version;
	private IdSpecification id;
	private String name;
	private URI uri;
	private Properties properties;
	
	public Double getVersion() {
		return version;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setVersion(Double version) {
		this.version = version;
	}
	
	public IdSpecification getId() {
		return id;
	}
	public void setId(IdSpecification id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public Element text(Document doc) {
		Element spec = doc.createElement("specification");
		if(this.getVersion()!=null) {
			spec.setAttribute("version", String.valueOf(this.getVersion()));
		}
		
		Element ID = doc.createElement("id");
		ID.appendChild(doc.createTextNode(this.getId().getValue()));
		
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(this.getName()));
		
		Element uri = doc.createElement("uri");
		uri.appendChild(doc.createTextNode(String.valueOf(this.getUri())));
		spec.appendChild(ID);
		spec.appendChild(name);
		spec.appendChild(uri);
		if(this.getProperties()!=null) {
			Element properties = doc.createElement("properties");
			
			for (PropertySpecification prop : this.getProperties().getPropertyDefinition()) {
				if(prop.getName()!=null) {
					Element s = prop.text(doc);
					properties.appendChild(s);
				}
			}
			spec.appendChild(properties);
		}
		return spec;
	}
	

}
