package br.com.unimedcuritiba.movcad.cooperado.web.util;

import br.com.visionnaire.core.common.validations.Validator;

/**
 * Enum do Tipo Pessoa do Benner
 *
 * @author Paulo Roberto Schwertner
 */
public enum TipoPessoa
{

	Física(1),
	Jurídica(2);

	private int codigo;

	private TipoPessoa (final int codigo)
	{
		this.codigo = codigo;
	}

	public int getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final int codigo)
	{
		this.codigo = codigo;
	}

	public static TipoPessoa get (final int tipoPessoa)
	{
		for (final TipoPessoa tg : TipoPessoa.values())
		{
			if (tg.codigo == tipoPessoa)
			{
				return tg;
			}
		}
		return null;
	}

	public static TipoPessoa get (final String tipoPessoa)
	{
		if (Validator.isInt(tipoPessoa))
		{
			return get(Integer.parseInt(tipoPessoa));
		}
		return null;
	}
}
