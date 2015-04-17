package com.Marty;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	DrawSnakeGamePanel gamePanel;
    Maze maze;
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel, Maze maze){
		this.snake = snake;
		this.kibble = kibble;
		this.gamePanel = gamePanel;
        this.maze = maze;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
						
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				//
				snake.moveSnake();
				if (snake.didEatKibble(kibble) == true) {		
					//tell kibble to update
                    snake.wonGame();
					kibble.moveKibble(snake);

                    boolean turnOffWarp = snake.getWarpChoice();
                    boolean turnOffMaze = maze.getMazeChoice();

                    //if warp walls are not on, player gets double points
                    if (turnOffWarp == true){
                        Score.doubleScore();
                    } else {
                        Score.increaseScore();
                    }

                    //if maze is on, player gets double points
                    if (turnOffMaze == false){
                        Score.doubleScore();
                    } else {
                        Score.increaseScore();
                    }

				}
				break;
			}
			case SnakeGame.GAME_OVER: {
				//fall through    //FINDBUGS code duplication
			}
			case SnakeGame.GAME_WON: {
				//fall through   //FINDBUGS code duplication
			}
			case SnakeGame.PAUSE_GAME:{
				this.cancel();  //stop the timer while game is paused
				break;
			}
			default:
				break;
		}
				
		gamePanel.repaint();		//In every circumstance, must update screen
		
	}
}
