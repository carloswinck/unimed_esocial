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
import br.com.unimedcuritiba.benner.vo.ContaFinVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 26 de dez de 2016
 */
@Stateless
public class ContaFinDao
    extends UnibenDao<ContaFinVO, Long>
{

	private static final long serialVersionUID = 5463065895224873177L;

	private static final transient Logger LOG = Logger.getLogger(ContaFinDao.class);

	public ContaFinVO findContaFinFetch (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM ContaFinVO p ");
				sb.append("  LEFT JOIN FETCH p.banco banco ");
				sb.append("  LEFT JOIN FETCH p.agencia agencia ");
				sb.append("  LEFT JOIN FETCH p.prestador prestador ");
				sb.append(" WHERE p.prestador = :prestador ");

				queryParameter.and("prestador", prestador);

				final TypedQuery<ContaFinVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findContaFinFetch: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}
}
