package ec.com.umbral.ws;

public class MessageInfo {


	private final String to_dev;

	private final String message;

	public MessageInfo(String to_dev, String message) {
		this.to_dev = to_dev;
		this.message = message;
	}

	public String getTo_dev() {
		return to_dev;
	}
	public String getMessage() {
		return message;
	}


}
