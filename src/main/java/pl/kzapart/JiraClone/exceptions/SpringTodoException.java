package pl.kzapart.JiraClone.exceptions;

public class SpringTodoException extends RuntimeException {
    public SpringTodoException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringTodoException(String exMessage) {
        super(exMessage);
    }
}