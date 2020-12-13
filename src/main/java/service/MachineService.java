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
		machine.setLastReport(null);
		return Response.ok(machine).build();
	}
	
	
	@POST
	@Path("/machine/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addMachine(VendingMachine machine) {
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
        if (oldMachine != null)
        {
            machine = merge(oldMachine, machine);
            session.save(machine);
            transaction.commit();
            session.close();
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
	
	private VendingMachine merge(VendingMachine oldMachine, VendingMachine newMachine) {
		if (newMachine.getSerialNb() != null)
			oldMachine.setSerialNb(newMachine.getSerialNb());
		if (newMachine.getType() != null)
			oldMachine.setType(newMachine.getType());
		if (newMachine.getInstallationAddress() != null)
			oldMachine.setInstallationAddress(newMachine.getInstallationAddress());
		if (newMachine.getLocation() != null)
			oldMachine.setLocation(newMachine.getLocation());
		if (newMachine.getLongitude() != 0.0)
			oldMachine.setLongitude(newMachine.getLongitude());
		if (newMachine.getLatitude() != 0.0)
			oldMachine.setLatitude(newMachine.getLatitude());
		if (newMachine.getLastIntervention() != null)
			oldMachine.setLastIntervention(newMachine.getLastIntervention());
		if (newMachine.getNotes() != null)
			oldMachine.setNotes(newMachine.getNotes());
		return oldMachine;
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

}
