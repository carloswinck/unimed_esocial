package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_SFN_TIPODOCUMENTO")
public class TipoDocumentoFinanceiroVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -6818301134390276774L;

	@Id
	@Column(name = "handle", insertable = false, updatable = false)
	private Long id;

	@Column(name = "z_grupo", insertable = false, updatable = false)
	private Long zGrupo;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	public TipoDocumentoFinanceiroVO ()
	{
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long id)
	{
		this.id = id;
	}

	public Long getzGrupo ()
	{
		return zGrupo;
	}

	public void setzGrupo (final Long zGrupo)
	{
		this.zGrupo = zGrupo;
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
