/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;

@Entity
@Table(name = "V_SAM_CATEGORIA_PRESTADOR")
public class CategoriaVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -5187012323681580009L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "datainicial", insertable = false, updatable = false)
	private Date dataInicial;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	@Compare(key = "consideradimvagas")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "consideradimvagas", length = 1)
	private Boolean consideraDimVagas;

	@Column(name = "CODIGO", insertable = false, updatable = false)
	private Long codigo;

	@Column(name = "CODIGOEXPORTACAO", insertable = false, updatable = false)
	private Long codigoExportacao;

	@Compare(key = "precooperado")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "precooperado", length = 1)
	private Boolean precoOperado;

	public CategoriaVO ()
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

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public Boolean getConsideraDimVagas ()
	{
		return consideraDimVagas;
	}

	public void setConsideraDimVagas (final Boolean consideraDimVagas)
	{
		this.consideraDimVagas = consideraDimVagas;
	}

	public Long getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Long codigo)
	{
		this.codigo = codigo;
	}

	public Long getCodigoExportacao ()
	{
		return codigoExportacao;
	}

	public void setCodigoExportacao (final Long codigoExportacao)
	{
		this.codigoExportacao = codigoExportacao;
	}

	public Boolean getPrecoOperado ()
	{
		return precoOperado;
	}

	public void setPrecoOperado (final Boolean precoOperado)
	{
		this.precoOperado = precoOperado;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}
}
