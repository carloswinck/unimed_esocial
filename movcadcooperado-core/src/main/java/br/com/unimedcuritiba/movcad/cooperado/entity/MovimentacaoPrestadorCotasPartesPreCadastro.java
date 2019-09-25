/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 11 de fev de 2017
 */
@Entity
@Table(name = "K_MCC_PRESTADOR_COT_PRE_CAD")
public class MovimentacaoPrestadorCotasPartesPreCadastro
    extends VEntity<Long>
{

	private static final long serialVersionUID = -7172392629059293232L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC015", sequenceName = "SEQ_MCC015", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC015")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "PARCELASPAGAMENTO")
	private Integer parcelasPagamento;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "PAGAMENTOAVISTA")
	private Boolean pagamentoAVista;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARAMETROSCOTASPARTES")
	private MovimentacaoPrestadorCotasPartesParametros movimentacaoPrestadorCotasPartesParametros;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVPRESTADORPRECADASTRO")
	private MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro;

	public MovimentacaoPrestadorCotasPartesPreCadastro ()
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

	public Integer getParcelasPagamento ()
	{
		return parcelasPagamento;
	}

	public void setParcelasPagamento (final Integer parcelasPagamento)
	{
		this.parcelasPagamento = parcelasPagamento;
	}

	public Boolean getPagamentoAVista ()
	{
		return pagamentoAVista;
	}

	public void setPagamentoAVista (final Boolean pagamentoAVista)
	{
		this.pagamentoAVista = pagamentoAVista;
	}

	public MovimentacaoPrestadorCotasPartesParametros getMovimentacaoPrestadorCotasPartesParametros ()
	{
		return movimentacaoPrestadorCotasPartesParametros;
	}

	public void setMovimentacaoPrestadorCotasPartesParametros (
	    final MovimentacaoPrestadorCotasPartesParametros movimentacaoPrestadorCotasPartesParametros)
	{
		this.movimentacaoPrestadorCotasPartesParametros = movimentacaoPrestadorCotasPartesParametros;
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
