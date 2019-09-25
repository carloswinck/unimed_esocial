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
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 *        1 - Visto permanente;
 *        2 - Visto temporário; 3 - Asilado;
 *        4 - Refugiado;
 *        5 - Solicitante de Refúgio;
 *        6 - Residente em país fronteiriço ao Brasil; 7 - Deficiente físico e com mais de 51 anos;
 *        8 - Com residência provisória e anistiado, em situação irregular;
 *        9 - Permanência no Brasil em razão de filhos ou cônjuge brasileiros; 10 - Beneficiado pelo acordo
 *        entre países do Mercosul;
 *        11 - Dependente de agente diplomático e/ou consular de países que mantém convênio de reciprocidade
 *        para o exercício de atividade remunerada no Brasil;
 *        12 - Beneficiado pelo Tratado de Amizade, Cooperação e Consulta entre a República Federativa do
 *        Brasil e a República Portuguesa.
 */
public enum RNECondicaoEnum implements Enumerable<Integer>
{
	DESCONHECIDO(0, "Desconhecido"),
	VISTO_PERMANENTE(1, "RNECondicaoEnum.VISTO_PERMANENTE"),
	VISTO_TEMPORARIO(2, "RNECondicaoEnum.VISTO_TEMPORARIO"),
	ASILADO(3, "RNECondicaoEnum.ASILADO"),
	REFUGIADO(4, "RNECondicaoEnum.REFUGIADO"),
	SOLICITANTE_DE_REFUGIO(5, "RNECondicaoEnum.SOLICITANTE_DE_REFUGIO"),
	RESIDENTE_EM_PAIS_FRONTEIRICO_AO_BRASIL(6, "RNECondicaoEnum.RESIDENTE_EM_PAIS_FRONTEIRICO_AO_BRASIL"),
	DEFICIENTE_FISICO_E_COM_MAIS_DE_51_ANOS(7, "RNECondicaoEnum.DEFICIENTE_FISICO_E_COM_MAIS_DE_51_ANOS"),
	COM_RESIDENCIA_PROVISORIA_E_ANISTIADO_EM_SITUACAO_IRREGULAR(8, "RNECondicaoEnum.COM_RESIDENCIA_PROVISORIA_E_ANISTIADO_EM_SITUACAO_IRREGULAR"),
	PERMANENCIA_NO_BRASIL_EM_RAZAO_DE_FILHOS_OU_CONJUGE_BRASILEIROS(9,
	        "RNECondicaoEnum.PERMANENCIA_NO_BRASIL_EM_RAZAO_DE_FILHOS_OU_CONJUGE_BRASILEIROS"),
	BENEFICIADO_PELO_ACORDO_ENTRE_PAISES_DO_MERCOSUL(10, "RNECondicaoEnum.BENEFICIADO_PELO_ACORDO_ENTRE_PAISES_DO_MERCOSUL"),
	DEPENDENTE_DE_AGENTE_DIPLOMATICO_E_OU_CONSULAR(11, "RNECondicaoEnum.DEPENDENTE_DE_AGENTE_DIPLOMATICO_E_OU_CONSULAR"),
	BENEFICIADO_PELO_TRATADO_DE_AMIZADE_COOPERACAO_CONSULTA(12, "RNECondicaoEnum.BENEFICIADO_PELO_TRATADO_DE_AMIZADE_COOPERACAO_CONSULTA");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, RNECondicaoEnum> map = new HashMap<Integer, RNECondicaoEnum>();

	static
	{
		for (final RNECondicaoEnum e : EnumSet.allOf(RNECondicaoEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private RNECondicaoEnum (final Integer codigo, final String descricao)
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

	public static RNECondicaoEnum get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, RNECondicaoEnum> getMap ()
	{
		return map;
	}
}
