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
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.NaturalId;


@XmlRootElement(name = "VendingMachine")
@Entity(name = "VendingMachine")
public class VendingMachine {
	
	@Id
	@GeneratedValue
	private Long id;

	@NaturalId
	@Column(name = "serial_nb")
	private String serailNb;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerailNb() {
		return serailNb;
	}

	public void setSerailNb(String serailNb) {
		this.serailNb = serailNb;
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

}
