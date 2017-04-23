package solvers;

import model.Action;
import model.Board;
import model.InvalidBoardOperationException;
import util.Utils;

import java.io.IOException;
import java.util.*;

/**
 * Created by szale_000 on 2017-04-17.
 */
public class BFS implements PuzzleSolver {

    private List<Action> fetchOrder;
    private List<Action> puzzleSolution;
    private double time = 0;
    private int maxLevel = 0;
    private HashSet<Board> visitedBoards = new HashSet<>();

    @Override
    public void solve(Board board, String params) {
        prepare(params);

        Queue<List<Action>> queue = new LinkedList<>();
        addActionsToQueue(queue, new ArrayList<>(), null);
        List<Action> solution = null;

        while (solution == null && !queue.isEmpty()) {
            List<Action> actions = queue.poll();
            //System.out.println("level " + actions.size() + " trying: " + actions);
            try {
                Board tempBoard = new Board(board, actions);
                if (!visitedBoards.contains(tempBoard)) {
                    visitedBoards.add(tempBoard);
                    if (tempBoard.isSolved()) {
                        solution = actions;
                    } else {
                        addActionsToQueue(queue, actions, actions.get(actions.size() - 1).opposite());
                    }
                }
            } catch (InvalidBoardOperationException e) {
                //DO NOTHING
            }
        }
        time = System.nanoTime() - time;
        puzzleSolution = solution;
    }

    @Override
    public void printResults(String solutionPath, String statsPath) throws IOException {
        new Utils().printResults(solutionPath, statsPath, puzzleSolution, visitedBoards.size(), maxLevel, time / Math.pow(10, 6));
    }

    private void addActionsToQueue(Queue<List<Action>> queue, List<Action> actionHistory, Action excludedAction) {
        for (Action action : fetchOrder) {
            if (action != excludedAction) {
                List<Action> actionsToFetch = new ArrayList<>(actionHistory);
                actionsToFetch.add(action);
                //System.out.println("adding "+actionsToFetch);
                queue.add(actionsToFetch);
            }
        }
        if (actionHistory.size() + 1 > maxLevel) {
            maxLevel = actionHistory.size() + 1;
        }
    }

    private void prepare(String fetchOrder) {
        this.fetchOrder = new Utils().parseBruteForceParameters(fetchOrder);
        time = System.nanoTime();
    }
}
