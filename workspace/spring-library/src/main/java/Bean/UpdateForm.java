package Bean;

import java.util.List;

import com.example.demo.Classification;

public class UpdateForm {
	private int booksId;
	private String booksName;
	private String booksAuthor;
	private String booksStock;
	private String booksLend;
	private String booksRemarks;
	private int classificationId;
	private List<Classification> classificationList;
	private List<ClassificationBean> classificationshowList;

	public int getBooksId() {
		return booksId;
	}

	public void setBooksId(int booksId) {
		this.booksId = booksId;
	}

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

	public String getBooksStock() {
		return booksStock;
	}

	public void setBooksStock(String booksStock) {
		this.booksStock = booksStock;
	}

	public String getBooksLend() {
		return booksLend;
	}

	public void setBooksLend(String booksLend) {
		this.booksLend = booksLend;
	}

	public String getBooksRemarks() {
		return booksRemarks;
	}

	public void setBooksRemarks(String booksRemarks) {
		this.booksRemarks = booksRemarks;
	}

	public List<ClassificationBean> getClassificationshowList() {
		return classificationshowList;
	}

	public void setClassificationshowList(List<ClassificationBean> classificationshowList) {
		this.classificationshowList = classificationshowList;
	}



}
