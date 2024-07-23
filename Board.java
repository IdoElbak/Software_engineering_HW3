public class Board {

    /**
     * Number of rows in the board
     * */
    private int rows;

    /**
     * Number of columns in the board
     * */
    private int cols;
    private int range;
    private int seed;

    /**
     * Board which occupies cells.
     * The board is initialized with dimensions ({@link #rows}, {@link #cols})
     *
     * */
    private Cell[][] cellBoard;


    public Board(Cell[][] cellBoard) {
    }

    public void nextGeneration(){

        Board tempBoard = new Board(cellBoard); //TODO: Make clone
        //Make changes to temp board
        this.cellBoard = tempBoard.cellBoard;

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
