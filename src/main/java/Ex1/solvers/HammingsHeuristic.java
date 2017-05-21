package Ex1.solvers;

import Ex1.model.Board;

/**
 * Created by szale_000 on 2017-04-17.
 */
public class HammingsHeuristic implements Heuristic {

    @Override
    public int calculateDistance(Board board, int historyCost) {
        int distance = 0;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                if (!board.isFieldOk(i, j) && board.getBoard()[i][j] != 0) {
                    distance++;
                }
            }
        }
        return distance + historyCost;
    }
}
