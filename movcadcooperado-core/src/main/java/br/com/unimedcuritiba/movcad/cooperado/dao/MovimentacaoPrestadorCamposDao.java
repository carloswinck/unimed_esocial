package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.dao.ZCampoDao;
import br.com.unimedcuritiba.benner.dao.ZTabelaDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCampo;
import br.com.unimedcuritiba.movcad.cooperado.enums.SimNaoEnum;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

@Stateless
public class MovimentacaoPrestadorCamposDao
    extends UnibenDao<MovimentacaoPrestadorCampo, Long>
{

	private static final long serialVersionUID = 3114817941269276216L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorCamposDao.class);

	@EJB
	private ZTabelaDao zTabelaDAO;

	@EJB
	private ZCampoDao zCampoDAO;

	/**
	 * Pesquisa a MovimentacaoPrestadorCampo pelo nome da tabela
	 *
	 * @param nomeTabela
	 * @return List
	 */
	public List<MovimentacaoPrestadorCampo> pesquisarMovimentacaoPrestadorCampoPorTabela (final String nomeTabela)
	{
		if (nomeTabela != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorCampo p ");
				sb.append("  LEFT JOIN FETCH p.campo campo ");
				sb.append("  LEFT JOIN FETCH p.tabela tabela ");
				sb.append(" WHERE tabela.nome = :nomeTabela ");

				queryParameter.and("nomeTabela", nomeTabela);

				final TypedQuery<MovimentacaoPrestadorCampo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getResultList();
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorCampoPorTabela: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}

		}

		return null;
	}

	/**
	 * Verifica se existe algum campo alterado que necessite de anexo.
	 *
	 * @param nomeTabela
	 * @param nomeCampo
	 * @return MovimentacaoPrestadorCampo
	 */
	public MovimentacaoPrestadorCampo pesquisarMovimentacaoPrestadorCampoAnexo (final String nomeTabela, final String nomeCampo)
	{
		if (nomeTabela != null && nomeCampo != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorCampo p ");
				sb.append("  LEFT JOIN FETCH p.campo campo ");
				sb.append("  LEFT JOIN FETCH p.tabela tabela ");
				sb.append(" WHERE p.tabelaMccNome = :nomeTabela ");
				sb.append("  AND campo.nome= :nomeCampo ");
				sb.append("  AND p.anexo='S' ");

				queryParameter.and("nomeTabela", nomeTabela);
				queryParameter.and("nomeCampo", nomeCampo);

				final TypedQuery<MovimentacaoPrestadorCampo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorCampoPorTabela: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}

		}
		return null;
	}

	/**
	 * Verifica se existe algum campo alterado que necessite analise.
	 *
	 * @param nomeTabela
	 * @param nomeCampo
	 * @return MovimentacaoPrestadorCampo
	 */
	public MovimentacaoPrestadorCampo pesquisarMovimentacaoPrestadorCampoAnalise (final String nomeTabela, final String nomeCampo)
	{
		if (nomeTabela != null && nomeCampo != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestadorCampo p ");
				sb.append("  LEFT JOIN FETCH p.campo campo ");
				sb.append("  LEFT JOIN FETCH p.tabela tabela ");
				sb.append(" WHERE p.tabelaMccNome = :nomeTabela ");
				sb.append("  AND campo.nome= :nomeCampo ");
				sb.append("  AND p.analise='S' ");

				queryParameter.and("nomeTabela", nomeTabela);
				queryParameter.and("nomeCampo", nomeCampo);

				final TypedQuery<MovimentacaoPrestadorCampo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorCampoAnalise: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Pesquisa os campos conforme tabela origem e destino.
	 *
	 * @param nomeTabelaOrigem
	 * @param nomeTabelaDestino
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, MovimentacaoPrestadorCampo> pesquisarCamposTabela (final String nomeTabelaOrigem, final String nomeTabelaDestino)
	{
		if (nomeTabelaOrigem != null && nomeTabelaDestino != null)
		{
			try
			{
				final Map<Long, MovimentacaoPrestadorCampo> campos = new HashMap<Long, MovimentacaoPrestadorCampo>();

				final StringBuilder tabelaQuery = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				tabelaQuery.append("SELECT DISTINCT ");
				tabelaQuery.append("	c.nome nomecampo ");
				tabelaQuery.append("	,c.legendaformulario legenda ");
				tabelaQuery.append("	,cm.opcional ");
				tabelaQuery.append("	,c.classe ");
				tabelaQuery.append("	,decode(c.classe, 5, 'D', 6, 'F', 1, 'I', 2, 'I', 'T') tipocampo ");
				tabelaQuery.append("	,c.ordem ");
				tabelaQuery.append("	,tm.handle tabelahandle");
				tabelaQuery.append("    ,c.handle campohandle ");
				tabelaQuery.append("    ,t.nome tabelaorigem ");
				tabelaQuery.append(" FROM V_Z_CAMPOS c ");
				tabelaQuery.append("  JOIN V_Z_TABELAS t ON t.handle = c.tabela ");
				tabelaQuery.append("  JOIN V_Z_CAMPOS cm ON cm.nome = c.nome ");
				tabelaQuery.append(" 					 AND cm.classe = c.classe ");
				tabelaQuery.append("  JOIN V_Z_TABELAS tm ON tm.handle = cm.tabela ");
				tabelaQuery.append(" WHERE t.nome = :nomeTabelaOrigem ");
				tabelaQuery.append("  AND tm.nome = :nomeTabelaDestino ");
				tabelaQuery.append("  AND c.classe NOT IN (15) ");
				tabelaQuery.append(" ORDER BY c.ordem ");

				queryParameter.and("nomeTabelaOrigem", nomeTabelaOrigem);
				queryParameter.and("nomeTabelaDestino", nomeTabelaDestino);

				final Query nativeQuery = getEntityManager().createNativeQuery(tabelaQuery.toString());

				setParamsOnQuery(nativeQuery, queryParameter.parameters());

				final List<Object[]> listaConsulta = nativeQuery.getResultList();

				if (Validator.isNotEmpty(listaConsulta))
				{

					MovimentacaoPrestadorCampo movimentacaoPrestadorCampo;

					for (final Object[] objects : listaConsulta)
					{
						movimentacaoPrestadorCampo = new MovimentacaoPrestadorCampo();
						movimentacaoPrestadorCampo.setCampoNome((String)objects[0]);
						movimentacaoPrestadorCampo.setLegenda((String)objects[1]);
						movimentacaoPrestadorCampo.setObrigatorio(SimNaoEnum.getBySigla((String)objects[2]).toBoolean());
						movimentacaoPrestadorCampo.setOrdem(Integer.valueOf((String)objects[5]));
						movimentacaoPrestadorCampo.setTabela(zTabelaDAO.findById(((BigDecimal)objects[6]).longValue()));
						movimentacaoPrestadorCampo.setCampo(zCampoDAO.findById(((BigDecimal)objects[7]).longValue()));
						movimentacaoPrestadorCampo.setAnalise(SimNaoEnum.getBySigla("N").toBoolean());
						movimentacaoPrestadorCampo.setAnexo(SimNaoEnum.getBySigla("N").toBoolean());
						movimentacaoPrestadorCampo.setDisable(SimNaoEnum.getBySigla("N").toBoolean());
						movimentacaoPrestadorCampo.setTabelaMccNome((String)objects[8]);

						campos.put(movimentacaoPrestadorCampo.getCampo().getHandle(), movimentacaoPrestadorCampo);
					}
				}

				return campos;
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarCamposTabela: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}

		return null;
	}

	/**
	 * Pesquisa a MovimentacaoPrestadorCampo
	 *
	 * @param movimentacaoPrestadorCampo
	 * @return movimentacaoPrestadorCampo
	 */
	public MovimentacaoPrestadorCampo pesquisarMovimentacaoPrestadorCampo (final MovimentacaoPrestadorCampo movimentacaoPrestadorCampo)
	{
		if (movimentacaoPrestadorCampo != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append("  FROM MovimentacaoPrestadorCampo p ");
				sb.append(" WHERE p.campoNome = :campoNome ");
				sb.append("  AND p.campo = :campo ");
				sb.append("  AND p.tabela = :tabela ");

				queryParameter.and("campoNome", movimentacaoPrestadorCampo.getCampoNome());
				queryParameter.and("campo", movimentacaoPrestadorCampo.getCampo());
				queryParameter.and("tabela", movimentacaoPrestadorCampo.getTabela());

				final TypedQuery<MovimentacaoPrestadorCampo> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("pesquisarMovimentacaoPrestadorCampoPorTabela: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}

		}

		return null;
	}

}
