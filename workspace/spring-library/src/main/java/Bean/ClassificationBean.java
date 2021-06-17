package Bean;

import java.sql.Date;

public class ClassificationBean {
	private int classificationId;
	private String classificationName;
	private Date insertDate;
	private int insertEmployeeId;
	private Date updateDate;
	private int updateEmployeeId;

	public ClassificationBean() {
	}

	public ClassificationBean(int classificationId, String classificationName) {
		this.classificationId = classificationId;
		this.classificationName = classificationName;
	}

	public int getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public int getInsertEmployeeId() {
		return insertEmployeeId;
	}
	public void setInsertEmployeeId(int insertEmployeeId) {
		this.insertEmployeeId = insertEmployeeId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getUpdateEmployeeId() {
		return updateEmployeeId;
	}
	public void setUpdateEmployeeId(int updateEmployeeId) {
		this.updateEmployeeId = updateEmployeeId;
	}
}
