/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.FilterDef;
import br.com.unimedcuritiba.benner.vo.PrestadorDependenteVO;
import br.com.unimedcuritiba.benner.vo.TipoDependenteVO;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;

/**
 * The persistent class for the V_SAM_PRESTADOR_DEPENDENTE database view.
 *
 * @author Paulo Roberto Schwertner
 * @since 03/10/2016
 */
@Entity
@Table(name = "K_MCC_PRESTADOR_DEPENDENTE")
@FilterDef(name = "dependente", defaultCondition = "dataExclusao IS NULL")
public class MovimentacaoPrestadorDependente
    extends VEntity<Long>
{

	private static final long serialVersionUID = 2450873329382944540L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC006", sequenceName = "SEQ_MCC006", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC006")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIMENTACAOPRESTADOR")
	private MovimentacaoPrestador movimentacaoPrestador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORDEPENDENTE")
	private PrestadorDependenteVO dependentePrestadorVO;

	@Compare(key = "movimentacaoPrestadorDependente.cpf")
	@Column(name = "CPF")
	private String cpf;

	@Compare(key = "movimentacaoPrestadorDependente.nome")
	@Column(name = "NOME", length = 60)
	private String nome;

	@Compare(key = "movimentacaoPrestadorDependente.dataNascimento")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATANASCIMENTO")
	private Date dataNascimento;

	// @Compare(key = "movimentacaoPrestadorDependente.dataInicial")
	// @Temporal(TemporalType.DATE)
	// @Column(name = "DATAINIDEPENDENCIA")
	// private Date dataInicial;

	// @Compare(key = "movimentacaoPrestadorDependente.dataFinal")
	// @Temporal(TemporalType.DATE)
	// @Column(name = "DATAFINDEPENDENCIA")
	// private Date dataFinal;

	@Compare(key = "movimentacaoPrestadorDependente.irrf")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "DEPENDENTEIRRF")
	private Boolean irrf;

	// @Compare(key = "movimentacaoPrestadorDependente.salarioFamilia")
	// @Convert(converter = BooleanToSimNaoConverter.class)
	// @Column(name = "DEPENDENTESALFAM")
	// private Boolean salarioFamilia;

	@Compare(key = "movimentacaoPrestadorDependente.planoPrivadoSaude")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "PLANOPRIVADOSAUDE")
	private Boolean planoPrivadoSaude;

	@Compare(key = "movimentacaoPrestadorDependente.tipoDependente")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPODEPENDENTEESOCIAL")
	private TipoDependenteVO tipoDependente;

	@Compare(key = "movimentacaoPrestadorDependente.dataExclusao")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAEXCLUSAO")
	private Date dataExclusao;

	@OneToMany(mappedBy = "prestadorDependente", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LogAuditoria> logsAuditoria;

	public MovimentacaoPrestadorDependente ()
	{
	}

	@Override
	public void setPkValue (final Long value)
	{
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long handle)
	{
		this.id = handle;
	}

	public MovimentacaoPrestador getMovimentacaoPrestador ()
	{
		return movimentacaoPrestador;
	}

	public void setMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		this.movimentacaoPrestador = movimentacaoPrestador;
	}

	public PrestadorDependenteVO getDependentePrestadorVO ()
	{
		return dependentePrestadorVO;
	}

	public void setDependentePrestadorVO (final PrestadorDependenteVO dependentePrestadorVO)
	{
		this.dependentePrestadorVO = dependentePrestadorVO;
	}

	public String getCpf ()
	{
		return cpf;
	}

	public void setCpf (final String cpf)
	{
		this.cpf = cpf;
	}

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public Date getDataNascimento ()
	{
		return dataNascimento;
	}

	public void setDataNascimento (final Date dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}

	public Boolean getIrrf ()
	{
		return irrf;
	}

	public void setIrrf (final Boolean irrf)
	{
		this.irrf = irrf;
	}

	public Boolean getPlanoPrivadoSaude ()
	{
		return planoPrivadoSaude;
	}

	public void setPlanoPrivadoSaude (final Boolean planoPrivadoSaude)
	{
		this.planoPrivadoSaude = planoPrivadoSaude;
	}

	public TipoDependenteVO getTipoDependente ()
	{
		return tipoDependente;
	}

	public void setTipoDependente (final TipoDependenteVO tipoDependente)
	{
		this.tipoDependente = tipoDependente;
	}

	public Date getDataExclusao ()
	{
		return dataExclusao;
	}

	public void setDataExclusao (final Date dataExclusao)
	{
		this.dataExclusao = dataExclusao;
	}

	public List<LogAuditoria> getLogsAuditoria ()
	{
		return logsAuditoria;
	}

	public void setLogsAuditoria (final List<LogAuditoria> logsAuditoria)
	{
		this.logsAuditoria = logsAuditoria;
	}

}
