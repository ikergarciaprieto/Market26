package exceptions;
public class EmailIsNotCorrectException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public EmailIsNotCorrectException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public EmailIsNotCorrectException(String s)
  {
    super(s);
  }
}
