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
import br.com.unimedcuritiba.benner.dao.PrestadorDependenteDao;
import br.com.unimedcuritiba.benner.vo.PrestadorDependenteVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorDependenteDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorDependente;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Diego Messias
 * @since 20 de out de 2016
 */
@Stateless
public class MovimentacaoPrestadorDependenteService
    extends DelegateCrud<MovimentacaoPrestadorDependente, Long, MovimentacaoPrestadorDependenteDao>
{

	private static final long serialVersionUID = -5287904891664701396L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorDependenteService.class);

	@Inject
	private Messages messages;

	@EJB
	private PrestadorDependenteDao prestadorDependenteDao;

	@EJB
	private MovimentacaoPrestadorDependenteDao movimentacaoPrestadorDependenteDao;

	@Override
	protected MovimentacaoPrestadorDependenteDao getDelegate ()
	{
		return movimentacaoPrestadorDependenteDao;
	}

	/**
	 * Pesquisa a MovimentacaoPrestadorDependente na tabela Client(K_) pelo Prestador.
	 */
	public List<MovimentacaoPrestadorDependente> getMovimentacaoPrestadorDependentes (final PrestadorVO prestador)
	{
		final List<MovimentacaoPrestadorDependente> movimentacaoPrestadorDependentes = new ArrayList<MovimentacaoPrestadorDependente>();

		try
		{
			// TODO
			// movimentacaoPrestadorDependentes =
			// movimentacaoPrestadorDependenteDao.findAllByPrestador(prestador);

		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorCurriculoService.getMovimentacaoPrestadorCurriculos() - error:" + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("movimentacao.prestador.curriculo.find.erro");
		}

		return movimentacaoPrestadorDependentes;
	}

	/**
	 * Inicia a carga da lista de Dependentes do Prestador.
	 * 1 - Pesquisa na tabela view (V_MCC_SAM_PRESTADOR_DEPENDENTE).
	 * 2 - Converte o VO para MovimentacaoPrestadorDependente.
	 * 3 - Retorna a lista de MovimentacaoPrestadorDependente.
	 */
	public List<MovimentacaoPrestadorDependente> createMovimentacaoPrestadorDependente (final PrestadorVO prestador,
	    final MovimentacaoPrestador movimentacaoPrestador)
	{
		if (prestador != null)
		{

			LOG.info("initMovimentacaoPrestadorDependente -> begin: " + prestador.getCodigoPrestador());

			final List<MovimentacaoPrestadorDependente> movimentacaoPrestadorDependentes = new ArrayList<MovimentacaoPrestadorDependente>();

			final List<PrestadorDependenteVO> prestadorDependentesVO = prestadorDependenteDao.findByPrestadorFullLoad(prestador);

			if (null == prestadorDependentesVO || prestadorDependentesVO.isEmpty())
			{
				return movimentacaoPrestadorDependentes;
			}

			try
			{
				MovimentacaoPrestadorDependente movimentacaoPrestadorDependente;
				for (final PrestadorDependenteVO prestadorDependenteVO : prestadorDependentesVO)
				{
					movimentacaoPrestadorDependente = new MovimentacaoPrestadorDependente();

					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					BeanUtils.copyProperties(movimentacaoPrestadorDependente, prestadorDependenteVO);

					movimentacaoPrestadorDependente.setDependentePrestadorVO(prestadorDependenteVO);
					movimentacaoPrestadorDependente.setMovimentacaoPrestador(movimentacaoPrestador);
					if (movimentacaoPrestadorDependente.getCpf() != null)
					{
						movimentacaoPrestadorDependente.setCpf(movimentacaoPrestadorDependente.getCpf().replace("-", "").replace(".", ""));
					}

					movimentacaoPrestadorDependentes.add(movimentacaoPrestadorDependente);
				}
			}
			catch (final Exception e)
			{
				LOG.error("MovimentacaoPrestadorDependenteService.createMovimentacaoPrestadorDependente() - error BeanUtils:" +
				    VExceptions.getErrorMessage(e), e);
				messages.addErrorMessage("movimentacao.prestador.dependente.find.erro");
			}

			LOG.info("initMovimentacaoPrestadorDependente <- end: " + prestador.getCodigoPrestador());

			return movimentacaoPrestadorDependentes;
		}
		return null;
	}

	@Override
	public void delete (final MovimentacaoPrestadorDependente entity) throws DAOException
	{
		if (entity != null)
		{
			if (entity.getDependentePrestadorVO() != null)
			{
				getDelegate().excluirLogicamente(entity);
			}
		}
	}
}
