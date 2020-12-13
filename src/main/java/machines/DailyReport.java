package machines;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "DailyReport")
@XmlRootElement(name = "dailyReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class DailyReport {

	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "generation_time")
	private Date generationTime;
	
	@Column(name = "out_of_order", columnDefinition = "tinyint default false")
	private boolean outOfOrder;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "current_state")
	private State currState;

	private float temperature;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "change_machine_state")
	private ChangeMachineState changeState;
	
	@Column(name = "chip_card_error", columnDefinition = "tinyint default false")
	private boolean chipCardError;
	
	@Column(name = "contactless_card_error", columnDefinition = "tinyint default false")
	private boolean contactlessCardError;
	
	@Column(name = "sum_of_sales")
	private BigDecimal sumOfSales;
	
	@OneToMany(
			mappedBy = "report",
			cascade = CascadeType.MERGE,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@XmlElement(name = "product")
	private List<ProductReport> products;
	
	@OneToMany(
			mappedBy = "report",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	@XmlElement(name = "error")
	private List<Error> errors;
	
	@OneToOne(
			mappedBy = "lastReport",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	private VendingMachine machine;
	
	public DailyReport() {
		products = new ArrayList<ProductReport>();
		errors = new ArrayList<Error>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(Date generationTime) {
		this.generationTime = generationTime;
	}

	public boolean getOutOfOrder() {
		return outOfOrder;
	}

	public void setOutOfOrder(boolean outOfOrder) {
		this.outOfOrder = outOfOrder;
	}

	public State getCurrState() {
		return currState;
	}

	public void setCurrState(State currState) {
		this.currState = currState;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public ChangeMachineState getChangeState() {
		return changeState;
	}

	public void setChangeState(ChangeMachineState changeState) {
		this.changeState = changeState;
	}

	public boolean getChipCardError() {
		return chipCardError;
	}

	public void setChipCardError(boolean chipCardError) {
		this.chipCardError = chipCardError;
	}

	public boolean getContactlessCardError() {
		return contactlessCardError;
	}

	public void setContactlessCardError(boolean contactlessCardError) {
		this.contactlessCardError = contactlessCardError;
	}

	public BigDecimal getSumOfSales() {
		return sumOfSales;
	}

	public void setSumOfSales(BigDecimal sumOfSales) {
		this.sumOfSales = sumOfSales;
	}

	public List<ProductReport> getProducts() {
		return products;
	}

	public void setProducts(List<ProductReport> products) {
		this.products = products;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public VendingMachine getMachine() {
		return machine;
	}

	public void setMachine(VendingMachine machine) {
		this.machine = machine;
	}
	
}
