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
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_SAM_ESPECIALIDADE")
public class EspecialidadeVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -5187012323681580009L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "DATAINICIAL", insertable = false, updatable = false)
	private Date dataInicial;

	@Column(name = "DATAFINAL", insertable = false, updatable = false)
	private Date dataFinal;

	@Column(name = "DESCRICAO", insertable = false, updatable = false)
	private String descricao;

	@Column(name = "TIPO", insertable = false, updatable = false)
	private String tipo;

	@Column(name = "CODIGO", insertable = false, updatable = false)
	private Long codigo;

	public EspecialidadeVO ()
	{
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

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public Long getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Long codigo)
	{
		this.codigo = codigo;
	}

	public Date getDataFinal ()
	{
		return dataFinal;
	}

	public void setDataFinal (final Date dataFinal)
	{
		this.dataFinal = dataFinal;
	}

	public String getTipo ()
	{
		return tipo;
	}

	public void setTipo (final String tipo)
	{
		this.tipo = tipo;
	}

	public void setPkValue (final Long value)
	{
	}
}
