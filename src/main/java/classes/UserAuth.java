package classes;

import java.time.LocalDate;

public class UserAuth {
	private String name;
	private String token;
	private LocalDate date;
	private String saltPBKDF2;
	private  byte[] saltScrypt;

	public UserAuth(String name, String token, LocalDate date, String saltPBKDF2,  byte[] saltScrypt) {
		this.name = name;
		this.token = token;
		this.date = date;
		this.saltPBKDF2 = saltPBKDF2;
		this.saltScrypt = saltScrypt;

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
	public String getSaltPBKDF2() {
		return saltPBKDF2;
	}
	public  byte[] getSaltScrypt() {
		return saltScrypt;
	}
	public void setSaltPBKDF2(String saltPBKDF2) {
		this.saltPBKDF2 = saltPBKDF2;
	}
	public void setSaltScrypt( byte[] saltScrypt) {
		this.saltScrypt = saltScrypt;
	}

}
