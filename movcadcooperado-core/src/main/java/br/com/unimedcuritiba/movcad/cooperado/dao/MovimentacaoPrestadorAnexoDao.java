package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorAnexo;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

@Stateless
public class MovimentacaoPrestadorAnexoDao
    extends UnibenDao<MovimentacaoPrestadorAnexo, Long>
{

	private static final long serialVersionUID = 7086839750889090574L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorAnexoDao.class);

	/**
	 * Pesquisa MovimentacaoPrestadorAnexo por MovimentacaoPrestador.
	 *
	 * @param movimentacaoPrestador
	 * @return list
	 */
	public List<MovimentacaoPrestadorAnexo> pesquisarMovimentacaoPrestadorAnexo (final MovimentacaoPrestador movimentacaoPrestador)
	{
		if (movimentacaoPrestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorAnexo p ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestador mvPrestador ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorCampo mvPrestadorCampo ");
				sb.append(" WHERE mvPrestador = :movimentacaoPrestador ");

				queryParameter.and("movimentacaoPrestador", movimentacaoPrestador);

				final TypedQuery<MovimentacaoPrestadorAnexo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorAnexo: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}

		}
		return null;
	}

	/**
	 * Pesquisa MovimentacaoPrestadorAnexo por MovimentacaoPrestador.
	 *
	 * @param movimentacaoPrestador
	 * @return list
	 */
	public List<MovimentacaoPrestadorAnexo> pesquisarMovimentacaoPrestadorAnexosUpload (final MovimentacaoPrestador movimentacaoPrestador)
	{
		if (movimentacaoPrestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorAnexo p ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestador mvPrestador ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorCampo mvPrestadorCampo ");
				sb.append(" WHERE mvPrestador = :movimentacaoPrestador ");
				sb.append(" AND p.nomeArquivo IS NOT NULL ");
				sb.append(" AND p.dataUpload IS NOT NULL ");

				queryParameter.and("movimentacaoPrestador", movimentacaoPrestador);

				final TypedQuery<MovimentacaoPrestadorAnexo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorAnexo: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}

		}
		return null;
	}

	/**
	 * Pesquisa a {@link MovimentacaoPrestadorAnexo} pelo codLogAuditoria
	 *
	 * @param codLogAuditoria
	 * @return {@link MovimentacaoPrestadorAnexo}
	 */
	public MovimentacaoPrestadorAnexo pesquisarMovimentacaoPrestadorAnexoLogAuditoria (final Long codLogAuditoria)
	{
		if (codLogAuditoria != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorAnexo p ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestador mvPrestador ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorCampo mvPrestadorCampo ");
				sb.append(" WHERE codigoLogAuditoria = :codLogAuditoria ");

				queryParameter.and("codLogAuditoria", codLogAuditoria);

				final TypedQuery<MovimentacaoPrestadorAnexo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorAnexo: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
