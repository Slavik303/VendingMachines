package service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

import machines.ChangeMachineState;
import machines.DailyReport;
import machines.Error;
import machines.Product;
import machines.ProductReport;
import machines.ProductType;
import machines.State;
import machines.VendingMachine;
import util.HibernateUtil;

@WebService
@Path("/")
public class DataService {
	
	public DataService() {

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
		List<Predicate> pred = new ArrayList<>();
		switch(criteria) {
			case "hors-service":
				Join<DailyReport,Error> errorJoin = reportJoin.join("errors", JoinType.LEFT);
				query.multiselect(machineTable, reportJoin, errorJoin);
				predicate = builder.equal(reportJoin.get("outOfOrder"), 1);
				break;
			case "reapprovisionner":
				Join<DailyReport,ProductReport> productsJoin = reportJoin.join("products", JoinType.INNER);				
				Join<ProductReport,Product> productItemsJoin = productsJoin.join("product", JoinType.INNER);
				query.multiselect(machineTable, reportJoin, productsJoin, productItemsJoin);
				pred.add(builder.lessThan(productsJoin.get("quantity"), 10));
				Predicate hotDrinksInsufficient = builder.and(
						builder.lessThan(reportJoin.get("temperature"), 5),
						builder.equal(productItemsJoin.get("type"), ProductType.HOT_DRINKS),
						builder.lessThan(productsJoin.get("quantity"), 30));
				pred.add(hotDrinksInsufficient);
				Predicate coldDrinksInsufficient = builder.and(
						builder.greaterThan(reportJoin.get("temperature"), 25),
						builder.equal(productItemsJoin.get("type"), ProductType.COLD_DRINKS),
						builder.lessThan(productsJoin.get("quantity"), 30));
				pred.add(coldDrinksInsufficient);
				predicate = builder.or(pred.toArray(Predicate[]::new));
				break;
			case "surveiller":
				query.multiselect(machineTable, reportJoin);
				pred.add(builder.notEqual(reportJoin.get("currState"), State.OK));
				pred.add(builder.notEqual(reportJoin.get("changeState"), ChangeMachineState.NORMAL));
				pred.add(builder.equal(reportJoin.get("chipCardError"), 1));
				pred.add(builder.equal(reportJoin.get("contactlessCardError"), 1));
				predicate = builder.and(
						builder.equal(reportJoin.get("outOfOrder"), 0),
						builder.or(pred.toArray(Predicate[]::new)));
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
