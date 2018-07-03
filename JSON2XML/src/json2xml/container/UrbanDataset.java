package json2xml.container;

import json2xml.specification.*;
import json2xml.context.*;
import json2xml.values.*;
public class UrbanDataset
{
  private Specification specification;
  private Context context;
  private Values values;
  
  public UrbanDataset() {}
  
  public Specification getSpecification()
  {
    return specification;
  }
  
  public void setSpecification(Specification specification) { this.specification = specification; }
  
  public Context getContext()
  {
    return context;
  }
  
  public void setContext(Context context) { this.context = context; }
  
  public Values getValues() {
    return values;
  }
  
  public void setValues(Values values) { this.values = values; }
}