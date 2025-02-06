package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class King extends JButton implements Piece {

    //50 move rule
    public String color;
    public int x;
    public int y;
    public int row, column, index;
    boolean played = false;
    public Core core;

    public King(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_king.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_king.png")).getFile();

        ImageIcon originalIcon = new ImageIcon(iconfilePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(Core.size, Core.size, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        setIcon(resizedIcon);
        setLocation(x, y);
        setSize(Core.size, Core.size);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusable(false);
        addActionListener(new PieceActionListener(this));
        setVisible(true);
    }

    public ArrayList<String> getPossibleMoves() {
        return null;
    }

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
