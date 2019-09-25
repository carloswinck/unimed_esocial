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
import br.com.visionnaire.core.common.validations.Validator;

/**
 * Enum do Tipo de Dependente do eSocial
 *
 * @author Paulo Roberto Schwertner
 * @since 10/10/2016
 */
public enum TipoDependenteEnum implements Enumerable<Integer>
{
	DESCONHECIDO(0, "Desconhecido"),
	CONJUGE(1, "Cônjuge"),
	COMPANHEIRO(2, "Companheiro(a) com o(a) qual tenha filho ou viva há mais de 5(cinco) anos ou possua Declaração de União Estável"),
	FILHO_21_ANOS(3, "Filho(a) ou enteado(a)"),
	// FILHO_INCAPACITADO(5, "Filho(a) ou enteado(a) em qualquer idade, quando incapacitado fisica e/ou
	// mentalmente para o trabalho"),
	IRMAO_NETO_21_ANOS(4, "Irmão(ã), neto(a) ou bisneto(a) sem arrimo dos pais, do(a) qual detenha a guarda judicial"),
	// IRMAO_NETO_ESTUDANTE_24_ANOS(7,
	// "Irmão(ã), neto(a) ou bisneto(a) sem arrimo dos pais, com idade ate 24 anos, se ainda estiver cursando
	// estabelecimento de nível superior ou escola técnica de 2º grau, desde que tenha detido sua guarda
	// judicial até os 21 anos"),
	// IRMAO_NETO_INCAPACITADO(8,
	// "Irmão(ã), neto(a) ou bisneto(a) sem arrimo dos pais, do(a) qual detenha a guarda judicial, em qualquer
	// idade, quando incapacitado fisica e/ou mentalmente para o trabalho"),
	PAIS_AVOS_BISAVOS(5, "Pais, avós e bisavós"),
	MENOR_POBRE_21_ANOS(6, "Menor pobre do qual detenha a guarda judicial"),
	INCAPAZ(7, "A pessoa absolutamente incapaz, da qual seja tutor ou curador"),
	FILHO_ESTUDANTE_24_ANOS(8, "Filho(a) ou enteado(a) universitário(a) ou cursando escola técnica de 2º grau, até 24(vinte e quatro) anos"),
	EX_CONJUGE_PENSAO_ALIMENTAR(15, "Ex-cônjuge"),
	AGREGADO(99, "Agregado/Outros");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, TipoDependenteEnum> map = new HashMap<Integer, TipoDependenteEnum>();

	static
	{
		for (final TipoDependenteEnum e : EnumSet.allOf(TipoDependenteEnum.class))
		{
			if (Validator.isNotEmpty(e.getCodigo()))
			{
				map.put(e.getCodigo(), e);
			}
		}
	}

	private TipoDependenteEnum (final Integer codigo, final String descricao)
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

	public static TipoDependenteEnum get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, TipoDependenteEnum> getMap ()
	{
		return map;
	}
}
