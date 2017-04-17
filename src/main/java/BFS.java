import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by szale_000 on 2017-04-17.
 */
public class BFS implements PuzzleSolver {

    private List<Action> fetchOrder;
    private List<Action> dsfSolution;
    private double time = 0;
    private int visitedStates = 0;
    private int maxLevel = 0;
    private static final int MAX_ALLOWED_LEVEL = 20;

    @Override
    public void solve(Board board, String params) {
        prepare(params);

        Queue<List<Action>> queue = new LinkedList<>();
        addActionsToQueue(queue, new ArrayList<>(), null);
        List<Action> solution = null;

        while (solution == null || queue.isEmpty()) {
            List<Action> actions = queue.poll();
            visitedStates++;
            //System.out.println("level " + actions.size() + " trying: " + actions);
            try {
                Board tempBoard = new Board(board, actions);
                if (tempBoard.isSolved()) {
                    solution = actions;
                } else if (actions.size() < MAX_ALLOWED_LEVEL) {
                    addActionsToQueue(queue, actions, actions.get(actions.size() - 1).opposite());
                }
            } catch (InvalidBoardOperationException e) {
                visitedStates--;
            }
        }
        time = System.nanoTime() - time;
        dsfSolution = solution;
    }

    @Override
    public void printResults(String solutionPath, String statsPath) throws IOException {
        new Utils().printResults(solutionPath, statsPath, dsfSolution, visitedStates, maxLevel, time / Math.pow(10, 6));
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
        visitedStates = 0;
        time = System.nanoTime();
    }
}
