package Ex1.solvers;

import Ex1.model.Board;

/**
 * Created by szale_000 on 2017-04-17.
 */
public interface Heuristic {
    int calculateDistance(Board board, int historyCost); //hisoryCost to liczba wcześniej wykonanych ruchów
}
