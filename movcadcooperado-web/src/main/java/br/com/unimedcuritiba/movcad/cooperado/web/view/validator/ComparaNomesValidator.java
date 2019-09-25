package br.com.unimedcuritiba.movcad.cooperado.web.view.validator;

import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang.StringUtils;
import org.omnifaces.validator.MultiFieldValidator;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.messages.Messages;

@Named
@ApplicationScoped
public class ComparaNomesValidator
    implements MultiFieldValidator
{
	@Inject
	private Messages messages;

	@Override
	public boolean validateValues (final FacesContext context, final List<UIInput> components, final List<Object> values)
	{
		if (!Validator.isEmpty(values))
		{
			Object objNomeCompleto = null;
			Object objNomeMae = null;
			Object objNomePai = null;

			if (!Validator.isEmpty(components))
			{
				if (values.size() == 1)
				{
					if (components.get(0).getId().equals("nomeCompleto"))
					{
						objNomeCompleto = values.get(0);
					}
					else if (components.get(0).getId().equals("nomeMae"))
					{
						objNomeMae = values.get(0);
					}
					else if (components.get(0).getId().equals("nomePai"))
					{
						objNomePai = values.get(0);
					}
				}
				else if (values.size() == 2)
				{
					if (components.get(0).getId().equals("nomeCompleto"))
					{
						objNomeCompleto = values.get(0);
					}
					else if (components.get(0).getId().equals("nomeMae"))
					{
						objNomeMae = values.get(0);
					}
					else
					{
						objNomePai = values.get(0);
					}

					if (components.get(1).getId().equals("nomeCompleto"))
					{
						objNomeCompleto = values.get(1);
					}
					else if (components.get(1).getId().equals("nomeMae"))
					{
						objNomeMae = values.get(1);
					}
					else
					{
						objNomePai = values.get(1);
					}
				}
				else if (values.size() == 3)
				{
					objNomeCompleto = values.get(0);
					objNomeMae = values.get(1);
					objNomePai = values.get(2);
				}
			}

			String nomeCompleto = null;
			String nomeMae = null;
			String nomePai = null;

			if (objNomeCompleto != null)
			{
				nomeCompleto = String.valueOf(objNomeCompleto).trim();
			}

			if (objNomeMae != null)
			{
				nomeMae = String.valueOf(objNomeMae).trim();
			}
			if (objNomePai != null)
			{
				nomePai = String.valueOf(objNomePai).trim();
			}

			if (nomeCompleto != null && nomeMae != null)
			{
				if (StringUtils.equalsIgnoreCase(nomeCompleto, nomeMae) && StringUtils.equalsIgnoreCase(nomeCompleto, nomePai) &&
				    Validator.isNotEmpty(nomePai))
				{
					messages.addErrorMessage("error.nomePai.nomeMae.nomeCompleto.incorretos");
					return false;
				}
				else if (StringUtils.equalsIgnoreCase(nomeCompleto, nomeMae))
				{
					messages.addErrorMessage("error.nomeCompleto.nomeMae.incorretos");
					return false;
				}
				else if (StringUtils.equalsIgnoreCase(nomeCompleto, nomePai) && Validator.isNotEmpty(nomePai))
				{
					messages.addErrorMessage("error.nomeCompleto.nomePai.incorretos");
					return false;
				}
				else if (StringUtils.equalsIgnoreCase(nomeMae, nomePai) && Validator.isNotEmpty(nomePai))
				{
					messages.addErrorMessage("error.nomeMae.nomePai.incorretos");
					return false;
				}
			}

		}
		return true;
	}

}
