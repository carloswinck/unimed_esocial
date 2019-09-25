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
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */

@Entity
@Table(name = "V_SAM_ESTADOCIVIL")
public class EstadoCivilVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 2714477680983020232L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(insertable = false, updatable = false)
	private String descricao;

	public EstadoCivilVO ()
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

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

}
