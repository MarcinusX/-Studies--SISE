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
public class AStar implements PuzzleSolver {
    private List<Action> puzzleSolution;
    private double time = 0;
    private int maxLevel = 0;
    private HashSet<Board> visitedBoards = new HashSet<>();
    private HashMap<List<Action>, Integer> options = new HashMap<>();//Integer jest odległością
    private Heuristic heuristic;

    @Override
    public void solve(Board board, String params) {
        prepare(params);

        addNewOptions(board, new ArrayList<>(), null);
        List<Action> solution = null;

        while (solution == null && !options.isEmpty()) {
            List<Action> actions = getBestOption();
            //System.out.println("level " + actions.size() + " trying: " + actions);
            try {
                Board tempBoard = new Board(board, actions);
                if (!visitedBoards.contains(tempBoard)) {
                    visitedBoards.add(tempBoard);
                    if (tempBoard.isSolved()) {
                        solution = actions;
                    } else {
                        addNewOptions(board, actions, actions.get(actions.size() - 1).opposite());
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

    private List<Action> getBestOption() {
        Map.Entry<List<Action>, Integer> entry =
                options.entrySet()
                        .stream()
                        .min(Comparator.comparingInt(Map.Entry::getValue))
                        .get();
        options.remove(entry.getKey());
        return entry.getKey();
    }

    private void addNewOptions(Board sourceBoard, List<Action> actionHistory, Action excludedAction) {
        for (Action action : Action.values()) {
            if (action != excludedAction) {
                List<Action> actionsToFetch = new ArrayList<>(actionHistory);
                actionsToFetch.add(action);
                try {
                    Board boardToAdd = new Board(sourceBoard, actionsToFetch);
                    if (!visitedBoards.contains(boardToAdd)) {
                        int distance = heuristic.calculateDistance(boardToAdd, actionsToFetch.size());
                        options.put(actionsToFetch, distance);
                        //System.out.println("adding " + actionsToFetch + "distance: " + distance);
                    }
                } catch (InvalidBoardOperationException e) {
                    //e.printStackTrace();
                }
            }
        }
        if (actionHistory.size() + 1 > maxLevel) {
            maxLevel = actionHistory.size() + 1;
        }
    }

    private void prepare(String heuristicParam) {
        this.heuristic = HeristicStrategyFactory.heuristics.get(heuristicParam);
        time = System.nanoTime();
    }

    public static class HeristicStrategyFactory {
        private static final Map<String, Heuristic> heuristics;

        static {
            heuristics = new HashMap<>();
            heuristics.put("hamm", new HammingsHeuristic());
            heuristics.put("manh", new ManhattanHeuristic());
        }
    }
}
