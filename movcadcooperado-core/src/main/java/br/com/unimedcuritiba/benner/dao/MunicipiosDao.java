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
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Stateless
public class MunicipiosDao
    extends UnibenDao<MunicipiosVO, Long>
{

	private static final long serialVersionUID = -902047409632887777L;

	private static final transient Logger LOG = Logger.getLogger(MunicipiosDao.class);

	/**
	 * Lista todos os Municipios.
	 *
	 * @return list
	 */
	public List<MunicipiosVO> findAll ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT e ");
			sb.append("  FROM MunicipiosVO e ");
			sb.append(" WHERE e.handle > 0 ");
			sb.append(" ORDER BY e.handle ");

			final TypedQuery<MunicipiosVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("MunicipiosDao.findAll(): " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

	/**
	 * Lista todos os Municipios podendo ordena-los.
	 *
	 * @return list
	 */
	public List<MunicipiosVO> findAllOrderBy (final String field)
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			sb.append("SELECT e ");
			sb.append(" FROM MunicipiosVO e ");
			sb.append("  INNER JOIN FETCH e.pais pais ");
			sb.append("  INNER JOIN FETCH e.estado estado ");
			sb.append(" WHERE e.handle > 0 ");
			sb.append(" ORDER BY e.");
			sb.append("" + field);

			final TypedQuery<MunicipiosVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

			return query.getResultList();
		}
		catch (final Exception e)
		{
			LOG.error("MunicipiosDao.findAllOrderBy(): " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

	public MunicipiosVO findByCepMunicipio (final String cep)
	{
		if (Validator.isNotEmpty(cep))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT m ");
				sb.append("  FROM MunicipiosVO m ");
				sb.append(" join fetch m.estado");
				sb.append(" WHERE m.cep = :cep ");

				queryParameter.and("cep", cep);

				final TypedQuery<MunicipiosVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
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
