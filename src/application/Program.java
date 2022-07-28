package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in); // Instanciando Scanner
        ChessMatch chessMatch = new ChessMatch(); // Instanciando Partida de xadrez
        List<ChessPiece> captured = new ArrayList<>(); // Instanciando lista de peças capturadas no tabuleiro.

        while (!chessMatch.getCheckMate()){ // Enquando não estiver com check mate.
            try {
                UI.clearScreen(); // Método de limpar a tela
                UI.printMatch(chessMatch, captured); // Imprimi a partida de xadrez pela classe UI
                System.out.println(); // Pula uma linha
                System.out.print("Source: "); // Imprime a palavra Source
                ChessPosition source = UI.readChessPosition(sc); // Lendo a posição de origem das peças.

                boolean[][] possibleMoves = chessMatch.possibleMoves(source); // Instanciando o método chessMatch.possible moves a partid da posição de origem
                UI.clearScreen(); // Limpando a tela
                UI.printBoard(chessMatch.getPieces(), possibleMoves); // Imprimindo o tabuleiro com a versão dos movimentos possiveis, sendo assim elas serão coloridas.

                System.out.println(); // Pula linha
                System.out.print("Target: "); // Imprime a palavra Target
                ChessPosition target = UI.readChessPosition(sc); // Lendo a posição alvo descrita pelo usuario

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target); // Realizando o movimento da origem lida ao alvo lido.

                if (capturedPiece != null){ // Se a peça capturada for diferente de nulo
                    captured.add(capturedPiece); // adicione a lista a peça capturada.
                }
            } catch (ChessException e ){ // Caso haja uma ChessException
                System.out.println(e.getMessage()); // Imprimia a mensagem descrita na tela.
                sc.nextLine(); // Enter para pular uma linha
            } catch (InputMismatchException e ){ // Caso haja um InputMismatchException
                System.out.println(e.getMessage()); // Imprima a mensagem descrita na tela.
                sc.nextLine(); // Enter para pular uma linha
            }
        }

        UI.clearScreen(); // Limpa a tela
        UI.printMatch(chessMatch, captured); // Mostra a partida finalizada

    }
}
