package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ChessMatch cm = new ChessMatch();

		while (true) {
			UI.printBoard(cm.getPieces());
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(scan);

			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(scan);
			
			ChessPiece capturedPiece = cm.performChessMove(source, target);
		}

	}

}
