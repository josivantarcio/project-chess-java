package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exception.ChessException;

public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ChessMatch cm = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<ChessPiece>();

		while (!cm.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(cm, captured);
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

				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}

				if (cm.getPromoted() != null) {
					System.out.print("Enter piece for promotion [B/N/Q/R]: ");
					String type = scan.nextLine().toUpperCase();

					while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Invalid Value! Enter again piece for promotion [B/N/Q/R]: ");
						type = scan.nextLine().toUpperCase();
					}

					cm.replacePromotedPiece(type);
				}

			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scan.nextLine();
			}
		}

		UI.clearScreen();
		UI.printMatch(cm, captured);
	}
}
