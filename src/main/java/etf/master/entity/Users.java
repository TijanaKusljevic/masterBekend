package etf.master.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId")
	private int userId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="address")
	private String address;
	
	@Column(name="number")
	private int number;
	
	@Column(name="type")
	private String type;
	
	@Column(name="licence")
	private String licence;
	
	@Column(name="licenceYear")
	private int licenceYear;
	
	@Column(name="biograohy")
	private String biography;
	
	@Column(name="image")
	private String image;
	
	@Column(name="roleId")
	private int roleId;
	
	@Transient
	private String role;
	
	/*@JsonBackReference
	@OneToMany (mappedBy = "users")
	private Set<UserTour> userTours = new HashSet<UserTour>(0);*/
	
	public Users() {
		super();
	}

	public Users(String username, String password) {
		System.out.println("mali konstruktroe");
		//super();
		this.username = username;
		this.password = password;
	}

	public Users(String username, String password, String firstName, String lastName, String telephone, String email, int roleId,
			 int number, String type, String licence, int licenceYear, String address, String biography) {
		//super();
		System.out.println("veliki konstruktroe");
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.number = number;
		this.type = type;
		this.licence = licence;
		this.licenceYear = licenceYear;
		this.address=address;
		this.biography=biography;
		this.roleId = roleId;
		this.role = "MIKI";
	}
	
	public Users(int userId, String username, String password, String firstName, String lastName, String telephone,
			String email, String address, int number, String type, String licence, int licenceYear, String biography,
			String image, int roleId, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
		this.number = number;
		this.type = type;
		this.licence = licence;
		this.licenceYear = licenceYear;
		this.biography = biography;
		this.image = image;
		this.roleId = roleId;
		this.role = role;
	}

	public String getUsername() {
        return username;
    }

	
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public int getLicenceYear() {
		return licenceYear;
	}

	public void setLicenceYear(int licenceYear) {
		this.licenceYear = licenceYear;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	@Id
	public int getUserId() {
		return userId;
	}

	@Id
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Transient
	public String getRole() {
		return role;
	}

	@Transient
	public void setRole(String role) {
		this.role = role;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	/*public Set<UserTour> getUserTours() {
		return userTours;
	}

	public void setUserTours(Set<UserTour> userTours) {
		this.userTours = userTours;
	}*/
	
	
}
