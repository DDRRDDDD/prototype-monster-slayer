package ms;

public interface UserManagement {

	public static final int USER_SIGN_UP = 1;
	public static final int USER_DROP = 2;
	public static final int LOG_IN = 3;
	public static final int LOG_OUT = 4;

	void userSignUp();
	void userDrop();
	void logIn();
	void logOut();
	
}
