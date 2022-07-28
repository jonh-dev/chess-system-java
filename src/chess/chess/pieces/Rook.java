package chess.chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

// Herda de ChessPiece
public class Rook extends ChessPiece {

    // Contrutor de Rooks
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "R";
    } // String com a letra que representará a peça no tabuleiro

    // Método concreto de possible moves da peça rei
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Instancia a matrix apontando para as linhas e as colunas do tabuleiro.

        Position p = new Position(0, 0); // Instanciando uma nova posição em 0

        // above
        p.setValues(position.getRow() - 1, position.getColumn()); // Pega a posição da peça na linha e subtrai 1
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ // Enquanto existir uma posição no tabuleiro e não houver peça
            mat[p.getRow()][p.getColumn()] = true; // As posições estão disponiveis
            p.setRow(p.getRow() - 1); // Mostra que mesmo assim a peça ainda pode se mover mais uma vez para cima.
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se a posição existir e houver oponente
            mat[p.getRow()][p.getColumn()] = true; // A peça poderá avançar sobre aquela posição.
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1); // Pega a posição da peça na coluna e subtrai 1
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ // Enquanto existir uma posição no tabuleiro e não houver peça
            mat[p.getRow()][p.getColumn()] = true; // As posições estão disponiveis
            p.setColumn(p.getColumn() - 1); // Mostra que mesmo assim a peça ainda pode se mover mais uma vez para esquerda.
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se a posição existir e houver oponente
            mat[p.getRow()][p.getColumn()] = true; // A peça poderá avançar sobre aquela posição.
        }

        // right
        p.setValues(position.getRow(), position.getColumn() + 1); // Pega a posição da peça na coluna e adiciona 1
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ // Enquanto existir uma posição no tabuleiro e não houver peça
            mat[p.getRow()][p.getColumn()] = true; // As posições estão disponiveis
            p.setColumn(p.getColumn() + 1); // Mostra que mesmo assim a peça ainda pode se mover mais uma vez para direita.
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se a posição existir e houver oponente
            mat[p.getRow()][p.getColumn()] = true; // A peça poderá avançar sobre aquela posição.
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn()); // Pega a posição da peça na linha e adiciona 1
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ // Enquanto existir uma posição no tabuleiro e não houver peça
            mat[p.getRow()][p.getColumn()] = true; // As posições estão disponiveis
            p.setRow(p.getRow() + 1); // Mostra que mesmo assim a peça ainda pode se mover mais uma vez para baixo.
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se a posição existir e houver oponente
            mat[p.getRow()][p.getColumn()] = true; // A peça poderá avançar sobre aquela posição.
        }

        return mat; // Retorna a matrix mat
    }
}
