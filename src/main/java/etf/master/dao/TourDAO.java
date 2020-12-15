package etf.master.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import etf.master.entity.Employee;
import etf.master.entity.Tour;
import etf.master.entity.Users;

@Repository
public class TourDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public TourDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	public void saveTour(Tour tour) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(tour);
	}

	public List<Tour> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery =currentSession.createQuery("from Tour", Tour.class);
		
		List<Tour> tours = theQuery.getResultList();
		
		return tours;
	}
}
