package chess;

import boardGame.BoardException;

// Criando Chess Exception herdamdo BoardException, está exception agora pode pegar exceções de ambas as classes.
public class ChessException extends BoardException {

    private static final long serialVersionUID = 1L;

    public ChessException(String msg){
        super(msg);
    }
}
