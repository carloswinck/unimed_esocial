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
import br.com.unimedcuritiba.benner.dao.LogradouroDao;
import br.com.unimedcuritiba.benner.dao.PrestadorEnderecoDao;
import br.com.unimedcuritiba.benner.dao.TipoLogradouroDao;
import br.com.unimedcuritiba.benner.vo.PrestadorEnderecoVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorEnderecoDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.enums.SimNaoEnum;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Eloi Bilek
 * @since 28 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorEnderecoService
    extends DelegateCrud<MovimentacaoPrestadorEndereco, Long, MovimentacaoPrestadorEnderecoDao>
{

	private static final long serialVersionUID = -8243184550810152836L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorEnderecoService.class);

	@Inject
	private Messages messages;

	@EJB
	private LogradouroDao logradouroDao;

	@EJB
	private TipoLogradouroDao tipoLogradouroDao;

	@EJB
	private PrestadorEnderecoDao prestadorEnderecoDao;

	@EJB
	private MovimentacaoPrestadorEnderecoDao movimentacaoPrestadorEnderecoDao;

	@Override
	protected MovimentacaoPrestadorEnderecoDao getDelegate ()
	{
		return movimentacaoPrestadorEnderecoDao;
	}

	/**
	 * Pesquisa a MovimentacaoPrestadorEndereco na tabela Client(K_) pelo Prestador.
	 */
	public MovimentacaoPrestadorEndereco getMovimentacaoPrestadorEndereco (final PrestadorVO prestador)
	{
		MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco = new MovimentacaoPrestadorEndereco();

		try
		{
			movimentacaoPrestadorEndereco = movimentacaoPrestadorEnderecoDao.findEnderecoResidencial(prestador);
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorCurriculoService.getMovimentacaoPrestadorEndereco() - error:" + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("movimentacao.prestador.endereco.find.erro");
		}

		return movimentacaoPrestadorEndereco;
	}

	/**
	 * Inicia a carga do Endereco Residencial do Prestador.
	 * 1 - Pesquisa na tabela view (V_MCC_SAM_PRESTADOR_ENDERECO).
	 * 2 - Converte o VO para MovimentacaoPrestadorEndereco.
	 * 3 - Retorna a MovimentacaoPrestadorEndereco.
	 */
	public MovimentacaoPrestadorEndereco createEnderecoResidencial (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			LOG.info("createEnderecoResidencial -> begin: " + prestador.getCodigoPrestador());

			final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco = new MovimentacaoPrestadorEndereco();

			final PrestadorEnderecoVO prestadorEnderecoVO = prestadorEnderecoDao.findResidencialByPrestadorFetch(prestador);

			if (null == prestadorEnderecoVO)
			{
				return movimentacaoPrestadorEndereco;
			}

			copyEndereco(movimentacaoPrestadorEndereco, prestadorEnderecoVO);

			LOG.info("createEnderecoResidencial <- end: " + prestador.getCodigoPrestador());

			return movimentacaoPrestadorEndereco;

		}
		return null;
	}

	/**
	 * Inicia a carga do Endereco de Atendimento do Prestador.
	 * 1 - Pesquisa na tabela view (V_MCC_SAM_PRESTADOR_ENDERECO).
	 * 2 - Converte o VO para MovimentacaoPrestadorEndereco.
	 * 3 - Retorna a MovimentacaoPrestadorEndereco.
	 *
	 * @param movPrestador
	 */
	public List<MovimentacaoPrestadorEndereco> createDadosAtendimento (final PrestadorVO prestador, final MovimentacaoPrestador movPrestador)
	{
		if (prestador != null)
		{
			LOG.info("createDadosAtendimento -> begin: " + prestador.getCodigoPrestador());

			final List<MovimentacaoPrestadorEndereco> dadosAtendimentoList = new ArrayList<MovimentacaoPrestadorEndereco>();

			final List<PrestadorEnderecoVO> endPrestAtendimentoList = prestadorEnderecoDao.findAtendimentoByPrestadorFetch(prestador);

			if (Validator.isNotEmpty(endPrestAtendimentoList))
			{
				for (final PrestadorEnderecoVO prestadorEnderecoVO : endPrestAtendimentoList)
				{
					final MovimentacaoPrestadorEndereco movPrestEnd = new MovimentacaoPrestadorEndereco();

					copyEndereco(movPrestEnd, prestadorEnderecoVO);

					movPrestEnd.setMovimentacaoPrestador(movPrestador);
					movPrestEnd.setTipoAtendimento(SimNaoEnum.SIM.getSigla());

					dadosAtendimentoList.add(movPrestEnd);
				}
			}

			LOG.info("createDadosAtendimento <- end: " + prestador.getCodigoPrestador());

			return dadosAtendimentoList;

		}
		return null;
	}

	/**
	 * @param movimentacaoPrestadorEndereco
	 * @param prestadorEnderecoVO
	 */
	private void copyEndereco (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco, final PrestadorEnderecoVO prestadorEnderecoVO)
	{
		if (movimentacaoPrestadorEndereco != null && prestadorEnderecoVO != null)
		{
			try
			{
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				BeanUtils.copyProperties(movimentacaoPrestadorEndereco, prestadorEnderecoVO);
				movimentacaoPrestadorEndereco.setPrestadorEnderecoVO(prestadorEnderecoVO);

				// Regra da Unimed Curitiba, se o Endereco for somente Pessoal, limpar o campo CNES.
				if (movimentacaoPrestadorEndereco.getPessoal().equals(SimNaoEnum.SIM.getSigla()) &&
				    movimentacaoPrestadorEndereco.getAtendimento().equals(SimNaoEnum.NAO.getSigla()))
				{
					movimentacaoPrestadorEndereco.setCnes(null);
				}
			}
			catch (final Exception e)
			{
				LOG.error("createEnderecoResidencial - error BeanUtils:" + VExceptions.getErrorMessage(e), e);
				messages.addErrorMessage("movimentacao.prestador.endereco.find.erro");
			}
		}
	}

	@Override
	public void delete (final MovimentacaoPrestadorEndereco entity) throws DAOException
	{
		if (entity != null)
		{
			if (entity.getPrestadorEnderecoVO() != null)
			{
				getDelegate().excluirLogicamente(entity);
			}
		}
	}
}
