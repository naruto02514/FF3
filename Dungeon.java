//10A3804 カンルイヨン
//Dungeon.java

import java.io.*;

class Dungeon implements Serializable{
	Actor[][] map;
	int mapSize,numNoMove,numEnemy,numPotion,dlvl;
	Dungeon(Player player,int mapSize,int numNoMove,int numEnemy,int numPotion,int dlvl){
		int x,y;
		if((1+1+numNoMove + numEnemy + numPotion)> mapSize * mapSize){
			System.out.println("エラー:actorが多すぎます");
			System.exit(1);
	}
	this.mapSize = mapSize; 
	this.numNoMove = numNoMove;
	this.numEnemy = numEnemy;
	this.numPotion = numPotion;
	this.dlvl=dlvl;
	map =new Actor[mapSize][mapSize];
	x=(int)(mapSize * Math.random());
	y=(int)(mapSize * Math.random());
	map[0][0]=player;
	map[mapSize-1][mapSize-1] = new Gate();
	for(int n=0;n< numNoMove;n++){
		do{
			x=(int)(mapSize * Math.random());
			y=(int)(mapSize * Math.random());
		}
		while(map[x][y] !=null); map[x][y]=new NoMove();
	}
	for(int n=0;n< numEnemy;n++){
		do{
			x=(int)(mapSize * Math.random());
			y=(int)(mapSize * Math.random());
		}
		while(map[x][y] !=null); map[x][y]=new Enemy();
	}
	for(int n=0; n<numPotion;n++){
		do{
			x=(int)(mapSize * Math.random());
			y=(int)(mapSize * Math.random());
		}
		while (map[x][y] != null); map[x][y] = new Potion();
	}
}

  void show() {
    System.out.println("ダンジョン[第"+ dlvl +"層,サイズ=" + mapSize + "×" + mapSize + ",敵の数=" + numEnemy + ",薬の個数=" + numPotion + "]");
    System.out.print('+');
    for (int x = 0; x < mapSize; x++) System.out.print('-');
    System.out.println('+');
    for (int y = 0; y < mapSize; y++) {
      System.out.print('l');
      for (int x = 0; x < mapSize; x++) {
        char s = '.';
        if (map[x][y] instanceof Player) s = 'P';  /* プレイヤー */
        if (map[x][y] instanceof Gate) s = 'G';  /* ゲート     */
        if (map[x][y] instanceof NoMove) s = 'X';  /* 移動不可   */
//        if (map[x][y] instanceof Enemy)  s = 'e';  /* 敵         */
//		if (map[x][y] instanceof Potion)  s = 'p';  /* Potion */
        System.out.print(s);
      }
      System.out.println('l');
    }
    System.out.print('+');
    for (int x=0; x<mapSize; x++) System.out.print('-');
    System.out.println('+');
  }


  Actor move(Player player, int dx, int dy, int opt) {
    /* 移動先にactorがいないときは、移動してnullを返す。        */
    /* 移動先にactorがいるときは、opt=0の場合はそのactorを返す、*/
    /* opt=1の場合はそのactorを取り除いて移動しnullを返す。     */
    int x0 = 0, y0 = 0, x1, y1;     /* 現在の位置と移動すべき位置 */
    int flag = 0;                   /* ループ制御用のフラグ */
    for (x0 = 0; x0 < mapSize; x0++) {
      for (y0 = 0; y0 < mapSize; y0++) {
        if (map[x0][y0] == player) { flag = 1; break; }
      }
      if (flag == 1) break;
    }
    x1 = x0 + dx; y1 = y0 + dy;
    if (x1<0 || x1>=mapSize || y1<0 || y1>=mapSize) return new NoMove();
    if (map[x1][y1] == null) {
      map[x1][y1] = player; map[x0][y0] = null;
      System.out.println("移動しました");
      return null;
    }
    if (opt == 0) return map[x1][y1];
    if (map[x1][y1] instanceof Enemy) numEnemy -= 1;
	if (map[x1][y1] instanceof Potion) numEnemy -= 1;
    map[x1][y1] = player; map[x0][y0] = null;
    System.out.println("移動しました");
    return null;
  }
}