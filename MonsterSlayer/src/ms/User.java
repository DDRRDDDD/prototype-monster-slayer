package ms;

import java.util.Objects;

public class User{
	public static final String pattern = "[%s/%s/%s]";
	
	private String id;
	private String password;
	private String name;
	
	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public void setUserName(String name) {
		this.name = name;
	}
	
	public String getUserName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, password);
		
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
		boolean isTrueForId       = Objects.equals(this.id, other.id);
		boolean isTrueForPassword = Objects.equals(this.password, other.password);
//		boolean isTrueForName     = Objects.equals(this.name, other.name);
		
		return isTrueForId && isTrueForPassword;
//		    && isTrueForName;
	}
		
	@Override
	public String toString() {
		return String.format(pattern, this.name, this.id, this.password);
	}
}
