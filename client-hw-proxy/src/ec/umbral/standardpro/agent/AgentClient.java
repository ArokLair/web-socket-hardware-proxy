package ec.umbral.standardpro.agent;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;


@ClientEndpoint
public class AgentClient {

	private final String uri = "ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID=HWPOS002";
	private Session session;
	private StandarPROAgentGUI clientWindow;
	private static final Log log = LogFactory.getLog(StandarPROAgentGUI.class);
	
	public AgentClient(StandarPROAgentGUI cw) {
		clientWindow = cw;
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, new URI(uri));

		} catch (Exception ex) {
			log.error("Error al conectar al ws:",ex);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		
	}

	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException ex) {

		}
	}
}
