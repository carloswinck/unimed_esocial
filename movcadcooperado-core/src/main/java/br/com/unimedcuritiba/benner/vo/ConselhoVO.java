package br.com.unimedcuritiba.benner.vo;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * The persistent class for the sam_conselho database table.
 */
@Entity
@Table(name = "v_sam_conselho")
public class ConselhoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 4106849143689886885L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(length = 50, insertable = false, updatable = false)
	private String descricao;

	@Column(length = 10, insertable = false, updatable = false)
	private String sigla;

	@Column(insertable = false, updatable = false)
	private Date datainicial;

	@Column(insertable = false, updatable = false)
	private Date datafinal;

	@Column(insertable = false, updatable = false)
	private Integer codigoTiss;

	public ConselhoVO ()
	{
	}

	public long getHandle ()
	{
		return this.handle;
	}

	public void setHandle (final long handle)
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

	public String getSigla ()
	{
		return this.sigla;
	}

	public void setSigla (final String sigla)
	{
		this.sigla = sigla;
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

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	public Integer getCodigoTiss ()
	{
		return codigoTiss;
	}

	public void setCodigoTiss (final Integer codigoTiss)
	{
		this.codigoTiss = codigoTiss;
	}

}
