package ms;

public class GoblinPawn extends Goblin implements Movable {
	
	private int y;
	private int x;
	
	public GoblinPawn(int y, int x) {
		super("고블린 졸개", 30, 0, 2, 2);
		this.y = y;
		this.x = x;
		
	}
	
	@Override
	public void setStatus(int index, int value) {
		if(index != Unit.HP) {
			System.err.println("잘못된 접근");
			return;
		}
		super.status.set(index, value);
	}
	
	@Override
	public int getY() {
		return this.y;
	}
	
	@Override
	public int getX() {
		return this.x;
	}
	
	@Override
	public void setY(int y) {
		if(y < 0 || y > Map.TOTAL_SIZE)
			return;
		
		this.y = y;
	}
	
	@Override
	public void setX(int x) {
		if(x < 0 || x > Map.TOTAL_SIZE)
			return;
		
		this.x = x;
	}
	
}
