package ec.umbral.standardpro.agent;

public class Parameters {
	private String deviceID;
	private String SERVER_URL;
	private String printerName;
	private static final String DEV_PREFIX="?devID=";
	
	public Parameters(String deviceID, String sERVER_URL, String printerName) {
		super();
		this.deviceID = deviceID;
		SERVER_URL = sERVER_URL;
		this.printerName = printerName;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getSERVER_URL() {
		return SERVER_URL;
	}
	public void setSERVER_URL(String sERVER_URL) {
		SERVER_URL = sERVER_URL;
	}
	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	public String getSERVER_URL_DEV() {
		return this.SERVER_URL+DEV_PREFIX+this.deviceID;
	}
	
	
}
