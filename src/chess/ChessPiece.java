package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 * Abstract class representing a chess piece with its color and move count.
 */
public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	/**
	 * Constructs a chess piece with a board and color.
	 *
	 * @param board The board where the piece is placed.
	 * @param color The color of the piece (WHITE or BLACK).
	 */
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	/**
	 * Retrieves the color of the chess piece.
	 *
	 * @return The color of the piece (WHITE or BLACK).
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Retrieves the number of moves made by the chess piece.
	 *
	 * @return The number of moves made.
	 */
	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * Increases the move count of the chess piece by one.
	 */
	public void increaseMoveCount() {
		moveCount++;
	}

	/**
	 * Decreases the move count of the chess piece by one.
	 */
	public void decreaseMoveCount() {
		moveCount--;
	}

	/**
	 * Checks if there is an opponent's piece at a given position.
	 *
	 * @param position The position to check for an opponent's piece.
	 * @return true if there is an opponent's piece at the position, false
	 *         otherwise.
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}

	/**
	 * Retrieves the chess position of the piece.
	 *
	 * @return The chess position of the piece.
	 */
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
}
