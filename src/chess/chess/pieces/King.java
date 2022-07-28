package chess.chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;
// Herda de ChessPiece
public class King extends ChessPiece {

    private ChessMatch chessMatch;

    // Construtor de King
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

    // Método que testa se a torre está apta a fazer o castling
    private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position); // Pegando a peça na posição informada
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; // Retorna se a pela for nula e a peça for Rook e da mesma cor e sem movimentos está apta para o castling
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

        // # Special move castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()){ // Se o rei não tiver feito movimento e não estiver em check
            // # Special move castling kingside rook
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // Pega a posição da torre por parte do lado do Rei
            if (testRookCastling(posT1)){ // Se o teste do castling na posição da torre for true
                Position p1 = new Position(position.getRow(), position.getColumn() + 1); // Pega a posição de uma casa ao lado direito do rei
                Position p2 = new Position(position.getRow(), position.getColumn() + 2); // Pega a posição de duas casas ao lado direito do rei
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null){ // Se as posições p1 e p2 forem nulas
                    mat[position.getRow()][position.getColumn() + 2] = true; // Então a posição duas casas a direita do Rei na matrix se torna um movimento possivel
                }
            }

            // # Special move castling Queenside rook
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // Pega a posição da torre por parte do lado do Rei
            if (testRookCastling(posT1)){ // Se o teste do castling na posição da torre for true
                Position p1 = new Position(position.getRow(), position.getColumn() - 1); // Pega a posição de uma casa ao lado esquerdo do rei
                Position p2 = new Position(position.getRow(), position.getColumn() - 2); // Pega a posição de duas casas ao lado esquerdo do rei
                Position p3 = new Position(position.getRow(), position.getColumn() - 3); // Pega a posição de tres casas ao lado esquerdo do rei
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){ // Se as posições p1, p2 e p3 forem nulas
                    mat[position.getRow()][position.getColumn() - 2] = true; // Então a posição duas casas a esquerda do Rei na matrix se torna um movimento possivel
                }
            }
        }

        return mat; // Retorna a matrix mat.
    }
}
