/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.view;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.web.service.MovimentacaoPrestadorEnderecoService;
import br.com.visionnaire.core.dao.Crud;
import br.com.visionnaire.core.view.AbstractCrudView;

/**
 * @author Paulo Roberto Schwertner
 * @since 30 de set de 2016
 */
@ManagedBean
@SessionScoped
public class EnderecoBean
    extends AbstractCrudView<MovimentacaoPrestadorEndereco, Long>
{

	private MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento;

	@EJB
	private MovimentacaoPrestadorEnderecoService movimentacaoPrestadorEnderecoService;
	/**
	 *
	 */
	private static final long serialVersionUID = 606602144071240717L;

	/*
	 * (non-Javadoc)
	 *
	 * @see br.com.visionnaire.core.view.AbstractListView#getCrudService()
	 */
	@Override
	protected Crud<MovimentacaoPrestadorEndereco, Long> getCrudService ()
	{
		return movimentacaoPrestadorEnderecoService;
	}

	public MovimentacaoPrestadorEndereco getMovimentacaoPrestadorEnderecoAtendimento ()
	{
		return movimentacaoPrestadorEnderecoAtendimento;
	}

	public void setMovimentacaoPrestadorEnderecoAtendimento (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento)
	{
		this.movimentacaoPrestadorEnderecoAtendimento = movimentacaoPrestadorEnderecoAtendimento;
	}

}
