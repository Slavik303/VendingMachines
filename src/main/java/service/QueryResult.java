package service;

import javax.xml.bind.annotation.XmlRootElement;

import machines.DailyReport;
import machines.Error;
import machines.Product;
import machines.ProductReport;
import machines.VendingMachine;

@XmlRootElement(name = "result")
public class QueryResult {
	
	private VendingMachine machine;
	private DailyReport report;
	private ProductReport productReport;
	private Product product;
	private Error error;

	public QueryResult() {
	
	}

	public QueryResult(VendingMachine machine) {
		this.machine = machine;
	}

	public QueryResult(VendingMachine machine, DailyReport report) {
		this.report = report;
		this.machine = machine;
	}

	public QueryResult(VendingMachine machine, DailyReport report, ProductReport productReport) {
		this.report = report;
		this.machine = machine;
		this.productReport = productReport;
	}

	public QueryResult(VendingMachine machine, DailyReport report, Error error) {
		this.report = report;
		this.machine = machine;
		this.error = error;
	}

	public QueryResult(VendingMachine machine, ProductReport productReport, Product product) {
		this.machine = machine;
		this.productReport = productReport;
		this.product = product;
	}

	public QueryResult(VendingMachine machine, DailyReport report, ProductReport productReport, Product product) {
		this.report = report;
		this.machine = machine;
		this.productReport = productReport;
		this.product = product;
	}

	public QueryResult(VendingMachine machine, DailyReport report, ProductReport productReport, Error error) {
		this.report = report;
		this.machine = machine;
		this.productReport = productReport;
		this.error = error;
	}

	public VendingMachine getMachine() {
		return machine;
	}

	public void setMachine(VendingMachine machine) {
		this.machine = machine;
	}

	public DailyReport getReport() {
		return report;
	}

	public void setReport(DailyReport report) {
		this.report = report;
	}

	public ProductReport getProductReport() {
		return productReport;
	}

	public void setProductReport(ProductReport productReport) {
		this.productReport = productReport;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
}
