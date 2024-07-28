public class Game {

    private final Board board;
    private final int maxGen;

    public Game(int rows, int cols, int seed, int range, int maxGen) {
        this.board = new Board(rows,cols,range,seed);
        this.maxGen = maxGen;
    }

    public void runGame(){
        int i = 0;
        Board copyBoard = new Board(this.board);
        Board prevBoard = null;

        for(i = 0; i <= maxGen; i++){
            System.out.printf("Generation %d:\n", i);
            System.out.println(copyBoard);

            if(copyBoard.isBoardDead()){
                System.out.println("All cells are dead.");
                break;
            }
            if(copyBoard.equals(prevBoard)){
                System.out.println("Cells have stabilized.");
                break;
            }
            if(i == maxGen){
                System.out.println("The generation limitation was reached.");
                break;
            }
            prevBoard = new Board(copyBoard);
            copyBoard.nextGeneration();
        }
    }
}
