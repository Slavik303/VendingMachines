package machines;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.NaturalId;


@Entity(name = "VendingMachine")
@XmlRootElement(name = "vendingMachine")
@XmlAccessorType(XmlAccessType.FIELD)
public class VendingMachine {
	
	@Id
	@GeneratedValue
	private Long id;

	@NaturalId
	@Column(name = "serial_nb")
	private String serialNb;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Embedded
	@Column(name = "installation_address")
	private Address installationAddress;
	
	private String location;
	
	private float longitude;

	private float latitude;
	
	@Column(name = "last_intervention")
	@Temporal(TemporalType.DATE)
	private Date lastIntervention;
	
	private String notes;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "last_report_id", referencedColumnName = "id")
	private DailyReport lastReport;
	
	public VendingMachine() {
		installationAddress = new Address();
	}

	public VendingMachine(String sn) {
		this();
		serialNb = sn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNb() {
		return serialNb;
	}

	public void setSerialNb(String serialNb) {
		this.serialNb = serialNb;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Address getInstallationAddress() {
		return installationAddress;
	}

	public void setInstallationAddress(Address installationAddress) {
		this.installationAddress = installationAddress;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
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

	public DailyReport getLastReport() {
		return lastReport;
	}

	public void setLastReport(DailyReport lastReport) {
		this.lastReport = lastReport;
	}

	public VendingMachine merge(VendingMachine that) {
		if (that.getSerialNb() != null)
			this.setSerialNb(that.getSerialNb());
		if (that.getType() != null)
			this.setType(that.getType());
		if (that.getInstallationAddress() != null)
			this.setInstallationAddress(that.getInstallationAddress());
		if (that.getLocation() != null)
			this.setLocation(that.getLocation());
		if (that.getLongitude() != 0.0)
			this.setLongitude(that.getLongitude());
		if (that.getLatitude() != 0.0)
			this.setLatitude(that.getLatitude());
		if (that.getLastIntervention() != null)
			this.setLastIntervention(that.getLastIntervention());
		if (that.getNotes() != null)
			this.setNotes(that.getNotes());
		return this;
	}

}
