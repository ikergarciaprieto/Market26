package exceptions;
public class UserOrPasswordIsWrongException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public UserOrPasswordIsWrongException()
  {
    super();
  }
  /**This exception is triggered if the date of the product to sell is previous than today
  *@param s String of the exception
  */
  public UserOrPasswordIsWrongException(String s)
  {
    super(s);
  }
}
