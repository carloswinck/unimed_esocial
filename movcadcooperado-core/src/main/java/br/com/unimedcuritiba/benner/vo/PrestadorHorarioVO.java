/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

/**
 * The persistent class for the V_SAM_PRESTADOR_HORARIO database view.
 *
 * @author Paulo Roberto Schwertner
 * @since 03/10/2016
 */
@Entity
@Table(name = "V_SAM_PRESTADOR_HORARIO")
public class PrestadorHorarioVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 2450873329382944540L;

	@Id
	@Column(name = "handle", insertable = false, updatable = false)
	private Long handle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADOR", insertable = false, updatable = false)
	private PrestadorVO prestador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENDERECO", insertable = false, updatable = false)
	private PrestadorEnderecoVO prestadorEndereco;

	@Temporal(TemporalType.TIME)
	@Column(name = "HORAINICIAL", insertable = false, updatable = false)
	private Date horaInicial;

	@Temporal(TemporalType.TIME)
	@Column(name = "HORAFINAL", insertable = false, updatable = false)
	private Date horaFinal;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "DOMINGO", insertable = false, updatable = false)
	private Boolean domingo;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SEGUNDA", insertable = false, updatable = false)
	private Boolean segunda;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "TERCA", insertable = false, updatable = false)
	private Boolean terca;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "QUARTA", insertable = false, updatable = false)
	private Boolean quarta;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "QUINTA", insertable = false, updatable = false)
	private Boolean quinta;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SEXTA", insertable = false, updatable = false)
	private Boolean sexta;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SABADO", insertable = false, updatable = false)
	private Boolean sabado;

	public PrestadorHorarioVO ()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub
	}

	public Long getHandle ()
	{
		return handle;
	}

	public void setHandle (final Long id)
	{
		handle = id;
	}

	public Date getHoraInicial ()
	{
		return horaInicial;
	}

	public void setHoraInicial (final Date horaInicial)
	{
		this.horaInicial = horaInicial;
	}

	public Date getHoraFinal ()
	{
		return horaFinal;
	}

	public void setHoraFinal (final Date horaFinal)
	{
		this.horaFinal = horaFinal;
	}

	public Boolean getDomingo ()
	{
		return domingo;
	}

	public void setDomingo (final Boolean domingo)
	{
		this.domingo = domingo;
	}

	public Boolean getSegunda ()
	{
		return segunda;
	}

	public void setSegunda (final Boolean segunda)
	{
		this.segunda = segunda;
	}

	public Boolean getTerca ()
	{
		return terca;
	}

	public void setTerca (final Boolean terca)
	{
		this.terca = terca;
	}

	public Boolean getQuarta ()
	{
		return quarta;
	}

	public void setQuarta (final Boolean quarta)
	{
		this.quarta = quarta;
	}

	public Boolean getQuinta ()
	{
		return quinta;
	}

	public void setQuinta (final Boolean quinta)
	{
		this.quinta = quinta;
	}

	public Boolean getSexta ()
	{
		return sexta;
	}

	public void setSexta (final Boolean sexta)
	{
		this.sexta = sexta;
	}

	public Boolean getSabado ()
	{
		return sabado;
	}

	public void setSabado (final Boolean sabado)
	{
		this.sabado = sabado;
	}

	public PrestadorEnderecoVO getPrestadorEndereco ()
	{
		return prestadorEndereco;
	}

	public void setPrestadorEndereco (final PrestadorEnderecoVO prestadorEndereco)
	{
		this.prestadorEndereco = prestadorEndereco;
	}

	public PrestadorVO getPrestador ()
	{
		return prestador;
	}

	public void setPrestador (final PrestadorVO prestador)
	{
		this.prestador = prestador;
	}

}
