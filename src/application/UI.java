package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

/**
 * Utility class that handles user interface operations for the chess game.
 */
public class UI {

    /**
     * ANSI escape codes for console text colors and backgrounds.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Clears the console screen.
     */
    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Reads a chess position from the user input.
     * 
     * @param scan Scanner object used to read input.
     * @return The ChessPosition object representing the user input.
     * @throws InputMismatchException If there is an error reading the chess position.
     */
    public static ChessPosition readChessPosition(Scanner scan) throws InputMismatchException {
        try {
            String s = scan.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading chessPosition");
        }
    }

    /**
     * Prints the current state of the chess match, including the board and captured pieces.
     * 
     * @param chessMatch The ChessMatch object representing the current game state.
     * @param captured   List of captured ChessPiece objects.
     */
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        if (!chessMatch.getCheckMate()) {
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("CHECK");
            }
        } else {
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    /**
     * Prints the chess board with the current pieces.
     * 
     * @param pieces 2D array representing the chess pieces on the board.
     */
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Prints the chess board with highlighted possible moves.
     * 
     * @param pieces        2D array representing the chess pieces on the board.
     * @param possibleMoves 2D boolean array indicating possible moves for pieces.
     */
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    /**
     * Prints a chess piece on the console with optional background color.
     * 
     * @param piece      The ChessPiece object to print.
     * @param background Indicates if background color should be applied.
     */
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    /**
     * Prints the list of captured pieces categorized by color.
     * 
     * @param captured List of captured ChessPiece objects.
     */
    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
                .collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
                .collect(Collectors.toList());
        System.out.println("Capture pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.print(Arrays.toString(white.toArray()));
        System.out.println(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.print(Arrays.toString(black.toArray()));
        System.out.println(ANSI_RESET);
    }
}
