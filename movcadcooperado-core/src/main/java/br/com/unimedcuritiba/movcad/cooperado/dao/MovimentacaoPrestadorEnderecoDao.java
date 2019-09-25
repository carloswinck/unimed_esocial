/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorEnderecoDao
    extends UnibenDao<MovimentacaoPrestadorEndereco, Long>
{

	private static final long serialVersionUID = -7952744360083354760L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorEnderecoDao.class);

	/**
	 * Pesquisa MovimentacaoPrestadorEndereco residencial pelo Prestador.
	 *
	 * @param codigo
	 * @return MovimentacaoPrestadorEndereco
	 */
	public MovimentacaoPrestadorEndereco findEnderecoResidencial (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorEndereco p ");
				sb.append("  LEFT JOIN FETCH p.tipoLogradouro tipoLogradouro ");
				sb.append("  LEFT JOIN FETCH p.municipio municipio ");
				sb.append("  LEFT JOIN FETCH p.estado estado ");
				sb.append(" WHERE p.movimentacaoPrestador.prestador = :prestador ");
				sb.append("  AND  p.movimentacaoPrestador.enderecoResidencial = p ");

				queryParameter.and("prestador", prestador);

				final TypedQuery<MovimentacaoPrestadorEndereco> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findEnderecoResidencial: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Exclui logicamente o Endereco
	 *
	 * @param pk
	 * @return
	 */
	public boolean excluirLogicamente (final MovimentacaoPrestadorEndereco t)
	{
		if (t != null)
		{
			final MovimentacaoPrestadorEndereco entity = getEntityManager().getReference(getEntityClass(), t.getId());

			if (entity != null)
			{
				update(entity);
				return true;
			}
		}
		return false;
	}
}
