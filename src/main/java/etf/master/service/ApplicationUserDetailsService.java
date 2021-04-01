package etf.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import etf.master.dao.CommentDAO;
import etf.master.dao.TourDAO;
import etf.master.dao.UserDAO;
import etf.master.dao.UserTourDAO;
import etf.master.entity.GuideComment;
import etf.master.entity.Tour;
import etf.master.entity.TourComment;
import etf.master.entity.Users;
import etf.master.front.Comment;
import etf.master.front.Guide;
import etf.master.front.Mountaineer;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    
	private UserDAO userDAO;
	private CommentDAO commentDAO;
	private UserTourDAO userTourDAO;
	private TourDAO tourDAO;
	
	@Autowired
	public ApplicationUserDetailsService(UserDAO userDAO, CommentDAO commentDAO, UserTourDAO userTourDAO, TourDAO tourDAO) {
		this.userDAO = userDAO;
		this.commentDAO = commentDAO;
		this.userTourDAO = userTourDAO;
		this.tourDAO = tourDAO;
	}

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        Users applicationUser = userDAO.findUser(username);
        
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        //napraviti listu role i prosledidti umesto prezne
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        
        String r = userDAO.selectRole(applicationUser.getRoleId());
        
        System.out.println(r + "Postavljam rolu");
        
        applicationUser.setRole(r);
        
        authorities.add(new SimpleGrantedAuthority(r));
        
        User u =  new User(applicationUser.getUsername(), applicationUser.getPassword(), authorities);
        
        return u;
    }
    
    @Transactional
    public Users getUserByUsername(String username) {
    	return userDAO.findUser(username);
    }
    
    @Transactional
    public String getRole(String username) {
    	Users applicationUser = userDAO.findUser(username);
    	return userDAO.selectRole(applicationUser.getRoleId());
    }
    
    @Transactional
    public void saveUser(Users user) {
    	userDAO.saveUser(user);
    }
    
    @Transactional
	public void saveImage(String originalFilename, int userId) {
		Users u = userDAO.getUser(userId);
		u.setImage(originalFilename);
		userDAO.saveUser(u);
	}

    @Transactional
	public List<Guide> getGuides() {
		List<Users> users = userDAO.getGuides();
		List<Guide> guides = new ArrayList<Guide>();
		
		for (Users u: users) {
			Guide g = new Guide(u.getUserId(), u.getFirstName(), u.getLastName(), u.getTelephone(), u.getEmail(), u.getLicence(), u.getBiography());
			g.setImageSrc(u.getImage());
			guides.add(g);
		}
		return guides;
	}
    
    @Transactional
    public List<Guide> getAllGuides() {
		List<Users> users = userDAO.getGuides();
		List<Guide> guides = new ArrayList<Guide>();
		
		for (Users u: users) {
			guides.add(new Guide(u.getUserId(), u.getFirstName(), u.getLastName(), u.getTelephone(), u.getEmail(), u.getLicence(), u.getBiography(), u.getLicenceYear(), u.getAddress(), u.getNumber(), u.getUsername()));
		}
		return guides;
	}
    
    @Transactional
	public List<Mountaineer> getMountaineers() {
		List<Users> users = userDAO.getMountaineers();
		List<Mountaineer> mountaineers = new ArrayList<Mountaineer>();
		
		for (Users u: users) {
			mountaineers.add(new Mountaineer(u.getUserId(), u.getUsername(), u.getFirstName(), u.getLastName(), u.getTelephone(), u.getEmail(), u.getAddress(), u.getNumber()));
		}
		return mountaineers;
	}

    @Transactional
	public Guide getGuide(int id) {
    	Users user = userDAO.getUser(id);
    	
		return new Guide(user.getUserId(), user.getFirstName(), user.getLastName(), user.getTelephone(), user.getEmail(), user.getLicence(), user.getBiography());
	}
    
    @Transactional
    public List<Tour> getPreviousTours(String username){
    	List<Tour> tours = userDAO.getUserPreviousTours(username);
    	for(Tour t: tours) {
    		Users u = tourDAO.getGuide(t);
    		t.setGuideName(u.getFirstName() + " " + u.getLastName());
    	}
    	return tours;
    }
    
    @Transactional
    public List<Tour> getGuidePreviousTours(String username){
    	Users g = userDAO.findUser(username);
    	List<Tour> tours = userDAO.getGuidePreviousTours(g.getUserId());
    	for(Tour t: tours) {
    		Users u = tourDAO.getGuide(t);
    		t.setGuideName(u.getFirstName() + " " + u.getLastName());
    	}
    	return tours;
    }
    
    @Transactional
    public List<Tour> getFutureTours(String username){
    	List<Tour> tours = userDAO.getUserFutureTours(username);
    	for(Tour t: tours) {
    		Users u = tourDAO.getGuide(t);
    		if(u!=null)
    			t.setGuideName(u.getFirstName() + " " + u.getLastName());
    	}
    	return tours;
    }
    
    @Transactional
    public List<Tour> getGuideFutureTours(String username){
    	Users g = userDAO.findUser(username);
    	List<Tour> tours = userDAO.getGuideFutureTours(g.getUserId());
    	for(Tour t: tours) {
    		Users u = tourDAO.getGuide(t);
    		t.setGuideName(u.getFirstName() + " " + u.getLastName());
    	}
    	return tours;
    }
    
    @Transactional
    public void saveComment(Comment c) {
    	Users a = userDAO.getUser(c.getAuthorId());
    	Users g = userDAO.getUser(c.getTargetId());
    	commentDAO.saveCommentGuide(a, g, c);
    }
    
    @Transactional
	public List<Comment> getGuideComments(int id) {
		List<GuideComment> guideComments = commentDAO.getCommentsGuide(id);
		
		List<Comment> comments = new ArrayList<Comment>();
		
		for (GuideComment gc: guideComments) {
			//comments.add(new Comment(gc.getAuthor().getUserId(), gc.getGuide().getUserId(), gc.getContent()));
			Users author = gc.getAuthor();
			if(author != null)
				comments.add(new Comment(gc.getContent(), author.getFirstName() + " " + author.getLastName(), gc.getGuideCommentId()));
			else
				comments.add(new Comment(gc.getContent(), "Bivši korisnik", gc.getGuideCommentId()));
		}
		return comments;
	}
    
    @Transactional
    public Users getUserById(int id) {
    	return userDAO.getUser(id);
    }
    
    @Transactional
    public void changePassword(String newPassword, Users user) {
    	userDAO.changePassword(newPassword, user);
    }
    
    @Transactional
    public void deleteUserTour(int userId, int tourId) {
    	userTourDAO.deleteUserTour(userId, tourId);
    }
    
    @Transactional
    public void deleteGuideComment(int id) {
    	commentDAO.deleteGuideComment(id);
    }

    @Transactional
	public void deleteUser(int id) {
		List<TourComment> comments = commentDAO.deleteUserComment(id);
		for(TourComment c: comments) {
			c.setAuthor(null);
			commentDAO.updateTourComment(c);
		}
		
		userDAO.deleteUser(id);
	}

    @Transactional
	public void deleteGuide(int id) {
		List<Tour> tours = userDAO.getGuideTours(id);
		
		for(Tour t: tours) {
			t.setGuideId(0);
			tourDAO.saveTour(t);
		}
		
		userDAO.deleteGuide(id);
	}

}
