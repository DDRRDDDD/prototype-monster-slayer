package ms;

import java.text.MessageFormat;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	private static final Main local = new Main();
	public static Scanner scan = new Scanner(System.in);
	public static Random ran = new Random();
	//딱히 스레드가 안쓰이면 빌더로 바꿀것
	public static StringBuffer strBuff = new StringBuffer();
	
	private Main() {}
	public static Main getInstance() {
		return local;
	}
	
	// 유닛 별로 능력치의 출력포맷
	public static String getStatusMsg(Unit unit) {
		Object[] args = new Object[7];
		
		args[0] = unit.name;
		for(int i = 1, statusIdx = 0; i < args.length; i++, statusIdx++) {
			args[i] = unit.status.get(statusIdx);
		}
		String messageFormat = "";
		messageFormat = MessageFormat.format(statusMsgFormat(), args);
		
		return messageFormat;
	}
	
	// getMsgFormat()의 포맷 양식
	private static String statusMsgFormat() {
		strBuff.setLength(0);
		strBuff.append(" {0}\n");
		strBuff.append("[HP : {2}/ {1}]\n");
		strBuff.append("[MP : {4}/ {3}]\n");
		strBuff.append("[ATT({5})/DEF({6})]\n");
		return strBuff.toString();
	}
	
	public static String hitMsg(String attacker, int att) {
		strBuff.setLength(0);
		strBuff.append(attacker).append(" 이(가)");
		strBuff.append(att).append("의 데미지로 공격!");
		return strBuff.toString();
	}
	
	// 돌려보면 앎 시스템 메세지
	public static void systemSpeak(String sentence) {
		int wordSize = sentence.length();
		int index = 0;
		int delayTime = 0;
		
		while(wordSize != index) {
			char word = sentence.charAt(index++);
			
			if(word == ' ')
				delayTime = 150;
			else
				delayTime = 100;
			
			System.out.print(word);
			try {
				Thread.sleep(delayTime);
			} catch (Exception e) {}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		GameRunner act = new GameRunner();
		Main.systemSpeak("뭔가 게임 시작하기 전에 시스템 메세지로 말하는게 좀 있어 보여서");
		Main.systemSpeak("솔직히 대본은 없고 그래도 이렇게 하면 정말 게임 플레이 전 인트로로는");
		Main.systemSpeak("딱인거 같아요");
		Main.systemSpeak("게임의 기본적인 조작은 w a s d로 진행이 되고");
		Main.systemSpeak("상호작용 키는 0으로 고정입니다");
		Main.systemSpeak("네 스토리는 마왕한테 납치당한 공주를 구하러 가는거구요");
		Main.systemSpeak("그러면 게임 시작합니다");
		Main.systemSpeak("GAME START!");
		Main.systemSpeak(" ");
		Main.systemSpeak("아무키나 눌러주세요");
		act.run();
	}
}
