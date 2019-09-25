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
 * 1 - Branca;
 * 2 - Negra;
 * 3 - Parda (parda ou declarada como mulata, cabocla, cafuza, mameluca ou mestiça de negro com pessoa
 * de outra cor ou raça);
 * 4 - Amarela (de origem japonesa, chinesa, coreana etc);
 * 5 - Indígena;
 * 6 - Não informado.
 * 
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */
public enum RacaCorEnum implements Enumerable<Integer>
{
	DESCONHECIDO(0, "Desconhecido"),
	BRANCA(1, "Branca"),
	NEGRA(2, "Negra"),
	PARDA(3, "Parda"),
	AMARELA(4, "Amarela"),
	INDIGENA(5, "Indígena"),
	NA0_INFORMADO(6, "Não informado");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, RacaCorEnum> map = new HashMap<Integer, RacaCorEnum>();

	static
	{
		for (final RacaCorEnum e : EnumSet.allOf(RacaCorEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private RacaCorEnum (final Integer codigo, final String descricao)
	{
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Integer codigo)
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

	public static RacaCorEnum get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, RacaCorEnum> getMap ()
	{
		return map;
	}

}
