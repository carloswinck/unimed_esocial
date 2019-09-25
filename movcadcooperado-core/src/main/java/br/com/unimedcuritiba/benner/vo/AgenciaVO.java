/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * View da tabela sfn_agencia
 *
 * @author Eloi Bilek
 * @since 23 de dez de 2016
 */

@Entity
@Table(name = "V_SFN_AGENCIA")
public class AgenciaVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 4733596866463342690L;

	@Id
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "AGENCIA", insertable = false, updatable = false)
	private String agencia;

	@Column(name = "NOME", insertable = false, updatable = false)
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANCO", insertable = false, updatable = false)
	private BancoVO banco;

	@Column(name = "DV", insertable = false, updatable = false)
	private String dv;

	@Column(name = "SITUACAO", insertable = false, updatable = false)
	private String situacao;

	public AgenciaVO ()
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

	public String getAgencia ()
	{
		return agencia;
	}

	public void setAgencia (final String agencia)
	{
		this.agencia = agencia;
	}

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public BancoVO getBanco ()
	{
		return banco;
	}

	public void setBanco (final BancoVO banco)
	{
		this.banco = banco;
	}

	public String getDv ()
	{
		return dv;
	}

	public void setDv (final String dv)
	{
		this.dv = dv;
	}

	public String getSituacao ()
	{
		return situacao;
	}

	public void setSituacao (final String situacao)
	{
		this.situacao = situacao;
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
