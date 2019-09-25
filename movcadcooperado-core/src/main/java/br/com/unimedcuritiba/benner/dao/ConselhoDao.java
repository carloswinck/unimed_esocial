/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.benner.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.vo.ConselhoVO;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Stateless
public class ConselhoDao
    extends UnibenDao<ConselhoVO, Long>
{

	private static final long serialVersionUID = 684763303726066438L;

	private static final transient Logger LOG = Logger.getLogger(ConselhoDao.class);

	/**
	 * Lista todos os conselhos.
	 *
	 * @return list
	 */
	public List<ConselhoVO> findAll ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT b ");
			sb.append("  FROM ConselhoVO b ");
			sb.append(" WHERE b.codigoTiss > 0 ");
			sb.append(" ORDER BY b.descricao ");

			final TypedQuery<ConselhoVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("ConselhoDao.findAll(): " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}
}
