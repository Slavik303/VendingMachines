package service;

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

import machines.VendingMachine;
import util.HibernateUtil;

@WebService
@Path("/")
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
		machine.unlink();
		return Response.ok(machine).build();
	}
	
	@POST
	@Path("/machine/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addMachine(VendingMachine machine) {
		if (machine.getSerialNb() == null) {
			return Response.notModified().build();
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
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
        VendingMachine oldMachine = session.get(VendingMachine.class, id);
        transaction.commit();
        if (oldMachine == null) {
            return Response.notModified().build();
        }
        oldMachine.merge(machine);

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
		try {
			session.update(oldMachine);
			transaction.commit();
		} catch(Exception e) {
			transaction.rollback();
            return Response.notModified().build();
		}
		oldMachine.unlink();
		return Response.ok(oldMachine).build();
    }
	

    @DELETE
    @Path("/machine/{id:\\d+}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteMachine(@PathParam("id") long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
        	VendingMachine machine = session.byId(VendingMachine.class).load(Long.valueOf(id));
            session.delete(machine);
            transaction.commit();
			machine.unlink();
			return Response.ok(machine).build();
        } catch (Exception e) {
            transaction.rollback();
            return Response.notModified().build();
        }
    }

}
