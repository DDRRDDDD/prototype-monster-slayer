package ms;

public class GoblinKing extends Goblin  implements Boss{
	
	private int skillValue;
	
	public GoblinKing() {
		super("고블린킹", 50, 20, 8, 5);
		this.skillValue = 5;
	}
	
	@Override
	public void setStatus(int index, int value) {
		boolean isMaxHp = index == Unit.MAX_HP ? true : false;
		boolean isMaxMp = index == Unit.MAX_MP ? true : false;
		boolean isAtt   = index == Unit.ATT    ? true : false;
		if(isMaxHp || isMaxMp || isAtt) {
			System.err.println("잘못된 접근");
			return;
		}
		super.status.set(index, value);
	}
	
	@Override
	public boolean skill() {
		String line1 = String.format("%s이 겁에 질려 도망칩니다!", super.name);
		String line2 = String.format("%s의 방어력이 5 상승합니다.", super.name);
		Main.systemSpeak(line1);
		Main.systemSpeak(line2);
		
		int def = super.status.get(DEF) + skillValue;
		setStatus(DEF, def);
		
		return true;
	}
	
}
