/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
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
import br.com.unimedcuritiba.benner.vo.TipoDocumentoFinanceiroVO;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "K_MCC_PRESTADOR_FIN_PRE_CAD")
public class MovimentacaoPrestadorFinanceiroPreCadastro
    extends VEntity<Long>
{

	private static final long serialVersionUID = 7324848169131061341L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC012", sequenceName = "SEQ_MCC012", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC012")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "NAOCOBRATARIFA")
	private Boolean naoCobraTarifa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPODOCUMENTO")
	private TipoDocumentoFinanceiroVO tipoDocumentoFinanceiroVO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVPRESTADORPRECADASTRO")
	private MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro;

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long id)
	{
		this.id = id;
	}

	public Boolean getNaoCobraTarifa ()
	{
		return naoCobraTarifa;
	}

	public void setNaoCobraTarifa (final Boolean naoCobraTarifa)
	{
		this.naoCobraTarifa = naoCobraTarifa;
	}

	public TipoDocumentoFinanceiroVO getTipoDocumentoFinanceiroVO ()
	{
		return tipoDocumentoFinanceiroVO;
	}

	public void setTipoDocumentoFinanceiroVO (final TipoDocumentoFinanceiroVO tipoDocumentoFinanceiroVO)
	{
		this.tipoDocumentoFinanceiroVO = tipoDocumentoFinanceiroVO;
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
		// TODO Auto-generated method stub

	}
}
