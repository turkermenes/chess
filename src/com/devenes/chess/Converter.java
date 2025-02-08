package com.devenes.chess;

import com.devenes.chess.core.Core;

public class Converter {

    public static int columnToX(int column) {
        return Core.size * column;
    }

    public static int xToColumn(int x) {
        int result = 0;
        int sum = 0;
        while (sum + Core.size <= x) {
            sum += Core.size;
            result++;
        }

        return result;
    }

    public static int rowToY(int row) {
        return Core.size * row;
    }

    public static int yToRow(int y) {
        int result = 0;
        int sum = 0;
        while (sum + Core.size <= y) {
            sum += Core.size;
            result++;
        }

        return result;
    }

    public static String rowColumnToSquare(int row, int column) {
        String result = "";
        result += columnToLetter(column);
        result += (8 - row);

        return result;
    }

    public static char columnToLetter(int column) {

        return switch (column) {
            case 0 -> 'a';
            case 1 -> 'b';
            case 2 -> 'c';
            case 3 -> 'd';
            case 4 -> 'e';
            case 5 -> 'f';
            case 6 -> 'g';
            case 7 -> 'h';
            default -> ' ';
        };
    }

    //en passant da kullanırsın belki reiz
    public static int[] squareToRowAndColumn(String square) {
        int[] result = new int[2];
        result[0] = Integer.parseInt(String.valueOf(square.charAt(1))) - 1;
        switch (square.charAt(0)) {
            case 'b':
                result[1] = 1;
                break;
            case 'c':
                result[1] = 2;
                break;
            case 'd':
                result[1] = 3;
                break;
            case 'e':
                result[1] = 4;
                break;
            case 'f':
                result[1] = 5;
                break;
            case 'g':
                result[1] = 6;
                break;
            case 'h':
                result[1] = 7;
                break;
        }
        return result;
    }
}
