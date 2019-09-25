/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * Classe que representa a Tabela de parametrizacao das Cotas Partes.
 * -
 * - Possui valor unico, nao tem sequence.
 * - Eh alterada via Benner Saude.
 * - Aba ADM, pasta Parametros Gerais.
 * -
 *
 * @author Eloi Bilek
 * @since 11 de fev de 2017
 */
@Entity
@Table(name = "K_MCC_COTAS_PARTES_PARAMETROS")
public class MovimentacaoPrestadorCotasPartesParametros
    extends VEntity<Long>
{

	private static final long serialVersionUID = 5899731540510008032L;

	@Id
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "VALORCOTASPARTES", insertable = false, updatable = false)
	private Double valorCotasPartes;

	@Column(name = "PARCELASCOTASPARTES", insertable = false, updatable = false)
	private Long parcelasCotasPartes;

	public MovimentacaoPrestadorCotasPartesParametros ()
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

	public Double getValorCotasPartes ()
	{
		return valorCotasPartes;
	}

	public void setValorCotasPartes (final Double valorCotasPartes)
	{
		this.valorCotasPartes = valorCotasPartes;
	}

	public Long getParcelasCotasPartes ()
	{
		return parcelasCotasPartes;
	}

	public void setParcelasCotasPartes (final Long parcelasCotasPartes)
	{
		this.parcelasCotasPartes = parcelasCotasPartes;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}

}
