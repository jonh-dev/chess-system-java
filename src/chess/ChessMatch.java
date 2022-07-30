package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    // Criação das variaveis
    private  int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    // Lista de peças no tabuleiro e peças capturadas.
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    // Contrutor
    public ChessMatch(){
        board = new Board(8, 8); // Instanciação de um novo board com 8 linhas e 8 colunas.
        turn = 1; // Inicia no turno 1
        currentPlayer = Color.WHITE; // Inicia com as peças brancas
        initialSetup(); // Iniciando a posição das peças
    }

    // Métodos get
    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; // Instancia ChessPiece com o tamanho de linhas e colunas do Board
        for (int i = 0; i < board.getRows(); i++){ // Percorre todas as linas do tabuleiro
            for (int j = 0; j < board.getColumns(); j++){ // Percorre todas as colunas do tabuleiro
                mat[i][j] = (ChessPiece) board.piece(i, j); // A matrix mat recebe as peças do tabuleiro nas linhas e colunas. É feito um DownCast para ser interpretado como uma peça de xadrez.
            }
        }
        return mat; // Retorna mat
    }

    // Método que retorna possiveis movimentos que serão coloridos ao fundo
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition(); // Convertendo a posição de xadrez para posição de matrix normal
        validateSourcePosition(position); // Validanto está posição de origem
        return board.piece(position).possibleMoves(); // Retornando os movimentos possiveis desta peça
    }
    // Método para retirar a peça da posição de origem e coloca-la na posição de destino
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition(); // Posição atual
        Position target = targetPosition.toPosition(); // Posição apontada
        validateSourcePosition(source); // Validando se na posição de origem ha uma peça
        validateTargetPosition(source, target); // Implementando a função para validar a posição de origem e a posição de destino
        Piece capturedPiece = makeMove(source, target); // Captura uma peça durante o movimento

        if (testCheck(currentPlayer)){ // Se o atual jogador se colocar em check
            undoMove(source, target, capturedPiece); // Chama o método voltar movimento
            throw new ChessException("You can't put yourself in check"); // Imprimi você não pode se colocar em check
        }

        ChessPiece movedPiece = (ChessPiece)board.piece(target); // Pega a peça que se moveu, que agora se encontra no destino

        // # Special move promotion

        promoted = null; // Promoção começa como nulo
        if (movedPiece instanceof Pawn) { // Se a peça movida for peão
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7))){ // Se a peça movida for igual a cor branca e chegar a linha 0 da matrix ou a peça movida for preta e chegar a linha 8 da matrix
                promoted = (ChessPiece) board.piece(target); // A peça que chegar a esse trajeto recebe a promoção
                promoted = replacePromotedPiece("Q"); // E por padrão é trocada pela Rainha
            }
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false; // Caso o oponente fique em check retorna true, caso contrario retorna false.

        if (testCheck(opponent(currentPlayer))) { // Se o testeCheck permanecer no atual oponente
            checkMate = true; // Check mate é true, ou seja o jogo acabou.
        }

        nextTurn(); // Implementa o método para troca de turno.

        // #Special Move En Passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) { // Se a peça movida for peão e o trajeto de destino for menor ou maior que duas linhas da origem, este peão está vulneravel ao En Passant
            enPassantVulnerable = movedPiece; // E a peça adversaria pode efetuar o movimento
        } else {
            enPassantVulnerable = null; // Caso contrario a peça não está vulneravel ao En Passant
        }

        return (ChessPiece) capturedPiece; // Retorna a peça capturada, é feito um DownCast
    }

    // Método para troca de peça promovida
    public ChessPiece replacePromotedPiece(String type){
        if (promoted == null) { // Se não houver promoção
            throw new IllegalStateException("There is no piece to be promoted"); // Imprimi está exceção
        }
        if (!type.equals("B") && !type.equals("H") && !type.equals("R") && !type.equals("Q")){ // Se o tipo de string não for igual a B, H, R e Q
            return promoted; // Retorne a rainha por padrão
        }

        Position pos = promoted.getChessPosition().toPosition(); // Pega a posição da peça promovida
        Piece p = board.removePiece(pos); // Remove a peça que está nesta posição
        piecesOnTheBoard.remove(p); // E remove a peça do tabuleiro

        ChessPiece newPiece = newPiece(type, promoted.getColor()); // Pega a nova peça em sua respectiva cor
        board.placePiece(newPiece, pos); // Coloca a nova peça na posição onde antes havia o peão
        piecesOnTheBoard.add(newPiece); // E adiciona a nova peça ao tabuleiro

        return newPiece; // Retorna a nova peça

    }

    // Método para nova peça da promoção
    private ChessPiece newPiece(String type, Color color){
        if (type.equals("B")) return new Bishop(board, color); // Se a string for igual a B, retorne um novo bishop ao tabuleiro na cor representante
        if (type.equals("H")) return new Horse(board, color); // Se a string for igual a H, retorne um novo horse ao tabuleiro na cor representante
        if (type.equals("Q")) return new Queen(board, color); // Se a string for igual a Q, retorne um novo queen ao tabuleiro na cor representante
        return new Rook(board, color); // Se a string for igual a R, retorne um novo rook ao tabuleiro na cor representante
    }


    // Método responsavel por fazer o movimento das peças
    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece) board.removePiece(source); // Retirando a peça da posição de origem, feito um DownCast para poder aumentar a quantidade de movimentos.
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target); // Removendo a possivel peça que esteja na posição de destino, por padrão será a peça capturada.
        board.placePiece(p, target); // Agora pode ser colocada a peça que estava na posição de origem na posição de destino.

        if (capturedPiece != null){ // Se a peça capturada for diferente de null
            piecesOnTheBoard.remove(capturedPiece); // Remova a peça do tabuleiro
            capturedPieces.add(capturedPiece); // Adicione a peça capturada na lista de peças capturadas.
        }

        // # Special move castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){ // Se a peça for Rei e sua casa de destino na coluna for igual a casa de origem na coluna mais 2
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Pegue a posição de origem da torre que se encontra na casa de origem do Rei na coluna mais 3
            Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Leve a torre para a posição de destino que se encontra na casa origem do Rei na coluna mais 1

            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // Remova a torre de sua posição de origem
            board.placePiece(rook, targetT); // Leve a torre para a posição de destino
            rook.increaseMoveCount(); // Aumente a contagem de movimento da torre
        }

        // # Special move castling Queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){ // Se a peça for Rei e sua casa de destino na coluna for igual a casa de origem na coluna menos 2
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Pegue a posição de origem da torre que se encontra na casa de origem do Rei na coluna menos 4
            Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Leve a torre para a posição de destino que se encontra na casa origem do Rei na coluna menos 1

            ChessPiece rook = (ChessPiece) board.removePiece(sourceT); // Remova a torre de sua posição de origem
            board.placePiece(rook, targetT); // Leve a torre para a posição de destino
            rook.increaseMoveCount(); // Aumente a contagem de movimento da torre
        }

        // # Special move En Passant

        if (p instanceof Pawn){ // Se a peça for Peão
            if (source.getColumn() != target.getColumn() && capturedPiece == null){ // Se a posição de origem for diferente da posição de destino e não tenha nenhuma peça capturada
                Position pawnPosition; // Pegando a posição do peão
                if (p.getColor() == Color.WHITE){ // Se a peça p for branca
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn()); // A posição do peão oponente que será capturada estara uma linha abaixo
                } else { // Se a peça for preta
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn()); // A posição do peão oponente que será capturada estara uma linha acima
                }
                capturedPiece = board.removePiece(pawnPosition); // Remova a peça capturada
                capturedPieces.add(capturedPiece); // Adiciona a peça capturada na lista de peças capturadas
                piecesOnTheBoard.remove(capturedPiece); // Remove a peça capturada da lista de peças no tabuleiro
            }
        }

        return capturedPiece; // Retorna a peça capturada.
    }

    // Método para retornar um movimento
    private void undoMove(Position source, Position target, Piece capturedPiece){ // Pega a posição de origem, destino e a peça capturada.
        ChessPiece p = (ChessPiece)board.removePiece(target); // Tira a peça que você moveu para o destino, feito um DownCast para implementar a diminuição dos movimentos
        p.decreaseMoveCount(); // Implementa a diminuição dos movimentos.
        board.placePiece(p, source); // E coloca a peça na posição de origem

        if (capturedPiece != null){ // Se a peça capturada for diferente de null
            board.placePiece(capturedPiece, target); // Volte está peça para o tabuleiro na posição de destino
            capturedPieces.remove(capturedPiece); // Remova a peça da lista de peças capturadas
            piecesOnTheBoard.add(capturedPiece); // Adiciona novamente a peça ao tabuleiro
        }

        // # Special move castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){ // Se a peça for Rei e sua casa de destino na coluna for igual a casa de origem na coluna mais 2
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3); // Pegue a posição de origem da torre que se encontra na casa de origem do Rei na coluna mais 3
            Position targetT = new Position(source.getRow(), source.getColumn() + 1); // Leve a torre para a posição de destino que se encontra na casa origem do Rei na coluna mais 1

            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // Remova a torre de sua posição de destino
            board.placePiece(rook, sourceT); // Leve a torre para a posição de origem
            rook.decreaseMoveCount(); // Decrementa a contagem de movimento da torre
        }

        // # Special move castling Queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){ // Se a peça for Rei e sua casa de destino na coluna for igual a casa de origem na coluna menos 2
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4); // Pegue a posição de origem da torre que se encontra na casa de origem do Rei na coluna menos 4
            Position targetT = new Position(source.getRow(), source.getColumn() - 1); // Leve a torre para a posição de destino que se encontra na casa origem do Rei na coluna menos 1

            ChessPiece rook = (ChessPiece) board.removePiece(targetT); // Remova a torre de sua posição de destino
            board.placePiece(rook, source); // Leve a torre para a posição de origem
            rook.decreaseMoveCount(); // Decrementa a contagem de movimento da torre
        }

        // # Special move En Passant

        if (p instanceof Pawn){ // Se a peça for Peão
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable){ // Se a posição de origem for diferente da posição de destino e não tenha nenhuma peça capturada
                ChessPiece pawn = (ChessPiece)board.removePiece(target); // Remove o peão capturado pelo en passant da posição de destino errada.
                Position pawnPosition; // Pegando a posição do peão
                if (p.getColor() == Color.WHITE){ // Se a peça p for branca
                    pawnPosition = new Position(3, target.getColumn()); // Recoloca a peça capturada do en passant novamente em sua posição
                } else { // Se a peça for preta
                    pawnPosition = new Position(4, target.getColumn()); // Recoloca a peça capturada do en passant novamente em sua posição
                }
                board.placePiece(pawn, pawnPosition); // Recoloca o peão oponente capturado pelo movimento en passant novamente no tabuleiro.c2
            }
        }
    }

    // Método para verificar se a posição é valida.
    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)){ // Se não existir uma peça nesta posição
            throw new ChessException("There is no piece on source position"); // Imprime está exceção
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){ // Se a cor for diferente do jogador atual. Tem um DownCast
            throw new ChessException("The chosen piece is not yours"); // Imprime está exceção
        }
        if (!board.piece(position).isThereAnyPossibleMove()){ // Se não existir a possibilidade de movimentação da peça
            throw new ChessException("There is no possible moves for the chosen piece"); // Retorna está exceção
        }
    }

    // Método para validar a posição de destino
    private void validateTargetPosition(Position source, Position target){
        if (!board.piece(source).possibleMove(target)){ // Se para a peça na posição de origem a posição de destino não é um movimento possivel
            throw new ChessException("The chosen piece can't move to target position"); // Imprima está exceção
        }
    }

    // Método para mudar o turno do jogador
    private void nextTurn(){
        turn++; // Soma mais 1 a cada turno
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // Caso o jogador atual seja peças brancas no proximo turno será as pretas. E assim sucessivamente.
    }

    // Método para diferenciar os oponentes com base na cor.
    private Color opponent (Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // Se a cor for branca, retorna a cor preta, caso contrario a cor branca novamente.
    }

    // Método para descobrir as peças Reis baseada em suas respectivas cores.
    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList()); // Filtra na lista de qual cor é determinada peça Rei. É feito um DownCast de Piece para ChessPiece.
        for (Piece p : list){ // Para cada peça p na minha lista list
            if (p instanceof King){ // Se a peça p é uma instancia da peça Rei
                return (ChessPiece) p; // Retorna a peça o. É feito um DownCast de Piece para ChessPiece.
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    // Método para testar se a peça Rei está em Check
    private boolean testCheck(Color color){ // Este método traz cor como argumento
        Position kingPosition = king(color).getChessPosition().toPosition(); // Pegando a posição do Rei no formato de matrix.
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList()); // Filtra uma lista das peças do oponente desta cor.
        for (Piece p : opponentPieces){ // Para cada peça p na lista de peça do oponente
            boolean[][] mat = p.possibleMoves(); // Matrix de movimento possiveis dessa peça adversaria p.
            if (mat [kingPosition.getRow()][kingPosition.getColumn()]){ // Se a matrix na linha do Rei e na coluna do Rei
                return true; // Retorna true.
            }
        }
        return false; // Se não houver check, retorna false.
    }

    // Método testando Check Mate
    private boolean testeCheckMate(Color color){
        if (!testCheck(color)){ // Se está cor não estiver em check;
            return false; // Retorna false, pois a mesma não está em check mate
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList()); // Filtra na lista de qual cor é determinada peça Rei. É feito um DownCast de Piece para ChessPiece.
        for (Piece p : list) { // Para cada peça p na lista do tabuleiro
            boolean[][] mat = p.possibleMoves(); // Criando uma matrix de possiveis movimentos da peça
            for (int i = 0; i < board.getRows(); i++){ // Percorre todas as linas do tabuleiro
                for (int j = 0; j < board.getColumns(); j++){ // Percorre todas as colunas do tabuleiro
                    if (mat[i][j]){ // Se tiver no i e no j que seja um movimento possivel tira do check
                        Position source = ((ChessPiece)p).getChessPosition().toPosition(); // Pegando a posição de origem da peça p. Necessario um DownCast para poder pegar o Chess Position.
                        Position target = new Position(i, j); // Pegando a posição de destino de acordo com a matrix mat.
                        Piece capturedPiece = makeMove(source, target); // Fazendo o movimento da origem para o destino.
                        boolean testCheck = testCheck(color); // Tester se o rei da cor atual ainda está em check
                        undoMove(source, target, capturedPiece); // Desfazer o movimento teste.
                        if (!testCheck){ // Se não estiver em check ainda
                            return false; // Retorna false, pois não está em check mate.
                        }
                    }
                }
            }
        }
        return true; // Retorna true, está em check mate.
    }

    // Método para receber as coordenadas do xadrez para inseri-las no tabuleiro
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); // Adicionando a peça em determinada posição no tabuleiro
        piecesOnTheBoard.add(piece); // Adicionando a peça a lista de peças que estão no tabuleiro.
    }
    // Método ultilizado para iniciação das peças de xadrez
    private void initialSetup(){
        // Peças inseridas no tabuleiro
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Horse(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Horse(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Horse(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Horse(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
