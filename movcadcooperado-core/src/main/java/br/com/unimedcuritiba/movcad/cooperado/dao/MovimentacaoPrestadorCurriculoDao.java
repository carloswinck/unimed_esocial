package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCurriculo;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorCurriculoDao
    extends UnibenDao<MovimentacaoPrestadorCurriculo, Long>
{

	private static final long serialVersionUID = -4098445761253083289L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorCurriculoDao.class);

	/**
	 * Pesquisa MovimentacaoPrestadorCurriculo pelo Prestador.
	 *
	 * @param codigo
	 * @return MovimentacaoPrestadorCurriculo
	 */
	public List<MovimentacaoPrestadorCurriculo> findAllByPrestador (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorCurriculo p ");
				sb.append("  LEFT JOIN FETCH p.curriculoPrestadorVO curriculoPrestadorVO ");
				sb.append("  LEFT JOIN FETCH p.tipoCursoEnum tipoCursoEnum ");
				sb.append("  LEFT JOIN FETCH p.areaCurso areaCurso ");
				sb.append("  LEFT JOIN FETCH p.entidadeEnsino entidadeEnsino ");
				sb.append(" WHERE p.movimentacaoPrestador.prestador = :prestador ");

				queryParameter.and("prestador", prestador);

				final TypedQuery<MovimentacaoPrestadorCurriculo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findAllByPrestador: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
