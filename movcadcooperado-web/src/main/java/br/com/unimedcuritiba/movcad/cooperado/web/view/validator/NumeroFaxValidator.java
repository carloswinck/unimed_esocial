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
public class NumeroFaxValidator
    implements MultiFieldValidator
{
	@Inject
	private Messages messages;

	@Override
	public boolean validateValues (final FacesContext context, final List<UIInput> components, final List<Object> values)
	{
		if (!Validator.isEmpty(values))
		{
			final Object dddFax = values.get(0);
			final Object prefixoFax = values.get(1);
			final Object numeroFax = values.get(2);

			String ddd = null;
			String prefixo = null;
			String numero = null;

			if (Validator.isNotEmpty((String)dddFax))
			{
				ddd = String.valueOf(dddFax).trim();
			}

			if (Validator.isNotEmpty((String)prefixoFax))
			{
				prefixo = String.valueOf(prefixoFax).trim();
			}
			if (Validator.isNotEmpty((String)numeroFax))
			{
				numero = String.valueOf(numeroFax).trim();
			}

			if (ddd != null || prefixo != null || numero != null)
			{
				final String numeroCompleto = prefixo + numero;

				if (numeroCompleto.length() < 8 || numeroCompleto.length() > 9)
				{
					messages.addErrorMessage("error.numeroFax.tamanhoInvalido");
					return false;
				}
				else if (!containsOnlyNumbers(numeroCompleto))
				{
					messages.addErrorMessage("error.numeroFax.tamanhoInvalido");
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
