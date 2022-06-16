package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	private boolean canMovie(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public String toString() {
		return "N";
	}

	@Override
	public boolean[][] possibleMovies() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		
		p.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMovie(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}

}
