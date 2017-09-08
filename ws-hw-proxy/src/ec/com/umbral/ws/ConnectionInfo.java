package ec.com.umbral.ws;

import java.util.List;

public class ConnectionInfo {
	 private final String user;

     private final List<Device> activeDevices;

     private ConnectionInfo(String device, List<Device> activeDevices) {
         this.user = device;
         this.activeDevices = activeDevices;
     }

     public String getUser() {
         return user;
     }

     public List<Device> getActiveDevices() {
         return activeDevices;
     }
}
