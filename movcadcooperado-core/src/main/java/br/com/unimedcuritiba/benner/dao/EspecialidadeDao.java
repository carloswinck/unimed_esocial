/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.vo.EspecialidadeVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

@Stateless
public class EspecialidadeDao
    extends UnibenDao<EspecialidadeVO, Long>
{
	private static final long serialVersionUID = 2979104604678144087L;
	private static final transient Logger LOG = Logger.getLogger(EspecialidadeDao.class);

	public List<EspecialidadeVO> findEspecialidade ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();
			final QueryParameter queryParameter = QueryParameter.create();
			sb.append("SELECT ES ");
			sb.append(" FROM EspecialidadeVO ES ");
			sb.append(" WHERE ES.tipo = 'E' ");
			sb.append(" AND ES.dataFinal IS NULL ");
			sb.append(" ORDER BY ES.descricao ");

			final TypedQuery<EspecialidadeVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());
			setParamsOnQuery(query, queryParameter.parameters());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("findEspecialidade: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

}
