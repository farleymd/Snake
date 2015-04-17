package com.Marty;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Marty on 4/8/2015.
 */
public final class Maze extends JPanel {  ///FindBugs; made class final so cannot be extended

    /**
     * See Code Documentation SIS-3 for detailed initialization process of Maze - Maze Wall Coordinates Array-
     * Passed to Kibble and Snake - Snake Game Panel called to draw -Display the maze, which rebuilds the
     * Wall Coordinates array
     */


    Random rnd = new Random();

    private int gameLevel = SnakeGame.getGameLevel();

    private int xNumOfSquares;
    private int yNumOfSquares;
    private int squareSize;

    private int xStart, yStart;

    private int snakeHeadX, snakeHeadY;

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

    private Image wallImage;


    ArrayList<Integer> wallBlockX = new ArrayList<Integer>();  //arrays used for before_game initiation
    ArrayList<Integer> wallBlockY = new ArrayList<Integer>();


    List<List<Integer>> walls = new ArrayList<List<Integer>>();  //array holding the Maze wall coordinates

    ArrayList<Integer> line = new ArrayList<Integer>();  //arrays for each of the maze types
    ArrayList<Integer> leftL = new ArrayList<Integer>();
    ArrayList<Integer> rightL = new ArrayList<Integer>();
    ArrayList<Integer> tetris = new ArrayList<Integer>();


    public Maze(int maxX, int maxY, int squareSize){
        this.xNumOfSquares = maxX;
        this.yNumOfSquares = maxY;
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

        fillWallArray();  //initiate the array used to hold wall coordinates

        initialization();  //finding the starting points for each maze piece

        wallBlockX();
        wallBlockY();

    }

    protected void whereIsSnake(){

        int screenXCenter = (int) xNumOfSquares/2;  //Cast just in case we have an odd number
        int screenYCenter = (int) yNumOfSquares/2;  //Cast just in case we have an odd number

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

    //Method used to find the X starting point of a wall
    private int buildTheWallX(){

        int xRandom = rnd.nextInt(xNumOfSquares);

        while(xRandom == snakeHeadX || xRandom == snakeHeadX + 1 || xRandom == snakeHeadX + 2 || xRandom == snakeHeadX + 3
                || xRandom == snakeHeadX - 1 || xRandom == snakeHeadX - 2){
            xRandom = rnd.nextInt(xNumOfSquares);
        }

        xStart = xRandom * squareSize;


        wallBlockX.set(blockX, xRandom);
        blockX++;

        return xStart;
    }

    //Method used to find the Y starting point of a wall
    private int buildTheWallY(){
        int yRandom = rnd.nextInt(yNumOfSquares);

        while (yRandom == snakeHeadY || yRandom == snakeHeadY + 1 || yRandom == snakeHeadY + 2 || yRandom == snakeHeadY + 3
                || yRandom == snakeHeadY - 1 || yRandom == snakeHeadY - 2){
            yRandom = rnd.nextInt(yNumOfSquares);
        }

        yStart = yRandom * squareSize;


        wallBlockY.set(blockY, yRandom);
        blockY++;

        return yStart;
    }

    public void displayMaze(Graphics g) {
        g.setColor(Color.red);

        gameLevel = SnakeGame.getGameLevel();


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

        //if picture doesn't exist, place rectangles instead
        try{
            wallImage = getImage("wall.png");
            g.drawImage(wallImage,line1X,line1Y,SnakeGame.squareSize, SnakeGame.squareSize,this);
            g.drawImage(wallImage,line2X,line2Y,SnakeGame.squareSize, SnakeGame.squareSize,this);
            g.drawImage(wallImage,line3X,line3Y,SnakeGame.squareSize, SnakeGame.squareSize,this);
        } catch (NullPointerException npe){
            g.fillRect(line1X, line1Y, SnakeGame.squareSize, SnakeGame.squareSize);
            g.fillRect(line2X, line2Y, SnakeGame.squareSize, SnakeGame.squareSize);
            g.fillRect(line3X, line3Y, SnakeGame.squareSize, SnakeGame.squareSize);
        }


        //store the wall coordinates

        line.set(lineIndex++, line1X / squareSize);
        line.set(lineIndex++, line1Y / squareSize);
        line.set(lineIndex++, line2X / squareSize);
        line.set(lineIndex++, line2Y / squareSize);
        line.set(lineIndex++, line3X / squareSize);
        line.set(lineIndex++, line3Y / squareSize);


        //add the wall coordinates to the Maze coordinate array
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
        int left4Y = wall.get(1)+90;

        int left5X = wall.get(0) + 30;
        int left5Y = wall.get(1)+90;

        int left6X = wall.get(0) + 60;
        int left6Y = wall.get(1)+90;

        try{
            wallImage = getImage("wall.png");
            g.drawImage(wallImage, left1X, left1Y, squareSize, squareSize, this);
            g.drawImage(wallImage, left2X, left2Y, squareSize, squareSize, this);
            g.drawImage(wallImage, left3X, left3Y, squareSize, squareSize, this);
            g.drawImage(wallImage, left4X, left4Y, squareSize, squareSize, this);
            g.drawImage(wallImage, left5X, left5Y, squareSize, squareSize, this);
            g.drawImage(wallImage, left6X, left6Y, squareSize, squareSize, this);
        } catch (NullPointerException npe){
            g.fillRect(left1X, left1Y, squareSize, squareSize);
            g.fillRect(left2X, left2Y, squareSize, squareSize);
            g.fillRect(left3X, left3Y, squareSize, squareSize);
            g.fillRect(left4X, left4Y, squareSize, squareSize);
            g.fillRect(left5X, left5Y, squareSize, squareSize);
            g.fillRect(left6X, left6Y, squareSize, squareSize);
        }

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

        try{
            wallImage = getImage("wall.png");
            g.drawImage(wallImage, right1X, right1Y, squareSize, squareSize, this);
            g.drawImage(wallImage, right2X, right2Y, squareSize, squareSize, this);
            g.drawImage(wallImage, right3X, right3Y, squareSize, squareSize, this);
            g.drawImage(wallImage, right4X, right4Y, squareSize, squareSize, this);
            g.drawImage(wallImage, right5X, right5Y, squareSize, squareSize, this);
            g.drawImage(wallImage, right6X, right6Y, squareSize, squareSize, this);

        } catch (NullPointerException npe){

            g.fillRect(right1X, right1Y, squareSize, squareSize);
            g.fillRect(right2X, right2Y, squareSize, squareSize);
            g.fillRect(right3X, right3Y, squareSize, squareSize);
            g.fillRect(right4X, right4Y, squareSize, squareSize);
            g.fillRect(right5X, right5Y, squareSize, squareSize);
            g.fillRect(right6X, right6Y, squareSize, squareSize);

        }

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

        try{
            wallImage = getImage("wall.png");

            g.drawImage(wallImage,tetris1X, tetris1Y, squareSize, squareSize,this);
            g.drawImage(wallImage,tetris2X, tetris2Y, squareSize, squareSize,this);
            g.drawImage(wallImage,tetris3X, tetris3Y, squareSize, squareSize,this);
            g.drawImage(wallImage,tetris4X, tetris4Y, squareSize, squareSize,this);
            g.drawImage(wallImage,tetris5X, tetris5Y, squareSize, squareSize,this);
            g.drawImage(wallImage,tetris6X, tetris6Y, squareSize, squareSize,this);

        } catch (NullPointerException npe){

            g.fillRect(tetris1X, tetris1Y, squareSize, squareSize);
            g.fillRect(tetris2X, tetris2Y, squareSize, squareSize);
            g.fillRect(tetris3X, tetris3Y, squareSize, squareSize);
            g.fillRect(tetris4X, tetris4Y, squareSize, squareSize);
            g.fillRect(tetris5X, tetris5Y, squareSize, squareSize);
            g.fillRect(tetris6X, tetris6Y, squareSize, squareSize);
        }

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

        for (int l = 0; l < 12; l++){
            line.add(l, 0);
        }

        for (int ll = 0; ll < 24; ll++){
            leftL.add(ll,0);
        }

        for (int r = 0; r < 24; r++){
            rightL.add(r,0);
        }

        for (int t = 0; t < 24; t++){
            tetris.add(t,0);
        }
    }

    public List<List<Integer>> getWalls(){

        return walls;
    }

    public boolean getMazeChoice() {
        return turnOffMaze;
    }

    public void mazeReset(){
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

        fillWallArray();  //initiate the array used to hold wall coordinates

        initialization();  //finding the starting points for each maze piece

        wallBlockX();
        wallBlockY();

    }

    private Image getImage(String imagePNG){
        Image image;
        ImageIcon icon = new ImageIcon(this.getClass().getResource(imagePNG));
        image = icon.getImage();
        return image;
    }

    public void setTurnOffMaze(boolean turnOffMaze) {
        this.turnOffMaze = turnOffMaze;
    }

}
