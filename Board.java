import java.util.Random;

/**
 * Describes a board containing cells of type {@link Cell}.
 * */
public class Board {

    /**
     * Number of rows in the board
     * */
    private final int rows;

    /**
     * Number of columns in the board
     * */
    private final int cols;

    /**
     * Board which occupies cells.
     * The board is initialized with dimensions ({@link #rows}, {@link #cols})
     * */
    private Cell[][] cellBoard;

    public Board(int rows, int cols, int range, int seed) {

        //Initializes board attributes.
        this.rows = rows;
        this.cols = cols;
        this.cellBoard = new Cell[rows][cols];

        //Randomly generates the board's cells.
        Random rnd = new Random(seed);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int randValue = rnd.nextInt(range);
                Cell cellNew;
                if(randValue % 2 == 0) {
                    cellNew = new DyingCell(true);
                }else {
                    cellNew = new NonDyingCell(false);
                }
                cellBoard[row][col] = cellNew;
            }
        }

    }

    public Board(Board board){ // a copy constructor
        this.rows = board.cellBoard.length;
        this.cols = board.cellBoard[0].length;
        this.cellBoard = new Cell[rows][cols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                if(board.cellBoard[row][col] instanceof DyingCell){
                    /*
                    cellBoard in spot row,col will be a new DyingCell because it is instance of it
                    getCellType will return 2 if the cell is Dying(stateBad = 0) and 3 if it's dead(stateBad = 1).
                    so if the returned value == 3 it means stateBad should equal true. else it should equal false
                     */
                    this.cellBoard[row][col] = new DyingCell(board.cellBoard[row][col].getCellType() == 3);
                }else if(board.cellBoard[row][col] instanceof NonDyingCell){
                    /*
                    cellBoard in spot row,col will be a new NonDyingCell because it is instance of it
                    getCellType will return 0 if the cell is Healthy(stateBad = 0) and 1 if it's sick(stateBad = 1).
                    so if the returned value == 1 it means stateBad should equal true. else it should equal false
                     */
                    this.cellBoard[row][col] = new NonDyingCell(board.cellBoard[row][col].getCellType() == 1);
                }
            }
        }
    }

    /**
     * this function calculates the board's next generation by doing as follows:
     * creates a temporary board as a copy of the instance's one.
     * changes the temporary board according to the demands of the game
     * and then changes the instance's board to the temporary one
     */
    public void nextGeneration(){
        Board tempBoard = new Board(this);
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){

                byte[] healthyAndSickCells = countHealthyAndSickCells(row, col);
                tempBoard.cellBoard[row][col] = cellBoard[row][col].nextGeneration(
                        healthyAndSickCells[0], healthyAndSickCells[1]);
            }
        }
        this.cellBoard = tempBoard.cellBoard;
    }

    /**
     * Counts the healthy cells and sick cells among the neighboring cells of
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
     * <br>In each neighboring cell, we will check whether it is sick, alive or neither, and act accordingly.
     * <br><br>
     * We will also check if the indexes of the neighboring cells are valid before checking them,
     * in order to avoid an {@link IndexOutOfBoundsException}.
     * */
    private byte[] countHealthyAndSickCells(int row, int col){

        //Stores the number of healthy cells.
        byte healthyCells = 0;
        //Stores the number of dead cells.
        byte sickCells = 0;

        if(row > 0){

            //Check 1:
            if(col > 0){
                if(cellBoard[row - 1][col - 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row - 1][col - 1].isSick())
                    sickCells++;
            }

            //Check 2:
            if(cellBoard[row - 1][col].isHealthy())
                healthyCells++;

            else if(cellBoard[row - 1][col].isSick())
                sickCells++;

            //Check 3:
            if(col < cols - 1){
                if(cellBoard[row - 1][col + 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row - 1][col + 1].isSick())
                    sickCells++;
            }
        }

        if(col > 0){

            //Check 4:
            if(cellBoard[row][col - 1].isHealthy())
                healthyCells++;

            else if(cellBoard[row][col - 1].isSick())
                sickCells++;

            //Check 5:
            if(row < rows - 1){
                if(cellBoard[row + 1][col - 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row + 1][col - 1].isSick())
                    sickCells++;
            }

        }

        if(row < rows - 1){

            //Check 6:
            if(cellBoard[row + 1][col].isHealthy())
                healthyCells++;

            else if(cellBoard[row + 1][col].isSick())
                sickCells++;

            //Check 7:
            if(col < cols - 1){
                if(cellBoard[row + 1][col + 1].isHealthy())
                    healthyCells++;

                else if(cellBoard[row + 1][col + 1].isSick())
                    sickCells++;
            }
        }

        //Check 8:
        if(col < cols - 1){

            if(cellBoard[row][col + 1].isHealthy())
                healthyCells++;

            else if(cellBoard[row][col + 1].isSick())
                sickCells++;

        }

        return new byte[]{healthyCells, sickCells};
    }

    public boolean isBoardDead(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                if(!this.cellBoard[row][col].isDead()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Board))
            return false;

        Board boardObj = (Board)obj;
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
        String resultString = "";
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                resultString += cellBoard[i][j].toString();
                if(j != cols - 1){
                    resultString += " ";
                }
            }
            if(i != rows-1)
                resultString += "\n";
        }
        return resultString;
    }
}