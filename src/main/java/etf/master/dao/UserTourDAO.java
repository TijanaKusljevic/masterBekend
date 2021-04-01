package etf.master.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import etf.master.entity.Role;
import etf.master.entity.Tour;
import etf.master.entity.UserTour;
import etf.master.entity.UserTourId;
import etf.master.entity.Users;


@Repository
public class UserTourDAO {
private EntityManager entityManager;
	
	@Autowired
	public UserTourDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	public void saveUserTour(int userId, int tourId, Users u, Tour t) {
		Session currentSession = entityManager.unwrap(Session.class);
		UserTourId uti = new UserTourId(userId, tourId);
		UserTour ut = new UserTour(uti, u, t);
		currentSession.saveOrUpdate(ut);
	}
	
	public UserTour getUserTour(int userId, int tourId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<UserTour> theQuery = currentSession.createQuery("select ut from UserTour ut where ut.id.userId = :userId and ut.id.tourId = :tourId", UserTour.class);
		theQuery.setParameter("userId", userId);
		theQuery.setParameter("tourId", tourId);
		
		List<UserTour> tours = theQuery.getResultList();
		return tours.get(0);
	}
	
	public void deleteUserTour(int userId, int tourId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		UserTour ut = getUserTour(userId, tourId);
		
		currentSession.delete(ut);
	}
	
}
