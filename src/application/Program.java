package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.exception.ChessException;

/**
 * A classe Program é o ponto de entrada para o aplicativo de xadrez.
 * Ele gerencia a lógica do jogo e a interação do usuário.
 */
public class Program {

    /**
     * O método principal que inicia o jogo de xadrez.
     * @param args argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Scanner para ler a entrada do usuário
        Scanner scan = new Scanner(System.in);

        // Instância do jogo de xadrez
        ChessMatch cm = new ChessMatch();

        // Lista para armazenar as peças capturadas
        List<ChessPiece> captured = new ArrayList<ChessPiece>();

        // Loop principal do jogo que continua até que haja um checkmate
        while (!cm.getCheckMate()) {
            try {
                // Limpa a tela e imprime o estado atual do jogo
                UI.clearScreen();
                UI.printMatch(cm, captured);
                System.out.println();

                // Solicita a posição de origem da peça a ser movida
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scan);

                // Obtém os movimentos possíveis para a peça na posição de origem
                boolean[][] possibleMoves = cm.possibleMoves(source);
                
                // Limpa a tela e imprime o tabuleiro com os movimentos possíveis destacados
                UI.clearScreen();
                UI.printBoard(cm.getPieces(), possibleMoves);

                // Solicita a posição de destino para mover a peça
                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scan);

                // Realiza o movimento e obtém a peça capturada, se houver
                ChessPiece capturedPiece = cm.performChessMove(source, target);

                // Se uma peça foi capturada, adiciona à lista de capturadas
                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                // Verifica se há uma promoção de peça pendente
                if (cm.getPromoted() != null) {
                    // Solicita ao usuário o tipo de peça para promoção
                    System.out.print("Enter piece for promotion [B/N/Q/R]: ");
                    String type = scan.nextLine().toUpperCase();

                    // Valida a entrada do usuário para o tipo de peça de promoção
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.print("Invalid Value! Enter again piece for promotion [B/N/Q/R]: ");
                        type = scan.nextLine().toUpperCase();
                    }

                    // Substitui a peça promovida pelo tipo escolhido
                    cm.replacePromotedPiece(type);
                }

            // Captura exceções específicas do jogo de xadrez e exibe a mensagem de erro
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                scan.nextLine();
            
            // Captura exceções de entrada inválida e exibe a mensagem de erro
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scan.nextLine();
            }
        }

        // Limpa a tela e imprime o estado final do jogo após o checkmate
        UI.clearScreen();
        UI.printMatch(cm, captured);
    }
}
