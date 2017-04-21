package solvers;

import model.Action;
import model.Board;
import model.InvalidBoardOperationException;
import util.Utils;

import java.io.IOException;
import java.util.*;

/**
 * Created by szale_000 on 2017-04-16.
 */

public class DFS implements PuzzleSolver {

    private List<Action> fetchOrder;
    private List<Action> puzzleSoltion;
    private double time = 0;
    private int maxLevel = 0;
    private static final int MAX_ALLOWED_LEVEL = 18;
    private HashMap<Board, Integer> visitedBoards = new HashMap<>();//Integer jest glebokoscia

    @Override
    public void solve(Board board, String params) {
        prepare(params);

        Stack<List<Action>> stack = new Stack<>();
        addActionsToStack(stack, new ArrayList<>(), null);
        List<Action> solution = null;

        while (solution == null && !stack.isEmpty()) {

            List<Action> actions = stack.pop();
            //System.out.println("level " + actions.size() + " trying: " + actions);
            try {
                Board tempBoard = new Board(board, actions);
                Integer currentSize = visitedBoards.get(tempBoard);
                if (currentSize == null || currentSize > actions.size()) {
                    visitedBoards.put(tempBoard, actions.size());
                    if (tempBoard.isSolved()) {
                        solution = actions;
                    } else if (actions.size() < MAX_ALLOWED_LEVEL) {
                        addActionsToStack(stack, actions, actions.get(actions.size() - 1).opposite());
                    }
                }
            } catch (InvalidBoardOperationException e) {
                //DO NOTHING
            }
        }
        time = System.nanoTime() - time;
        puzzleSoltion = solution;
    }

    @Override
    public void printResults(String solutionPath, String statsPath) throws IOException {
        new Utils().printResults(solutionPath, statsPath, puzzleSoltion, visitedBoards.size(), maxLevel, time / Math.pow(10, 6));
    }

    private void addActionsToStack(Stack<List<Action>> stack, List<Action> actionHistory, Action excludedAction) {
        for (Action action : fetchOrder) {
            if (action != excludedAction) {
                List<Action> actionsToFetch = new ArrayList<>(actionHistory);
                actionsToFetch.add(action);
                //System.out.println("adding "+actionsToFetch);
                stack.push(actionsToFetch);
            }
        }
        if (actionHistory.size() + 1 > maxLevel) {
            maxLevel = actionHistory.size() + 1;
        }
    }

    private void prepare(String fetchOrder) {
        this.fetchOrder = new Utils().parseBruteForceParameters(fetchOrder);
        Collections.reverse(this.fetchOrder);
        time = System.nanoTime();
    }

}
