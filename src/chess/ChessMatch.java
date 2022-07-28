package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.chess.pieces.King;
import chess.chess.pieces.Pawn;
import chess.chess.pieces.Rook;

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

        check = (testCheck(opponent(currentPlayer))) ? true : false; // Caso o oponente fique em check retorna true, caso contrario retorna false.

        if (testCheck(opponent(currentPlayer))) { // Se o testeCheck permanecer no atual oponente
            checkMate = true; // Check mate é true, ou seja o jogo acabou.
        }

        nextTurn(); // Implementa o método para troca de turno.
        return (ChessPiece) capturedPiece; // Retorna a peça capturada, é feito um DownCast
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
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }
}
