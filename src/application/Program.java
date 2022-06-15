package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exception.ChessException;

public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ChessMatch cm = new ChessMatch();

		while (true) {
			try {
				UI.clearScreen();
				UI.printMatch(cm);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(scan);
				
				boolean[][] possibleMoves = cm.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(cm.getPieces(), possibleMoves);
	
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(scan);
				
				ChessPiece capturedPiece = cm.performChessMove(source, target);
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
		}

	}

}
