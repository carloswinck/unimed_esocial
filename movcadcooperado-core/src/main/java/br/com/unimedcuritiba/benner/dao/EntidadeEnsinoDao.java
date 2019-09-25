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
import br.com.unimedcuritiba.benner.vo.EntidadeEnsinoVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class EntidadeEnsinoDao
    extends UnibenDao<EntidadeEnsinoVO, Long>
{

	private static final long serialVersionUID = -8017544004994048153L;

	private static final transient Logger LOG = Logger.getLogger(EntidadeEnsinoDao.class);

	/**
	 * Pesquisa entidades de ensino ativas.
	 */
	public List<EntidadeEnsinoVO> findAllActives ()
	{

		try
		{
			final StringBuilder sb = new StringBuilder();

			final QueryParameter queryParameter = QueryParameter.create();

			sb.append("SELECT p ");
			sb.append("  FROM EntidadeEnsinoVO p ");
			sb.append(" WHERE p.inativo = 'N' ");
			sb.append("  ORDER BY p.descricao asc ");

			final TypedQuery<EntidadeEnsinoVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			setParamsOnQuery(query, queryParameter.parameters());

			return query.getResultList();
		}
		catch (final NoResultException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			LOG.error("findAllActives: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);

			LOG.error(e.getLocalizedMessage(), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

}
