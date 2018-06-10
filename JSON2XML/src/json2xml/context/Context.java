package json2xml.context;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Context {
	
	public Context() {}
	
	private Producer producer;
	private String timeZone;
	private String timestamp;
	private Coordinates coordinates;
	private String language;
	private String schemeID;
	public String getSchemeID() {
		return schemeID;
	}

	public void setSchemeID(String schemeID) {
		this.schemeID = schemeID;
	}
	
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Producer getProducer() {
		return producer;
	}
	public void setProducer(Producer producer) {
		this.producer = producer;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Element text(Document doc) {
		Element context = doc.createElement("context");
		
		Element producer = doc.createElement("producer");
		Element ID = doc.createElement("id");
		if(this.getProducer().getSchemeID()!=null) {
			ID.setAttribute("schemeID", this.getProducer().getSchemeID());
			ID.appendChild(doc.createTextNode(this.getProducer().getId()));
		
		}
		producer.appendChild(ID);
		
		Element timeZone = doc.createElement("timeZone");
		timeZone.appendChild(doc.createTextNode(this.getTimeZone()));
		
		Element timestamp = doc.createElement("timestamp");
		timestamp.appendChild(doc.createTextNode((this.getTimestamp())));
		if(this.getSchemeID()!=null) {
			timestamp.setAttribute("schemeID",this.getSchemeID());
			timestamp.appendChild(doc.createTextNode(this.getSchemeID()));
		}
		
		Element coordinates = doc.createElement("coordinates");
		coordinates.setAttribute("format", this.getCoordinates().getFormat());
		Element latitude = doc.createElement("latitude");
		latitude.appendChild(doc.createTextNode(String.valueOf(this.getCoordinates().getLatitude())));
		Element longitude = doc.createElement("longitude");
		longitude.appendChild(doc.createTextNode(String.valueOf(this.getCoordinates().getLongitude())));
		if(this.getCoordinates().getHeight()!=null) {
			
			Element altitude = doc.createElement("height");
			altitude.appendChild(doc.createTextNode(String.valueOf(this.getCoordinates().getHeight())));
			coordinates.appendChild(altitude);
			
		}
		
		coordinates.appendChild(latitude);
		coordinates.appendChild(longitude);
		context.appendChild(producer);
		context.appendChild(timeZone);
		context.appendChild(timestamp);
		context.appendChild(coordinates);
		if(this.getLanguage()!=null) {
			Element lang = doc.createElement("language");
			lang.appendChild(doc.createTextNode(this.getLanguage()));
			context.appendChild(lang);
		}
		
		

		return context;
	}

	



	
	
	
}
