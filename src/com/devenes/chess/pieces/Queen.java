package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Queen extends JButton implements Piece {

    public String color;
    public int x;
    public int y;

    public int row, column, index;
    Core core;
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
        addActionListener(new PieceActionListener(core,this));
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

//    public void makeMove() {
//        if (Core.targetSquare != null) {
//            // Move to an empty square
//            Core.board[row][column] = 0;
//            String[] split = Core.targetSquare.split(",");
//            int x = Converter.columnToX(Integer.parseInt(split[1]));
//            int y = Converter.rowToY(Integer.parseInt(split[0]));
//            this.x = x;
//            this.y = y;
//            setLocation(x, y);
//            row = Converter.yToRow(y);
//            column = Converter.xToColumn(x);
//            Core.board[row][column] = 6;
//
//            //add moves
//            if (color.equals("white")) {
//                String[] temp = {Converter.rowColumnToSquare(row, column), ""};
//                Core.moves.add(temp);
//            } else {
//                String[] temp = Core.moves.get(Core.moves.size() - 1);
//                temp[1] = Converter.rowColumnToSquare(row, column);
//            }
//        } else if (Core.targetPiece != null) {
//            // Taking a piece
//            Pawn selectedPawn = (Pawn) Core.selectedPiece;
//            Pawn targetPawn = (Pawn) Core.targetPiece;
//            String move = "";
//            move += Converter.columnToLetter(selectedPawn.column);
//            move += "x";
//
//            Core.board[selectedPawn.row][selectedPawn.column] = 0;
//            selectedPawn.x = targetPawn.x;
//            selectedPawn.y = targetPawn.y;
//            selectedPawn.row = targetPawn.row;
//            selectedPawn.column = targetPawn.column;
//            selectedPawn.setLocation(selectedPawn.x, selectedPawn.y);
//            Core.board[targetPawn.row][targetPawn.column] = 6;
//            Core.pieces.remove(Core.targetPiece);
//            move += Converter.rowColumnToSquare(row, column);
//
//            //add moves
//            if (color.equals("white")) {
//                String[] temp = new String[2];
//                temp[0] = move;
//                Core.moves.add(temp);
//            } else {
//                String[] temp = Core.moves.get(Core.moves.size() - 1);
//                temp[1] = move;
//            }
//
//        }
//        Core.selectedPiece = null;
//        Core.targetSquare = null;
//        Core.targetPiece = null;
//        Core.turn = Core.turn == 1 ? 2 : 1;
//        core.updateBoard();
//    }

    public void makeMove() {
        if (Core.selectedPiece != null && Core.targetSquare != null) {
            // Move to an empty square
            if (Core.selectedPiece instanceof Pawn pawn) {
                Core.board[pawn.row][pawn.column] = 0;
                String[] split = Core.targetSquare.split(",");
                int x = Converter.columnToX(Integer.parseInt(split[1]));
                int y = Converter.rowToY(Integer.parseInt(split[0]));
                pawn.x = x;
                pawn.y = y;
                pawn.setLocation(x, y);
                pawn.row = Converter.yToRow(y);
                pawn.column = Converter.xToColumn(x);
                Core.board[pawn.row][pawn.column] = pawn.color.equals("white") ? 6 : 12;
            } else if (Core.selectedPiece instanceof Bishop bishop) {
                Core.board[bishop.row][bishop.column] = 0;
                String[] split = Core.targetSquare.split(",");
                int x = Converter.columnToX(Integer.parseInt(split[1]));
                int y = Converter.rowToY(Integer.parseInt(split[0]));
                bishop.x = x;
                bishop.y = y;
                bishop.setLocation(x, y);
                bishop.row = Converter.yToRow(y);
                bishop.column = Converter.xToColumn(x);
                Core.board[bishop.row][bishop.column] = bishop.color.equals("white") ? 5 : 11;
            } else if (Core.selectedPiece instanceof Knight knight) {
                Core.board[knight.row][knight.column] = 0;
                String[] split = Core.targetSquare.split(",");
                int x = Converter.columnToX(Integer.parseInt(split[1]));
                int y = Converter.rowToY(Integer.parseInt(split[0]));
                knight.x = x;
                knight.y = y;
                knight.setLocation(x, y);
                knight.row = Converter.yToRow(y);
                knight.column = Converter.xToColumn(x);
                Core.board[knight.row][knight.column] = knight.color.equals("white") ? 4 : 10;
            } else if (Core.selectedPiece instanceof Rook rook) {
                Core.board[rook.row][rook.column] = 0;
                String[] split = Core.targetSquare.split(",");
                int x = Converter.columnToX(Integer.parseInt(split[1]));
                int y = Converter.rowToY(Integer.parseInt(split[0]));
                rook.x = x;
                rook.y = y;
                rook.setLocation(x, y);
                rook.row = Converter.yToRow(y);
                rook.column = Converter.xToColumn(x);
                Core.board[rook.row][rook.column] = rook.color.equals("white") ? 3 : 9;
            } else if (Core.selectedPiece instanceof Queen queen) {
                Core.board[queen.row][queen.column] = 0;
                String[] split = Core.targetSquare.split(",");
                int x = Converter.columnToX(Integer.parseInt(split[1]));
                int y = Converter.rowToY(Integer.parseInt(split[0]));
                queen.x = x;
                queen.y = y;
                queen.setLocation(x, y);
                queen.row = Converter.yToRow(y);
                queen.column = Converter.xToColumn(x);
                Core.board[queen.row][queen.column] = queen.color.equals("white") ? 2 : 8;
            } else if (Core.selectedPiece instanceof King king) {
                Core.board[king.row][king.column] = 0;
                String[] split = Core.targetSquare.split(",");
                int x = Converter.columnToX(Integer.parseInt(split[1]));
                int y = Converter.rowToY(Integer.parseInt(split[0]));
                king.x = x;
                king.y = y;
                king.setLocation(x, y);
                king.row = Converter.yToRow(y);
                king.column = Converter.xToColumn(x);
                Core.board[king.row][king.column] = king.color.equals("white") ? 1 : 7;
            }


        } else if (Core.selectedPiece != null && Core.targetPiece != null) {
            // Taking a piece
            int targetX = 0, targetY = 0, targetRow = 0, targetColumn = 0;
            if (Core.targetPiece instanceof Pawn pawn) {
                targetX = pawn.x;
                targetY = pawn.y;
                targetRow = pawn.row;
                targetColumn = pawn.column;
                Core.pieces.remove(pawn);
            } else if (Core.targetPiece instanceof Bishop bishop) {
                targetX = bishop.x;
                targetY = bishop.y;
                targetRow = bishop.row;
                targetColumn = bishop.column;
                Core.pieces.remove(bishop);
            } else if (Core.targetPiece instanceof Knight knight) {
                targetX = knight.x;
                targetY = knight.y;
                targetRow = knight.row;
                targetColumn = knight.column;
                Core.pieces.remove(knight);
            }
            else if (Core.targetPiece instanceof Rook rook) {
                targetX = rook.x;
                targetY = rook.y;
                targetRow = rook.row;
                targetColumn = rook.column;
                Core.pieces.remove(rook);
            } else if (Core.targetPiece instanceof Queen queen) {
                targetX = queen.x;
                targetY = queen.y;
                targetRow = queen.row;
                targetColumn = queen.column;
                Core.pieces.remove(queen);
            } else if (Core.targetPiece instanceof King king) {
                targetX = king.x;
                targetY = king.y;
                targetRow = king.row;
                targetColumn = king.column;
                Core.pieces.remove(king);
            }

            if (Core.selectedPiece instanceof Pawn pawn) {
                Core.board[pawn.row][pawn.column] = 0;
                pawn.x = targetX;
                pawn.y = targetY;
                pawn.row = targetRow;
                pawn.column = targetColumn;
                pawn.setLocation(pawn.x, pawn.y);
                Core.board[targetRow][targetColumn] = pawn.color.equals("white") ? 6 : 12;
            } else if (Core.selectedPiece instanceof Bishop bishop) {
                Core.board[bishop.row][bishop.column] = 0;
                bishop.x = targetX;
                bishop.y = targetY;
                bishop.row = targetRow;
                bishop.column = targetColumn;
                bishop.setLocation(bishop.x, bishop.y);
                Core.board[targetRow][targetColumn] = bishop.color.equals("white") ? 5 : 11;
            } else if (Core.selectedPiece instanceof Knight knight) {
                Core.board[knight.row][knight.column] = 0;
                knight.x = targetX;
                knight.y = targetY;
                knight.row = targetRow;
                knight.column = targetColumn;
                knight.setLocation(knight.x, knight.y);
                Core.board[targetRow][targetColumn] = knight.color.equals("white") ? 4 : 10;
            }
            else if (Core.selectedPiece instanceof Rook rook) {
                Core.board[rook.row][rook.column] = 0;
                rook.x = targetX;
                rook.y = targetY;
                rook.row = targetRow;
                rook.column = targetColumn;
                rook.setLocation(rook.x, rook.y);
                Core.board[targetRow][targetColumn] = rook.color.equals("white") ? 3 : 9;

            } else if (Core.selectedPiece instanceof Queen queen) {
                Core.board[queen.row][queen.column] = 0;
                queen.x = targetX;
                queen.y = targetY;
                queen.row = targetRow;
                queen.column = targetColumn;
                queen.setLocation(queen.x, queen.y);
                Core.board[targetRow][targetColumn] = queen.color.equals("white") ? 2 : 8;
            } else if (Core.selectedPiece instanceof King king) {
                Core.board[king.row][king.column] = 0;
                king.x = targetX;
                king.y = targetY;
                king.row = targetRow;
                king.column = targetColumn;
                king.setLocation(king.x, king.y);
                Core.board[targetRow][targetColumn] = king.color.equals("white") ? 1 : 7;
            }

            Core.pieces.remove(Core.targetPiece);


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
