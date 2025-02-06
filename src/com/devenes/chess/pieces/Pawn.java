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

public class Pawn extends JButton implements Piece {
    //en passant, promote

    public String color;
    public int x;
    public int y;
    public int row, column, index;
    boolean firstMove = true;
    Core core;

    public Pawn(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_pawn.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_pawn.png")).getFile();

        ImageIcon originalIcon = new ImageIcon(iconfilePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(Core.size, Core.size, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        setIcon(resizedIcon);
        setLocation(x, y);
        setSize(Core.size, Core.size);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusable(false);
        addActionListener(new PieceActionListener(core, this));
        setVisible(true);
    }

    public ArrayList<String> getPossibleMoves() {
        ArrayList<String> result = new ArrayList<>();
        if (color.equals("white")) {
            // two step move
            if (firstMove) {
                if (Core.board[row - 2][column] == 0 && Core.board[row - 1][column] == 0) {
                    String s = (row - 2) + "," + column;
                    result.add(s);
                }
            }
            // one step move
            if (row - 1 >= 0 && Core.board[row - 1][column] == 0) {
                String s = (row - 1) + "," + column;
                result.add(s);
            }
            // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
            // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12

            // take left piece
            if (row - 1 >= 0 && column - 1 >= 0) {
                int val = Core.board[row - 1][column - 1];
                if (val == 8 || val == 9 || val == 10 || val == 11 || val == 12) {
                    String s = (row - 1) + "," + (column - 1);
                    result.add(s);
                }
            }
            //take right piece
            if (row - 1 >= 0 && column + 1 < 8) {
                int val = Core.board[row - 1][column + 1];
                if (val == 8 || val == 9 || val == 10 || val == 11 || val == 12) {
                    String s = (row - 1) + "," + (column + 1);
                    result.add(s);
                }
            }


        } else {
            // two step move
            if (firstMove) {
                if (Core.board[row + 2][column] == 0 && Core.board[row + 1][column] == 0) {
                    String s = (row + 2) + "," + column;
                    result.add(s);
                }
            }
            // one step move
            if (row + 1 < 8 && Core.board[row + 1][column] == 0) {
                String s = (row + 1) + "," + column;
                result.add(s);
            }
            // take left piece
            if (row + 1 < 8 && column - 1 >= 0) {
                int val = Core.board[row + 1][column - 1];
                if (val == 2 || val == 3 || val == 4 || val == 5 || val == 6) {
                    String s = (row + 1) + "," + (column - 1);
                    result.add(s);
                }
            }
            // take right piece
            if (row + 1 < 8 && column + 1 < 8) {
                int val = Core.board[row + 1][column + 1];
                if (val == 2 || val == 3 || val == 4 || val == 5 || val == 6) {
                    String s = (row + 1) + "," + (column + 1);
                    result.add(s);
                }
            }
        }
        return result;
    }

//    public void promote() {
//
//    }

    public void makeMove() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<Class<?>, Integer> pieceValues = Map.of(
                Pawn.class, 6,
                Bishop.class, 5,
                Knight.class, 4,
                Rook.class, 3,
                Queen.class, 2,
                King.class, 1
        );

        if (Core.selectedPiece != null && Core.targetSquare != null) {
            // Move to an empty square
            Object selectedP = Core.selectedPiece;
            int selectedPieceRow = (int) selectedP.getClass().getField("row").get(selectedP);
            int selectedPieceColumn = (int) selectedP.getClass().getField("column").get(selectedP);
            Core.board[selectedPieceRow][selectedPieceColumn] = 0;
            String[] split = Core.targetSquare.split(",");
            int x = Converter.columnToX(Integer.parseInt(split[1]));
            int y = Converter.rowToY(Integer.parseInt(split[0]));
            selectedP.getClass().getField("x").set(selectedP, x);
            selectedP.getClass().getField("y").set(selectedP, y);
            selectedP.getClass().getMethod("setLocation", int.class, int.class).invoke(selectedP, x, y);
            selectedP.getClass().getField("row").set(selectedP, Converter.yToRow(y));
            selectedP.getClass().getField("column").set(selectedP, Converter.xToColumn(x));
            int row = (int) selectedP.getClass().getField("row").get(selectedP);
            int column = (int) selectedP.getClass().getField("column").get(selectedP);

            int baseValue = pieceValues.getOrDefault(selectedP.getClass(), 0);
            String color = (String) selectedP.getClass().getField("color").get(selectedP);
            Core.board[row][column] = color.equals("white") ? baseValue : baseValue + 6;

        } else if (Core.selectedPiece != null && Core.targetPiece != null) {
            // Taking a piece
            Object targetP = Core.targetPiece;
            int targetX = (int) targetP.getClass().getField("x").get(targetP);
            int targetY = (int) targetP.getClass().getField("y").get(targetP);
            int targetRow = (int) targetP.getClass().getField("row").get(targetP);
            int targetColumn = (int) targetP.getClass().getField("column").get(targetP);
            Core.pieces.remove(targetP);

            Object selectedP = Core.selectedPiece;
            int selectedPieceRow = (int) selectedP.getClass().getField("row").get(selectedP);
            int selectedPieceColumn = (int) selectedP.getClass().getField("column").get(selectedP);
            Core.board[selectedPieceRow][selectedPieceColumn] = 0;
            selectedP.getClass().getField("x").set(selectedP, targetX);
            selectedP.getClass().getField("y").set(selectedP, targetY);
            selectedP.getClass().getField("row").set(selectedP, targetRow);
            selectedP.getClass().getField("column").set(selectedP, targetColumn);
            selectedP.getClass().getMethod("setLocation", int.class, int.class).invoke(selectedP, targetX, targetY);

            int baseValue = pieceValues.getOrDefault(selectedP.getClass(), 0);
            String color = (String) selectedP.getClass().getField("color").get(selectedP);
            Core.board[targetRow][targetColumn] = color.equals("white") ? baseValue : baseValue + 6;

        } else return;

        Core.selectedPiece = null;
        Core.targetSquare = null;
        Core.targetPiece = null;
        Core.turn = Core.turn == 1 ? 2 : 1;
        core.updateBoard();
    }

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
