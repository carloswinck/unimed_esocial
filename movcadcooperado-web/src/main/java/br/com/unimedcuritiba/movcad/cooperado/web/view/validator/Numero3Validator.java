package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.validator.MultiFieldValidator;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.messages.Messages;

@Named
@ApplicationScoped
public class Numero3Validator
    implements MultiFieldValidator
{
	@Inject
	private Messages messages;

	@Override
	public boolean validateValues (final FacesContext context, final List<UIInput> components, final List<Object> values)
	{
		if (!Validator.isEmpty(values))
		{
			final Object ddd3 = values.get(0);
			final Object prefixo3 = values.get(1);
			final Object numero3 = values.get(2);

			String ddd = null;
			String prefixo = null;
			String numero = null;

			if (Validator.isNotEmpty((String)ddd3))
			{
				ddd = String.valueOf(ddd3).trim();
			}

			if (Validator.isNotEmpty((String)prefixo3))
			{
				prefixo = String.valueOf(prefixo3).trim();
			}
			if (Validator.isNotEmpty((String)numero3))
			{
				numero = String.valueOf(numero3).trim();
			}

			if (ddd != null || prefixo != null || numero != null)
			{
				final String numeroCompleto = prefixo + numero;

				if (numeroCompleto.length() < 8 || numeroCompleto.length() > 9)
				{
					messages.addErrorMessage("error.numero3.tamanhoInvalido");
					return false;
				}
				else if (!containsOnlyNumbers(numeroCompleto))
				{
					messages.addErrorMessage("error.numero3.tamanhoInvalido");
					return false;
				}

			}

		}
		return true;
	}

	public boolean containsOnlyNumbers (final String str)
	{
		// verifica se é vazio ou nulo
		if (str == null || str.length() == 0)
		{
			return false;
		}
		for (int i = 0; i < str.length(); i++)
		{
			// Se o digito for diferente de um digito, retorna falso.
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

}
