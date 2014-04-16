package it.xpug.hangman.domain;

public class User {
	UserId id;
	String name;
	String password;

	public User(UserId id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public boolean authenticates(UserId userId, String password) {
		return this.password.equals(password) && this.id.equals(userId);
	}

	@Override
	public int hashCode() {
		return id.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return this.id.equals(other.id);
	}


}
