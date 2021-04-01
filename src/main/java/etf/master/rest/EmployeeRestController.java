package etf.master.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import etf.master.dao.EmployeeDAO;
import etf.master.dao.UserDAO;
import etf.master.entity.Users;
import etf.master.filter.AuthorizationFilter;
import etf.master.front.Comment;
import etf.master.front.Guide;
import etf.master.front.Mountaineer;
import etf.master.front.Password;
import etf.master.entity.Employee;
import etf.master.entity.GuideComment;
import etf.master.entity.Tour;
import etf.master.service.ApplicationUserDetailsService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeDAO employeeDAO;
	private ApplicationUserDetailsService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	//private AuthorizationFilter authorizationFilter;
	
	@Autowired
	public EmployeeRestController(EmployeeDAO theEmployeeDAO, ApplicationUserDetailsService theUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		userService= theUserService;
		employeeDAO = theEmployeeDAO;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public String signUp(@RequestBody Users user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		if(user.getType().equals("Planinar")) {
			user.setRoleId(1);
		} else {
			user.setRoleId(2);
		}
		
		userService.saveUser(user);
		String username = user.getUsername();
		
		Users u = userService.getUserByUsername(username);
		
		return Integer.toString(u.getUserId());
	}
	
	@PostMapping("/edit")
	@ResponseBody
	public void editUser(@RequestBody Users user){
		Users u = userService.getUserByUsername(user.getUsername());
		u.setAddress(user.getAddress());
		u.setBiography(user.getBiography());
		u.setEmail(user.getEmail());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setLicence(user.getLicence());
		u.setLicenceYear(user.getLicenceYear());
		u.setNumber(user.getNumber());
		u.setTelephone(user.getTelephone());
		userService.saveUser(u);
	}
	
	@PostMapping("/fileUpload/{id}")
	public void imageUpload(@PathVariable("id") String id,  @RequestParam("file") MultipartFile file) {
		try {
			System.out.println(id+"problematicno");
			String dir = System.getProperty("user.dir")+"\\src\\main\\resources";
			
            Path copyLocation = Paths
                .get(dir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            userService.saveImage(file.getOriginalFilename(), Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}
	
	// expose "/employees" and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}

	@GetMapping("/guides")
	public List<Guide> findGuides(){
		List<Guide> guides = userService.getGuides();
		
		for(Guide g: guides) {
			String img = System.getProperty("user.dir")+"\\src\\main\\resources\\"+g.getImageSrc();
			
			byte[] bytes = null;
			
			try {
				bytes = Files.readAllBytes(Paths.get(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			g.setImageSrc(Base64.getEncoder().encodeToString(bytes));
		}
		return guides;
	}
	
	@GetMapping("/guide/{id}")
	public Guide getGuide(@PathVariable("id") int id){
		return userService.getGuide(id);
	}
	
	/*@GetMapping("/tours/{id}")
	public List<Tour> getTours(@PathVariable("id") int id){
		return userService.getTours(id);
	}*/
	
	@GetMapping("/myPreviousTours/{role}")
	@ResponseBody
	public List<Tour> getMyPreviousTours(@PathVariable("role") String role1, Authentication authentication){
		
		String s = authentication.getName();
		System.out.println(s);
		int start = s.indexOf("role=");
		String h = s.substring(start);
		String role = h.substring(5, h.indexOf(","));
		String username = s.substring(5, s.indexOf(","));
		
		List<Tour> tours = null;
		
		if(role.equals("GUIDE") && role1.equals("guide")) {
			tours = userService.getGuidePreviousTours(username);
		}else{
			tours = userService.getPreviousTours(username);
		}
		
		for(Tour t: tours) {
			
			String img = System.getProperty("user.dir")+"\\src\\main\\resources\\"+t.getImage();
			
			byte[] bytes = null;
			
			try {
				bytes = Files.readAllBytes(Paths.get(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			t.setImageSrc(Base64.getEncoder().encodeToString(bytes));
			
		}
		return tours;
	}
	
	@GetMapping("/myFutureTours")
	@ResponseBody
	public List<Tour> getMyFutureTours(Authentication authentication){
		String s = authentication.getName();
		int start = s.indexOf("role=");
		String h = s.substring(start);
		String role = h.substring(5, h.indexOf(","));
		String username = s.substring(5, s.indexOf(","));
		
		List<Tour> tours = null;
		
		if(role.equals("GUIDE")) {
			tours = userService.getGuideFutureTours(username);
			for(Tour t: tours) {
				System.out.println(t.isOwner());
			}
		}else {
			tours = userService.getFutureTours(username);
		}
		
		for(Tour t: tours) {
			
			String img = System.getProperty("user.dir")+"\\src\\main\\resources\\"+t.getImage();
			
			byte[] bytes = null;
			
			try {
				bytes = Files.readAllBytes(Paths.get(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			t.setImageSrc(Base64.getEncoder().encodeToString(bytes));
			
		}
		
		return tours;
	}
	
	@PostMapping("/guide/comment")
	public void saveComment(@RequestBody Comment comment, Authentication authentication) {
		System.out.println("Komentar");
		System.out.println(comment.getTargetId());
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		Users user = userService.getUserByUsername(username);
		comment.setAuthorId(user.getUserId());
		userService.saveComment(comment);
	}
	
	@GetMapping("/guide/comments/{id}")
	public List<Comment> getComments(@PathVariable("id") int id){
		System.out.println("poziv");
		return userService.getGuideComments(id);
	}
	
	@GetMapping("/allUsers")
	public List<Mountaineer> getMountaineers(){
		return userService.getMountaineers();
	}
	
	@GetMapping("/allGuides")
	public List<Guide> getAllGuides(){
		return userService.getAllGuides();
	}
	
	@GetMapping("/user/{id}")
	public Users getUser(@PathVariable("id") int id) {
		return userService.getUserById(id);
	}
	
	@GetMapping("/user/role")
	public String getRole(Authentication authentication) {
		System.out.println("Poziv za setovanje role");
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		return userService.getRole(username);
	}
	
	@GetMapping("/userDetails")
	@ResponseBody
	public Users getUserDetails(Authentication authentication) {
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		return userService.getUserByUsername(username);
	}
	
	@PostMapping("/changePassword")
	public void changePassword(@RequestBody Password password, Authentication authentication) {
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		Users user = userService.getUserByUsername(username);
		String oldPassword = bCryptPasswordEncoder.encode(password.getOldPassword());
		
		System.out.println(password.getNewPassword() + password.getOldPassword());
		System.out.println(oldPassword + "     " + user.getPassword());
		
		if(!bCryptPasswordEncoder.matches(password.getOldPassword(), user.getPassword())) {
			System.out.println("usao u if");
			//throw new WrongPasswordException("Pograšan unos trenutne lozinke");
			
		}
		System.out.println("usao");
		user.setPassword(bCryptPasswordEncoder.encode(password.getNewPassword()));
		userService.saveUser(user);
	}
	
	@DeleteMapping("/giveUp/{id}")
	public void deleteUserTour(@PathVariable("id") int id, Authentication authentication){
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		Users user = userService.getUserByUsername(username);
		int userId = user.getUserId();
		userService.deleteUserTour(userId, id);
	}
	
	@DeleteMapping("/removeComment/{id}")
	public void deleteComment(@PathVariable("id") int id) {
		userService.deleteGuideComment(id);
	}
	
	@DeleteMapping("/removeUser/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
	}
	
	@DeleteMapping("/removeGuide/{id}")
	public void deleteGuide(@PathVariable("id") int id) {
		userService.deleteGuide(id);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(WrongPasswordException e){
		ErrorResponse er = new ErrorResponse();
		er.setStatus(HttpStatus.BAD_REQUEST.value());
		er.setMessage(e.getMessage());
		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	}
}










