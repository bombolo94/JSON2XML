package json2xml.specification;
import java.net.URI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PropertySpecification {
	

	private String propertyName;
	private String propertyDescription;
	private String dataType;
	private URI codeList;
	private String unitOfMeasure;
	private SubProperties subProperties;
	
	
	public SubProperties getSubProperties() {
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
			String string = this.getDescription();
			descr.appendChild(doc.createTextNode(string));
			def.appendChild(descr);
		}
		
		if (this.getSubProperties() == null) {
			Element dataType = doc.createElement("dataType");
			dataType.appendChild(doc.createTextNode(this.getDataType()));
			def.appendChild(dataType);
			
			if(this.getCodeList()!=null) {
				Element codeList = doc.createElement("codeList");
				codeList.appendChild(doc.createTextNode(String.valueOf(this.getCodeList())));
				def.appendChild(codeList);
				
			}
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
