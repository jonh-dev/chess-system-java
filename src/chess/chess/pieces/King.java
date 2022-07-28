package chess.chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;
// Herda de ChessPiece
public class King extends ChessPiece {
    // Construtor de King
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "K";
    } // String que representa a peça no tabuleiro

    // Método criado para ver se a peça se pode mover para determinada posição
    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position); // Pega a peça desta posição
        return p == null || p.getColor() != getColor(); // E verifica se ela não é nula e que seja diferente da sua cor.
    }
    // Método concreto de possible moves da peça rei
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Instancia a matrix apontando para as linhas e as colunas do tabuleiro.

        Position p = new Position(0, 0);

        // above
        p.setValues(position.getRow() - 1, position.getColumn()); // Definindo um movimento para uma casa acima da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn()); // Definindo um movimento para uma casa baixo da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1); // Definindo um movimento para uma casa esquerda da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // right
        p.setValues(position.getRow(), position.getColumn() + 1); // Definindo um movimento para uma casa direita da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // nw
        p.setValues(position.getRow() - 1, position.getColumn() - 1); // Definindo um movimento para uma casa noroeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // ne
        p.setValues(position.getRow() - 1, position.getColumn() + 1); // Definindo um movimento para uma casa nordeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // sw
        p.setValues(position.getRow() + 1, position.getColumn() - 1); // Definindo um movimento para uma casa sudoeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true;  // Define como true, para que o movimento seja efetuado na matrix
        }

        // se
        p.setValues(position.getRow() + 1, position.getColumn() + 1); // Definindo um movimento para uma casa sudeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        return mat; // Retorna a matrix mat.
    }
}
