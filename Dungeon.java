//10A3804 �J�����C����
//Dungeon.java

import java.io.*;

class Dungeon implements Serializable{
	Actor[][] map;
	int mapSize,numNoMove,numEnemy,numPotion,dlvl;
	Dungeon(Player player,int mapSize,int numNoMove,int numEnemy,int numPotion,int dlvl){
		int x,y;
		if((1+1+numNoMove + numEnemy + numPotion)> mapSize * mapSize){
			System.out.println("�G���[:actor���������܂�");
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
    System.out.println("�_���W����[��"+ dlvl +"�w,�T�C�Y=" + mapSize + "�~" + mapSize + ",�G�̐�=" + numEnemy + ",��̌�=" + numPotion + "]");
    System.out.print('+');
    for (int x = 0; x < mapSize; x++) System.out.print('-');
    System.out.println('+');
    for (int y = 0; y < mapSize; y++) {
      System.out.print('l');
      for (int x = 0; x < mapSize; x++) {
        char s = '.';
        if (map[x][y] instanceof Player) s = 'P';  /* �v���C���[ */
        if (map[x][y] instanceof Gate) s = 'G';  /* �Q�[�g     */
        if (map[x][y] instanceof NoMove) s = 'X';  /* �ړ��s��   */
//        if (map[x][y] instanceof Enemy)  s = 'e';  /* �G         */
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
    /* �ړ����actor�����Ȃ��Ƃ��́A�ړ�����null��Ԃ��B        */
    /* �ړ����actor������Ƃ��́Aopt=0�̏ꍇ�͂���actor��Ԃ��A*/
    /* opt=1�̏ꍇ�͂���actor����菜���Ĉړ���null��Ԃ��B     */
    int x0 = 0, y0 = 0, x1, y1;     /* ���݂̈ʒu�ƈړ����ׂ��ʒu */
    int flag = 0;                   /* ���[�v����p�̃t���O */
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
      System.out.println("�ړ����܂���");
      return null;
    }
    if (opt == 0) return map[x1][y1];
    if (map[x1][y1] instanceof Enemy) numEnemy -= 1;
	if (map[x1][y1] instanceof Potion) numEnemy -= 1;
    map[x1][y1] = player; map[x0][y0] = null;
    System.out.println("�ړ����܂���");
    return null;
  }
}