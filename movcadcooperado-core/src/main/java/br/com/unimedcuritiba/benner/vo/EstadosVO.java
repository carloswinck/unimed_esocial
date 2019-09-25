package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * The persistent class for the ESTADOS database table.
 */
@Entity
@Table(name = "V_ESTADOS")
public class EstadosVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -2091817287858988704L;

	@Id
	@Column(insertable = false, updatable = false)
	private Long handle;

	@Column(insertable = false, updatable = false)
	private String nome;

	@Column(insertable = false, updatable = false)
	private String sigla;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pais", insertable = false, updatable = false)
	private PaisesVO pais;

	@Column(insertable = false, updatable = false)
	private Integer codigoTiss;

	public EstadosVO ()
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

	public String getNome ()
	{
		return this.nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public String getSigla ()
	{
		return this.sigla;
	}

	public void setSigla (final String sigla)
	{
		this.sigla = sigla;
	}

	public Integer getCodigoTiss ()
	{
		return this.codigoTiss;
	}

	public void setCodigoTiss (final Integer codigoTiss)
	{
		this.codigoTiss = codigoTiss;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	public PaisesVO getPais ()
	{
		return pais;
	}

	public void setPais (final PaisesVO pais)
	{
		this.pais = pais;
	}
}
