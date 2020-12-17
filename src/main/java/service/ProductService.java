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
		product.unlink();
		return Response.ok(product).build();
	}
	
	
	@POST
	@Path("/product/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addProduct(Product product) {
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
    public Response updateProduct(@PathParam("id") long id, Product product) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Product oldProduct = session.get(Product.class, id);
        transaction.commit();
        if (oldProduct == null) {
            return Response.notModified().build();
        }
        oldProduct.merge(product);

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            session.update(oldProduct);
            transaction.commit();
        } catch(Exception e) {
        	transaction.rollback();
        	return Response.notModified().build();
        }
        oldProduct.unlink();
        return Response.ok(oldProduct).build();
    }

    @DELETE
    @Path("/product/{id:\\d+}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteProduct(@PathParam("id") long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
        	Product product = session.byId(Product.class).load(Long.valueOf(id));
            session.delete(product);
            transaction.commit();
            product.unlink();
            return Response.ok(product).build();
        } catch (Exception e) {
            transaction.rollback();
            return Response.notModified().build();
        }
    }

}
