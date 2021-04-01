package etf.master.dao;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import etf.master.entity.Role;
import etf.master.entity.Tour;
import etf.master.entity.Users;
import etf.master.front.Password;

@Repository
public class UserDAO {
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public UserDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	public Users findUser(String username) {
		
		System.out.println("************************************"+username);
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Users> theQuery =currentSession.createQuery("select u from Users as u where u.username = :pusername", Users.class); // ne radi sa :username
		
		theQuery.setParameter("pusername", username);
		
		List<Users> users = theQuery.getResultList();
		
		System.out.println("medjukorak");
		
		if(users.size() == 0) 
			return null;
		
		Users u = users.get(0);
		
		return u;
	}
	
	public void saveUser(Users user) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(user);
	}


	public List<Users> getGuides() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Users> theQuery =currentSession.createQuery("select u from Users as u where u.type = :guide", Users.class);
		
		theQuery.setParameter("guide", "Vodic");
		
		List<Users> users = theQuery.getResultList();
		
		return users;
	}
	
	public List<Users> getMountaineers() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Users> theQuery =currentSession.createQuery("select u from Users as u where u.type = :guide", Users.class);
		
		theQuery.setParameter("guide", "Planinar");
		
		List<Users> users = theQuery.getResultList();
		
		return users;
	}


	public Users getUser(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Users> theQuery = currentSession.createQuery("select u from Users as u where u.id = :id", Users.class);
		
		theQuery.setParameter("id", id);
		
		List<Users> users = theQuery.getResultList();
		
		return users.get(0);
	}
	
	public List<Tour> getUserPreviousTours(String username){
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery = currentSession.createQuery("select t "
								  + "from Users u, UserTour ut, Tour t "
								  + "where u.username = :username "
								  + "and ut.user = u.id "
								  + "and t.id = ut.tour "
								  + "and t.startDate < sysdate()", Tour.class);
		
		theQuery.setParameter("username", username);
		
		List<Tour> tours = theQuery.getResultList();
		
		for(Tour t: tours) {
			t.setSignedIn(true);
			t.setFinished(true);
		}
		Collections.sort(tours);
		Collections.reverse(tours);
		
		return tours;
	}
	
	public List<Tour> getGuidePreviousTours(int userId){
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery = currentSession.createQuery("select t from Tour t where t.guideId = :userId and t.startDate < sysdate()", Tour.class);

		theQuery.setParameter("userId", userId);
		
		List<Tour> tours = theQuery.getResultList();
		
		for(Tour t: tours) {
			t.setOwner(true);
			t.setFinished(true);
		}
		Collections.sort(tours);
		Collections.reverse(tours);
		
		return tours;
	}
	
	public List<Tour> getUserFutureTours(String username){
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery = currentSession.createQuery("select t "
								  + "from Users u, UserTour ut, Tour t "
								  + "where u.username = :username "
								  + "and ut.user = u.id "
								  + "and t.id = ut.tour "
								  + "and t.startDate >= sysdate()", Tour.class);
		
		theQuery.setParameter("username", username);
		
		List<Tour> tours = theQuery.getResultList();
		
		for(Tour t: tours) {
			t.setSignedIn(true);
			t.setFinished(false);
		}
		Collections.sort(tours);
		Collections.reverse(tours);
		
		return tours;
	}
	
	public List<Tour> getGuideFutureTours(int userId){
		System.out.println("U buducun");
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Tour> theQuery = currentSession.createQuery("select t from Tour t where t.guideId = :userId and t.startDate >= sysdate()", Tour.class);

		theQuery.setParameter("userId", userId);
		
		List<Tour> tours = theQuery.getResultList();
		
		for(Tour t: tours) {
			t.setOwner(true);
			System.out.println("U buducum for");
		}
		Collections.sort(tours);
		Collections.reverse(tours);
		
		return tours;
	}
	
	public String selectRole(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Role> theQuery = currentSession.createQuery("select r from Role as r where r.id = :id", Role.class);
		
		theQuery.setParameter("id", id);
		
		List<Role> roles = theQuery.getResultList();
		
		return roles.get(0).getName();
	}
	
	public void changePassword(String newPassword, Users user) {
		Session currentSession = entityManager.unwrap(Session.class);
		user.setPassword(newPassword);
		currentSession.saveOrUpdate(user);
	}


	public void deleteUser(int id) {
		Session currentSession = entityManager.unwrap(Session.class);	
		Users u = getUser(id);
		System.out.println("delete user");
		currentSession.delete(u);
	}


	public List<Tour> getGuideTours(int id) {
		//postavi turama na null
		List<Tour> allTours = Stream.concat(getGuidePreviousTours(id).stream(), getGuideFutureTours(id).stream()).collect(Collectors.toList());
		
		return allTours;
	}


	public void deleteGuide(int id) {
		Session currentSession = entityManager.unwrap(Session.class);	
		Users u = getUser(id);
		System.out.println("delete guide");
		currentSession.delete(u);
	}
}
