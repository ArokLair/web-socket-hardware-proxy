package ec.com.umbral.ws;

import javax.websocket.Session;

public class ClientDevice {
	private final int id;
	private final Session session;
	
	public ClientDevice(int id, Session session) {
		this.id=id;
		this.session=session;
	}
}
