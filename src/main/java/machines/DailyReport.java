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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
	
	@Column(name = "out_of_order")
	private boolean outOfOrder;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "current_state")
	private State currState;

	private float temperature;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "change_machine_state")
	private ChangeMachineState changeState;
	
	@Column(name = "chip_card_error")
	private boolean chipCardError;
	
	@Column(name = "contactless_card_error")
	private boolean constactlessCardError;
	
	@Column(name = "sum_of_sales")
	private BigDecimal sumOfSales;
	
	@OneToMany(
			mappedBy = "report",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<ProductReport> productsLeft;
	
	@OneToMany(
			mappedBy = "report",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Error> errors;
	
	@OneToOne(mappedBy = "lastReport")
	private VendingMachine machine;
	
	public DailyReport() {
		productsLeft = new ArrayList<ProductReport>();
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

	public boolean isOutOfOrder() {
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

	public boolean isChipCardError() {
		return chipCardError;
	}

	public void setChipCardError(boolean chipCardError) {
		this.chipCardError = chipCardError;
	}

	public boolean isConstactlessCardError() {
		return constactlessCardError;
	}

	public void setConstactlessCardError(boolean constactlessCardError) {
		this.constactlessCardError = constactlessCardError;
	}

	public BigDecimal getSumOfSales() {
		return sumOfSales;
	}

	public void setSumOfSales(BigDecimal sumOfSales) {
		this.sumOfSales = sumOfSales;
	}
	
	public List<ProductReport> getProductsLeft() {
		return productsLeft;
	}

	public void setProductsLeft(List<ProductReport> productsLeft) {
		this.productsLeft = productsLeft;
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
