package machines;

import java.net.URI;
import java.util.ListIterator;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

@WebService
@Path("/")
public class MachineService {

	public MachineService() { 
		System.out.println("MACHINE SERVICE");

	}
	
	@GET
	@Path("/machine/{id:\\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMachine(@PathParam("id") long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		VendingMachine machine = session.byId(VendingMachine.class).load(Long.valueOf(id));
		transaction.commit();
		machine.setLastReport(null);
		return Response.ok(machine).build();
	}
	
	
	@POST
	@Path("/machine/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addMachine(VendingMachine machine) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		System.out.println(machine.getSerialNb());
		try {
			session.save(machine);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return Response.notModified().build();
		}
		URI uri = UriBuilder.fromPath("machineservice/machine/{id}").build(machine.getId());
		return Response.created(uri).build();
	}
	
	@PUT
    @Path("/machine/{id:\\d+}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateMachine(@PathParam("id") long id, VendingMachine machine) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        if (session.get(VendingMachine.class, id) != null)
        {
            machine.setId(id);
            session.merge(machine);
            transaction.commit();
            machine.setLastReport(null);
            return Response.ok(machine).build();
        }
        else
        {
            session.clear();
            session.close();
            return Response.notModified().build();
        }
    }

    @DELETE
    @Path("/machine/{id:\\d+}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteMachine(@PathParam("id") long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        VendingMachine machine = session.byId(VendingMachine.class).load(Long.valueOf(id));
        try {
            session.delete(machine);
            transaction.commit();
            machine.setLastReport(null);
            return Response.ok(machine).build();
        } catch (Exception e) {
            transaction.rollback();
            return Response.notModified().build();
        }
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
		
		// TODO move to tests
		/*
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();
		ProductReport pr = session.get(ProductReport.class, new ProductReportKey(Long.valueOf(1),Long.valueOf(1)));
		System.out.println(pr.getProduct().getName());
		transaction.commit();
		session.close();
		*/
	}

}
