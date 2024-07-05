package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 * Classe abstrata que representa uma peça de xadrez.
 */
public abstract class ChessPiece extends Piece {
	
	/** Cor da peça */
	private Color color;
	
	/** Contagem de movimentos da peça */
	private int moveCount;

	/**
	 * Construtor que inicializa uma peça de xadrez com o tabuleiro e cor especificados.
	 * 
	 * @param board Tabuleiro onde a peça será colocada.
	 * @param color Cor da peça (Branco ou Preto).
	 */
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	/**
	 * Retorna a cor da peça.
	 * 
	 * @return Cor da peça.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Retorna o número de movimentos feitos pela peça.
	 * 
	 * @return Número de movimentos feitos pela peça.
	 */
	public int getMoveCount() {
		return moveCount;
	}
	
	/**
	 * Incrementa o contador de movimentos da peça.
	 */
	public void increaseMoveCount() {
		moveCount++;
	}
	
	/**
	 * Decrementa o contador de movimentos da peça.
	 */
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	/**
	 * Verifica se há uma peça adversária na posição especificada.
	 * 
	 * @param position Posição a ser verificada.
	 * @return true se há uma peça adversária na posição, false caso contrário.
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
	
	/**
	 * Retorna a posição da peça de xadrez no formato de coordenadas do xadrez.
	 * 
	 * @return Posição da peça de xadrez.
	 */
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
}
