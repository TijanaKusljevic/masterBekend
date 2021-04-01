package etf.master.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import etf.master.entity.Employee;
import etf.master.entity.Tour;
import etf.master.entity.UserTour;
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

	public List<Tour> findAll(String username, int userId, String role) {
		
		System.out.println("find all " + username + userId);
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery =currentSession.createQuery("from Tour", Tour.class);
		
		List<Tour> tours = theQuery.getResultList();
		
		for(Tour tour: tours) {
			
			if(tour.getStartDate().before(new Date())) {
				tour.setFinished(true);
			}
			
			//if(role.equals("USER")) {
				Query<UserTour> query = currentSession.createQuery("select ut "
						  + "from Users u, UserTour ut, Tour t "
						  + "where u.username = :username "
						  + "and ut.user = u.id "
						  + "and t.tourId = :tId "
						  + "and ut.tour = t.tourId", UserTour.class);
				
				query.setParameter("username", username);
				query.setParameter("tId", tour.getTourId());
				
				List<UserTour> t = query.getResultList();
				
				if(t != null && t.size() > 0) { 
					tour.setSignedIn(true);
				} else {
					if(role.equals("GUIDE") && tour.getGuideId() != userId) {
						System.out.println("usao u novi deo");
						//proveri da li je zazet tokom trajanja te ture (datum pocetka pre kraja ove ili datum kraja posle pocetka ove)
						// select * from tour t where t.guideId = userId and t.startDate <= ova.startDate and t.endDate <= ova.endDate
						/*Query<Tour> query2 = currentSession.createQuery("select t "
								  + "from Tour t "
								  + "where t.guideId = :guideId "
								  + "and t.startDate >= :startDate "
								  + "and t.endDate <= :endDate ", Tour.class);*/
						
						Query<Tour> query2 = currentSession.createQuery("select t "
								  + "from Tour t "
								  + "where t.guideId = :guideId "
								  + "and (t.startDate between :startDate and  :endDate "
								  + "or t.endDate between :startDate and :endDate) ", Tour.class);
						
						//"select t from Tour t where t.guideId = :guideId and 
						//t.tourId in (select t1.tourId from Tour t1 where t1.startDate between )"
						
						query2.setParameter("guideId", userId);
						query2.setParameter("startDate", tour.getStartDate());
						query2.setParameter("endDate", tour.getEndDate());
						
						List<Tour> t2 = query2.getResultList();
						
						if(t2 != null && t2.size() > 0) { 
							tour.setNotAvailable(true);
							System.out.println("stavio not available");
						}
						
						/*Query<Tour> query3 = currentSession.createQuery("select t "
								  + "from Tour t "
								  + "where t.guideId = :guideId "
								  + "and t.startDate >= :startDate "
								  + "and t.endDate >= :endDate ", Tour.class);*/
					}
				}
			//} else {
			if(role.equals("GUIDE")) { // ovo valjda moze samo da proverim id
				Query<Tour> query1 = currentSession.createQuery("select t "
						  + "from Users u, Tour t "
						  + "where u.username = :username "
						  + "and t.tourId = :tId "
						  + "and t.guideId = u.userId", Tour.class);
				
				query1.setParameter("username", username);
				query1.setParameter("tId", tour.getTourId());
				
				List<Tour> t1 = query1.getResultList();
				
				if(t1 != null && t1.size() > 0) { 
					tour.setOwner(true);
				}
			}
		}
		
		Collections.sort(tours);
		Collections.reverse(tours);
		return tours;
	}
	
	public Users getGuide(Tour tour) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Users> theQuery = currentSession.createQuery("select u "
				  											+ "from Users u, Tour t "
				  											+ "where t.tourId = :tId "
				  											+ "and u.id = t.guideId ", Users.class);
		
		theQuery.setParameter("tId", tour.getTourId());
		List<Users> users = theQuery.getResultList();
		
		if(users == null || users.size() == 0) {
			return null;
		}
		return users.get(0);
	}
	
	public List<Tour> findOneDayTrips() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery =currentSession.createQuery("select t from Tour t where t.nights = 0", Tour.class);
		
		List<Tour> tours = theQuery.getResultList();
		
		Collections.sort(tours);
		Collections.reverse(tours);
		
		return tours;
	}

	public Tour getTour(int id, String username) {
		System.out.println("id je " +id);
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery =currentSession.createQuery("select t from Tour as t where t.id = :id", Tour.class);
		
		theQuery.setParameter("id", id);
		
		List<Tour> tours = theQuery.getResultList();
		
		//System.out.print(tours.get(0).getuserTours().isEmpty());
		
		Tour tour = tours.get(0);
		
		if(username != null) {
		
			Query<UserTour> query = currentSession.createQuery("select ut "
					  + "from Users u, UserTour ut, Tour t "
					  + "where u.username = :username "
					  + "and ut.user = u.id "
					  + "and t.tourId = :tId "
					  + "and ut.tour = t.tourId", UserTour.class);
			
			query.setParameter("username", username);
			query.setParameter("tId", tour.getTourId());
			
			List<UserTour> t = query.getResultList();
			
			if(tour.getStartDate().before(new Date())) {
				tour.setFinished(true);
			}
			
			if(t != null && t.size() > 0) { 
				tour.setSignedIn(true);
			}
		}
		return tour;
	}

	public void deleteTour(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Tour t = getTour(id,null);
		
		currentSession.delete(t);
		
	}
}
