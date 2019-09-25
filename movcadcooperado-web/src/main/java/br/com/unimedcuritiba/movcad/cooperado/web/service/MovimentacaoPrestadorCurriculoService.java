/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.PrestadorCurriculoDao;
import br.com.unimedcuritiba.benner.vo.PrestadorCurriculoVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorCurriculoDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCurriculo;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Eloi Bilek
 * @since 28 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorCurriculoService
    extends DelegateCrud<MovimentacaoPrestadorCurriculo, Long, MovimentacaoPrestadorCurriculoDao>
{

	private static final long serialVersionUID = -5287904891664701396L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorCurriculoService.class);

	@Inject
	private Messages messages;

	@EJB
	private PrestadorCurriculoDao prestadorCurriculoDao;

	@EJB
	private MovimentacaoPrestadorCurriculoDao movimentacaoPrestadorCurriculoDao;

	@Override
	protected MovimentacaoPrestadorCurriculoDao getDelegate ()
	{
		return movimentacaoPrestadorCurriculoDao;
	}

	/**
	 * Pesquisa a MovimentacaoPrestadorCurriculo na tabela Client(K_) pelo Prestador.
	 */
	public List<MovimentacaoPrestadorCurriculo> getMovimentacaoPrestadorCurriculos (final PrestadorVO prestador)
	{
		List<MovimentacaoPrestadorCurriculo> movimentacaoPrestadorCurriculos = new ArrayList<MovimentacaoPrestadorCurriculo>();

		try
		{
			movimentacaoPrestadorCurriculos = movimentacaoPrestadorCurriculoDao.findAllByPrestador(prestador);
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorCurriculoService.getMovimentacaoPrestadorCurriculos() - error:" + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("movimentacao.prestador.curriculo.find.erro");
		}

		return movimentacaoPrestadorCurriculos;
	}

	/**
	 * Inicia a carga da lista de Curriculos do Prestador.
	 * 1 - Pesquisa na tabela view (V_MCC_SAM_PRESTADOR_CURRICULO).
	 * 2 - Converte o VO para MovimentacaoPrestadorCurriculo.
	 * 3 - Retorna a lista de MovimentacaoPrestadorCurriculo.
	 */
	public List<MovimentacaoPrestadorCurriculo> createMovimentacaoPrestadorCurriculo (final PrestadorVO prestador,
	    final MovimentacaoPrestador movimentacaoPrestador)
	{
		if (prestador != null)
		{

			LOG.info("initMovimentacaoPrestadorCurriculo -> begin: " + prestador.getCodigoPrestador());

			final List<MovimentacaoPrestadorCurriculo> movimentacaoPrestadorCurriculos = new ArrayList<MovimentacaoPrestadorCurriculo>();

			final List<PrestadorCurriculoVO> prestadorCurriculosVO = prestadorCurriculoDao.findByPrestadorFullLoad(prestador.getCodigoPrestador());

			if (null == prestadorCurriculosVO || prestadorCurriculosVO.isEmpty())
			{
				return movimentacaoPrestadorCurriculos;
			}

			try
			{
				MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo;
				for (final PrestadorCurriculoVO prestadorCurriculoVO : prestadorCurriculosVO)
				{
					movimentacaoPrestadorCurriculo = new MovimentacaoPrestadorCurriculo();

					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					BeanUtils.copyProperties(movimentacaoPrestadorCurriculo, prestadorCurriculoVO);

					movimentacaoPrestadorCurriculo.setCurriculoPrestadorVO(prestadorCurriculoVO);
					movimentacaoPrestadorCurriculo.setMovimentacaoPrestador(movimentacaoPrestador);
					movimentacaoPrestadorCurriculos.add(movimentacaoPrestadorCurriculo);
				}
			}
			catch (final Exception e)
			{
				LOG.error(
				    "MovimentacaoPrestadorCurriculoService.initMovimentacaoPrestadorCurriculo() - error BeanUtils:" + VExceptions.getErrorMessage(e),
				    e);
				messages.addErrorMessage("movimentacao.prestador.curriculo.find.erro");
			}

			LOG.info("initMovimentacaoPrestadorCurriculo <- end: " + prestador.getCodigoPrestador());

			return movimentacaoPrestadorCurriculos;
		}
		return null;
	}
}
