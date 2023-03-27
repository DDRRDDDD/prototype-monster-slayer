package ms;

public class Map implements Mapable {

	private int[][] board;
	
	@Override
	public void loadMapByFloor(int floor) {
		String fileName = "floor" +floor+ ".txt";
		FileManager fm = new FileManager(fileName);
		board = fm.getMap();
	}

	public void addEnemyToEnemyList() {
		for(int i = 0; i < TOTAL_SIZE; i++) {
			for(int j = 0; j < TOTAL_SIZE; j++) {
				if(board[i][j] == ENEMY) {
					GameRunner.enemyList.add(new GoblinPawn(i, j));
				}
			}
		}
	}
	
	@Override
	public void printMap(Player player) {
		int indexY = player.getY() >= VIEW_SIZE ? VIEW_SIZE : 0; 
		int indexX = player.getX() >= VIEW_SIZE ? VIEW_SIZE : 0; 

		int viewY = VIEW_SIZE + indexY;
		int viewX = VIEW_SIZE + indexX;

		for(int i = indexY; i < viewY; i++) {
			for(int j = indexX; j < viewX; j++) {
				int index = board[i][j];
				System.out.print(objects[index]);
			}
			System.out.println();
		}
	}

	public Player initPlayerCoordinate() {
		int y = -1;
		int x = -1;

		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == PLAYER) {
					y = i;
					x = j;
				}
			}
		}
		return new Player(y, x);
	}

	public int getElementAt(int y, int x) {
		return this.board[y][x];
	}
	
	public void setElementAt(int y, int x, int element) {
		if(element < 0 || element > CHEST)
			return;
		
		this.board[y][x] = element;
	}

	public void setPosition(Movable unit, int y, int x) {
		int unitY = unit.getY();
		int unitX = unit.getX();

		int tmp = board[y][x];
		board[y][x] = board[unitY][unitX];
		board[unitY][unitX] = tmp;

	}
}
