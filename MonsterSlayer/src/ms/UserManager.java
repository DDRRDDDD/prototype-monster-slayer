package ms;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class UserManager implements UserManagement{
	private Map<Integer, User> infoByUser;
	private FileManager fm;
	private Integer log;
	
	private String inputId;
	private String inputPw;
	
	public UserManager() {
		log = null;
		initUserManager();
	}
	private void initUserManager() {
		this.fm = new FileManager("userData.txt");
		infoByUser = fm.getInfoByUser();
		infoByUser = infoByUser == null ? new HashMap<>() : infoByUser;
		
	}
	
	public String getName() {
		return infoByUser.get(log).getUserName();
	}
	
	private boolean isDuplication(int hashCode) {
		return this.infoByUser.get(hashCode) != null;
	}
	
	public boolean isLogging(boolean check) {
		boolean logging = log != null;
		
		if(check == logging) {
			if(check == false)
				Main.systemWrite("로그인이 되어 있지 않습니다");
			else
				Main.systemWrite("이미 로그인 중입니다");
		}
		return logging;
	}
	
	private void insertIdAndPassword() {
		Main.systemWrite("아이디를 입력하세요");
		this.inputId = Main.systemRead();
		Main.systemWrite("비밀번호를 입력하세요");
		this.inputPw = Main.systemRead();
	}
	
	public void saveUserInfoData() {
		Set<Integer> set = infoByUser.keySet();
		
		Main.strBuffer.setLength(0);
		for(Integer hashKey : set) {
			String tmp = infoByUser.get(hashKey).toString();
			Main.strBuffer.append(tmp).append("\n");
		}
		
		fm.save(Main.strBuffer.toString());
	}

	
	@Override
	public void userSignUp() {
		insertIdAndPassword();
		int userKey = Objects.hash(this.inputId, this.inputPw);
		if(isDuplication(userKey)) {
			Main.systemWrite("이미 존재하는 아이디와 비밀번호입니다");
			return;
		}
		
		User user = new User(this.inputId, this.inputPw);
		
		Main.systemWrite("사용자의 이름을 알려주세요!");
		String name = Main.systemRead();
		user.setUserName(name);
		
		infoByUser.put(userKey, user);
	}

	@Override
	public void userDrop() {
		insertIdAndPassword();
		int userKey = Objects.hash(this.inputId, this.inputPw);
		if(! isDuplication(userKey)) {
			Main.systemWrite("입력하신 아이디와 비밀번호를 다시 확인해주세요");
			return;
		}
		
		Main.systemWrite("정상 처리되었습니다");
		infoByUser.remove(userKey);
	}

	@Override
	public void logIn() {
		if(isLogging(true))
			return;
		insertIdAndPassword();
		int userKey = Objects.hash(this.inputId, this.inputPw);
		if(! isDuplication(userKey)) {
			Main.systemWrite("입력하신 아이디와 비밀번호를 다시 확인해주세요");
			return;
		}
		
		log = userKey;
		
		String name = infoByUser.get(log).getUserName();
		String message = String.format("환영합니다 %s님!", name);
		Main.systemWrite(message);
	}

	@Override
	public void logOut() {
		if(!isLogging(false)) 
			return;

		log = null;
	}
	
	
}
