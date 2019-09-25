package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.messages.Messages;

/**
 * Valida nome conforme regras da ANS
 *
 * @param nome
 * @return
 */
@FacesValidator
@ApplicationScoped
@ManagedBean(eager = true)
public class NomeValidator
    implements javax.faces.validator.Validator
{
	@Inject
	private Messages messages;

	/**
	 * Verifica se o nome informado segue regra da ANS (Agencia Nacional de Saude) Regra ANS: não
	 * pode ter somente uma letra, exceto quando o nome começar por: D, E, I, O, U, Y
	 *
	 * @param nome <code>null<code> não permitido
	 * @return
	 */
	private boolean hasValidName (final String nome)
	{
		if (nome.length() <= 1)
		{
			final char letra = nome.charAt(0);
			if (letra != 'D' && letra != 'E' && letra != 'I' && letra != 'O' && letra != 'U' && letra != 'Y')
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public void validate (final FacesContext context, final UIComponent component, final Object value)
	{
		String nome = null;

		if (value != null)
		{
			nome = String.valueOf(value);
		}

		if (!Validator.isEmpty(nome))
		{
			nome = nome.trim().toUpperCase();

			// limpa espaços duplos ate deixar tudo com espaços simples
			while (nome.contains("  "))
			{
				nome = nome.replaceAll("  ", " ");
			}

			final String[] palavras = nome.split(" ");

			// se for digitado so o primeiro nome
			if (!hasValidName(palavras[0]))
			{
				((UIInput)component).setValid(false);
				throw new ValidatorException(messages.getErrorMessage("dwr.validation.message.text.PrimeiroNome"));
				// erros.add(getText("dwr.validation.message.text.PrimeiroNome", args));
			}

			// se só existir o primeiro nome
			if (palavras.length <= 1)
			{
				((UIInput)component).setValid(false);
				throw new ValidatorException(messages.getErrorMessage("dwr.validation.message.text.SemSobrenome"));
				// erros.add(getText("dwr.validation.message.text.SemSobrenome", args));
			}
			else
			{
				// valida o(s) sobrenome(s)
				for (int i = 1; i < palavras.length; i++)
				{
					if (!hasValidName(palavras[i]))
					{
						((UIInput)component).setValid(false);
						throw new ValidatorException(messages.getErrorMessage("dwr.validation.message.text.UltimoNome"));
						// erros.add(getText("dwr.validation.message.text.UltimoNome", args));
					}
				}

			}
		}
	}

}
