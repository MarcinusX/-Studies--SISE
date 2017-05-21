package Ex1.model;

/**
 * Created by szale_000 on 2017-04-14.
 */
public class InvalidBoardOperationException extends Exception {
    public InvalidBoardOperationException(Board board, Action action) {
        super("niepoprawna akcja: " + action + "\nboard:\n" + board.toString());
    }
}
