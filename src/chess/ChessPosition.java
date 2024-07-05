package chess;

import boardgame.Position;
import chess.exception.ChessException;

/**
 * Representa uma posição no tabuleiro de xadrez, usando coordenadas no formato de xadrez (coluna 'a'-'h' e linha 1-8).
 */
public class ChessPosition {

	/** Coluna da posição */
	private char column;
	
	/** Linha da posição */
	private int row;
	
	/**
	 * Construtor que inicializa uma posição de xadrez com uma coluna e linha especificadas.
	 * 
	 * @param column Coluna da posição ('a'-'h').
	 * @param row Linha da posição (1-8).
	 * @throws ChessException Se os valores fornecidos não estiverem dentro dos limites válidos.
	 */
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition. Valid values are from 'a1' to 'h8'");
		}
		this.column = column;
		this.row = row;
	}

	/**
	 * Retorna a coluna da posição.
	 * 
	 * @return Coluna da posição.
	 */
	public char getColumn() {
		return column;
	}

	/**
	 * Retorna a linha da posição.
	 * 
	 * @return Linha da posição.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Converte a posição de xadrez para uma posição no tabuleiro (classe Position do boardgame).
	 * 
	 * @return Objeto Position equivalente à posição de xadrez.
	 */
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	/**
	 * Converte uma posição no tabuleiro (classe Position do boardgame) para uma posição de xadrez.
	 * 
	 * @param position Posição no tabuleiro a ser convertida.
	 * @return Objeto ChessPosition equivalente à posição no tabuleiro.
	 */
	protected static ChessPosition fromPosition(Position position) {
		char column = (char)('a' + position.getColumn());
		int row = 8 - position.getRow();
		return new ChessPosition(column, row);
	}

	/**
	 * Retorna uma representação da posição de xadrez no formato de string (ex: "a1", "h8").
	 * 
	 * @return String representando a posição de xadrez.
	 */
	@Override
	public String toString() {
		return "" + column + row;
	}
	
}
