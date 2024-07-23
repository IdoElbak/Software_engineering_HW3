
/**
 * Object representing a cell in the game of life.
 * */
public class Cell {

    /**
     *The cell's state
     *
     * */
    private CellState cellState;

    public Cell(CellState cellState) {
        this.cellState = cellState;
    }

    public CellState getCellState() {
        return this.cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public Cell nextGeneration(){

        CellState temp = CellState.DYING;
        return new Cell(temp);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        switch (this.cellState){
            case HEALTHY:
                return 0;

            case SICK:
                return 1;

            case DYING:
                return 2;

            case DEAD:
                return 3;
        }

        return -1;
        //Impossible edge case. TODO: explain
    }
}
