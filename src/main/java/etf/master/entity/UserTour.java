package etf.master.entity;

import java.io.Serializable;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//implements Serializable

@Entity
@Table(name="usersTour")
public class UserTour {
	
	@EmbeddedId
    private UserTourId id;

	@JsonIgnore
	@MapsId("userId")
	@ManyToOne
	@JoinColumn(name = "userId")
	private Users user;
	
	@JsonIgnore
	@MapsId("tourId")
	@ManyToOne
	@JoinColumn(name = "tourId")
	private Tour tour;

	public UserTour() {
		super();
	}

	public UserTour(UserTourId id) {
		super();
		this.id = id;
	}
	
	public UserTour(UserTourId id, Users user, Tour tour) {
		super();
		this.id = id;
		this.user = user;
		this.tour = tour;
	}
	
	public UserTourId getId() {
		return id;
	}

	public void setId(UserTourId id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
	
}
