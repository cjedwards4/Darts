package application;

public class DartsPlayer {
	private int score;
	private String name;
	private boolean game_over=false;
	private boolean is_turn=false;
	public int turn_number=1;
	
	public DartsPlayer(){
		score=0;
		name="player1";
	}
	public DartsPlayer(String str){
		score=0;
		name=str;
	}

	public int getScore(){
		return score;
	}
	public String getName(){
		return name;
	}
	public int getTurnNumber(){
		return turn_number;
	}
	public void setName(String str){
		name=str;
	}
	public boolean isOver(){
		return game_over;
	}
	public boolean isTurn(){
		return is_turn;
	}
	public void incrementTurn(){
		turn_number+=1;
		if((turn_number-1)%3==0)
			switchTurn();
		else if(turn_number==11)
			game_over=true;
	}
	public void switchTurn(){
		is_turn=!is_turn;
	}

	public void addScore(int x){
		score+=x;
	}
	public String toString(){
		return "name: "+name+"\nscore: "+score;
	}
	public static void main(String[] args){
		
	}
	

}
