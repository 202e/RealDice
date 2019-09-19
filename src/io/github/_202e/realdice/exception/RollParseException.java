package io.github._202e.realdice.exception;

public class RollParseException extends Exception {
    public final String problem;
    public RollParseException(String problem) {
        super();
        this.problem = problem;
    }
}
