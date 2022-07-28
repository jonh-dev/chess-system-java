package boardGame;

public class Position {

    // Criando as variaveis linha e coluna
    private int row;
    private int column;

    // Contrutor e Get and Setter
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    // Operação para atualizar os valores de uma posição
    public void setValues(int row, int column){
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString(){
        return row
                + ", "
                + column;
    }
}
