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
import br.com.unimedcuritiba.benner.vo.PrestadorCurriculoVO;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 28 de jun de 2016
 */
@Stateless
public class PrestadorCurriculoDao
    extends UnibenDao<PrestadorCurriculoVO, Long>
{

	private static final long serialVersionUID = -4098445761253083289L;

	private static final transient Logger LOG = Logger.getLogger(PrestadorCurriculoDao.class);

	/**
	 * Pesquisa PrestadorCurriculoVO por codigo do Prestador e carrega seus relacionamentos.
	 *
	 * @param codigo
	 * @return List PrestadorCurriculoVO
	 */
	public List<PrestadorCurriculoVO> findByPrestadorFullLoad (final Long codigoPrestador)
	{
		if (Validator.isNotEmpty(codigoPrestador))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM PrestadorCurriculoVO p ");
				sb.append("  LEFT JOIN FETCH p.entidadeEnsino entidadeEnsino ");
				sb.append("  LEFT JOIN FETCH p.areaCurso areaCurso ");
				sb.append("  LEFT JOIN FETCH p.prestadorVO prestadorVO ");
				sb.append(" WHERE prestadorVO.codigoPrestador = :prestador ");

				queryParameter.and("prestador", codigoPrestador);

				final TypedQuery<PrestadorCurriculoVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findByPrestadorFullLoad: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
