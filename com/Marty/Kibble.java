package com.Marty;

import java.util.ArrayList;
import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * 
	 */
	
	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)
    private ArrayList<Integer> wallBlockX = new ArrayList<Integer>();
    private ArrayList<Integer> wallBlockY = new ArrayList<Integer>();

    private Maze maze;


    public Kibble(Snake s, Maze m){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
        this.maze = m;

        buildWallBlocks();

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

            for (int x = 0; x < wallBlockX.size(); x++){
                for (int y =0; y < wallBlockY.size(); y++){
                    int wallX = wallBlockX.get(x);
                    int wallY = wallBlockY.get(y);

                if (wallX == kibbleX){
                    kibbleX = rng.nextInt(SnakeGame.xSquares);
                }
                    if (wallY == kibbleY){
                        kibbleY = rng.nextInt(SnakeGame.xSquares);
                    }
                }
            }

			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);
		}
	}

    private void buildWallBlocks(){
        wallBlockX = maze.getWallBlockX();
        wallBlockY = maze.getWallBlockY();

    }

	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}



	
}
