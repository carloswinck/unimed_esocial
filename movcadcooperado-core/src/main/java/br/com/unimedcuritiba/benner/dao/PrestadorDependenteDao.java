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
import br.com.unimedcuritiba.benner.vo.PrestadorDependenteVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Paulo Roberto Schwertner
 * @since 10/10/2016
 */
@Stateless
public class PrestadorDependenteDao
    extends UnibenDao<PrestadorDependenteVO, Long>
{

	private static final long serialVersionUID = -5118889169221197060L;

	private static final transient Logger LOG = Logger.getLogger(PrestadorDependenteDao.class);

	/**
	 * Pesquisa PrestadorDependenteVO por codigo do Prestador e carrega seus relacionamentos.
	 *
	 * @param codigo
	 * @return List PrestadorCurriculoVO
	 */
	public List<PrestadorDependenteVO> findByPrestadorFullLoad (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM PrestadorDependenteVO p ");
				sb.append("  LEFT JOIN FETCH p.prestador prestador ");
				sb.append("  LEFT JOIN FETCH p.tipoDependente tipoDependente ");
				sb.append(" WHERE prestador = :prestador and p.dataFinal is null");

				queryParameter.and("prestador", prestador);

				final TypedQuery<PrestadorDependenteVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

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
