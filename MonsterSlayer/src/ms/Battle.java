package ms;

public class Battle implements Combatable{
	
	private Unit player;
	private Unit enemy;
	
	private int turn;
	private int[][] battleGround;
	
	private int playerY;
	private int playerX;
	private int enemyY;
	private int enemyX;
	
	public Battle(Unit player, Unit enemy) {
		this.player = player;
		this.enemy = enemy;
		this.turn = 0;
		initBattleGround();
		
		String enemyName = enemy.name;
		Main.systemSpeak(enemyName + " 이(가) 나타났다!!");
	}
	
	private void initBattleGround() {
		FileManager fm;
		if(enemy instanceof Boss) {
			fm = new FileManager("battleGroundByBoss");
			playerY = SPAWN_PLAYER_CORRDINATE_Y_FOR_BOSS;
			playerX = SPAWN_PLAYER_CORRDINATE_X_FOR_BOSS;
			enemyY  = SPAWN_BOSS_CORRDINATE_Y;
			enemyX  = SPAWN_BOSS_CORRDINATE_X;
		
		}
		else {
			fm = new FileManager("battleGround");			
			playerY = SPAWN_PLAYER_CORRDINATE_GENERAL_Y;
			playerX = SPAWN_PLAYER_CORRDINATE_GENERAL_X;
			enemyY  = SPAWN_ENEMY_CORRDINATE_Y;
			enemyX  = SPAWN_ENEMY_CORRDINATE_X;
		}

		battleGround = fm.getBattleGround();
	}
	
	
	public void placedUnit() {
		boolean isMyTurn = this.turn % 2 == 0 ? true : false;
		if(isMyTurn) {
			Main.systemSpeak("당신의 공격 차례입니다");
			Main.systemSpeak("공격로를 정해주세요!");
		}else {
			Main.systemSpeak("당신의 수비 차례입니다");
			Main.systemSpeak("적의 공격을 예상해 회피하십쇼!");
		}
		
		while(true) {
			printCombatInfo();
			if(playerPlacement()) {
				enemyPlacement();
				printCombatInfo();				
				break;
			}
		}	
		
		// 대사 추가
	}
	
	@Override
	public void attack() {
		boolean isMyTurn = this.turn % 2 == 0 ? true : false;
		this.turn += 1;
		
		if(this.enemyY != this.playerY) {
			Main.systemSpeak("아무일도 없었다..");
			return;
		}

		Unit victim = null;
		Unit attacker = null;
		
		victim   = isMyTurn ? enemy : player;
		attacker = isMyTurn ? player : enemy;
				
		int demage    = attacker.getStatus(Unit.ATT);
		int victimDef = victim.getStatus(Unit.DEF);
		int victimHp  = victim.getStatus(Unit.HP);
		
		//데미지 연산
		demage -= victimDef;
		demage = demage < 1 ? 1 : demage;
		victimHp -= demage;
		
		victim.setStatus(Unit.HP, victimHp);
		
		
		String name = attacker.name;
		int att = attacker.getStatus(Unit.ATT);
		Main.systemSpeak(Main.hitMsg(name, att));
		
		if(demage < 3)
			Main.systemSpeak("효과가 별로인거 같다..");
		
	}
	
	private boolean playerPlacement() {
		String move = Main.systemRead();
		int playerTmpY = this.playerY;
		
		if(move.equals("0")) return true;
		else if(move.equals("s")) playerTmpY += 1;			
		else if(move.equals("w")) playerTmpY -= 1;
		
		if(!canMove(playerTmpY, playerX))
			return false;
		
		
		battleGround[this.playerY][this.playerX] = Mapable.GROUND;
		this.playerY = playerTmpY;
		battleGround[this.playerY][this.playerX] = Mapable.PLAYER;
		
		return false;
	}
	
	private void enemyPlacement() {
		boolean isBoss = enemy instanceof Boss;
		
		// 상수 사이즈는 맵의 전체적인 크기이기 때문에 태두리부분을 제외한 나머지만 담기 위해 -2로 초기화
		int motionRange = -2;
		int initialCoordinate = 0;
		
		motionRange += isBoss ? 
				BOSS_GROUND_SIZE : GENERAL_GROUND_SIZE;
		
		initialCoordinate = isBoss ?
				SPAWN_BOSS_CORRDINATE_Y : SPAWN_ENEMY_CORRDINATE_Y;
		
		int operate = 0;
		int enemyTmpY = 0;
		
		operate = motionRange / 2;
		enemyTmpY = Main.ran.nextInt(motionRange) - operate;
		enemyTmpY +=  initialCoordinate;
		
		// 테스트용
//		enemyTmpY = 2;
		
		int tmp = battleGround[enemyTmpY][this.enemyX];
		battleGround[enemyTmpY][this.enemyX] = battleGround[this.enemyY][this.enemyX];
		battleGround[this.enemyY][this.enemyX] = tmp;
		
		this.enemyY = enemyTmpY;
	}
	
	
	private boolean canMove(int y, int x) {
		return this.battleGround[y][x] != Mapable.WALL;
	}
	
	@Override
	public boolean fightDone() {
		if(enemy.getStatus(Unit.HP) < 1) {
			Main.systemSpeak("와!");
			Main.systemSpeak(enemy.name + "을 물리쳤다! 전투 종료");
			return true;
		}
		if(player.getStatus(Unit.HP) < 1) {
			Main.systemSpeak("당신은 전투에서 패배하였습니다");
			Main.printAscii(Main.GAME_OVER);
			System.exit(0);
		}
		return false;
	}
	
	// 죽은 유닛의 좌표값 뽑기
	public int[] getDeadEnemyCoordinate() {
		if(!(enemy instanceof Movable))
			return null;
			
		Movable unit = (Movable) enemy;
		return new int[] {unit.getY(), unit.getX()};
	}
	
	private void printCombatInfo() {
		System.out.print(this.enemy.toString());
		for(int i = 0; i < battleGround.length; i++) {
			for(int j = 0; j < battleGround[i].length; j++) {
				int index = battleGround[i][j];
				System.out.print(Map.objects[index]);
			}
			System.out.println();
		}
		System.out.println(this.player.toString());
		
	}
}
