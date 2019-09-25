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
 * Enum Identificador dos Estados
 *
 * @author Paulo Roberto Schwertner
 * @since 4.0.0
 */
public enum EstadoEnum implements br.com.visionnaire.core.common.Enumerable<Integer>
{

	AC(12, "ACRE", "AC"),
	AL(27, "ALAGOAS", "AL"),
	AM(13, "AMAZONAS", "AM"),
	AP(16, "AMAPA", "AP"),
	BA(29, "BAHIA", "BA"),
	CE(23, "CEARA", "CE"),
	DF(53, "DISTRITO FEDERAL", "DF"),
	ES(32, "ESPIRITO SANTO", "ES"),
	GO(52, "GOIAS", "GO"),
	MA(21, "MARANHAO", "MA"),
	MG(31, "MINAS GERAIS", "MG"),
	MS(50, "MATO GROSSO DO SUL", "MS"),
	MT(51, "MATO GROSSO", "MT"),
	PA(15, "PARA", "PA"),
	PB(25, "PARAIBA", "PB"),
	PE(26, "PERNAMBUCO", "PE"),
	PI(22, "PIAUI", "PI"),
	PR(41, "PARANA", "PR"),
	RJ(33, "RIO DE JANEIRO", "RJ"),
	RN(24, "RIO GRANDE DO NORTE", "RN"),
	RO(11, "RONDONIA", "RO"),
	RR(14, "RORAIMA", "RR"),
	RS(43, "RIO GRANDE DO SUL", "RS"),
	SC(42, "SANTA CATARINA", "SC"),
	SE(28, "SERGIPE", "SE"),
	SP(35, "SAO PAULO", "SP"),
	TO(17, "TOCANTINS", "TO");

	private Integer nuUfIbge;

	private String noUfIbge;

	private String sgUfIbge;

	private EstadoEnum (final Integer nuUfIbge, final String noUfIbge, final String sgUfIbge)
	{
		this.nuUfIbge = nuUfIbge;
		this.noUfIbge = noUfIbge;
		this.sgUfIbge = sgUfIbge;
	}

	private static final Map<String, EstadoEnum> MAP = new HashMap<String, EstadoEnum>();

	static
	{
		for (final EstadoEnum e : EnumSet.allOf(EstadoEnum.class))
		{
			MAP.put(e.getSgUfIbge(), e);
		}
	}

	public static EstadoEnum getBySigla (final String sgUfIbge)
	{
		if (MAP.containsKey(sgUfIbge))
		{
			return MAP.get(sgUfIbge);
		}

		return null;
	}

	public static Map<String, EstadoEnum> getMap ()
	{
		return MAP;
	}

	public Integer getCodigo ()
	{
		return nuUfIbge;
	}

	public void setCodigo (final Integer id)
	{
		this.nuUfIbge = id;
	}

	public String getDescricao ()
	{
		return noUfIbge;
	}

	public void setDescricao (final String descricao)
	{
		this.noUfIbge = descricao;
	}

	public String getSgUfIbge ()
	{
		return sgUfIbge;
	}

	public void setSgUfIbge (final String sgUfIbge)
	{
		this.sgUfIbge = sgUfIbge;
	}

}
