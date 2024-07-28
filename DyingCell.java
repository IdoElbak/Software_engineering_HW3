/**
 * Describes a cell which is either dying or dead.
 * In this case, {@link #stateBad} is set as follows:
 * true if the cell is dead, false if the cell is dying.
 * */
public class DyingCell extends Cell {

    public DyingCell(boolean stateBad) {
        super(stateBad);
    }

    @Override
    public Cell nextGeneration(byte healthyCells, byte sickCells) {
        /*
        * Note that both possible types on DyingCell (Dying/Dead) change state based on
        * whether they have 3 neighboring healthy cells.
        * More specifically, if any cell of type DyingCell does not have 3 neighboring healthy
        * cells, it will change to or stay in the dead state.
        *
        * Therefore, we check if there are 3 healthy cells for the general case.
        * If there aren't, the new cell is dead.
        * */
        if(healthyCells != 3)
            return new DyingCell(true);

        //From here onwards we know that healthyCells == 3.
        if(this.stateBad){
            /*
            * If the cell is dead, it will change to a living cell if
            * the number of healthy cells neighboring it is exactly 3.
            * we know this to be true, and therefore the next cell is healthy.
            * */
            return new NonDyingCell(false);
        }else{
            /*
             * In this case, we know that the cell is dying and not dead.
             * The conditions for a dying cell to become dead are:
             * 1. Number of neighboring healthy cells is different to 3 (known as false at this point)
             * 2. Number of neighboring sick cells is higher than 1.
             * The conditions are set in an 'or' clause, meaning we should only check condition 2.
             * If condition 2 isn't fulfilled, the cell will become a healthy cell.
             * */
            if(sickCells > 1)
                return new DyingCell(true);

            else return new NonDyingCell(false);
        }
    }

    @Override
    public String toString() {
        if(stateBad){
            return "-";
        }
        return "D";
    }
}
