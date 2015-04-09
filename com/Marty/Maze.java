package com.Marty;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

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

    LinkedList<Point> wallCoordinates = new LinkedList<Point>();

    public Maze(int maxX, int maxY, int squareSize){
        this.XnumOfSquares = maxX;
        this.YnumOfSquares = maxY;
        this.squareSize = squareSize;

        getRandonNumber();
    }

    public void displayMaze(Graphics g){
        g.setColor(Color.black);

        getStartingPoint();

        buildLine(g);


//        g.fillRect(xStart,yStart,squareSize,squareSize);
//        g.fillRect(xStart,yStart+30,squareSize,squareSize);
//        g.fillRect(xStart,yStart+60,squareSize,squareSize);

    }

    public void getRandonNumber(){

        xStarting = rnd.nextInt(XnumOfSquares);
        yStarting = rnd.nextInt(YnumOfSquares);

    }

    public void getStartingPoint(){
        xStart = xStarting * squareSize;
        yStart = yStarting * squareSize;
    }

    public void buildLine(Graphics g){
        getStartingPoint();

        for (int s = 1; s < 4; s++){
            g.fillRect(xStart, yStart + (30*s), squareSize, squareSize);
        }

    }




    //TODO ADD MAZE FUNCTIONALITY
}
