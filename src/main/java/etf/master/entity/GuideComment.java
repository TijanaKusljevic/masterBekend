package etf.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="guideComment")
public class GuideComment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idguideComment")
	private int guideCommentId;

	//@JsonIgnore
	
	@JoinColumn(name = "guideId")
	@ManyToOne
	private Users guide;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "authorId")
	private Users author;
	
	@JoinColumn(name = "content")
	private String content;

	public GuideComment() {
		super();
	}

	public GuideComment(Users guide, Users author, String content) {
		super();
		this.guide = guide;
		this.author = author;
		this.content = content;
	}

	public int getGuideCommentId() {
		return guideCommentId;
	}

	public void setGuideCommentId(int guideCommentId) {
		this.guideCommentId = guideCommentId;
	}

	public Users getGuide() {
		return guide;
	}

	public void setGuide(Users guide) {
		this.guide = guide;
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
