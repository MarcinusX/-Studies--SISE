package solvers;

import model.Board;

/**
 * Created by szale_000 on 2017-04-17.
 */
public interface Heuristic {
    int calculateDistance(Board board, int historyCost);
}
