package br.com.unimedcuritiba.movcad.cooperado.enums;

/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import br.com.visionnaire.core.common.Enumerable;

/**
 * @author Eloi Bilek
 * @since 8 de jun de 2017
 */
public enum TipoDocumentoAnexoPreCadEnum implements Enumerable<Integer>
{
	DESCONHECIDO(0, "Ignorar / DESCONHECIDO"),
	CONDUTA_ETICA_PROFISSIONAL(1, "Declaração de conduta ética e profissional CRM/PR"),
	COMPROVANTE_ENDERECO(2, "Cópia autenticada de comprovante de endereço"),
	ALVARA_PREFEITURA_PF(3, "Cópia do Alvará da Prefeitura (PESSOA FÍSICA)"),
	TRIBUTOS_MUNICIPAIS_PF(4, "Certidão de Tributos Municipais para Pessoa Física"),
	NUMERO_INSCRICAO_INSS(5, "Documentação do número de inscrição no INSS"),
	NEGATIVA_DEBITOS_INSS(6, "Certidão Negativa de Débitos INSS - Declaração de Regularidade de Situação do Contribuinte Individual (DRSCI)"),
	DEBITOS_TRIBUTOS_FEDERAIS(7, "Certidão Conjunta de Débitos Relativos a Tributos Federais e à Dívida Ativa da União"),
	ANTECEDENTES_CRIMINAIS(8, "Atestado de Antecedentes Criminais"),
	NEGATIVA_2_DISTRIBUIDOR_CWB(9, "Certidão Negativa do 2º Distribuidor emitida em Curitiba"),
	NEGATIVA_TITULOS_PROTESTOS_CWB(10, "Certidão Negativa de Títulos e Protestos emitida em Curitiba"),
	INSCRICAO_CNES(11, "Documento Comprobatório de inscrição no CNES");

	private Integer codigo;
	private String descricao;

	private static final Map<Integer, TipoDocumentoAnexoPreCadEnum> map = new HashMap<Integer, TipoDocumentoAnexoPreCadEnum>();

	static
	{
		for (final TipoDocumentoAnexoPreCadEnum e : EnumSet.allOf(TipoDocumentoAnexoPreCadEnum.class))
		{
			map.put(e.getCodigo(), e);
		}
	}

	private TipoDocumentoAnexoPreCadEnum (final Integer codigo, final String descricao)
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

	public static TipoDocumentoAnexoPreCadEnum get (final Integer codigo)
	{
		if (map.containsKey(codigo))
		{
			return map.get(codigo);
		}

		return null;
	}

	public static Map<Integer, TipoDocumentoAnexoPreCadEnum> getMap ()
	{
		return map;
	}

}
