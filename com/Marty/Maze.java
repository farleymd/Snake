package com.Marty;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Marty on 4/8/2015.
 */
public class Maze extends JPanel {

    //TODO WALL HIT EQUALS END GAME

    //TODO FIGURE OUT SNAKE STARTING POSITION WITHOUT USING SNAKE


    Random rnd = new Random();

    private int gameLevel = SnakeGame.getGameLevel();
    //private Snake snake;

    private int XnumOfSquares;
    private int YnumOfSquares;
    private int squareSize;

    private int xStart;
    private int yStart;

    private int snakeHeadX;
    private int snakeHeadY;

    private boolean turnOffMaze = false;

    private int lineIndex = 0;
    private int leftLIndex = 0;
    private int rightLIndex = 0;
    private int tetrisIndex = 0;

    ArrayList<Integer> wall1 = new ArrayList<Integer>();
    ArrayList<Integer> wall2 = new ArrayList<Integer>();
    ArrayList<Integer> wall3 = new ArrayList<Integer>();
    ArrayList<Integer> wall4 = new ArrayList<Integer>();
    ArrayList<Integer> wall5 = new ArrayList<Integer>();
    ArrayList<Integer> wall6 = new ArrayList<Integer>();

    private int bg;
    private int dg;
    private int blockX;
    private int blockY;

    private int ac; //hopefully get rid of

    ArrayList<Integer> wallBlockX = new ArrayList<Integer>();
    ArrayList<Integer> wallBlockY = new ArrayList<Integer>();


    List<List<Integer>> walls = new ArrayList<List<Integer>>();
    ArrayList<Integer> line = new ArrayList<Integer>();
    ArrayList<Integer> leftL = new ArrayList<Integer>();
    ArrayList<Integer> rightL = new ArrayList<Integer>();
    ArrayList<Integer> tetris = new ArrayList<Integer>();


    public Maze(int maxX, int maxY, int squareSize){
        this.XnumOfSquares = maxX;
        this.YnumOfSquares = maxY;
        this.squareSize = squareSize;

        for (int i = 0; i < gameLevel+1; i++){
            wallBlockX.add(i,0);
        }

        for (int y = 0; y < gameLevel+1; y++){
            wallBlockY.add(y,0);
        }

        this.bg = 0;
        this.dg = 0;
        this.blockX = 0;
        this.blockY = 0;



        whereIsSnake();  //make sure maze doesn't build on top of starting snake

        fillWallArray();

        initialization();

        wallBlockX();
        wallBlockY();

    }

    protected void whereIsSnake(){

        int screenXCenter = (int) XnumOfSquares/2;  //Cast just in case we have an odd number
        int screenYCenter = (int) YnumOfSquares/2;  //Cast just in case we have an odd number

        snakeHeadX = screenXCenter;
        snakeHeadY = screenYCenter;
    }

    protected void initialization(){

        switch(gameLevel){
            case 1:{
                //add two walls
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());

                walls.set(bg++,wallBlockX);
                walls.set(bg++,wallBlockY);
                break;
            }
            case 2:{
                wall1.add(0, buildTheWallX());
                wall1.add(1, buildTheWallY());

                wall2.add(0, buildTheWallX());
                wall2.add(1, buildTheWallY());

                wall3.add(0, buildTheWallX());
                wall3.add(1, buildTheWallY());

                walls.set(bg++,wallBlockX);
                walls.set(bg++,wallBlockY);
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

                walls.set(bg++,wallBlockX);
                walls.set(bg++,wallBlockY);
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

                walls.set(bg++,wallBlockX);
                walls.set(bg++,wallBlockY);
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

                walls.set(bg++,wallBlockX);
                walls.set(bg++,wallBlockY);
                break;
            }
            default:
                break;
        }

    }

    private int buildTheWallX(){
        int xRandom = rnd.nextInt(XnumOfSquares);

        while(xRandom == snakeHeadX || xRandom == snakeHeadX + 1 || xRandom == snakeHeadX + 2 || xRandom == snakeHeadX + 3
                || xRandom == snakeHeadX - 1 || xRandom == snakeHeadX - 2){
            xRandom = rnd.nextInt(XnumOfSquares);
        }

        xStart = xRandom * squareSize;


        wallBlockX.set(blockX, xRandom);
        blockX++;

        return xStart;
    }

    private int buildTheWallY(){
        int yRandom = rnd.nextInt(YnumOfSquares);

        while (yRandom == snakeHeadY || yRandom == snakeHeadY + 1 || yRandom == snakeHeadY + 2 || yRandom == snakeHeadY + 3
                || yRandom == snakeHeadY - 1 || yRandom == snakeHeadY - 2){
            yRandom = rnd.nextInt(YnumOfSquares);
        }

        yStart = yRandom * squareSize;


        wallBlockY.set(blockY, yRandom);
        blockY++;

        return yStart;
    }

    public void displayMaze(Graphics g) {
        g.setColor(Color.red);

        switch (gameLevel) {
            case 1: {
                lineIndex = 0;
                leftLIndex = 0;
                dg = 0;

                buildLine(g, wall1);
                buildLeftL(g, wall2);
                break;
            }
            case 2: {
                lineIndex = 0;
                leftLIndex = 0;
                rightLIndex = 0;
                dg = 0;

                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                break;
            }
            case 3: {
                lineIndex = 0;
                leftLIndex = 0;
                rightLIndex = 0;
                dg = 0;

                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                buildLine(g, wall4);
                break;
            }
            case 4: {
                lineIndex = 0;
                leftLIndex = 0;
                rightLIndex = 0;
                dg = 0;

                buildLine(g, wall1);
                buildLeftL(g, wall2);
                buildRightL(g, wall3);
                buildLine(g, wall4);
                buildRightL(g,wall5);
                break;
            }
            case 5: {
                lineIndex = 0;
                leftLIndex = 0;
                rightLIndex = 0;
                tetrisIndex = 0;
                dg = 0;

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

        line.set(lineIndex++, line1X / squareSize);
        line.set(lineIndex++, line1Y / squareSize);
        line.set(lineIndex++, line2X / squareSize);
        line.set(lineIndex++, line2Y / squareSize);
        line.set(lineIndex++, line3X / squareSize);
        line.set(lineIndex++, line3Y / squareSize);


        walls.set(dg, line);
        dg++;
    }

    private void buildLeftL(Graphics g, ArrayList<Integer> wall){
        int left1X = wall.get(0);
        int left1Y = wall.get(1);

        int left2X = wall.get(0);
        int left2Y = wall.get(1)+30;

        int left3X = wall.get(0);
        int left3Y = wall.get(1) + 60;

        int left4X = wall.get(0);
        int left4Y = wall.get(0)+90;

        int left5X = wall.get(0) + 30;
        int left5Y = wall.get(1)+90;

        int left6X = wall.get(0) + 60;
        int left6Y = wall.get(1)+90;

        g.fillRect(left1X, left1Y, squareSize, squareSize);
        g.fillRect(left2X, left2Y, squareSize, squareSize);
        g.fillRect(left3X, left3Y, squareSize, squareSize);
        g.fillRect(left4X, left4Y, squareSize, squareSize);
        g.fillRect(left5X, left5Y, squareSize, squareSize);
        g.fillRect(left6X, left6Y, squareSize, squareSize);

        //set wall coordinates

        leftL.set(leftLIndex++, left1X / squareSize);
        leftL.set(leftLIndex++, left1Y / squareSize);
        leftL.set(leftLIndex++, left2X/squareSize);
        leftL.set(leftLIndex++, left2Y/squareSize);
        leftL.set(leftLIndex++, left3X/squareSize);
        leftL.set(leftLIndex++, left3Y / squareSize);
        leftL.set(leftLIndex++, left4X / squareSize);
        leftL.set(leftLIndex++, left4Y / squareSize);
        leftL.set(leftLIndex++, left5X / squareSize);
        leftL.set(leftLIndex++, left5Y / squareSize);
        leftL.set(leftLIndex++, left6X / squareSize);
        leftL.set(leftLIndex++, left6Y / squareSize);


        walls.set(dg, leftL);
        dg++;
    }

    private void buildRightL(Graphics g, ArrayList<Integer> wall){
        int right1X = wall.get(0);
        int right1Y = wall.get(1);

        int right2X = wall.get(0);
        int right2Y = wall.get(1)+30;

        int right3X = wall.get(0);
        int right3Y = wall.get(1)+60;

        int right4X = wall.get(0);
        int right4Y = wall.get(1)+90;

        int right5X = wall.get(0)-30;
        int right5Y = wall.get(1)+90;

        int right6X = wall.get(0)-60;
        int right6Y = wall.get(1)+90;

        g.fillRect(right1X, right1Y, squareSize, squareSize);
        g.fillRect(right2X, right2Y, squareSize, squareSize);
        g.fillRect(right3X, right3Y, squareSize, squareSize);
        g.fillRect(right4X, right4Y, squareSize, squareSize);
        g.fillRect(right5X, right5Y, squareSize, squareSize);
        g.fillRect(right6X, right6Y, squareSize, squareSize);

        rightL.set(rightLIndex++, right1X / squareSize);
        rightL.set(rightLIndex++, right1Y / squareSize);
        rightL.set(rightLIndex++, right2X / squareSize);
        rightL.set(rightLIndex++, right2Y / squareSize);
        rightL.set(rightLIndex++, right3X / squareSize);
        rightL.set(rightLIndex++, right3Y / squareSize);
        rightL.set(rightLIndex++, right4X / squareSize);
        rightL.set(rightLIndex++, right4Y / squareSize);
        rightL.set(rightLIndex++, right5X / squareSize);
        rightL.set(rightLIndex++, right5Y / squareSize);
        rightL.set(rightLIndex++, right6X / squareSize);
        rightL.set(rightLIndex++,right6Y/squareSize);

        walls.set(dg, rightL);
        dg++;

    }

    private void buildTetris(Graphics g, ArrayList<Integer> wall){
        int tetris1X = wall.get(0);
        int tetris1Y = wall.get(1);

        int tetris2X = wall.get(0)+30;
        int tetris2Y = wall.get(1);

        int tetris3X = wall.get(0)+60;
        int tetris3Y = wall.get(1);

        int tetris4X = wall.get(0)+90;
        int tetris4Y = wall.get(1);

        int tetris5X = wall.get(0)+60;
        int tetris5Y = wall.get(1)-30;

        int tetris6X = wall.get(0)+30;
        int tetris6Y = wall.get(1)-30;


        g.fillRect(wall.get(0), wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+30, wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+60, wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+90, wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0)+60, wall.get(1)-30, squareSize, squareSize);
        g.fillRect(wall.get(0)+90, wall.get(1)-30, squareSize, squareSize);

        tetris.set(tetrisIndex++, tetris1X / squareSize);
        tetris.set(tetrisIndex++, tetris1Y / squareSize);
        tetris.set(tetrisIndex++, tetris2X / squareSize);
        tetris.set(tetrisIndex++, tetris2Y / squareSize);
        tetris.set(tetrisIndex++, tetris3X / squareSize);
        tetris.set(tetrisIndex++, tetris3Y / squareSize);
        tetris.set(tetrisIndex++, tetris4X / squareSize);
        tetris.set(tetrisIndex++, tetris4Y / squareSize);
        tetris.set(tetrisIndex++, tetris5X / squareSize);
        tetris.set(tetrisIndex++, tetris5Y/squareSize);
        tetris.set(tetrisIndex++, tetris6X / squareSize);
        tetris.set(tetrisIndex++, tetris6Y/squareSize);

        walls.set(dg, tetris);
        dg++;

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


    private void fillWallArray(){
        ArrayList<Integer> wallSegments = new ArrayList<Integer>();

        //there are a possibility of 30 segements in a single wall piece
        for (int i = 0; i < 30; i++){
            wallSegments.add(i,0);
        }

        for (int x = 0; x < gameLevel+1; x++){
            walls.add(x,wallSegments);
        }

        for (int i = 0; i < 6; i++){
            line.add(i, 0);
        }

        for (int i = 0; i < 12; i++){
            leftL.add(i,0);
        }
    }


    public List<List<Integer>> getWalls(){

        return walls;
    }

    public boolean getMazeChoice() {
        return turnOffMaze;
    }



}
