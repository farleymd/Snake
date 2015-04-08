package com.Marty;

import javax.swing.*;
import java.awt.*;

/**
 * Created by marty.farley on 4/8/2015.
 */
public class SnakePanelGUI extends JFrame {
    private JPanel rootPanel;

    int maxX = SnakeGame.xPixelMaxDimension;
    int maxY = SnakeGame.yPixelMaxDimension;
    int squareSize = SnakeGame.squareSize;

    JFrame gridFrame = new JFrame();

    public SnakePanelGUI(){
        super("Snake!");
        gridFrame.setLayout(new GridLayout(maxX,maxY));
        gridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridFrame.pack();
        gridFrame.setVisible(true);

    }

}
