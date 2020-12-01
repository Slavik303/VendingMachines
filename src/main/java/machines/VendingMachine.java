package machines;

import java.util.Date;

public class VendingMachine {
	
	/* TODO add Type */
	private String serailNb;
	private String installationAddr;
	private String location;
	private String gpsCoords;
	private Date lastIntervention;
	private String notes;
	
	public VendingMachine() {
	}

	public String getSerailNb() {
		return serailNb;
	}

	public void setSerailNb(String serailNb) {
		this.serailNb = serailNb;
	}

	public String getInstallationAddr() {
		return installationAddr;
	}

	public void setInstallationAddr(String installationAddr) {
		this.installationAddr = installationAddr;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGpsCoords() {
		return gpsCoords;
	}

	public void setGpsCoords(String gpsCoords) {
		this.gpsCoords = gpsCoords;
	}

	public Date getLastIntervention() {
		return lastIntervention;
	}

	public void setLastIntervention(Date lastIntervention) {
		this.lastIntervention = lastIntervention;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
