/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.ContaFinDao;
import br.com.unimedcuritiba.benner.vo.ContaFinVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorFinanceiroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorFinanceiro;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Eloi Bilek
 * @since 26 de dez de 2016
 */
@Stateless
public class MovimentacaoPrestadorFinanceiroService
    extends DelegateCrud<MovimentacaoPrestadorFinanceiro, Long, MovimentacaoPrestadorFinanceiroDao>
{

	private static final long serialVersionUID = 1826524071480345518L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorFinanceiroService.class);

	@Inject
	private Messages messages;

	@EJB
	private ContaFinDao contaFinDao;

	@EJB
	private MovimentacaoPrestadorFinanceiroDao movimentacaoPrestadorFinanceiroDao;

	@Override
	protected MovimentacaoPrestadorFinanceiroDao getDelegate ()
	{
		return movimentacaoPrestadorFinanceiroDao;
	}

	/**
	 * Inicia a carga dos Dados Financeiros do Prestador.
	 * 1 - Pesquisa na tabela view (V_SFN_CONTAFIN).
	 * 2 - Converte o VO para MovimentacaoPrestadorFinanceiro.
	 * 3 - Retorna a MovimentacaoPrestadorFinanceiro.
	 */
	public MovimentacaoPrestadorFinanceiro createDadosFinanceiros (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			LOG.info("createDadosFinanceiros -> begin: " + prestador.getCodigoPrestador());

			final MovimentacaoPrestadorFinanceiro movimentacaoPrestadorFinanceiro = new MovimentacaoPrestadorFinanceiro();

			final ContaFinVO contaFinVO = contaFinDao.findContaFinFetch(prestador);

			if (null == contaFinVO)
			{
				return movimentacaoPrestadorFinanceiro;
			}

			copyDadosFinanceiros(movimentacaoPrestadorFinanceiro, contaFinVO);

			LOG.info("createDadosFinanceiros <- end: " + prestador.getCodigoPrestador());

			return movimentacaoPrestadorFinanceiro;

		}
		return null;
	}

	/**
	 * @param movimentacaoPrestadorFinanceiro
	 * @param contaFinVO
	 */
	private void copyDadosFinanceiros (final MovimentacaoPrestadorFinanceiro movimentacaoPrestadorFinanceiro, final ContaFinVO contaFinVO)
	{
		if (movimentacaoPrestadorFinanceiro != null && contaFinVO != null)
		{
			try
			{
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				BeanUtils.copyProperties(movimentacaoPrestadorFinanceiro, contaFinVO);
				movimentacaoPrestadorFinanceiro.setContaFin(contaFinVO);
			}
			catch (final Exception e)
			{
				LOG.error("createDadosFinanceiros - error BeanUtils:" + VExceptions.getErrorMessage(e), e);
				messages.addErrorMessage("movimentacao.prestador.financeiro.find.erro");
			}
		}
	}

}
