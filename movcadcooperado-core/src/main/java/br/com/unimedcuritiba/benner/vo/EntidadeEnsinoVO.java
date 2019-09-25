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
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Entity
@Table(name = "V_ENTIDADESENSINO")
public class EntidadeEnsinoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -4937021869513734487L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "codigo", insertable = false, updatable = false)
	private Long codigo;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "k_inativo", length = 1, insertable = false, updatable = false)
	private Boolean inativo;

	public EntidadeEnsinoVO ()
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

	public Long getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Long codigo)
	{
		this.codigo = codigo;
	}

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public Boolean getInativo ()
	{
		return inativo;
	}

	public void setInativo (final Boolean inativo)
	{
		this.inativo = inativo;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
