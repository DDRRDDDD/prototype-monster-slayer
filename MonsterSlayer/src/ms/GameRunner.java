package ms;

import java.util.ArrayList;

public class GameRunner{
	private Map map;
	private Player player;
	private UserManager userManager;

	public static ArrayList<Movable> enemyList = new ArrayList<>();
	public GameRunner() {
		this.userManager = new UserManager();
		this.map = new Map();
		this.map.loadMapByFloor(1);
		this.map.addEnemyToEnemyList();
		this.player = this.map.initPlayerCoordinate();
		
	}

	private void move() {
		String direction = Main.systemRead();

		int tmpY = player.getY();
		int tmpX = player.getX();

		if(direction.equals("w"))      tmpY--;
		else if(direction.equals("a")) tmpX--;
		else if(direction.equals("s")) tmpY++;
		else if(direction.equals("d")) tmpX++;

		if(!isEventTriggered(tmpY, tmpX))
			return;

		map.setPosition(player, tmpY, tmpX);
		player.setY(tmpY);
		player.setX(tmpX);
	}

	private void enemyRandomMove() {
		int size = enemyList.size()-1;
		for(int i = size; i >= 0; i--) {
			Movable unit = enemyList.get(i);
			int unitY = unit.getY();
			int unitX = unit.getX();

			int operation = Main.ran.nextBoolean() ? 1 : -1;
			if(Main.ran.nextBoolean())
				unitY += operation;
			else
				unitX += operation;

			int elementByMap = map.getElementAt(unitY, unitX);
			if(Map.GROUND == elementByMap) {
				map.setPosition(unit, unitY, unitX);
				unit.setY(unitY);
				unit.setX(unitX);				
			}else {
				if(Map.PLAYER == elementByMap) {
					Main.systemSpeak("적이 먼저 시비를 걸어옵니다 전투준비");
					startBattle((Unit)unit);
				}
				continue;					
			}
		}

	}

	private Unit getUnitByCoordinate(int y, int x) {
		Unit result = null;
		for(int i = 0; i < enemyList.size(); i++) {
			Movable enemy = enemyList.get(i);
			int tmpY = enemy.getY();
			int tmpX = enemy.getX();
			if(tmpY == y && tmpX == x) {
				result = (Unit) enemy;
			}
		}
		return result;
	}

	private boolean isEventTriggered(int y, int x) {
		int elementByMap = map.getElementAt(y, x);
		if(Map.GROUND == elementByMap)
			return true;			

		if(Map.ENEMY == elementByMap) {
			Main.systemSpeak("적과 만났다 전투 준비");
			Unit enemy = getUnitByCoordinate(y, x);
			startBattle(enemy);

		}else if(Map.BOSS == elementByMap) {
			Main.systemSpeak("보스와 마주쳤다 전 투 준 비!!");
			startBattle(new GoblinKing());

		}else if(Map.CHEST == elementByMap) {
			Main.systemSpeak("왠지 기분 좋은 일이 있을거 같다..");
		}
		return false;
	}

	private void startBattle(Unit enemy) {
		Battle battle = new Battle(player, enemy);
		while(true) {
			battle.placedUnit();
			battle.attack();
			if(battle.fightDone())
				break;
		}
		if(enemy instanceof Boss) {
			Main.printAscii(Main.GAME_CLEAR);
			System.exit(0);
		}
			
		else
			removeUnitList(battle.getDeadEnemyCoordinate());
	}

	private void removeUnitList(int[] coordinate) {
		int targetY = coordinate[0];
		int targetX = coordinate[1];
		for(int i = 0; i < enemyList.size(); i++) {
			int tmpY = enemyList.get(i).getY();
			int tmpX = enemyList.get(i).getX();

			if(targetY == tmpY && targetX == tmpX) {
				enemyList.remove(i);
				getRandomElements(targetY, targetX);
			}
		}
	}
	private void getRandomElements(int y, int x) {
		int randomChance = Main.ran.nextInt(10);
		if(randomChance < 2)
			map.setElementAt(y, x, Map.CHEST);
		else
			map.setElementAt(y, x, Map.GROUND);

	}

	private void initList() {
		int size = enemyList.size() - 1;
		for(int i = size; i >= 0; i--) {
			if(enemyList.get(i) == null)
				enemyList.remove(i);
		}
	}
	
	private void userInterface() {
		do {
			Main.systemWrite("1. 회원가입");
			Main.systemWrite("2. 회원탈퇴");
			Main.systemWrite("3. 로그인");
			Main.systemWrite("4. 로그아웃");
			Main.systemWrite("0. 시작하기");					
		}while(userInterfaceMenuSel());
	}
	
	private boolean userInterfaceMenuSel() {
		
		int sel = Main.systemReadForInteger();
		if(sel == 0) {
			if(userManager.isLogging(false)) {
				String name = userManager.getName();
				Main.systemSpeak(name + "님 환영합니다 곧 게임을 시작합니다");
				userManager.saveUserInfoData();
				return false;
			}
		}
		
		else if(sel == UserManager.USER_SIGN_UP) userManager.userSignUp();
		else if(sel == UserManager.USER_DROP)    userManager.userDrop();
		else if(sel == UserManager.LOG_IN)       userManager.logIn();
		else if(sel == UserManager.LOG_OUT)      userManager.logOut();
		return true;
	}

	public void run(){
		userInterface();
		while(true) {
			printMap();
			move();
			enemyRandomMove();
			initList();
		}
	}

	private void printMap() {
		this.map.printMap(player);
	}

}
