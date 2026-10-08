package Object;

public class NoValidMoves extends RuntimeException {
    public NoValidMoves(String message) {
        super(message);
    }
}
