package chess.exception;

import boardgame.exception.BoardException;

/**
 * A classe ChessException representa uma exceção específica para erros no jogo de xadrez.
 * Estende a classe BoardException para indicar exceções que podem ocorrer durante a execução de regras do xadrez.
 * 
 * @author Josevan Oliveira
 */
public class ChessException extends BoardException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe uma mensagem de erro para a exceção.
     * 
     * @param msg mensagem de erro para a exceção.
     */
    public ChessException(String msg) {
        super(msg);
    }
}
