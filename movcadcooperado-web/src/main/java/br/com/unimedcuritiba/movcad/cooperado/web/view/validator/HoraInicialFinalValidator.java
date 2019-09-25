package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.validator.MultiFieldValidator;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.messages.Messages;

/**
 * Validador de hora inical e final
 */

@Named
@ApplicationScoped
public class HoraInicialFinalValidator
    implements MultiFieldValidator
{

	@Inject
	private Messages messages;

	@Override
	public boolean validateValues (final FacesContext context, final List<UIInput> components, final List<Object> values)
	{
		if (Validator.isNotEmpty(values))
		{
			final Object hi = values.get(0);
			final Object hf = values.get(1);

			// Se os dois valores forem null ou vazios, nao valida...
			if (Validator.isEmpty((String)hi) && Validator.isEmpty((String)hf))
			{
				return true;
			}

			// Obrigatorio preencher a hora inicial
			if (Validator.isNotEmpty((String)hf) && Validator.isEmpty((String)hi))
			{
				messages.addErrorMessage("validator.horaInicialNula");
				return false;
			}

			// Obrigatorio preencher a hora final
			if (Validator.isNotEmpty((String)hi) && Validator.isEmpty((String)hf))
			{
				messages.addErrorMessage("validator.horaFinalNula");
				return false;
			}

			// Valida se a final nao eh maior que a inicial
			final SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");

			Date horaInicial = null;
			Date horaFinal = null;

			try
			{
				horaInicial = formatador.parse(String.valueOf(hi));
				horaFinal = formatador.parse(String.valueOf(hf));

				if (horaFinal.before(horaInicial))
				{
					messages.addErrorMessage("validator.horaFinalMaior");
					return false;
				}

			}
			catch (final ParseException e)
			{
				messages.addErrorMessage("validator.horaIniciaFinalParse");
				return false;
			}
		}
		return true;
	}
}
