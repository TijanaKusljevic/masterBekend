package etf.master.front;

public class Comment {
	
	private int authorId;
	private int targetId;
	private String content;
	private String name;
	private int id;
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int authorId, int targetId, String content) {
		System.out.println("target je " + targetId);
		//super();
		this.authorId = authorId;
		this.targetId = targetId;
		this.content = content;
	}
	
	public Comment(String content, String name, int id) {
		super();
		this.content = content;
		this.name = name;
		this.id = id;
	}
	
	public Comment(int authorId, int targetId, String content, String name, int id) {
		super();
		this.authorId = authorId;
		this.targetId = targetId;
		this.content = content;
		this.name = name;
		this.id = id;
	}
	
	public Comment(int targetId, String content) {
		this.content = content;
		this.targetId = targetId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
