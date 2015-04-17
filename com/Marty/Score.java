package com.Marty;

/** Keeps track of, and display the user's score
 * 
 */


public class Score {

	static int score;
	int highScore = 0;
	static int increment;
    static int doubleIncrement;
	
	public Score(){
		score = 0;
		increment = 1;  //how many points for eating a kibble
        doubleIncrement = 2;

	}
	
	public static void resetScore() {
		score = 0;	
	}
	
	public static void increaseScore() {
		
		score = score + increment;
		
	}

    public static void doubleScore(){

        score = score + doubleIncrement;
    }
	
	public int getScore(){
		return score;
	}
	
	//Checks if current score is greater than the current high score. 
	//updates high score and returns true if so.
	
	public boolean gameOver(){
		
		if (score > highScore) {
			highScore = score;
			return true;
		}
		return false;
	}

	//These methods are useful for displaying score at the end of the game
	
	public String getStringScore() {
		return Integer.toString(score);
	}

	public String newHighScore() {
		
		if (score > highScore) {
			highScore = score;
			return "New High Score!!";
		} else {
			return "";
	}
	}

	public String getStringHighScore() {
		return Integer.toString(highScore);
	}
	
}

