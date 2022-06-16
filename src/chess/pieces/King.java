package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	private boolean canMovie(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRockCastling(Position position) {
		ChessPiece cp = (ChessPiece) getBoard().piece(position);
		return cp != null && cp instanceof Rook && cp.getColor() == getColor() && cp.getMoveCount() == 0;
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMovies() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// up
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// down
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// up-left
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// up-right
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// down-left
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// down-right
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/**
		 * Specialmove Castling
		 */
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// king-side
			Position posTK = new Position(position.getRow(), position.getColumn() + 3);
			if (testRockCastling(posTK)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// queen-side
			Position posTQ = new Position(position.getRow(), position.getColumn() - 4);
			if (testRockCastling(posTQ)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
		}

		return mat;
	}

}
