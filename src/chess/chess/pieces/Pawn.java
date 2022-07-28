package chess.chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {


    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // Instancia a matrix apontando para as linhas e as colunas do tabuleiro.

        Position p = new Position(0, 0); // Instanciando uma nova posição em 0

        if (getColor() == Color.WHITE){ // Se a cor de peão for branca
            p.setValues(position.getRow() - 1, position.getColumn()); // Pega a posição do peão na linha e diminui 1 na matrix
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ // Se houve a posição no tabuleiro e a mesma não houver peça
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
            p.setValues(position.getRow() - 2, position.getColumn()); // Pega a posição do peão na linha e diminui 2 na matrix
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){ // Se houve a posição no tabuleiro nas posições p1 e p2 e a mesma não houver peça e a contagem de movimentos for 0
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
            p.setValues(position.getRow() - 1, position.getColumn() - 1); // Pega a posição do peão na linha e na coluna e diminui 1 na matrix
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se houve a posição no tabuleiro existe e se houver peça do oponente na mesma
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
            p.setValues(position.getRow() - 1, position.getColumn() + 1); // Pega a posição do peão na linha e na coluna e diminui 1 na matrix
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se houve a posição no tabuleiro existe e se houver peça do oponente na mesma
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
        } else {
            p.setValues(position.getRow() + 1, position.getColumn()); // Pega a posição do peão na linha e diminui 1 na matrix
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){ // Se houve a posição no tabuleiro e a mesma não houver peça
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
            p.setValues(position.getRow() + 2, position.getColumn()); // Pega a posição do peão na linha e diminui 2 na matrix
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){ // Se houve a posição no tabuleiro nas posições p1 e p2 e a mesma não houver peça e a contagem de movimentos for 0
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
            p.setValues(position.getRow() + 1, position.getColumn() - 1); // Pega a posição do peão na linha e na coluna e diminui 1 na matrix
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se houve a posição no tabuleiro existe e se houver peça do oponente na mesma
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
            p.setValues(position.getRow() + 1, position.getColumn() + 1); // Pega a posição do peão na linha e na coluna e diminui 1 na matrix
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){ // Se houve a posição no tabuleiro existe e se houver peça do oponente na mesma
                mat[p.getRow()][p.getColumn()] = true; // Então o peão poderá ir até essa posição
            }
        }

        return mat;
    }

    @Override
    public String toString(){
        return "P";
    }
}
