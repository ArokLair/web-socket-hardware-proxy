package ec.com.umbral.messages;

import java.util.List;

public class ConnectionInfoDevice {

    private final ConnectionInfo connectionInfo;

    public ConnectionInfoDevice(String dev, List<String> activeDevs) {
        this.connectionInfo = new ConnectionInfo(dev, activeDevs);
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    class ConnectionInfo {

        private final String device;
        private final List<String> activeDevs;
        private ConnectionInfo(String dev, List<String> activeDevs) {
            this.device = dev;
            this.activeDevs = activeDevs;
        }

        public List<String> getActiveDevs() {
            return activeDevs;
        }

		public String getDevice() {
			return device;
		}
    }

}