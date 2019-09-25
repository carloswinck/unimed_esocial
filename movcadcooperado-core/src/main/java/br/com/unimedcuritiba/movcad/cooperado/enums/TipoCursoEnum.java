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
 * Retirado os itens especialização, extra-curricular e
 * participação em eventos referente ao bug UCWBACCF2-88
 *
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
public enum TipoCursoEnum implements Enumerable<Integer>
{
	DESCONHECIDO(0, "DESCONHECIDO"),
	GRADUACAO(1, "Graduação"),
	RESIDENCIA(2, "Residência"),
	ESPECIALIZACAO(3, "Especialização"),
	POS_GRADUACAO(4, "Pós-Graduação"),
	MESTRADO(5, "Mestrado"),
	DOUTORADO(6, "Doutorado"),
	POS_DOUTORADO(7, "Pós-Doutorado"),
	EXTRA_CURRICULAR(8, "Extra-Curricular"),
	PARTICIPACAO_EVENTOS(9, "Participação Eventos");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, TipoCursoEnum> map = new HashMap<Integer, TipoCursoEnum>();

	static
	{
		for (final TipoCursoEnum e : EnumSet.allOf(TipoCursoEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private TipoCursoEnum (final Integer codigo, final String descricao)
	{
		this.codigo = codigo;
		this.descricao = descricao;
	}

	@Override
	public Integer getCodigo ()
	{
		return codigo;
	}

	@Override
	public void setCodigo (final Integer codigo)
	{
		this.codigo = codigo;
	}

	@Override
	public String getDescricao ()
	{
		return descricao;
	}

	@Override
	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public static TipoCursoEnum get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, TipoCursoEnum> getMap ()
	{
		return map;
	}

}
