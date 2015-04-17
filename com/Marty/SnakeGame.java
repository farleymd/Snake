package com.Marty;

import java.awt.event.*;
import java.util.Timer;

import javax.swing.*;


public class SnakeGame {

    //TODO CLEAN UP MENU, INCLUDE INSTRUCTIONS

	public final static int xPixelMaxDimension = 601;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public final static int yPixelMaxDimension = 601;

	public static int xSquares ;
	public static int ySquares ;

	public final static int squareSize = 30;

	protected static Snake snake ;

	protected static Kibble kibble;

	protected static Score score;

    protected static Maze maze;


	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;
	static final int PAUSE_GAME = 5;
	 //The values are not important. The important thing is to use the constants
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as SnakeGame.GAME_OVER

    static final int LV_ONE = 1;
    static final int LV_TWO = 2;
    static final int LV_THREE = 3;
    static final int LV_FOUR = 4;
    static final int LV_FIVE = 5;

	//TODO INITIALIZE GAME WINING PARAMETERS

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

    //set to lv_five for testing, set to lv_one for release
    private static int gameLevel = LV_FIVE;

    protected static long clockInterval = 500; //controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1  second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	private static void createAndShowGUI() {
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menu = setJMenuBar();

        snakeFrame.setJMenuBar(menu);

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score, maze);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake, maze, score));

		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);
	}

	private static void initializeGame() {
		//set up score, snake and first kibble
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

        maze = new Maze (xSquares,ySquares,squareSize);  //maze must be initiated before kibble and snake
        snake = new Snake(xSquares, ySquares, squareSize, maze, snakePanel);
		kibble = new Kibble(snake, maze);
		score = new Score();


		gameStage = BEFORE_GAME;
	}

	protected static void newGame() {
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel, maze);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
	}

    public static void setGameLevel(int gameLevel) {
        SnakeGame.gameLevel = gameLevel;
    }

    private static void setClockInterval(long clockInterval) {
        SnakeGame.clockInterval = clockInterval;
    }

	public static void resumeGame(){
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake,kibble,score,snakePanel, maze);
		timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				createAndShowGUI();
			}
		});
	}

    public static int getGameLevel(){
        return gameLevel;
    }

	public static int getGameStage() {
		return gameStage;
	}

	public static boolean gameEnded() {
		if (gameStage == GAME_OVER || gameStage == GAME_WON){
			return true;
		}
		return false;
	}

	public static void setGameStage(int gameStage) {

        SnakeGame.gameStage = gameStage;
	}

	public static void closeGame(){
		snakeFrame.dispatchEvent(new WindowEvent(snakeFrame, WindowEvent.WINDOW_CLOSING));
	}

    public static JMenuBar setJMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        jMenuBar.add(gameMenu);

        final JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME){
                    //Start the game
                    setGameStage(SnakeGame.DURING_GAME);
                    newGame();
                    snakePanel.repaint();
                }
            }
        });
        gameMenu.add(newGame);

        JMenuItem quitGame = new JMenuItem("Quit (Q)");
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeGame();
            }
        });
        gameMenu.add(quitGame);

        JMenu warpWallsMenu = new JMenu("Warp Walls");
        ButtonGroup warpGroup = new ButtonGroup();
        final JRadioButtonMenuItem warpOn = new JRadioButtonMenuItem("On");
        warpOn.setSelected(true);
        warpOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setTurnOffWarp(false);

            }
        });

        final JRadioButtonMenuItem warpOff = new JRadioButtonMenuItem("Off");
        warpOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                snake.setTurnOffWarp(true);
            }
        });
        warpGroup.add(warpOn);
        warpGroup.add(warpOff);
        warpWallsMenu.add(warpOn);
        warpWallsMenu.add(warpOff);

        warpOn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED){
                    //if the button is selected, de-select the other button
                    snake.setTurnOffWarp(false);
                    warpOff.setSelected(false);
                }
            }
        });

        warpOff.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED){
                    snake.setTurnOffWarp(true);
                    warpOn.setSelected(false);
                }
            }
        });

        //Add the Warp Wall choice to the main menu
        jMenuBar.add(warpWallsMenu);

        JMenu mazeMenu = new JMenu("Maze");
        ButtonGroup mazeGroup = new ButtonGroup();
        final JRadioButtonMenuItem mazeOn = new JRadioButtonMenuItem("On");
        mazeOn.setSelected(true);
        warpOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze.setTurnOffMaze(false);
            }
        });
        mazeGroup.add(mazeOn);
        mazeMenu.add(mazeOn);

        final JRadioButtonMenuItem mazeOff = new JRadioButtonMenuItem("Off");
        //level one will be set as the default
        mazeOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maze.setTurnOffMaze(true);
                mazeOn.setSelected(false);
            }
        });

        mazeOn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state==ItemEvent.SELECTED){
                    maze.setTurnOffMaze(false);
                    mazeOff.setSelected(false);
                }
            }
        });

        mazeOff.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state==ItemEvent.SELECTED){
                    maze.setTurnOffMaze(true);
                    mazeOn.setSelected(false);
                }
            }
        });

        mazeGroup.add(mazeOff);
        mazeMenu.add(mazeOff);

        //Add the Maze choice menu to the main menu
        jMenuBar.add(mazeMenu);

        JMenu gridMenu = new JMenu("Grid");
        ButtonGroup gridGroup = new ButtonGroup();
        final JRadioButtonMenuItem gridOn = new JRadioButtonMenuItem("On");
        gridOn.setSelected(true);

        gridGroup.add(gridOn);
        gridMenu.add(gridOn);

        final JRadioButtonMenuItem gridOff = new JRadioButtonMenuItem("Off");
        gridGroup.add(gridOff);
        gridMenu.add(gridOff);

        gridOn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakePanel.setDisplayGrid(true);
                gridOff.setSelected(false);
            }
        });

        gridOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakePanel.setDisplayGrid(false);
                gridOn.setSelected(false);
            }
        });

        jMenuBar.add(gridMenu);

        JMenu gameLevel = new JMenu("Levels");
        ButtonGroup levelGroup = new ButtonGroup();
        JRadioButtonMenuItem lv_one = new JRadioButtonMenuItem("Level One");
        //level one will be set as the default
        lv_one.setSelected(true);
        lv_one.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setGameLevel(LV_ONE);
            }
        });
        levelGroup.add(lv_one);
        gameLevel.add(lv_one);

        JRadioButtonMenuItem lv_two = new JRadioButtonMenuItem("Level Two");
        //level one will be set as the default
        lv_two.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setGameLevel(LV_TWO);
            }
        });
        levelGroup.add(lv_two);
        gameLevel.add(lv_two);

        JRadioButtonMenuItem lv_three = new JRadioButtonMenuItem("Level Three");
        //level one will be set as the default
        lv_three.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setGameLevel(LV_THREE);
            }
        });
        levelGroup.add(lv_three);
        gameLevel.add(lv_three);

        JRadioButtonMenuItem lv_four = new JRadioButtonMenuItem("Level Four");
        //level one will be set as the default
        lv_four.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setGameLevel(LV_FOUR);
            }
        });
        levelGroup.add(lv_four);
        gameLevel.add(lv_four);

        JRadioButtonMenuItem lv_five = new JRadioButtonMenuItem("Level Five");
        //level one will be set as the default
        lv_five.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setGameLevel(LV_FIVE);
            }
        });
        levelGroup.add(lv_five);
        gameLevel.add(lv_five);

        jMenuBar.add(gameLevel);


        JMenu speed = new JMenu("Speed");
        ButtonGroup speedGroup = new ButtonGroup();
        JRadioButtonMenuItem slowItem = new JRadioButtonMenuItem("Slow");
        slowItem.setSelected(true);
        slowItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setClockInterval(500);
            }
        });
        speedGroup.add(slowItem);
        speed.add(slowItem);

        JRadioButtonMenuItem mediumItem = new JRadioButtonMenuItem("Medium");
        mediumItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setClockInterval(300);
            }
        });
        speedGroup.add(mediumItem);
        speed.add(mediumItem);

        JRadioButtonMenuItem fastItem = new JRadioButtonMenuItem("Fast");
        fastItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setClockInterval(200);
            }
        });
        speedGroup.add(fastItem);
        speed.add(fastItem);

        jMenuBar.add(speed);

        JMenu aboutMenu = new JMenu("About");
        jMenuBar.add(aboutMenu);

        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Use the UP, DOWN, LEFT and RIGHT \n" +
                        "arrow keys to control the snake. \n" +
                        "Eat the fruit that appears on the screen \n" +
                        "without hitting your tail. \n" +
                        "Turn warp walls and mazes on for an extra challenge! \n" +
                        "To play the game: press any key \n" +
                        "To pause the game: press p \n" +
                        "To quit the game: press q");
            }
        });
        aboutMenu.add(instructions);



        JMenuItem authors = new JMenuItem("Authors");
        authors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Authors: Marty Farley, Clara James \n Assistance " +
                        "from Andre Degel and Tyler Chester \n" +
                        "Version: Snake 2.0 \n" +
                        "Date Released: April 17th, 2015 \n" +"");
            }
        });
        aboutMenu.add(authors);

        return jMenuBar;
    }


}
