package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;

// Herda a classe Piece que atribui mais algumas funções as peças. Ainda é uma classe genérica.
public abstract class ChessPiece extends Piece {

    // Acrescentando a variavel Color
    private Color color;
    private int moveCount;

    // Construtor com super classe board
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    // Método que pega a posição do tabuleiro de xadrez.
    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position); // Retorna a posição da peça no tabuleiro de xadrez.
    }
    // Usando somente o getColor
    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    // Método que aumenta a contagem de movimentos
    public void increaseMoveCount(){
        moveCount++;
    }

    // Método que diminui a contagem de movimentos
    public void decreaseMoveCount(){
        moveCount--;
    }

    // Método para verificar se existe uma peça adversaria em uma determinada casa
    protected boolean isThereOpponentPiece(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position); // Variavel p recebe a peça que estiver nesta posição. Necessario DownCast
        return p != null && p.getColor() != color; // Para concluir que a variavel p é uma peça adversaria
    }

}
