/*
 * %W% %E%
 *
 * Copyright (c) 2015, Unimed Curitiba Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.enums;

/**
 * Enum Indicador Sim Não
 *
 * @author Paulo Roberto Schwertner
 * @since 4.0.0
 */
public enum SimNaoEnum implements br.com.visionnaire.core.common.Enumerable<Integer>
{

    /* *************************************** */
    /* ** NÃO ALTERAR A ORDEM DOS ENUMS ** */
    /* ** MAPEAMENTO JPA ENUMERATED ORDINAL ** */
    /* *************************************** */

	NAO(0, "N\u00E3o", "N", "enum.nao"),
	SIM(1, "Sim", "S", "enum.sim");

	private Integer id;

	private String descricao;

	private String sigla;

	private String property;

	private SimNaoEnum (final Integer id, final String descricao, final String sigla, final String property)
	{
		this.id = id;
		this.descricao = descricao;
		this.sigla = sigla;
		this.property = property;
	}

	public Integer getCodigo ()
	{
		return id;
	}

	public void setCodigo (final Integer id)
	{
		this.id = id;
	}

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public String getSigla ()
	{
		return this.sigla;
	}

	public String getProperty ()
	{
		return property;
	}

	public void setProperty (final String property)
	{
		this.property = property;
	}

	public static SimNaoEnum getBySigla (final String sigla)
	{
		if (SIM.getSigla().equalsIgnoreCase(sigla))
		{
			return SIM;
		}
		else if (NAO.getSigla().equalsIgnoreCase(sigla))
		{
			return NAO;
		}
		return null;
	}

	public boolean toBoolean ()
	{
		if (SIM.getCodigo().equals(this.id))
		{
			return true;
		}
		return false;
	}
}
