package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exception.ChessException;

/**
 * The main class that drives the chess game application.
 */
public class Program {

    /**
     * Main method that starts the chess game application.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ChessMatch cm = new ChessMatch(); // Creates a new chess match instance.
        List<ChessPiece> captured = new ArrayList<ChessPiece>(); // List to hold captured chess pieces.

        // Main game loop that continues until checkmate condition is met.
        while (!cm.getCheckMate()) {
            try {
                UI.clearScreen(); // Clears the console screen.
                UI.printMatch(cm, captured); // Prints current state of the chess match.
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scan); // Reads source position from user input.

                // Calculates possible moves for the selected piece.
                boolean[][] possibleMoves = cm.possibleMoves(source);
                UI.clearScreen(); // Clears the console screen.
                UI.printBoard(cm.getPieces(), possibleMoves); // Prints the chess board with possible moves highlighted.

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scan); // Reads target position from user input.

                // Performs the chess move and captures any piece if applicable.
                ChessPiece capturedPiece = cm.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece); // Adds the captured piece to the list.
                }

                // Handles pawn promotion if applicable.
                if (cm.getPromoted() != null) {
                    System.out.print("Enter piece for promotion [B/N/Q/R]: ");
                    String type = scan.nextLine().toUpperCase();

                    // Validates and sets the promoted piece type.
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.print("Invalid Value! Enter again piece for promotion [B/N/Q/R]: ");
                        type = scan.nextLine().toUpperCase();
                    }

                    cm.replacePromotedPiece(type); // Replaces the promoted pawn with the chosen piece type.
                }

            } catch (ChessException e) {
                System.out.println(e.getMessage()); // Prints error message for chess-specific exceptions.
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage()); // Prints error message for input mismatch exceptions.
                scan.nextLine();
            }
        }

        UI.clearScreen(); // Clears the console screen.
        UI.printMatch(cm, captured); // Prints final state of the chess match.
    }
}
