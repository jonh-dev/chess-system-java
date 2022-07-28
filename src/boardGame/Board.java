package boardGame;

public class Board {

    // Criação das variaveis rows, columns e pieces como matrix
    private int rows;
    private int columns;
    private Piece[][] pieces;

    // Construtor e apenas Get das variaveis Rows e Columns
    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1){ // Criando exceção caso a quantidade de linhas for menor que 1 e a de colunas também, então imprime a exceção
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns]; // Instanciando pieces com a quantidade de linhas e colunas
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column){
        if (!positionExists(row, column)){ // Se está posição não existir, imprime a exceção
            throw new BoardException("Position not on the board");
        }
        return pieces[row][column]; // Retorna a matrix pieces na linha row e coluna column
    }

    public Piece piece(Position position){
        if (!positionExists(position)){ // Se está posição não existir, imprime a exceção
            throw new BoardException("Position not on the board");
        }
        return pieces[position.getRow()][position.getColumn()]; // Retorna na posição da linha e na coluna da linha
    }

    public void placePiece(Piece piece, Position position){
        if (thereIsAPiece(position)){ // Se existir uma peça na atual posição, imprimi a exceção
            throw new BoardException("There is already a piece on position " + position);
        }

        pieces[position.getRow()][position.getColumn()] = piece; // Posicionando uma peça no tabuleiro.
        piece.position = position; // A peça não está mais nula, agora está na posição informada
    }

    // Método para remover uma peça com um sistema defensivo
    public Piece removePiece(Position position){
        if (!positionExists(position)){ // Se está posição não exixtir
            throw new BoardException("Position not on the board"); // Lance está execeção
        }
        if (piece(position) == null){ // Se está posição for igual a nulo
            return null; // Retorna nulo
        }
        Piece aux = piece(position); // Declarando uma variação para a peça que estiver nessa posição
        aux.position = null; // A posição desta peça é nulo
        pieces[position.getRow()][position.getColumn()] = null; // Essa matrix na linha.getRow e na coluna.getColumn é igual a nulo
        return aux; // Retorna a peça aux
    }
    // Metodo usado para verificar quando uma linha e uma coluna existem.
    private boolean positionExists(int row, int column){
        return row >= 0 && row < rows && column >= 0 && column < columns; // Linha tem que ser maior ou igual a 0 e menor que o atributo rows, assim como a coluna.
    }
    // Metodo para retornar a posição das peças
    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn()); // Retorna a posição da linha e a coluna.
    }

    public boolean thereIsAPiece(Position position){
        if (!positionExists(position)){ // Se existir uma peça na atual posição, imprimi a exceção
            throw new BoardException("Position not on the board");
        }
        return piece(position) != null; // Se a peça for diferente de nulo essa peça retorna uma posição
    }
}
