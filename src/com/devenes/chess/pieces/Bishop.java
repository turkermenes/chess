package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Bishop extends JButton {
    public String color;
    public int x, y, row, column;
    public Core core;

    public Bishop(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_bishop.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_bishop.png")).getFile();

        ImageIcon originalIcon = new ImageIcon(iconfilePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(Core.size, Core.size, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        setIcon(resizedIcon);
        setLocation(x, y);
        setSize(Core.size, Core.size);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        addActionListener(new PieceActionListener(this));
        setFocusable(false);
        setVisible(true);
    }
    public ArrayList<String> getPossibleMoves() {
        // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
        // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12
        ArrayList<String> result = new ArrayList<>();
        int pawn = color.equals("white") ? 6 : 12;
        int bishop = color.equals("white") ? 5 : 11;
        int knight = color.equals("white") ? 4 : 10;
        int rook = color.equals("white") ? 3 : 9;
        int queen = color.equals("white") ? 2 : 8;
        //top right
        int tempRow = row;
        int tempColumn = column;
        int val;
        while (tempRow >= 0 && tempColumn < 8) {
            if (tempRow - 1 >= 0 && tempColumn + 1 < 8)
                val = Core.board[tempRow - 1][tempColumn + 1];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (tempRow-- - 1) + "," + (tempColumn++ + 1);
                result.add(s);
                if (val != 0) break;
            } else break;
        }

        //top left
        tempRow = row;
        tempColumn = column;
        while (tempRow >= 0 && tempColumn >= 0) {
            if (tempRow - 1 >= 0 && tempColumn - 1 >= 0)
                val = Core.board[tempRow - 1][tempColumn - 1];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (tempRow-- - 1) + "," + (tempColumn-- - 1);
                result.add(s);
                if (val != 0) break;
            } else break;
        }

        //bottom right
        tempRow = row;
        tempColumn = column;
        while (tempRow < 8 && tempColumn < 8) {
            if (tempRow + 1 < 8 && tempColumn + 1 < 8)
                val = Core.board[tempRow + 1][tempColumn + 1];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (tempRow++ + 1) + "," + (tempColumn++ + 1);
                result.add(s);
                if (val != 0) break;
            } else break;
        }
        //bottom left
        tempRow = row;
        tempColumn = column;
        while (tempRow < 8 && tempColumn >= 0) {
            if (tempRow + 1 < 8 && tempColumn - 1 >= 0)
                val = Core.board[tempRow + 1][tempColumn - 1];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (tempRow++ + 1) + "," + (tempColumn-- - 1);
                result.add(s);
                if (val != 0) break;
            } else break;
        }
        return result;
    }

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
