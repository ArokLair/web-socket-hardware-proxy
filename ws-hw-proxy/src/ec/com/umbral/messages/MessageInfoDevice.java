package ec.com.umbral.messages;

public class MessageInfoDevice {
	private final MessageInfo messageInfo;

	public MessageInfoDevice(String from, String to, String message) {
		this.messageInfo = new MessageInfo(from, to, message);
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public class MessageInfo {

		private final String from;

		private final String to;

		private final String message;

		public MessageInfo(String from, String to, String message) {
			this.from = from;
			this.to = to;
			this.message = message;
		}

		public String getFrom() {
			return from;
		}

		public String getTo() {
			return to;
		}

		public String getMessage() {
			return message;
		}
	}

}
