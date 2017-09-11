package ec.com.umbral.messages;

public class MessageInfoDevice {
	private final MessageInfo messageInfo;

	public MessageInfoDevice(String from_dev, String to_dev, String message) {
		this.messageInfo = new MessageInfo(from_dev, to_dev, message);
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public class MessageInfo {

		private final String from_dev;

		private final String to_dev;

		private final String message;

		public MessageInfo(String from_dev, String to_dev, String message) {
			this.from_dev = from_dev;
			this.to_dev = to_dev;
			this.message = message;
		}

		public String getFrom_dev() {
			return from_dev;
		}

		public String getTo_dev() {
			return to_dev;
		}

		public String getMessage() {
			return message;
		}

		
	}

}
