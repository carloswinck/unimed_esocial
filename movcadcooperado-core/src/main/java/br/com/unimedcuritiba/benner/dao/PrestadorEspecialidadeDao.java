/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.vo.PrestadorEspecialidadeVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

@Stateless
public class PrestadorEspecialidadeDao
    extends UnibenDao<PrestadorEspecialidadeVO, Long>
{

	private static final long serialVersionUID = 7773076501584466215L;
	private static final transient Logger LOG = Logger.getLogger(PrestadorEspecialidadeDao.class);

	public PrestadorEspecialidadeVO findEspecialidadeByPrestadorFetch (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM PrestadorEspecialidadeVO p ");
				sb.append("  LEFT JOIN FETCH p.prestadorVO prestadorVO ");
				sb.append("  LEFT JOIN FETCH p.especialidade especialidadeVO ");
				sb.append(" WHERE prestadorVO = :prestador ");
				sb.append(" AND p.principal = 'S' ");
				sb.append(" AND p.dataFinal IS NULL ");

				queryParameter.and("prestador", prestador);

				final TypedQuery<PrestadorEspecialidadeVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findEspecialidadeByPrestadorFetch: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
