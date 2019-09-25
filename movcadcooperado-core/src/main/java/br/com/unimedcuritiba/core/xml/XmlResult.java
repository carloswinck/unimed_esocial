package br.com.unimedcuritiba.core.xml;

import java.util.List;

/**
 * Encapsula o resultado do processamento do parser de um arquivo XML
 *
 * @author Paulo Roberto Schwertner
 */
public class XmlResult
{

	private Object result;
	private List<String> errors;

	public XmlResult ()
	{
		super();
		this.result = null;
		this.errors = null;
	}

	public XmlResult (final Object result, final List<String> errors)
	{
		super();
		this.result = result;
		this.errors = errors;
	}

	public Object getResult ()
	{
		return result;
	}

	public void setResult (final Object result)
	{
		this.result = result;
	}

	public List<String> getErrors ()
	{
		return errors;
	}

	public void setErrors (final List<String> errors)
	{
		this.errors = errors;
	}

	public boolean hasErrors ()
	{
		return (errors != null && !errors.isEmpty());
	}
}
