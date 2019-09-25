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
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.vo.LogradouroVO;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class LogradouroDao
    extends UnibenDao<LogradouroVO, Long>
{

	private static final long serialVersionUID = 5809666014866534191L;

	private static final transient Logger LOG = Logger.getLogger(LogradouroDao.class);

	/**
	 * Pesquisa Logradouro por cep.
	 *
	 * @param cep
	 * @param Long
	 * @return LogradouroVO
	 */
	public List<LogradouroVO> findByCep (final String cep)
	{
		if (Validator.isNotEmpty(cep))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append("  FROM LogradouroVO p ");
				sb.append(" WHERE p.cep = :cep ");

				queryParameter.and("cep", cep);

				final TypedQuery<LogradouroVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findByCep: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
