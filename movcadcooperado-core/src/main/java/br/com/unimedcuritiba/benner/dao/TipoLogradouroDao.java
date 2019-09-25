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
import br.com.unimedcuritiba.benner.vo.TipoLogradouroVO;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class TipoLogradouroDao
    extends UnibenDao<TipoLogradouroVO, Long>
{

	private static final long serialVersionUID = 954509189346449275L;
	private static final transient Logger LOG = Logger.getLogger(TipoLogradouroDao.class);

	public List<TipoLogradouroVO> findTipoComSigla ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT b ");
			sb.append("  FROM TipoLogradouroVO b ");
			sb.append(" WHERE b.sigla is not null ");
			sb.append(" ORDER BY b.sigla ");

			final TypedQuery<TipoLogradouroVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("findTipoComSigla: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}
}
