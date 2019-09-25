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
import br.com.unimedcuritiba.benner.vo.PrestadorEnderecoVO;
import br.com.unimedcuritiba.benner.vo.PrestadorHorarioVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Paulo Roberto Schwertner
 * @since 4 de out de 2016
 */
@Stateless
public class PrestadorHorarioDao
    extends UnibenDao<PrestadorHorarioVO, Long>
{

	private static final long serialVersionUID = 7017856510962140521L;

	private static final transient Logger LOG = Logger.getLogger(PrestadorHorarioDao.class);

	/**
	 * Pesquisa o PrestadorHorarioVO por codigo do Prestado, Endereco, e carrega seus relacionamentos.
	 *
	 * @param prestador endereco
	 * @return PrestadorHorarioVO
	 */
	public List<PrestadorHorarioVO> findByPrestadorEnderecoFullLoad (final PrestadorVO prestador, final PrestadorEnderecoVO endereco)
	{
		if (prestador != null && endereco != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM PrestadorHorarioVO p ");
				sb.append("  LEFT JOIN FETCH p.prestador prestador ");
				sb.append("  LEFT JOIN FETCH p.prestadorEndereco prestadorEndereco ");
				sb.append(" WHERE p.prestador = :prestador ");
				sb.append(" AND p.prestadorEndereco = :endereco ");
				sb.append(" ORDER BY p.handle DESC ");

				queryParameter.and("prestador", prestador);
				queryParameter.and("endereco", endereco);

				final TypedQuery<PrestadorHorarioVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("findByPrestadorEnderecoFullLoad: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
