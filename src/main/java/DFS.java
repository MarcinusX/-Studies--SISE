import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by szale_000 on 2017-04-16.
 */

public class DFS {

    private Board board;
    private List<Action> fetchOrder;
    private List<Action> dsfSolution;
    private double time = 0;
    private int visitedStates = 0;
    private int maxLevel = 0;
    private static final int MAX_ALLOWED_LEVEL = 20;

    public DFS(Board board, String fetchOrder) {
        this.board = board;
        this.fetchOrder = parseParameters(fetchOrder);
    }

    public void dsf() {
        time = System.nanoTime();
        Stack<List<Action>> stack = new Stack<>();
        List<Action> actions = new ArrayList<>();
        addActionsToStack(stack, actions, null);
        visitedStates = 0;

        List<Action> solution = null;
        while (solution == null || stack.isEmpty()) {
            actions = stack.pop();
            visitedStates++;
            //System.out.println("level " + actions.size() + " trying: " + actions);
            try {
                Board tempBoard = new Board(board, actions);
                if (tempBoard.isSolved()) {
                    solution = actions;
                } else if (actions.size() < MAX_ALLOWED_LEVEL) {
                    addActionsToStack(stack, actions, actions.get(actions.size() - 1).opposite());
                }
            } catch (InvalidBoardOperationException e) {
                visitedStates--;
            }
        }
        time = System.nanoTime() - time;
        dsfSolution = solution;
    }

    public void addActionsToStack(Stack<List<Action>> stack, List<Action> actionHistory, Action excludedAction) {
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

    private List<Action> parseParameters(String params) {
        List<Action> order = new ArrayList<>();
        for (char symbol : params.toCharArray()) {
            order.add(Action.fromString(String.valueOf(symbol)));
        }
        Collections.reverse(order);
        return order;
    }

    public void printResults(String solutionPath, String statsPath) throws IOException {
        printSolution(solutionPath);
        printStats(statsPath);
    }

    private void printStats(String statsPath) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(statsPath));
        int length = dsfSolution == null ? -1 : dsfSolution.size();
        pw.println(length);
        pw.println(visitedStates);
        pw.println(maxLevel);
        pw.println(time / Math.pow(10, 6));
        pw.close();
    }

    private void printSolution(String solutionPath) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(solutionPath));
        if (dsfSolution == null) {
            pw.print(-1);
        } else {
            pw.println(dsfSolution.size());
            for (Action action : dsfSolution) {
                pw.print(action);
            }
        }
        pw.close();
    }
}
