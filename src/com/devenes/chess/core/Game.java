package com.devenes.chess.core;


import com.devenes.chess.Converter;
import com.devenes.chess.pieces.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Game {

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

            //castle
            if (selectedP instanceof King king) {
                //make short castle
                if (king.column + 2 == Integer.parseInt(split[1])) {
                    for (Object o : Core.pieces) {
                        if (o instanceof Rook rook && rook.color.equals(king.color) && rook.row == king.row && rook.column == king.column + 3) {
                            //x, y, setLocation, row, column değiş
                            rook.x = Converter.columnToX(king.column + 1);
                            rook.setLocation(rook.x, rook.y);
                            rook.column = king.column + 1;
                            king.played = true;
                            rook.played = true;
                            break;
                        }
                    }
                } else if (king.column - 2 == Integer.parseInt(split[1])) {
                    for (Object o : Core.pieces) {
                        if (o instanceof Rook rook && rook.color.equals(king.color) && rook.row == king.row && rook.column == king.column - 4) {
                            //x, y, setLocation, row, column değiş
                            rook.x = Converter.columnToX(king.column - 1);
                            rook.setLocation(rook.x, rook.y);
                            rook.column = king.column - 1;
                            king.played = true;
                            rook.played = true;
                            break;
                        }
                    }
                }
            }

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
            if (selectedP instanceof Pawn pawn)
                pawn.firstMove = false;
            else if (selectedP instanceof Rook rook)
                rook.played = true;


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

        Core core = (Core) Core.selectedPiece.getClass().getField("core").get(Core.selectedPiece);
        Core.selectedPiece = null;
        Core.targetSquare = null;
        Core.targetPiece = null;
        Core.possibleMoves = null;
        Core.turn = Core.turn == 1 ? 2 : 1;
        if (core != null)
            core.updateBoard();


    }

}
