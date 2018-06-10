package json2xml.specification;
import java.net.URI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class PropertySpecification {
	

	private String propertyName= null;
	private String propertyDescription= null;
	private String dataType=null;
	private URI codeList = null;
	private String unitOfMeasure= null;
	
	//private ArrayList<String> subProperties = null;
	private SubProperties subProperties = null;
	
	
	public SubProperties getSubProperties() {
		if(subProperties==null) {
			return null;
		}
		return subProperties;
	}
	public void setSubProperties(SubProperties subProperties) {
		this.subProperties = subProperties;
	}
	public PropertySpecification() {}
	public String getName() {
		return propertyName;
	}
	public void setName(String name) {
		this.propertyName = name;
	}
	public String getDescription() {
		return propertyDescription;
	}
	public void setDescription(String description) {
		this.propertyDescription = description;
	}
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	
	public URI getCodeList() {
		return codeList;
	}
	public void setCodeList(URI codeList) {
		this.codeList = codeList;
	}
	public Element text(Document doc) {
		Element def = doc.createElement("propertyDefinition");
		
		Element name = doc.createElement("propertyName");
		name.appendChild(doc.createTextNode(this.getName()));
		def.appendChild(name);
		
		if(this.getDescription()!=null) {
			Element descr = doc.createElement("propertyDescription");
			descr.appendChild(doc.createTextNode(this.getDescription()));
			def.appendChild(descr);
		}
		
		if (this.getSubProperties() == null) {
			Element dataType = doc.createElement("dataType");
			dataType.appendChild(doc.createTextNode(this.getDataType()));
			def.appendChild(dataType);
			Element unit = doc.createElement("unitOfMeasure");
			if (this.unitOfMeasure != null) {
				unit.appendChild(doc.createTextNode(this.getUnitOfMeasure()));
			} else {
				unit.appendChild(doc.createTextNode("adimensionale"));
			}
			def.appendChild(unit);
		} else {
			Element subP = doc.createElement("subProperties");
			
			for (String s : subProperties.getPropertyName()) {
				Element subPName = doc.createElement("propertyName");
				subPName.appendChild(doc.createTextNode(s));
				subP.appendChild(subPName);
			}
			
			
			def.appendChild(subP);
		}
		
		return def;
	}
	

	
	
	

}