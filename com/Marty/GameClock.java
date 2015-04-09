package com.Marty;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	Score score;
	DrawSnakeGamePanel gamePanel;
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
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
					kibble.moveKibble(snake);

                    boolean warpWallsOff = snake.getWarpChoice();

                    //if warp walls are not on, player gets double points
                    if (warpWallsOff == true){
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
