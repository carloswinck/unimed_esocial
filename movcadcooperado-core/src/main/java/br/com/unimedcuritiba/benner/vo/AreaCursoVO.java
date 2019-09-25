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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Entity
@Table(name = "V_AREASCURSO")
public class AreaCursoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 3046719288841387593L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "datainicial", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicial;

	@Column(name = "datafinal", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinal;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	public AreaCursoVO ()
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

	public Date getDataInicial ()
	{
		return dataInicial;
	}

	public void setDataInicial (final Date dataInicial)
	{
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal ()
	{
		return dataFinal;
	}

	public void setDataFinal (final Date dataFinal)
	{
		this.dataFinal = dataFinal;
	}

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
