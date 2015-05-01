//10A3804 �J�����C����
//Actor.java

import java.io.*;
abstract class Actor implements Serializable{
	abstract void show();
}

class Player extends Actor{
	String name;
	int hp,max_hp,strength,numPotion,lvl,numExp,maxExp,elvl,estr;
	
	Player(String name){
		lvl=1;
		this.name = name;
		hp = 1000000;
		max_hp = 10000000;
		strength = 10000000;
		numPotion= 0;
		numExp=0;
		maxExp=100;
	}
	void show(){
		System.out.println(name + "[Lvl=" + lvl + ",EXP=" + numExp + "/" + maxExp + ",��HP=" + hp + ",�ő�HP=" + max_hp + ",�U����=" + strength + ",��=" + numPotion + "]");
	}
	int attack(Enemy enemy){
		elvl=enemy.lvl;
		estr=enemy.strength;
		System.out.println(name + "�̍U��!!");
		try{
			Thread.sleep(1000);
		}
		catch (Exception e){
			return 0;
		}
		int hit=(int) (strength * Math.random());
		enemy.hp = enemy.hp - hit;
		if (enemy.hp > 0) {
		return 0;
		}
		System.out.println("�G�͎��ɂ܂���");
		getExp();
		Lvlup();
		return 1;
	}
	void getPotion(){
		numPotion=numPotion+1;
	}
	void usePotion(){
		if(numPotion==0){
			System.out.println("�񕜖�͎����Ă��܂���");
			return;
		}
		hp+=max_hp/3;
		if(hp>=max_hp){
			hp=max_hp;
		}
		numPotion-=1;
		System.out.println("HP���񕜂��܂���");
	}
	void Lvlup(){
		if(numExp>=maxExp){
			numExp-=maxExp;
			lvl+=1;
			max_hp+=50;
			hp+=50;
			strength+=30;
			maxExp+=100;
			System.out.println("���x���A�[�v�I�I�I");
			System.out.println("����"+lvl+"LVL�ɂȂ�܂���!!!!");
		}
	}
	void getExp(){
		if(elvl>=lvl){
			numExp+=elvl* 2+50;
		}else{
			numExp+=elvl*1.5+50;
		}
	}
}
class Enemy extends Actor{
	int hp,max_hp,strength,lvl;
	String name;
	Enemy(){
			name="�d��";
			hp = max_hp = (int) ((lvl*20) * Math.random())+ (lvl+30);
			strength = (int) ((lvl*15) * Math.random())+ (lvl*20);
		}
	void Boss(){
			name="����";
			
	}
	void show(){
			System.out.println(name + "[Lvl=" + lvl + ",��HP=" + hp + ",�ő�HP=" + max_hp + ",�U����=" + strength + "]");
	}
	int attack(Player player){
		System.out.println("�G�̍U��!!");
		try{
			Thread.sleep(1000);
		}
		catch (Exception e) {
			return 0;
		}
		int hit = (int) (strength * Math.random());
		player.hp = player.hp - hit;
		if(player.hp > 0)	return 0;
		System.out.println(player.name + "�͎��ɂ܂���");
		return 1;
	}
}

class Gate extends Actor{
	Gate(){ }
	void show(){}
}
class NoMove extends Actor{
	NoMove() {}
	void show(){
		System.out.println("�����ɂ͈ړ��ł��܂���");
	}
}
class Potion extends Actor{
	Potion(){}
	void show(){
		System.out.println("��[HP���ő�܂ŉ񕜂�����]");
	}
}
