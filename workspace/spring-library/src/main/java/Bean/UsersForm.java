package Bean;

public class UsersForm {

	private String usersName;
	private String usersBirthday;
	private String usersAddress;
	private String usersPhone;
	private String usersEmail;
	
	public UsersForm(){
		
	}
	
	public UsersForm(String usersName, String usersBirthday, String usersAddress, String usersPhone, String usersEmail){
		this.usersName = usersName;
		this.usersBirthday = usersBirthday;
		this.usersAddress = usersAddress;
		this.usersPhone = usersPhone;
		this.usersEmail = usersEmail;
	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	public String getUsersBirthday() {
		return usersBirthday;
	}

	public void setUsersBirthday(String usersBirthday) {
		this.usersBirthday = usersBirthday;
	}

	public String getUsersAddress() {
		return usersAddress;
	}

	public void setUsersAddress(String usersAddress) {
		this.usersAddress = usersAddress;
	}

	public String getUsersPhone() {
		return usersPhone;
	}

	public void setUsersPhone(String usersPhone) {
		this.usersPhone = usersPhone;
	}

	public String getUsersEmail() {
		return usersEmail;
	}

	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}
	
	
}
