package solvers;

import model.Board;

import java.io.IOException;

/**
 * Created by szale_000 on 2017-04-17.
 */
public interface PuzzleSolver {
    void solve(Board board, String params);

    void printResults(String solutionPath, String statsPath) throws IOException;
}
