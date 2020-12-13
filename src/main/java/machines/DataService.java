package machines;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.jws.WebService;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.graph.GraphParser;
import org.hibernate.graph.RootGraph;

import util.HibernateUtil;

@WebService
@Path("/")
public class DataService {
	
	public DataService() {
		System.out.println("DATA SERVICE");
		
	}
	
	@GET
	@Path("machines/{criteria}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getMachines(@PathParam("criteria") String criteria) {
		criteria = criteria.toLowerCase();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<QueryResult> query = builder.createQuery(QueryResult.class);
		Root<VendingMachine> machineTable = query.from(VendingMachine.class);
		Join<VendingMachine,DailyReport> reportJoin = machineTable.join("lastReport", JoinType.INNER);
		Predicate predicate = builder.conjunction();
		switch(criteria) {
			case "hors-service":
				query.multiselect(machineTable, reportJoin);
				predicate = builder.equal(reportJoin.get("outOfOrder"), 1);
				break;
			case "reapprovisionner":
				Join<DailyReport,ProductReport> productsJoin = reportJoin.join("products", JoinType.INNER);				
				Join<ProductReport,Product> productItemsJoin = productsJoin.join("product", JoinType.INNER);
				query.multiselect(machineTable, productsJoin, productItemsJoin);
				predicate = builder.lessThan(productsJoin.get("quantity"), 10);
				break;
			case "surveiller":
				Join<DailyReport,Error> errorJoin = reportJoin.join("errors",JoinType.LEFT);
				query.multiselect(machineTable, reportJoin, errorJoin);
				List<Predicate> pred = new ArrayList<>();
				pred.add(builder.notEqual(reportJoin.get("currState"), State.OK));
				pred.add(builder.notEqual(reportJoin.get("changeState"), ChangeMachineState.NORMAL));
				pred.add(builder.equal(reportJoin.get("chipCardError"), 1));
				pred.add(builder.equal(reportJoin.get("contactlessCardError"), 1));
				pred.add(builder.isNotEmpty(reportJoin.get("errors")));
				predicate = builder.or(pred.toArray(Predicate[]::new));
				break;
			case "ventes":
				query.multiselect(machineTable, reportJoin).orderBy(builder.desc(reportJoin.get("sumOfSales")));
				break;
		}
		query.where(predicate);
		List<QueryResult> list = session.createQuery(query).getResultList();
		transaction.commit();
		session.close();

		// wrap the list 
		QueryResults results = new QueryResults(list);
		return Response.ok(results).build();
	}

}
