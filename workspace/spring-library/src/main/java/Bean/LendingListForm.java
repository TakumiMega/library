package Bean;

import java.util.List;

public class LendingListForm {
	private String booksName;
	private String booksAuthor;
	private int classificateId;
	private List<ClassificationBean> classificationList;
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
	public List<ClassificationBean> getClassificationList() {
		return classificationList;
	}
	public void setClassificationList(List<ClassificationBean> classificationList) {
		this.classificationList = classificationList;
	}
	public int getClassificateId() {
		return classificateId;
	}
	public void setClassificateId(int classificateId) {
		this.classificateId = classificateId;
	}
}
