//10A3804 カンルイヨン
//FF.java

import java.io.*;
	public class FF{
		public static void main(String [] arg){
			Player player = null;
			Enemy enemy = null;
			Dungeon dungeon = null;
			Actor actor =null;
			String input;
			int runuse=0;
			int gateopen=0; /*Gate初期値*/
			int dungeonlvl=1;/*初期dungeonレベル*/
			int dx=0,dy=0;
			System.out.println("************* First Fantasy 3 *************");
			InputStreamReader stdin = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader (stdin);
			try {
				System.out.println("前のゲームをロードしましすか?[y/n]");
				input =reader.readLine();
			}
			catch (IOException e){
				System.out.println(e);
				return;
			}
			if(input.length() > 0 && input.charAt(0) == 'y'){
				try{
					FileInputStream fileIn = new FileInputStream("ff.save");
					ObjectInputStream in=new ObjectInputStream(fileIn);
					player = (Player)in.readObject();
					dungeon = (Dungeon)in.readObject();
					actor = (Actor)in.readObject();
					enemy = (Enemy)in.readObject();
					dx=(int)in.readInt();
					dy=(int)in.readInt();
					in.close();
				}
				catch (Exception e){
					System.out.println(e);
					return;
				}
				System.out.println("ロードしました");
			}
			else{
				try {
					System.out.println("プレイヤーの名前？");
					input= reader.readLine();
				}
				catch(IOException e){
					System.out.println(e);
					return;
				}
				player = new Player(input);
				dungeon = new Dungeon(player,8,15,20,5,1);
			}
			dungeon.show();
			player.show();
			while(true){
				System.out.println("\nどうする？" + "[m(移動),f(戦う),p(回復),r(逃げる),w(表示),s(セーブ),x(終了)]");
				try {
					input = reader.readLine();
				}
				catch(IOException e){
					System.out.println(e);
					return;
				}
				if(input.length() == 0) continue;
				switch (input.charAt(0)){
					case 'm':
						if (enemy !=null){
							System.out.println("敵がいるので移動できませえん");
							break;
						}
						while(true){
							System.out.println("どっち？[e(東),w(西),s(南),n(北)]");
							try{ input=reader.readLine(); }
							catch (IOException e){ System.out.println(e);	return;}
							if(input.length() ==0) continue;
							if(input.charAt(0) =='e') {dx=+1; dy=0; break;}
							if(input.charAt(0) =='w') {dx=-1; dy=0; break;}
							if(input.charAt(0) =='s') {dx=0; dy=+1; break;}
							if(input.charAt(0) =='n') {dx=0; dy=-1; break;}
						}
						actor = dungeon.move(player,dx,dy,0);
						if (actor == null) { dungeon.show(); break;}
						if (actor instanceof NoMove){ actor.show(); break;}
						if (actor instanceof Gate){ 
							gateopen = 1;	
							enemy = new Enemy();
							enemy.lvl=dungeonlvl*5;
							enemy.hp = enemy.max_hp = dungeonlvl*300;
							enemy.strength=dungeonlvl*50;
							enemy.Boss();
							enemy.show(); 
							break;
						}
						if (actor instanceof Potion){
							System.out.println("アイテムを手に入れました"); 
							actor.show(); 
							player.getPotion();
							dungeon.move(player,dx,dy,1);
							dungeon.show(); 
							break; 
						}
						if (actor instanceof Enemy){ System.out.println("敵がいました!!!\n"); 
							enemy=(Enemy)actor; 
							enemy.lvl=(int)((dungeonlvl + 3) * Math.random())+dungeonlvl;
							enemy.show(); 
							break; 
						}
					case 'f':
						if (enemy==null){ System.out.println("敵がいません"); break;}
						if (player.attack(enemy) == 1){
							enemy =null;
							if(gateopen==1){
								if(dungeonlvl>=30){
									System.out.println("おめでとう！！宝を手に入りました");
									return;
								}
								else{
									gateopen=0;
									dungeonlvl +=1;
									System.out.println("ダンジョン第" + dungeonlvl +"層に入りました");
									dungeon = new Dungeon(player,dungeonlvl+8,dungeonlvl+15,dungeonlvl+20,5,dungeonlvl); 
									dungeon.show();
								}
								break;
							}
							dungeon.move(player,dx,dy,1);
							dungeon.show();
							break;	
						}
						enemy.show();
						if(enemy.attack(player) == 1){
							System.out.println("Game Over");
							return;
						}
						player.show();
						runuse=0;
						break;
					case 'r':
						int run=0;
						if(runuse==1){ System.out.println("もう使えない"); break;};	
						if (enemy==null){ System.out.println("敵がいません"); break;}
						if(gateopen==1){
							System.out.println("この戦い逃がさない");
							break;
						}
						else{
							if(enemy.lvl>player.lvl){
								run=(int)((5) * Math.random());
								if(run < 2){
									System.out.println("脱走失敗");
									runuse =1;
									break;
								}
								else{
								enemy =null;
								System.out.println("脱走成功！！");
								dungeon.move(player,dx,dy,1);
								dungeon.show();
								break;
								}
							}
							run=(int)((3) * Math.random());
							if(run>0){
								System.out.println("脱走成功！！");
								enemy =null;
								dungeon.move(player,dx,dy,1);
								dungeon.show();
							}
							else{
							System.out.println("脱走失敗");
							runuse =1;
							break;
							}
							break;
						}
						
					case 'p':
						player.usePotion();
						break;
					case 'w':
						dungeon.show();
						player.show();
						if (enemy !=null ) enemy.show();
						break;
					case 's':
						try{
							FileOutputStream fileOut = new FileOutputStream("ff.save");
							ObjectOutputStream out = new ObjectOutputStream(fileOut);
							out.writeObject(player);
							out.writeObject(dungeon);
							out.writeObject(actor);
							out.writeObject(enemy);
							out.writeInt(dx);
							out.writeInt(dy);
							out.close();
						}
						catch (Exception e){ System.out.println(e); return; }
						System.out.println("セーブしました");
						break;
					case 'x':
						System.out.println("終了");
						return;
					default:
						System.out.println("コマンド不正");
					
				}
			}
		}
	}