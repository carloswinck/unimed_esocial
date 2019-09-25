package br.com.unimedcuritiba.benner.vo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_Z_TABELAS")
public class ZTabelaVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -6655251991012847553L;

	@Id
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "NOME", insertable = false, updatable = false)
	private String nome;

	@Column(name = "APELIDO", insertable = false, updatable = false)
	private String apelido;

	@Column(name = "LEGENDA", insertable = false, updatable = false)
	private String legenda;

	@Column(name = "ULTIMAATUALIZACAO", insertable = false, updatable = false)
	private Date ultimaAtualizacao;

	@Column(name = "ULTIMAALTERACAO", insertable = false, updatable = false)
	private Date ultimaAlteracao;

	public ZTabelaVO ()
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

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public String getApelido ()
	{
		return apelido;
	}

	public void setApelido (final String apelido)
	{
		this.apelido = apelido;
	}

	public String getLegenda ()
	{
		return legenda;
	}

	public void setLegenda (final String legenda)
	{
		this.legenda = legenda;
	}

	public Date getUltimaAtualizacao ()
	{
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao (final Date ultimaAtualizacao)
	{
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public Date getUltimaAlteracao ()
	{
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao (final Date ultimaAlteracao)
	{
		this.ultimaAlteracao = ultimaAlteracao;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}

}
