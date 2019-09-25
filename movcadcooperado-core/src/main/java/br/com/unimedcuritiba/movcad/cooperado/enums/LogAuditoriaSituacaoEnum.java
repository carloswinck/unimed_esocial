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
 * @since 27 de jun de 2016
 */
public enum LogAuditoriaSituacaoEnum implements Enumerable<Integer>
{
	DESCONHECIDO(0, "Ignorar / DESCONHECIDO"),
	ANALISE(1, "Analise"),
	CONCLUIDO(2, "Concluído");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, LogAuditoriaSituacaoEnum> map = new HashMap<Integer, LogAuditoriaSituacaoEnum>();

	static
	{
		for (final LogAuditoriaSituacaoEnum e : EnumSet.allOf(LogAuditoriaSituacaoEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private LogAuditoriaSituacaoEnum (final Integer codigo, final String descricao)
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

	public static LogAuditoriaSituacaoEnum get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, LogAuditoriaSituacaoEnum> getMap ()
	{
		return map;
	}

}
