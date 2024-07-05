package boardgame.exception;

/**
 * A classe BoardException representa uma exceção personalizada para erros relacionados ao tabuleiro do jogo.
 * Estende a classe RuntimeException para indicar exceções que podem ocorrer durante a manipulação do tabuleiro.
 * 
 * @author Josevan Oliveira
 */
public class BoardException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe uma mensagem de erro para a exceção.
     * 
     * @param msg mensagem de erro para a exceção.
     */
    public BoardException(String msg) {
        super(msg);
    }
}
