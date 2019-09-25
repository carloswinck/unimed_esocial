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
 * @since 01 de jul de 2016
 */
public enum CategoriaCnhEnum implements Enumerable<String>
{
	A("A", "A"),
	B("B", "B"),
	C("C", "C"),
	D("D", "D"),
	E("E", "E"),
	AB("AB", "AB"),
	AC("AC", "AC"),
	AD("AD", "AD"),
	AE("AE", "AE");

	private String codigo;
	private String descricao;

	private static final Map<String, CategoriaCnhEnum> map = new HashMap<String, CategoriaCnhEnum>();

	static
	{
		for (final CategoriaCnhEnum e : EnumSet.allOf(CategoriaCnhEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private CategoriaCnhEnum (final String codigo, final String descricao)
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

	public static CategoriaCnhEnum get (final String codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<String, CategoriaCnhEnum> getMap ()
	{
		return map;
	}

}
