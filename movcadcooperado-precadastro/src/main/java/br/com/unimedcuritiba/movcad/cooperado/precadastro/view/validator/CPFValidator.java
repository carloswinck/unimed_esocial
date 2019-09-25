package br.com.unimedcuritiba.movcad.cooperado.precadastro.view.validator;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import br.com.unimedcuritiba.movcad.cooperado.precadastro.util.Util;
import br.com.visionnaire.core.messages.Messages;

/**
 * Validador de hora inical e final
 *
 * @author Diego SIlva
 * @since 31 de out de 2016
 */

@FacesValidator
@ApplicationScoped
@ManagedBean(eager = true)
public class CPFValidator
    implements Validator
{

	@Inject
	private Messages messages;

	@Override
	public void validate (final FacesContext context, final UIComponent component, final Object value) throws ValidatorException
	{
		if (value == null)
		{
			return;
		}

		final Object cpfValue = value;
		if (cpfValue != null)
		{
			if (Util.validarCpf(cpfValue.toString()))
			{
				return;
			}
			else
			{
				throw new ValidatorException(messages.getErrorMessage("validator.cpf"));
			}
		}
	}
}
