package br.com.unimedcuritiba.core.exception;

public class DAOException
    extends BusinessException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -229026111308181740L;

	/**
	 * 
	 */
	public DAOException ()
	{
		super();
	}

	/**
	 * @param e
	 */
	public DAOException (Exception e)
	{
		super(e);
	}

	/**
	 * @param e
	 * @param paramsOfMethod
	 */
	public DAOException (Exception e, Object... paramsOfMethod)
	{
		super(e, paramsOfMethod);
	}

	/**
	 * @param e
	 * @param paramsOfMethod
	 * @param classe
	 */
	public DAOException (Exception e, Object[] paramsOfMethod, Class<?> classe)
	{
		super(e, paramsOfMethod, classe);
	}
}
