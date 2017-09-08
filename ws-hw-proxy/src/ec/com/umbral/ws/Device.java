package ec.com.umbral.ws;

public class Device {
	private String devID;
	private String name;
	private String periphal;
	
	public Device(String devID,String name, String periphal) {
		this.devID=devID;
		this.name=name;
		this.periphal=periphal;
	}
	
	public String getDevID() {
		return devID;
	}
	public void setDevID(String devID) {
		this.devID = devID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPeriphal() {
		return periphal;
	}
	public void setPeriphal(String periphal) {
		this.periphal = periphal;
	}
	
	
	
}
