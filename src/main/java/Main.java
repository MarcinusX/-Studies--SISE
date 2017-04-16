import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by szale_000 on 2017-04-14.
 */
public class Main {

    public static void main(String... args) {
        System.out.println("Hello world");
        try {
            Board board = new Board("4x4_01_00001.txt");
            System.out.println(board);
            board.performAction(Action.RIGHT);
            System.out.println(board);
        } catch (FileNotFoundException | InvalidBoardOperationException e) {
            e.printStackTrace();
        }
    }

    private static void dsf(Board board, List<Action> actions) {

    }
}
