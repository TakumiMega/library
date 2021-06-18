package Bean;

import java.util.List;

import com.example.demo.Classification;

public class UpdateForm {
	private String booksName;
	private String booksAuthor;
	private int classificationId;
	private List<Classification> classificationList;
	public String getBooksName() {
		return booksName;
	}
	public void setBooksName(String booksName) {
		this.booksName = booksName;
	}
	public String getBooksAuthor() {
		return booksAuthor;
	}
	public void setBooksAuthor(String booksAuthor) {
		this.booksAuthor = booksAuthor;
	}
	public int getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}
	public List<Classification> getClassificationList() {
		return classificationList;
	}
	public void setClassificationList(List<Classification> classificationList) {
		this.classificationList = classificationList;
	}


}
