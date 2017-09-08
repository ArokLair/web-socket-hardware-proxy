package ec.com.umbral.ws;

public class MessageInfoDevice {
	 private final MessageInfo messageInfo;

	    public MessageInfoDevice(String to_dev, String message) {
	        this.messageInfo = new MessageInfo(to_dev, message);
	    }

	    public MessageInfo getMessageInfo() {
	        return messageInfo;
	    }
}
