package etf.master.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import etf.master.entity.GuideComment;
import etf.master.entity.Role;
import etf.master.entity.Tour;
import etf.master.entity.TourComment;
import etf.master.entity.UserTour;
import etf.master.entity.Users;
import etf.master.front.Comment;

@Repository
public class CommentDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public CommentDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	public void saveCommentGuide(Users author, Users guide, Comment c) {
		Session currentSession = entityManager.unwrap(Session.class);
		GuideComment gc = new GuideComment(guide, author, c.getContent());
		currentSession.saveOrUpdate(gc);
	}
	
	public void saveCommentTour(Users author, Tour tour, Comment c) {
		Session currentSession = entityManager.unwrap(Session.class);
		TourComment tc = new TourComment(tour, author, c.getContent());
		currentSession.saveOrUpdate(tc);
	}
	
	public List<TourComment> getCommentsTour(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<TourComment> theQuery =currentSession.createQuery("select tc from TourComment as tc where tc.tour.tourId = :id", TourComment.class);
		theQuery.setParameter("id", id);
		List<TourComment> tourComments = theQuery.getResultList();
		return tourComments;
	}
	
	public List<GuideComment> getCommentsGuide(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<GuideComment> theQuery =currentSession.createQuery("select gc from GuideComment as gc where gc.guide.userId = :id", GuideComment.class);
		theQuery.setParameter("id", id);
		List<GuideComment> guideComments = theQuery.getResultList();
		return guideComments;
	}
	
	public void deleteTourComment(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		TourComment c = getTourComment(id);
		
		currentSession.delete(c);
	}
	
	public void deleteTourComment(TourComment tc) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.delete(tc);
	}
	
	public TourComment getTourComment(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<TourComment> theQuery = currentSession.createQuery("select c from TourComment as c where c.tourCommentId = :id", TourComment.class);
		theQuery.setParameter("id", id);
		List<TourComment> comments = theQuery.getResultList();
		
		return comments.get(0);
	}
	
	public void deleteGuideComment(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		GuideComment c = getGuideComment(id);
		
		currentSession.delete(c);
	}
	
	public GuideComment getGuideComment(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<GuideComment> theQuery = currentSession.createQuery("select c from GuideComment as c where c.guideCommentId = :id", GuideComment.class);
		theQuery.setParameter("id", id);
		List<GuideComment> comments = theQuery.getResultList();
		
		return comments.get(0);
	}

	public List<TourComment> deleteUserComment(int id) {
		System.out.println("Usao u delete comment " + id);
		Session currentSession = entityManager.unwrap(Session.class);
		
		/*Query<GuideComment> theQuery = currentSession.createQuery("select c from GuideComment as c where c.author.userId = :id", GuideComment.class);
		theQuery.setParameter("id", id);
		
		List<GuideComment> comments = theQuery.getResultList();
		
		for(GuideComment gc: comments) {
			gc.setAuthor(null);
			currentSession.saveOrUpdate(gc);
		}*/
		
		Query<TourComment> theQuery1 = currentSession.createQuery("select c from TourComment as c where c.author.userId = :id", TourComment.class);
		theQuery1.setParameter("id", id);
		
		List<TourComment> comments1 = theQuery1.getResultList();
		
		return comments1;
		
		/*for(TourComment tc: comments1) {
			System.out.println("for of komentara za turu");
			tc.setContent(null);
			System.out.println(tc.getContent());
			currentSession.saveOrUpdate(tc);
			System.out.println("kao gotovo " + tc.getAuthor());
			System.out.println(getCommentsTour(tc.getTour().getTourId()).get(1).getAuthor());
			System.out.println(getCommentsTour(tc.getTour().getTourId()).get(0).getAuthor());
		}*/
	}

	public void updateTourComment(TourComment c) {
		System.out.println("usao u novu metodu");
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(c);
		
	}
}
