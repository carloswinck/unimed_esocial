package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Entity
@Table(name = "V_LOGRADOUROS_TIPO")
public class TipoLogradouroVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -2337488038116042072L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "codigo", insertable = false, updatable = false)
	private Long codigo;

	@Column(name = "descricao", insertable = false, updatable = false)
	private String descricao;

	@Column(name = "sigla", insertable = false, updatable = false)
	private String sigla;

	public TipoLogradouroVO ()
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

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	public String getSigla ()
	{
		return sigla;
	}

	public void setSigla (final String sigla)
	{
		this.sigla = sigla;
	}

}
