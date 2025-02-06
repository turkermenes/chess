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
    Core core;

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
        addActionListener(new PieceActionListener(core,this));
        setVisible(true);
    }

    public ArrayList<String> getPossibleMoves() {
        return null;
    }


//    public boolean canShortCastle() {
//        if (!played) {
//            if (color.equals("white")) {
//                if (Core.board[7][])
//            }
//        }
//        return false;
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
