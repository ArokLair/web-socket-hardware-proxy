package ec.com.umbral.ws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.google.gson.Gson;

import ec.com.umbral.messages.ConnectionInfoDevice;

@ClientEndpoint
public class ServerAPI {

	private Session sesion;
	private String wsURL;
	private static final String SERVER_ID = "SERVER";
	private Gson jsonparse = new Gson();
	private ConnectionInfoDevice cid;
	private static final Log log = LogFactory.getLog(ServerAPI.class);
	// SAMPLE URL =
	// ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID=HWPOS001
	private Object someObject=new Object();

	public ServerAPI() {
	}

	public void connect(String endPointWS) {
		WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
		try {
			URI endPointURI = ServerAPI.appendUri(endPointWS, "devID=" + SERVER_ID);
			this.sesion = wsc.connectToServer(this, endPointURI);
			synchronized (someObject) {
				try {
					someObject.wait();
				} catch (InterruptedException e) {
					log.error(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@OnMessage
	public void onTextMessage(String message) {
		this.cid = jsonparse.fromJson(message, ConnectionInfoDevice.class);
		synchronized (someObject) {
			someObject.notify();
		}
	}

	public void disconnect() {
		try {
			this.sesion.close();
		} catch (IOException e) {
			log.error(e);
		}
	}

	public void sendMessage(String from, String to, String message) {
		try {
			this.sesion.getBasicRemote().sendText("Hello!");
		} catch (IOException e) {
			log.error(e);
		}
	}

	// public List<String> getAciveDevices(){
	// this.sesion.getBasicRemote().sendText("GET LIST DEVICES");
	// }

	private static URI appendUri(String uri, String appendQuery) throws URISyntaxException {
		URI oldUri = new URI(uri);

		String newQuery = oldUri.getQuery();
		if (newQuery == null) {
			newQuery = appendQuery;
		} else {
			newQuery += "&" + appendQuery;
		}

		URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(), oldUri.getPath(), newQuery,
				oldUri.getFragment());

		return newUri;
	}

	public String getWsURL() {
		return wsURL;
	}

	public void setWsURL(String wsURL) {
		this.wsURL = wsURL;
	}

	public ConnectionInfoDevice getCid() {
		return cid;
	}

	public void setCid(ConnectionInfoDevice cid) {
		this.cid = cid;
	}

}
