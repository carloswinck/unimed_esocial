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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Entity
@Table(name = "V_LOGRADOUROS")
public class LogradouroVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -3821024512268965006L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@OneToOne
	@JoinColumn(name = "pais", insertable = false, updatable = false)
	private PaisesVO pais;

	@OneToOne
	@JoinColumn(name = "estado", insertable = false, updatable = false)
	private EstadosVO estado;

	@OneToOne
	@JoinColumn(name = "municipio", insertable = false, updatable = false)
	private MunicipiosVO municipio;

	@Column(name = "cep", insertable = false, updatable = false)
	private String cep;

	@Column(name = "logradouro", insertable = false, updatable = false)
	private String logradouro;

	@OneToOne
	@JoinColumn(name = "tipologradouro", insertable = false, updatable = false)
	private TipoLogradouroVO tipoLogradouro;

	@Column(name = "bairro", insertable = false, updatable = false)
	private String bairro;

	public LogradouroVO ()
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

	public PaisesVO getPais ()
	{
		return pais;
	}

	public void setPais (final PaisesVO pais)
	{
		this.pais = pais;
	}

	public EstadosVO getEstado ()
	{
		return estado;
	}

	public void setEstado (final EstadosVO estado)
	{
		this.estado = estado;
	}

	public MunicipiosVO getMunicipio ()
	{
		return municipio;
	}

	public void setMunicipio (final MunicipiosVO municipio)
	{
		this.municipio = municipio;
	}

	public String getCep ()
	{
		return cep;
	}

	public void setCep (final String cep)
	{
		this.cep = cep;
	}

	public String getLogradouro ()
	{
		return logradouro;
	}

	public void setLogradouro (final String logradouro)
	{
		this.logradouro = logradouro;
	}

	public TipoLogradouroVO getTipoLogradouro ()
	{
		return tipoLogradouro;
	}

	public void setTipoLogradouro (final TipoLogradouroVO tipoLogradouro)
	{
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getBairro ()
	{
		return bairro;
	}

	public void setBairro (final String bairro)
	{
		this.bairro = bairro;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
