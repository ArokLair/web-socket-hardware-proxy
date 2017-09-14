package ec.com.umbral.ws;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.google.gson.Gson;

import ec.com.umbral.messages.ConnectionInfoDevice;
import ec.com.umbral.messages.MessageInfoDevice;
import ec.com.umbral.messages.StatusInfoDevice;

@ServerEndpoint(value = "/websocket/hw-proxy")
public class HwProxy {

	private static final Map<String, HwProxy> connections = new HashMap<String, HwProxy>();
	//private static final Logger log = LoggerFactory.getLogger(HwProxy.class);
	private static final Log log = LogFactory.getLog(HwProxy.class);

	private String connectionId=null;
	private String device=null;
	private Gson jsonProcessor=new Gson();
	private Session session;

	public HwProxy() {
		
	}
//	public HwProxy(String connectionId, String device) {
//		this.connectionId = connectionId;
//		this.device = device;
//		this.jsonProcessor = new Gson();
//	}

	@OnOpen
	public void onOpen(Session session) {
		this.setSession(session);
		this.device=session.getPathParameters().get("devID");
		sendConnectionInfo(session);
		sendStatusInfoToOtherClients(new StatusInfoDevice(device, StatusInfoDevice.STATUS.CONNECTED));
		connections.put(connectionId, this);
	}

	@OnClose
	public void onClose() {
		 sendStatusInfoToOtherClients(new StatusInfoDevice(device, StatusInfoDevice.STATUS.DISCONNECTED));
		 connections.remove(connectionId);
	}
	
	@OnMessage
	public void onTextMessage(String mess) {
		 final MessageInfoDevice message = jsonProcessor.fromJson(mess, MessageInfoDevice.class);
         final HwProxy destinationConnection = getDestinationDevConnection(message.getMessageInfo().getTo_dev());
         if (destinationConnection != null) {
             final CharBuffer jsonMessage = CharBuffer.wrap(jsonProcessor.toJson(message));
             try {
				destinationConnection.getSession().getBasicRemote().sendText(jsonMessage.toString());
			} catch (IOException e) {
				log.error("Error de IO");
				e.printStackTrace();
			}
         } else {
             log.warn("Se está intentando enviar un mensaje a un device no conectado");
}
	}
	
	private HwProxy getDestinationDevConnection(String to_dev) {
		for (HwProxy connection : connections.values()) {
            if (to_dev.equals(connection.getDevice())) {
                return connection;
            }
        }
		return null;
	}
	private void sendStatusInfoToOtherClients(StatusInfoDevice message) {
		final Collection<HwProxy> otherUsersConnections = getAllChatConnectionsExceptThis();
		for (HwProxy connection : otherUsersConnections) {
			try {
				connection.getSession().getBasicRemote()
						.sendText(CharBuffer.wrap(jsonProcessor.toJson(message)).toString());
			} catch (IOException e) {
				log.error("No se pudo enviar el mensaje", e);
			}
		}

	}

	private Collection<HwProxy> getAllChatConnectionsExceptThis() {
		final Collection<HwProxy> allConnections = connections.values();
		allConnections.remove(this);
		return allConnections;
	}

	private void sendConnectionInfo(Session session) {
		final List<String> activeDevices = getActiveDevices();
		final ConnectionInfoDevice connectionInfoDevice = new ConnectionInfoDevice(device, activeDevices);
		try {
			session.getBasicRemote().sendText(CharBuffer.wrap(jsonProcessor.toJson(connectionInfoDevice)).toString());
		} catch (IOException e) {
			log.error("No se pudo enviar el mensaje al dispotivos", e);
		}

	}

	private List<String> getActiveDevices() {
		final List<String> activeDevices = new ArrayList<String>();
		for (HwProxy connection : connections.values()) {
			activeDevices.add(connection.getDevice());
		}
		return activeDevices;
	}

	



	@OnError
	public void onError(Throwable t) throws Throwable {
		log.error("Error: " + t.toString(), t);
	}

	public String getDevice() {
		return device;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
