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
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

/**
 * The persistent class for the V_SAM_PRESTADOR_DEPENDENTE database view.
 *
 * @author Paulo Roberto Schwertner
 * @since 03/10/2016
 */
@Entity
@Table(name = "V_SAM_PRESTADOR_DEPENDENTE")
public class PrestadorDependenteVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 2450873329382944540L;

	@Id
	@Column(name = "handle", insertable = false, updatable = false)
	private Long handle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestador", insertable = false, updatable = false)
	private PrestadorVO prestador;

	@Column(name = "cpf", insertable = false, updatable = false)
	private String cpf;

	@Column(name = "nome", length = 60, insertable = false, updatable = false)
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "datanascimento", insertable = false, updatable = false)
	private Date dataNascimento;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATAINIDEPENDENCIA", insertable = false, updatable = false)
	private Date dataInicial;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATAFINDEPENDENCIA", insertable = false, updatable = false)
	private Date dataFinal;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "DEPENDENTEIRRF", insertable = false, updatable = false)
	private Boolean irrf;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "DEPENDENTESALFAM", insertable = false, updatable = false)
	private Boolean salarioFamilia;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "PLANOPRIVADOSAUDE", insertable = false, updatable = false)
	private Boolean planoPrivadoSaude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPODEPENDENTEESOCIAL", insertable = false, updatable = false)
	private TipoDependenteVO tipoDependente;

	public PrestadorDependenteVO ()
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

	public PrestadorVO getPrestador ()
	{
		return prestador;
	}

	public void setPrestador (final PrestadorVO prestador)
	{
		this.prestador = prestador;
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

	public Boolean getIrrf ()
	{
		return irrf;
	}

	public void setIrrf (final Boolean irrf)
	{
		this.irrf = irrf;
	}

	public Boolean getSalarioFamilia ()
	{
		return salarioFamilia;
	}

	public void setSalarioFamilia (final Boolean salarioFamilia)
	{
		this.salarioFamilia = salarioFamilia;
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

	@Override
	public void setPkValue (final Long value)
	{

	}

}
