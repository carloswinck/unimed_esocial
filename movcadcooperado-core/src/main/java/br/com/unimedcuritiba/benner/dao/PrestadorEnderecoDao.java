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
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.enums.SimNaoEnum;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 1 de jul de 2016
 */
@Stateless
public class PrestadorEnderecoDao
    extends UnibenDao<PrestadorEnderecoVO, Long>
{

	private static final long serialVersionUID = -7952744360083354760L;

	private static final transient Logger LOG = Logger.getLogger(PrestadorEnderecoDao.class);

	/**
	 * Pesquisa o PrestadorEnderecoVO Residencial por codigo do Prestador e carrega seus relacionamentos.
	 *
	 * @param codigo
	 * @return PrestadorEnderecoVO
	 */
	public PrestadorEnderecoVO findResidencialByPrestadorFetch (final PrestadorVO prestador)
	{
		final List<PrestadorEnderecoVO> list = findByPrestadorFullLoad(prestador, true, false);

		if (Validator.isEmpty(list))
		{
			return null;
		}

		return list.get(0);
	}

	/**
	 * Pesquisa o PrestadorEnderecoVO de Atendimento por codigo do Prestador e carrega seus relacionamentos.
	 *
	 * @param codigo
	 * @return PrestadorEnderecoVO
	 */
	public List<PrestadorEnderecoVO> findAtendimentoByPrestadorFetch (final PrestadorVO prestador)
	{
		final List<PrestadorEnderecoVO> list = findByPrestadorFullLoad(prestador, false, true);

		return list;
	}

	/**
	 * Pesquisa o PrestadorEnderecoVO por codigo do Prestador e carrega seus relacionamentos.
	 *
	 * @param codigo
	 * @return PrestadorEnderecoVO
	 */
	public List<PrestadorEnderecoVO> findByPrestadorFullLoad (final PrestadorVO prestador, final boolean pessoal, final boolean atendimento)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM PrestadorEnderecoVO p ");
				sb.append("  LEFT JOIN FETCH p.prestadorVO prestadorVO ");
				sb.append("  LEFT JOIN FETCH p.tipoLogradouro tipoLogradouro ");
				sb.append("  LEFT JOIN FETCH p.municipio municipio ");
				sb.append("  LEFT JOIN FETCH p.estado estado ");
				sb.append(" WHERE p.prestadorVO = :prestador ");
				sb.append(" AND CURRENT_DATE BETWEEN p.dataInicial AND COALESCE(p.dataCancelamento,CURRENT_DATE) ");

				if (pessoal)
				{
					sb.append(" AND p.pessoal = :pessoal ");
					queryParameter.and("pessoal", SimNaoEnum.SIM.getSigla());
				}

				if (atendimento)
				{
					sb.append(" AND p.atendimento = :atendimento ");
					queryParameter.and("atendimento", SimNaoEnum.SIM.getSigla());
				}

				sb.append(" ORDER BY p.dataInicial DESC ");

				queryParameter.and("prestador", prestador);

				final TypedQuery<PrestadorEnderecoVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
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
