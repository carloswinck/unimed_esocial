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
public enum SituacaoEnumPreCad implements Enumerable<Integer>
{
	DESCONHECIDO(0, "Ignorar / DESCONHECIDO"),
	DIGITACAO(1, "Digitação"),
	ANALISE(2, "Analise"),
	CONCLUIDO_PENDENTE_PAGAMENTO(3, "Concluído Pendente de Pagamento"),
	CONCLUIDO_PAGAMENTO_EFETUADO(4, "Concluído Pagamento Efetuadao");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, SituacaoEnumPreCad> map = new HashMap<Integer, SituacaoEnumPreCad>();

	static
	{
		for (final SituacaoEnumPreCad e : EnumSet.allOf(SituacaoEnumPreCad.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private SituacaoEnumPreCad (final Integer codigo, final String descricao)
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

	public static SituacaoEnumPreCad get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, SituacaoEnumPreCad> getMap ()
	{
		return map;
	}

}
