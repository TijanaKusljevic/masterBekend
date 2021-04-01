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
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import etf.master.entity.Tour;
import etf.master.entity.Users;
import etf.master.front.CheckIn;
import etf.master.front.Comment;
import etf.master.front.Guide;
import etf.master.service.ApplicationUserDetailsService;
import etf.master.service.TourService;

@RestController
@RequestMapping("/tour")
public class TourRestController {

	private TourService tourService;
	private ApplicationUserDetailsService userService;

	@Autowired
	public TourRestController(TourService tourService, ApplicationUserDetailsService userService) {
		super();
		this.tourService = tourService;
		this.userService = userService;
	}
	
	@PostMapping("/fileUpload/{id}")
	public void imageUpload(@PathVariable("id") String id,  @RequestParam("file") MultipartFile file) {
		try {
			System.out.println(id+"problematicno");
			String dir = System.getProperty("user.dir")+"\\src\\main\\resources";
			
            Path copyLocation = Paths
                .get(dir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            tourService.saveImage(file.getOriginalFilename(), Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}
	
	@PostMapping("/newTour")
	@ResponseBody
	public String createTour(@RequestBody Tour tour, Authentication authentication) {
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		Users user = userService.getUserByUsername(username);
		tour.setGuideId(user.getUserId());
		tourService.saveTour(tour);
		System.out.println(tour.getTourId()+"*");
		int tourId = tour.getTourId();
		return Integer.toString(tourId);
	}
	
	@PostMapping("/editTour")
	public void editTour(@RequestBody Tour tour) {
		System.out.println("edit tour");
		System.out.println(tour.getName() + tour.getTourId());
		Tour t = tourService.getTour(tour.getTourId(), null);
		t.setDays(tour.getDays());
		t.setNights(tour.getNights());
		t.setName(tour.getName());
		t.setStartDate(tour.getStartDate());
		t.setEndDate(tour.getEndDate());
		t.setPhysicalAbility(tour.getPhysicalAbility());
		t.setDescription(tour.getDescription());
		//tour.setImage(t.getImage());
		//tour.setGuideId(t.getGuideId());
		tourService.saveTour(t);
	}
	
	@PostMapping("/replicateTour")
	public void replicateTour(@RequestBody Tour tour) {
		Tour t = new Tour(tour.getName(), tour.getStartDate(), tour.getEndDate(), tour.getDays(), tour.getNights(), tour.getPhysicalAbility(), tour.getDescription());
		Tour t1 = tourService.getTour(tour.getTourId(), null);
		t.setGuideId(t1.getGuideId());
		t.setImage(t1.getImage());
		tourService.saveTour(t);
	}
	
	@GetMapping("/allTours")
	public List<Tour> getAllTours(Authentication authentication){
		String s = authentication.getName();
		int start = s.indexOf("role=");
		String h = s.substring(start);
		String role = h.substring(5, h.indexOf(","));
		String username = s.substring(5, s.indexOf(","));
		List<Tour> tours = tourService.findAll(username, role);
		
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
		System.out.println("Vracam ture");
		return tours;
	}
	
	@GetMapping("/oneDayTrips")
	public List<Tour> getOneDayTrips(){
		List<Tour> tours = tourService.findOneDayTrips();
		
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
	
	//String encoded = Base64.getEncoder().encodeToString("Hello".getBytes());
	@GetMapping("/img")
	public String getImage() {
		
		String img = System.getProperty("user.dir")+"\\src\\main\\resources\\dijagramKlasa.PNG";
		
		byte[] bytes = null;
		
		try {
			bytes = Files.readAllBytes(Paths.get(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(bytes);
		
	}
	
	@GetMapping("/tour/{id}")
	public Tour getGuide(@PathVariable("id") int id, Authentication authentication){
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		
		Tour t = tourService.getTour(id, username);
		
		String img = System.getProperty("user.dir")+"\\src\\main\\resources\\"+t.getImage();
		
		byte[] bytes = null;
		
		try {
			bytes = Files.readAllBytes(Paths.get(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		t.setImageSrc(Base64.getEncoder().encodeToString(bytes));
		
		return t;
	}
	
	@PostMapping("/checkIn/{id}")
	public void createTour(@PathVariable("id") int id, Authentication authentication) {
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		Users user = userService.getUserByUsername(username);
		CheckIn checkIn = new CheckIn(user.getUserId(), id);
		tourService.saveUserTour(checkIn.getUser(), checkIn.getTour());
	}
	
	@PostMapping("/comment")
	public void saveComment(@RequestBody Comment comment, Authentication authentication) {
		System.out.println("Komentar");
		System.out.println(comment.getTargetId());
		String s = authentication.getName();
		String username = s.substring(5, s.indexOf(","));
		Users user = userService.getUserByUsername(username);
		comment.setAuthorId(user.getUserId());
		tourService.saveComment(comment);
	}
	
	@GetMapping("/comments/{id}")
	public List<Comment> getComments(@PathVariable("id") int id){
		System.out.println("poziv");
		return tourService.getTourComments(id);
	}
	
	@DeleteMapping("/removeComment/{id}")
	public void deleteComment(@PathVariable("id") int id) {
		tourService.deleteTourComment(id);
	}
	
	@DeleteMapping("/removeTour/{id}")
	public void deleteTour(@PathVariable("id") int id) {
		tourService.deleteTour(id);
	}
	
	//sliku pretvoriti u niz bajtova convert to base 64 string
	//niz bajtova pretvorti u base 64
}
