package etf.master.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import etf.master.dao.EmployeeDAO;
import etf.master.entity.Users;
import etf.master.front.Guide;
import etf.master.entity.Employee;
import etf.master.service.ApplicationUserDetailsService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeDAO employeeDAO;
	private ApplicationUserDetailsService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public EmployeeRestController(EmployeeDAO theEmployeeDAO, ApplicationUserDetailsService theUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		userService= theUserService;
		employeeDAO = theEmployeeDAO;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping("/signup")
	public void signUp(@RequestBody Users user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.saveUser(user);

	}
	
	// expose "/employees" and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}

	@GetMapping("/guides")
	public List<Guide> findGuides(){
		return userService.getGuides();
	}
	
}










