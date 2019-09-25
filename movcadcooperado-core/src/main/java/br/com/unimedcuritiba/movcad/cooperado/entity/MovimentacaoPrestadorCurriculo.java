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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import br.com.unimedcuritiba.benner.vo.AreaCursoVO;
import br.com.unimedcuritiba.benner.vo.EntidadeEnsinoVO;
import br.com.unimedcuritiba.benner.vo.PrestadorCurriculoVO;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoCursoEnum;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;
import br.com.visionnaire.core.util.beancomparator.CompareJoin;

/**
 * The persistent class for the K_MCC_PRESTADOR_CURRICULO database table.
 *
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */
@Entity
@Table(name = "K_MCC_PRESTADOR_CURRICULO")
public class MovimentacaoPrestadorCurriculo
    extends VEntity<Long>
{

	private static final long serialVersionUID = 2450873329382944540L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC004", sequenceName = "SEQ_MCC004", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC004")
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long Id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movimentacaoprestador")
	private MovimentacaoPrestador movimentacaoPrestador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curriculoprestador")
	private PrestadorCurriculoVO curriculoPrestadorVO;

	@Compare(key = "movimentacaoPrestadorCurriculo.tipoCursoEnum")
	@Column(name = "tipocurso")
	@Enumerated(EnumType.ORDINAL)
	private TipoCursoEnum tipoCursoEnum;

	@Compare(key = "movimentacaoPrestadorCurriculo.dataInicial")
	@Temporal(TemporalType.DATE)
	@Column(name = "datainicial")
	private Date dataInicial;

	@Compare(key = "movimentacaoPrestadorCurriculo.dataConclusao")
	@Temporal(TemporalType.DATE)
	@Column(name = "dataconclusao")
	private Date dataConclusao;

	@CompareJoin(key = "movimentacaoPrestadorCurriculo.areaCurso", field = "descricao")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "areacurso")
	private AreaCursoVO areaCurso;

	@Compare(key = "movimentacaoPrestadorCurriculo.curso")
	@Column(name = "curso")
	private String curso;

	@Compare(key = "movimentacaoPrestadorCurriculo.entidade")
	@Column(name = "entidade")
	private String entidade;

	@CompareJoin(key = "movimentacaoPrestadorCurriculo.entidadeEnsino", field = "descricao")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entidadeensino")
	private EntidadeEnsinoVO entidadeEnsino;

	@OneToMany(mappedBy = "prestadorCurriculo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LogAuditoria> logsAuditoria;

	public MovimentacaoPrestadorCurriculo ()
	{
		// TODO Auto-generated constructor stub
	}

	public Long getId ()
	{
		return Id;
	}

	public void setId (final Long id)
	{
		Id = id;
	}

	public MovimentacaoPrestador getMovimentacaoPrestador ()
	{
		return movimentacaoPrestador;
	}

	public void setMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		this.movimentacaoPrestador = movimentacaoPrestador;
	}

	public PrestadorCurriculoVO getCurriculoPrestadorVO ()
	{
		return curriculoPrestadorVO;
	}

	public void setCurriculoPrestadorVO (final PrestadorCurriculoVO curriculoPrestadorVO)
	{
		this.curriculoPrestadorVO = curriculoPrestadorVO;
	}

	public TipoCursoEnum getTipoCursoEnum ()
	{
		return tipoCursoEnum;
	}

	public void setTipoCursoEnum (final TipoCursoEnum tipoCursoEnum)
	{
		this.tipoCursoEnum = tipoCursoEnum;
	}

	public Date getDataInicial ()
	{
		return dataInicial;
	}

	public void setDataInicial (final Date dataInicial)
	{
		this.dataInicial = dataInicial;
	}

	public Date getDataConclusao ()
	{
		return dataConclusao;
	}

	public void setDataConclusao (final Date dataConclusao)
	{
		this.dataConclusao = dataConclusao;
	}

	public AreaCursoVO getAreaCurso ()
	{
		return areaCurso;
	}

	public void setAreaCurso (final AreaCursoVO areaCurso)
	{
		this.areaCurso = areaCurso;
	}

	public String getCurso ()
	{
		return curso;
	}

	public void setCurso (final String curso)
	{
		this.curso = curso;
	}

	public String getEntidade ()
	{
		return entidade;
	}

	public void setEntidade (final String entidade)
	{
		this.entidade = entidade;
	}

	public EntidadeEnsinoVO getEntidadeEnsino ()
	{
		return entidadeEnsino;
	}

	public void setEntidadeEnsino (final EntidadeEnsinoVO entidadeEnsino)
	{
		this.entidadeEnsino = entidadeEnsino;
	}

	public List<LogAuditoria> getLogsAuditoria ()
	{
		return logsAuditoria;
	}

	public void setLogsAuditoria (final List<LogAuditoria> logsAuditoria)
	{
		this.logsAuditoria = logsAuditoria;
	}

	@Override
	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
