/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.adm.service;

import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorCamposDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCampo;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Diego Messias
 */
@Stateless
public class MovimentacaoPrestadorCampoService
    extends DelegateCrud<MovimentacaoPrestadorCampo, Long, MovimentacaoPrestadorCamposDao>
{

	private static final long serialVersionUID = -2981347572030658356L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorCampoService.class);

	@EJB
	private MovimentacaoPrestadorCamposDao movimentacaoPrestadorCamposDao;

	@Override
	protected MovimentacaoPrestadorCamposDao getDelegate ()
	{
		return movimentacaoPrestadorCamposDao;
	}

	/**
	 * Carrega os campos existentes nas tabelas de origem(MCC) e destino(SAM).
	 * Verifica a existencia na tabela MovimentacaoPrestadorCampo.
	 * Se existir algum novo, o adiciona na tabela de parametrizacao.
	 */
	public void verificarNovosCampos ()
	{
		// Carrega os campos da MovimentacaoPrestador e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposMP = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_MOVIMENTACAO_PRESTADOR, MovCadCooperadoConstants.TABELA_SAM_PRESTADOR);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoMP : mapCamposMP.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoMP.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoMP.getValue());
			}
		}

		// Carrega os campos MovimentacaoPrestadorEndereco e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposPE = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_PRESTADOR_ENDERECO, MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoPE : mapCamposPE.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoPE.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoPE.getValue());
			}
		}

		// Carrega os campos MovimentacaoPrestadorCurriculo e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposPC = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_PRESTADOR_CURRICULO, MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_CURRICULO);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoPC : mapCamposPC.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoPC.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoPC.getValue());
			}
		}

		// Carrega os campos MovimentacaoPrestadorDependente e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposPD = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_PRESTADOR_DEPENDENTE, MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_DEPENDENTE);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoPD : mapCamposPD.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoPD.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoPD.getValue());
			}
		}

		// Carrega os campos MovimentacaoPrestadorHorario e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposPH = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_PRESTADOR_HORARIO, MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_HORARIO);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoPH : mapCamposPH.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoPH.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoPH.getValue());
			}
		}

		// Carrega os campos MovimentacaoPrestadorFinanceiro e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposPF = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_PRESTADOR_FINANCEIRO, MovCadCooperadoConstants.TABELA_SFN_CONTAFIN);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoPF : mapCamposPF.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoPF.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoPF.getValue());
			}
		}

		// Carrega os campos MovimentacaoPrestadorEspecialidade e verifica a existencia deles na
		// MovimentacaoPrestadorCampo
		final Map<Long, MovimentacaoPrestadorCampo> mapCamposPEs = movimentacaoPrestadorCamposDao.pesquisarCamposTabela(
		    MovCadCooperadoConstants.TABELA_PRESTADOR_ESPECIALIDADE, MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ESPECIALIDADE);
		for (final Map.Entry<Long, MovimentacaoPrestadorCampo> mapCampoPEs : mapCamposPEs.entrySet())
		{
			if (movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampo(mapCampoPEs.getValue()) == null)
			{
				salvarMovimentacaoPrestadorCampo(mapCampoPEs.getValue());
			}
		}
	}

	/**
	 * Salva a {@link MovimentacaoPrestadorCampo} caso ela nao exista.
	 *
	 * @param mapCampoMP
	 */
	private void salvarMovimentacaoPrestadorCampo (final MovimentacaoPrestadorCampo movimentacaoPrestadorCampo)
	{
		try
		{
			movimentacaoPrestadorCamposDao.save(movimentacaoPrestadorCampo);
		}
		catch (final DAOException e)
		{
			LOG.error("verificarNovosCampos: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);

			LOG.error(e.getLocalizedMessage(), e);
			VExceptions.handleToRuntimeException(e);
		}
	}

	public List<MovimentacaoPrestadorCampo> pesquisarMovimentacaoPrestadorCampoPorTabela (final String nomeTabela)
	{
		return movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampoPorTabela(nomeTabela);
	}
}
