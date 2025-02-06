package com.devenes.chess.gui;

import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.MouseListener;

import javax.swing.*;
import java.awt.*;

import static com.devenes.chess.core.Core.*;

public class GameBoard extends JPanel {

    Core core;
    public GameBoard() {

        setSize(size * 8, size * 8);
        setLocation(45, 30);
        setDoubleBuffered(true);
        setLayout(null);
        setVisible(true);
        addMouseListener(new MouseListener());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    public void setCore(Core core) {
        this.core = core;
    }

    private void drawBoard(Graphics g) {
        int x, y = 0;
        int color = 1;

        for (int i = 0; i < 8; i++) {
            x = 0;
            for (int j = 0; j < 8; j++) {
                if (color == 1) {
                    g.setColor(Color.decode("#F0D9B5"));
                    color = 2;
                } else {
                    g.setColor(Color.decode("#B58863"));
                    color = 1;
                }
                g.fillRect(x, y, size, size);
                x += size;
            }
            color = (color == 1) ? 2 : 1;
            y += size;
        }
    }

//    private void drawPossibleMoves(Graphics g) {
//
//    }

}
