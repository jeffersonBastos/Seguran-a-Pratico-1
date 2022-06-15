import java.time.LocalDate;

public class UserLogin {
	private String username;
	private String password;

	public UserLogin(String username, String password) {
		this.username = username;
this.password = password;
	}

	public String getName() {
		return username;
	}

	public void setUserame(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}


}
