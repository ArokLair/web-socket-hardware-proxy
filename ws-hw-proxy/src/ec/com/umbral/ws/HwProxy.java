package ec.com.umbral.ws;

import java.io.EOFException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ec.com.umbral.messages.ConnectionInfoDevice;
import ec.com.umbral.messages.StatusInfoDevice;

@ServerEndpoint(value = "/websocket/hw-proxy")
public class HwProxy {

	private static final Map<String, HwProxy> connections = new HashMap<String, HwProxy>();
	private static final Logger log = LoggerFactory.getLogger(HwProxy.class);

	private final String connectionId;
	private final String device;
	private final Gson jsonProcessor;
	private Session session;

	public HwProxy(String connectionId, String device) {
		this.connectionId = connectionId;
		this.device = device;
		this.jsonProcessor = new Gson();
	}

	@OnOpen
	public void onOpen(Session session) {
		this.setSession(session);
		sendConnectionInfo(session);
		sendStatusInfoToOtherUsers(new StatusInfoDevice(device, StatusInfoDevice.STATUS.CONNECTED));
		connections.put(connectionId, this);
	}

	private void sendStatusInfoToOtherUsers(StatusInfoDevice message) {
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

	@OnMessage
	public void onTextMessage(String message) {

	}

	@OnClose
	public void onClose() {

	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		// Most likely cause is a user closing their browser. Check to see if
		// the root cause is EOF and if it is ignore it.
		// Protect against infinite loops.
		int count = 0;
		Throwable root = t;
		while (root.getCause() != null && count < 20) {
			root = root.getCause();
			count++;
		}
		if (root instanceof EOFException) {
			// Assume this is triggered by the user closing their browser and
			// ignore it.
		} else {
			throw t;
		}
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
