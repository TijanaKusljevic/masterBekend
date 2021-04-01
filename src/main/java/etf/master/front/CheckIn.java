package etf.master.front;

public class CheckIn {
	
	private int user;
	private int tour;
	
	public CheckIn() {
		super();
	}

	public CheckIn(int user, int tour) {
		super();
		this.user = user;
		this.tour = tour;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}
	
}
