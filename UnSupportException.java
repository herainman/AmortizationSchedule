
public class UnSupportException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnSupportException() {
		super();
	}

	public UnSupportException(String message) {
		super(message);
	}

	public UnSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnSupportException(Throwable cause) {
		super(cause);
	}

}
