package ec.com.umbral.ws;

import ec.com.umbral.ws.StatusInfoDevice.STATUS;

public class StatusInfo {
	 private final String device;

     private final STATUS status;

     public StatusInfo(String user, STATUS status) {
         this.device = user;
         this.status = status;
     }

     public String getUser() {
         return device;
     }

     public STATUS getStatus() {
         return status;
     }
}
