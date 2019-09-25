/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.AgenciaDao;
import br.com.unimedcuritiba.benner.dao.ConselhoDao;
import br.com.unimedcuritiba.benner.dao.PrestadorDao;
import br.com.unimedcuritiba.benner.vo.AgenciaVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.LogAuditoriaDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorAnexoDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorCamposDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.LogAuditoria;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorAnexo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCampo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCurriculo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorDependente;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEspecialidade;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorFinanceiro;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorHorario;
import br.com.unimedcuritiba.movcad.cooperado.enums.LogAuditoriaSituacaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SimNaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoCursoEnum;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;
import br.com.visionnaire.core.util.CustomDate;
import br.com.visionnaire.core.util.beancomparator.BeanComparator;
import br.com.visionnaire.core.util.beancomparator.BeanDifference;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class MovimentacaoPrestadorService
    extends DelegateCrud<MovimentacaoPrestador, Long, MovimentacaoPrestadorDao>
{

	private static final long serialVersionUID = -881422620340688458L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorService.class);

	@Inject
	private Messages messages;

	@EJB
	private AgenciaDao agenciaDao;

	@EJB
	private ConselhoDao conselhoDao;

	@EJB
	private PrestadorDao prestadorDao;

	@EJB
	private LogAuditoriaDao logAuditoriaDao;

	@EJB
	private MovimentacaoPrestadorDao movimentacaoPrestadorDao;

	@EJB
	private MovimentacaoPrestadorAnexoDao movimentacaoPrestadorAnexoDao;

	@EJB
	private MovimentacaoPrestadorCamposDao movimentacaoPrestadorCamposDao;

	@EJB
	private MovimentacaoPrestadorPreCadastroDao movimentacaoPreCadastroDao;

	@EJB
	private MovimentacaoPrestadorHorarioService movimentacaoPrestadorHorarioService;

	@EJB
	private MovimentacaoPrestadorEnderecoService movimentacaoPrestadorEnderecoService;

	@EJB
	private MovimentacaoPrestadorCurriculoService movimentacaoPrestadorCurriculoService;

	@EJB
	private MovimentacaoPrestadorDependenteService movimentacaoPrestadorDependenteService;

	@EJB
	private MovimentacaoPrestadorFinanceiroService movimentacaoPrestadorFinanceiroService;

	@EJB
	private MovimentacaoPrestadorEspecialidadeService movimentacaoPrestadorEspecialidadeService;

	@Override
	protected MovimentacaoPrestadorDao getDelegate ()
	{
		return movimentacaoPrestadorDao;
	}

	/**
	 * Pesquisa a MovimentacaoPrestador na tabela Client(K_) pelo Prestador.
	 *
	 * @param prestadorVO
	 * @return
	 */
	public MovimentacaoPrestador getMovimentacaoPrestador (final PrestadorVO prestadorVO, final SituacaoEnum situacao)
	{
		MovimentacaoPrestador movimentacaoPrestador = new MovimentacaoPrestador();

		try
		{
			movimentacaoPrestador = movimentacaoPrestadorDao.findByPrestadorSituacao(prestadorVO, situacao);
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorService.getMovimentacaoPrestador() - error:" + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("movimentacao.prestador.find.erro");
		}

		return movimentacaoPrestador;
	}

	/**
	 * Pesquisa a MovimentacaoPrestador na tabela Client(K_) pelo Prestador.
	 *
	 * @param prestadorVO
	 * @return
	 */
	public MovimentacaoPrestador getMovimentacaoPrestadorJahAtualizado (final PrestadorVO prestadorVO, final SituacaoEnum situacao)
	{
		MovimentacaoPrestador movimentacaoPrestador = new MovimentacaoPrestador();

		try
		{
			movimentacaoPrestador = movimentacaoPrestadorDao.prestadorJahAtualizado(prestadorVO, situacao);
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorService.getMovimentacaoPrestadorJahAtualizado() - error:" + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("movimentacao.prestador.find.erro");
		}

		return movimentacaoPrestador;
	}

	/**
	 * Constroi a MovimentacaoPrestador, caso ainda nao exista da tabela Client(K_).
	 *
	 * @param prestadorVO
	 * @return MovimentacaoPrestador
	 */
	public MovimentacaoPrestador buildMovimentacaoPrestador (final PrestadorVO prestadorVO)
	{
		LOG.info("buildMovimentacaoPrestador -> begin: " + prestadorVO.getCodigoPrestador());

		final MovimentacaoPrestador movimentacaoPrestador = new MovimentacaoPrestador();

		try
		{
			BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
			BeanUtils.copyProperties(movimentacaoPrestador, prestadorVO);
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorService.initMovimentacaoPrestador() - error BeanUtils:" + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("movimentacao.prestador.find.erro");
		}

		// Relacionando o Prestador.
		movimentacaoPrestador.setPrestador(prestadorVO);

		// Cria os dados financeiros e gera os relacionamentos.
		final MovimentacaoPrestadorFinanceiro movimentacaoPrestadorFinanceiro = movimentacaoPrestadorFinanceiroService.createDadosFinanceiros(
		    prestadorVO);
		movimentacaoPrestadorFinanceiro.setMovimentacaoPrestador(movimentacaoPrestador);
		movimentacaoPrestador.setDadosFinanceiros(movimentacaoPrestadorFinanceiro);

		// Cria o endereco residencial e gera os relacionamentos.
		final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco = movimentacaoPrestadorEnderecoService.createEnderecoResidencial(
		    prestadorVO);
		movimentacaoPrestadorEndereco.setTipoAtendimento(SimNaoEnum.NAO.getSigla());
		movimentacaoPrestadorEndereco.setMovimentacaoPrestador(movimentacaoPrestador);
		movimentacaoPrestador.setEnderecoResidencial(movimentacaoPrestadorEndereco);

		// Cria as qualificacoes/curriculos do prestador
		final List<MovimentacaoPrestadorCurriculo> movimentacaoPrestadorCurriculos = movimentacaoPrestadorCurriculoService.createMovimentacaoPrestadorCurriculo(
		    prestadorVO, movimentacaoPrestador);
		movimentacaoPrestador.setMovimentacaoPrestadorCurriculos(movimentacaoPrestadorCurriculos);

		// Cria os dependentes do prestador
		final List<MovimentacaoPrestadorDependente> movimentacaoPrestadorDependentes = movimentacaoPrestadorDependenteService.createMovimentacaoPrestadorDependente(
		    prestadorVO, movimentacaoPrestador);
		movimentacaoPrestador.setDependentes(movimentacaoPrestadorDependentes);

		// Cria os enderecos de atendimento do prestador
		final List<MovimentacaoPrestadorEndereco> movimentacaoPrestadorEnderecoAtendimentos = movimentacaoPrestadorEnderecoService.createDadosAtendimento(
		    prestadorVO, movimentacaoPrestador);
		movimentacaoPrestador.setEnderecosAtendimento(movimentacaoPrestadorEnderecoAtendimentos);

		// Cria os horarios dos enderecos de atendimento
		if (Validator.isNotEmpty(movimentacaoPrestador.getEnderecosAtendimento()))
		{
			List<MovimentacaoPrestadorHorario> movimentacaoPrestadorHorarios;
			for (final MovimentacaoPrestadorEndereco movPrestadorEndereco : movimentacaoPrestador.getEnderecosAtendimento())
			{
				movimentacaoPrestadorHorarios = movimentacaoPrestadorHorarioService.createHorariosAtendimento(prestadorVO, movPrestadorEndereco);
				movPrestadorEndereco.setPrestadorHorarios(movimentacaoPrestadorHorarios);
			}
		}

		// Cria as especialidades do prestador
		final MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade = movimentacaoPrestadorEspecialidadeService.createEspecialidade(
		    prestadorVO);
		movimentacaoPrestadorEspecialidade.setMovimentacaoPrestador(movimentacaoPrestador);
		movimentacaoPrestador.setMovimentacaoPrestadorEspecialidade(movimentacaoPrestadorEspecialidade);

		LOG.info("buildMovimentacaoPrestador <- end: " + prestadorVO.getCodigoPrestador());

		return movimentacaoPrestador;
	}

	/**
	 * Conclui e salva a MovimentacaoPrestador na tabela Client(K_).
	 *
	 * @param movimentacaoPrestador
	 * @return MovimentacaoPrestador
	 * @throws Exception
	 */
	public MovimentacaoPrestador concluirMovimentacao (MovimentacaoPrestador movimentacaoPrestador) throws Exception
	{
		try
		{
			// Executa os inserts e updates nas tabelas SAM.
			if (movimentacaoPrestador.getId() != null)
			{

				if (verificarMovimentacaoAnalise(movimentacaoPrestador))
				{
					/*
					 * Se existir algum campo alterado setado como analise,
					 * a situacao da Movimentacao sera ANALISE,
					 * ficando a cargo da equipe da Unimed de validar esse
					 * cadastro.
					 */
					movimentacaoPrestador.setSituacao(SituacaoEnum.ANALISE);

					movimentacaoPrestador = movimentacaoPrestadorDao.update(movimentacaoPrestador);
				}
				else
				{
					if (movimentacaoPrestador.getPreCadastro())
					{
						movimentacaoPrestadorDao.transfereDadosSalvosPreCadastro(movimentacaoPrestador.getId());
					}
					else
					{
						movimentacaoPrestadorDao.transfereDadosSalvos(movimentacaoPrestador.getId());
					}

					/*
					 * Apos atualizacao executada pela Procedure,
					 * registra a Movimentacao Prestador como concluida.
					 */
					movimentacaoPrestador.setSituacao(SituacaoEnum.CONCLUIDO);
					movimentacaoPrestador.setDataIntegracao(new Date());
					movimentacaoPrestador = movimentacaoPrestadorDao.update(movimentacaoPrestador);

					LOG.info("MovimentacaoPrestadorService.savePartialMovimentacaoPrestador() -::::: >>>>> Cadastro Concluído <<<<< :::::\n");
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestador.concluirMovimentacao() -> " + VExceptions.getErrorMessage(e), e);
			throw e;
		}
		return movimentacaoPrestador;
	}

	/**
	 * Verifica se existe algum campo alterado que requer analise,
	 * apos a conclusao do cadastro.
	 *
	 * @return boolean
	 */
	private boolean verificarMovimentacaoAnalise (final MovimentacaoPrestador movimentacaoPrestador)
	{
		boolean retorno = false;

		if (movimentacaoPrestador != null)
		{
			// Lista de logs da Movimentacao
			final List<LogAuditoria> listAuditoria = logAuditoriaDao.findLogAuditoriaByPrestador(movimentacaoPrestador);

			if (Validator.isNotEmpty(listAuditoria))
			{
				MovimentacaoPrestadorCampo campoAlteradoAnexo = null;

				for (final LogAuditoria logAuditoria : listAuditoria)
				{
					// Campo alterado que exige analise
					campoAlteradoAnexo = movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampoAnalise(logAuditoria.getNomeTabela(),
					    logAuditoria.getNomeCampo());

					// Se o campo existir
					if (campoAlteradoAnexo != null)
					{
						// Elimina o log se o valor novo eh nulo ou falso.
						if (logAuditoria.getValorNovo() != null && !logAuditoria.getValorNovo().equals("null") &&
						    !logAuditoria.getValorNovo().equals("false"))
						{
							logAuditoria.setSituacao(LogAuditoriaSituacaoEnum.ANALISE);
							retorno = true;
						}
					}
				}
			}
		}
		return retorno;
	}

	/**
	 * Carrega a lista de Anexos exigidos conforme campos alterados.
	 *
	 * @param movimentacaoPrestador
	 * @return list
	 */
	public List<MovimentacaoPrestadorAnexo> carregarListaCamposAnexo (final MovimentacaoPrestador movimentacaoPrestador)
	{
		final List<MovimentacaoPrestadorAnexo> movimentacaoPrestadorAnexosRetorno = new ArrayList<MovimentacaoPrestadorAnexo>();

		final List<MovimentacaoPrestadorAnexo> movimentacaoPrestadorAnexosNovos = new ArrayList<MovimentacaoPrestadorAnexo>();

		Map<LogAuditoria, MovimentacaoPrestadorCampo> mapCamposComAnexo = new HashMap<LogAuditoria, MovimentacaoPrestadorCampo>();

		try
		{
			if (movimentacaoPrestador != null)
			{

				/*
				 * Primeira parte:
				 * - Carrega lista dos logs de auditoria da Movimentacao
				 * - Carrega o campo alterado com base no log
				 * - Faz as verificacoes para nao duplicar os pedidos de anexo
				 * - Monta o Map para verificar anexos novos X anexos antigos
				 */
				final List<LogAuditoria> logAuditoriaList = logAuditoriaDao.findLogAuditoriaByPrestador(movimentacaoPrestador);

				if (Validator.isNotEmpty(logAuditoriaList))
				{
					MovimentacaoPrestadorCampo movimentacaoPrestadorCampo;

					for (final LogAuditoria logAuditoria : logAuditoriaList)
					{
						// Carrega o campo alterado que exige anexo
						movimentacaoPrestadorCampo = movimentacaoPrestadorCamposDao.pesquisarMovimentacaoPrestadorCampoAnexo(
						    logAuditoria.getNomeTabela(), logAuditoria.getNomeCampo());

						if (movimentacaoPrestadorCampo != null)
						{
							final Set<LogAuditoria> logkeys = mapCamposComAnexo.keySet();

							// Regra para Curriculo
							if (logAuditoria.getPrestadorCurriculo() != null)
							{
								// Regra a pedido da Unimed Curitiba Tarefa: 342777 (Incidentes)
								if (null == logAuditoria.getPrestadorCurriculo().getCurriculoPrestadorVO())
								{
									// Regra a pedido da Unimed Curitiba Issue UCWBACCF2-100 (Jira)
									if (!logAuditoria.getPrestadorCurriculo().getTipoCursoEnum().equals(TipoCursoEnum.GRADUACAO) &&
									    !logAuditoria.getPrestadorCurriculo().getTipoCursoEnum().equals(TipoCursoEnum.POS_GRADUACAO))
									{
										if (Validator.isNotEmpty(mapCamposComAnexo))
										{
											// Apresentar apenas um registro de anexo por curriculo
											if (!verificarExistenciaCurriculo(logAuditoria.getPrestadorCurriculo(), logkeys))
											{
												mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
											}
										}
										else
										{
											mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
										}
									}
								}
							}
							// Regra para Dependente
							else if (logAuditoria.getPrestadorDependente() != null)
							{
								if (Validator.isNotEmpty(mapCamposComAnexo))
								{
									// Apresentar apenas um registro de anexo por dependente
									if (!verificarExistenciaDependente(logAuditoria.getPrestadorDependente(), logkeys))
									{
										mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
									}
								}
								else
								{
									mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
								}
							}
							// Regra para Endereco
							else if (logAuditoria.getPrestadorEndereco() != null)
							{
								// Desconsiderar campos do endereco pessoal
								if (!(logAuditoria.getNomeCampo().equals(MovCadCooperadoConstants.CNES)) ||
								    !(logAuditoria.getPrestadorEndereco().getPessoal().equals("S")))
								{
									if (Validator.isNotEmpty(mapCamposComAnexo))
									{
										// Verifica se jah existe log de um determinado campo
										if (!verificarExistenciaEndereco(logAuditoria, logkeys))
										{
											mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
										}
									}
									else
									{
										mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
									}
								}
							}
							// Regra para Horario
							else if (logAuditoria.getPrestadorHorario() != null)
							{
								if (Validator.isNotEmpty(mapCamposComAnexo))
								{
									// Verifica se jah existe log de um determinado campo
									if (!verificarExistenciaHorario(logAuditoria, logkeys))
									{
										mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
									}
								}
								else
								{
									mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
								}
							}
							// Regra para Financeiro
							else if (logAuditoria.getPrestadorFinanceiro() != null)
							{
								if (Validator.isNotEmpty(mapCamposComAnexo))
								{
									// Apresentar apenas um registro de anexo para dados financeiros
									if (!verificarExistenciaFinanceiro(logAuditoria.getPrestadorFinanceiro(), logkeys))
									{
										mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
									}
								}
								else
								{
									mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
								}
							}
							// Regra para Especialidade
							else if (logAuditoria.getPrestadorEspecialidade() != null)
							{
								if (Validator.isNotEmpty(mapCamposComAnexo))
								{
									// Apresentar apenas um registro de anexo para dados da especialidade
									if (!verificarExistenciaEspecialidade(logAuditoria.getPrestadorEspecialidade(), logkeys))
									{
										mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
									}
								}
								else
								{
									mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
								}
							}
							// Regra para Prestador
							else
							{
								// Nesse ponto eh um campo direto do Prestador
								if (Validator.isNotEmpty(mapCamposComAnexo))
								{
									// Verifica se jah existe log de um determinado campo
									if (!verificarExistenciaLogCampo(logAuditoria, logkeys))
									{
										mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
									}
								}
								else
								{
									mapCamposComAnexo.put(logAuditoria, movimentacaoPrestadorCampo);
								}
							}
						}
					}

					/*
					 * Segunda parte:
					 * - Monta as listas de comparacao
					 * - Compara os logs
					 * - Monta a lista de retorno
					 */
					if (Validator.isNotEmpty(mapCamposComAnexo))
					{
						mapCamposComAnexo = limparAnexos(mapCamposComAnexo);

						MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo;

						// Cria a lista de anexos novos
						for (final Map.Entry<LogAuditoria, MovimentacaoPrestadorCampo> mapCampo : mapCamposComAnexo.entrySet())
						{
							movimentacaoPrestadorAnexo = gerarAnexo(movimentacaoPrestador, mapCampo);

							movimentacaoPrestadorAnexosNovos.add(movimentacaoPrestadorAnexo);
						}

						// Cria a lista de anexo antigos
						final List<MovimentacaoPrestadorAnexo> movimentacaoPrestadorAnexosAntigos = movimentacaoPrestadorAnexoDao.pesquisarMovimentacaoPrestadorAnexosUpload(
						    movimentacaoPrestador);

						if (Validator.isNotEmpty(movimentacaoPrestadorAnexosAntigos))
						{
							MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexoAntigo;

							// Laco para verifiar a existencia de um anexo novo na lista de anexos antigos
							for (final MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexoNovo : movimentacaoPrestadorAnexosNovos)
							{
								// Procura por anexo antigo
								movimentacaoPrestadorAnexoAntigo = verificarExistenciaAnexo(movimentacaoPrestadorAnexoNovo,
								    movimentacaoPrestadorAnexosAntigos);

								// Se existir, consideramos o antigo
								if (movimentacaoPrestadorAnexoAntigo != null)
								{
									movimentacaoPrestadorAnexosRetorno.add(movimentacaoPrestadorAnexoAntigo);
								}
								else
								{
									// Se nao, vai o novo anexo
									movimentacaoPrestadorAnexosRetorno.add(movimentacaoPrestadorAnexoNovo);
								}
							}
						}
						else
						{
							// Se nao existe anexos salvos/antigos...
							movimentacaoPrestadorAnexosRetorno.addAll(movimentacaoPrestadorAnexosNovos);
						}
					}
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorService.carregarListaCamposAnexo() - error:" + VExceptions.getErrorMessage(e), e);
			throw e;
		}

		return movimentacaoPrestadorAnexosRetorno;
	}

	/**
	 * Metodo para limpar os Anexos conforme regras gerais.
	 *
	 * @param mapCamposComAnexo
	 * @return
	 */
	private Map<LogAuditoria, MovimentacaoPrestadorCampo> limparAnexos (final Map<LogAuditoria, MovimentacaoPrestadorCampo> mapCamposComAnexo)
	{
		// Novo map
		final Map<LogAuditoria, MovimentacaoPrestadorCampo> mapCamposComAnexoNovo = new HashMap<LogAuditoria, MovimentacaoPrestadorCampo>();

		// Cria a lista de anexos novos
		for (final Map.Entry<LogAuditoria, MovimentacaoPrestadorCampo> mapCampo : mapCamposComAnexo.entrySet())
		{
			// Regra para remover do map, campos de checkbox desmarcados.
			if (mapCampo.getValue().getCampo().getClasse().intValue() != 4 || mapCampo.getKey().getValorNovo().equals("true"))
			{
				mapCamposComAnexoNovo.put(mapCampo.getKey(), mapCampo.getValue());
			}
		}
		return mapCamposComAnexoNovo;
	}

	/**
	 * Monta a infomacao referente ao campo modificado que sera apresentado na grid de Anexos.
	 *
	 * @param mapInfo
	 * @return String
	 */
	public String criarInformacao (final Map.Entry<LogAuditoria, MovimentacaoPrestadorCampo> mapInfo)
	{
		final StringBuilder info = new StringBuilder();
		// Regra para Curriculo
		if (mapInfo.getKey().getPrestadorCurriculo() != null)
		{
			info.append(mapInfo.getKey().getPrestadorCurriculo().getTipoCursoEnum().getDescricao());
			info.append("\r\n");
			info.append(mapInfo.getKey().getPrestadorCurriculo().getEntidadeEnsino().getDescricao());
			info.append("\r\n");
			info.append(mapInfo.getKey().getValorDescNovo());
		}
		// Regra para Dependente
		else if (mapInfo.getKey().getPrestadorDependente() != null)
		{
			info.append(mapInfo.getKey().getPrestadorDependente().getNome());
			info.append("\r\n");
			info.append(mapInfo.getKey().getValorDescNovo());
		}
		// Regra para Endereco
		else if (mapInfo.getKey().getPrestadorEndereco() != null)
		{
			info.append(mapInfo.getKey().getPrestadorEndereco().getCep());
			info.append("\r\n");
			info.append(mapInfo.getKey().getPrestadorEndereco().getBairro());
			info.append("\r\n");
			info.append(mapInfo.getKey().getValorDescNovo());
		}
		// Regra para Horario
		else if (mapInfo.getKey().getPrestadorHorario() != null)
		{
			info.append(mapInfo.getKey().getPrestadorHorario().getHoraInicial());
			info.append("\r\n");
			info.append(mapInfo.getKey().getPrestadorHorario().getHoraFinal());
			info.append("\r\n");
			info.append(mapInfo.getKey().getValorDescNovo());
		}
		// Regra para Financeiro
		else if (mapInfo.getKey().getPrestadorFinanceiro() != null)
		{
			info.append(mapInfo.getKey().getPrestadorFinanceiro().getBanco().getNome());
			info.append("\r\n");
			info.append(mapInfo.getKey().getPrestadorFinanceiro().getAgencia().getNome());
			info.append("\r\n");
			info.append(mapInfo.getKey().getPrestadorFinanceiro().getContaCorrente() + "-" + mapInfo.getKey().getPrestadorFinanceiro().getDv());
		}
		// Regra para Prestador
		else
		{
			info.append(mapInfo.getKey().getMovimentacaoPrestador().getNomePrestador());
			info.append("\r\n");
			info.append(mapInfo.getKey().getValorDescNovo());
		}
		return info.toString();
	}

	/**
	 * Gera a MovimentacaoPrestadorAnexo
	 *
	 * @param movimentacaoPrestador
	 * @param mapCampo
	 * @return MovimentacaoPrestadorAnexo
	 */
	private MovimentacaoPrestadorAnexo gerarAnexo (final MovimentacaoPrestador movimentacaoPrestador,
	    final Map.Entry<LogAuditoria, MovimentacaoPrestadorCampo> mapCampo)
	{
		final MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo = new MovimentacaoPrestadorAnexo();
		movimentacaoPrestadorAnexo.setCodigoLogAuditoria(mapCampo.getKey().getHandle());
		movimentacaoPrestadorAnexo.setMovimentacaoPrestador(movimentacaoPrestador);
		movimentacaoPrestadorAnexo.setMovimentacaoPrestadorCampo(mapCampo.getValue());
		movimentacaoPrestadorAnexo.setInformacaoCampoAnexo(criarInformacao(mapCampo));

		final StringBuilder tabelaCampoCodigo = new StringBuilder();

		switch (mapCampo.getValue().getTabelaMccNome())
		{
		case MovCadCooperadoConstants.TABELA_MOVIMENTACAO_PRESTADOR:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_MOVIMENTACAO_PRESTADOR);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getKey().getMovimentacaoPrestador().getId().toString());
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		case MovCadCooperadoConstants.TABELA_PRESTADOR_ENDERECO:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_PRESTADOR_ENDERECO);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(
			    mapCampo.getKey().getPrestadorEndereco() != null ? mapCampo.getKey().getPrestadorEndereco().getId().toString() : "");
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		case MovCadCooperadoConstants.TABELA_PRESTADOR_CURRICULO:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_PRESTADOR_CURRICULO);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(
			    mapCampo.getKey().getPrestadorCurriculo() != null ? mapCampo.getKey().getPrestadorCurriculo().getId().toString() : "");
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		case MovCadCooperadoConstants.TABELA_PRESTADOR_DEPENDENTE:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_PRESTADOR_DEPENDENTE);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(
			    mapCampo.getKey().getPrestadorDependente() != null ? mapCampo.getKey().getPrestadorDependente().getId().toString() : "");
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		case MovCadCooperadoConstants.TABELA_PRESTADOR_HORARIO:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_PRESTADOR_HORARIO);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(
			    mapCampo.getKey().getPrestadorHorario() != null ? mapCampo.getKey().getPrestadorHorario().getId().toString() : "");
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		case MovCadCooperadoConstants.TABELA_PRESTADOR_FINANCEIRO:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_PRESTADOR_FINANCEIRO);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(
			    mapCampo.getKey().getPrestadorFinanceiro() != null ? mapCampo.getKey().getPrestadorFinanceiro().getId().toString() : "");
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		case MovCadCooperadoConstants.TABELA_PRESTADOR_ESPECIALIDADE:
			tabelaCampoCodigo.append(MovCadCooperadoConstants.TABELA_PRESTADOR_ESPECIALIDADE);
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(mapCampo.getValue().getCampoNome());
			tabelaCampoCodigo.append("-");
			tabelaCampoCodigo.append(
			    mapCampo.getKey().getPrestadorEspecialidade() != null ? mapCampo.getKey().getPrestadorEspecialidade().getId().toString() : "");
			movimentacaoPrestadorAnexo.setTabelaCodigo(tabelaCampoCodigo.toString());
			break;
		default:
			break;
		}

		return movimentacaoPrestadorAnexo;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem logs de curriculo.
	 *
	 * @param curriculo
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaCurriculo (final MovimentacaoPrestadorCurriculo curriculo, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (curriculo.equals(logAuditoria.getPrestadorCurriculo()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem logs de dependentes.
	 *
	 * @param dependente
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaDependente (final MovimentacaoPrestadorDependente dependente, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (dependente.equals(logAuditoria.getPrestadorDependente()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem log de enderecos.
	 *
	 * @param log
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaEndereco (final LogAuditoria log, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (log.getNomeTabela().equals(logAuditoria.getNomeTabela()) && log.getNomeCampo().equals(logAuditoria.getNomeCampo()))
			{
				if (log.getPrestadorEndereco() != null && logAuditoria.getPrestadorEndereco() != null &&
				    log.getPrestadorEndereco().equals(logAuditoria.getPrestadorEndereco()))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem logs de dados financeiros.
	 *
	 * @param log
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaFinanceiro (final MovimentacaoPrestadorFinanceiro financeiro, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (financeiro.equals(logAuditoria.getPrestadorFinanceiro()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem logs de dados da especialidade.
	 *
	 * @param log
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaEspecialidade (final MovimentacaoPrestadorEspecialidade especialidade, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (especialidade.equals(logAuditoria.getPrestadorEspecialidade()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem logs de horarios.
	 *
	 * @param log
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaHorario (final LogAuditoria log, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (log.getNomeTabela().equals(logAuditoria.getNomeTabela()) && log.getNomeCampo().equals(logAuditoria.getNomeCampo()))
			{
				if (log.getPrestadorHorario() != null && logAuditoria.getPrestadorHorario() != null &&
				    log.getPrestadorHorario().equals(logAuditoria.getPrestadorHorario()))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Metodo auxiliar para verificar se jah existem logs de alteracao de um determinado campo.
	 *
	 * @param log
	 * @param logAuditorias
	 * @return boolean
	 */
	private boolean verificarExistenciaLogCampo (final LogAuditoria log, final Set<LogAuditoria> logAuditorias)
	{
		for (final LogAuditoria logAuditoria : logAuditorias)
		{
			if (log.getNomeTabela().equals(logAuditoria.getNomeTabela()) && log.getNomeCampo().equals(logAuditoria.getNomeCampo()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo para encontrar um registro de anexo jah salvo
	 *
	 * @param anexo
	 * @param prestadorAnexos
	 * @return MovimentacaoPrestadorAnexo
	 */
	private MovimentacaoPrestadorAnexo verificarExistenciaAnexo (final MovimentacaoPrestadorAnexo anexo,
	    final List<MovimentacaoPrestadorAnexo> prestadorAnexos)
	{
		for (final MovimentacaoPrestadorAnexo anx : prestadorAnexos)
		{
			// Tenta encontrar pelo log auditoria
			if (anexo.getCodigoLogAuditoria().equals(anx.getCodigoLogAuditoria()))
			{
				return anx;
			}

			// Tratamento expecifico para curriculos...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_CURRICULO) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_CURRICULO) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}

			// Tratamento expecifico para dependentes...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_DEPENDENTE) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_DEPENDENTE) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}

			// Tratamento expecifico para enderecos...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_ENDERECO) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_ENDERECO) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}

			// Tratamento expecifico para horarios...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_HORARIO) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_HORARIO) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}

			// Tratamento expecifico para financeiro...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_FINANCEIRO) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_FINANCEIRO) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}

			// Tratamento expecifico para especialidade...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_ESPECIALIDADE) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_PRESTADOR_ESPECIALIDADE) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}

			// Tratamento expecifico para prestador...
			if (anexo.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_MOVIMENTACAO_PRESTADOR) &&
			    anx.getTabelaCodigo().contains(MovCadCooperadoConstants.TABELA_MOVIMENTACAO_PRESTADOR) &&
			    anexo.getTabelaCodigo().equals(anx.getTabelaCodigo()))
			{
				return anx;
			}
		}
		return null;
	}

	/**
	 * Salva parcialmente a MovimentacaoPrestador na tabela Client(K_).
	 *
	 * @param movimentacaoPrestador
	 * @return MovimentacaoPrestador
	 * @throws DAOException
	 */
	public MovimentacaoPrestador salvarParcialMovimentacaoPrestador (MovimentacaoPrestador movimentacaoPrestador) throws DAOException
	{

		// Registra a Movimentacao como salva parcialmente.
		movimentacaoPrestador.setSituacao(SituacaoEnum.SALVO_PARCIALMENTE);
		movimentacaoPrestador.setDataAtualizacao(new Date());

		// Aplica determinadas regras a pedido da Unimed.
		movimentacaoPrestador = prepareMovimentacaoPrestador(movimentacaoPrestador);

		final List<LogAuditoria> logsAuditoria = createLogsAuditoria(movimentacaoPrestador);
		movimentacaoPrestador.setLogsAuditoria(logsAuditoria);

		try
		{
			movimentacaoPrestador = movimentacaoPrestadorDao.save(movimentacaoPrestador);
			LOG.info("MovimentacaoPrestadorService.savePartialMovimentacaoPrestador() - >>>>> Salvo Parcialmente <<<<<\n");
		}
		catch (final DAOException e)
		{
			LOG.error("MovimentacaoPrestadorService.savePartialMovimentacaoPrestador() - error:" + VExceptions.getErrorMessage(e), e);
			throw e;
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorService.savePartialMovimentacaoPrestador() - error:" + VExceptions.getErrorMessage(e), e);
			throw e;
		}

		return movimentacaoPrestador;
	}

	/**
	 * Prepara a Movimentacao Prestador e seus relacionamentos, conforme as regras informadas pela Unimed.
	 *
	 * @param movimentacaoPrestador
	 * @return MovimentacaoPrestador
	 */
	public MovimentacaoPrestador prepareMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		// Para endereco residencial, o cnes dever ser em branco (nulo), caso:
		if (movimentacaoPrestador.getEnderecoResidencial().getPessoal() == null &&
		    movimentacaoPrestador.getEnderecoResidencial().getAtendimento() == null)
		{
			movimentacaoPrestador.getEnderecoResidencial().setCnes(null);
			movimentacaoPrestador.getEnderecoResidencial().setPessoal(SimNaoEnum.SIM.getSigla());
			movimentacaoPrestador.getEnderecoResidencial().setAtendimento(SimNaoEnum.NAO.getSigla());
		}
		else if (movimentacaoPrestador.getEnderecoResidencial().getPessoal().equals(SimNaoEnum.SIM.getSigla()) &&
		    movimentacaoPrestador.getEnderecoResidencial().getAtendimento().equals(SimNaoEnum.NAO.getSigla()))
		{
			movimentacaoPrestador.getEnderecoResidencial().setCnes(null);
		}

		// Para os dados financeiros, acrescentar zeros a esquerda ateh completar 8 digitos.
		final StringBuilder contaCorrente = new StringBuilder();
		contaCorrente.append(movimentacaoPrestador.getDadosFinanceiros().getContaCorrente());

		if (movimentacaoPrestador.getDadosFinanceiros() != null && movimentacaoPrestador.getDadosFinanceiros().getContaCorrente() != null)
		{
			while (movimentacaoPrestador.getDadosFinanceiros().getContaCorrente().length() < MovCadCooperadoConstants.CONTA_CORRENTE_TAMANHO)
			{
				contaCorrente.insert(0, MovCadCooperadoConstants.ZERO);
				movimentacaoPrestador.getDadosFinanceiros().setContaCorrente(contaCorrente.toString());
			}
		}

		// Passar os dados do telefone celular do Prestador, para o Numero 2 do Endereco Residencial.
		// Solicitado pela Unimed a retirada dessa regra em 03/02/2017
		// movimentacaoPrestador.getEnderecoResidencial().setDdd2(
		// movimentacaoPrestador.getDddFoneCelular() != null ? movimentacaoPrestador.getDddFoneCelular() :
		// null);
		// movimentacaoPrestador.getEnderecoResidencial().setPrefixo2(
		// movimentacaoPrestador.getPrefixoFoneCelular() != null ?
		// movimentacaoPrestador.getPrefixoFoneCelular() : null);
		// movimentacaoPrestador.getEnderecoResidencial().setNumero2(
		// movimentacaoPrestador.getNumeroFoneCelular() != null ? movimentacaoPrestador.getNumeroFoneCelular()
		// : null);

		return movimentacaoPrestador;
	}

	/**
	 * Inicia o fluxo do sistema.
	 * 1 - Verifica a existencia do Prestador na tabela Client (K_MCC_MOVIMENTACAO_PRESTADOR) pelo seu codigo
	 * e situacao (Parcial).
	 * 2 - Se existir, retorna o objeto MovimentacaoPrestador para o Bean.
	 * 3 - Se não, busca na tabela view (V_SAM_PRESTADOR).
	 * 4 - Converte o VO para MovimentacaoPrestador.
	 * 5 - Carrega e vincula o Prestador, os Curriculos e o Endereco Residencial.
	 * 6 - Retorna a MovimentacaoPrestador para o Bean.
	 *
	 * @param prestadorVO
	 * @return MovimentacaoPrestador
	 */
	public MovimentacaoPrestador initMovimentacaoPrestador (final PrestadorVO prestador)
	{
		if (prestador != null)
		{
			LOG.info("initMovimentacaoPrestador -> begin: " + prestador.getCodigoPrestador());

			MovimentacaoPrestador movimentacaoPrestador;

			final PrestadorVO prestadorVO = prestadorDao.findByHandleFullLoad(prestador.getHandle());

			if (null == prestadorVO)
			{
				return null;
			}

			// Verifica existencia MovimentacaoPrestador por COUNT.
			final Long existeMovimentacaoPrestador = movimentacaoPrestadorDao.verificaExistenciaMovimentacaoPrestador(prestadorVO);

			// Se nao existir, realiza a carga atraves do VOs.
			if (existeMovimentacaoPrestador == null || existeMovimentacaoPrestador == 0)
			{
				movimentacaoPrestador = buildMovimentacaoPrestador(prestadorVO);
				movimentacaoPrestador.setDataInclusao(new Date());

				LOG.info("initMovimentacaoPrestador -> end: " + prestador.getCodigoPrestador());
				return movimentacaoPrestador;
			}

			// Se existir, realiza a pesquisa nas tabelas MCC com situacao em analise.
			movimentacaoPrestador = getMovimentacaoPrestador(prestadorVO, SituacaoEnum.ANALISE);

			if (movimentacaoPrestador != null)
			{
				LOG.info("initMovimentacaoPrestador -> end: " + prestador.getCodigoPrestador());
				return movimentacaoPrestador;
			}

			// Se nao existir MovimentacaoPrestador em Analise, procura a que esta salva parcialmente.
			movimentacaoPrestador = getMovimentacaoPrestador(prestadorVO, SituacaoEnum.SALVO_PARCIALMENTE);

			LOG.info("initMovimentacaoPrestador -> end: " + prestador.getCodigoPrestador());
			return movimentacaoPrestador;
		}
		return null;
	}

	/**
	 * Realiza a comparacao de movimentacao para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	private List<BeanDifference> compareMovimentacaoPrestador (final MovimentacaoPrestador movNovo, final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestador: " + movNovo.getId());

		final BeanComparator beanComparator = new BeanComparator();

		// Comparando os contratos.
		beanComparator.compare(movOriginal, movNovo);

		final List<BeanDifference> differences = beanComparator.getDifferences();

		LOG.info("end <- compareMovimentacaoPrestador: " + movNovo.getId());

		return differences;

	}

	/**
	 * Realiza a comparacao de dados financeiros para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	private Map<MovimentacaoPrestadorFinanceiro, List<BeanDifference>> compareMovimentacaoPrestadorFinanceiro (final MovimentacaoPrestador movNovo,
	    final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorFinanceiro: " + movNovo.getId());

		final Map<MovimentacaoPrestadorFinanceiro, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorFinanceiro, List<BeanDifference>>();

		final BeanComparator beanComparator = new BeanComparator();

		// Comparando os dados financeiros
		beanComparator.compare(movOriginal.getDadosFinanceiros(), movNovo.getDadosFinanceiros(),
		    messages.getMessageFromBundle("movimentacaoPrestador.dadosFinanceiros"));

		mapBeanDifference.put(movNovo.getDadosFinanceiros(), beanComparator.getDifferences());

		LOG.info("end <- compareMovimentacaoPrestadorFinanceiro: " + movNovo.getId());

		return mapBeanDifference;

	}

	/**
	 * Realiza a comparacao de endereco para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	private Map<MovimentacaoPrestadorEndereco, List<BeanDifference>> compareMovimentacaoPrestadorEndereco (final MovimentacaoPrestador movNovo,
	    final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorEndereco: " + movNovo.getId());

		final Map<MovimentacaoPrestadorEndereco, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorEndereco, List<BeanDifference>>();

		final BeanComparator beanComparator = new BeanComparator();

		// Comparando os enderecos.
		beanComparator.compare(movOriginal.getEnderecoResidencial(), movNovo.getEnderecoResidencial(),
		    messages.getMessageFromBundle("movimentacaoPrestador.enderecoResidencial"));

		mapBeanDifference.put(movNovo.getEnderecoResidencial(), beanComparator.getDifferences());

		LOG.info("end <- compareMovimentacaoPrestadorEndereco: " + movNovo.getId());

		return mapBeanDifference;

	}

	/**
	 * Realiza a comparacao de especialidades para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	private Map<MovimentacaoPrestadorEspecialidade, List<BeanDifference>> compareMovimentacaoPrestadorEspecialidade (
	    final MovimentacaoPrestador movNovo, final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorEspecialidade: " + movNovo.getId());

		final Map<MovimentacaoPrestadorEspecialidade, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorEspecialidade, List<BeanDifference>>();

		final BeanComparator beanComparator = new BeanComparator();

		// Comparando as especialidades.
		beanComparator.compare(movOriginal.getMovimentacaoPrestadorEspecialidade(), movNovo.getMovimentacaoPrestadorEspecialidade(),
		    messages.getMessageFromBundle("movimentacaoPrestadorEspecialidade.especialidade"));

		mapBeanDifference.put(movNovo.getMovimentacaoPrestadorEspecialidade(), beanComparator.getDifferences());

		LOG.info("end <- compareMovimentacaoPrestadorEspecialidade: " + movNovo.getId());

		return mapBeanDifference;

	}

	/**
	 * Realiza a comparacao de curriculos para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	public Map<MovimentacaoPrestadorCurriculo, List<BeanDifference>> compareMovimentacaoPrestadorCurriculo (final MovimentacaoPrestador movNovo,
	    final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorCurriculo: " + movNovo.getId());

		final Map<MovimentacaoPrestadorCurriculo, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorCurriculo, List<BeanDifference>>();

		// Comparando os curriculos.
		if (Validator.isNotEmpty(movNovo.getMovimentacaoPrestadorCurriculos()) &&
		    Validator.isNotEmpty(movOriginal.getMovimentacaoPrestadorCurriculos()))
		{
			// Para comparar pelos VOs.
			final Map<Long, MovimentacaoPrestadorCurriculo> mapMovNovoCompareVO = new HashMap<Long, MovimentacaoPrestadorCurriculo>();
			final Map<Long, MovimentacaoPrestadorCurriculo> mapMovOriginalCompareVO = new HashMap<Long, MovimentacaoPrestadorCurriculo>();

			// Para comparar pelos Ids.
			final Map<Long, MovimentacaoPrestadorCurriculo> mapMovNovoCompareId = new HashMap<Long, MovimentacaoPrestadorCurriculo>();
			final Map<Long, MovimentacaoPrestadorCurriculo> mapMovOriginalCompareId = new HashMap<Long, MovimentacaoPrestadorCurriculo>();

			// Monta os Maps dos registros novos.
			for (final MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo : movNovo.getMovimentacaoPrestadorCurriculos())
			{
				if (movimentacaoPrestadorCurriculo.getCurriculoPrestadorVO() != null)
				{
					mapMovNovoCompareVO.put(movimentacaoPrestadorCurriculo.getCurriculoPrestadorVO().getHandle(), movimentacaoPrestadorCurriculo);
				}
				else if (movimentacaoPrestadorCurriculo.getId() != null)
				{
					mapMovNovoCompareId.put(movimentacaoPrestadorCurriculo.getId(), movimentacaoPrestadorCurriculo);
				}
				else
				{
					final BeanComparator beanComparator = new BeanComparator();

					// Se nao tem VO relacionado e nem Id ainda, compara direto com um novo objeto.
					beanComparator.compare(new MovimentacaoPrestadorCurriculo(), movimentacaoPrestadorCurriculo);

					if (Validator.isNotEmpty(beanComparator.getDifferences()))
					{
						mapBeanDifference.put(movimentacaoPrestadorCurriculo, beanComparator.getDifferences());
					}
				}
			}

			// Monta o Maps dos registros originais.
			for (final MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo : movOriginal.getMovimentacaoPrestadorCurriculos())
			{
				if (movimentacaoPrestadorCurriculo.getCurriculoPrestadorVO() != null)
				{
					mapMovOriginalCompareVO.put(movimentacaoPrestadorCurriculo.getCurriculoPrestadorVO().getHandle(), movimentacaoPrestadorCurriculo);
				}
				else if (movimentacaoPrestadorCurriculo.getId() != null)
				{
					mapMovOriginalCompareId.put(movimentacaoPrestadorCurriculo.getId(), movimentacaoPrestadorCurriculo);
				}
			}

			// Executa a comparacao por VO.
			if (Validator.isNotEmpty(mapMovNovoCompareVO) && Validator.isNotEmpty(mapMovOriginalCompareVO))
			{
				for (final Map.Entry<Long, MovimentacaoPrestadorCurriculo> mapMovNovoElementVO : mapMovNovoCompareVO.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareVO.containsKey(mapMovNovoElementVO.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareVO.get(mapMovNovoElementVO.getKey()),
						    mapMovNovoCompareVO.get(mapMovNovoElementVO.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementVO.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}

			// Executa a comparacao por Id.
			if (Validator.isNotEmpty(mapMovNovoCompareId) && Validator.isNotEmpty(mapMovOriginalCompareId))
			{
				for (final Map.Entry<Long, MovimentacaoPrestadorCurriculo> mapMovNovoElementId : mapMovNovoCompareId.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareId.containsKey(mapMovNovoElementId.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareId.get(mapMovNovoElementId.getKey()),
						    mapMovNovoCompareId.get(mapMovNovoElementId.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementId.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}

		}
		else if (Validator.isNotEmpty(movNovo.getMovimentacaoPrestadorCurriculos()))
		{
			for (final MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo : movNovo.getMovimentacaoPrestadorCurriculos())
			{
				final BeanComparator beanComparator = new BeanComparator();

				// Nesse ponto ele nao tem VO relacionado e nem Id ainda, compara direto com um novo objeto.
				beanComparator.compare(new MovimentacaoPrestadorCurriculo(), movimentacaoPrestadorCurriculo);

				if (Validator.isNotEmpty(beanComparator.getDifferences()))
				{
					mapBeanDifference.put(movimentacaoPrestadorCurriculo, beanComparator.getDifferences());
				}
			}
		}

		LOG.info("end <- compareMovimentacaoPrestadorCurriculo: " + movNovo.getId());

		return mapBeanDifference;
	}

	/**
	 * Realiza a comparacao de enderecos de atendimento para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	public Map<MovimentacaoPrestadorEndereco, List<BeanDifference>> compareMovimentacaoPrestadorEnderecoAtendimento (
	    final MovimentacaoPrestador movNovo, final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorEnderecoAtendimento: " + movNovo.getId());

		final Map<MovimentacaoPrestadorEndereco, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorEndereco, List<BeanDifference>>();

		if (Validator.isNotEmpty(movNovo.getEnderecosAtendimento()) && Validator.isNotEmpty(movOriginal.getEnderecosAtendimento()))
		{
			// Para comparar pelos VOs.
			final Map<Long, MovimentacaoPrestadorEndereco> mapMovNovoCompareVO = new HashMap<Long, MovimentacaoPrestadorEndereco>();
			final Map<Long, MovimentacaoPrestadorEndereco> mapMovOriginalCompareVO = new HashMap<Long, MovimentacaoPrestadorEndereco>();

			// Para comparar pelos Ids.
			final Map<Long, MovimentacaoPrestadorEndereco> mapMovNovoCompareId = new HashMap<Long, MovimentacaoPrestadorEndereco>();
			final Map<Long, MovimentacaoPrestadorEndereco> mapMovOriginalCompareId = new HashMap<Long, MovimentacaoPrestadorEndereco>();

			// Monta os Maps dos registros novos.
			for (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento : movNovo.getEnderecosAtendimento())
			{
				if (movimentacaoPrestadorEnderecoAtendimento.getPrestadorEnderecoVO() != null)
				{
					mapMovNovoCompareVO.put(movimentacaoPrestadorEnderecoAtendimento.getPrestadorEnderecoVO().getHandle(),
					    movimentacaoPrestadorEnderecoAtendimento);
				}
				else if (movimentacaoPrestadorEnderecoAtendimento.getId() != null)
				{
					mapMovNovoCompareId.put(movimentacaoPrestadorEnderecoAtendimento.getId(), movimentacaoPrestadorEnderecoAtendimento);
				}
				else
				{
					final BeanComparator beanComparator = new BeanComparator();

					// Se nao tem VO relacionado e nem Id ainda, compara direto com um novo objeto.
					beanComparator.compare(new MovimentacaoPrestadorEndereco(), movimentacaoPrestadorEnderecoAtendimento);

					if (Validator.isNotEmpty(beanComparator.getDifferences()))
					{
						mapBeanDifference.put(movimentacaoPrestadorEnderecoAtendimento, beanComparator.getDifferences());
					}
				}
			}

			// Monta o Maps dos registros originais.
			for (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento : movOriginal.getEnderecosAtendimento())
			{
				if (movimentacaoPrestadorEnderecoAtendimento.getPrestadorEnderecoVO() != null)
				{
					mapMovOriginalCompareVO.put(movimentacaoPrestadorEnderecoAtendimento.getPrestadorEnderecoVO().getHandle(),
					    movimentacaoPrestadorEnderecoAtendimento);
				}
				else if (movimentacaoPrestadorEnderecoAtendimento.getId() != null)
				{
					mapMovOriginalCompareId.put(movimentacaoPrestadorEnderecoAtendimento.getId(), movimentacaoPrestadorEnderecoAtendimento);
				}
			}

			// Executa a comparacao por VO.
			if (Validator.isNotEmpty(mapMovNovoCompareVO) && Validator.isNotEmpty(mapMovOriginalCompareVO))
			{
				for (final Map.Entry<Long, MovimentacaoPrestadorEndereco> mapMovNovoElementVO : mapMovNovoCompareVO.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareVO.containsKey(mapMovNovoElementVO.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareVO.get(mapMovNovoElementVO.getKey()),
						    mapMovNovoCompareVO.get(mapMovNovoElementVO.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementVO.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}

			// Executa a comparacao por Id.
			if (Validator.isNotEmpty(mapMovNovoCompareId) && Validator.isNotEmpty(mapMovOriginalCompareId))
			{
				for (final Map.Entry<Long, MovimentacaoPrestadorEndereco> mapMovNovoElementId : mapMovNovoCompareId.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareId.containsKey(mapMovNovoElementId.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareId.get(mapMovNovoElementId.getKey()),
						    mapMovNovoCompareId.get(mapMovNovoElementId.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementId.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}
		}
		else if (Validator.isNotEmpty(movNovo.getEnderecosAtendimento()))
		{
			for (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento : movNovo.getEnderecosAtendimento())
			{
				final BeanComparator beanComparator = new BeanComparator();

				// Nesse ponto ele nao tem VO relacionado e nem Id ainda, compara direto com um novo objeto.
				beanComparator.compare(new MovimentacaoPrestadorEndereco(), movimentacaoPrestadorEnderecoAtendimento);

				if (Validator.isNotEmpty(beanComparator.getDifferences()))
				{
					mapBeanDifference.put(movimentacaoPrestadorEnderecoAtendimento, beanComparator.getDifferences());
				}
			}
		}

		LOG.info("end <- compareMovimentacaoPrestadorEnderecoAtendimento: " + movNovo.getId());

		return mapBeanDifference;
	}

	/**
	 * Realiza a comparacao de dependentes para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	private Map<MovimentacaoPrestadorDependente, List<BeanDifference>> compareMovimentacaoPrestadorDependente (final MovimentacaoPrestador movNovo,
	    final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorDependente: " + movNovo.getId());

		final Map<MovimentacaoPrestadorDependente, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorDependente, List<BeanDifference>>();

		// Comparando os dependentes.
		if (Validator.isNotEmpty(movNovo.getDependentes()) && Validator.isNotEmpty(movOriginal.getDependentes()))
		{
			// Para comparar pelos VOs.
			final Map<Long, MovimentacaoPrestadorDependente> mapMovNovoCompareVO = new HashMap<Long, MovimentacaoPrestadorDependente>();
			final Map<Long, MovimentacaoPrestadorDependente> mapMovOriginalCompareVO = new HashMap<Long, MovimentacaoPrestadorDependente>();

			// Para comparar pelos Ids.
			final Map<Long, MovimentacaoPrestadorDependente> mapMovNovoCompareId = new HashMap<Long, MovimentacaoPrestadorDependente>();
			final Map<Long, MovimentacaoPrestadorDependente> mapMovOriginalCompareId = new HashMap<Long, MovimentacaoPrestadorDependente>();

			// Monta os Maps dos registros novos.
			for (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente : movNovo.getDependentes())
			{
				if (movimentacaoPrestadorDependente.getDependentePrestadorVO() != null)
				{
					mapMovNovoCompareVO.put(movimentacaoPrestadorDependente.getDependentePrestadorVO().getHandle(), movimentacaoPrestadorDependente);
				}
				else if (movimentacaoPrestadorDependente.getId() != null)
				{
					mapMovNovoCompareId.put(movimentacaoPrestadorDependente.getId(), movimentacaoPrestadorDependente);
				}
				else
				{
					final BeanComparator beanComparator = new BeanComparator();

					// Se nao tem VO relacionado e nem Id ainda, compara direto com um novo objeto.
					beanComparator.compare(new MovimentacaoPrestadorDependente(), movimentacaoPrestadorDependente);

					if (Validator.isNotEmpty(beanComparator.getDifferences()))
					{
						mapBeanDifference.put(movimentacaoPrestadorDependente, beanComparator.getDifferences());
					}
				}
			}

			// Monta o Maps dos registros originais.
			for (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente : movOriginal.getDependentes())
			{
				if (movimentacaoPrestadorDependente.getDependentePrestadorVO() != null)
				{
					mapMovOriginalCompareVO.put(movimentacaoPrestadorDependente.getDependentePrestadorVO().getHandle(),
					    movimentacaoPrestadorDependente);
				}
				else if (movimentacaoPrestadorDependente.getId() != null)
				{
					mapMovOriginalCompareId.put(movimentacaoPrestadorDependente.getId(), movimentacaoPrestadorDependente);
				}
			}

			// Executa a comparacao por VO.
			if (Validator.isNotEmpty(mapMovNovoCompareVO) && Validator.isNotEmpty(mapMovOriginalCompareVO))
			{
				for (final Entry<Long, MovimentacaoPrestadorDependente> mapMovNovoElementVO : mapMovNovoCompareVO.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareVO.containsKey(mapMovNovoElementVO.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareVO.get(mapMovNovoElementVO.getKey()),
						    mapMovNovoCompareVO.get(mapMovNovoElementVO.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementVO.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}

			// Executa a comparacao por Id.
			if (Validator.isNotEmpty(mapMovNovoCompareId) && Validator.isNotEmpty(mapMovOriginalCompareId))
			{
				for (final Map.Entry<Long, MovimentacaoPrestadorDependente> mapMovNovoElementId : mapMovNovoCompareId.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareId.containsKey(mapMovNovoElementId.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareId.get(mapMovNovoElementId.getKey()),
						    mapMovNovoCompareId.get(mapMovNovoElementId.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementId.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}

		}
		else if (Validator.isNotEmpty(movNovo.getDependentes()))
		{
			for (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente : movNovo.getDependentes())
			{
				final BeanComparator beanComparator = new BeanComparator();

				// Nesse ponto ele nao tem VO relacionado e nem Id ainda, compara direto com um novo objeto.
				beanComparator.compare(new MovimentacaoPrestadorDependente(), movimentacaoPrestadorDependente);

				if (Validator.isNotEmpty(beanComparator.getDifferences()))
				{
					mapBeanDifference.put(movimentacaoPrestadorDependente, beanComparator.getDifferences());
				}
			}
		}

		LOG.info("end <- compareMovimentacaoPrestadorDependente: " + movNovo.getId());

		return mapBeanDifference;
	}

	/**
	 * Realiza a comparacao de horario de atendimento para geração do log de alteracao de campos
	 *
	 * @param movNovo
	 * @param movOriginal
	 * @return
	 */
	private Map<MovimentacaoPrestadorHorario, List<BeanDifference>> compareMovimentacaoPrestadorHorario (final MovimentacaoPrestador movNovo,
	    final MovimentacaoPrestador movOriginal)
	{
		LOG.info("begin -> compareMovimentacaoPrestadorHorario: " + movNovo.getId());

		final Map<MovimentacaoPrestadorHorario, List<BeanDifference>> mapBeanDifference = new HashMap<MovimentacaoPrestadorHorario, List<BeanDifference>>();

		if (Validator.isNotEmpty(movNovo.getEnderecosAtendimento()) && Validator.isNotEmpty(movOriginal.getEnderecosAtendimento()))
		{
			// Para comparar pelos VOs.
			final Map<Long, MovimentacaoPrestadorHorario> mapMovNovoCompareVO = new HashMap<Long, MovimentacaoPrestadorHorario>();
			final Map<Long, MovimentacaoPrestadorHorario> mapMovOriginalCompareVO = new HashMap<Long, MovimentacaoPrestadorHorario>();

			// Para comparar pelos Ids.
			final Map<Long, MovimentacaoPrestadorHorario> mapMovNovoCompareId = new HashMap<Long, MovimentacaoPrestadorHorario>();
			final Map<Long, MovimentacaoPrestadorHorario> mapMovOriginalCompareId = new HashMap<Long, MovimentacaoPrestadorHorario>();

			for (final MovimentacaoPrestadorEndereco enderecoAtendimentoNovo : movNovo.getEnderecosAtendimento())
			{
				if (Validator.isNotEmpty(enderecoAtendimentoNovo.getPrestadorHorarios()))
				{
					// Monta os Maps dos registros novos.
					for (final MovimentacaoPrestadorHorario horarioAtendimentoNovo : enderecoAtendimentoNovo.getPrestadorHorarios())
					{
						if (horarioAtendimentoNovo.getPrestadorHorarioVO() != null)
						{
							mapMovNovoCompareVO.put(horarioAtendimentoNovo.getPrestadorHorarioVO().getHandle(), horarioAtendimentoNovo);
						}
						else if (horarioAtendimentoNovo.getId() != null)
						{
							mapMovNovoCompareId.put(horarioAtendimentoNovo.getId(), horarioAtendimentoNovo);
						}
						else
						{
							final BeanComparator beanComparator = new BeanComparator();

							// Se nao tem VO relacionado e nem Id, compara direto com um novo objeto.
							beanComparator.compare(new MovimentacaoPrestadorHorario(), horarioAtendimentoNovo);

							if (Validator.isNotEmpty(beanComparator.getDifferences()))
							{
								mapBeanDifference.put(horarioAtendimentoNovo, beanComparator.getDifferences());
							}
						}
					}
				}
			}

			for (final MovimentacaoPrestadorEndereco enderecoAtendimentoOriginal : movOriginal.getEnderecosAtendimento())
			{
				if (Validator.isNotEmpty(enderecoAtendimentoOriginal.getPrestadorHorarios()))
				{
					// Monta os Maps dos registros originais.
					for (final MovimentacaoPrestadorHorario horarioAtendimentoOriginal : enderecoAtendimentoOriginal.getPrestadorHorarios())
					{
						if (horarioAtendimentoOriginal.getPrestadorHorarioVO() != null)
						{
							mapMovOriginalCompareVO.put(horarioAtendimentoOriginal.getPrestadorHorarioVO().getHandle(), horarioAtendimentoOriginal);
						}
						else if (horarioAtendimentoOriginal.getId() != null)
						{
							mapMovOriginalCompareId.put(horarioAtendimentoOriginal.getId(), horarioAtendimentoOriginal);
						}
					}
				}
			}

			// Executa a comparacao por VO.
			if (Validator.isNotEmpty(mapMovNovoCompareVO) && Validator.isNotEmpty(mapMovOriginalCompareVO))
			{
				for (final Entry<Long, MovimentacaoPrestadorHorario> mapMovNovoElementVO : mapMovNovoCompareVO.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareVO.containsKey(mapMovNovoElementVO.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareVO.get(mapMovNovoElementVO.getKey()),
						    mapMovNovoCompareVO.get(mapMovNovoElementVO.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementVO.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}

			// Executa a comparacao por Id.
			if (Validator.isNotEmpty(mapMovNovoCompareId) && Validator.isNotEmpty(mapMovOriginalCompareId))
			{
				for (final Map.Entry<Long, MovimentacaoPrestadorHorario> mapMovNovoElementId : mapMovNovoCompareId.entrySet())
				{
					// Se as chaves existirem, eh gerada a comparacao.
					if (mapMovOriginalCompareId.containsKey(mapMovNovoElementId.getKey()))
					{
						final BeanComparator beanComparator = new BeanComparator();

						beanComparator.compare(mapMovOriginalCompareId.get(mapMovNovoElementId.getKey()),
						    mapMovNovoCompareId.get(mapMovNovoElementId.getKey()));

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(mapMovNovoElementId.getValue(), beanComparator.getDifferences());
						}
					}
				}
			}
		}
		else if (Validator.isNotEmpty(movNovo.getEnderecosAtendimento()))
		{
			for (final MovimentacaoPrestadorEndereco enderecoAtendimentoNovo : movNovo.getEnderecosAtendimento())
			{
				if (Validator.isNotEmpty(enderecoAtendimentoNovo.getPrestadorHorarios()))
				{
					for (final MovimentacaoPrestadorHorario horarioAtendimento : enderecoAtendimentoNovo.getPrestadorHorarios())
					{
						final BeanComparator beanComparator = new BeanComparator();

						// Nesse ponto ele nao tem VO relacionado e nem Id, compara direto com um novo objeto.
						beanComparator.compare(new MovimentacaoPrestadorHorario(), horarioAtendimento);

						if (Validator.isNotEmpty(beanComparator.getDifferences()))
						{
							mapBeanDifference.put(horarioAtendimento, beanComparator.getDifferences());
						}
					}
				}
			}
		}

		LOG.info("end <- compareMovimentacaoPrestadorHorario: " + movNovo.getId());

		return mapBeanDifference;
	}

	/**
	 * Cria os registro de Log de auditoria do contrato
	 *
	 * @param movimentacao
	 * @param remoteHost
	 */
	public List<LogAuditoria> createLogsAuditoria (final MovimentacaoPrestador movimentacao)
	{
		final List<LogAuditoria> logs = new ArrayList<LogAuditoria>();

		// Pesquisa MovimentacaoPrestador local, na tabela MCC.
		MovimentacaoPrestador movOriginal = getMovimentacaoPrestador(movimentacao.getPrestador(), SituacaoEnum.SALVO_PARCIALMENTE);

		// Se nao encontrar, monta a MovimentacaoPrestador atraves dos VOs (tabelas Views).
		if (movOriginal == null)
		{
			movOriginal = buildMovimentacaoPrestador(movimentacao.getPrestador());
		}

		// Realiza a comparacao da MovimentacaoPrestador.
		final List<BeanDifference> differencesPrestador = compareMovimentacaoPrestador(movimentacao, movOriginal);

		logs.addAll(populateLogs(movimentacao, differencesPrestador));

		// Realiza a comparacao da MovimentacaoPrestadorFinanceiro.
		final Map<MovimentacaoPrestadorFinanceiro, List<BeanDifference>> mapMovPrestadorFinanceiro = compareMovimentacaoPrestadorFinanceiro(
		    movimentacao, movOriginal);

		if (Validator.isNotEmpty(mapMovPrestadorFinanceiro))
		{
			for (final Map.Entry<MovimentacaoPrestadorFinanceiro, List<BeanDifference>> mapBeanDifference : mapMovPrestadorFinanceiro.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{
					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		// Realiza a comparacao da MovimentacaoPrestadorEndereco.
		final Map<MovimentacaoPrestadorEndereco, List<BeanDifference>> mapMovPrestadorEndereco = compareMovimentacaoPrestadorEndereco(movimentacao,
		    movOriginal);

		if (Validator.isNotEmpty(mapMovPrestadorEndereco))
		{
			for (final Map.Entry<MovimentacaoPrestadorEndereco, List<BeanDifference>> mapBeanDifference : mapMovPrestadorEndereco.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{
					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		// Realiza a comparacao das MovimentacaoPrestadorCurriculo.
		final Map<MovimentacaoPrestadorCurriculo, List<BeanDifference>> mapMovPrestadorCurriculo = compareMovimentacaoPrestadorCurriculo(movimentacao,
		    movOriginal);

		if (Validator.isNotEmpty(mapMovPrestadorCurriculo))
		{
			for (final Map.Entry<MovimentacaoPrestadorCurriculo, List<BeanDifference>> mapBeanDifference : mapMovPrestadorCurriculo.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{
					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		// Realiza a comparacao da MovimentacaoPrestadorEspecialidade
		final Map<MovimentacaoPrestadorEspecialidade, List<BeanDifference>> mapMovPrestadorEspecialidade = compareMovimentacaoPrestadorEspecialidade(
		    movimentacao, movOriginal);

		if (Validator.isNotEmpty(mapMovPrestadorEspecialidade))
		{
			for (final Map.Entry<MovimentacaoPrestadorEspecialidade, List<BeanDifference>> mapBeanDifference : mapMovPrestadorEspecialidade.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{
					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		// Realiza a comparacao das MovimentacaoPrestadorDependente.
		final Map<MovimentacaoPrestadorDependente, List<BeanDifference>> mapMovPrestadorDependente = compareMovimentacaoPrestadorDependente(
		    movimentacao, movOriginal);
		if (Validator.isNotEmpty(mapMovPrestadorDependente))
		{
			for (final Map.Entry<MovimentacaoPrestadorDependente, List<BeanDifference>> mapBeanDifference : mapMovPrestadorDependente.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{
					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		// Realiza a comparacao das MovimentacaoPrestadorEndereco de atendimento.
		final Map<MovimentacaoPrestadorEndereco, List<BeanDifference>> mapMovPrestadorEnderecoAtendimento = compareMovimentacaoPrestadorEnderecoAtendimento(
		    movimentacao, movOriginal);

		if (Validator.isNotEmpty(mapMovPrestadorEnderecoAtendimento))
		{
			for (final Map.Entry<MovimentacaoPrestadorEndereco, List<BeanDifference>> mapBeanDifference : mapMovPrestadorEnderecoAtendimento.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{
					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		// Realiza a comparacao das MovimentacaoPrestadorEndereco de atendimento.
		final Map<MovimentacaoPrestadorHorario, List<BeanDifference>> mapMovPrestadorHorarioAtendimento = compareMovimentacaoPrestadorHorario(
		    movimentacao, movOriginal);

		if (Validator.isNotEmpty(mapMovPrestadorHorarioAtendimento))
		{
			for (final Map.Entry<MovimentacaoPrestadorHorario, List<BeanDifference>> mapBeanDifference : mapMovPrestadorHorarioAtendimento.entrySet())
			{
				if (Validator.isNotEmpty(mapBeanDifference.getValue()))
				{

					logs.addAll(populateLogs(mapBeanDifference.getKey(), mapBeanDifference.getValue()));
				}
			}
		}

		return logs;
	}

	/**
	 * Popula a lista de LogAuditoria para setar na MovimentacaoPrestador.
	 *
	 * @param entity
	 * @param differences
	 * @return
	 */
	public List<LogAuditoria> populateLogs (final Serializable entity, final List<BeanDifference> differences)
	{
		final List<LogAuditoria> logs = new ArrayList<LogAuditoria>();

		if (!Validator.isEmpty(differences))
		{
			for (final BeanDifference diff : differences)
			{
				final LogAuditoria log = new LogAuditoria();

				if (entity instanceof MovimentacaoPrestador)
				{
					log.setMovimentacaoPrestador((MovimentacaoPrestador)entity);
				}
				else if (entity instanceof MovimentacaoPrestadorEndereco)
				{
					log.setMovimentacaoPrestador(((MovimentacaoPrestadorEndereco)entity).getMovimentacaoPrestador());
					log.setPrestadorEndereco((MovimentacaoPrestadorEndereco)entity);
				}
				else if (entity instanceof MovimentacaoPrestadorCurriculo)
				{
					log.setMovimentacaoPrestador(((MovimentacaoPrestadorCurriculo)entity).getMovimentacaoPrestador());
					log.setPrestadorCurriculo((MovimentacaoPrestadorCurriculo)entity);
				}
				else if (entity instanceof MovimentacaoPrestadorDependente)
				{
					log.setMovimentacaoPrestador(((MovimentacaoPrestadorDependente)entity).getMovimentacaoPrestador());
					log.setPrestadorDependente((MovimentacaoPrestadorDependente)entity);
				}
				else if (entity instanceof MovimentacaoPrestadorHorario)
				{
					log.setMovimentacaoPrestador(
					    ((MovimentacaoPrestadorHorario)entity).getMovimentacaoPrestadorEndereco().getMovimentacaoPrestador());
					log.setPrestadorHorario((MovimentacaoPrestadorHorario)entity);
					log.setPrestadorEndereco(((MovimentacaoPrestadorHorario)entity).getMovimentacaoPrestadorEndereco());
				}
				else if (entity instanceof MovimentacaoPrestadorFinanceiro)
				{
					log.setMovimentacaoPrestador(((MovimentacaoPrestadorFinanceiro)entity).getMovimentacaoPrestador());
					log.setPrestadorFinanceiro((MovimentacaoPrestadorFinanceiro)entity);
				}
				else if (entity instanceof MovimentacaoPrestadorEspecialidade)
				{
					log.setMovimentacaoPrestador(((MovimentacaoPrestadorEspecialidade)entity).getMovimentacaoPrestador());
					log.setPrestadorEspecialidade((MovimentacaoPrestadorEspecialidade)entity);
				}

				log.setCampoAlterado(diff.getProperty());

				log.setDataHoraAlteracao(CustomDate.getCurrentDate());

				log.setNomeTabela(StringUtils.upperCase(diff.getTableName()));

				log.setNomeCampo(StringUtils.upperCase(diff.getColumnName()));

				tratarCamposDescritivos(diff, log);

				log.setTipoOperacao(diff.getTypeOperation());

				logs.add(log);
			}
		}
		return logs;
	}

	private void tratarCamposDescritivos (final BeanDifference diff, final LogAuditoria log)
	{
		if (diff.getProperty() != null && diff.getColumnName().equals(MovCadCooperadoConstants.HORA_INICIO))
		{
			// Formata o valor do horario inicial de atendimento.
			log.setValorDescNovo(String.valueOf(diff.getNewValue().toString().subSequence(11, 16)).trim());
			log.setValorNovo(String.valueOf(diff.getNewValue().toString().subSequence(11, 16)).trim());

			if (diff.getOldValue() != null)
			{
				if (diff.getOldValue().toString().length() > 8)
				{
					log.setValorDescOriginal(String.valueOf(diff.getOldValue().toString().subSequence(11, 16)).trim());
					log.setValorOriginal(String.valueOf(diff.getOldValue().toString().subSequence(11, 16)).trim());
				}
				else
				{
					log.setValorDescOriginal(String.valueOf(diff.getOldValue().toString().trim()));
					log.setValorOriginal(String.valueOf(diff.getOldValue().toString().trim()));
				}
			}
			else
			{
				log.setValorDescOriginal(String.valueOf(diff.getOldValueFormatted()));
				if (diff.getOldValue() != null && diff.getOldValue() instanceof VEntity)
				{
					log.setValorOriginal(String.valueOf(((VEntity<?>)diff.getOldValue()).getPkValue()));
				}
				else
				{
					log.setValorOriginal(String.valueOf(diff.getOldValue()));
				}
			}
		}
		else if (diff.getProperty() != null && diff.getColumnName().equals(MovCadCooperadoConstants.HORA_FIM))
		{
			// Formata o valor do horario final de atendimento.
			log.setValorDescNovo(String.valueOf(diff.getNewValue().toString().subSequence(11, 16)).trim());
			log.setValorNovo(String.valueOf(diff.getNewValue().toString().subSequence(11, 16)).trim());

			if (diff.getOldValue() != null)
			{
				if (diff.getOldValue().toString().length() > 8)
				{
					log.setValorDescOriginal(String.valueOf(diff.getOldValue().toString().subSequence(11, 16)).trim());
					log.setValorOriginal(String.valueOf(diff.getOldValue().toString().subSequence(11, 16)).trim());
				}
				else
				{
					log.setValorDescOriginal(String.valueOf(diff.getOldValue().toString().trim()));
					log.setValorOriginal(String.valueOf(diff.getOldValue().toString().trim()));
				}
			}
			else
			{
				log.setValorDescOriginal(String.valueOf(diff.getOldValueFormatted()));
				if (diff.getOldValue() != null && diff.getOldValue() instanceof VEntity)
				{
					log.setValorOriginal(String.valueOf(((VEntity<?>)diff.getOldValue()).getPkValue()));
				}
				else
				{
					log.setValorOriginal(String.valueOf(diff.getOldValue()));
				}
			}
		}
		else if (diff.getProperty() != null && diff.getColumnName().equals(MovCadCooperadoConstants.AGENCIA))
		{
			// Formata os valores descritivos, para aparecer o numero e nome da agencia.
			if (diff.getNewValue() != null && diff.getNewValue() instanceof VEntity)
			{
				// pesquidsar a agencia nova.
				final AgenciaVO agenciaVO = agenciaDao.findById(Long.valueOf(((VEntity<?>)diff.getNewValue()).getPkValue().toString()));
				final String agencia = agenciaVO != null ? agenciaVO.getAgencia() : "";

				log.setValorNovo(String.valueOf(((VEntity<?>)diff.getNewValue()).getPkValue()));
				log.setValorDescNovo(agencia + " - " + String.valueOf(diff.getNewValueFormatted()));
			}
			else
			{
				log.setValorNovo(String.valueOf(diff.getNewValue()));
				log.setValorDescNovo(String.valueOf(diff.getNewValueFormatted()));
			}

			if (diff.getOldValue() != null && diff.getOldValue() instanceof VEntity)
			{
				// pesquisa a agencia antiga.
				final AgenciaVO agenciaVO = agenciaDao.findById(Long.valueOf(((VEntity<?>)diff.getOldValue()).getPkValue().toString()));
				final String agencia = agenciaVO != null ? agenciaVO.getAgencia() : "";

				log.setValorOriginal(String.valueOf(((VEntity<?>)diff.getOldValue()).getPkValue()));
				log.setValorDescOriginal(agencia + " - " + String.valueOf(diff.getOldValueFormatted()));
			}
			else
			{
				log.setValorOriginal(String.valueOf(diff.getOldValue()));
				log.setValorDescOriginal(String.valueOf(diff.getOldValueFormatted()));
			}
		}
		else
		{
			log.setValorDescNovo(String.valueOf(diff.getNewValueFormatted()));
			log.setValorDescOriginal(String.valueOf(diff.getOldValueFormatted()));

			if (diff.getNewValue() != null && diff.getNewValue() instanceof VEntity)
			{
				log.setValorNovo(String.valueOf(((VEntity<?>)diff.getNewValue()).getPkValue()));
			}
			else
			{
				log.setValorNovo(String.valueOf(diff.getNewValue()));
			}

			if (diff.getOldValue() != null && diff.getOldValue() instanceof VEntity)
			{
				log.setValorOriginal(String.valueOf(((VEntity<?>)diff.getOldValue()).getPkValue()));
			}
			else
			{
				log.setValorOriginal(String.valueOf(diff.getOldValue()));
			}
		}
	}

	/**
	 * Carrega o Map de MovimentacaoPrestadorCampo para a validacao dos required dos componentes de front
	 *
	 * @return
	 */
	public Map<String, Map<String, MovimentacaoPrestadorCampo>> carregarMapMovimentacaoPrestadorCampo ()
	{
		final Map<String, Map<String, MovimentacaoPrestadorCampo>> mapTabelas = new HashMap<>();

		Map<String, MovimentacaoPrestadorCampo> mapCampos;

		for (final MovimentacaoPrestadorCampo campo : movimentacaoPrestadorCamposDao.getAll())
		{

			if (mapTabelas.containsKey(campo.getTabela().getNome()))
			{
				mapTabelas.get(campo.getTabela().getNome()).put(campo.getCampoNome(), campo);
			}
			else
			{
				mapCampos = new HashMap<>();
				mapCampos.put(campo.getCampoNome(), campo);
				mapTabelas.put(campo.getTabela().getNome(), mapCampos);
			}
		}

		return mapTabelas;
	}

	/**
	 * Pesquisa se a Movimentacao já foi concluida, e retorna a data da integracao.
	 */
	public Date verificaMaiorDataIntegracao (final PrestadorVO prestadorVO)
	{
		return movimentacaoPrestadorDao.verificaMaiorDataIntegracao(prestadorVO);
	}
}
