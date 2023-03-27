package ms;

public class Player extends Unit implements Movable {
	private int y;
	private int x;
	
	public Player(int y, int x) {
		super("당신", 100, 50, 9999, 10);
		this.y = y;
		this.x = x;
	}
	
	@Override
	public Integer getStatus(int index) {
		return super.status.get(index);
	}
	
	@Override
	public void setStatus(int index, int value) {
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
