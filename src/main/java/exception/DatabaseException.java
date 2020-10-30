package exception;

public class DatabaseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2063645563693290795L;
	ExceptionType type;
	
	public enum ExceptionType {
		UNABLE_TO_CONNECT, UNABLE_TO_EXECUTE_QUERY;
	}
	
	public DatabaseException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
