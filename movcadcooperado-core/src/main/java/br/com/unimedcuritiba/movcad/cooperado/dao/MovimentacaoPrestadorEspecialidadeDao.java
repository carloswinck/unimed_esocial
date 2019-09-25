/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.util.List;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.vo.EspecialidadeVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEspecialidade;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

@Stateless
public class MovimentacaoPrestadorEspecialidadeDao
    extends UnibenDao<MovimentacaoPrestadorEspecialidade, Long>
{
	private static final long serialVersionUID = 6745018634993789961L;
	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorEspecialidadeDao.class);

	public EspecialidadeVO findResidencialByPrestadorFetch (final PrestadorVO prestador)
	{
		final List<EspecialidadeVO> list = findByPrestadorFullLoad(prestador, true);

		if (Validator.isEmpty(list))
		{
			return null;
		}

		return list.get(0);
	}

	public List<EspecialidadeVO> findByPrestadorFullLoad (final PrestadorVO prestador, final boolean pessoal)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM EspecialidadeVO p ");
				sb.append("  LEFT JOIN FETCH p.prestadorVO prestadorVO ");
				// sb.append(" LEFT JOIN FETCH p.tipoLogradouro tipoLogradouro ");
				// sb.append(" LEFT JOIN FETCH p.municipio municipio ");
				// sb.append(" LEFT JOIN FETCH p.estado estado ");
				sb.append(" WHERE p.prestadorVO = :prestador ");
				sb.append(" AND CURRENT_DATE BETWEEN p.dataInicial AND COALESCE(p.dataCancelamento,CURRENT_DATE) ");

				sb.append(" ORDER BY p.dataInicial DESC ");

				queryParameter.and("prestador", prestador);

				// final TypedQuery<EspecialidadeVO> query = getEntityManager().createQuery(sb.toString(),
				// getEntityClass());

				// setParamsOnQuery(query, queryParameter.parameters());

				// return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("findByPrestadorFullLoad: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}
}
