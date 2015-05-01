//10A3804 �J�����C����
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
			int gateopen=0; /*Gate�����l*/
			int dungeonlvl=1;/*����dungeon���x��*/
			int dx=0,dy=0;
			System.out.println("************* First Fantasy 3 *************");
			InputStreamReader stdin = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader (stdin);
			try {
				System.out.println("�O�̃Q�[�������[�h���܂�����?[y/n]");
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
				System.out.println("���[�h���܂���");
			}
			else{
				try {
					System.out.println("�v���C���[�̖��O�H");
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
				System.out.println("\n�ǂ�����H" + "[m(�ړ�),f(�키),p(��),r(������),w(�\��),s(�Z�[�u),x(�I��)]");
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
							System.out.println("�G������̂ňړ��ł��܂�����");
							break;
						}
						while(true){
							System.out.println("�ǂ����H[e(��),w(��),s(��),n(�k)]");
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
							System.out.println("�A�C�e������ɓ���܂���"); 
							actor.show(); 
							player.getPotion();
							dungeon.move(player,dx,dy,1);
							dungeon.show(); 
							break; 
						}
						if (actor instanceof Enemy){ System.out.println("�G�����܂���!!!\n"); 
							enemy=(Enemy)actor; 
							enemy.lvl=(int)((dungeonlvl + 3) * Math.random())+dungeonlvl;
							enemy.show(); 
							break; 
						}
					case 'f':
						if (enemy==null){ System.out.println("�G�����܂���"); break;}
						if (player.attack(enemy) == 1){
							enemy =null;
							if(gateopen==1){
								if(dungeonlvl>=30){
									System.out.println("���߂łƂ��I�I�����ɓ���܂���");
									return;
								}
								else{
									gateopen=0;
									dungeonlvl +=1;
									System.out.println("�_���W������" + dungeonlvl +"�w�ɓ���܂���");
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
						if(runuse==1){ System.out.println("�����g���Ȃ�"); break;};	
						if (enemy==null){ System.out.println("�G�����܂���"); break;}
						if(gateopen==1){
							System.out.println("���̐킢�������Ȃ�");
							break;
						}
						else{
							if(enemy.lvl>player.lvl){
								run=(int)((5) * Math.random());
								if(run < 2){
									System.out.println("�E�����s");
									runuse =1;
									break;
								}
								else{
								enemy =null;
								System.out.println("�E�������I�I");
								dungeon.move(player,dx,dy,1);
								dungeon.show();
								break;
								}
							}
							run=(int)((3) * Math.random());
							if(run>0){
								System.out.println("�E�������I�I");
								enemy =null;
								dungeon.move(player,dx,dy,1);
								dungeon.show();
							}
							else{
							System.out.println("�E�����s");
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
						System.out.println("�Z�[�u���܂���");
						break;
					case 'x':
						System.out.println("�I��");
						return;
					default:
						System.out.println("�R�}���h�s��");
					
				}
			}
		}
	}