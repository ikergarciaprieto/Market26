package exceptions;

public class MoneyIsNegativeException extends Exception {
	private static final long serialVersionUID = 1L;

	public MoneyIsNegativeException()
	{
		super();
	}
	/**This exception is triggered if the date of the product to sell is previous than today
	 *@param s String of the exception
	 */
	public MoneyIsNegativeException(String s)
	{
		super(s);
	}
}
