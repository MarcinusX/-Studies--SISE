import java.io.IOException;

/**
 * Created by szale_000 on 2017-04-14.
 */
public class Main {

    public static void main(String... args) {
        String alogrithm = args[0];
        String parameter = args[1];
        String inputPath = args[2];
        String solutionPath = args[3];
        String statsPath = args[4];
        try {
            Board board = new Board(inputPath);
            System.out.println(board);
            DFS dfs = new DFS(board, parameter);
            dfs.dsf();
            dfs.printResults(solutionPath, statsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
