/**<p>Class describing the general cell, without regard to its type.
 * The specific type of cell is defined via inheritors
 * (namely {@link NonDyingCell} and {@link DyingCell}).</>
 *<br>
 * <p> <strong>Rational for class design:</strong> <br>
 *  Note that when checking the next generation of a cell, each type of cell has different criteria.
 *  Moreover, certain types of cells have similar criteria, allowing them to be organized under one
 *  child class. Specifically, Healthy and sick cells have similar criteria,
 *  as well as dying and dead cells.</>
 * */
abstract public class Cell {

    /**Used to differ between cells of different types.
     * See child class implementation for more info.*/
    protected boolean stateBad;

    /**Numeric constant denoting a healthy cell.*/
    public static final byte STATE_HEALTHY = 0;

    /**Numeric constant denoting a sick cell.*/
    public static final byte STATE_SICK = 1;

    /**Numeric constant denoting a dying cell.*/
    public static final byte STATE_DYING = 2;

    /**Numeric constant denoting a dead cell.*/
    public static final byte STATE_DEAD = 3;

    public Cell(boolean stateBad) {
        this.stateBad = stateBad;
    }

    public abstract Cell nextGeneration(byte healthyCells, byte sickCells);

    /**Compares to objects. Note that two objects of the cell class are equal
     * iff they are from the same inheritor, and have the same value in the {@link #stateBad} attribute.
     * @return true if the objects are equal via the definition, otherwise false.*/
    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass()))
            return false;

        return this.stateBad == ((Cell) obj).stateBad;
    }

    /**Checks whether a cell is healthy.<br>
     * A healthy cell is a cell of the {@link NonDyingCell} class with fulfills {@link #stateBad} = false.
     * @return true if the cell is healthy, otherwise false.*/
    public boolean isHealthy(){
        return this.getCellType() == STATE_HEALTHY;
    }

    /**Checks whether a cell is dead.<br>
     * A dead cell is a cell of the {@link DyingCell} class with fulfills {@link #stateBad} = true.
     * @return true if the cell is dead, otherwise false.*/
    public boolean isSick(){
        return this.getCellType() == STATE_SICK;
    }

    public byte getCellType(){
        if(this instanceof NonDyingCell)
            return this.stateBad ? STATE_SICK : STATE_HEALTHY;

        return this.stateBad ? STATE_DEAD : STATE_DYING;
    }
}
