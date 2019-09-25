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
import br.com.unimedcuritiba.benner.vo.PaisesVO;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Stateless
public class PaisesDao
    extends UnibenDao<PaisesVO, Long>
{

	private static final long serialVersionUID = 2931008048660278491L;

	private static final transient Logger LOG = Logger.getLogger(PaisesDao.class);

	/**
	 * Lista todos os Paises.
	 *
	 * @return list
	 */
	public List<PaisesVO> findAll ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT p ");
			sb.append("  FROM PaisesVO p ");
			sb.append(" WHERE p.handle > 0");
			sb.append(" ORDER BY UPPER(p.nome) ");

			final TypedQuery<PaisesVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("PaisesDao.findAll(): " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

}
