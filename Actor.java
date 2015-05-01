//10A3804 カンルイヨン
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
		System.out.println(name + "[Lvl=" + lvl + ",EXP=" + numExp + "/" + maxExp + ",現HP=" + hp + ",最大HP=" + max_hp + ",攻撃力=" + strength + ",薬数=" + numPotion + "]");
	}
	int attack(Enemy enemy){
		elvl=enemy.lvl;
		estr=enemy.strength;
		System.out.println(name + "の攻撃!!");
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
		System.out.println("敵は死にました");
		getExp();
		Lvlup();
		return 1;
	}
	void getPotion(){
		numPotion=numPotion+1;
	}
	void usePotion(){
		if(numPotion==0){
			System.out.println("回復薬は持っていません");
			return;
		}
		hp+=max_hp/3;
		if(hp>=max_hp){
			hp=max_hp;
		}
		numPotion-=1;
		System.out.println("HPが回復しました");
	}
	void Lvlup(){
		if(numExp>=maxExp){
			numExp-=maxExp;
			lvl+=1;
			max_hp+=50;
			hp+=50;
			strength+=30;
			maxExp+=100;
			System.out.println("レベルアープ！！！");
			System.out.println("現在"+lvl+"LVLになりました!!!!");
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
			name="妖怪";
			hp = max_hp = (int) ((lvl*20) * Math.random())+ (lvl+30);
			strength = (int) ((lvl*15) * Math.random())+ (lvl*20);
		}
	void Boss(){
			name="魔王";
			
	}
	void show(){
			System.out.println(name + "[Lvl=" + lvl + ",現HP=" + hp + ",最大HP=" + max_hp + ",攻撃力=" + strength + "]");
	}
	int attack(Player player){
		System.out.println("敵の攻撃!!");
		try{
			Thread.sleep(1000);
		}
		catch (Exception e) {
			return 0;
		}
		int hit = (int) (strength * Math.random());
		player.hp = player.hp - hit;
		if(player.hp > 0)	return 0;
		System.out.println(player.name + "は死にました");
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
		System.out.println("ここには移動できません");
	}
}
class Potion extends Actor{
	Potion(){}
	void show(){
		System.out.println("薬[HPを最大まで回復させる]");
	}
}
