/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorAnexoDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorAnexo;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorAnexoService
    extends DelegateCrud<MovimentacaoPrestadorAnexo, Long, MovimentacaoPrestadorAnexoDao>
{

	private static final long serialVersionUID = 6137384351969438023L;

	@EJB
	private MovimentacaoPrestadorAnexoDao movimentacaoPrestadorAnexoDao;

	@Override
	protected MovimentacaoPrestadorAnexoDao getDelegate ()
	{
		return movimentacaoPrestadorAnexoDao;
	}

	/**
	 * Pesquisa a lista de MovimentacaoPrestadorAnexo.
	 *
	 * @param movimentacaoPrestador
	 * @return
	 */
	public List<MovimentacaoPrestadorAnexo> carregarListaMovimentacaoPrestadorAnexo (final MovimentacaoPrestador movimentacaoPrestador)
	{
		return movimentacaoPrestadorAnexoDao.pesquisarMovimentacaoPrestadorAnexo(movimentacaoPrestador);
	}

	/**
	 * Pesquisa a lista de MovimentacaoPrestadorAnexo com o Upload realizado.
	 *
	 * @param movimentacaoPrestador
	 * @return
	 */
	public List<MovimentacaoPrestadorAnexo> carregarListaMovimentacaoPrestadorAnexoUpload (final MovimentacaoPrestador movimentacaoPrestador)
	{
		return movimentacaoPrestadorAnexoDao.pesquisarMovimentacaoPrestadorAnexosUpload(movimentacaoPrestador);
	}

}
