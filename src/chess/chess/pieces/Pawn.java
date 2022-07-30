package chess.chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    // Declarando Variaveis
    private ChessMatch chessMatch;

    // Construtor
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

            // # Special move En Passant White
            if (position.getRow() == 3) { // Se a posição for a linha 3 da matrix
                Position left = new Position(position.getRow(), position.getColumn() - 1); // Pegando a posição do peão vulneravel ao En Passant que está a esquerda do peão
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) { // Se a posição a esquerda existir e lá exixstir uma peça e essa peça esteja vulneravel ao En Passant
                    mat[left.getRow() - 1][left.getColumn()] = true; // Então o peão poderá se mover acima da peça do oponente e realizar o En Passant
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1); // Pegando a posição do peão vulneravel ao En Passant que está a esquerda do peão
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) { // Se a posição a esquerda existir e lá exixstir uma peça e essa peça esteja vulneravel ao En Passant
                    mat[right.getRow() - 1][right.getColumn()] = true; // Então o peão poderá se mover acima da peça do oponente e realizar o En Passant
                }
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

            // # Special move En Passant Black
            if (position.getRow() == 4) { // Se a posição for a linha 3 da matrix
                Position left = new Position(position.getRow(), position.getColumn() - 1); // Pegando a posição do peão vulneravel ao En Passant que está a esquerda do peão
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) { // Se a posição a esquerda existir e lá exixstir uma peça e essa peça esteja vulneravel ao En Passant
                    mat[left.getRow() + 1][left.getColumn()] = true; // Então o peão poderá se mover acima da peça do oponente e realizar o En Passant
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1); // Pegando a posição do peão vulneravel ao En Passant que está a esquerda do peão
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) { // Se a posição a esquerda existir e lá exixstir uma peça e essa peça esteja vulneravel ao En Passant
                    mat[right.getRow() + 1][right.getColumn()] = true; // Então o peão poderá se mover acima da peça do oponente e realizar o En Passant
                }
            }
        }

        return mat;
    }

    @Override
    public String toString(){
        return "P";
    }
}
