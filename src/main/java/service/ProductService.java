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

import machines.Product;
import util.HibernateUtil;

@WebService
@Path("/")
public class ProductService {
	
	public ProductService() {

	}
	
	@GET
	@Path("/product/{id:\\d+}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProduct(@PathParam("id") long id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Product product = session.byId(Product.class).load(Long.valueOf(id));
		transaction.commit();
		product.setReports(null);
		return Response.ok(product).build();
	}
	
	
	@POST
	@Path("/product/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addMachine(Product product) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(product);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return Response.notModified().build();
		}
		URI uri = UriBuilder.fromPath("productservice/product/{id}").build(product.getId());
		return Response.created(uri).build();
	}
	
	@PUT
    @Path("/product/{id:\\d+}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateMachine(@PathParam("id") long id, Product product) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Product oldProduct = session.get(Product.class, id);
        if (oldProduct != null)
        {
            product = merge(oldProduct, product);
            session.save(product);
            transaction.commit();
            session.close();
            product.setReports(null);
            return Response.ok(product).build();
        }
        else
        {
            session.clear();
            session.close();
            return Response.notModified().build();
        }
    }
	
	private Product merge(Product oldProduct, Product newProduct) {
		if (newProduct.getName() != null)
			oldProduct.setName(newProduct.getName());
		if (newProduct.getType() != null)
			oldProduct.setType(newProduct.getType());
		return oldProduct;
	}

    @DELETE
    @Path("/product/{id:\\d+}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteMachine(@PathParam("id") long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.byId(Product.class).load(Long.valueOf(id));
        try {
            session.delete(product);
            transaction.commit();
            product.setReports(null);
            return Response.ok(product).build();
        } catch (Exception e) {
            transaction.rollback();
            return Response.notModified().build();
        }
    }

}
