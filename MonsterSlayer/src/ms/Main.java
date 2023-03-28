package ms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Random;

public class Main{
	public static String[][] ASCII;
	public static final int LOGO       = 0;
	public static final int GAME_CLEAR = 1;
	public static final int GAME_OVER  = 2;
	
	private static final Main local = new Main();
	
	public static StringBuffer strBuffer;
	private static BufferedReader reader;
	private static BufferedWriter writer;
	
	public static Random ran = new Random();
	
	private Main() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		writer = new BufferedWriter(new OutputStreamWriter(System.out));
		strBuffer = new StringBuffer();
		initAscii();
	}
	public static Main getInstance() {
		return local;
	}
	private void initAscii() {
		ASCII = new String[][]
				{new FileManager("Logo.txt").load()
				,new FileManager("GameClear.txt").load()
				,new FileManager("GameOver.txt").load()};
	}
	
	public static String systemRead() {
		String value = "";
		
		try {value = reader.readLine();} 
		catch (IOException e) {}

		return value;
	}
	
	public static int systemReadForInteger() {
		int value = 0;
		
		try {
			value = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException nfe) {
			return systemReadForInteger();
		}catch (IOException e) {e.printStackTrace();}
		
		return value;
	}
	
	public static void systemWrite(String input) {
		try {
			writer.write("\n" + input);
			writer.flush();			
		} catch (IOException e) {e.printStackTrace();}
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
		strBuffer.setLength(0);
		strBuffer.append(" {0}\n");
		strBuffer.append("[HP : {2}/ {1}]\n");
		strBuffer.append("[MP : {4}/ {3}]\n");
		strBuffer.append("[ATT({5})/DEF({6})]\n");
		return strBuffer.toString();
	}
	
	public static String hitMsg(String attacker, int att) {
		strBuffer.setLength(0);
		strBuffer.append(attacker).append(" 이(가)");
		strBuffer.append(att).append("의 데미지로 공격!");
		return strBuffer.toString();
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
			
			systemWrite(word +"");
			try {
				Thread.sleep(delayTime);
			} catch (Exception e) {}
		}
		systemWrite("\n");
	}
	
	public static void printAscii(int title) {
		String[] str = ASCII[title];
		for(String logo : str) {
			System.out.println(logo);
			try {
				Thread.sleep(200);
			} catch (Exception e) {}
		}
	}
	
	public static void main(String[] args) {
		printAscii(LOGO);
		GameRunner act = new GameRunner();
		act.run();
	}
}
