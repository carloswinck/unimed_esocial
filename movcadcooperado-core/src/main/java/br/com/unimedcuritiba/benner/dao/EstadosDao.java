/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.benner.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Stateless
public class EstadosDao
    extends UnibenDao<EstadosVO, Long>
{

	private static final long serialVersionUID = 7979323427137973786L;

	private static final transient Logger LOG = Logger.getLogger(EstadosDao.class);

	public EstadosVO findBySigla (final String sigla)
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT e ");
			sb.append("  FROM EstadosVO e ");
			sb.append(" WHERE e.sigla = :sigla ");

			final TypedQuery<EstadosVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			query.setParameter("sigla", sigla);

			return query.getSingleResult();
		}
		catch (final NoResultException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			LOG.error("findBySigla: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

	public List<EstadosVO> findEstados ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT e ");
			sb.append("  FROM EstadosVO e  ");
			sb.append(" WHERE e.pais.nome = :brasil ");
			sb.append(" AND e.sigla NOT IN ('EX') ");
			sb.append(" ORDER BY e.sigla ");

			final TypedQuery<EstadosVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());
			query.setParameter("brasil", MovCadCooperadoConstants.BRASIL);
			return query.getResultList();
		}
		catch (final NoResultException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			LOG.error("findEstados: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

}
