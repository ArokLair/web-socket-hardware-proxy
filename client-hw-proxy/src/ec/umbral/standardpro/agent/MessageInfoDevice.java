package ec.umbral.standardpro.agent;


public class MessageInfoDevice {
	private final MessageInfo messageInfo;

	public MessageInfoDevice(String from_dev, String to_dev, String message,String printer) {
		this.messageInfo = new MessageInfo(from_dev, to_dev, message,printer);
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public class MessageInfo {

		private final String from_dev;

		private final String to_dev;

		private final String message;
		
		private final String to_printer;

		public MessageInfo(String from_dev, String to_dev, String message, String to_printer) {
			this.from_dev = from_dev;
			this.to_dev = to_dev;
			this.message = message;
			this.to_printer=to_printer;
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
		public String getTo_printer() {
			return to_printer;
		}	
	}
}