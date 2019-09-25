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
import br.com.unimedcuritiba.benner.vo.PrestadorHorarioVO;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;

/**
 * The persistent class for the K_MCC_PRESTADOR_HORARIO database table.
 *
 * @author Paulo Roberto Schwertner
 * @since 03/10/2016
 */
@Entity
@Table(name = "K_MCC_PRESTADOR_HORARIO")
@FilterDef(name = "horario", defaultCondition = " AND dataExclusao IS NULL ")
public class MovimentacaoPrestadorHorario
    extends VEntity<Long>
{

	private static final long serialVersionUID = 2450873329382944540L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC005", sequenceName = "SEQ_MCC005", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC005")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HORARIOPRESTADOR")
	private PrestadorHorarioVO prestadorHorarioVO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MCCPRESTADORENDERECO")
	private MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco;

	@Compare(key = "movimentacaoPrestadorHorario.horaInicial")
	@Temporal(TemporalType.TIME)
	@Column(name = "HORAINICIAL")
	private Date horaInicial;

	@Compare(key = "movimentacaoPrestadorHorario.horaFinal")
	@Temporal(TemporalType.TIME)
	@Column(name = "HORAFINAL")
	private Date horaFinal;

	@Compare(key = "movimentacaoPrestadorHorario.domingo")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "DOMINGO")
	private Boolean domingo;

	@Compare(key = "movimentacaoPrestadorHorario.segunda")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SEGUNDA")
	private Boolean segunda;

	@Compare(key = "movimentacaoPrestadorHorario.terca")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "TERCA")
	private Boolean terca;

	@Compare(key = "movimentacaoPrestadorHorario.quarta")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "QUARTA")
	private Boolean quarta;

	@Compare(key = "movimentacaoPrestadorHorario.quinta")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "QUINTA")
	private Boolean quinta;

	@Compare(key = "movimentacaoPrestadorHorario.sexta")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SEXTA")
	private Boolean sexta;

	@Compare(key = "movimentacaoPrestadorHorario.sabado")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SABADO")
	private Boolean sabado;

	@Compare(key = "movimentacaoPrestadorHorario.dataExclusao")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAEXCLUSAO")
	private Date dataExclusao;

	@OneToMany(mappedBy = "prestadorHorario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LogAuditoria> logsAuditoria;

	public MovimentacaoPrestadorHorario ()
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

	public void setId (final Long id)
	{
		this.id = id;
	}

	public PrestadorHorarioVO getPrestadorHorarioVO ()
	{
		return prestadorHorarioVO;
	}

	public void setPrestadorHorarioVO (final PrestadorHorarioVO prestadorHorarioVO)
	{
		this.prestadorHorarioVO = prestadorHorarioVO;
	}

	public MovimentacaoPrestadorEndereco getMovimentacaoPrestadorEndereco ()
	{
		return movimentacaoPrestadorEndereco;
	}

	public void setMovimentacaoPrestadorEndereco (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco)
	{
		this.movimentacaoPrestadorEndereco = movimentacaoPrestadorEndereco;
	}

	public Date getHoraInicial ()
	{
		return horaInicial;
	}

	public void setHoraInicial (final Date horaInicial)
	{
		this.horaInicial = horaInicial;
	}

	public Date getHoraFinal ()
	{
		return horaFinal;
	}

	public void setHoraFinal (final Date horaFinal)
	{
		this.horaFinal = horaFinal;
	}

	public Boolean getDomingo ()
	{
		return domingo;
	}

	public void setDomingo (final Boolean domingo)
	{
		this.domingo = domingo;
	}

	public Boolean getSegunda ()
	{
		return segunda;
	}

	public void setSegunda (final Boolean segunda)
	{
		this.segunda = segunda;
	}

	public Boolean getTerca ()
	{
		return terca;
	}

	public void setTerca (final Boolean terca)
	{
		this.terca = terca;
	}

	public Boolean getQuarta ()
	{
		return quarta;
	}

	public void setQuarta (final Boolean quarta)
	{
		this.quarta = quarta;
	}

	public Boolean getQuinta ()
	{
		return quinta;
	}

	public void setQuinta (final Boolean quinta)
	{
		this.quinta = quinta;
	}

	public Boolean getSexta ()
	{
		return sexta;
	}

	public void setSexta (final Boolean sexta)
	{
		this.sexta = sexta;
	}

	public Boolean getSabado ()
	{
		return sabado;
	}

	public void setSabado (final Boolean sabado)
	{
		this.sabado = sabado;
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
