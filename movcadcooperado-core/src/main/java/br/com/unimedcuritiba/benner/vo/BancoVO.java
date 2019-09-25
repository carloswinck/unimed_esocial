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
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * View da tabela sfn_banco
 *
 * @author Eloi Bilek
 * @since 23 de dez de 2016
 */

@Entity
@Table(name = "V_SFN_BANCO")
public class BancoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -5165535946244564025L;

	@Id
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "CODIGO", insertable = false, updatable = false)
	private Long codigo;

	@Column(name = "NOME", insertable = false, updatable = false)
	private String nome;

	@Column(name = "APELIDO", insertable = false, updatable = false)
	private String apelido;

	public BancoVO ()
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

	public Long getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Long codigo)
	{
		this.codigo = codigo;
	}

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public String getApelido ()
	{
		return apelido;
	}

	public void setApelido (final String apelido)
	{
		this.apelido = apelido;
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
