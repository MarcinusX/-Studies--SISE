import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by szale_000 on 2017-04-14.
 */
public class Board {
    private int rows;
    private int columns;
    private int[][] board;

    private int emptyRowIndex;
    private int emptyColumnIndex;

    public Board(Board board, List<Action> actions) throws InvalidBoardOperationException {
        this.rows = board.getRows();
        this.columns = board.getColumns();
        this.board = cloneArray(board.getBoard());
        setEmptyIndexes();
        performActions(actions);
    }

    public Board(String filename) throws FileNotFoundException {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String[] lines = bufferedReader.lines().toArray(String[]::new);
        String[] firstLine = lines[0].split(" ");
        rows = Integer.valueOf(firstLine[0]);
        columns = Integer.valueOf(firstLine[1]);
        board = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            board[i] = Arrays.stream(lines[i + 1].split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        setEmptyIndexes();
    }

    private int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public boolean isSolved() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (!isFieldOk(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isFieldOk(int row, int column) {
        int expectedNumber = row * columns + column + 1;
        int maxNumber = rows * columns;
        return board[row][column] == expectedNumber % maxNumber;
    }

    public void performAction(Action action) throws InvalidBoardOperationException {
        try {
            board[emptyRowIndex][emptyColumnIndex] = board[emptyRowIndex + action.getRowFinder()][emptyColumnIndex + action.getColumnFinder()];
            board[emptyRowIndex + action.getRowFinder()][emptyColumnIndex + action.getColumnFinder()] = 0;
            emptyRowIndex += action.getRowFinder();
            emptyColumnIndex += action.getColumnFinder();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidBoardOperationException(this, action);
        }
    }

    public void performActions(List<Action> actions) throws InvalidBoardOperationException {
        for (Action action : actions) {
            performAction(action);
        }
    }

    private void setEmptyIndexes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j] == 0) {
                    emptyRowIndex = i;
                    emptyColumnIndex = j;
                    return;
                }
            }
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                string += board[i][j] + " ";
            }
            string += "\n";
        }
        return string;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int[][] getBoard() {
        return board;
    }
}
