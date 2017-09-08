package ec.com.umbral.ws;

import java.io.EOFException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/hw-proxy")
public class HwProxy {

	public HwProxy() {
		
	}
	
	@OnOpen
	public void onOpen(Session session) {
		
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
            count ++;
        }
        if (root instanceof EOFException) {
            // Assume this is triggered by the user closing their browser and
            // ignore it.
        } else {
            throw t;
        }
    }
}
