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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.com.unimedcuritiba.movcad.cooperado.enums.LogAuditoriaSituacaoEnum;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;
import br.com.visionnaire.core.util.beancomparator.DiffTypeOperationEnum;

/**
 * The persistent class for the K_MCC_LOG_AUDITORIA database table.
 *
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */
@Entity
@Table(name = "K_MCC_LOG_AUDITORIA")
public class LogAuditoria
    extends VEntity<Long>
{

	private static final long serialVersionUID = -3073704904827277086L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC002", sequenceName = "SEQ_MCC002", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC002")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long handle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIMENTACAOPRESTADOR")
	private MovimentacaoPrestador movimentacaoPrestador;

	@Column(name = "CAMPOALTERADO")
	private String campoAlterado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAHORAALTERACAO")
	private Date dataHoraAlteracao;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TIPOOPERACAO")
	private DiffTypeOperationEnum tipoOperacao;

	@Column(name = "VALORNOVO")
	private String valorNovo;

	@Column(name = "VALORORIGINAL")
	private String valorOriginal;

	@Column(name = "NOMETABELA")
	private String nomeTabela;

	@Column(name = "NOMECAMPO")
	private String nomeCampo;

	@Column(name = "VALORDESCNOVO")
	private String valorDescNovo;

	@Column(name = "VALORDESCORIGINAL")
	private String valorDescOriginal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORENDERECO")
	private MovimentacaoPrestadorEndereco prestadorEndereco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORCURRICULO")
	private MovimentacaoPrestadorCurriculo prestadorCurriculo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORDEPENDENTE")
	private MovimentacaoPrestadorDependente prestadorDependente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORHORARIO")
	private MovimentacaoPrestadorHorario prestadorHorario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORFINANCEIRO")
	private MovimentacaoPrestadorFinanceiro prestadorFinanceiro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORESPECIALIDADE")
	private MovimentacaoPrestadorEspecialidade prestadorEspecialidade;

	@Compare(key = "logAuditoria.situacao", ignoreNull = true)
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "situacao")
	private LogAuditoriaSituacaoEnum situacao;

	public LogAuditoria ()
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

	public MovimentacaoPrestador getMovimentacaoPrestador ()
	{
		return movimentacaoPrestador;
	}

	public void setMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		this.movimentacaoPrestador = movimentacaoPrestador;
	}

	public String getCampoAlterado ()
	{
		return campoAlterado;
	}

	public void setCampoAlterado (final String campoAlterado)
	{
		this.campoAlterado = campoAlterado;
	}

	public Date getDataHoraAlteracao ()
	{
		return dataHoraAlteracao;
	}

	public void setDataHoraAlteracao (final Date dataHoraAlteracao)
	{
		this.dataHoraAlteracao = dataHoraAlteracao;
	}

	public String getValorNovo ()
	{
		return valorNovo;
	}

	public void setValorNovo (final String valorNovo)
	{
		this.valorNovo = valorNovo;
	}

	public String getValorOriginal ()
	{
		return valorOriginal;
	}

	public void setValorOriginal (final String valorOriginal)
	{
		this.valorOriginal = valorOriginal;
	}

	public DiffTypeOperationEnum getTipoOperacao ()
	{
		return tipoOperacao;
	}

	public void setTipoOperacao (final DiffTypeOperationEnum tipoOperacao)
	{
		this.tipoOperacao = tipoOperacao;
	}

	public String getNomeTabela ()
	{
		return nomeTabela;
	}

	public void setNomeTabela (final String nomeTabela)
	{
		this.nomeTabela = nomeTabela;
	}

	public String getNomeCampo ()
	{
		return nomeCampo;
	}

	public void setNomeCampo (final String nomeCampo)
	{
		this.nomeCampo = nomeCampo;
	}

	public String getValorDescNovo ()
	{
		return valorDescNovo;
	}

	public void setValorDescNovo (final String valorDescNovo)
	{
		this.valorDescNovo = valorDescNovo;
	}

	public String getValorDescOriginal ()
	{
		return valorDescOriginal;
	}

	public void setValorDescOriginal (final String valorDescOriginal)
	{
		this.valorDescOriginal = valorDescOriginal;
	}

	public MovimentacaoPrestadorEndereco getPrestadorEndereco ()
	{
		return prestadorEndereco;
	}

	public void setPrestadorEndereco (final MovimentacaoPrestadorEndereco prestadorEndereco)
	{
		this.prestadorEndereco = prestadorEndereco;
	}

	public MovimentacaoPrestadorCurriculo getPrestadorCurriculo ()
	{
		return prestadorCurriculo;
	}

	public void setPrestadorCurriculo (final MovimentacaoPrestadorCurriculo prestadorCurriculo)
	{
		this.prestadorCurriculo = prestadorCurriculo;
	}

	public MovimentacaoPrestadorDependente getPrestadorDependente ()
	{
		return prestadorDependente;
	}

	public void setPrestadorDependente (final MovimentacaoPrestadorDependente prestadorDependente)
	{
		this.prestadorDependente = prestadorDependente;
	}

	public MovimentacaoPrestadorHorario getPrestadorHorario ()
	{
		return prestadorHorario;
	}

	public void setPrestadorHorario (final MovimentacaoPrestadorHorario prestadorHorario)
	{
		this.prestadorHorario = prestadorHorario;
	}

	public MovimentacaoPrestadorFinanceiro getPrestadorFinanceiro ()
	{
		return prestadorFinanceiro;
	}

	public void setPrestadorFinanceiro (final MovimentacaoPrestadorFinanceiro prestadorFinanceiro)
	{
		this.prestadorFinanceiro = prestadorFinanceiro;
	}

	public MovimentacaoPrestadorEspecialidade getPrestadorEspecialidade ()
	{
		return prestadorEspecialidade;
	}

	public void setPrestadorEspecialidade (final MovimentacaoPrestadorEspecialidade prestadorEspecialidade)
	{
		this.prestadorEspecialidade = prestadorEspecialidade;
	}

	public LogAuditoriaSituacaoEnum getSituacao ()
	{
		return situacao;
	}

	public void setSituacao (final LogAuditoriaSituacaoEnum situacao)
	{
		this.situacao = situacao;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub
	}
}
