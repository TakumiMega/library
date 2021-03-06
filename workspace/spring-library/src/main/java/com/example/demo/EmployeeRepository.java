package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Integer> {

	Employee findByEmployeeNameAndEmployeePass(String employeeName, String employeePass);	//社員名とパスワード参照

	Employee findByEmployeeId(int attribute);

	Employee findByEmployeeName(String employeeName);

	Employee findByEmployeeIdNotAndEmployeeNameAndEmployeePass(int employeeId, String employeeName, String employeePass);
	
}
