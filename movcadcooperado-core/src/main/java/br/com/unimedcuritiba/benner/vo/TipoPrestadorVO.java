package br.com.unimedcuritiba.benner.vo;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * The view class for the v_sam_tipoprestador database table.
 */
@Entity
@Table(name = "v_sam_tipoprestador")
public class TipoPrestadorVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -4873882814068010228L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(length = 50, insertable = false, updatable = false)
	private String descricao;

	@Column(precision = 10, insertable = false, updatable = false)
	private Long codigo;

	@Column(insertable = false, updatable = false)
	private Date datainicial;

	@Column(insertable = false, updatable = false)
	private Date datafinal;

	public TipoPrestadorVO ()
	{
	}

	public Long getHandle ()
	{
		return this.handle;
	}

	public void setHandle (final Long handle)
	{
		this.handle = handle;
	}

	public String getDescricao ()
	{
		return this.descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public Date getDatainicial ()
	{
		return datainicial;
	}

	public void setDatainicial (final Date datainicial)
	{
		this.datainicial = datainicial;
	}

	public Date getDatafinal ()
	{
		return datafinal;
	}

	public void setDatafinal (final Date datafinal)
	{
		this.datafinal = datafinal;
	}

	public Long getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Long codigo)
	{
		this.codigo = codigo;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
