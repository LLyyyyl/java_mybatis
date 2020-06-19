package vo;

public class User {
	private String userName;//用户名
	private String uId;// id
	private String password;//密码
	private String role;//角色
	
	public User() {
		super();
	}

	public User(String userName, String uId, String password, String role) {
		super();
		this.userName = userName;
		this.uId = uId;
		this.password = password;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", uId=" + uId + ", password=" + password + ", role=" + role + "]";
	}

	
}
