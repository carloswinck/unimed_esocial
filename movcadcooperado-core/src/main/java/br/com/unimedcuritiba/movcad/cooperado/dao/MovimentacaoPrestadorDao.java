/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.core.dao.HibernateUtil;
import br.com.unimedcuritiba.core.dao.JDBCUtil;
import br.com.unimedcuritiba.core.exception.BusinessException;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorAnexo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorDependente;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorHorario;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnum;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.QueryParameter;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorDao
    extends UnibenDao<MovimentacaoPrestador, Long>
{

	private static final long serialVersionUID = -7531809969276580247L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorDao.class);

	/**
	 * Verifica a existencia da MovimentacaoPrestador.
	 *
	 * @param Long
	 */
	public Long verificaExistenciaMovimentacaoPrestador (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT COUNT(p) ");
				sb.append(" FROM MovimentacaoPrestador p ");
				sb.append("  WHERE p.prestador = :prestador ");
				sb.append("  AND p.situacao in (:situacaoParcial, :situacaoAnalise) ");

				queryParameter.and("prestador", prestador);
				queryParameter.and("situacaoParcial", SituacaoEnum.SALVO_PARCIALMENTE);
				queryParameter.and("situacaoAnalise", SituacaoEnum.ANALISE);

				final TypedQuery<Long> query = getEntityManager().createQuery(sb.toString(), Long.class);

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("verificaExistenciMovimentacaoPrestador: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Verifica todos os cadastros com recusa para envio de e-mail.
	 *
	 * @param Long
	 */
	public List<MovimentacaoPrestador> verificaRecusaMovimentacaoPrestador ()
	{
		try
		{
			final StringBuilder sb = new StringBuilder();

			final QueryParameter queryParameter = QueryParameter.create();

			sb.append(" FROM MovimentacaoPrestador p ");
			sb.append("  WHERE p.situacao = :situacaoDigitacao ");
			sb.append("  AND   p.motivoRecusa IS NOT NULL ");
			sb.append("  AND   p.enviaEmailCadastroRecusado = 'S' ");

			queryParameter.and("situacaoDigitacao", SituacaoEnum.SALVO_PARCIALMENTE);

			final TypedQuery<MovimentacaoPrestador> query = getEntityManager().createQuery(sb.toString(), MovimentacaoPrestador.class);

			setParamsOnQuery(query, queryParameter.parameters());

			return query.getResultList();
		}
		catch (final NoResultException e)
		{
			return null;
		}
		catch (final Exception e)
		{
			LOG.error("verificaRecusaMovimentacaoPrestador: " + VExceptions.getErrorMessage(e), e);
			VExceptions.handleToRuntimeException(e);

			LOG.error(e.getLocalizedMessage(), e);
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

	/**
	 * Verifica a maior data de integracao de uma Movimentacao.
	 *
	 * @param Long
	 */
	public Date verificaMaiorDataIntegracao (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			try
			{
				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT MAX(p.dataIntegracao) ");
				sb.append(" FROM MovimentacaoPrestador p ");
				sb.append(" WHERE p.prestador = :prestador ");
				sb.append(" AND p.situacao = :situacao ");

				queryParameter.and("prestador", prestador);
				queryParameter.and("situacao", SituacaoEnum.CONCLUIDO);

				final TypedQuery<Date> query = getEntityManager().createQuery(sb.toString(), Date.class);

				setParamsOnQuery(query, queryParameter.parameters());

				return query.getSingleResult();
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("verificaMaiorDataIntegracao: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Pesquisa para ver se o Prestador jah esta atualizado.
	 *
	 * @param prestador
	 * @param situacao
	 * @return MovimentacaoPrestador
	 */
	public MovimentacaoPrestador prestadorJahAtualizado (final PrestadorVO prestador, final SituacaoEnum situacao)
	{
		if (prestador != null && situacao != null)
		{
			try
			{
				List<MovimentacaoPrestador> movimentacaoPrestadorList = new ArrayList<MovimentacaoPrestador>();

				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestador p ");
				sb.append("  LEFT JOIN FETCH p.prestador prestador ");
				sb.append(" WHERE prestador = :prestador ");
				sb.append(" AND p.situacao = :situacao ");
				sb.append(" ORDER BY p.dataInclusao DESC ");

				queryParameter.and("prestador", prestador);
				queryParameter.and("situacao", situacao);

				final TypedQuery<MovimentacaoPrestador> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				movimentacaoPrestadorList = query.getResultList();

				if (Validator.isEmpty(movimentacaoPrestadorList))
				{
					return null;
				}

				return movimentacaoPrestadorList.get(0);
			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("ehPrestadorPreCadastro: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * Pesquisa MovimentacaoPrestador pelo Prestador, e carrega seus relacionamentos.
	 *
	 * @param PrestadorVO
	 * @return MovimentacaoPrestador
	 */
	public MovimentacaoPrestador findByPrestadorSituacao (final PrestadorVO prestador, final SituacaoEnum situacao)
	{
		if (prestador != null)
		{
			try
			{
				final Session session = (Session)getEntityManager().getDelegate();

				List<MovimentacaoPrestador> movimentacaoPrestadorList = new ArrayList<MovimentacaoPrestador>();

				final StringBuilder sb = new StringBuilder();

				final QueryParameter queryParameter = QueryParameter.create();

				sb.append("SELECT p ");
				sb.append(" FROM MovimentacaoPrestador p ");
				sb.append("  LEFT JOIN FETCH p.prestador prestador ");
				sb.append("  LEFT JOIN FETCH p.naturalidade naturalidade ");
				sb.append("  LEFT JOIN FETCH p.estado estado ");
				sb.append("  LEFT JOIN FETCH p.nacionalidade nacionalidade ");
				sb.append("  LEFT JOIN FETCH p.conselhoRegional conselhoRegional ");
				sb.append("  LEFT JOIN FETCH p.estadoCivil estadoCivil ");
				sb.append("  LEFT JOIN FETCH p.municipioPagamento municipioPagamento ");
				sb.append("  LEFT JOIN FETCH p.estadoPagamento estadoPagamento ");
				sb.append("  LEFT JOIN FETCH p.ufCNH ufCNH ");
				sb.append("  LEFT JOIN FETCH p.ufCTPS ufCTPS ");
				sb.append("  LEFT JOIN FETCH p.dadosFinanceiros dadosFinanceiros ");
				sb.append("  LEFT JOIN FETCH dadosFinanceiros.banco banco ");
				sb.append("  LEFT JOIN FETCH dadosFinanceiros.agencia agencia ");
				sb.append("  LEFT JOIN FETCH dadosFinanceiros.contaFin contaFin ");
				sb.append("  LEFT JOIN FETCH p.enderecoResidencial enderecoResidencial ");
				sb.append("  LEFT JOIN FETCH enderecoResidencial.tipoLogradouro tipoLogradouro ");
				sb.append("  LEFT JOIN FETCH enderecoResidencial.municipio municipio ");
				sb.append("  LEFT JOIN FETCH enderecoResidencial.estado estado ");
				sb.append("  LEFT JOIN FETCH enderecoResidencial.prestadorEnderecoVO prestadorEnderecoVO ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorCurriculos prestadorCurriculos ");
				sb.append("  LEFT JOIN FETCH prestadorCurriculos.entidadeEnsino entidadeEnsino ");
				sb.append("  LEFT JOIN FETCH prestadorCurriculos.areaCurso areaCurso ");
				sb.append("  LEFT JOIN FETCH prestadorCurriculos.curriculoPrestadorVO curriculoPrestadorVO ");
				sb.append("  LEFT JOIN FETCH p.movimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade ");
				sb.append("  LEFT JOIN FETCH movimentacaoPrestadorEspecialidade.prestadorEspecialidade prestadorEspecialidade ");
				sb.append("  LEFT JOIN FETCH movimentacaoPrestadorEspecialidade.especialidade especialidade ");
				sb.append("  LEFT JOIN FETCH prestadorEspecialidade.especialidade especialidadeVO ");
				sb.append("  LEFT JOIN FETCH p.ufEmissao ufEmissao ");
				sb.append("  LEFT JOIN FETCH p.ufEmissorDNI ufEmissorDNI ");
				sb.append(" WHERE prestador = :prestador ");
				sb.append(" AND p.situacao = :situacao ");
				sb.append(" ORDER BY p.dataInclusao DESC ");

				queryParameter.and("prestador", prestador);
				queryParameter.and("situacao", situacao);

				session.enableFilter("dependente");
				session.enableFilter("atendimento");
				session.enableFilter("horario");
				session.enableFilter("anexo");

				final TypedQuery<MovimentacaoPrestador> query = getEntityManager().createQuery(sb.toString(), getEntityClass());

				setParamsOnQuery(query, queryParameter.parameters());

				movimentacaoPrestadorList = query.getResultList();

				if (Validator.isEmpty(movimentacaoPrestadorList))
				{
					return null;
				}

				if (!situacao.equals(SituacaoEnum.CONCLUIDO))
				{
					for (final MovimentacaoPrestador movimentacaoPrestador : movimentacaoPrestadorList)
					{
						if (Validator.isNotEmpty(movimentacaoPrestador.getEnderecosAtendimento()))
						{

							Hibernate.initialize(movimentacaoPrestador.getEnderecosAtendimento());

							for (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco : movimentacaoPrestador.getEnderecosAtendimento())
							{
								Hibernate.initialize(movimentacaoPrestadorEndereco.getPrestadorEnderecoVO());
								Hibernate.initialize(movimentacaoPrestadorEndereco.getTipoLogradouro());
								Hibernate.initialize(movimentacaoPrestadorEndereco.getEstado());
								Hibernate.initialize(movimentacaoPrestadorEndereco.getMunicipio());

								if (movimentacaoPrestadorEndereco.getPrestadorEnderecoVO() != null)
								{
									Hibernate.initialize(movimentacaoPrestadorEndereco.getPrestadorEnderecoVO().getPrestadorVO());
								}

								if (Validator.isNotEmpty(movimentacaoPrestadorEndereco.getPrestadorHorarios()))
								{

									Hibernate.initialize(movimentacaoPrestadorEndereco.getPrestadorHorarios());

									for (final MovimentacaoPrestadorHorario horario : movimentacaoPrestadorEndereco.getPrestadorHorarios())
									{
										Hibernate.initialize(horario.getPrestadorHorarioVO());
										if (horario.getPrestadorHorarioVO() != null)
										{
											Hibernate.initialize(horario.getPrestadorHorarioVO().getPrestador());
										}
									}
								}
							}
						}

						if (Validator.isNotEmpty(movimentacaoPrestador.getDependentes()))
						{
							Hibernate.initialize(movimentacaoPrestador.getDependentes());

							for (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente : movimentacaoPrestador.getDependentes())
							{
								Hibernate.initialize(movimentacaoPrestadorDependente.getDependentePrestadorVO());
								if (movimentacaoPrestadorDependente.getDependentePrestadorVO() != null)
								{
									Hibernate.initialize(movimentacaoPrestadorDependente.getDependentePrestadorVO().getPrestador());
									Hibernate.initialize(movimentacaoPrestadorDependente.getTipoDependente());
								}
							}
						}

						if (Validator.isNotEmpty(movimentacaoPrestador.getAnexosList()))
						{
							Hibernate.initialize(movimentacaoPrestador.getAnexosList());

							for (final MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo : movimentacaoPrestador.getAnexosList())
							{
								Hibernate.initialize(movimentacaoPrestadorAnexo.getMovimentacaoPrestadorCampo());
							}
						}
					}
				}

				return movimentacaoPrestadorList.get(0);

			}
			catch (final NoResultException e)
			{
				return null;
			}
			catch (final Exception e)
			{
				LOG.error("findByPrestadorSituacao: " + VExceptions.getErrorMessage(e), e);
				VExceptions.handleToRuntimeException(e);

				LOG.error(e.getLocalizedMessage(), e);
				VExceptions.handleToRuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * @param Executa a procedure de transferencia de dados
	 * @throws BusinessException
	 */
	public void transfereDadosSalvos (final Long handleMovimentacaoPrestador) throws Exception
	{
		Connection connection = null;
		CallableStatement st = null;
		try
		{
			connection = HibernateUtil.getConnection(getEntityManager());

			LOG.debug("Id Prestador: " + handleMovimentacaoPrestador);

			final StringBuffer buffer = new StringBuffer();
			buffer.append(" call ");
			buffer.append(" visionnaire_movcad_cooperado.atualizarcadastro(?,?)");
			LOG.debug(buffer);
			st = connection.prepareCall(buffer.toString());

			if (handleMovimentacaoPrestador != null)
			{
				int i = 1;
				JDBCUtil.setParameterStatement(st, handleMovimentacaoPrestador, i++);
				st.registerOutParameter(i, Types.VARCHAR);

				st.execute();

				final String string = st.getString(i);
				LOG.debug("CallableStatement: " + string);
			}

			LOG.debug("Id Prestador: " + handleMovimentacaoPrestador);

		}
		catch (final Exception e)
		{
			LOG.error(e.getLocalizedMessage(), e.getCause());
			throw e;
		}
		finally
		{
			JDBCUtil.closeStatement(st);
			// JDBCUtil.closeConnection(connection);
			connection = null;
			st = null;
		}
	}

	/**
	 * @param Executa a procedure de transferencia de dados para prestador que veio do pre-cadastro
	 * @throws BusinessException
	 */
	public void transfereDadosSalvosPreCadastro (final Long handleMovimentacaoPrestador) throws Exception
	{
		Connection connection = null;
		CallableStatement st = null;
		try
		{
			connection = HibernateUtil.getConnection(getEntityManager());

			LOG.debug("Id Prestador: " + handleMovimentacaoPrestador);

			final StringBuffer buffer = new StringBuffer();
			buffer.append(" call ");
			buffer.append(" visionnaire_precad_integracao.atualizarcadastro(?,?)");
			LOG.debug(buffer);
			st = connection.prepareCall(buffer.toString());

			if (handleMovimentacaoPrestador != null)
			{
				int i = 1;
				JDBCUtil.setParameterStatement(st, handleMovimentacaoPrestador, i++);
				st.registerOutParameter(i, Types.VARCHAR);

				st.execute();

				final String string = st.getString(i);
				LOG.debug("CallableStatement: " + string);
			}

			LOG.debug("Id Prestador: " + handleMovimentacaoPrestador);

		}
		catch (final Exception e)
		{
			LOG.error(e.getLocalizedMessage(), e.getCause());
			throw e;
		}
		finally
		{
			JDBCUtil.closeStatement(st);
			// JDBCUtil.closeConnection(connection);
			connection = null;
			st = null;
		}
	}

}
