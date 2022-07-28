package chess.chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;
// Herda de ChessPiece
public class Horse extends ChessPiece {
    // Construtor de King
    public Horse(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "H";
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

        // above and left
        p.setValues(position.getRow() - 1, position.getColumn() - 2); // Definindo um movimento para uma casa acima da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // above and right
        p.setValues(position.getRow() + 1, position.getColumn() - 2); // Definindo um movimento para uma casa baixo da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // left and below
        p.setValues(position.getRow() - 2, position.getColumn() - 1); // Definindo um movimento para uma casa esquerda da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // left and above
        p.setValues(position.getRow() - 2, position.getColumn() + 1); // Definindo um movimento para uma casa direita da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // below and left
        p.setValues(position.getRow() - 1, position.getColumn() + 2); // Definindo um movimento para uma casa noroeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // below and right
        p.setValues(position.getRow() + 1, position.getColumn() + 2); // Definindo um movimento para uma casa nordeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        // right and below
        p.setValues(position.getRow() + 2, position.getColumn() - 1); // Definindo um movimento para uma casa sudoeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true;  // Define como true, para que o movimento seja efetuado na matrix
        }

        // right and above
        p.setValues(position.getRow() + 2, position.getColumn() + 1); // Definindo um movimento para uma casa sudeste da peça
        if (getBoard().positionExists(p) && canMove(p)){ // Se a peça do tabuleiro estiver em uma posição existente e puder se mover
            mat[p.getRow()][p.getColumn()] = true; // Define como true, para que o movimento seja efetuado na matrix
        }

        return mat; // Retorna a matrix mat.
    }
}
