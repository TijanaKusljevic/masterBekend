package etf.master.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tourComment")
public class TourComment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idtourComment")
	private int tourCommentId;
	
	@JoinColumn(name = "tourId")
	@ManyToOne
	private Tour tour;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn( columnDefinition="integer", name = "authorId" )
	private Users author;
	
	@JoinColumn(name = "content", nullable = true)
	private String content;

	public TourComment() {
		super();
	}

	public TourComment(Tour tour, Users author, String content) {
		super();
		this.tour = tour;
		this.author = author;
		this.content = content;
	}
	
	public TourComment(int tourCommentId, Tour tour, String content) {
		super();
		this.tourCommentId = tourCommentId;
		this.tour = tour;
		this.content = content;
	}

	public TourComment(int tourCommentId, Tour tour) {
		super();
		this.tourCommentId = tourCommentId;
		this.tour = tour;
	}

	public TourComment(int tourCommentId, Tour tour, Users author) {
		super();
		this.tourCommentId = tourCommentId;
		this.tour = tour;
		this.author = author;
	}

	public int getTourCommentId() {
		return tourCommentId;
	}

	public void setTourCommentId(int tourCommentId) {
		this.tourCommentId = tourCommentId;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Users getAuthor() {
		return author;
	}

	public void setAuthor(Users author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
