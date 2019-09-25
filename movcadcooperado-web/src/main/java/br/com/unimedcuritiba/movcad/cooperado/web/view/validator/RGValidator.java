package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import java.util.Arrays;
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
public class RGValidator
    implements Validator
{
	@Inject
	private Messages messages;

	@Override
	public void validate (final FacesContext context, final UIComponent component, final Object value) throws ValidatorException
	{
		String valor = null;
		if (value != null)
		{
			valor = String.valueOf(value);
			final char[] vetorSequencial = valor.toCharArray();
			final char[] vetorVerificador = new char[vetorSequencial.length];
			final char digito;
			if (vetorSequencial != null)
			{
				digito = vetorSequencial[0];
				for (int i = 0; i < vetorSequencial.length; i++)
				{
					if (vetorSequencial[i] == digito)
					{
						vetorVerificador[i] = vetorSequencial[i];
					}
				}
				if (Arrays.equals(vetorVerificador, vetorSequencial))
				{
					((UIInput)component).setValid(false);
					throw new ValidatorException(messages.getErrorMessage("dwr.validation.message.text.rg"));
				}
			}
		}

	}

}
