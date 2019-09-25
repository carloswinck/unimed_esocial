/**
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import java.util.Date;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.util.CustomDate;

/**
 * Validator de datas futuras
 * 
 * @author daniloa
 */
@FacesValidator
@ApplicationScoped
@ManagedBean(eager = true)
public class DataFuturaValidator
    implements Validator
{

	@Inject
	private Messages messages;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext,
	 * javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate (final FacesContext context, final UIComponent component, final Object value) throws ValidatorException
	{
		if (value != null && value instanceof java.util.Date)
		{
			final Date date = (Date)value;

			if (!CustomDate.validaData(date))
			{
				throw new ValidatorException(messages.getErrorMessage("validator.dataFutura"));
			}
		}

	}

}
