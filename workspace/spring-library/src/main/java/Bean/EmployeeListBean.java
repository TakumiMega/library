package Bean;

public class EmployeeListBean {
	
	private int employeeId;
	private String employeeName;
	private String positionName;
	
	public EmployeeListBean() {
		
	}
	
	public EmployeeListBean(int employeeId, String employeeName, String positionName) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.positionName = positionName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	

}
