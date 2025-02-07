package com.devenes.chess.gui;

import com.devenes.chess.core.Core;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public int frameWidth = Core.size * 8 + 100;
    public int frameHeight = Core.size * 8 + 100;
    JPanel gameBoard = new GameBoard();

    //core'u silme yapılandırıcı metot çalışıyor :)
    Core core = new Core(this, gameBoard);

    public Frame() {
        super("Chess");
        setSize(frameWidth, frameHeight);
        getContentPane().setBackground(Color.darkGray);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        add(gameBoard);

        gameBoard.revalidate();
        gameBoard.repaint();
    }


}
