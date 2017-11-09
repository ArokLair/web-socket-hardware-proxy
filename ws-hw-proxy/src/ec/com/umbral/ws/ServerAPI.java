package ec.com.umbral.ws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import ec.com.umbral.messages.MessageInfoDevice;

@ClientEndpoint
public class ServerAPI {

	private Session sesion;
	private String wsURL;
	private static final String SERVER_ID = "[SERVER]";
	private Gson jsonparse = new Gson();
	private ConnectionInfoDevice cid;
	private static final Log log = LogFactory.getLog(ServerAPI.class);
	// SAMPLE URL =
	// ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID=HWPOS001
	private Object someObject = new Object();

	public ServerAPI() {
	}

	public void connect(String endPointWS) {
		WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
		try {
			URI endPointURI = ServerAPI.appendUri(endPointWS, "devID=" + SERVER_ID);
			this.sesion = wsc.connectToServer(this, endPointURI);
			synchronized (someObject) {
				try {
					someObject.wait(2000);
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
		cid = jsonparse.fromJson(message, ConnectionInfoDevice.class);
		if(cid.getConnectionInfo()==null) {
			MessageInfoDevice mid = jsonparse.fromJson(message, MessageInfoDevice.class);
			if(mid.getMessageInfo().getFrom_dev().equals(HwProxy.HWPROXY_ID)) {
				someObject=new Exception(mid.getMessageInfo().getMessage());
			}
		}
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

	public void sendMessage(String from, String to, String message,String printer) throws Exception {
			MessageInfoDevice msg = new MessageInfoDevice(from, to, message,printer);
			this.sesion.getBasicRemote().sendText(jsonparse.toJson(msg));
			synchronized (someObject) {
				try {
					someObject.wait(2000);
				} catch (InterruptedException e) {
					log.error(e);
				}
			}
			if(someObject instanceof Exception) {
				throw (Exception)someObject;
			}
	}

	public String getAciveDevices() {
		return jsonparse.toJson(this.cid.getConnectionInfo());
	}
	
	/*
	 * TODO Por implementar
	 */
	public List<String> getPrinters(String dev) throws IOException{
		List<String> ret = null;
		this.sesion.getBasicRemote().sendText("getPrinters");
		
		return ret;
	}

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

}
