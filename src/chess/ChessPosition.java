package chess;

import boardGame.Position;

public class ChessPosition {

    // Variaveis do Chess Position
    private char column;
    private int row;

    // Construtor com sistema defensivo
    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8){ // Se a coluna for menor que o 'a' ou coluna for maior que 'h' ou linha for menor que 1 ou linha for maior que 8 imprima a exceção
            throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8");
        }
        this.column = column;
        this.row = row;
    }

    // Somente os Gets
    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    // Método com a lógica para efetuar as jogadas usando cordenadas de um tabuleiro de xadrez.
    protected Position toPosition(){
        return new Position(8 - row, column - 'a');
    }

    // Método para converter as cordenadas da matrix para as cordenadas de um tabuleiro de xadrez. É usado um upcast.
    protected static ChessPosition fromPosition(Position position){
        return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString(){
        return "" + column + row;
    }
}
