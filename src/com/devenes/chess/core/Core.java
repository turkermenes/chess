package com.devenes.chess.core;

import com.devenes.chess.Converter;
import com.devenes.chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Core {

//    boolean showPossibleMoves = false;
    public static ArrayList<Object> pieces = new ArrayList<>();
    public static ArrayList<String[]> moves = new ArrayList<>();
    public static ArrayList<String> possibleMoves = new ArrayList<>();
    public static Object selectedPiece, targetPiece;
    public static int[][] board = new int[8][8];
    public static String targetSquare;
    public static int size = 120;
    public static int turn = 1;
    JFrame frame;
    JPanel gamePanel;

    public Core(JFrame frame, JPanel panel) {
        this.frame = frame;
        gamePanel = panel;
        setDefaultPosition();
        createPieceObjects();
        updateBoard();
    }
    public void setDefaultPosition() {

        // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
        // Black -> King: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12

        board[0][0] = 9;
        board[0][1] = 10;
        board[0][2] = 11;
        board[0][3] = 8;
        board[0][4] = 7;
        board[0][5] = 11;
        board[0][6] = 10;
        board[0][7] = 9;

        for (int i = 0; i < 8; i++)
            board[1][i] = 12;

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++)
                board[i][j] = 0;
        }

        for (int i = 0; i < 8; i++)
            board[6][i] = 6;

        board[7][0] = 3;
        board[7][1] = 4;
        board[7][2] = 5;
        board[7][3] = 2;
        board[7][4] = 1;
        board[7][5] = 5;
        board[7][6] = 4;
        board[7][7] = 3;

    }

    public void createPieceObjects() {
        // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
        // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12
        int x = 0;
        int y = 0;
        String color;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != 0) {
                    switch (board[i][j]) {
                        case 1:
                        case 7:
                            color = board[i][j] == 1 ? "white" : "black";
                            King king = new King(this, color, x, y);
                            king.row = i;
                            king.column = j;
                            pieces.add(king);
                            king.index = pieces.indexOf(king);
                            break;
                        case 2:
                        case 8:
                            color = board[i][j] == 2 ? "white" : "black";
                            Queen queen = new Queen(this, color, x, y);
                            queen.row = i;
                            queen.column = j;
                            pieces.add(queen);
                            queen.index = pieces.indexOf(queen);
                            break;
                        case 3:
                        case 9:
                            color = board[i][j] == 3 ? "white" : "black";
                            Rook rook = new Rook(this, color, x, y);
                            rook.row = i;
                            rook.column = j;
                            pieces.add(rook);
                            rook.index = pieces.indexOf(rook);
                            break;
                        case 4:
                        case 10:
                            color = board[i][j] == 4 ? "white" : "black";
                            Knight knight = new Knight(this, color, x, y);
                            knight.row = i;
                            knight.column = j;
                            pieces.add(knight);
                            knight.index = pieces.indexOf(knight);
                            break;
                        case 5:
                        case 11:
                            color = board[i][j] == 5 ? "white" : "black";
                            Bishop bishop = new Bishop(this, color, x, y);
                            bishop.row = i;
                            bishop.column = j;
                            pieces.add(bishop);
                            bishop.index = pieces.indexOf(bishop);
                            break;
                        case 6:
                        case 12:
                            color = board[i][j] == 6 ? "white" : "black";
                            Pawn pawn = new Pawn(this, color, x, y);
                            pawn.row = i;
                            pawn.column = j;
                            pieces.add(pawn);
                            pawn.index = pieces.indexOf(pawn);
                            break;
                    }
                }

                x += size;
            }
            x = 0;
            y += size;
        }
    }

    public void updateBoard() {
        // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
        // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12
        gamePanel.removeAll();
        for (Object o : pieces) {
            gamePanel.add((Component) o);
        }
        gamePanel.repaint();
        gamePanel.revalidate();
        frame.repaint();
        frame.revalidate();
    }

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
        updateBoard();
    }

}
