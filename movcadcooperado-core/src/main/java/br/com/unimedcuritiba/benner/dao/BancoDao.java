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
import br.com.unimedcuritiba.benner.vo.BancoVO;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 26 de dez de 2016
 */
@Stateless
public class BancoDao
    extends UnibenDao<BancoVO, Long>
{

	private static final long serialVersionUID = -8305492491791436338L;

	private static final transient Logger LOG = Logger.getLogger(BancoDao.class);

	/**
	 * Realiza a pesquisa de um deteminado banco pelo seu codigo.
	 *
	 * @param codigo
	 * @return {@link List}
	 */
	public List<BancoVO> pesquisarBancoPorCodigo (final Long codigo)
	{
		if (codigo != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT b ");
				sb.append("  FROM BancoVO b ");
				sb.append(" WHERE b.codigo = :codigo ");

				queryParameter.and("codigo", codigo);

				final TypedQuery<BancoVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("findSantander: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}
}
