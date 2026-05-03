package exceptions;

public class CartDoesntExistException extends Exception{
	private static final long serialVersionUID = 1L;
	public CartDoesntExistException() {
		super();
	}
	
	
	public CartDoesntExistException(String s) {
		super(s);
	}

}
