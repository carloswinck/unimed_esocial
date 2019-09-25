package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 10 de jan de 2017
 */

@Entity
@Table(name = "V_SAM_ESOCIAL_TIPODEPENDENTE")
public class TipoDependenteVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -1502723920219692414L;

	@Id
	@Column(name = "handle", insertable = false, updatable = false)
	private Long handle;

	@Column(name = "codigo", insertable = false, updatable = false)
	private Integer codigo;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	public TipoDependenteVO ()
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

	public Integer getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Integer codigo)
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

	@Override
	public void setPkValue (final Long value)
	{
	}
}
