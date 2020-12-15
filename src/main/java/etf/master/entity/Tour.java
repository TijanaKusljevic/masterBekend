package etf.master.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tour")
public class Tour {
	
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
		
		System.out.println(this);
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
	
	
}
