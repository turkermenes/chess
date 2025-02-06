package com.devenes.chess.core;

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
                            break;
                        case 2:
                        case 8:
                            color = board[i][j] == 2 ? "white" : "black";
                            Queen queen = new Queen(this, color, x, y);
                            queen.row = i;
                            queen.column = j;
                            pieces.add(queen);
                            break;
                        case 3:
                        case 9:
                            color = board[i][j] == 3 ? "white" : "black";
                            Rook rook = new Rook(this, color, x, y);
                            rook.row = i;
                            rook.column = j;
                            pieces.add(rook);
                            break;
                        case 4:
                        case 10:
                            color = board[i][j] == 4 ? "white" : "black";
                            Knight knight = new Knight(this, color, x, y);
                            knight.row = i;
                            knight.column = j;
                            pieces.add(knight);
                            break;
                        case 5:
                        case 11:
                            color = board[i][j] == 5 ? "white" : "black";
                            Bishop bishop = new Bishop(this, color, x, y);
                            bishop.row = i;
                            bishop.column = j;
                            pieces.add(bishop);
                            break;
                        case 6:
                        case 12:
                            color = board[i][j] == 6 ? "white" : "black";
                            Pawn pawn = new Pawn(this, color, x, y);
                            pawn.row = i;
                            pawn.column = j;
                            pieces.add(pawn);
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

}
