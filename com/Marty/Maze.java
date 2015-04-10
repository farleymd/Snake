package com.Marty;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Marty on 4/8/2015.
 */
public class Maze extends JPanel {

    //TODO WALL HIT EQUALS END GAME
    

    Random rnd = new Random();

    private int gameLevel = SnakeGame.getGameLevel();
    private Snake snake;

    private int XnumOfSquares;
    private int YnumOfSquares;
    private int squareSize;

    private int xStart;
    private int yStart;

    private int snakeHeadX;
    private int snakeHeadY;

    ArrayList<Integer> wall1 = new ArrayList<Integer>();
    ArrayList<Integer> wall2 = new ArrayList<Integer>();
    ArrayList<Integer> wall3 = new ArrayList<Integer>();
    ArrayList<Integer> wall4 = new ArrayList<Integer>();
    ArrayList<Integer> wall5 = new ArrayList<Integer>();
    ArrayList<Integer> wall6 = new ArrayList<Integer>();

    ArrayList<Integer> wallBlockX = new ArrayList<Integer>();
    ArrayList<Integer> wallBlockY = new ArrayList<Integer>();

    //ArrayList<ArrayList<Integer>> wallCoordinates = new ArrayList<ArrayList<Integer>>();
    HashMap<Integer,Integer> hash = new HashMap<Integer, Integer>();

    public Maze(Snake snake, int maxX, int maxY, int squareSize){
        this.XnumOfSquares = maxX;
        this.YnumOfSquares = maxY;
        this.squareSize = squareSize;
        this.snake = snake;

        whereIsSnake();  //make sure maze doesn't build on top of starting snake

        initialization();

        wallBlockX();
        wallBlockY();
    }

    protected void whereIsSnake(){
        snakeHeadX = snake.getSnakeHeadX();
        snakeHeadY = snake.getSnakeHeadY();
    }

    protected void initialization(){

        switch(gameLevel){
            case 1:{
                //add two walls
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());
                break;
            }
            case 2:{
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());

                wall3.add(0, buildTheWallX());
                wall3.add(1, buildTheWallY());
                break;
            }
            case 3:{
                //add 4 walls
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());

                wall3.add(0, buildTheWallX());
                wall3.add(1, buildTheWallY());

                wall4.add(0, buildTheWallX());
                wall4.add(1, buildTheWallY());
                break;
            }
            case 4:{
                //add 5 walls
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());

                wall3.add(0, buildTheWallX());
                wall3.add(1, buildTheWallY());

                wall4.add(0, buildTheWallX());
                wall4.add(1, buildTheWallY());

                wall5.add(0, buildTheWallX());
                wall5.add(1, buildTheWallY());
                break;
            }
            case 5: {
                //add 6 walls
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());

                wall3.add(0, buildTheWallX());
                wall3.add(1, buildTheWallY());

                wall4.add(0, buildTheWallX());
                wall4.add(1, buildTheWallY());

                wall5.add(0, buildTheWallX());
                wall5.add(1, buildTheWallY());

                wall6.add(0, buildTheWallX());
                wall6.add(1, buildTheWallY());
                break;
            }
            default:
                break;
        }

    }

    private int buildTheWallX(){
        int xRandom = rnd.nextInt(XnumOfSquares);

        while(xRandom == snakeHeadX || xRandom == snakeHeadX + 1 || xRandom == snakeHeadX + 2 || xRandom == snakeHeadX + 3){
            xRandom = rnd.nextInt(XnumOfSquares);
        }

        xStart = xRandom * squareSize;

        return xStart;
    }

    private int buildTheWallY(){
        int yRandom = rnd.nextInt(YnumOfSquares);

        while (yRandom == snakeHeadY || yRandom == snakeHeadY + 1 || yRandom == snakeHeadY + 2 || yRandom == snakeHeadY + 3){
            yRandom = rnd.nextInt(YnumOfSquares);
        }

        yStart = yRandom * squareSize;
        return yStart;
    }

    public void displayMaze(Graphics g) {
        g.setColor(Color.red);

        switch (gameLevel) {
            case 1: {
                buildLine(g, wall1);
                buildLeftL(g, wall2);
                break;
            }
            case 2: {
                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                break;
            }
            case 3: {
                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                buildLine(g, wall4);
                break;
            }
            case 4: {
                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                buildLine(g, wall4);
                buildRightL(g,wall5);
                break;
            }
            case 5: {
                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                buildLine(g, wall4);
                buildRightL(g,wall5);
                buildTetris(g, wall6);
                break;
            }
            default:
                break;

        }
    }

    private void buildLine(Graphics g, ArrayList<Integer> wall){
        int line1X = wall.get(0);
        int line1Y = wall.get(1);

        int line2X = wall.get(0);
        int line2Y = wall.get(1) + 30;

        int line3X = wall.get(0);
        int line3Y = wall.get(1) + 60;

        //build the line
        g.fillRect(line1X, line1Y, squareSize, squareSize);
        g.fillRect(line2X, line2Y, squareSize, squareSize);
        g.fillRect(line3X, line3Y, squareSize, squareSize);

        //store the wall coordinates

        hash.put(line1X, line1Y);
        hash.put(line2X, line2Y);
        hash.put(line3X,line3Y);
    }

    private void buildLeftL(Graphics g, ArrayList<Integer> wall){
        g.fillRect(wall.get(0), wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1)+30, squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1)+60, squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1)+90, squareSize, squareSize);
        g.fillRect(wall.get(0)+30, wall.get(1)+90, squareSize, squareSize);
        g.fillRect(wall.get(0)+60, wall.get(1)+90, squareSize, squareSize);
    }

    private void buildRightL(Graphics g, ArrayList<Integer> wall){
        g.fillRect(wall.get(0), wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1)+30, squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1)+60, squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1)+90, squareSize, squareSize);
        g.fillRect(wall.get(0)-30, wall.get(1)+90, squareSize, squareSize);
        g.fillRect(wall.get(0)-60, wall.get(1)+90, squareSize, squareSize);
    }

    private void buildTetris(Graphics g, ArrayList<Integer> wall){
        g.fillRect(wall.get(0), wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+30, wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+60, wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+90, wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+60, wall.get(1)-30, squareSize, squareSize);
        g.fillRect(wall.get(0)+90, wall.get(1)-30, squareSize, squareSize);

    }

    private void wallBlockX(){

        switch(gameLevel){
            case 1:{
                wallBlockX.add(0, (wall1.get(0)/squareSize));  //kibble is placed based on squares
                wallBlockX.add(1, (wall2.get(0)/squareSize));
                break;
            }
            case 2: {
                wallBlockX.add(0, (wall1.get(0)/squareSize));
                wallBlockX.add(1, (wall2.get(0)/squareSize));
                wallBlockX.add(2, (wall3.get(0)/squareSize));
                break;
            }case 3: {
                wallBlockX.add(0, (wall1.get(0)/squareSize));
                wallBlockX.add(1, (wall2.get(0)/squareSize));
                wallBlockX.add(2, (wall3.get(0)/squareSize));
                wallBlockX.add(3, (wall4.get(0)/squareSize));
                break;
            }case 4: {
                wallBlockX.add(0, (wall1.get(0)/squareSize));
                wallBlockX.add(1, (wall2.get(0)/squareSize));
                wallBlockX.add(2, (wall3.get(0)/squareSize));
                wallBlockX.add(3, (wall4.get(0)/squareSize));
                wallBlockX.add(4, (wall5.get(0)/squareSize));
                break;
            }case 5: {
                wallBlockX.add(0, (wall1.get(0)/squareSize));
                wallBlockX.add(1, (wall2.get(0)/squareSize));
                wallBlockX.add(2, (wall3.get(0)/squareSize));
                wallBlockX.add(3, (wall4.get(0)/squareSize));
                wallBlockX.add(4, (wall5.get(0)/squareSize));
                wallBlockX.add(5, (wall6.get(0)/squareSize));
                break;
            }
            default:
                break;
            }
    }

    private void wallBlockY(){
        switch(gameLevel){
            case 1:{
                wallBlockY.add(0, (wall1.get(1)/squareSize));  //kibble is placed based on squares
                wallBlockY.add(1, (wall2.get(1)/squareSize));
                break;
            }
            case 2: {
                wallBlockY.add(0, (wall1.get(1)/squareSize));
                wallBlockY.add(1, (wall2.get(1)/squareSize));
                wallBlockY.add(2, (wall3.get(1)/squareSize));
                break;
            }case 3: {
                wallBlockY.add(0, (wall1.get(1)/squareSize));
                wallBlockY.add(1, (wall2.get(1)/squareSize));
                wallBlockY.add(2, (wall3.get(1)/squareSize));
                wallBlockY.add(3, (wall4.get(1)/squareSize));
                break;
            }case 4: {
                wallBlockY.add(0, (wall1.get(1)/squareSize));
                wallBlockY.add(1, (wall2.get(1)/squareSize));
                wallBlockY.add(2, (wall3.get(1)/squareSize));
                wallBlockY.add(3, (wall4.get(1)/squareSize));
                wallBlockY.add(4, (wall5.get(1)/squareSize));
                break;
            }case 5: {
                wallBlockY.add(0, (wall1.get(1)/squareSize));
                wallBlockY.add(1, (wall2.get(1)/squareSize));
                wallBlockY.add(2, (wall3.get(1)/squareSize));
                wallBlockY.add(3, (wall4.get(1)/squareSize));
                wallBlockY.add(4, (wall5.get(1)/squareSize));
                wallBlockY.add(5, (wall6.get(1)/squareSize));
                break;
            }
            default:
                break;
        }
    }

    public ArrayList<Integer> getWallBlockX(){

        return wallBlockX;
    }

    public ArrayList<Integer> getWallBlockY(){

        return wallBlockY;
    }



}
