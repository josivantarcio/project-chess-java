package boardgame;

/**
 * A classe Position representa uma posição no tabuleiro com linhas e colunas.
 * Encapsula os métodos para obter e definir a linha e coluna da posição.
 * 
 * @author Josevan Oliveira
 */
public class Position {
    private int row;
    private int column;

    /**
     * Construtor que inicializa uma posição com as coordenadas de linha e coluna especificadas.
     * 
     * @param row a coordenada da linha da posição.
     * @param column a coordenada da coluna da posição.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retorna a coordenada da linha da posição.
     * 
     * @return a coordenada da linha.
     */
    public int getRow() {
        return row;
    }

    /**
     * Define a coordenada da linha da posição.
     * 
     * @param row a coordenada da linha a ser definida.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Retorna a coordenada da coluna da posição.
     * 
     * @return a coordenada da coluna.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Define a coordenada da coluna da posição.
     * 
     * @param column a coordenada da coluna a ser definida.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Define os valores da linha e coluna da posição.
     * 
     * @param row a coordenada da linha a ser definida.
     * @param column a coordenada da coluna a ser definida.
     */
    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retorna uma representação textual da posição no formato "row, column".
     * 
     * @return uma string representando a posição.
     */
    @Override
    public String toString() {
        return row + ", " + column;
    }
}
