package etf.master.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users implements java.io.Serializable{
	
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
	
	@Column(name="dateOfBirth")
	private Date dateOfBirth;
	
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
	
	public Users() {
		super();
	}

	public Users(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Users(String username, String password, String firstName, String lastName, String telephone, String email,
			Date dateOfBirth, int number, String type, String licence, int licenceYear, String address, String biography) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.number = number;
		this.type = type;
		this.licence = licence;
		this.licenceYear = licenceYear;
		this.address=address;
		this.biography=biography;
	}

	@Id
	public String getUsername() {
        return username;
    }

	@Id
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
    
}
