package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    // Codigos para imprimir as cores no console.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // Método para limpar tela.
    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    // Método para fazer a leitura da posição da peça no tabuleiro
    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine(); // Ler uma string s
            char column = s.charAt(0); // Ler um caracter que será a coluna do tabuleiro
            int row = Integer.parseInt(s.substring(1)); // Variavel int que fará o recorte da string e a transfomara em string
            return new ChessPosition(column, row); // Retorna uma nova posição no xadrez em coluna e linha
        } catch (RuntimeException e){ // Qualquer RunTimeException
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8"); // Imprime este erro
        }
    }

    // Método para imprimir uma partida de xadrez
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured){
        printBoard(chessMatch.getPieces()); // Imprimindo o tabuleiro
        System.out.println(); // Quebra de linha
        printCapturedPieces(captured); // Imprimi as peças capturadas
        System.out.println(); // Quebra de linha
        System.out.println("Turn: " + chessMatch.getTurn()); // Imprime o turno que a partida se encontra
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer()); // Imprime qual é o atual jogador que deve jogar.
        if (chessMatch.getCheck()){ // Se durante a partida check acusar true
            System.out.println("CHECK!"); // Imprime Check
        }
    }

    public static void printBoard(ChessPiece[][] pieces){
        for (int i = 0; i < pieces.length; i++){ // Este for percorre as colunas que serão impressas
            System.out.print((8 - i) + " "); // Imprimi ao lado do tabuleiro a numeração das linhas.
            for (int j = 0; j < pieces.length; j++){ // Este for percorre as linhas que serão impressas
                printPiece(pieces[i][j], false); // Imprimi a peça na matrix sem background.
            }
            System.out.println(); // Quebra de linha do tabuleiro
        }
        System.out.println("  a b c d e f g h"); // Imprimi abaixo do tabuleiro as letras de cada coluna
    }

    // Método para imprimir no tabuleiro as possiveis movimentações com cores.
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        for (int i = 0; i < pieces.length; i++){ // Este for percorre as colunas que serão impressas
            System.out.print((8 - i) + " "); // Imprimi ao lado do tabuleiro a numeração das linhas.
            for (int j = 0; j < pieces.length; j++){ // Este for percorre as linhas que serão impressas
                printPiece(pieces[i][j], possibleMoves[i][j]); // Será pintado o fundo colorido dependendo da variavel de possiveis movimentos. Mostrando ao jogador para onde a peça pode se mexer.
            }
            System.out.println(); // Quebra de linha do tabuleiro
        }
        System.out.println("  a b c d e f g h"); // Imprimi abaixo do tabuleiro as letras de cada coluna
    }
    // Método para imprimir apenas 1 peça
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background){ // A peça impressa tem background azul mostrando quais casas pode andar
            System.out.print(ANSI_BLUE_BACKGROUND); // Imprimi a cor de fundo azul
        }
        if (piece == null) { // Se peça é igual a nulo, quer dizer que não tinha peça
            System.out.print("-" + ANSI_RESET); // E então imprime o - e reseta o background
        }
        else {
            if (piece.getColor() == Color.WHITE) { // Se a peça for igual a cor branca caso contrario insira a cor amarela
                System.out.print(ANSI_WHITE + piece + ANSI_RESET); // Insira na pela a cor branca e depois resete a cor
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET); // Insira na pela a cor amarela e depois resete a cor
            }
        }
        System.out.print(" "); // O espaço em brando pra que as peças não fiquem grudadas umas nas outras.
    }

    // Método para imprimir a lista de peças capturadas
    private static void printCapturedPieces(List<ChessPiece> captured){
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList()); // Lista das peças capturadas brancas.
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList()); // Lista das peças capturadas pretas.

        System.out.println("Captured pieces:"); // Imprime a palavra Captured Pieces
        System.out.print("White: "); // Imprime a palavra White:
        System.out.print(ANSI_WHITE); // Imprime a cor branca
        System.out.println(Arrays.toString(white.toArray())); // Imprimindo o array da lista de peças brancas
        System.out.print(ANSI_RESET); // Reseta a cor
        System.out.print("Black: "); // Imprime a palavra Black:
        System.out.print(ANSI_YELLOW); // Imprime a cor amarela, representando as pretas
        System.out.println(Arrays.toString(black.toArray())); // Imprimindo o array da lista de peças pretas
        System.out.print(ANSI_RESET); // Reseta a cor

    }
}
