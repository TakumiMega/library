package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classification")
public class Classification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "classification_id")
	private int classificationId;

	@Column(name = "classification_name")
	private String classificationName;

	@Column(name = "insert_date")
	private String insertDate;

	@Column(name = "insert_employee_id")
	private String insertEmployeeId;

	@Column(name = "update_date")
	private String updateDate;

	@Column(name = "update_employee_id")
	private String updateEmployeeId;

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

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getInsertEmployeeId() {
		return insertEmployeeId;
	}

	public void setInsertEmployeeId(String insertEmployeeId) {
		this.insertEmployeeId = insertEmployeeId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateEmployeeId() {
		return updateEmployeeId;
	}

	public void setUpdateEmployeeId(String updateEmployeeId) {
		this.updateEmployeeId = updateEmployeeId;
	}

	Classification(){

	}
}
