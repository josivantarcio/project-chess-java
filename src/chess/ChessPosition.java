package chess;

import boardgame.Position;
import chess.exception.ChessException;

public class ChessPosition {

	private char columns;
	private int rows;
	
	public ChessPosition(char columns, int rows) {
		if(columns < 'a' || columns > 'h' || rows < 1 || rows > 8) {
			throw new ChessException("Error instantianling ChessPosition. Valid values are from 'a1' to 'h8'");
		}
		this.columns = columns;
		this.rows = rows;
	}

	public char getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
	
	protected Position toPositioin() {
		return new Position(8 - rows, columns - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		char col = (char)('a' - position.getColumn());
		char row = (char)(8 - position.getRow());
		return new ChessPosition(col, row);
	}

	@Override
	public String toString() {
		return "" + columns + rows;
	}
	
	
}
