package ms;

import java.util.ArrayList;

abstract class Unit {
	public static final int MAX_HP = 0;
	public static final int HP     = 1;
	public static final int MAX_MP = 2;
	public static final int MP     = 3;
	public static final int ATT    = 4;
	public static final int DEF    = 5;
	
	protected String name;
	protected ArrayList<Integer> status;
	
	protected Unit(String name, int hp, int mp, int att, int def) {
		this.status = new ArrayList<>(6);
		this.name = name;
		
		status.add(MAX_HP, hp);
		status.add(HP,     hp);
		status.add(MAX_MP, mp);
		status.add(MP,     mp);
		status.add(ATT,   att);
		status.add(DEF,   def);
	}
	
	
	public String toString() {
		return Main.getStatusMsg(this);
	}
	
	abstract Integer getStatus(int index);
	
	abstract void setStatus(int index, int value);
	// 고블린킹 : 스킬로 방어력이 올라가는 버프 (hp, mp, def) 빼고 막음
	// 고블린 : 스테이터스 수정을 hp빼고 막아야함

	// player : 개방
	
	
}
