package service;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.annotation.XmlRootElement;

import machines.DailyReport;
import machines.Error;
import machines.Product;
import machines.ProductReport;

@XmlRootElement(name = "results")
public class QueryResults {
	
	private List<QueryResult> results;
	
	public QueryResults() {
		results = new LinkedList<>();
	}
	
	public QueryResults(List<QueryResult> results) {
		this.results = results;
		unlinkResults();
	}
	
	private void unlinkResults() {
		ListIterator<QueryResult> iterator = results.listIterator();
		while (iterator.hasNext()) {
			QueryResult result = iterator.next();
			result.getMachine().setLastReport(null);
			DailyReport report = result.getReport();
			if (report != null) {
				report.setProducts(null);
				report.setErrors(null);
				report.setMachine(null);
			}
			ProductReport pr = result.getProductReport();
			if (pr != null) {
				pr.setProduct(null);
				pr.setReport(null);
			}
			Product p = result.getProduct();
			if (p != null) {
				p.setReports(null);
			}
			Error err = result.getError();
			if (err != null) {
				err.setReport(null);
			}
		}
	}

	public List<QueryResult> getResults() {
		return results;
	}

	public void setResults(List<QueryResult> results) {
		this.results = results;
		unlinkResults();
	}

}
