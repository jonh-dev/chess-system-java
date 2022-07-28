package boardGame;

public abstract class Piece {

    // Criação das variaveis position e board
    protected Position position; // Está protected por que não quero que essa posição seja visivel na camada de chess
    private Board board;

    // Contrutor e apenas o Get da variavel board
    public Piece(Board board) {
        this.board = board;
        position = null; // por padrão o java já coloca o valor nulo
    }

    protected Board getBoard() {
        return board;
    }

    // Método abstrato de possiveis movimentos pois é um método generico que será apontado para um método concreto.
    public abstract boolean[][] possibleMoves();

    // Rook Methods que faz um gancho com a subclasse que ira retornar se a peça pode mover pra uma determinada posição ou não. Comumente chamado de teamplate method nos padroes de projeto.
    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()]; // Retorna um possivel movimento com o comando position.getRow para a linha e position.getColumn para a coluna.
    }
    // Método que retorna true se a peça pode fazer movimentos. Caso não possa retorna false
    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves(); // Chama o método abstrato possible moves para varrer a matrix e verificar se existe possibilidade de movimentação da peça
        for (int i = 0; i < mat.length; i++){ // Percorre a linha
            for (int j = 0; j < mat.length; j++){ // Percorre a coluna
                if (mat[i][j]){ // Se a matrix na linha i e coluna j for verdadeira
                    return true; // retorna true
                }
            }
        }
        return false; // Se não for verdadeira, retorna false.
    }
}