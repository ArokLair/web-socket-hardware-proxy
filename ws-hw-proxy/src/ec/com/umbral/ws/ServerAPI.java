package ec.com.umbral.ws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.google.gson.Gson;

@ClientEndpoint
public class ServerAPI {

	private Session sesion;
	private String wsURL;
	private static final String SERVER_ID = "SERVER";
	private Gson jsonparse = new Gson();
	private static final Log log = LogFactory.getLog(ServerAPI.class);
	// ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID=HWPOS001

	// public ServerAPI() {
	// this.setWsURL("ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID="+SERVER_ID);
	// }
	public ServerAPI(String endPointWS) {
		WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
		try {
			URI endPointURI = ServerAPI.appendUri(endPointWS, "devID=" + SERVER_ID);
			wsc.connectToServer(this, endPointURI);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@OnOpen
	public void onOpen(Session sess) {
		this.sesion = sess;
		log.info("Abriendo websocket desde el servidor");
	}

	@OnClose
	public void onClose(Session sess, CloseReason reason) {

	}

	@OnMessage
	public void onMessage(String message) {
		log.debug(message);
	}

	public void sendMessage(String from, String to, String message) {
		try {

			this.sesion.getBasicRemote().sendText("Hello!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getAciveDevices() {
		List<String> ret = new ArrayList<String>();

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
