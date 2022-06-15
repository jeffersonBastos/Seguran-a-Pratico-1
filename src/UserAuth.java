import java.time.LocalDate;

public class UserAuth {
	private String name;
	private String token;
	private LocalDate date;

	public UserAuth(String name, String token, LocalDate date) {
		this.name = name;
		this.token = token;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
