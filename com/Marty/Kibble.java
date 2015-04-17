package com.Marty;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * 
	 */
	
	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)

    List<List<Integer>> walls = new ArrayList<List<Integer>>();

    private Maze maze;

    private boolean turnMazeOff;


    public Kibble(Snake s, Maze m){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
        this.maze = m;

        getMazeChoice();

        checkWalls();

        moveKibble(s);



	}

	protected void moveKibble(Snake s){
		
		Random rng = new Random();
		boolean kibbleInSnake = true;
		while (kibbleInSnake == true) {
			//Generate random kibble location
			kibbleX = rng.nextInt(SnakeGame.xSquares);
			kibbleY = rng.nextInt(SnakeGame.ySquares);

            //check if kibble X or Y intersects with walls

            //TODO KIBBLE STILL INTERACTING WITH WALLS

            if (turnMazeOff == false){
                //TODO if random number generates to wall coordinate previously in the array, BUG

                for (int i = 0; i < walls.size(); i++){
                    for (int j = 0; j < walls.get(i).size(); j++){
                        int tester = walls.get(i).get(j);

                        while (tester == kibbleX || tester == kibbleY){
                            if (tester == kibbleX){
                            kibbleX = rng.nextInt(SnakeGame.xSquares);
                        } else if (tester == kibbleY){
                            kibbleY = rng.nextInt(SnakeGame.xSquares);

                        }

                        }
                    }

                }
            }

			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);

            checkWalls();
		}
	}

    private void getMazeChoice(){

        turnMazeOff = maze.getMazeChoice();

    }

    private void checkWalls(){
        walls = maze.getWalls();
    }

	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}



	
}
