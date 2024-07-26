import java.util.Random;

/**
 * Describes a board containing cells of type {@link Cell}.
 *
 * */
public class Board {

    /**
     * Number of rows in the board
     * */
    private int rows;

    /**
     * Number of columns in the board
     * */
    private int cols;

    /**
     * Board which occupies cells.
     * The board is initialized with dimensions ({@link #rows}, {@link #cols})
     *
     * */
    private Cell[][] cellBoard;

    public Board(int rows, int cols, int range, int seed) {

        //Initializes board attributes.
        this.rows = rows;
        this.cols = cols;
        this.cellBoard = new Cell[rows][cols];


        //Randomly generates the board's cells.
        Random rand = new Random(seed);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                int randValue = rand.nextInt(range);
                Cell cellNew;
                if(randValue % 2 == 0)
                    cellNew = new NonDyingCell(true);

                else
                    cellNew = new NonDyingCell(false);

                cellBoard[row][col] = cellNew;
            }
        }

    }
    public Board(Cell[][] cellBoard) {
    }

    public void nextGeneration(){

        Cell[][] tempBoard = new Cell[rows][cols]; //TODO: Make clone
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){

                byte[] healthyAndDeadCells = countHealthyAndDeadCells(row, col);
                tempBoard[row][col] = cellBoard[row][col].nextGeneration(
                        healthyAndDeadCells[0], healthyAndDeadCells[1]);
            }
        }
        this.cellBoard = tempBoard;

    }

    /**
     * Counts the healthy cells and dead cells among the neighboring cells of
     * the cell at a given row and column.
     *
     * @param row The given row.
     * @param col The given column
     * @return 2-cell byte array with a value of {row, col}.
     * <br><br>
     * <strong>Operation rational</strong>:<br>
     * There are 8 neighboring cells (maximum) to check.
     * Therefore, we will preform 8 checks, in comparison to the cell in indexes (row, col):
     * <br>1. The cell in indexes (row - 1, col - 1).
     * <br>2. The cell in indexes (row - 1, col).
     * <br>3. The cell in indexes (row - 1, col + 1).
     * <br>4. The cell in indexes (row, col - 1)
     * <br>5. The cell in indexes (row + 1, col - 1)
     * <br>6. The cell in indexes (row + 1, col)
     * <br>7. The cell in indexes (row + 1, col + 1)
     * <br>8. The cell in indexes (row, col + 1)
     * <br>In each neighboring cell, we will check whether it is dead, alive or neither, and act accordingly.
     * <br><br>
     * We will also check if the indexes of the neighboring cells are valid before checking them,
     * in order to avoid an {@link IndexOutOfBoundsException}.
     * */
    private byte[] countHealthyAndDeadCells(int row, int col){

        //Stores the number of healthy cells.
        byte healthyCells = 0;

        //Stores the number of dead cells.
        byte deadCells = 0;

        if(row > 0){

            //Check 1:
            if(col > 0){
                if(cellBoard[row - 1][col - 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row - 1][col - 1].isDead())
                    deadCells++;
            }

            //Check 2:
            if(cellBoard[row - 1][col].isHealthy())
                healthyCells++;

            else if(cellBoard[row - 1][col].isDead())
                deadCells++;

            //Check 3:
            if(col < cols - 1){
                if(cellBoard[row - 1][col + 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row - 1][col + 1].isDead())
                    deadCells++;
            }
        }

        if(col > 0){

            //Check 4:
            if(cellBoard[row][col - 1].isHealthy())
                healthyCells++;

            else if(cellBoard[row][col - 1].isDead())

            //Check 5:
            if(row < rows - 1){
                if(cellBoard[row + 1][col - 1].isHealthy())
                    healthyCells++;
            }

            else if(cellBoard[row + 1][col - 1].isDead())
                deadCells++;

        }

        if(row < rows - 1){

            //Check 6:
            if(cellBoard[row + 1][col].isHealthy())
                healthyCells++;

            else if(cellBoard[row + 1][col].isDead())
                deadCells++;

            //Check 7:
            if(col < cols - 1){
                if(cellBoard[row + 1][col + 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row + 1][col + 1].isDead())
                    deadCells++;
            }
        }

        //Check 8:
        if(col < cols - 1){

            if(cellBoard[row][col + 1].isHealthy())
                healthyCells++;

            else if(cellBoard[row][col + 1].isDead())
                deadCells++;

        }

        return new byte[]{healthyCells, deadCells};

    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Board boardObj))
            return false;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(!this.cellBoard[i][j].equals(boardObj.cellBoard[i][j]))
                    return false;
            }
        }

        return true;
    }

    /**
     * Generates a hash code for the given board.
     * The generating algorithm is as such:
     *
     *
     * */
    @Override
    public int hashCode() {
        String hashString = rows + "_" + cols + "_";
        for(int i = 0; i < rows; i++){

            for (int j = 0; j < cols; j++){
                hashString += cellBoard[i][j].getCellType();
            }

        }
        return hashString.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
