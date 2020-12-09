package machines;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class MachineManager {

	public MachineManager() { 
		
	}
	
	public void createAndAddMachine(String serialNb) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		VendingMachine m = new VendingMachine();
		m.setSerailNb(serialNb);
		Transaction tx = session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		session.close();
	}

}
