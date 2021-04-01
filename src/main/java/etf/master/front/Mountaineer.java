package etf.master.front;

import java.util.Date;

public class Mountaineer {
	
	private int mountaineerId;
	private String userName;
	private String firstName;
	private String lastName;
	private String telephone;
	private String email;
	private String address;
	private int number;
	
	public Mountaineer() {
		super();
	}

	public Mountaineer(int mountaineerId, String userName, String firstName, String lastName, String telephone,
			String email, String address, int number) {
		super();
		this.mountaineerId = mountaineerId;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
		this.number = number;
	}

	public int getMountaineerId() {
		return mountaineerId;
	}

	public void setMountaineerId(int mountaineerId) {
		this.mountaineerId = mountaineerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
}
