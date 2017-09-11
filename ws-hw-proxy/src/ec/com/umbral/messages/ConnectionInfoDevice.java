package ec.com.umbral.messages;

import java.util.List;

public class ConnectionInfoDevice {

    private final ConnectionInfo connectionInfo;

    public ConnectionInfoDevice(String device, List<String> activeDevs) {
        this.connectionInfo = new ConnectionInfo(device, activeDevs);
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    class ConnectionInfo {

        private final String dev;

        private final List<String> activeDevs;

        private ConnectionInfo(String dev, List<String> activeDevs) {
            this.dev = dev;
            this.activeDevs = activeDevs;
        }

        public String getDev() {
            return dev;
        }

        public List<String> getActiveDevs() {
            return activeDevs;
        }
    }

}