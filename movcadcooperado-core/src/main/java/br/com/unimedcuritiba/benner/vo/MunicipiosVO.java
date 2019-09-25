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
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Entity
@Table(name = "V_MUNICIPIOS")
public class MunicipiosVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 7010497193935244626L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "estado", insertable = false, updatable = false)
	private EstadosVO estado;

	@Column(name = "nome", insertable = false, updatable = false)
	private String nome;

	@Column(name = "cep", insertable = false, updatable = false)
	private String cep;

	public MunicipiosVO ()
	{
		// TODO Auto-generated constructor stub
	}

	public Long getHandle ()
	{
		return handle;
	}

	public void setHandle (final Long handle)
	{
		this.handle = handle;
	}

	public EstadosVO getEstado ()
	{
		return estado;
	}

	public void setEstado (final EstadosVO estado)
	{
		this.estado = estado;
	}

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public String getCep ()
	{
		return cep;
	}

	public void setCep (final String cep)
	{
		this.cep = cep;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
