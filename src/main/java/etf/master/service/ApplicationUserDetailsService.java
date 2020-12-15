package etf.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import etf.master.dao.UserDAO;
import etf.master.entity.Users;
import etf.master.front.Guide;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    
	private UserDAO userDAO;
	
	@Autowired
	public ApplicationUserDetailsService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users applicationUser = userDAO.findUser(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
    
    @Transactional
    public void saveUser(Users user) {
    	userDAO.saveUser(user);
    }

    @Transactional
	public List<Guide> getGuides() {
    	
		List<Users> users = userDAO.getGuides();
		List<Guide> guides = new ArrayList<Guide>();
		
		for (Users u: users) {
			guides.add(new Guide(u.getFirstName(), u.getLastName(), u.getTelephone(), u.getEmail(), u.getLicence(), u.getBiography()));
		}
		
		return guides;
	}
}
