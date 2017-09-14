package ec.com.umbral.ws;

import java.io.IOException;
import java.net.URI;

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

@ClientEndpoint
public class ServerAPI {
	
	private Session sesion;
	private static final Log log = LogFactory.getLog(HwProxy.class);
	//ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID=HWPOS001
	public ServerAPI(URI endPointURI) {
		WebSocketContainer wsc=ContainerProvider.getWebSocketContainer();
		try {
			wsc.connectToServer(this, endPointURI);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@OnOpen
	public void onOpen(Session sess) {
		this.sesion=sess;
		log.info("Abriendo websocket desde el servidor");
	}
	
	@OnClose
	public void onClose(Session sess, CloseReason reason) {
		
	}
	
	@OnMessage
	public void onMessage(String message) {
		
	}
	
	public void sendMessage(String from,String to,String message) {
		try {
			this.sesion.getBasicRemote().sendText("Hello!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
