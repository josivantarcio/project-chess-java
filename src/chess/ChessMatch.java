package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exception.ChessException;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * A classe ChessMatch representa uma partida de xadrez.
 * Responsável por gerenciar o estado atual do jogo, movimentos de peças, regras de jogo e validações.
 * Extende funcionalidades da classe Board para definir e manipular as peças no tabuleiro.
 * 
 * @author Josevan Oliveira
 */
public class ChessMatch {

    private int turn; // Número de turnos decorridos
    private Color currentPlayer; // Jogador atual
    private Board board; // Tabuleiro de jogo
    private boolean check; // Indica se o jogador atual está em xeque
    private boolean checkMate; // Indica se o jogador atual está em xeque mate
    private ChessPiece enPassant; // Peça que está em situação de en passant
    private ChessPiece promoted; // Peça promovida durante o jogo

    private List<Piece> piecesOnTheBoard = new ArrayList<>(); // Lista de peças no tabuleiro
    private List<Piece> capturedPieces = new ArrayList<>(); // Lista de peças capturadas

    /**
     * Construtor padrão que inicializa uma nova partida de xadrez.
     * Configura o tabuleiro, define o jogador inicial, e realiza a configuração inicial das peças.
     */
    public ChessMatch() {
        board = new Board(8, 8); // Inicializa um novo tabuleiro 8x8
        turn = 1; // Inicia o contador de turnos em 1
        check = false; // Inicializa como não estando em xeque
        currentPlayer = Color.WHITE; // Define que as peças brancas começam primeiro
        initialSetup(); // Configuração inicial das peças no tabuleiro
    }

    /**
     * Retorna o número de turnos decorridos na partida.
     * 
     * @return número de turnos decorridos
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Retorna o jogador atual que deve fazer o próximo movimento.
     * 
     * @return jogador atual
     */
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Verifica se o jogador atual está em xeque.
     * 
     * @return true se o jogador está em xeque, false caso contrário
     */
    public boolean getCheck() {
        return check;
    }

    /**
     * Verifica se o jogador atual está em xeque mate.
     * 
     * @return true se o jogador está em xeque mate, false caso contrário
     */
    public boolean getCheckMate() {
        return checkMate;
    }

    /**
     * Retorna a peça que está em situação de en passant na partida atual.
     * 
     * @return peça em en passant
     */
    public ChessPiece getEnPassant() {
        return enPassant;
    }

    /**
     * Retorna a peça promovida durante a partida.
     * 
     * @return peça promovida
     */
    public ChessPiece getPromoted() {
        return promoted;
    }

    /**
     * Retorna uma matriz bidimensional de peças de xadrez representando o estado atual do tabuleiro.
     * 
     * @return matriz de peças de xadrez
     */
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; // Cria uma matriz para o tabuleiro
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j); // Preenche a matriz com as peças do tabuleiro
            }
        }
        return mat;
    }

    /**
     * Retorna uma matriz de booleanos indicando os movimentos possíveis para a peça na posição de origem fornecida.
     * 
     * @param sourcePosition posição de origem da peça
     * @return matriz de movimentos possíveis
     */
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition(); // Converte ChessPosition para Position
        validateSourcePosition(position); // Valida se há uma peça na posição de origem
        return board.piece(position).possibleMoves(); // Retorna os movimentos possíveis para a peça
    }

    /**
     * Executa o movimento de xadrez da posição de origem para a posição de destino especificadas.
     * Valida o movimento, verifica xeque, xeque mate, promoção e en passant.
     * 
     * @param sourcePosition posição de origem do movimento
     * @param targetPosition posição de destino do movimento
     * @return peça capturada durante o movimento, se houver
     */
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition(); // Converte ChessPosition de origem para Position
        Position target = targetPosition.toPosition(); // Converte ChessPosition de destino para Position
        validateSourcePosition(source); // Valida a posição de origem
        validateTargetPosition(source, target); // Valida a posição de destino
        Piece capturedPiece = makeMove(source, target); // Realiza o movimento e captura, se houver

        if (testCheck(currentPlayer)) { // Verifica se o jogador atual está em xeque
            undoMove(source, target, capturedPiece); // Desfaz o movimento
            throw new ChessException("You can't put yourself in check"); // Lança exceção
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target); // Obtém a peça movida

        // Promoção de peão
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if (movedPiece.getColor() == Color.WHITE && target.getRow() == 0 ||
                movedPiece.getColor() == Color.BLACK && target.getRow() == 7) {
                promoted = (ChessPiece) board.piece(target); // Define a peça promovida como a peça na posição de destino
                promoted = replacePromotedPiece("Q"); // Realiza a promoção (padrão para Rainha)
            }
        }

        check = testCheck(opponent(currentPlayer)); // Verifica se o oponente está em xeque

        if (testCheckMate(opponent(currentPlayer))) { // Verifica se o oponente está em xeque mate
            checkMate = true; // Define xeque mate como verdadeiro
        } else {
            nextTurn(); // Passa para o próximo turno
        }

        // Movimento especial en passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassant = movedPiece; // Define a peça em en passant
        } else {
            enPassant = null; // Caso contrário, não há en passant
        }

        return (ChessPiece) capturedPiece; // Retorna a peça capturada durante o movimento
    }

    /**
     * Substitui a peça promovida por uma nova peça do tipo especificado.
     * 
     * @param type tipo da nova peça ("Q", "R", "B", "N")
     * @return nova peça promovida
     */
    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) { // Verifica se há uma peça para ser promovida
            throw new IllegalStateException("There isn't a piece to be promoted"); // Lança exceção
        }
        if (!type.equals("Q") && !type.equals("R") && !type.equals("B") && !type.equals("N")) {
            return promoted; // Retorna a peça promovida
        }
        Position pos = promoted.getChessPosition().toPosition(); // Obtém a posição da peça promovida
        Piece p = board.removePiece(pos); // Remove a peça do tabuleiro
        piecesOnTheBoard.remove(p); // Remove a peça da lista de peças no tabuleiro

        ChessPiece newPiece = newPiece(type, promoted.getColor()); // Cria uma nova peça do tipo especificado
        board.placePiece(newPiece, pos); // Coloca a nova peça no tabuleiro
        piecesOnTheBoard.add(newPiece); // Adiciona a nova peça à lista de peças no tabuleiro

        return newPiece; // Retorna a nova peça promovida
    }

    /**
     * Cria uma nova peça com base no tipo e cor especificados.
     * 
     * @param type tipo da peça ("B", "N", "Q", "R")
     * @param color cor da peça (Color.WHITE ou Color.BLACK)
     * @return nova peça criada
     */
    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("B")) return new Bishop(board, color); // Cria um Bispo
        if (type.equals("N")) return new Knight(board, color); // Cria um Cavalo
        if (type.equals("Q")) return new Queen(board, color); // Cria uma Rainha
        if (type.equals("R")) return new Rook(board, color); // Cria uma Torre
        return null; // Retorna nulo se o tipo não for reconhecido
    }

    /**
     * Realiza o movimento de uma peça no tabuleiro.
     * 
     * @param source posição de origem do movimento
     * @param target posição de destino do movimento
     * @return peça capturada durante o movimento, se houver
     */
    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source); // Remove a peça da posição de origem
        p.increaseMoveCount(); // Aumenta o contador de movimentos da peça
        Piece capturedPiece = board.removePiece(target); // Remove a peça da posição de destino
        board.placePiece(p, target); // Coloca a peça na posição de destino

        if (capturedPiece != null) { // Se houve captura
            piecesOnTheBoard.remove(capturedPiece); // Remove a peça da lista de peças no tabuleiro
            capturedPieces.add(capturedPiece); // Adiciona a peça à lista de peças capturadas
        }

        // Movimento especial: Roque
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Posição da Torre
            Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Posição de destino da Torre
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // Remove a Torre
            board.placePiece(rook, targetT); // Coloca a Torre na nova posição
            rook.increaseMoveCount(); // Aumenta o contador de movimentos da Torre
        }
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Posição da Torre
            Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Posição de destino da Torre
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // Remove a Torre
            board.placePiece(rook, targetT); // Coloca a Torre na nova posição
            rook.increaseMoveCount(); // Aumenta o contador de movimentos da Torre
        }

        // Movimento especial: En passant
        if (p instanceof Pawn) { // Se a peça for um Peão
            if (source.getColumn() != target.getColumn() && capturedPiece == null) { // Se houve movimento diagonal sem captura
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn()); // Posição do Peão capturado
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn()); // Posição do Peão capturado
                }
                capturedPiece = board.removePiece(pawnPosition); // Remove o Peão capturado
                capturedPieces.add(capturedPiece); // Adiciona o Peão à lista de peças capturadas
                piecesOnTheBoard.remove(capturedPiece); // Remove o Peão da lista de peças no tabuleiro
            }
        }

        return capturedPiece; // Retorna a peça capturada durante o movimento
    }

    /**
     * Desfaz o movimento realizado no tabuleiro.
     * 
     * @param source posição de origem do movimento
     * @param target posição de destino do movimento
     * @param capturedPiece peça capturada durante o movimento
     */
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target); // Remove a peça da posição de destino
        p.decreaseMoveCount(); // Decrementa o contador de movimentos da peça
        board.placePiece(p, source); // Coloca a peça de volta na posição de origem

        if (capturedPiece != null) { // Se houve captura durante o movimento
            board.placePiece(capturedPiece, target); // Coloca a peça capturada de volta na posição de destino
            capturedPieces.remove(capturedPiece); // Remove a peça da lista de peças capturadas
            piecesOnTheBoard.add(capturedPiece); // Adiciona a peça de volta à lista de peças no tabuleiro
        }

        // Desfazimento do movimento especial: Roque
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Posição da Torre
            Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Posição de destino da Torre
            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // Remove a Torre da nova posição
            board.placePiece(rook, sourceT); // Coloca a Torre de volta na posição original
            rook.decreaseMoveCount(); // Decrementa o contador de movimentos da Torre
        }
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Posição da Torre
            Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Posição de destino da Torre
            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // Remove a Torre da nova posição
            board.placePiece(rook, sourceT); // Coloca a Torre de volta na posição original
            rook.decreaseMoveCount(); // Decrementa o contador de movimentos da Torre
        }

        // Desfazimento do movimento especial: En passant
        if (p instanceof Pawn) { // Se a peça for um Peão
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassant) { // Se houve movimento diagonal sem captura
                ChessPiece pawn = (ChessPiece) board.removePiece(target); // Remove o Peão movido para o en passant
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn()); // Posição original do Peão
                } else {
                    pawnPosition = new Position(4, target.getColumn()); // Posição original do Peão
                }
                board.placePiece(pawn, pawnPosition); // Coloca o Peão de volta na posição original
            }
        }
    }

    /**
     * Valida se a posição de origem contém uma peça do jogador atual.
     * 
     * @param position posição de origem a ser validada
     */
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) { // Se não houver peça na posição
            throw new ChessException("There is no piece on source position"); // Lança exceção
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) { // Se a cor da peça na posição de origem não corresponde ao jogador atual
            throw new ChessException("The chosen piece is not yours"); // Lança exceção
        }
        if (!board.piece(position).isThereAnyPossibleMove()) { // Se não houver movimentos possíveis para a peça na posição de origem
            throw new ChessException("There are no possible moves for the chosen piece"); // Lança exceção
        }
    }

    /**
     * Valida se a posição de destino é um movimento válido para a peça na posição de origem.
     * 
     * @param source posição de origem do movimento
     * @param target posição de destino do movimento
     */
    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) { // Se a posição de destino não for um movimento possível para a peça na posição de origem
            throw new ChessException("The chosen piece can't move to target position"); // Lança exceção
        }
    }

    /**
     * Verifica se o jogador especificado está em xeque.
     * 
     * @param color cor do jogador a ser verificado
     * @return true se o jogador está em xeque, false caso contrário
     */
    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition(); // Posição do rei do jogador
        List<Piece> opponentPieces = piecesOnTheBoard.stream() // Lista de peças do oponente
                .filter(piece -> ((ChessPiece) piece).getColor() == opponent(color))
                .collect(Collectors.toList());
        for (Piece piece : opponentPieces) { // Para cada peça do oponente
            boolean[][] mat = piece.possibleMoves(); // Movimentos possíveis da peça
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // Se o rei está na linha ou coluna de ataque da peça
                return true; // O jogador está em xeque
            }
        }
        return false; // O jogador não está em xeque
    }

    /**
     * Verifica se o jogador especificado está em xeque mate.
     * 
     * @param color cor do jogador a ser verificado
     * @return true se o jogador está em xeque mate, false caso contrário
     */
    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) { // Se o jogador não estiver em xeque
            return false; // Não está em xeque mate
        }
        List<Piece> list = piecesOnTheBoard.stream() // Lista de peças do jogador
                .filter(piece -> ((ChessPiece) piece).getColor() == color)
                .collect(Collectors.toList());
        for (Piece piece : list) { // Para cada peça do jogador
            boolean[][] mat = piece.possibleMoves(); // Movimentos possíveis da peça
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) { // Se há algum movimento possível
                        Position source = ((ChessPiece) piece).getChessPosition().toPosition(); // Posição de origem da peça
                        Position target = new Position(i, j); // Nova posição de destino
                        Piece capturedPiece = makeMove(source, target); // Realiza o movimento
                        boolean testCheck = testCheck(color); // Verifica se ainda está em xeque
                        undoMove(source, target, capturedPiece); // Desfaz o movimento
                        if (!testCheck) { // Se não estiver em xeque
                            return false; // Não está em xeque mate
                        }
                    }
                }
            }
        }
        return true; // Está em xeque mate
    }

    /**
     * Passa para o próximo turno da partida de xadrez.
     */
    private void nextTurn() {
        turn++; // Incrementa o contador de turnos
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // Alterna o jogador atual
    }

    /**
     * Retorna o rei da cor especificada.
     * 
     * @param color cor do rei a ser retornado
     * @return rei da cor especificada
     */
    private King king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream() // Lista de peças do jogador
                .filter(piece -> ((ChessPiece) piece).getColor() == color)
                .collect(Collectors.toList());
        for (Piece piece : list) { // Para cada peça do jogador
            if (piece instanceof King) { // Se a peça for um Rei
                return (King) piece; // Retorna o Rei
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board"); // Lança exceção se não houver Rei da cor especificada
    }

    /**
     * Retorna a cor do oponente especificado.
     * 
     * @param color cor do jogador
     * @return cor do oponente
     */
    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // Retorna a cor oposta ao jogador especificado
    }

    /**
     * Coloca uma nova peça de xadrez no tabuleiro na posição e cor especificadas.
     * 
     * @param column coluna do tabuleiro (a-h, 1-8)
     * @param row linha do tabuleiro (1-8)
     * @param piece peça de xadrez a ser colocada
     */
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); // Coloca a peça no tabuleiro na posição especificada
        piecesOnTheBoard.add(piece); // Adiciona a peça à lista de peças no tabuleiro
    }

    /**
     * Configuração inicial das peças no tabuleiro de xadrez.
     */
    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE)); // Torre branca em a1
        placeNewPiece('b', 1, new Knight(board, Color.WHITE)); // Cavalo branco em b1
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE)); // Bispo branco em c1
        placeNewPiece('d', 1, new Queen(board, Color.WHITE)); // Rainha branca em d1
        placeNewPiece('e', 1, new King(board, Color.WHITE, this)); // Rei branco em e1
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE)); // Bispo branco em f1
        placeNewPiece('g', 1, new Knight(board, Color.WHITE)); // Cavalo branco em g1
        placeNewPiece('h', 1, new Rook(board, Color.WHITE)); // Torre branca em h1
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em a2
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em b2
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em c2
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em d2
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em e2
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em f2
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em g2
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this)); // Peão branco em h2

        placeNewPiece('a', 8, new Rook(board, Color.BLACK)); // Torre preta em a8
        placeNewPiece('b', 8, new Knight(board, Color.BLACK)); // Cavalo preto em b8
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK)); // Bispo preto em c8
        placeNewPiece('d', 8, new Queen(board, Color.BLACK)); // Rainha preta em d8
        placeNewPiece('e', 8, new King(board, Color.BLACK, this)); // Rei preto em e8
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK)); // Bispo preto em f8
        placeNewPiece('g', 8, new Knight(board, Color.BLACK)); // Cavalo preto em g8
        placeNewPiece('h', 8, new Rook(board, Color.BLACK)); // Torre preta em h8
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em a7
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em b7
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em c7
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em d7
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em e7
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em f7
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em g7
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this)); // Peão preto em h7
    }
}
