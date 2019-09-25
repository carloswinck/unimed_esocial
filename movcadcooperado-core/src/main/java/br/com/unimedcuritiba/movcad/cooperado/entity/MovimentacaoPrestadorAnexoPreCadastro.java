/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "K_MCC_PRESTADOR_ANEXO_PRE_CAD")
public class MovimentacaoPrestadorAnexoPreCadastro
    extends VEntity<Long>
{

	private static final long serialVersionUID = -8965664787485434760L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC013", sequenceName = "SEQ_MCC013", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC013")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NOMEARQUIVO")
	private String nomeArquivo;

	@Column(name = "DATAUPLOAD")
	private Date dataUpload;

	@Column(name = "CODIGOTIPOANEXO")
	private Integer codigoTipoAnexo;

	@Column(name = "INFORMACAOCAMPOANEXO")
	private String informacaoCampoAnexo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIMENTACAOPRESTADORPRECAD")
	private MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro;

	public MovimentacaoPrestadorAnexoPreCadastro ()
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

	public String getNomeArquivo ()
	{
		return nomeArquivo;
	}

	public void setNomeArquivo (final String nomeArquivo)
	{
		this.nomeArquivo = nomeArquivo;
	}

	public String getInformacaoCampoAnexo ()
	{
		return informacaoCampoAnexo;
	}

	public void setInformacaoCampoAnexo (final String informacaoCampoAnexo)
	{
		this.informacaoCampoAnexo = informacaoCampoAnexo;
	}

	public Integer getCodigoTipoAnexo ()
	{
		return codigoTipoAnexo;
	}

	public void setCodigoTipoAnexo (final Integer codigoTipoAnexo)
	{
		this.codigoTipoAnexo = codigoTipoAnexo;
	}

	public Date getDataUpload ()
	{
		return dataUpload;
	}

	public void setDataUpload (final Date dataUpload)
	{
		this.dataUpload = dataUpload;
	}

	public MovimentacaoPrestadorPreCadastro getMovimentacaoPrestadorPreCadastro ()
	{
		return movimentacaoPrestadorPreCadastro;
	}

	public void setMovimentacaoPrestadorPreCadastro (final MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro)
	{
		this.movimentacaoPrestadorPreCadastro = movimentacaoPrestadorPreCadastro;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}
}
