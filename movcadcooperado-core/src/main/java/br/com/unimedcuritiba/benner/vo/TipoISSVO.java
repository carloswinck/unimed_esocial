package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_SFN_ISS")
public class TipoISSVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 3926611735865262042L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	public TipoISSVO ()
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
		// TODO Auto-generated method stub

	}

}
