package ms;


//나무1  \uD83C\uDF32
//나무2  \uD83C\uDF33
//고블린  \uD83E\uDD22
//보스   \uD83D\uDE08
//바닥   \uD83D\uDFEB
//플레이어 \uD83E\uDD20
//상자    \uD83C\uDF81


public interface Mapable {
//	static final String[] objects = {"+", "♟", "x", "X", "◼", "★"};
	static final String[] objects = {"\uD83D\uDFEB", "\uD83E\uDD20", "\uD83E\uDD22", "\uD83D\uDE08", "\uD83C\uDF32", "\uD83C\uDF81"};
	
	public static final int TOTAL_SIZE = 40;
	public static final int VIEW_SIZE = 20;

	public static final int GROUND = 0;
	public static final int PLAYER = 1;
	public static final int ENEMY  = 2;
	public static final int BOSS   = 3;
	public static final int WALL   = 4;
	public static final int CHEST  = 5;

	void printMap(Player player);

	void loadMapByFloor(int floor);
}
