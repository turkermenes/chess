package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Queen extends JButton implements Piece {

    public String color;
    public int x;
    public int y;

    public int row, column, index;
    public Core core;
    public Queen(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_queen.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_queen.png")).getFile();

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
        ArrayList<String> result = new ArrayList<>();
        int pawn = color.equals("white") ? 6 : 12;
        int bishop = color.equals("white") ? 5 : 11;
        int knight = color.equals("white") ? 4 : 10;
        int rook = color.equals("white") ? 3 : 9;
        int queen = color.equals("white") ? 2 : 8;

        //right
        int tempColumn = column;
        int val;
        while (tempColumn < 8) {
            if (tempColumn + 1 < 8)
                val = Core.board[row][tempColumn + 1];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (row) + "," + (tempColumn++ + 1);
                result.add(s);
                if (val != 0) break;
            } else break;
        }
        //left
        tempColumn = column;
        while (tempColumn >= 0) {
            if (tempColumn - 1 >= 0)
                val = Core.board[row][tempColumn - 1];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (row) + "," + (tempColumn-- - 1);
                result.add(s);
                if (val != 0) break;
            } else break;
        }
        //up
        int tempRow = row;
        while (tempRow >= 0) {
            if (tempRow - 1 >= 0)
                val = Core.board[tempRow - 1][column];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (tempRow-- - 1) + "," + (column);
                result.add(s);
                if (val != 0) break;
            } else break;
        }
        //down
        tempRow = row;
        while (tempRow < 8) {
            if (tempRow + 1 < 8)
                val = Core.board[tempRow + 1][column];
            else break;
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (tempRow++ + 1) + "," + (column);
                result.add(s);
                if (val != 0) break;
            } else break;
        }
        //top right
        tempRow = row;
        tempColumn = column;
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
