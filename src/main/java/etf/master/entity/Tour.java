package etf.master.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="tour")
public class Tour implements Comparable<Tour>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tourId")
	private int tourId;

	@Column(name = "name")
	private String name;
	
	@Column(name = "startDate")
	private Date startDate;
	
	@Column(name = "endDate")
	private Date endDate;
	
	@Column(name = "days")
	private int days;
	
	@Column(name = "nights")
	private int nights;
	
	@Column(name = "physicalAbility")
	private int physicalAbility;
	
	@Column(name = "description")
	private String description;
	
	@Column(name="guide")
	private int guideId;
	
	@Column(name="image")
	private String image;
	
	@Column(name="mounteneers")
	private int mounteneers;
	
	/*@JsonManagedReference
	@OneToMany (mappedBy = "tour")
	private Set<UserTour> userTours = new HashSet<UserTour>(0);*/
	
	@Transient
	private boolean finished;
	
	@Transient
	private boolean signedIn;
	
	@Transient
	private boolean owner;
	
	@Transient
	private boolean notAvailable;
	
	@Transient
	private String guideName;
	
	@Transient
	private String imageSrc;

	public Tour() {
		
	}

	public Tour(String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description) {
		
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		
		Date currentDate = new Date();
		System.out.println(currentDate);
		
		if (startDate.before(currentDate)) {
			System.out.println("true");
			finished = true;
		} else {
			finished = false;
		}
		
		System.out.println(this);
	}
	
	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
	}

	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, boolean finished, boolean signedIn) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.finished = finished;
		this.signedIn = signedIn;
	}
	
	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, boolean finished, boolean signedIn, String guideName) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.finished = finished;
		this.signedIn = signedIn;
		this.guideName = guideName;
	}
	
	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, String image, boolean finished, boolean signedIn, String guideName) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.image = image;
		this.finished = finished;
		this.signedIn = signedIn;
		this.guideName = guideName;
	}
	
	

	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, String image, boolean finished, boolean signedIn, String guideName,
			String imageSrc) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.image = image;
		this.finished = finished;
		this.signedIn = signedIn;
		this.guideName = guideName;
		this.imageSrc = imageSrc;
	}
	
	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, String image, boolean finished, boolean signedIn, boolean owner,
			String guideName, String imageSrc) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.image = image;
		this.finished = finished;
		this.signedIn = signedIn;
		this.owner = owner;
		this.guideName = guideName;
		this.imageSrc = imageSrc;
	}
	
	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, String image, int mounteneers, boolean finished, boolean signedIn,
			boolean owner, String guideName, String imageSrc) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.image = image;
		this.mounteneers = mounteneers;
		this.finished = finished;
		this.signedIn = signedIn;
		this.owner = owner;
		this.guideName = guideName;
		this.imageSrc = imageSrc;
	}
	
	

	public Tour(int tourId, String name, Date startDate, Date endDate, int days, int nights, int physicalAbility,
			String description, int guideId, String image, int mounteneers, boolean finished, boolean signedIn,
			boolean owner, boolean notAvailable, String guideName, String imageSrc) {
		super();
		this.tourId = tourId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.nights = nights;
		this.physicalAbility = physicalAbility;
		this.description = description;
		this.guideId = guideId;
		this.image = image;
		this.mounteneers = mounteneers;
		this.finished = finished;
		this.signedIn = signedIn;
		this.owner = owner;
		this.notAvailable = notAvailable;
		this.guideName = guideName;
		this.imageSrc = imageSrc;
	}

	public int getTourId() {
		return tourId;
	}

	public void setTourId(int tourId) {
		this.tourId = tourId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public int getPhysicalAbility() {
		return physicalAbility;
	}

	public void setPhysicalAbility(int physicalAbility) {
		this.physicalAbility = physicalAbility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Tour [tourId=" + tourId + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", days=" + days + ", nights=" + nights + ", physicalAbility=" + physicalAbility + ", description="
				+ description + "]";
	}
	
	   //(fetch = FetchType.LAZY, mappedBy = "users")
	/*public Set<UserTour> getuserTours() {
		return this.userTours;
	}

	public void setuserTours(Set<UserTour> userTours) {
		this.userTours = userTours;
	}*/

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isSignedIn() {
		return signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}

	public int getGuideId() {
		return guideId;
	}

	public void setGuideId(int guideId) {
		this.guideId = guideId;
	}

	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public int getMounteneers() {
		return mounteneers;
	}

	public void setMounteneers(int mounteneers) {
		this.mounteneers = mounteneers;
	}

	public boolean isNotAvailable() {
		return notAvailable;
	}

	public void setNotAvailable(boolean notAvailable) {
		this.notAvailable = notAvailable;
	}

	@Override
	public int compareTo(Tour o) {
		if (getStartDate() == null || o.getStartDate() == null) {
		   return 0;
		}
		return getStartDate().compareTo(o.getStartDate());
	}
	
}
