/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.enums.CategoriaPrestadorEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnumPreCad;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoEmailPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

@Stateless
public class MovimentacaoPrestadorPreCadastroDao
    extends UnibenDao<MovimentacaoPrestadorPreCadastro, Long>
{

	private static final long serialVersionUID = -7373401441099298419L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorPreCadastroDao.class);

	/**
	 * Pesquisa o Pre Cadastro jah iniciado,
	 * do cadidato aprovado na selecao publica da Unimed.
	 *
	 * @param Long
	 */
	public MovimentacaoPrestadorPreCadastro pesquisarPrestadorPreCadastro (final String email, final String cpf)
	{
		if (Validator.isNotEmpty(email) && Validator.isNotEmpty(cpf))
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append(" FROM MovimentacaoPrestadorPreCadastro p ");
				sb.append("  LEFT JOIN FETCH p.categoria categoria ");
				sb.append("  LEFT JOIN FETCH p.conselhoRegional conselho ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorEnderecoPreCadastro endereco ");
				sb.append("  LEFT JOIN FETCH endereco.tipoLogradouro tipoLogradouro ");
				sb.append("  LEFT JOIN FETCH endereco.municipio municipio ");
				sb.append("  LEFT JOIN FETCH endereco.estado estado ");
				sb.append("  LEFT JOIN FETCH p.especialidade especialidade ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorFinanceiroPreCadastro financeiro ");
				sb.append("  LEFT JOIN FETCH financeiro.tipoDocumentoFinanceiroVO tipoDocumentoFinanceiro ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorCotasPartesPreCadastro cotasPartes ");
				sb.append("  LEFT JOIN FETCH cotasPartes.movimentacaoPrestadorCotasPartesParametros parametrosCotasPartes ");
				sb.append("  LEFT JOIN FETCH p.anexosPreCadastroList anexoList ");
				sb.append(" WHERE UPPER(p.email) = :email ");
				sb.append(" AND p.cpfPrestador = :cpf ");

				queryParameter.and("email", email);
				queryParameter.and("cpf", cpf);

				final TypedQuery<MovimentacaoPrestadorPreCadastro> query = getEntityManager().createQuery(sb.toString(),
				    MovimentacaoPrestadorPreCadastro.class);

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findPrestadorPreCadastro: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Pesquisa uma lista de Pre Cadastros para envio de Email, conforme as flags informadas nos parametros.
	 *
	 * @param novo
	 * @param recusado
	 * @param aprovado
	 * @return List
	 */
	public List<MovimentacaoPrestadorPreCadastro> pesquisarPrestadorPreCadastrosEmail (final TipoEmailPreCadastro tipoEmailPreCadastro)
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			final QueryParameter queryParameter = QueryParameter.create();

			sb.append("SELECT DISTINCT p ");
			sb.append(" FROM MovimentacaoPrestadorPreCadastro p ");
			sb.append("  LEFT JOIN FETCH p.categoria categoria ");
			sb.append("  LEFT JOIN FETCH p.conselhoRegional conselho ");
			sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorEnderecoPreCadastro endereco ");
			sb.append("  LEFT JOIN FETCH endereco.tipoLogradouro tipoLogradouro ");
			sb.append("  LEFT JOIN FETCH endereco.municipio municipio ");
			sb.append("  LEFT JOIN FETCH endereco.estado estado ");
			sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorFinanceiroPreCadastro financeiro ");
			sb.append("  LEFT JOIN FETCH financeiro.tipoDocumentoFinanceiroVO tipoDocumentoFinanceiro ");
			sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorCotasPartesPreCadastro cotasPartes ");
			sb.append("  LEFT JOIN FETCH cotasPartes.movimentacaoPrestadorCotasPartesParametros parametrosCotasPartes ");
			sb.append("  LEFT JOIN FETCH p.anexosPreCadastroList anexoList ");

			switch (tipoEmailPreCadastro)
			{
			case PRECADASTRO_LIBERADO:
				sb.append(" WHERE p.situacao = :situacao ");
				sb.append(" AND p.emailPreCadastro = :emailPreCadastro ");
				sb.append(" AND p.emailPreCadastroRecusado = :emailPreCadastroRecusado ");
				sb.append(" AND p.emailPreCadastroAprovado = :emailPreCadastroAprovado ");

				queryParameter.and("situacao", SituacaoEnumPreCad.DIGITACAO);
				queryParameter.and("emailPreCadastro", false);
				queryParameter.and("emailPreCadastroRecusado", false);
				queryParameter.and("emailPreCadastroAprovado", false);

				break;
			case PRECADASTRO_RECUSADO:
				sb.append(" WHERE p.situacao = :situacao  ");
				sb.append(" AND p.motivoRecusa IS NOT NULL ");
				sb.append(" AND p.emailPreCadastro = :emailPreCadastro ");
				sb.append(" AND p.emailPreCadastroRecusado = :emailPreCadastroRecusado ");
				sb.append(" AND p.emailPreCadastroAprovado = :emailPreCadastroAprovado ");

				queryParameter.and("situacao", SituacaoEnumPreCad.DIGITACAO);
				queryParameter.and("emailPreCadastro", true);
				queryParameter.and("emailPreCadastroRecusado", false);
				queryParameter.and("emailPreCadastroAprovado", false);

				break;
			case PRECADASTRO_CONCLUIDO:
				sb.append(" WHERE p.situacao = :situacao  ");
				sb.append(" AND p.emailPreCadastro = :emailPreCadastro ");
				sb.append(" AND p.emailPreCadastroAprovado = :emailPreCadastroAprovado ");

				queryParameter.and("situacao", SituacaoEnumPreCad.CONCLUIDO_PAGAMENTO_EFETUADO);
				queryParameter.and("emailPreCadastro", true);
				queryParameter.and("emailPreCadastroAprovado", false);

				break;
			default:
				break;
			}

			final TypedQuery<MovimentacaoPrestadorPreCadastro> query = getEntityManager().createQuery(sb.toString(),
			    MovimentacaoPrestadorPreCadastro.class);

			setParamsOnQuery(query, queryParameter.parameters());

			return query.getResultList();
		}
		catch (final NoResultException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			LOG.error("pesquisarPrestadorPreCadastrosNovos: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);

			LOG.error(e.getLocalizedMessage(), e);
			VExceptions.handleToRuntimeException(e);
		}

		return null;
	}

	/**
	 * Verifica se o prestador da movimentação veio de um pre cadastro(Filiação) ou é um prestador existente
	 *
	 * @param prestador
	 * @return
	 */
	public Boolean isPreCadastro (final PrestadorVO prestador)
	{
		if (prestador != null && prestador.getNomePrestador() != null && prestador.getCpfPrestador() != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append(" FROM MovimentacaoPrestadorPreCadastro p ");
				sb.append(" LEFT JOIN FETCH p.categoria c");
				sb.append(" LEFT JOIN FETCH p.tipoPrestador t");
				sb.append(" WHERE p.nomePrestador = :nome");
				sb.append(" AND c.codigo = :codigoCategoria ");
				sb.append(" AND p.cpfPrestador = :cpf ");
				sb.append(" AND t.codigo = :tipoPrestador ");

				queryParameter.and("nome", prestador.getNomePrestador());
				queryParameter.and("cpf", prestador.getCpfPrestador());
				queryParameter.and("codigoCategoria", Long.valueOf(CategoriaPrestadorEnum.MEDICO_COOPERADO.getCodigo()));
				queryParameter.and("tipoPrestador", MovCadCooperadoConstants.TIPO_PRESTADOR_COOPERADO);

				final TypedQuery<MovimentacaoPrestadorPreCadastro> query = getEntityManager().createQuery(sb.toString(),
				    MovimentacaoPrestadorPreCadastro.class);

				setParamsOnQuery(query, queryParameter.parameters());

				final MovimentacaoPrestadorPreCadastro preCadastro = query.getSingleResult();

				if (preCadastro != null)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			catch (final NoResultException e)
			{
				return false;
			}
			catch (final Exception e)
			{
				LOG.error("isPreCadastro: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}
}
