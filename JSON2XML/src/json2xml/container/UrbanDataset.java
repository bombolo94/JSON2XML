package json2xml.container;


import json2xml.context.Context;
import json2xml.specification.Specification;
import json2xml.values.Values;

public class UrbanDataset {
	
	public UrbanDataset() {}
	private Specification specification;
	private Context context;
	private Values values;


	public Specification getSpecification() {
		return specification;
	}
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public Values getValues() {
		return values;
	}
	public void setValues(Values values) {
		this.values = values;
	}	
	
	
}
