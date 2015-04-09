package com.Marty;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Marty on 4/8/2015.
 */
public class Maze extends JPanel {

    //TODO MAKE SURE KIBBLE DOESN'T END UP IN WALLS
    //TODO WALL HIT EQUALS END GAME

    Random rnd = new Random();

    private int gameLevel = SnakeGame.getGameLevel();

    int XnumOfSquares;
    int YnumOfSquares;
    int squareSize;

    int xStart;
    int yStart;

    ArrayList<Integer> wall1 = new ArrayList<Integer>();
    ArrayList<Integer> wall2 = new ArrayList<Integer>();
    ArrayList<Integer> wall3 = new ArrayList<Integer>();
    ArrayList<Integer> wall4 = new ArrayList<Integer>();
    ArrayList<Integer> wall5 = new ArrayList<Integer>();
    ArrayList<Integer> wall6 = new ArrayList<Integer>();

    public Maze(int maxX, int maxY, int squareSize){
        this.XnumOfSquares = maxX;
        this.YnumOfSquares = maxY;
        this.squareSize = squareSize;

        initialization();
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
        xStart = xRandom * squareSize;

        return xStart;
    }

    private int buildTheWallY(){
        int yRandom = rnd.nextInt(YnumOfSquares);
        yStart = yRandom * squareSize;

        return yStart;
    }

    public void displayMaze(Graphics g) {
        g.setColor(Color.black);

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
        g.fillRect(wall.get(0), wall.get(1), squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1) + 30, squareSize, squareSize);
        g.fillRect(wall.get(0), wall.get(1) + 60, squareSize, squareSize);
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

    public ArrayList kibbleBlockX(){
        ArrayList<Integer> kibbleBlockX = new ArrayList<Integer>();

        kibbleBlockX.add(0, wall1.get(0));
        kibbleBlockX.add(0, wall2.get(0));
        kibbleBlockX.add(0, wall3.get(0));
        kibbleBlockX.add(0, wall4.get(0));
        kibbleBlockX.add(0, wall5.get(0));
        kibbleBlockX.add(0, wall6.get(0));

        return kibbleBlockX;
    }

}
