package ms;

public interface Combatable {
	
	// 라인수 말하는겁니당
	public static final int GENERAL_GROUND_SIZE = 5;
	public static final int BOSS_GROUND_SIZE = 7;
	
	public static final int SPAWN_PLAYER_CORRDINATE_GENERAL_Y  = 2;
	public static final int SPAWN_PLAYER_CORRDINATE_GENERAL_X  = 2;
	public static final int SPAWN_ENEMY_CORRDINATE_Y  = 2;
	public static final int SPAWN_ENEMY_CORRDINATE_X  = 9;

	public static final int SPAWN_PLAYER_CORRDINATE_Y_FOR_BOSS  = 3;
	public static final int SPAWN_PLAYER_CORRDINATE_X_FOR_BOSS  = 2;
	public static final int SPAWN_BOSS_CORRDINATE_Y  = 3;
	public static final int SPAWN_BOSS_CORRDINATE_X  = 10;
	
	void attack();
	boolean fightDone();
	
}
