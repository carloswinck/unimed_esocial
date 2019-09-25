/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Remove a mascara do CPF apos o submit do botao Salvar da Dialog de Dependentes
 *
 * @author Eloi Bilek
 * @since 10 de mar de 2017
 */
@FacesConverter(value = "converterCPF")
public class ConverterCPF
    implements Converter
{

	@Override
	public Object getAsObject (final FacesContext context, final UIComponent component, String value)
	{
		if (value != null && value != "")
		{
			value = value.toString().replaceAll("[- /.]", "");
			value = value.toString().replaceAll("[-()]", "");
		}
		return value;
	}

	@Override
	public String getAsString (final FacesContext context, final UIComponent component, final Object value)
	{
		return value.toString();
	}

}
