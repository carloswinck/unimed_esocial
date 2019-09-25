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
import br.com.unimedcuritiba.benner.dao.PrestadorEspecialidadeDao;
import br.com.unimedcuritiba.benner.vo.PrestadorEspecialidadeVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorEspecialidadeDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEspecialidade;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

@Stateless
public class MovimentacaoPrestadorEspecialidadeService
    extends DelegateCrud<MovimentacaoPrestadorEspecialidade, Long, MovimentacaoPrestadorEspecialidadeDao>
{

	private static final long serialVersionUID = 99491651665753527L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorEspecialidadeService.class);

	@Inject
	private Messages messages;

	@EJB
	private PrestadorEspecialidadeDao prestadorEspecialidadeDao;

	@EJB
	private MovimentacaoPrestadorEspecialidadeDao movimentacaoPrestadorEspecialidadeDao;

	@Override
	protected MovimentacaoPrestadorEspecialidadeDao getDelegate ()
	{
		return movimentacaoPrestadorEspecialidadeDao;
	}

	/**
	 * Inicia a carga da Especialidade Principal do Prestador.
	 * 1 - Pesquisa na tabela view (V_SAM_PRESTADOR_ESPECIALIDADE).
	 * 2 - Converte o VO para MovimentacaoPrestadorEspecialidade.
	 * 3 - Retorna a MovimentacaoPrestadorEspecialidade.
	 */
	public MovimentacaoPrestadorEspecialidade createEspecialidade (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			LOG.info("createEspecialidade -> begin: " + prestador.getCodigoPrestador());

			final MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade = new MovimentacaoPrestadorEspecialidade();

			final PrestadorEspecialidadeVO prestadorEspecialidadeVO = prestadorEspecialidadeDao.findEspecialidadeByPrestadorFetch(prestador);

			if (null == prestadorEspecialidadeVO)
			{
				return movimentacaoPrestadorEspecialidade;
			}

			copyDadosEspecialidade(movimentacaoPrestadorEspecialidade, prestadorEspecialidadeVO);

			LOG.info("createEspecialidade <- end: " + prestador.getCodigoPrestador());

			return movimentacaoPrestadorEspecialidade;

		}
		return null;
	}

	/**
	 * @param movimentacaoPrestadorEspecialidade
	 * @param contaFinVO
	 */
	private void copyDadosEspecialidade (final MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade,
	    final PrestadorEspecialidadeVO prestadorEspecialidadeVO)
	{
		if (movimentacaoPrestadorEspecialidade != null && prestadorEspecialidadeVO != null)
		{
			try
			{
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				BeanUtils.copyProperties(movimentacaoPrestadorEspecialidade, prestadorEspecialidadeVO);
				movimentacaoPrestadorEspecialidade.setPrestadorEspecialidade(prestadorEspecialidadeVO);
				movimentacaoPrestadorEspecialidade.setEspecialidade(prestadorEspecialidadeVO.getEspecialidade());
			}
			catch (final Exception e)
			{
				LOG.error("createDadosFinanceiros - error BeanUtils:" + VExceptions.getErrorMessage(e), e);
				messages.addErrorMessage("movimentacao.prestador.financeiro.find.erro");
			}
		}
	}

}
