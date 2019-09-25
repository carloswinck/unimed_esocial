/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.benner.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.util.TipoPessoa;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.BusinessException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.util.CustomDate;

/**
 * @author Paulo Roberto Schwertner
 * @since 13/04/2015
 */
@Stateless
public class PrestadorDao
    extends UnibenDao<PrestadorVO, Long>
{

	private static final long serialVersionUID = -7736530715101283463L;

	private static final transient Logger LOG = Logger.getLogger(PrestadorDao.class);

	/**
	 * Pesquisa cooperados por codigo, CRM ou nome
	 *
	 * @param codigo
	 * @param maxResults
	 * @return
	 */
	public List<PrestadorVO> findCooperadosForAutoComplete (final String codigo, final Integer maxResults)
	{
		if (Validator.isNotEmpty(codigo))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.with("fisicaJuridica", TipoPessoa.Física.getCodigo());

				sb.append("SELECT p ");
				sb.append("  FROM PrestadorVO p ");
				sb.append(" WHERE p.fisicaJuridica = :fisicaJuridica ");
				sb.append("   AND p.cpfPrestador != p.codigoPrestador ");
				sb.append("   AND (p.dataDescredenciamento is null or p.dataDescredenciamento >= CURRENT_DATE)");

				if (Validator.isLong(codigo))
				{
					sb.append(" AND	( p.codigoPrestador = :codigo OR p.inscricaoCRM = :crm ) ");
					queryParameter.and("codigo", Validator.valueOfLong(codigo));
					queryParameter.and("crm", codigo.trim());
				}
				else
				{
					sb.append(" AND	p.nome LIKE :nome ");
					queryParameter.and("nome", "%" + codigo.trim().toUpperCase() + "%");
				}

				sb.append(" ORDER BY p.nome ");

				final TypedQuery<PrestadorVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				if (Validator.isNotEmpty(maxResults))
				{
					query.setMaxResults(maxResults);
				}

				return query.getResultList();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error(e.getLocalizedMessage(), e);
				e.printStackTrace();
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;

	}

	/**
	 * Pesquisa Prestador por codigo
	 *
	 * @param codigo
	 * @return
	 */
	public PrestadorVO findByCodigo (final Long codigo)
	{
		if (Validator.isNotEmpty(codigo))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append("  FROM PrestadorVO p ");
				sb.append(" WHERE p.codigoPrestador = :codigoPrestador ");

				queryParameter.and("codigoPrestador", codigo);

				final TypedQuery<PrestadorVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findByCodigo: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				e.printStackTrace();
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;

	}

	/**
	 * Pesquisa prestador por id (handle) e carrega seus relacionamentos.
	 *
	 * @param codigo
	 * @return
	 */
	public PrestadorVO findByHandleFullLoad (final Long handle)
	{
		if (Validator.isNotEmpty(handle))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM PrestadorVO p ");
				sb.append("  LEFT JOIN FETCH p.naturalidade naturalidade ");
				sb.append("  LEFT JOIN FETCH p.estado estado ");
				sb.append("  LEFT JOIN FETCH p.nacionalidade nacionalidade ");
				sb.append("  LEFT JOIN FETCH p.conselhoRegional conselhoRegional ");
				sb.append("  LEFT JOIN FETCH p.estadoCivil estadoCivil ");
				sb.append("  LEFT JOIN FETCH p.municipioPagamento municipioPagamento ");
				sb.append("  LEFT JOIN FETCH p.estadoPagamento estadoPagamento ");
				sb.append("  LEFT JOIN FETCH p.ufCNH ufCNH ");
				sb.append("  LEFT JOIN FETCH p.ufCTPS ufCTPS ");
				sb.append("  LEFT JOIN FETCH p.tipoPrestador tipoPrestador ");
				sb.append("  LEFT JOIN FETCH p.ufEmissao ufEmissao ");
				sb.append(" WHERE p.handle = :handle ");

				queryParameter.and("handle", handle);

				final TypedQuery<PrestadorVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findByHandleFullLoad: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;

	}

	/**
	 * Busca prestador pelo codigo e Tipo Pessoa
	 *
	 * @param codigo
	 * @param tipoPessoa
	 *        Enum Tipo Pessoa (fisica / juridica)
	 * @return
	 * @throws BusinessException
	 */
	public PrestadorVO findByCodigoTipoPessoa (final Long codigo, final TipoPessoa tipoPessoa, final Date data)
	{
		if (Validator.isNotEmpty(codigo))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append("  FROM PrestadorVO p ");
				sb.append(" WHERE p.codigoPrestador = :codigoPrestador ");
				sb.append("   AND (p.dataDescredenciamento is null or p.dataDescredenciamento >= :dataDescredenciamento) ");

				queryParameter.and("codigoPrestador", codigo);
				queryParameter.and("dataDescredenciamento", data != null ? data : CustomDate.getCurrentSqlDate());

				if (tipoPessoa != null)
				{
					sb.append("    AND p.fisicaJuridica = :fisicaJuridica ");
					queryParameter.and("fisicaJuridica", tipoPessoa.getCodigo());
				}

				final TypedQuery<PrestadorVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findByCodigoTipoPessoa: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	public PrestadorVO findAtivoByCrm (final String crm, final EstadosVO uf)
	{
		if (Validator.isNotEmpty(crm) && uf != null)
		{

			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				final PrestadorVO entity = new PrestadorVO();

				entity.setEstado(uf);

				sb.append("SELECT entity ");
				sb.append("  FROM PrestadorVO entity ");
				sb.append(" WHERE (entity.dataDescredenciamento is null or entity.dataDescredenciamento >= CURRENT_DATE) ");
				sb.append("   AND entity.inscricaoCRM = :inscricaoCRM");

				queryParameter.and("inscricaoCRM", crm.trim());

				assemblyWhere(entity, sb, queryParameter.parameters());

				final TypedQuery<PrestadorVO> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findAtivoByCrm: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);
			}

		}
		return null;
	}

	public PrestadorVO findPrestador (final String login)
	{
		PrestadorVO medico = null;
		try
		{
			if (Validator.isLong(login))
			{
				medico = findByCodigoTipoPessoa(Long.valueOf(login), null, null);
			}
			else
			{
				// Incidente Nº 61107 / Portal Prestador_Consulta Elegibilidade (Logins Final A)
				final char lastChar = login.charAt(login.length() - 1);
				final char firstChar = login.charAt(0);

				if (Character.isDigit(firstChar) && Character.isLetter(lastChar))
				{
					final String loginAtendente = login.replace(lastChar, ' ').trim();
					medico = findByCodigoTipoPessoa(Long.valueOf(loginAtendente), null, null);
				}
			}
			return medico;
		}
		catch (final Exception e)
		{
			return medico;
		}
	}

}
