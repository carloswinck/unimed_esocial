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
import org.hibernate.annotations.FilterDef;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "K_MCC_PRESTADOR_ANEXO")
@FilterDef(name = "anexo", defaultCondition = "AND dataUpload IS NOT NULL AND nomeArquivo IS NOT NULL")
public class MovimentacaoPrestadorAnexo
    extends VEntity<Long>
{

	private static final long serialVersionUID = -8965664787485434760L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC008", sequenceName = "SEQ_MCC008", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC008")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NOMEARQUIVO")
	private String nomeArquivo;

	@Column(name = "DATAUPLOAD")
	private Date dataUpload;

	@Column(name = "CODIGOLOGAUDITORIA")
	private Long codigoLogAuditoria;

	@Column(name = "TABELACODIGO")
	private String tabelaCodigo;

	@Column(name = "INFORMACAOCAMPOANEXO")
	private String informacaoCampoAnexo;

	@JoinColumn(name = "MOVIMENTACAOPRESTADOR")
	@ManyToOne(fetch = FetchType.LAZY)
	private MovimentacaoPrestador movimentacaoPrestador;

	@JoinColumn(name = "MOVIMENTACAOPRESTADORCAMPO")
	@ManyToOne(fetch = FetchType.LAZY)
	private MovimentacaoPrestadorCampo movimentacaoPrestadorCampo;

	public MovimentacaoPrestadorAnexo ()
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

	public Date getDataUpload ()
	{
		return dataUpload;
	}

	public void setDataUpload (final Date dataUpload)
	{
		this.dataUpload = dataUpload;
	}

	public Long getCodigoLogAuditoria ()
	{
		return codigoLogAuditoria;
	}

	public void setCodigoLogAuditoria (final Long codigoLogAuditoria)
	{
		this.codigoLogAuditoria = codigoLogAuditoria;
	}

	public String getTabelaCodigo ()
	{
		return tabelaCodigo;
	}

	public void setTabelaCodigo (final String tabelaCodigo)
	{
		this.tabelaCodigo = tabelaCodigo;
	}

	public String getInformacaoCampoAnexo ()
	{
		return informacaoCampoAnexo;
	}

	public void setInformacaoCampoAnexo (final String informacaoCampoAnexo)
	{
		this.informacaoCampoAnexo = informacaoCampoAnexo;
	}

	public MovimentacaoPrestador getMovimentacaoPrestador ()
	{
		return movimentacaoPrestador;
	}

	public void setMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		this.movimentacaoPrestador = movimentacaoPrestador;
	}

	public MovimentacaoPrestadorCampo getMovimentacaoPrestadorCampo ()
	{
		return movimentacaoPrestadorCampo;
	}

	public void setMovimentacaoPrestadorCampo (final MovimentacaoPrestadorCampo movimentacaoPrestadorCampo)
	{
		this.movimentacaoPrestadorCampo = movimentacaoPrestadorCampo;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}
}
