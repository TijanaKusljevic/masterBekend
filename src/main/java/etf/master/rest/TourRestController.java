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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import etf.master.entity.Tour;
import etf.master.service.TourService;

@RestController
@RequestMapping("/tour")
public class TourRestController {

	private TourService tourService;

	@Autowired
	public TourRestController(TourService tourService) {
		super();
		this.tourService = tourService;
	}
	
	@PostMapping("/fileUpload/{id}")
	public void imageUpload(@PathVariable("id") int id,  @RequestParam("file") MultipartFile file) {
		try {
			
			String dir = System.getProperty("user.dir")+"\\src\\main\\resources";
			
            Path copyLocation = Paths
                .get(dir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}
	
	@PostMapping("/newTour")
	public void createTour(@RequestBody Tour tour) {
		tourService.saveTour(tour);
	}
	
	@GetMapping("/allTours")
	public List<Tour> getAllTours(){
		System.out.println("pozz");
		return tourService.findAll();
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
	
	@GetMapping("/bla")
	public void kiki(){
		System.out.println("pozz");
	}
	
	//sliku pretvoriti u niz bajtova convert to base 64 string
	//niz bajtova pretvorti u base 64
}
