/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

/**
 * View da tabela sfn_contafin
 *
 * @author Eloi Bilek
 * @since 23 de dez de 2016
 */

@Entity
@Table(name = "V_SFN_CONTAFIN")
public class ContaFinVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -6251766058003140339L;

	@Id
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANCO", insertable = false, updatable = false)
	private BancoVO banco;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AGENCIA", insertable = false, updatable = false)
	private AgenciaVO agencia;

	@Column(name = "CONTACORRENTE", insertable = false, updatable = false)
	private String contaCorrente;

	@Column(name = "DV", insertable = false, updatable = false)
	private String dv;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EHCONTAPOUPANCA")
	private Boolean contaPoupanca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestador", insertable = false, updatable = false)
	private PrestadorVO prestador;

	public ContaFinVO ()
	{
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long id)
	{
		this.id = id;
	}

	public BancoVO getBanco ()
	{
		return banco;
	}

	public void setBanco (final BancoVO banco)
	{
		this.banco = banco;
	}

	public AgenciaVO getAgencia ()
	{
		return agencia;
	}

	public void setAgencia (final AgenciaVO agencia)
	{
		this.agencia = agencia;
	}

	public String getContaCorrente ()
	{
		return contaCorrente;
	}

	public void setContaCorrente (final String contaCorrente)
	{
		this.contaCorrente = contaCorrente;
	}

	public String getDv ()
	{
		return dv;
	}

	public void setDv (final String dv)
	{
		this.dv = dv;
	}

	public Boolean getContaPoupanca ()
	{
		return contaPoupanca;
	}

	public void setContaPoupanca (final Boolean contaPoupanca)
	{
		this.contaPoupanca = contaPoupanca;
	}

	public PrestadorVO getPrestador ()
	{
		return prestador;
	}

	public void setPrestador (final PrestadorVO prestador)
	{
		this.prestador = prestador;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see br.com.visionnaire.core.entity.Persistable#setPkValue(java.io.Serializable)
	 */
	@Override
	public void setPkValue (final Long value)
	{
	}

}
