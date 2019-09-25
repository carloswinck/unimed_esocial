package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import br.com.visionnaire.core.messages.Messages;

@FacesValidator
@ApplicationScoped
@ManagedBean(eager = true)
public class InscricaoValidator
    implements Validator
{

	@Inject
	private Messages messages;

	@Override
	public void validate (final FacesContext context, final UIComponent component, final Object value) throws ValidatorException
	{

		if (value != null)
		{

			final String inscricaoINSS = String.valueOf(value);

			if (inscricaoINSS != null)
			{

				if (inscricaoINSS.length() != 11)
				{
					((UIInput)component).setValid(false);
					throw new ValidatorException(messages.getErrorMessage("error.inscricao.inss.validator"));
				}
			}

		}

	}

}
