package ec.com.umbral.messages;

public class StatusInfoDevice {
	public enum STATUS {
		CONNECTED, DISCONNECTED
	}

	private final StatusInfo statusInfo;

	public StatusInfoDevice(String device, STATUS status) {
		this.statusInfo = new StatusInfo(device, status);
	}

	public StatusInfo getStatusInfo() {
		return statusInfo;
	}

	class StatusInfo {

		private final String device;

		private final STATUS status;

		public StatusInfo(String device, STATUS status) {
			this.device = device;
			this.status = status;
		}

		public String getDevice() {
			return device;
		}

		public STATUS getStatus() {
			return status;
		}
	}

}
