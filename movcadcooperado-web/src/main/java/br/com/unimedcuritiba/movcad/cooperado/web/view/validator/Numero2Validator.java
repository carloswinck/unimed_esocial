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
public class Numero2Validator
    implements MultiFieldValidator
{
	@Inject
	private Messages messages;

	@Override
	public boolean validateValues (final FacesContext context, final List<UIInput> components, final List<Object> values)
	{
		if (!Validator.isEmpty(values))
		{
			final Object ddd2 = values.get(0);
			final Object prefixo2 = values.get(1);
			final Object numero2 = values.get(2);

			String ddd = null;
			String prefixo = null;
			String numero = null;

			if (Validator.isNotEmpty((String)ddd2))
			{
				ddd = String.valueOf(ddd2).trim();
			}

			if (Validator.isNotEmpty((String)prefixo2))
			{
				prefixo = String.valueOf(prefixo2).trim();
			}
			if (Validator.isNotEmpty((String)numero2))
			{
				numero = String.valueOf(numero2).trim();
			}

			if (ddd != null || prefixo != null || numero != null)
			{
				final String numeroCompleto = prefixo + numero;

				if (numeroCompleto.length() < 8 || numeroCompleto.length() > 9)
				{

					messages.addErrorMessage("error.numero2.tamanhoInvalido");
					return false;
				}
				else if (!containsOnlyNumbers(numeroCompleto))
				{
					messages.addErrorMessage("error.numero2.tamanhoInvalido");
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
