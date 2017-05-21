package Ex1.model;

/**
 * Created by szale_000 on 2017-04-14.
 */
public enum Action {
    UP("U", -1, 0),
    DOWN("D", 1, 0),
    LEFT("L", 0, -1),
    RIGHT("R", 0, 1);

    private String value;
    private int rowFinder;
    private int columnFinder;

    Action(String s, int rowFinder, int columnFinder) {
        value = s;
        this.rowFinder = rowFinder;
        this.columnFinder = columnFinder;
    }

    public String toString() {
        return value;
    }

    public int getRowFinder() {
        return rowFinder;
    }

    public int getColumnFinder() {
        return columnFinder;
    }

    public static Action fromString(String symbol) {
        for (Action b : Action.values()) {
            if (b.value.equalsIgnoreCase(symbol)) {
                return b;
            }
        }
        return null;
    }

    public Action opposite() {
        switch (this) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return UP;//nie ma znaczenia
        }
    }
}
