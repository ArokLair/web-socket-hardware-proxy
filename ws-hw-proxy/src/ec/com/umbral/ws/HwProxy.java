package ec.com.umbral.ws;

import java.io.IOException;
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
	// private static final Logger log = LoggerFactory.getLogger(HwProxy.class);
	private static final Log log = LogFactory.getLog(HwProxy.class);
	static final String HWPROXY_ID = "[HWPROXY]";

	private String connectionId = new String();

	private String device = new String();
	private Gson jsonProcessor = new Gson();
	private Session session;

	public HwProxy() {

	}

	@OnOpen
	public void onOpen(Session session) {
		this.setSession(session);
		this.setConnectionId(session.getId());
		this.device = session.getRequestParameterMap().get("devID").toString();
		sendConnectionInfo(session);
		sendStatusInfoToOtherClients(new StatusInfoDevice(device, StatusInfoDevice.STATUS.CONNECTED));
		connections.put(connectionId, this);
	}

	@OnClose
	public void onClose() {
		log.info("Cerrando la conexion de un cliente....");
		sendStatusInfoToOtherClients(new StatusInfoDevice(device, StatusInfoDevice.STATUS.DISCONNECTED));
		connections.remove(connectionId);
	}

	/**
	 * Struct Json Message { messageInfo : { from_dev : '[SERVER]', to_dev :
	 * '[HWPOS001]', message : 'PRINT_FILE' } }
	 * 
	 * @param mess
	 */
	@OnMessage
	public void onTextMessage(String mess, Session sesion) {
//		log.info(mess);
		Map<?,?> map = jsonProcessor.fromJson(mess,Map.class);
		if(map!=null && map.get("option")!=null) {
			sesion.getUserProperties().put("devID", map.get("devID"));
			sesion.getUserProperties().put("printers", map.get("printers"));
			return; 
		}
		final MessageInfoDevice message = jsonProcessor.fromJson(mess, MessageInfoDevice.class);
		final HwProxy destinationConnection = getDestinationDevConnection(message.getMessageInfo().getTo_dev());
		try {
			if (destinationConnection != null) {
				final String jsonMessage = jsonProcessor.toJson(message);
				destinationConnection.getSession().getBasicRemote().sendText(jsonMessage);
			} else {
				final String info_message = "Se está intentando enviar un mensaje a un device no conectado:" + mess;
				log.warn(info_message);
				sesion.getBasicRemote().sendText(jsonProcessor.toJson(
						new MessageInfoDevice(HWPROXY_ID, message.getMessageInfo().getFrom_dev(), info_message,message.getMessageInfo().getTo_printer())));
			}
		} catch (IOException e) {
			log.error("Error de IO", e);
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
				connection.getSession().getBasicRemote().sendText(jsonProcessor.toJson(message));
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
			session.getBasicRemote().sendText(jsonProcessor.toJson(connectionInfoDevice));
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
		log.error("Error Disconected client: " + t.toString(), t);
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

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
}
