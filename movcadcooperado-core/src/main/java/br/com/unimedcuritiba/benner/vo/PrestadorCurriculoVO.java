/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoCursoEnum;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Entity
@Table(name = "V_MCC_SAM_PRESTADOR_CURRICULO")
public class PrestadorCurriculoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -3181900169226531573L;

	@Id
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "publicarnolivro", length = 1, insertable = false, updatable = false)
	private String publicarNoLivro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidadeensino", insertable = false, updatable = false)
	private EntidadeEnsinoVO entidadeEnsino;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areacurso", insertable = false, updatable = false)
	private AreaCursoVO areaCurso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestador", insertable = false, updatable = false)
	private PrestadorVO prestadorVO;

	@Column(name = "curso", insertable = false, updatable = false)
	private String curso;

	@Column(name = "entidade", insertable = false, updatable = false)
	private String entidade;

	@Temporal(TemporalType.DATE)
	@Column(name = "datainicial", insertable = false, updatable = false)
	private Date dataInicial;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataconclusao", insertable = false, updatable = false)
	private Date dataConclusao;

	@Column(name = "tipocurso", insertable = false, updatable = false)
	@Enumerated(EnumType.ORDINAL)
	private TipoCursoEnum tipoCursoEnum;

	@Column(name = "cargahoraria", insertable = false, updatable = false)
	private Integer cargaHoraria;

	@Column(name = "observacao", insertable = false, updatable = false)
	private String observacao;

	public PrestadorCurriculoVO ()
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

	public String getPublicarNoLivro ()
	{
		return publicarNoLivro;
	}

	public void setPublicarNoLivro (final String publicarNoLivro)
	{
		this.publicarNoLivro = publicarNoLivro;
	}

	public EntidadeEnsinoVO getEntidadeEnsino ()
	{
		return entidadeEnsino;
	}

	public void setEntidadeEnsino (final EntidadeEnsinoVO entidadeEnsino)
	{
		this.entidadeEnsino = entidadeEnsino;
	}

	public AreaCursoVO getAreaCurso ()
	{
		return areaCurso;
	}

	public void setAreaCurso (final AreaCursoVO areaCurso)
	{
		this.areaCurso = areaCurso;
	}

	public PrestadorVO getPrestadorVO ()
	{
		return prestadorVO;
	}

	public void setPrestadorVO (final PrestadorVO prestadorVO)
	{
		this.prestadorVO = prestadorVO;
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

	public Integer getCargaHoraria ()
	{
		return cargaHoraria;
	}

	public void setCargaHoraria (final Integer cargaHoraria)
	{
		this.cargaHoraria = cargaHoraria;
	}

	public String getObservacao ()
	{
		return observacao;
	}

	public void setObservacao (final String observacao)
	{
		this.observacao = observacao;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	public TipoCursoEnum getTipoCursoEnum ()
	{
		return tipoCursoEnum;
	}

	public void setTipoCursoEnum (final TipoCursoEnum tipoCursoEnum)
	{
		this.tipoCursoEnum = tipoCursoEnum;
	}

}
