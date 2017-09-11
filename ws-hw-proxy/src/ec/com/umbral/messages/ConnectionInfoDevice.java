package ec.com.umbral.ws;

import ec.com.umbral.ws.StatusInfoDevice.STATUS;

public class ConnectionInfoDevice {
	private final String id_dev;
	private final STATUS status;
	
	public ConnectionInfoDevice(String id_dev, STATUS status) {
		this.id_dev=id_dev;
		this.status=status;
	}
}
