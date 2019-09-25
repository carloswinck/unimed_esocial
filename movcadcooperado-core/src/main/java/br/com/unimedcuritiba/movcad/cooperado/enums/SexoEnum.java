/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import br.com.visionnaire.core.common.Enumerable;

/**
 * @author Tiago Henrique Gomes da Silva Balduino
 * @since 30 de jun de 2016
 *        F - Feminino;
 *        M - Masculino;
 */
public enum SexoEnum implements Enumerable<String>
{
	FEMININO("F", "Feminino"),
	MASCULINO("M", "Masculino");

	private String codigo;
	private String descricao;

	private static final Map<String, SexoEnum> map = new HashMap<String, SexoEnum>();

	static
	{
		for (final SexoEnum e : EnumSet.allOf(SexoEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private SexoEnum (final String codigo, final String descricao)
	{
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final String codigo)
	{
		this.codigo = codigo;
	}

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public static SexoEnum get (final String codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<String, SexoEnum> getMap ()
	{
		return map;
	}

}
