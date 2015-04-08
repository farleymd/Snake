package com.Marty;

import javax.swing.*;
import java.awt.*;

/**
 * Created by marty.farley on 4/8/2015.
 */
public class SnakeStartGUI extends JFrame {
    private JLabel snakeLabel;
    private JRadioButton warpYes;
    private JRadioButton warpNo;
    private JRadioButton mazeYes;
    private JRadioButton mazeNo;
    private JPanel rootPanel;


    public SnakeStartGUI(){
        super("Snake!");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension));

    }

}
