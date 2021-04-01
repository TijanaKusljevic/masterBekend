package etf.master.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import etf.master.dao.CommentDAO;
import etf.master.dao.TourDAO;
import etf.master.dao.UserDAO;
import etf.master.dao.UserTourDAO;
import etf.master.entity.Tour;
import etf.master.entity.TourComment;
import etf.master.entity.Users;
import etf.master.front.Comment;

@Service
public class TourService {
	
	private TourDAO tourDAO;
	private UserTourDAO userTourDAO;
	private UserDAO userDAO;
	private CommentDAO commentDAO;
	
	@Autowired
	public TourService(TourDAO tourDAO, UserTourDAO userTourDAO, UserDAO userDAO, CommentDAO commentDAO) {
		this.tourDAO = tourDAO;
		this.userTourDAO = userTourDAO;
		this.userDAO = userDAO;
		this.commentDAO = commentDAO;
	}
	
	@Transactional
    public void saveTour(Tour tour) {
    	tourDAO.saveTour(tour);
    }
	
	@Transactional
    public List<Tour> findAll(String username, String role) {
		Users user = userDAO.findUser(username);
    	List<Tour> tours = tourDAO.findAll(username, user.getUserId(), role);
    	for(Tour t: tours) {
    		Users u = tourDAO.getGuide(t);
    		if(u == null) {
    			t.setGuideName(null);
    		} else {
    			t.setGuideName(u.getFirstName() + " " + u.getLastName());
    		}
    	}
    	return tours;
    }
	
	@Transactional
    public List<Tour> findOneDayTrips() {
    	return tourDAO.findOneDayTrips();
    }

	@Transactional
	public Tour getTour(int id, String username) {
		Tour t = tourDAO.getTour(id, username);
		Users u = tourDAO.getGuide(t);
		if(u != null)
			t.setGuideName(u.getFirstName() + " " + u.getLastName());
		return t;
	}
	
	@Transactional
	public void saveImage(String image, int tourId) {
		Tour t = tourDAO.getTour(tourId, null);
		t.setImage(image);
		tourDAO.saveTour(t);
	}
	
	@Transactional
	public void saveUserTour(int userId, int tourId) {
		Tour t = tourDAO.getTour(tourId, null);
		Users u = userDAO.getUser(userId);
		userTourDAO.saveUserTour(userId, tourId, u, t);
	}
	
	@Transactional
    public void saveComment(Comment c) {
    	Users a = userDAO.getUser(c.getAuthorId());
    	Tour t = tourDAO.getTour(c.getTargetId(), null);
    	commentDAO.saveCommentTour(a, t, c);
    }
	
	@Transactional
	public List<Comment> getTourComments(int id) {
		List<TourComment> tourComments = commentDAO.getCommentsTour(id);
		
		List<Comment> comments = new ArrayList<Comment>();
		
		for (TourComment tc: tourComments) {
			System.out.println("u foru");
			Users author = tc.getAuthor();
			if(author != null)
				comments.add(new Comment(tc.getContent(), author.getFirstName() + " " + author.getLastName(), tc.getTourCommentId()));
			else
				comments.add(new Comment(tc.getContent(), "Bivši korisnik", tc.getTourCommentId()));
		}
		return comments;
	}
	
	 @Transactional
	 public void deleteUserTour(int userId, int tourId) {
		 userTourDAO.deleteUserTour(userId, tourId);
	 }
	 
	 @Transactional
	 public void deleteTourComment(int id) {
		 commentDAO.deleteTourComment(id);
	 }
	 
	 @Transactional
	 public void deleteTour(int id) {
		 List<TourComment> list = commentDAO.getCommentsTour(id);
		 
		 for(TourComment tc : list) {
			 commentDAO.deleteTourComment(tc);
		 }
		 
		 tourDAO.deleteTour(id);
	 }
	 
}
