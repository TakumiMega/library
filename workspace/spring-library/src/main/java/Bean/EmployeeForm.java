package Bean;

public class EmployeeForm {
	
	private String employeeName;
	private int positionId;
	private String employeePass;
	private String employeeRePass;
	
	public EmployeeForm() {
		
	}
	
	public EmployeeForm(String employeeName, int positionId, String employeePass) {
		this.employeeName = employeeName;
		this.positionId = positionId;
		this.employeePass = employeePass;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getEmployeePass() {
		return employeePass;
	}

	public void setEmployeePass(String employeePass) {
		this.employeePass = employeePass;
	}

	public String getEmployeeRePass() {
		return employeeRePass;
	}

	public void setEmployeeRePass(String employeeRePass) {
		this.employeeRePass = employeeRePass;
	}

	
	
	

}
