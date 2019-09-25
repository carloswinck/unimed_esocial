/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.entity.LogAuditoria;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnum;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 28 de jun de 2016
 */
@Stateless
public class LogAuditoriaDao
    extends UnibenDao<LogAuditoria, Long>
{

	private static final long serialVersionUID = 225952067230808736L;

	private static final transient Logger LOG = Logger.getLogger(LogAuditoriaDao.class);

	/**
	 * Verifica a existencia da LogAuditoria conforme o PrestadorVO e o nome do campo.
	 *
	 * @param Long
	 */
	public Long verificaExistenciaLogAuditoriaIntegrada (final PrestadorVO prestadorVO, final String nomeCampo,
	    final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco)
	{
		if (prestadorVO != null && nomeCampo != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT COUNT(l) ");
				sb.append(" FROM LogAuditoria l ");
				sb.append(" WHERE l.movimentacaoPrestador.prestador = :prestadorVO ");
				sb.append(" AND l.movimentacaoPrestador.situacao = :situacao ");
				sb.append(" AND TRUNC(l.movimentacaoPrestador.dataIntegracao) = TRUNC(CURRENT_DATE) ");
				sb.append(" AND l.nomeCampo = :nomeCampo");
				sb.append(" AND l.valorNovo = :cep");

				queryParameter.and("prestadorVO", prestadorVO);
				queryParameter.and("situacao", SituacaoEnum.CONCLUIDO);
				queryParameter.and("nomeCampo", nomeCampo);
				queryParameter.and("cep", movimentacaoPrestadorEndereco.getCep());

				final TypedQuery<Long> query = getEntityManager().createQuery(sb.toString(), Long.class);

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("verificaExistenciMovimentacaoPrestador: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	public List<LogAuditoria> findLogAuditoriaByPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			final QueryParameter queryParameter = QueryParameter.create();

			sb.append("SELECT l ");
			sb.append(" FROM LogAuditoria l ");
			sb.append(" WHERE l.movimentacaoPrestador = :movimentacaoPrestador ");
			sb.append(" ORDER BY l.dataHoraAlteracao desc ");

			queryParameter.and("movimentacaoPrestador", movimentacaoPrestador);

			final TypedQuery<LogAuditoria> query = getEntityManager().createQuery(sb.toString(), LogAuditoria.class);

			setParamsOnQuery(query, queryParameter.parameters());

			return query.getResultList();
		}
		catch (final NoResultException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			LOG.error("findLogAuditoriaByPrestador: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);

			LOG.error(e.getLocalizedMessage(), e);
			VExceptions.handleToRuntimeException(e);
		}

		return null;

	}

}
