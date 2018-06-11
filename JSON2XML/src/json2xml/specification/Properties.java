package json2xml.specification;

import java.util.ArrayList;

public class Properties {
	public Properties() {}
	private ArrayList<PropertySpecification> propertyDefinition;
	public ArrayList<PropertySpecification> getPropertyDefinition() {
		return propertyDefinition;
	}
	public void setPropertyDefinition(ArrayList<PropertySpecification> propertyDefinition) {
		this.propertyDefinition = propertyDefinition;
	}

}
