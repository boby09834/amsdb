package beans;

import org.bson.Document;

public class Teacher {

	private String username;
	private String name;
	private String email;
	private String phone;
	private String password;

	public Teacher(String username, String name, String email, String phone, String password) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public Teacher(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Document constructDbDocument(Teacher teacher) {
		return new Document().append("username", teacher.getUsername()).append("name", teacher.getName())
				.append("phone", teacher.getPhone()).append("email", teacher.getEmail())
				.append("password", teacher.getPassword());
	}

}
