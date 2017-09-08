package ec.com.umbral.ws;

public class StatusInfoDevice {
	public enum STATUS {CONNECTED, DISCONNECTED}

	private final StatusInfo statusInfo;
	
	public StatusInfoDevice(String device, STATUS status) {
        this.statusInfo = new StatusInfo(device, status);
    }

    public StatusInfo getStatusInfo() {
        return statusInfo;
    }
}
