package com.Marty;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.*;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */
public final class DrawSnakeGamePanel extends JPanel {  ///FindBugs; made class final so cannot be extended

    //TODO ADD WINNING IMAGE AND POSSIBLY GIF

	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	
	private Snake snake;
	private Kibble kibble;
	private Score score;
    private Maze maze;

    private boolean turnOffMaze = false;
    private boolean displayGrid = true;  //user controlled choice to display the game grid

    private Image strawberry;
    private Image snakeBody;
    private Image snakeHead;

    private int winTracker = 0;
	
	DrawSnakeGamePanel(Snake s, Kibble k, Score sc, Maze m){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
        this.maze = m;

        getMazeChoice();

	}

	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
        case 1: {
        	displayInstructions(g);
        	break;
        } 
        case 2 : {
        	displayGame(g);
        	break;
        }
        case 3: {
        	displayGameOver(g);
        	break;
        }
        case 4: {
        	displayGameWon(g);
        	break;
        }
			case 5:{
				displayPause(g);
				break;
			}
			default:  //FINDBUGS add default to switch
				break;
        }

    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);

        String textScore = score.getStringScore();
        String textHighScore = score.getStringHighScore();
        String newHighScore = score.newHighScore();

        g.drawString("SCORE = " + textScore, 150, 250);

        g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
        g.drawString(newHighScore, 150, 400);


        int currentGameLevel = SnakeGame.getGameLevel();

        if (winTracker == 0){
            switch (currentGameLevel) {
                case 1: {
                    SnakeGame.setGameLevel(SnakeGame.LV_TWO);
                    g.drawString("press a key to play again", 150, 350);
                    break;
                }case 2: {
                    SnakeGame.setGameLevel(SnakeGame.LV_THREE);
                    g.drawString("press a key to play again", 150, 350);
                    break;
                }case 3: {
                    SnakeGame.setGameLevel(SnakeGame.LV_FOUR);
                    g.drawString("press a key to play again", 150, 350);
                    break;
                }case 4: {
                    SnakeGame.setGameLevel(SnakeGame.LV_FIVE);
                    g.drawString("press a key to play again", 150, 350);
                    break;
                }case 5: {
                    g.drawString("YOU ARE THE ULTIMATE SNAKE WARRIOR", 150, 350);
                    break;
                }
                default:
                    break;
            }

        }

        System.out.println(currentGameLevel);
        winTracker = 1;
		
	}
	private void displayGameOver(Graphics g) {

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);		
    			
	}

	private void displayGame(Graphics g) {

        displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);

        getMazeChoice();

        if (turnOffMaze == false){
            maze.displayMaze(g);
        }

        int gameLevel = SnakeGame.getGameLevel();
        String levelDisplay = String.valueOf(gameLevel);

        g.setColor(Color.black);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("Level " + levelDisplay,550,550);
        winTracker = 0;
	}


	private void displayGameGrid(Graphics g) {

        //SnakePanelGUI snakePanelGUI = new SnakePanelGUI();

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;

		g.clearRect(0, 0, maxX, maxY);

        if (displayGrid == true){
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }



		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){
			g.drawLine(x, 0, x, maxY);
		}

	}

	private void displayKibble(Graphics g) {

        //Draw the kibble in green
		//g.setColor(Color.GREEN);

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

        try{
            strawberry = getImage("strawberry.png");
            g.drawImage(strawberry,x+1,y+1,SnakeGame.squareSize-2, SnakeGame.squareSize-2,this);
        } catch (NullPointerException npe){
            g.setColor(Color.GREEN);
            g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);
        }
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();

		// g.setColor(Color.LIGHT_GRAY);
		Point head = coordinates.pop();

        int lastPoint = coordinates.size();

        int snakeHeading = snake.getCurrentHeading();
        int pointCounter = 0;

        try {
            if (snakeHeading == 0) {
                snakeHead = getImage("snakeHead.png");
                double degrees = 90.00;
                snakeHead = rotateImage("snakeHead.png", degrees, this);
                g.drawImage(snakeHead, (int) head.getX(), (int) head.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
            } else if (snakeHeading == 1) {
                snakeHead = getImage("snakeHead.png");
                snakeHead = rotateImage("snakeHead.png", 270, this);
                g.drawImage(snakeHead, (int) head.getX(), (int) head.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
            } else if (snakeHeading == 2) {
                snakeHead = getImage("snakeHead.png");
                g.drawImage(snakeHead, (int) head.getX(), (int) head.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
            } else if (snakeHeading == 3) {
                snakeHead = getImage("snakeHead.png");
                snakeHead = flipImage("snakeHead.png", this);
                g.drawImage(snakeHead, (int) head.getX(), (int) head.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
            }
        } catch (NullPointerException npe){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
        }


		//Draw rest of snake in black
		g.setColor(Color.BLACK);
		for (Point p : coordinates) {
            pointCounter = pointCounter + 1;

            try {
                if (snakeHeading == 0) {
                    snakeBody = getImage("snakeBody.png");
                    snakeBody = rotateImage("snakeBody.png", 90, this);
                    g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);

                    if (pointCounter == lastPoint) {
                        snakeBody = getImage("snakeTail.png");
                        snakeBody = rotateImage("snakeTail.png", 90, this);
                        g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
                    }

                } else if (snakeHeading == 1) {
                    snakeBody = getImage("snakeBody.png");
                    snakeBody = rotateImage("snakeBody.png", 270, this);
                    g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);

                    if (pointCounter == lastPoint) {
                        snakeBody = getImage("snakeTail.png");
                        snakeBody = rotateImage("snakeTail.png", 270, this);
                        g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
                    }
                } else if (snakeHeading == 2) {
                    snakeBody = getImage("snakeBody.png");
                    g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);

                    if (pointCounter == lastPoint) {
                        snakeBody = getImage("snakeTail.png");
                        g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
                    }
                } else if (snakeHeading == 3) {
                    snakeBody = getImage("snakeBody.png");
                    snakeBody = flipImage("snakeBody.png", this);
                    g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);

                    if (pointCounter == lastPoint) {
                        snakeBody = getImage("snakeTail.png");
                        snakeBody = flipImage("snakeTail.png", this);
                        g.drawImage(snakeBody, (int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
                    }
                }
            } catch (NullPointerException npe){
                g.setColor(Color.BLACK);
                g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);

            }
		}

	}

	private void displayInstructions(Graphics g) {
        try{
            URL url = new URL("http://g3.std3.ru/G/1/71/97/51974-6a6a0c50128edc7aacd9a223719672d3.gif");
            //Icon icon = new ImageIcon(url);
            ImageIcon icon = new ImageIcon(url);

            icon.paintIcon(this,g,300,100);


        } catch (MalformedURLException mue){
            mue.printStackTrace();
        }

        g.drawString("Press any key to begin!", 100, 200);
        g.drawString("Press q to quit the game",100,300);
		g.drawString("Press p to pause the game", 100, 400);


    	}

	private void displayPause(Graphics g){

		g.drawString("Your game is paused. Press r to Resume.", 100, 200);
	}

    private void getMazeChoice() {

        turnOffMaze = maze.getMazeChoice();
    }

    private Image getImage(String imagePNG){
        Image image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource(imagePNG));
        image = icon.getImage();
        return image;
    }

    private Image rotateImage(String imagePNG, double degrees, ImageObserver o){
        Image image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource(imagePNG));
        image = icon.getImage();

        BufferedImage blankCanvas = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)blankCanvas.getGraphics();
        g2.rotate(Math.toRadians(degrees),icon.getIconWidth()/2,icon.getIconHeight()/2);
        g2.drawImage(image,0,0,o);

        image = blankCanvas;
        return image;

    }

    private Image flipImage(String imagePNG, ImageObserver o){
        Image image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource(imagePNG));
        image = icon.getImage();

        int w = image.getWidth(o);
        int h = image.getHeight(o);

        BufferedImage blankCanvas = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)blankCanvas.getGraphics();
        g2.drawImage(image,0,0,w,h,w,0,0,h,null);


        image = blankCanvas;
        return image;
    }

    public void setDisplayGrid(boolean displayGrid) {
        this.displayGrid = displayGrid;
    }

}

