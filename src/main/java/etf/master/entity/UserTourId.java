package etf.master.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//implements Serializable

@Embeddable
public class UserTourId implements Serializable{
	
	@Column(name = "userId")
    private int userId;
	
    @Column(name = "tourId")
    private int tourId;
    
	public UserTourId() {
		super();
	}

	public UserTourId(int userId, int tourId) {
		super();
		this.userId = userId;
		this.tourId = tourId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTourId() {
		return tourId;
	}

	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
    
}
