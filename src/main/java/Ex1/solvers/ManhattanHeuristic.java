package Ex1.solvers;

import Ex1.model.Board;

/**
 * Created by szale_000 on 2017-04-17.
 */
public class ManhattanHeuristic implements Heuristic {

    @Override
    public int calculateDistance(Board board, int historyCost) {
        int distance = 0;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                int value = board.getBoard()[i][j];
                if (value != 0) {
                    int expectedRow = (value - 1) / board.getRows();
                    int expectedColumn = (value - 1) % board.getColumns();
                    distance += Math.abs(i - expectedRow) + Math.abs(j - expectedColumn);
                }
            }
        }
        return distance + historyCost;
    }
}
