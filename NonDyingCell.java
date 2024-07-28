/**
 * Describes a cell which is either healthy or sick.
 * In this case, {@link #stateBad} is set as follows:
 * true if the cell is sick, false if the cell is healthy.
 * */
public class NonDyingCell extends Cell {

    public NonDyingCell(boolean stateBad) {
        super(stateBad);
    }

    @Override
    public Cell nextGeneration(byte healthyCells, byte sickCells) {

        /*
        * Note that the both a healthy cell and a sick cell will change state if (but not only if)
        * the number of neighboring healthy cells is less than 2 or more than 3.
        *
        * Therefore, we check this condition for the general case.
        * */
        if(healthyCells < 2 || healthyCells > 3){
            //If the cell is sick, it should become a dying cell.
            if(this.stateBad){
                //Return a dying cell according to its definition in the DyingCell class.
                return new DyingCell(false);
            }

            //Return a dead according to its definition in the DyingCell class.
            else return new NonDyingCell(true);

        }

        //We now know that 2<= healthyCells <= 3
        else{

            //A healthy cell will become a sick cell if it has more than 3 neighboring sick cells.
            if(!this.stateBad && sickCells > 3)
                return new NonDyingCell(true);

            //A sick cell will become a dying cell if it has more than 2 neighboring sick cells.
            else if(this.stateBad && sickCells > 2)
                return new DyingCell(false);

        }
        return new NonDyingCell(false);
    }

    @Override
    public String toString() {
        if(stateBad){
            return "S";
        }
        return "H";
    }
}
