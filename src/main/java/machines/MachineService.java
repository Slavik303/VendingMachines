package machines;

import java.net.URI;

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
@Path("/machineservice/")
public class MachineService {

	public MachineService() { 
		
	}
	
	@GET
	@Path("/machine/{id:\\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMachine(@PathParam("id") long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		VendingMachine machine = session.byId(VendingMachine.class).load(Long.valueOf(id));
		transaction.commit();
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
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void updateMachine(long id, VendingMachine machine) {
		
	}

	@DELETE
	@Path("/machine/{id:\\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deleteMachine(long id) {
		
	}

}
