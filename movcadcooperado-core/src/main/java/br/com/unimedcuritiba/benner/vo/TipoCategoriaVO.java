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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_SAM_CATEGORIA_PRESTADOR_TIPO")
public class TipoCategoriaVO
    extends VEntity<Long>
    implements Comparable<TipoCategoriaVO>
{

	private static final long serialVersionUID = -5187012323681580009L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "z_grupo", insertable = false, updatable = false)
	private Long zGrupo;

	@Column(name = "tipoinvertido", insertable = false, updatable = false)
	private String tipoInvertido;

	@Column(name = "tipoacrescimo", insertable = false, updatable = false)
	private String tipoAcrescimo;

	@Column(name = "tipo", insertable = false, updatable = false)
	private String tipo;

	@Column(name = "categoria", insertable = false, updatable = false)
	private Long categoria;

	@Column(name = "tipovalido", insertable = false, updatable = false)
	private Long tipoValido;

	@Column(name = "datainicial", insertable = false, updatable = false)
	private Date dataInicial;

	@Column(name = "datafinal", insertable = false, updatable = false)
	private Date dataFinal;

	public TipoCategoriaVO ()
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

	public Long getzGrupo ()
	{
		return zGrupo;
	}

	public void setzGrupo (final Long zGrupo)
	{
		this.zGrupo = zGrupo;
	}

	public String getTipoInvertido ()
	{
		return tipoInvertido;
	}

	public void setTipoInvertido (final String tipoInvertido)
	{
		this.tipoInvertido = tipoInvertido;
	}

	public String getTipoAcrescimo ()
	{
		return tipoAcrescimo;
	}

	public void setTipoAcrescimo (final String tipoAcrescimo)
	{
		this.tipoAcrescimo = tipoAcrescimo;
	}

	public String getTipo ()
	{
		return tipo;
	}

	public void setTipo (final String tipo)
	{
		this.tipo = tipo;
	}

	public Long getCategoria ()
	{
		return categoria;
	}

	public void setCategoria (final Long categoria)
	{
		this.categoria = categoria;
	}

	public Long getTipoValido ()
	{
		return tipoValido;
	}

	public void setTipoValido (final Long tipoValido)
	{
		this.tipoValido = tipoValido;
	}

	public Date getDataInicial ()
	{
		return dataInicial;
	}

	public void setDataInicial (final Date dataInicial)
	{
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal ()
	{
		return dataFinal;
	}

	public void setDataFinal (final Date dataFinal)
	{
		this.dataFinal = dataFinal;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo (final TipoCategoriaVO o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
