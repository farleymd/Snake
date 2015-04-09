package com.Marty;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Marty on 4/8/2015.
 */
public class Maze extends JPanel {

    Random rnd = new Random();

    int XnumOfSquares;
    int YnumOfSquares;
    int squareSize;

    int xStarting;
    int yStarting;

    int xStart;
    int yStart;

    ArrayList<Integer> firstWall = new ArrayList<Integer>();
    ArrayList<Integer> secondWall = new ArrayList<Integer>();

    public Maze(int maxX, int maxY, int squareSize){
        this.XnumOfSquares = maxX;
        this.YnumOfSquares = maxY;
        this.squareSize = squareSize;

        initialization();
    }

    public void displayMaze(Graphics g){
        g.setColor(Color.black);

        buildLine(g);

    }

    public void initialization(){

        int xRandom = rnd.nextInt(XnumOfSquares);
        int yRandom = rnd.nextInt(YnumOfSquares);

        xStart = xRandom * squareSize;
        yStart = yRandom * squareSize;

        firstWall.add(0,xStart);
        firstWall.add(1,yStart);

        int xRandom2 = rnd.nextInt(XnumOfSquares);
        int yRandom2 = rnd.nextInt(YnumOfSquares);

        int xStart2 = xRandom2 * squareSize;
        int yStart2 = yRandom2 * squareSize;

        secondWall.add(0,xStart2);
        secondWall.add(1,yStart2);

    }

    public void buildLine(Graphics g){
        g.fillRect(firstWall.get(0), firstWall.get(1), squareSize, squareSize);
        g.fillRect(firstWall.get(0), firstWall.get(1) + 30, squareSize, squareSize);
        g.fillRect(firstWall.get(0), firstWall.get(1) + 60, squareSize, squareSize);

        g.fillRect(secondWall.get(0), secondWall.get(1), squareSize, squareSize);
        g.fillRect(secondWall.get(0), secondWall.get(1) + 30, squareSize, squareSize);
        g.fillRect(secondWall.get(0), secondWall.get(1) + 60, squareSize, squareSize);


//        for (int s = 1; s < 4; s++){
//            g.fillRect(xStart, yStart + (30*s), squareSize, squareSize);
//        }

    }

}
