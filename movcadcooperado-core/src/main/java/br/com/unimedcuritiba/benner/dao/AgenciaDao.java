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
import br.com.unimedcuritiba.benner.vo.AgenciaVO;
import br.com.unimedcuritiba.benner.vo.BancoVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 26 de dez de 2016
 */
@Stateless
public class AgenciaDao
    extends UnibenDao<AgenciaVO, Long>
{

	private static final long serialVersionUID = 1064014076056916658L;

	private static final transient Logger LOG = Logger.getLogger(AgenciaDao.class);

	/**
	 * Realiza a pesquisa das agencias ativas de um determinado banco.
	 *
	 * @param banco
	 * @return {@link List}
	 */
	public List<AgenciaVO> pesquisarAgenciaPorBanco (final BancoVO banco)
	{
		if (banco != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT b ");
				sb.append("  FROM AgenciaVO b ");
				sb.append(" WHERE b.banco = :banco ");
				sb.append(" AND b.situacao = 'A' ");
				sb.append(" ORDER BY b.nome ");

				queryParameter.and("banco", banco);

				final TypedQuery<AgenciaVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarAgenciaPorBanco: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

}
