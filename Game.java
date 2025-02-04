/**
 * Describes the game itself
 * */
public class Game {

    /**
     * the games board
     * initialized with random values that are generated by random with a specific seed
     */
    private final Board board;

    /**
     * the maximum number of generations the game can last
     */
    private final int maxGen;

    public Game(int rows, int cols, int seed, int range, int maxGen) {
        this.board = new Board(rows,cols,range,seed);
        this.maxGen = maxGen;
    }

    /**
     * the main function the handles the game.
     * it creates a copy of the board so the original one will stay the same after the function is done.
     * then the function prints the current "generation" of the board.
     * unless the end conditions are triggered it will update the board to its next generation and repeat
     * the end conditions:
     * - if all the cells on the board are dead ones the function should end.
     * - if the board stabilized meaning the board didn't change from the previous generation the function
     *   will print the appropriate message and end itself.
     * - if the max generation was reached the function should end as well.
     */
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
            if(i == maxGen){// done hear because we shouldn't calculate the next generation.
                System.out.println("The generation limitation was reached.");
                break;
            }
            prevBoard = new Board(copyBoard);
            copyBoard.nextGeneration();
        }
    }
}
