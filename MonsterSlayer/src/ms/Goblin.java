package ms;

abstract public class Goblin extends Unit{
	
	public Goblin(String name, int hp, int mp, int att, int def) {
		super(name, hp, mp, att, def);
	}
	
	@Override
	public Integer getStatus(int index) {
		return super.status.get(index);		
	}
}