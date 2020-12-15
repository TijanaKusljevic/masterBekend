package etf.master.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import etf.master.entity.Users;

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
		
		if(users.size() == 0) 
			return null;
		
		System.out.println("************************************"+users.size());
		
		return users.get(0);
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
}
