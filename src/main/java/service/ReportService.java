package service;

import java.util.ListIterator;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.Transaction;

import machines.DailyReport;
import machines.Error;
import machines.Product;
import machines.ProductReport;
import machines.VendingMachine;
import util.HibernateUtil;

@WebService
@Path("/")
public class ReportService {
	
	public ReportService() {

	}

	@POST
	@Path("/report")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addReport(DailyReport report) {
		// link errors with the report
		if (report.getErrors() != null) {
			ListIterator<Error> errorIterator = report.getErrors().listIterator();
			while(errorIterator.hasNext()) {
				Error error = errorIterator.next();
				error.setReport(report);
			}
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		// check if products exist in DB
		if (report.getProducts() != null) {
			ListIterator<ProductReport> prIterator = report.getProducts().listIterator();
			while (prIterator.hasNext()) {
				ProductReport pr = prIterator.next();
				if (session.find(Product.class, pr.getId().getProductId()) == null) {
					session.clear();
					session.close();
					return Response.notModified("Product doesn't exist").build();
				}
			}
		}

		// link machine with the report
		if (report.getMachine() == null) {
			session.clear();
			session.close();
			return Response.notModified("Machine not provided").build();
		}
		VendingMachine machine = session.bySimpleNaturalId(VendingMachine.class).load(report.getMachine().getSerialNb());
		if (machine == null) {
			session.clear();
			session.close();
			return Response.notModified("Machine doesn't exist").build();
		}
		machine.setLastReport(report);
		report.setMachine(machine);
		session.persist(machine);

		// persist the report to get its id assigned
		session.persist(report);
		transaction.commit();
		session.close();


		session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();
		
		// set link products with the report (using report's id)
		ListIterator<ProductReport> prIterator = report.getProducts().listIterator();
		while (prIterator.hasNext()) {
			ProductReport pr = prIterator.next();
			// using report's id
			pr.getId().setReportId(report.getId());
			session.save(pr);
		}
		transaction.commit();
		session.close();
		
		return Response.ok().build();
	}

}
