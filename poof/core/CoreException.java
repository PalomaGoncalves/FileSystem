package poof.core;

public class CoreException extends Exception{

	private String _message;
	
	public CoreException(String message) {
		_message = message;
	}
	
	public String getMessage() {
		return _message;
	}
}

