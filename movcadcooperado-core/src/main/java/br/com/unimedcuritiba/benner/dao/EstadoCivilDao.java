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
import br.com.unimedcuritiba.benner.vo.EstadoCivilVO;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */
@Stateless
public class EstadoCivilDao
    extends UnibenDao<EstadoCivilVO, Long>
{

	private static final long serialVersionUID = 8675589568286621199L;

	private static final transient Logger LOG = Logger.getLogger(EstadoCivilDao.class);

	/**
	 * Lista todos os tipos de Estado Civil.
	 *
	 * @return list
	 */
	public List<EstadoCivilVO> findAll ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT b ");
			sb.append("  FROM EstadoCivilVO b ");
			sb.append(" WHERE b.handle > 0 ");
			sb.append(" ORDER BY b.handle ");

			final TypedQuery<EstadoCivilVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("EstadoCivilDao.findAll(): " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

}
