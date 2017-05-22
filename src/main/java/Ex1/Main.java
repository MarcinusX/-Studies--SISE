package Ex1;

import Ex1.model.Board;
import Ex1.solvers.AStar;
import Ex1.solvers.BFS;
import Ex1.solvers.DFS;
import Ex1.solvers.PuzzleSolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by szale_000 on 2017-04-14.
 */
public class Main {

    public static class SolverStrategyFactory {
        private static final Map<String, PuzzleSolver> strategies;

        static {
            strategies = new HashMap<>();
            strategies.put("dfs", new DFS());
            strategies.put("bfs", new BFS());
            strategies.put("astr", new AStar());
        }
    }

    public static void main(String... args) {
        String algorithm = args[0];
        String parameter = args[1];
        String inputPath = args[2];
        String solutionPath = args[3];
        String statsPath = args[4];
        PuzzleSolver puzzleSolver = SolverStrategyFactory.strategies.get(algorithm);
        try {
            Board board = new Board(inputPath);
            //System.out.println(board);
            puzzleSolver.solve(board, parameter);
            puzzleSolver.printResults(solutionPath, statsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
