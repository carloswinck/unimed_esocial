/*
 * %W% %E%
 *
 * Copyright (c) 2015, Unimed Curitiba
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum Identificador das Categorias
 *
 * @author Paulo Roberto Schwertner
 * @since 4.0.0
 */
public enum CategoriaPrestadorEnum implements br.com.visionnaire.core.common.Enumerable<Integer>
{

	MEDICO_COOPERADO(1, "Médico cooperado"),
	PF_NAOCOOPERADO(2, "Pessoa física não cooperado"),
	AUTORIZADO(3, "Autorizado"),
	AUTORIZADO_LIMINAR(4, "Autorizado Liminar"),
	CREDENCIADO(5, "Credenciado"),
	UNIMED(6, "Unimed"),
	RECURSOS_PROPRIOS(7, "Recursos Próprios"),
	LIVRE_ESCOLHA(8, "Livre Escolha"),
	PROPONENTE(9, "Proponente");

	private Integer codigo;

	private String descricao;

	private CategoriaPrestadorEnum (final Integer codigo, final String descricao)
	{
		this.codigo = codigo;
		this.descricao = descricao;
	}

	private static final Map<String, CategoriaPrestadorEnum> MAP = new HashMap<String, CategoriaPrestadorEnum>();

	static
	{
		for (final CategoriaPrestadorEnum e : EnumSet.allOf(CategoriaPrestadorEnum.class))
		{
			MAP.put(e.getDescricao(), e);
		}
	}

	public static Map<String, CategoriaPrestadorEnum> getMap ()
	{
		return MAP;
	}

	public Integer getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Integer id)
	{
		this.codigo = id;
	}

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

}
