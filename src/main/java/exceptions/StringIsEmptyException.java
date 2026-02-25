package exceptions;
public class StringIsEmptyException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public StringIsEmptyException()
  {
    super();
  }
  /**This exception is triggered if a necesary string is empty 
  *@param s String of the exception
  */
  public StringIsEmptyException(String s)
  {
    super(s);
  }
}
