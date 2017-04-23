package util;

import model.Action;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szale_000 on 2017-04-17.
 */
public class Utils {

    public void printResults(String solutionPath, String statsPath, List<Action> solution, int visitedStates, int maxLevel, double time) throws IOException {
        int length = solution == null ? -1 : solution.size();

        new Utils().printSolution(solutionPath, solution);
        new Utils().printStats(statsPath, length, visitedStates, maxLevel, time);
    }

    public void printStats(String statsPath, int length, int visitedStates, int maxLevel, double time) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(statsPath));
        pw.println(length);
        pw.println(visitedStates);
        pw.println(maxLevel);
        pw.format("%.3f", time);
        pw.close();
    }

    public void printSolution(String solutionPath, List<Action> solution) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(solutionPath));
        if (solution == null) {
            pw.print(-1);
        } else {
            pw.println(solution.size());
            for (Action action : solution) {
                pw.print(action);
            }
        }
        pw.close();
    }

    public List<Action> parseBruteForceParameters(String params) {
        List<Action> order = new ArrayList<>();
        for (char symbol : params.toCharArray()) {
            order.add(Action.fromString(String.valueOf(symbol)));
        }
        return order;
    }
}
