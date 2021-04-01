package etf.master.front;

import java.util.Date;

import javax.persistence.Transient;

public class Guide {
	
	private int guideId;
	private String firstName;
	private String lastName;
	private String telephone;
	private String email;
	private String licence;
	private String biography;
	private int licenceYear;
	private String address;
	private int number;
	private String username;
	private String imageSrc;
	
	public Guide() {}
	
	public Guide(int guideId, String firstName, String lastName, String telephone, String email, String licence, String biography) {
		super();
		this.guideId = guideId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.licence = licence;
		this.biography = biography;
	}
	
	public Guide(int guideId, String firstName, String lastName, String telephone, String email, String licence,
			String biography, int licenceYear, String address, int number, String username) {
		super();
		this.guideId = guideId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.licence = licence;
		this.biography = biography;
		this.licenceYear = licenceYear;
		this.address = address;
		this.number = number;
		this.username = username;
	}

	public int getGuideId() {
		return guideId;
	}

	public void setGuideId(int guideId) {
		this.guideId = guideId;
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

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	
}
