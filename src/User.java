public class User {

	public String phone, name, email, pass;

	public User() {
		phone = name = email = pass = "";
	}

	public User(String u, String p) {
		email = u;
		pass = p;
		phone = name = "";
	}

	public String toString() {
		return name + " " + email + " " + phone;
	}
}
