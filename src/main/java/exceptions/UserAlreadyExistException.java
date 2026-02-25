package exceptions;
public class UserAlreadyExistException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public UserAlreadyExistException()
  {
    super();
  }
  /**This exception is triggered if a necesary string is empty 
  *@param s String of the exception
  */
  public UserAlreadyExistException(String s)
  {
    super(s);
  }
}
