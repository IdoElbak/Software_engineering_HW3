public class Cell {
    private byte state;

    public Cell(byte state) {
        this.state = state;
    }

    public byte getState() {
        return this.state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
