package br.com.unimedcuritiba.movcad.cooperado.web.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import br.com.unimedcuritiba.benner.dao.AgenciaDao;
import br.com.unimedcuritiba.benner.dao.AreaCursoDao;
import br.com.unimedcuritiba.benner.dao.BancoDao;
import br.com.unimedcuritiba.benner.dao.EntidadeEnsinoDao;
import br.com.unimedcuritiba.benner.dao.EspecialidadeDao;
import br.com.unimedcuritiba.benner.dao.EstadoCivilDao;
import br.com.unimedcuritiba.benner.dao.EstadosDao;
import br.com.unimedcuritiba.benner.dao.LogradouroDao;
import br.com.unimedcuritiba.benner.dao.MunicipiosDao;
import br.com.unimedcuritiba.benner.dao.PaisesDao;
import br.com.unimedcuritiba.benner.dao.TipoDependenteDao;
import br.com.unimedcuritiba.benner.dao.TipoLogradouroDao;
import br.com.unimedcuritiba.benner.vo.AgenciaVO;
import br.com.unimedcuritiba.benner.vo.AreaCursoVO;
import br.com.unimedcuritiba.benner.vo.BancoVO;
import br.com.unimedcuritiba.benner.vo.EntidadeEnsinoVO;
import br.com.unimedcuritiba.benner.vo.EspecialidadeVO;
import br.com.unimedcuritiba.benner.vo.EstadoCivilVO;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.LogradouroVO;
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.unimedcuritiba.benner.vo.PaisesVO;
import br.com.unimedcuritiba.benner.vo.TipoDependenteVO;
import br.com.unimedcuritiba.benner.vo.TipoLogradouroVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorAnexo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCampo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCurriculo;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorDependente;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEspecialidade;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorFinanceiro;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorHorario;
import br.com.unimedcuritiba.movcad.cooperado.enums.CategoriaCnhEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.EstadoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.RNECondicaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.RacaCorEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SexoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SimNaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoCursoEnum;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;
import br.com.unimedcuritiba.movcad.cooperado.web.service.LogAuditoriaService;
import br.com.unimedcuritiba.movcad.cooperado.web.service.MovimentacaoPrestadorAnexoService;
import br.com.unimedcuritiba.movcad.cooperado.web.service.MovimentacaoPrestadorDependenteService;
import br.com.unimedcuritiba.movcad.cooperado.web.service.MovimentacaoPrestadorEnderecoService;
import br.com.unimedcuritiba.movcad.cooperado.web.service.MovimentacaoPrestadorHorarioService;
import br.com.unimedcuritiba.movcad.cooperado.web.service.MovimentacaoPrestadorService;
import br.com.unimedcuritiba.movcad.cooperado.web.util.Util;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.Crud;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.util.CustomDate;
import br.com.visionnaire.core.util.FileUtil;
import br.com.visionnaire.core.util.SortTool;
import br.com.visionnaire.core.view.AbstractCrudView;

/**
 * @author Tiago Henrique Gomes da Silva Balduino
 * @since 27 de jun de 2016
 */
@ManagedBean
@SessionScoped
public class MovimentacaoPrestadorBean
    extends AbstractCrudView<MovimentacaoPrestador, Long>
{

	private static final long serialVersionUID = 8765925419483970785L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorBean.class);

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@EJB
	private LogAuditoriaService logAuditoriaService;

	@EJB
	private MovimentacaoPrestadorService movimentacaoPrestadorService;

	@EJB
	private MovimentacaoPrestadorAnexoService movimentacaoPrestadorAnexoService;

	@EJB
	private MovimentacaoPrestadorHorarioService movimentacaoPrestadorHorarioService;

	@EJB
	private MovimentacaoPrestadorEnderecoService movimentacaoPrestadorEnderecoService;

	@EJB
	private MovimentacaoPrestadorDependenteService movimentacaoPrestadorDependenteService;

	@EJB
	private MovimentacaoPrestadorPreCadastroDao movimentacaoPreCadastroDao;

	@EJB
	private EstadosDao estadosDao;

	@EJB
	private MunicipiosDao municipiosDao;

	@EJB
	private PaisesDao paisesDao;

	@EJB
	private EstadoCivilDao estadoCivilDao;

	@EJB
	private AreaCursoDao areaCursoDao;

	@EJB
	private TipoLogradouroDao tipoLogradouroDao;

	@EJB
	private TipoDependenteDao tipoDependenteDao;

	@Inject
	private Messages messages;

	@EJB
	private EntidadeEnsinoDao entidadeEnsinoDao;

	@EJB
	private LogradouroDao logradouroDao;

	@EJB
	private BancoDao bancoDao;

	@EJB
	private EspecialidadeDao especialidadeDao;

	@EJB
	private AgenciaDao agenciaDao;

	private List<SexoEnum> sexoList;

	private List<EstadoEnum> estadosOrgaoEmissorRG;

	private List<RacaCorEnum> racaCorEnumList;

	private List<TipoCursoEnum> tipoCursoList;

	private List<RNECondicaoEnum> rneCondicaoList;

	private List<CategoriaCnhEnum> categoriaCnhList;

	private List<TipoDependenteVO> tipoDependenteList;

	private List<BancoVO> bancosList;

	private List<EspecialidadeVO> especialidadeList;

	private List<PaisesVO> paisesList;

	private List<EstadosVO> estadosList;

	private List<AgenciaVO> agenciasList;

	private List<AreaCursoVO> areaCursoList;

	private List<MunicipiosVO> municipiosList;

	private List<LogradouroVO> logradourosList;

	private List<EstadoCivilVO> estadoCivilList;

	private List<TipoLogradouroVO> tipoLogradouroList;

	private List<EntidadeEnsinoVO> entidadeEnsinoList;

	private List<MovimentacaoPrestadorDependente> movimentacaoPrestadorDependenteList;

	private List<MovimentacaoPrestadorAnexo> movimentacaoPrestadorAnexoList;

	private List<MovimentacaoPrestadorEndereco> movimentacaoPrestadorEnderecoAtendimentoList;

	private List<MovimentacaoPrestadorHorario> movimentacaoPrestadorHorarioList;

	private MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo;

	private MovimentacaoPrestadorDependente movimentacaoPrestadorDependente;

	private MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo;

	private MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento;

	private MovimentacaoPrestadorHorario movimentacaoPrestadorHorario;

	private MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade;

	private EstadosVO estadoVerificador;

	private LogradouroVO logradouroItem;

	private boolean showHiddenConcluir;

	private boolean habilitaEstadoEndereco;

	private boolean cepGeral;

	private boolean avancar;

	private boolean enderecoAtendimento;

	private boolean desabilitarDiaHorarioAtendimento;

	private boolean renderizarCheckBoxDomingo;

	private boolean renderizarCheckBoxSegunda;

	private boolean renderizarCheckBoxTerca;

	private boolean renderizarCheckBoxQuarta;

	private boolean renderizarCheckBoxQuinta;

	private boolean renderizarCheckBoxSexta;

	private boolean renderizarCheckBoxSabado;

	private String cepPesquisa;

	private String ultimaMovimentacao;

	private String currentStep;

	private String textoMensagemGenerica;

	private String textoIntrodutorio;

	private String agenciaAux;

	private UploadedFile file;

	private String cpfDependente;

	private Map<String, Map<String, MovimentacaoPrestadorCampo>> mapMovimentacaoPrestadorCampo;

	private ResourceBundle properties;

	private boolean status = true;

	private final boolean removeBanco = false;

	public static final String SUCCESS_PAGE = "/pages/success.xhtml";

	@Override
	protected Crud<MovimentacaoPrestador, Long> getCrudService ()
	{
		return movimentacaoPrestadorService;
	}

	@PostConstruct
	@Override
	public void init ()
	{
		super.init();

		textoMensagemGenerica = "";

		if (loginBean != null && loginBean.getPrestador() != null)
		{
			setEditEntity(movimentacaoPrestadorService.initMovimentacaoPrestador(loginBean.getPrestador()));

			aplicaRegraOrgaoEmissor();
			montaUltimaMovimentacaoTexto();
		}

		// Verificacao para evitar null pointer em registros antigos
		if (getEditEntity() != null)
		{
			if (getEditEntity().getDadosFinanceiros() == null)
			{
				getEditEntity().setDadosFinanceiros(new MovimentacaoPrestadorFinanceiro());
			}
			else
			{
				if (getEditEntity().getDadosFinanceiros().getAgencia() != null)
				{
					if (getEditEntity().getDadosFinanceiros().getAgencia().getAgencia() != null)
					{
						agenciaAux = getEditEntity().getDadosFinanceiros().getAgencia().getAgencia();
					}
				}
			}

		}

		if (currentStep == null)
		{
			currentStep = "dadosPessoais";
		}

		// Preparando fluxo e componentes para aba Dados de Atendimento.
		setEnderecoAtendimento(false);
		iniciandoValoresHorariosAtendimento();

		// usado para habilitar o estado e municipio caso não encontre na base.
		// renderizaValidaEndereco();

		// Carrega arquivo de propriedades.
		properties = ResourceBundle.getBundle("AppMessages");

		// Verifica se o Cooperado jah atualizou seu cadastro (se existe algum registro jah concluido...).
		final MovimentacaoPrestador movimentacaoPrestador = movimentacaoPrestadorService.getMovimentacaoPrestadorJahAtualizado(
		    getEditEntity().getPrestador(), SituacaoEnum.CONCLUIDO);

		// Se movimentacaoPrestador == null, ele ainda nao foi atualizado.
		if (movimentacaoPrestador == null && getEditEntity() != null && getEditEntity().getPrestador() != null)
		{
			// Pesquisa na tabela de Pre Cadastro, se existir true, senao false.
			final Boolean preCadastro = movimentacaoPreCadastroDao.isPreCadastro(getEditEntity().getPrestador());

			getEditEntity().setPreCadastro(preCadastro);

			// Se for derivado do Pre Cadastro, desmarca a flag de Atendimento, e limpa os Enderecos.
			if (preCadastro)
			{
				getEditEntity().getEnderecoResidencial().setAtendimento(SimNaoEnum.NAO.getSigla());

				// Verifica se nao esta salvo parcialmente (digitacao).
				if (getEditEntity().getSituacao() == null)
				{
					// Gera uma lista vazia.
					getEditEntity().setEnderecosAtendimento(new ArrayList<MovimentacaoPrestadorEndereco>());
				}
			}
		}
		else
		{
			getEditEntity().setPreCadastro(false);
		}

		// Carrega as listas e map
		iniciarListaDependentes();
		iniciarListaEnderecoAtendimento();
		iniciarListaAnexos();
		iniciarMapMovimentacaoPrestadorCampo();

	}

	/**
	 * Verifica o genero do Cooperado para montar a mensagem de "Boas vindas..."
	 *
	 * @return String
	 */
	public String verificaSexoMsgEntrada ()
	{
		String msg = "";
		if (getEditEntity() != null && getEditEntity().getSexo() != null)
		{
			if (getEditEntity().getSexo().equals(SexoEnum.MASCULINO.getCodigo()))
			{
				msg = properties.getString("head.welcome.text.m");
			}
			else
			{
				msg = properties.getString("head.welcome.text.f");
			}
		}

		return msg;
	}

	/**
	 * Verifica a situacao do cadastro, e gera a mesangem incial do sitema conforme seu valor.
	 */
	public String verificarMensagemTextoIntrodutorio ()
	{

		if (getEditEntity() != null)
		{

			textoIntrodutorio = MovCadCooperadoConstants.CARREGANDO;

			final RequestContext context = RequestContext.getCurrentInstance();

			// Primeiro acesso
			if (getEditEntity().getSituacao() == null)
			{
				textoIntrodutorio = properties.getString("texto.introdutorio.body");
			}
			// Jah esta salvo parcial
			else if (getEditEntity().getSituacao().equals(SituacaoEnum.SALVO_PARCIALMENTE))
			{
				if (getEditEntity().getMotivoRecusa() == null)
				{
					textoIntrodutorio = properties.getString("texto.introdutorio.body");
				}
				else
				{
					textoIntrodutorio = getEditEntity().getMotivoRecusa();
				}
			}
			// Esta em analise
			else
			{
				textoIntrodutorio = properties.getString("texto.movimentacao.analise.body");
			}

			context.update("textoIntrodutorioDialog");

		}

		return textoIntrodutorio;
	}

	/**
	 * Inicia os volores para renderizar os checkbox dos dias do horario de atendimento.
	 */
	protected void iniciandoValoresHorariosAtendimento ()
	{
		renderizarCheckBoxDomingo = true;
		renderizarCheckBoxSegunda = true;
		renderizarCheckBoxTerca = true;
		renderizarCheckBoxQuarta = true;
		renderizarCheckBoxQuinta = true;
		renderizarCheckBoxSexta = true;
		renderizarCheckBoxSabado = true;
	}

	/**
	 * Carrega a lista manipulavel do dataTable de dependentes.
	 */
	private void iniciarListaDependentes ()
	{
		movimentacaoPrestadorDependenteList = new ArrayList<MovimentacaoPrestadorDependente>();

		if (Validator.isNotEmpty(getEditEntity().getDependentes()))
		{
			for (final MovimentacaoPrestadorDependente dependente : getEditEntity().getDependentes())
			{
				if (dependente.getDataExclusao() == null)
				{
					movimentacaoPrestadorDependenteList.add(dependente);
				}
			}
		}
	}

	/**
	 * Carrega a lista manipulavel do dataTable de endereco de atendimento.
	 */
	private void iniciarListaEnderecoAtendimento ()
	{
		movimentacaoPrestadorEnderecoAtendimentoList = new ArrayList<MovimentacaoPrestadorEndereco>();

		if (Validator.isNotEmpty(getEditEntity().getEnderecosAtendimento()))
		{
			for (final MovimentacaoPrestadorEndereco endereco : getEditEntity().getEnderecosAtendimento())
			{
				if (endereco.getDataExclusao() == null)
				{
					movimentacaoPrestadorEnderecoAtendimentoList.add(endereco);
				}
			}
		}
	}

	/**
	 * Carrega a lista manipulavel do dataTable de horarios de atendimento.
	 */
	private void iniciarListaHorarioAtendimento ()
	{
		movimentacaoPrestadorHorarioList = new ArrayList<MovimentacaoPrestadorHorario>();

		if (Validator.isNotEmpty(movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios()))
		{
			for (final MovimentacaoPrestadorHorario horario : movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios())
			{
				if (horario.getDataExclusao() == null)
				{
					movimentacaoPrestadorHorarioList.add(horario);
				}
			}
		}
	}

	/**
	 * Carrega a lista manipulavel do dataTable de anexos dos documentos do Prestador.
	 */
	private void iniciarListaAnexos ()
	{
		movimentacaoPrestadorAnexoList = new ArrayList<MovimentacaoPrestadorAnexo>();

		if (Validator.isNotEmpty(getEditEntity().getAnexosList()))
		{
			for (final MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo : getEditEntity().getAnexosList())
			{
				movimentacaoPrestadorAnexoList.add(movimentacaoPrestadorAnexo);
			}
		}
	}

	/**
	 * Carrega o Map de MovimentacaoPrestadorCampo para a validacao dos requered dos componentes do Primefaces
	 */
	private void iniciarMapMovimentacaoPrestadorCampo ()
	{
		mapMovimentacaoPrestadorCampo = movimentacaoPrestadorService.carregarMapMovimentacaoPrestadorCampo();
	}

	/**
	 * Faz a verificacao da aba que o formulario esta,
	 * para saber qual de qual tabela pertence o campo,
	 * e retorna a verificacao de obrigatoriedade.
	 *
	 * @param nomeCampo
	 * @return boolean
	 */
	public boolean isRequired (final String nomeCampo)
	{

		String nomeTabela = null;

		switch (currentStep)
		{
		case "dadosPessoais":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR;
			break;
		case "dadosContato":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR;
			break;
		case "dadosFinanceiros":
			nomeTabela = MovCadCooperadoConstants.TABELA_SFN_CONTAFIN;
			break;
		case "enderecoResidencial":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO;
			break;
		case "qualificadores":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_CURRICULO;
			break;
		case "dependentes":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_DEPENDENTE;
			break;
		case "dadosAtendimento":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO;
			break;
		case "dadosEspecialidades":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ESPECIALIDADE;
			break;
		default:
			break;
		}

		return this.verificarCampo(nomeTabela, nomeCampo);
	}

	/**
	 * Metodo especifico para a dialog com o campos de insercao e edicao, dos horarios de atendimento.
	 * Essa dialog eh utilizado na aba de - dadosAtendimento.
	 *
	 * @param nomeCampo
	 * @return boolean
	 */
	public boolean isRequiredHorario (final String nomeCampo)
	{
		return this.verificarCampo(MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_HORARIO, nomeCampo);
	}

	/**
	 * Verifica o atributo de obrigatoriedade do campo atraves da tabela a qual ele pertence.
	 *
	 * @param nomeTabela
	 * @param nomeCampo
	 * @return boolean
	 */
	public boolean verificarCampo (final String nomeTabela, final String nomeCampo)
	{
		boolean isRequired = true;

		if (nomeTabela != null)
		{
			final Map<String, MovimentacaoPrestadorCampo> mapTabela = mapMovimentacaoPrestadorCampo.get(nomeTabela);

			if (mapTabela != null)
			{
				final MovimentacaoPrestadorCampo campo = mapTabela.get(nomeCampo);
				if (campo != null)
				{
					isRequired = campo.getObrigatorio();
				}
			}
		}

		return isRequired;
	}

	/**
	 * Faz a verificacao da aba que o formulario esta,
	 * para saber qual de qual tabela pertence o campo,
	 * e retorna a verificacao de ativacao.
	 *
	 * @param nomeCampo
	 * @return boolean
	 */
	public boolean isDisable (final String nomeCampo)
	{

		String nomeTabela = null;

		switch (currentStep)
		{
		case "dadosPessoais":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR;
			break;
		case "dadosContato":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR;
			break;
		case "dadosFinanceiros":
			nomeTabela = MovCadCooperadoConstants.TABELA_SFN_CONTAFIN;
			break;
		case "enderecoResidencial":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO;
			break;
		case "qualificadores":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_CURRICULO;
			break;
		case "dependentes":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_DEPENDENTE;
			break;
		case "dadosAtendimento":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO;
			break;
		case "dadosEspecialidades":
			nomeTabela = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ESPECIALIDADE;
			break;
		default:
			break;
		}

		return this.verificarCampoDisable(nomeTabela, nomeCampo);
	}

	/**
	 * Metodo especifico para a dialog com o campos de insercao e edicao, dos horarios de atendimento.
	 * Essa dialog eh utilizado na aba de - dadosAtendimento.
	 *
	 * @param nomeCampo
	 * @return boolean
	 */
	public boolean isDisableHorario (final String nomeCampo)
	{
		return this.verificarCampoDisable(MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_HORARIO, nomeCampo);
	}

	/**
	 * Verifica o atributo de desativacao do campo atraves da tabela a qual ele pertence.
	 *
	 * @param nomeTabela
	 * @param nomeCampo
	 * @return boolean
	 */
	public boolean verificarCampoDisable (final String nomeTabela, final String nomeCampo)
	{
		boolean isDisable = true;

		if (nomeTabela != null)
		{
			final Map<String, MovimentacaoPrestadorCampo> mapTabela = mapMovimentacaoPrestadorCampo.get(nomeTabela);

			if (mapTabela != null)
			{
				final MovimentacaoPrestadorCampo campo = mapTabela.get(nomeCampo);
				if (campo != null)
				{
					isDisable = campo.getDisable();
				}
			}
		}

		return isDisable;
	}

	/**
	 * Monta a informacao da ultima movimentacao do cooperado, se ela existir, no sistema.
	 */
	public void montaUltimaMovimentacaoTexto ()
	{
		if (getEditEntity() != null)
		{
			final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final String corpoTexto = "{0}, ultima {1} em {2}";
			final Object[] arguments = new Object[3];

			// Se a Movimentacao jah foi salva parcialmente
			if (getEditEntity().getDataAtualizacao() != null)
			{
				if (getEditEntity().getSituacao().equals(SituacaoEnum.ANALISE))
				{
					arguments[0] = MovCadCooperadoConstants.CAD_ANALISE;
					arguments[1] = MovCadCooperadoConstants.ATUALIZACAO;
					arguments[2] = df.format(getEditEntity().getDataAtualizacao());
					ultimaMovimentacao = MessageFormat.format(corpoTexto, arguments);
				}
				else
				{
					arguments[0] = MovCadCooperadoConstants.CAD_NAO_CONCLUIDO;
					arguments[1] = MovCadCooperadoConstants.ATUALIZACAO;
					arguments[2] = df.format(getEditEntity().getDataAtualizacao());
					ultimaMovimentacao = MessageFormat.format(corpoTexto, arguments);
				}
			}
			// Se a Movimentacao jah foi concluida alguma vez
			else
			{
				final Date dataIntegracao = movimentacaoPrestadorService.verificaMaiorDataIntegracao(getEditEntity().getPrestador());
				if (dataIntegracao != null)
				{
					arguments[0] = MovCadCooperadoConstants.CAD_CONCLUIDO;
					arguments[1] = MovCadCooperadoConstants.ATUALIZACAO;
					arguments[2] = df.format(dataIntegracao);
					ultimaMovimentacao = MessageFormat.format(corpoTexto, arguments);
				}
			}
		}
	}

	/**
	 * Aplica regra que valida o orgao emissor caso venha preenchido
	 * ou
	 * seta o valor SSP para Brasileiros e estrangeiros que estejam com o RG Preenchido
	 */
	public void aplicaRegraOrgaoEmissor ()
	{

		if (getEditEntity() != null)
		{
			if (getEditEntity().getOrgaoEmissorRG() != null)
			{
				// valida se o órgão emissor está no padrão ssp pr
				if (validaOrgaoEmissorRG(getEditEntity().getOrgaoEmissorRG()))
				{

					final String[] partes = getEditEntity().getOrgaoEmissorRG().split(" ");

					// com órgão e estado separados, a busca do estado para preenchimento no campo é feita
					final EstadosVO estado = estadosDao.findBySigla(partes[1]);
					if (estado != null)
					{
						getEditEntity().setOrgaoEmissorRG(partes[0]);
						getEditEntity().setEstadoOrgaoEmissorRG(estado.getSigla());
					}
					else
					{ // se o estado for inválido, é verificado a nacionalidade para fixar ssp no órgão
						if (getEditEntity().getEstadoOrgaoEmissorRG() != null)
						{
							getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
						}
						else if (getEditEntity().getNacionalidade() != null)
						{
							if (getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
							{
								getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
							}
						}
						else
						{
							getEditEntity().setOrgaoEmissorRG(null);
						}
					}
				}
				else
				{ // caso o órgão emissor não esteja no padrão ssp, verifica a nacionalidade para fixar ssp
					if (getEditEntity().getEstadoOrgaoEmissorRG() != null)
					{
						getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
						getEditEntity().setEstadoOrgaoEmissorRG(null);

					}
					else if (getEditEntity().getNacionalidade() != null)
					{
						if (getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
						{
							getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
							getEditEntity().setEstadoOrgaoEmissorRG(null);
						}
					}
					else
					{
						getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
						getEditEntity().setEstadoOrgaoEmissorRG(null);
					}
				}
			}
			// órgão emissor não é setado se a nacionalidade for estrangeira e o número do rg estiver nulo
			else if (getEditEntity().getNacionalidade() != null)
			{
				if ((!(!getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL) &&
				    getEditEntity().getNumeroRG() == null)) || getEditEntity().getNacionalidade() == null)
				{
					getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
				}
			}
		}
	}

	public String cadastroConcluido ()
	{
		RequestContext.getCurrentInstance().execute("PF('wiz').loadStep('dadosPessoais',true)");
		clear();
		currentStep = "dadosPessoais";
		verificarMensagemTextoIntrodutorio();
		return getReturnEdit();
	}

	/**
	 * Direciona o fluxo conforme a aba que invocou o dialog de logradouros.
	 */
	public void adicionaItemLogradouro ()
	{
		if (isEnderecoAtendimento())
		{
			adicionaItemLogradouroAtendimento();
		}
		else
		{
			adicionaItemLogradouroResidencial();
		}
	}

	/**
	 * Inclui os dados do logradouro selecionado no endereco de residencial.
	 */
	public void adicionaItemLogradouroResidencial ()
	{
		if (logradouroItem != null)
		{
			if (logradouroItem.getCep() != null)
			{
				getEditEntity().getEnderecoResidencial().setCep(cepPesquisa);
			}
			if (logradouroItem.getLogradouro() != null)
			{
				getEditEntity().getEnderecoResidencial().setLogradouro(logradouroItem.getLogradouro());
			}
			else
			{
				getEditEntity().getEnderecoResidencial().setLogradouro(null);
			}
			if (logradouroItem.getBairro() != null)
			{
				getEditEntity().getEnderecoResidencial().setBairro(logradouroItem.getBairro());
			}
			else
			{
				getEditEntity().getEnderecoResidencial().setBairro(null);
			}
			if (logradouroItem.getEstado() != null)
			{
				getEditEntity().getEnderecoResidencial().setEstado(logradouroItem.getEstado());
			}
			else
			{
				getEditEntity().getEnderecoResidencial().setEstado(null);
			}
			if (logradouroItem.getMunicipio() != null)
			{
				getEditEntity().getEnderecoResidencial().setMunicipio(logradouroItem.getMunicipio());
			}
			else
			{
				getEditEntity().getEnderecoResidencial().setMunicipio(null);
			}

			habilitaEstadoEndereco = true;
			if (!avancar)
			{
				getEditEntity().getEnderecoResidencial().setTipoLogradouro(logradouroItem.getTipoLogradouro());
				messages.addInfoMessage("informativo.endereco.alterado");
			}

			logradouroItem = new LogradouroVO();

		}
	}

	/**
	 * Inclui os dados do logradouro selecionado no endereco de atendimento.
	 */
	public void adicionaItemLogradouroAtendimento ()
	{
		if (logradouroItem != null)
		{
			if (logradouroItem.getCep() != null)
			{
				movimentacaoPrestadorEnderecoAtendimento.setCep(cepPesquisa);
			}
			if (logradouroItem.getLogradouro() != null)
			{
				movimentacaoPrestadorEnderecoAtendimento.setLogradouro(logradouroItem.getLogradouro());
			}
			else
			{
				movimentacaoPrestadorEnderecoAtendimento.setLogradouro(null);
			}
			if (logradouroItem.getBairro() != null)
			{
				movimentacaoPrestadorEnderecoAtendimento.setBairro(logradouroItem.getBairro());
			}
			else
			{
				movimentacaoPrestadorEnderecoAtendimento.setBairro(null);
			}
			if (logradouroItem.getEstado() != null)
			{
				movimentacaoPrestadorEnderecoAtendimento.setEstado(logradouroItem.getEstado());
			}
			else
			{
				movimentacaoPrestadorEnderecoAtendimento.setEstado(null);
			}
			if (logradouroItem.getMunicipio() != null)
			{
				movimentacaoPrestadorEnderecoAtendimento.setMunicipio(logradouroItem.getMunicipio());
			}
			else
			{
				movimentacaoPrestadorEnderecoAtendimento.setMunicipio(null);
			}

			habilitaEstadoEndereco = true;
			if (!avancar)
			{
				movimentacaoPrestadorEnderecoAtendimento.setTipoLogradouro(logradouroItem.getTipoLogradouro());
			}

			logradouroItem = new LogradouroVO();

		}
	}

	@Override
	public String clear ()
	{
		RequestContext.getCurrentInstance().execute("PF('wiz').loadStep('dadosPessoais',true)");
		super.clear();
		currentStep = "dadosPessoais";
		return getReturnEdit();
	}

	/**
	 * Valida se o Orgao Emissor do RG já esta no padrao: SSP <ESTADO SIGLA>
	 *
	 * @param orgao
	 * @return
	 */
	public boolean validaOrgaoEmissorRG (final String orgao)
	{
		if (orgao.length() == 6)
		{
			if (orgao.toUpperCase().substring(0, 4).equals("SSP "))
			{
				if (Character.isLetter(orgao.charAt(4)) && Character.isLetter(orgao.charAt(5)))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void listAll ()
	{
		// OK
	}

	@Override
	public String list ()
	{
		// OK
		return null;
	}

	/**
	 * Metodo que intercepta e trata o Avacar e Voltar das abas.
	 *
	 * @param event
	 * @return String
	 */
	public String onFlowProcess (final FlowEvent event)
	{
		String newStep = event.getNewStep();
		final String oldStep = event.getOldStep();

		if (newStep.equals("enderecoResidencial") && oldStep.equals("dadosContato"))
		{
			avancar = true;
			setEnderecoAtendimento(false);
		}

		// Validacao para informar qual o direcionamento do fluxo.
		if (newStep.equals("dadosAtendimento") && oldStep.equals("dependentes"))
		{
			setEnderecoAtendimento(true);
		}
		else if (newStep.equals("dependentes") && oldStep.equals("dadosAtendimento"))
		{
			setEnderecoAtendimento(false);
		}

		// Validacao para nao proseguir navegacao quando nao existe qualificadores.
		if (newStep.equals("dependentes") && oldStep.equals("qualificadores"))
		{
			if (getEditEntity().getMovimentacaoPrestadorCurriculos() == null || getEditEntity().getMovimentacaoPrestadorCurriculos().size() == 0)
			{
				newStep = oldStep;
				messages.addErrorMessage("informativo.formacao.obrigatorio");
			}
		}

		// Validacao do cnes dos enderecos de atendimento
		if (newStep.equals("dadosEspecialidades") && oldStep.equals("dadosAtendimento"))
		{
			if (getEditEntity() != null)
			{
				if (Validator.isNotEmpty(getEditEntity().getEnderecosAtendimento()))
				{
					if (!validarCnes(getEditEntity().getEnderecosAtendimento()))
					{
						newStep = oldStep;
						messages.addErrorMessage("error.endereco.cnes.validacao");
						return newStep;
					}
				}
			}
		}

		// Faz a verificacao dos campos que necessitam de anexo
		if (newStep.equals("anexarArquivos") && oldStep.equals("dadosEspecialidades"))
		{
			if (getEditEntity() != null &&
			    (getEditEntity().getSituacao() == null || getEditEntity().getSituacao().equals(SituacaoEnum.SALVO_PARCIALMENTE)))
			{
				try
				{
					atualizaOrgaoEmissor();

					/*
					 * Salva Parcialmente e gera os Logs de Auditoria,
					 * para poder verificar os campos que necessitam de anexo.
					 */
					setEditEntity(movimentacaoPrestadorService.salvarParcialMovimentacaoPrestador(getEditEntity()));

					init();

					// Executa a verificacao
					if (verificarCamposComAnexo())
					{
						messages.addWarnMessage("informativo.anexo.campo");
					}
				}
				catch (final Exception e)
				{
					LOG.error("Erro ao verificar a existencia de anexos: " + VExceptions.getErrorMessage(e), e);
					messages.addErrorMessage("informativo.anexo.erro");
				}
			}
		}

		avancar = false;

		currentStep = newStep;
		return newStep;
	}

	/**
	 * @return true para requirimento dos campos referente a RG
	 */
	public boolean isEstrangeiro ()
	{
		if (getEditEntity() != null)
		{
			if (getEditEntity().getNacionalidade() != null)
			{
				if (getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
				{
					return true;
				}
				else if (!getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL) &&
				    getEditEntity().getNumeroRG() != null)
				{
					return true;
				}
				else if (!getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL) &&
				    getEditEntity().getDataExpedicaoRG() != null)
				{
					return true;
				}
				else if (!getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL) &&
				    getEditEntity().getEstadoOrgaoEmissorRG() != null)
				{
					return true;
				}
				else
				{
					return false;
				}

			}
		}

		return true;
	}

	public boolean isMaior ()
	{
		if (getMovimentacaoPrestadorDependente().getDataNascimento() != null)
		{
			final Date dataNasc = getMovimentacaoPrestadorDependente().getDataNascimento();
			if (Util.calculaIdade(new SimpleDateFormat("dd/MM/yyyy").format(dataNasc), "dd/MM/yyyy") >= 8)
			{
				return true;
			}
		}
		return false;
	}

	public boolean habilitaCamposReferenteRG ()
	{
		if (getEditEntity() != null)
		{
			if (getEditEntity().getNacionalidade() != null)
			{
				if (getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
				{
					return false;
				}
				else if (!getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL) &&
				    getEditEntity().getNumeroRG() != null)
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			else if (getEditEntity().getNumeroRG() != null)
			{
				return false;
			}
		}
		return false;
	}

	public void limpaEstadoMunicipioNatual ()
	{
		if (getEditEntity().getNacionalidade() != null)
		{
			if (getEditEntity().getNacionalidade().getNome() != MovCadCooperadoConstants.BRASIL)
			{
				getEditEntity().setEstado(null);
				getEditEntity().setNaturalidade(null);
			}
		}

	}

	public MovimentacaoPrestadorCurriculo getMovimentacaoPrestadorCurriculo ()
	{
		if (movimentacaoPrestadorCurriculo == null)
		{
			movimentacaoPrestadorCurriculo = new MovimentacaoPrestadorCurriculo();
		}
		return movimentacaoPrestadorCurriculo;
	}

	public void setMovimentacaoPrestadorCurriculo (final MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo)
	{
		this.movimentacaoPrestadorCurriculo = movimentacaoPrestadorCurriculo;
	}

	public MovimentacaoPrestadorEndereco getMovimentacaoPrestadorEnderecoAtendimento ()
	{
		if (movimentacaoPrestadorEnderecoAtendimento == null)
		{
			movimentacaoPrestadorEnderecoAtendimento = new MovimentacaoPrestadorEndereco();
		}
		return movimentacaoPrestadorEnderecoAtendimento;
	}

	public void setMovimentacaoPrestadorEnderecoAtendimento (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento)
	{
		this.movimentacaoPrestadorEnderecoAtendimento = movimentacaoPrestadorEnderecoAtendimento;
	}

	public MovimentacaoPrestadorDependente getMovimentacaoPrestadorDependente ()
	{
		if (movimentacaoPrestadorDependente == null)
		{
			movimentacaoPrestadorDependente = new MovimentacaoPrestadorDependente();
		}
		return movimentacaoPrestadorDependente;
	}

	public void setMovimentacaoPrestadorDependente (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente)
	{
		this.movimentacaoPrestadorDependente = movimentacaoPrestadorDependente;
	}

	public MovimentacaoPrestadorHorario getMovimentacaoPrestadorHorario ()
	{
		if (movimentacaoPrestadorHorario == null)
		{
			movimentacaoPrestadorHorario = new MovimentacaoPrestadorHorario();
		}
		return movimentacaoPrestadorHorario;
	}

	public void setMovimentacaoPrestadorHorario (final MovimentacaoPrestadorHorario movimentacaoPrestadorHorario)
	{
		this.movimentacaoPrestadorHorario = movimentacaoPrestadorHorario;
	}

	public List<EstadosVO> getEstadosList ()
	{
		if (Validator.isEmpty(estadosList))
		{
			estadosList = estadosDao.findEstados();
		}
		return estadosList;
	}

	public List<EstadoEnum> getEstadosRG ()
	{
		if (Validator.isEmpty(estadosOrgaoEmissorRG))
		{
			estadosOrgaoEmissorRG = new ArrayList<EstadoEnum>(EstadoEnum.getMap().values());

			estadosOrgaoEmissorRG = SortTool.sort(estadosOrgaoEmissorRG, new String[]{"sgUfIbge"});
		}

		return estadosOrgaoEmissorRG;
	}

	public List<RacaCorEnum> racaCorEnumList ()
	{
		if (Validator.isEmpty(racaCorEnumList))
		{
			racaCorEnumList = new ArrayList<RacaCorEnum>(RacaCorEnum.getMap().values());
			racaCorEnumList.remove(RacaCorEnum.DESCONHECIDO);

		}
		return racaCorEnumList;
	}

	public List<MunicipiosVO> getMunicipiosEstado (final EstadosVO estado)
	{
		if (estado != null)
		{
			if (estado != estadoVerificador)
			{

				final MunicipiosVO municipio = new MunicipiosVO();
				municipio.setEstado(estado);
				municipiosList = municipiosDao.listOrdered(municipio, new String[]{"nome"});
				estadoVerificador = estado;
				return municipiosList;
			}

		}
		return municipiosList;
	}

	public List<PaisesVO> getPaisesList ()
	{
		if (Validator.isEmpty(paisesList))
		{
			paisesList = paisesDao.findAll();

			if (!Validator.isEmpty(paisesList))
			{
				Collections.sort(paisesList);
			}

			// Move o objeto Brasil para a primeira posição
			if (!Validator.isEmpty(paisesList))
			{
				Collections.swap(paisesList, 32, 32 - 32);
			}

		}

		return paisesList;

	}

	public List<EstadoCivilVO> getEstadoCivilList ()
	{
		if (Validator.isEmpty(estadoCivilList))
		{
			estadoCivilList = estadoCivilDao.listAllOrdered((new String[]{"descricao"}));
		}
		return estadoCivilList;
	}

	public List<TipoLogradouroVO> getTipoLogradouroList ()
	{
		if (habilitaEstadoEndereco == true)
		{
			tipoLogradouroList = tipoLogradouroDao.listAllOrdered((new String[]{"descricao"}));

		}
		else if (habilitaEstadoEndereco == false)
		{
			tipoLogradouroList = tipoLogradouroDao.findTipoComSigla();
		}
		return tipoLogradouroList;
	}

	public List<SexoEnum> sexoList ()
	{
		if (Validator.isEmpty(sexoList))
		{
			sexoList = new ArrayList<SexoEnum>();
			for (final SexoEnum sexoEnum : SexoEnum.values())
			{
				sexoList.add(sexoEnum);
			}

		}
		return sexoList;
	}

	public List<TipoCursoEnum> tipoCursoList ()
	{
		if (Validator.isEmpty(tipoCursoList))
		{
			tipoCursoList = new ArrayList<TipoCursoEnum>(TipoCursoEnum.getMap().values());
			tipoCursoList.remove(TipoCursoEnum.DESCONHECIDO);
			tipoCursoList.remove(TipoCursoEnum.ESPECIALIZACAO);
			tipoCursoList.remove(TipoCursoEnum.EXTRA_CURRICULAR);
			tipoCursoList.remove(TipoCursoEnum.PARTICIPACAO_EVENTOS);

		}
		return tipoCursoList;
	}

	public List<TipoDependenteVO> tipoDependenteList ()
	{
		return tipoDependenteList = tipoDependenteDao.listAllOrdered((new String[]{"descricao"}));
	}

	public List<CategoriaCnhEnum> categoriasCnh ()
	{
		if (Validator.isEmpty(categoriaCnhList))
		{
			categoriaCnhList = new ArrayList<CategoriaCnhEnum>();
			for (final CategoriaCnhEnum categoriaCnh : CategoriaCnhEnum.values())
			{
				categoriaCnhList.add(categoriaCnh);
			}

		}
		return categoriaCnhList;
	}

	public List<RNECondicaoEnum> rneCondicaoList ()
	{
		if (Validator.isEmpty(rneCondicaoList))
		{
			rneCondicaoList = new ArrayList<RNECondicaoEnum>(RNECondicaoEnum.getMap().values());
			rneCondicaoList.remove(RNECondicaoEnum.DESCONHECIDO);

		}
		return rneCondicaoList;
	}

	/**
	 * Concatena o órgão emissor com o estado antes de salvar
	 */
	public void atualizaOrgaoEmissor ()
	{
		if (getEditEntity().getOrgaoEmissorRG() != null && getEditEntity().getEstadoOrgaoEmissorRG() != null)
		{
			getEditEntity().setOrgaoEmissorRG(new String());
			getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG + " " + getEditEntity().getEstadoOrgaoEmissorRG());
		}

	}

	/**
	 * Caso estado do órgão emissor seja preenchido para prestador estrangeiro,
	 * órgão recebe SSP
	 */
	public void atualizaOrgaoEmissorDeEstrangeiro ()
	{
		if (getEditEntity().getEstadoOrgaoEmissorRG() != null && getEditEntity().getNacionalidade() != null)

		{
			if (!getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
			{
				getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
			}

		}
		else
		{
			getEditEntity().setOrgaoEmissorRG(null);
		}
	}

	public void fecharModalCurriculo ()
	{
		movimentacaoPrestadorCurriculo = new MovimentacaoPrestadorCurriculo();
	}

	public void fecharModalDependente ()
	{
		movimentacaoPrestadorDependente = new MovimentacaoPrestadorDependente();
	}

	public void fecharModalEnderecoAtendimento ()
	{
		movimentacaoPrestadorEnderecoAtendimento = new MovimentacaoPrestadorEndereco();
	}

	public void fecharModalHorarioAtendimento ()
	{
		movimentacaoPrestadorHorario = new MovimentacaoPrestadorHorario();
	}

	/**
	 * Adiciona um Curriculo novo.
	 */
	public void adicionaItemCurriculo ()
	{
		if (movimentacaoPrestadorCurriculo != null)
		{
			if (!Validator.isEmpty(getEditEntity().getMovimentacaoPrestadorCurriculos()))
			{
				if (!getEditEntity().getMovimentacaoPrestadorCurriculos().contains(movimentacaoPrestadorCurriculo))
				{
					gerarItemCurriculo();
				}
				else
				{
					messages.addInfoMessage("informativo.formacao.atualizado");
				}
			}
			else
			{
				gerarItemCurriculo();
			}
		}
	}

	/**
	 * Gera um curriculo.
	 */
	private void gerarItemCurriculo ()
	{
		movimentacaoPrestadorCurriculo.setMovimentacaoPrestador(getEditEntity());
		getEditEntity().getMovimentacaoPrestadorCurriculos().add(movimentacaoPrestadorCurriculo);

		movimentacaoPrestadorCurriculo = new MovimentacaoPrestadorCurriculo();
		messages.addInfoMessage("informativo.formacao.adicionada");
	}

	/**
	 * Adiciona um dependente.
	 */
	public void adicionarDependente ()
	{
		// Realiza novamente as validacoes
		if (movimentacaoPrestadorDependente != null && movimentacaoPrestadorDependente.getCpf() != null)
		{
			validarCpfIgualPrestador();
			movimentacaoPrestadorDependente.setCpf(movimentacaoPrestadorDependente.getCpf().replace(".", "").replace("-", ""));
		}
		else if (movimentacaoPrestadorDependente.getCpf() != null && Validator.isNotEmpty(getEditEntity().getDependentes()))
		{
			validarCpfIgualDependentes();
		}

		if (!Validator.isEmpty(getEditEntity().getDependentes()))
		{
			if (!getEditEntity().getDependentes().contains(movimentacaoPrestadorDependente))
			{
				if (movimentacaoPrestadorDependente.getCpf() != null)
				{
					movimentacaoPrestadorDependente.setCpf(movimentacaoPrestadorDependente.getCpf().replace(".", "").replace("-", ""));
				}
				gerarDependente();
			}
			else
			{
				messages.addInfoMessage("informativo.dependente.atualizado");
			}

		}
		else
		{
			gerarDependente();
		}
	}

	/**
	 * Gera o dependente.
	 */
	private void gerarDependente ()
	{
		movimentacaoPrestadorDependente.setMovimentacaoPrestador(getEditEntity());
		getEditEntity().getDependentes().add(movimentacaoPrestadorDependente);
		movimentacaoPrestadorDependenteList.add(movimentacaoPrestadorDependente);

		movimentacaoPrestadorDependente = new MovimentacaoPrestadorDependente();
		messages.addInfoMessage("informativo.dependente.adicionado");
	}

	/**
	 * Metodo utilizado no atributo validator do campo de cpf do prestador.
	 *
	 * @return
	 */
	public void validarCpfDependente (final FacesContext fac, final UIComponent com, final Object obj)
	{
		if (obj != null)
		{
			cpfDependente = ((String)obj);

			cpfDependente = cpfDependente.trim().replace(".", "").replace("-", "");

			if (!Util.validarCpf(cpfDependente))
			{
				throw new ValidatorException(messages.getErrorMessage("validator.cpf"));
			}

			if (validarCpfIgualPrestador())
			{
				throw new ValidatorException(messages.getErrorMessage("error.dependente.cpf.prestador"));
			}

			if (validarCpfIgualDependentes())
			{
				throw new ValidatorException(messages.getErrorMessage("error.dependente.cpf.dependente"));
			}
		}
	}

	public void validarMaiorDataIncioCurriculo (final FacesContext fac, final UIComponent com, final Object obj)
	{
		Date dtInicial = new Date();
		if (obj != null && obj instanceof java.util.Date)
		{
			dtInicial = (Date)obj;
			if (getEditEntity().getDataNascimento() != null)
			{
				if (dtInicial.before(getEditEntity().getDataNascimento()))
				{
					throw new ValidatorException(messages.getErrorMessage("informativo.dataInvalida"));
				}
			}

			if (getEditEntity().getItemCurriculo().getDataConclusao() != null &&
			    getEditEntity().getItemCurriculo().getDataConclusao().before(dtInicial))
			{
				getEditEntity().getItemCurriculo().setDataInicial(null);
				throw new ValidatorException(messages.getErrorMessage("informativo.dataInvalida.inicio"));
			}

			if (!CustomDate.validaData(dtInicial))
			{
				throw new ValidatorException(messages.getErrorMessage("validator.dataFutura"));
			}
		}
	}

	public void validarMaiorDataConclusaoCurriculo (final FacesContext fac, final UIComponent com, final Object obj)
	{
		if (obj != null)
		{
			Date dtFinal = new Date();
			if (getEditEntity().getItemCurriculo().getDataInicial() != null)
			{
				dtFinal = (Date)obj;
				if (dtFinal.before(getEditEntity().getItemCurriculo().getDataInicial()))
				{
					getEditEntity().getItemCurriculo().setDataConclusao(null);
					throw new ValidatorException(messages.getErrorMessage("informativo.dataInvalida.conclusao"));
				}
				else if (dtFinal.before(getEditEntity().getItemCurriculo().getDataInicial()))
				{
					throw new ValidatorException(messages.getErrorMessage("informativo.dataInvalida.inicio"));

				}
			}
		}
	}

	/**
	 * Metodo utilizado para validar o numero e a existencia do CNES.
	 *
	 * @return
	 */
	public void validarCnes (final FacesContext fac, final UIComponent com, final Object obj)
	{
		if (obj != null)
		{
			final MovimentacaoPrestadorEndereco enderecoModal = (MovimentacaoPrestadorEndereco)com.getAttributes().get("enderecoParam");

			final List<Integer> cnesList = new ArrayList<Integer>();
			cnesList.add(0000000);
			cnesList.add(1111111);
			cnesList.add(2222222);
			cnesList.add(3333333);
			cnesList.add(4444444);
			cnesList.add(5555555);
			cnesList.add(6666666);
			cnesList.add(7777777);
			cnesList.add(8888888);
			cnesList.add(9999999);
			cnesList.add(1234567);

			// Verifica se o valor eh valido
			for (final Integer cnes : cnesList)
			{
				if (obj.equals(cnes))
				{
					messages.addErrorMessage("validator.cnes.invalido");
					throw new ValidatorException(messages.getErrorMessage("validator.cnes.invalido"));
				}
			}

			// Verifica se o valor nao eh repetido de algum endereco de atendimento
			if (getEditEntity() != null && Validator.isNotEmpty(getEditEntity().getEnderecosAtendimento()))
			{
				for (final MovimentacaoPrestadorEndereco endereco : getEditEntity().getEnderecosAtendimento())
				{
					if (!enderecoModal.equals(endereco))
					{
						if (obj.equals(endereco.getCnes()))
						{
							messages.addErrorMessage("validator.cnes.existente");
							throw new ValidatorException(messages.getErrorMessage("validator.cnes.existente"));
						}
					}
				}
			}
		}
	}

	/**
	 * Verifica se o cpf do Dependente nao eh o mesmo do Cooperado/Prestador.
	 *
	 * @return boolean
	 */
	private boolean validarCpfIgualPrestador ()
	{
		if (cpfDependente != null && getEditEntity().getCpfPrestador() != null)
		{
			return validarCpfIgual(getEditEntity().getCpfPrestador(), cpfDependente);
		}
		return false;
	}

	/**
	 * Verifica se o cpf do Dependente nao eh o mesmo de algum outro Dependente.
	 *
	 * @return boolean
	 */
	private boolean validarCpfIgualDependentes ()
	{
		if (Validator.isNotEmpty(getEditEntity().getDependentes()))
		{
			for (final MovimentacaoPrestadorDependente dependente : getEditEntity().getDependentes())
			{
				if (!dependente.equals(movimentacaoPrestadorDependente))
				{
					if (cpfDependente != null && dependente.getCpf() != null)
					{
						if (validarCpfIgual(cpfDependente, dependente.getCpf()))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Realiza a validacao de igualdade entre dois cpfs.
	 *
	 * @param cpf1
	 * @param cpf2
	 * @return boolean
	 */
	private boolean validarCpfIgual (final String cpf1, final String cpf2)
	{
		if (cpf1 != null && cpf2 != null)
		{
			if (cpf1.replace(".", "").replace("-", "").equals(cpf2.replace(".", "").replace("-", "")))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Adiciona um novo Endereco de Atendimento.
	 */
	public void adicionarEnderecoAtendimento ()
	{
		if (movimentacaoPrestadorEnderecoAtendimento != null)
		{
			if (!Validator.isEmpty(getEditEntity().getEnderecosAtendimento()))
			{
				if (!getEditEntity().getEnderecosAtendimento().contains(movimentacaoPrestadorEnderecoAtendimento))
				{
					gerarEnderecoAtendimento();
				}
				else
				{
					messages.addInfoMessage("informativo.endereco.atendimento.atualizado");
				}
			}
			else
			{
				gerarEnderecoAtendimento();
			}
		}
	}

	/**
	 * Gera um endereco de atendimento.
	 */
	private void gerarEnderecoAtendimento ()
	{
		movimentacaoPrestadorEnderecoAtendimento.setAtendimento(SimNaoEnum.SIM.getSigla());
		movimentacaoPrestadorEnderecoAtendimento.setTipoAtendimento(SimNaoEnum.SIM.getSigla());
		movimentacaoPrestadorEnderecoAtendimento.setPessoal(SimNaoEnum.NAO.getSigla());
		movimentacaoPrestadorEnderecoAtendimento.setMovimentacaoPrestador(getEditEntity());

		getEditEntity().getEnderecosAtendimento().add(movimentacaoPrestadorEnderecoAtendimento);

		movimentacaoPrestadorEnderecoAtendimentoList.add(movimentacaoPrestadorEnderecoAtendimento);

		movimentacaoPrestadorEnderecoAtendimento = new MovimentacaoPrestadorEndereco();
		messages.addInfoMessage("informativo.endereco.atendimento.adicionado");
	}

	/**
	 * Adiciona ou atualiza Horario de Atendimento.
	 */
	public void adicionarHorarioAtendimento ()
	{

		if (Validator.isNotEmpty(movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios()) &&
		    movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios().contains(movimentacaoPrestadorHorario))
		{
			messages.addInfoMessage("informativo.endereco.atendimento.horario.atualizado");
		}
		else
		{
			MovimentacaoPrestadorHorario movimentacaoPrestadorHorarioAux;

			final List<MovimentacaoPrestadorHorario> movimentacaoPrestadorHorarios = new ArrayList<MovimentacaoPrestadorHorario>();

			if (movimentacaoPrestadorHorario.getDomingo() != null && movimentacaoPrestadorHorario.getDomingo().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setDomingo(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (movimentacaoPrestadorHorario.getSegunda() != null && movimentacaoPrestadorHorario.getSegunda().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setSegunda(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (movimentacaoPrestadorHorario.getTerca() != null && movimentacaoPrestadorHorario.getTerca().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setTerca(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (movimentacaoPrestadorHorario.getQuarta() != null && movimentacaoPrestadorHorario.getQuarta().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setQuarta(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (movimentacaoPrestadorHorario.getQuinta() != null && movimentacaoPrestadorHorario.getQuinta().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setQuinta(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (movimentacaoPrestadorHorario.getSexta() != null && movimentacaoPrestadorHorario.getSexta().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setSexta(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (movimentacaoPrestadorHorario.getSabado() != null && movimentacaoPrestadorHorario.getSabado().equals(true))
			{
				movimentacaoPrestadorHorarioAux = gerarHorarioAtendimento();
				movimentacaoPrestadorHorarioAux.setSabado(true);
				movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorarioAux);
			}

			if (Validator.isNotEmpty(movimentacaoPrestadorHorarios))
			{
				if (Validator.isNotEmpty(movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios()))
				{
					for (final MovimentacaoPrestadorHorario movimentacaoPrestadorHorario : movimentacaoPrestadorHorarios)
					{
						movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios().add(movimentacaoPrestadorHorario);
						movimentacaoPrestadorHorarioList.add(movimentacaoPrestadorHorario);
					}
				}
				else
				{
					movimentacaoPrestadorEnderecoAtendimento.setPrestadorHorarios(new ArrayList<MovimentacaoPrestadorHorario>());
					for (final MovimentacaoPrestadorHorario movimentacaoPrestadorHorario : movimentacaoPrestadorHorarios)
					{
						movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios().add(movimentacaoPrestadorHorario);
						movimentacaoPrestadorHorarioList.add(movimentacaoPrestadorHorario);
					}
				}
				messages.addInfoMessage("informativo.endereco.atendimento.horario.adicionado");
			}
		}
	}

	/**
	 * Adiciona um novo Horario de Atendimento.
	 */
	protected MovimentacaoPrestadorHorario gerarHorarioAtendimento ()
	{
		final MovimentacaoPrestadorHorario movimentacaoPrestadorHorarioNovo = new MovimentacaoPrestadorHorario();
		movimentacaoPrestadorHorarioNovo.setMovimentacaoPrestadorEndereco(movimentacaoPrestadorEnderecoAtendimento);
		movimentacaoPrestadorHorarioNovo.setHoraInicial(movimentacaoPrestadorHorario.getHoraInicial());
		movimentacaoPrestadorHorarioNovo.setHoraFinal(movimentacaoPrestadorHorario.getHoraFinal());
		setValoresHorario(movimentacaoPrestadorHorarioNovo);
		return movimentacaoPrestadorHorarioNovo;
	}

	private void setValoresHorario (final MovimentacaoPrestadorHorario movimentacaoPrestadorHorarioNovo)
	{
		movimentacaoPrestadorHorarioNovo.setDomingo(false);
		movimentacaoPrestadorHorarioNovo.setSegunda(false);
		movimentacaoPrestadorHorarioNovo.setTerca(false);
		movimentacaoPrestadorHorarioNovo.setQuarta(false);
		movimentacaoPrestadorHorarioNovo.setQuinta(false);
		movimentacaoPrestadorHorarioNovo.setSexta(false);
		movimentacaoPrestadorHorarioNovo.setSabado(false);
	}

	/**
	 * Renderiza os campos de checkbox referentes aos dias da semana.
	 */
	public void renderizarCamposDialogHorarioAtendimentoInsercao ()
	{
		iniciandoValoresHorariosAtendimento();

		final RequestContext context = RequestContext.getCurrentInstance();

		if (movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios().size() < MovCadCooperadoConstants.DIAS_DA_SEMANA)
		{
			if (Validator.isNotEmpty(movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios()))
			{
				for (final MovimentacaoPrestadorHorario prestadorHorario : movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios())
				{
					// Se o dia nao estiver selecionado, mostra o checkbox
					if (renderizarCheckBoxDomingo)
					{
						renderizarCheckBoxDomingo = prestadorHorario.getDomingo() != null && prestadorHorario.getDomingo() ? false : true;
					}
					if (renderizarCheckBoxSegunda)
					{
						renderizarCheckBoxSegunda = prestadorHorario.getSegunda() != null && prestadorHorario.getSegunda() ? false : true;
					}
					if (renderizarCheckBoxTerca)
					{
						renderizarCheckBoxTerca = prestadorHorario.getTerca() != null && prestadorHorario.getTerca() ? false : true;
					}
					if (renderizarCheckBoxQuarta)
					{
						renderizarCheckBoxQuarta = prestadorHorario.getQuarta() != null && prestadorHorario.getQuarta() ? false : true;
					}
					if (renderizarCheckBoxQuinta)
					{
						renderizarCheckBoxQuinta = prestadorHorario.getQuinta() != null && prestadorHorario.getQuinta() ? false : true;
					}
					if (renderizarCheckBoxSexta)
					{
						renderizarCheckBoxSexta = prestadorHorario.getSexta() != null && prestadorHorario.getSexta() ? false : true;
					}
					if (renderizarCheckBoxSabado)
					{
						renderizarCheckBoxSabado = prestadorHorario.getSabado() != null && prestadorHorario.getSabado() ? false : true;
					}
				}
			}

			context.execute("PF('itemHorarioAtendimento').show();");

		}
		else
		{
			textoMensagemGenerica = properties.getString("movimentacaoPrestadorHorario.horariosJaPrenchidos");
			context.execute("PF('textoGenericoDialog').show();");
		}
	}

	/**
	 * Renderiza os campos de checkbox referentes aos dias da semana.
	 */
	public void renderizarCamposDialogHorarioAtendimentoEdicao (final MovimentacaoPrestadorHorario prestadorHorario)
	{
		iniciandoValoresHorariosAtendimento();

		// Se o dia nao estiver selecionado, mostra o checkbox
		if (renderizarCheckBoxDomingo)
		{
			renderizarCheckBoxDomingo = prestadorHorario.getDomingo() != null && prestadorHorario.getDomingo() ? true : false;
		}
		if (renderizarCheckBoxSegunda)
		{
			renderizarCheckBoxSegunda = prestadorHorario.getSegunda() != null && prestadorHorario.getSegunda() ? true : false;
		}
		if (renderizarCheckBoxTerca)
		{
			renderizarCheckBoxTerca = prestadorHorario.getTerca() != null && prestadorHorario.getTerca() ? true : false;
		}
		if (renderizarCheckBoxQuarta)
		{
			renderizarCheckBoxQuarta = prestadorHorario.getQuarta() != null && prestadorHorario.getQuarta() ? true : false;
		}
		if (renderizarCheckBoxQuinta)
		{
			renderizarCheckBoxQuinta = prestadorHorario.getQuinta() != null && prestadorHorario.getQuinta() ? true : false;
		}
		if (renderizarCheckBoxSexta)
		{
			renderizarCheckBoxSexta = prestadorHorario.getSexta() != null && prestadorHorario.getSexta() ? true : false;
		}
		if (renderizarCheckBoxSabado)
		{
			renderizarCheckBoxSabado = prestadorHorario.getSabado() != null && prestadorHorario.getSabado() ? true : false;
		}
	}

	public void onChangeCep ()
	{
		if (cepPesquisa != null)
		{
			logradourosList = new ArrayList<>();

			final List<LogradouroVO> logradouros = logradouroDao.findByCep(cepPesquisa);
			if (!Validator.isEmpty(logradouros))
			{

				logradourosList.addAll(logradouros);
			}
		}
	}

	/**
	 * Valida o cep do cooperado
	 *
	 * @param context
	 * @param component 80310-452
	 * @param value
	 */
	public void validateCEP (final FacesContext context, final UIComponent component, final Object value)
	{
		String cep = null;
		if (value != null)
		{
			cep = (String)value;
		}

		if (Validator.isNotEmpty(cep))
		{
			final List<LogradouroVO> logradouros = logradouroDao.findByCep(cep);
			if (!Validator.isEmpty(logradouros))
			{
				((UIInput)component).setValid(true);

			}
			else
			{

				((UIInput)component).setValid(false);
				throw new ValidatorException(messages.getErrorMessage("error.cep.invalido"));
			}

		}
		else
		{
			((UIInput)component).setValid(false);
			throw new ValidatorException(messages.getErrorMessage("generico_obrigatorio"));
		}
	}

	public void onChangeNacionalidade ()
	{
		if (getEditEntity().getNacionalidade() != null)
		{

			if (getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
			{
				getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);

				getEditEntity().setDataChegadaRNE(null);
				getEditEntity().setCondicaoRNE(null);
				getEditEntity().setNumeroRNE(null);
				getEditEntity().setOrgaoEmissorRNE(null);
				getEditEntity().setDataExpedicaoRNE(null);
				getEditEntity().setCasadoComBrasileiro(false);
				getEditEntity().setFilhosBrasileiros(false);

			}
			else if (getEditEntity().getNumeroRG() != null && !getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
			{
				getEditEntity().setOrgaoEmissorRG(MovCadCooperadoConstants.ORGAO_EMISSOR_RG);
			}
			else
			{
				getEditEntity().setOrgaoEmissorRG(null);
				getEditEntity().setEstadoOrgaoEmissorRG(null);
				getEditEntity().setDataExpedicaoRG(null);
			}

		}
		else
		{
			getEditEntity().setOrgaoEmissorRG(null);
			getEditEntity().setEstadoOrgaoEmissorRG(null);
			getEditEntity().setDataExpedicaoRG(null);
		}

	}

	/**
	 * caso o prestador seja estrangeiro e preencha o RG, habilita os campos data expedição, estado orgão
	 * emissor RG,
	 */
	// public boolean validaPreenchimentoRGEstrangeiro ()
	// {
	// if (getEditEntity().getNumeroRG() != null &&
	// !getEditEntity().getNacionalidade().getNome().equals(MovCadCooperadoConstants.BRASIL))
	// {
	// return true;
	// }
	//
	// }

	public void limpaEnderecoResidencial ()
	{
		getEditEntity().getEnderecoResidencial().setLogradouro(null);
		getEditEntity().getEnderecoResidencial().setBairro(null);
		getEditEntity().getEnderecoResidencial().setEstado(null);
		getEditEntity().getEnderecoResidencial().setMunicipio(null);
		getEditEntity().getEnderecoResidencial().setTipoLogradouro(null);
		getEditEntity().getEnderecoResidencial().setNumero(null);
		getEditEntity().getEnderecoResidencial().setComplemento(null);
	}

	/**
	 * Gerar um Curriculo novo.
	 */
	public void novaFormacao ()
	{
		movimentacaoPrestadorCurriculo = new MovimentacaoPrestadorCurriculo();
		getEditEntity().setItemCurriculo(movimentacaoPrestadorCurriculo);
	}

	/**
	 * Edita um Curriculo selecionado.
	 */
	public void editarItemCurriculo (final MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculo)
	{
		if (getEditEntity().getItemCurriculo() != null)
		{
			this.movimentacaoPrestadorCurriculo = getEditEntity().getItemCurriculo();
		}

	}

	/**
	 * Exclui o Curriculo selecionado
	 */
	public void excluirItemCurriculo ()
	{
		final MovimentacaoPrestadorCurriculo movimentacaoPrestadorCurriculoExcluido = getEditEntity().getItemCurriculo();

		getEditEntity().getMovimentacaoPrestadorCurriculos().remove(movimentacaoPrestadorCurriculoExcluido);

		getEditEntity().setItemCurriculo(new MovimentacaoPrestadorCurriculo());
	}

	/**
	 * Verifica se o cadastro não está em fase de analise, caso esteja não mostra os campos para edição
	 *
	 * @return
	 */
	public boolean isEditarCurriculo ()
	{
		if (getEditEntity().getMovimentacaoPrestadorCurriculos() != null)
		{
			if (getEditEntity().getSituacao() == null || getEditEntity().getSituacao().equals(SituacaoEnum.SALVO_PARCIALMENTE))
			{
				return true;
			}

		}
		return false;
	}

	/**
	 * Verifica se o cadastro não está em fase de analise, caso esteja não mostra os campos para edição
	 *
	 * @return
	 */
	public boolean isEditarDependente ()
	{
		if (getEditEntity().getDependentes() != null)
		{
			if (getEditEntity().getSituacao() == null || getEditEntity().getSituacao().equals(SituacaoEnum.SALVO_PARCIALMENTE))
			{
				return true;
			}

		}
		return false;
	}

	/**
	 * Verifica se o cadastro não está em fase de analise, caso esteja não mostra os campos para edição
	 *
	 * @return
	 */
	public boolean isEditarDadosAtendimento ()
	{
		if (getEditEntity().getEnderecosAtendimento() != null)
		{
			if (getEditEntity().getSituacao() == null || getEditEntity().getSituacao().equals(SituacaoEnum.SALVO_PARCIALMENTE))
			{
				return true;
			}

		}
		return false;
	}

	/**
	 * Verifica se o cadastro não está em fase de analise, caso esteja não mostra os campos para edição
	 *
	 * @return
	 */
	public boolean isEditarAnexarArquivos ()
	{

		if (getEditEntity().getAnexosList() != null)
		{
			if (getEditEntity().getSituacao() == null || getEditEntity().getSituacao().equals(SituacaoEnum.SALVO_PARCIALMENTE))
			{
				return true;
			}

		}
		return false;
	}

	/**
	 * Exclui o arquivo selecionado
	 */
	public void excluirItemArquivo (final ActionEvent event)
	{
		final String nomeParam = (String)event.getComponent().getAttributes().get("nomeParam");
		final Long logParam = (Long)event.getComponent().getAttributes().get("logParam");

		MovimentacaoPrestadorAnexo anexoDeletado = null;

		if (Validator.isNotEmpty(getEditEntity().getAnexosList()))
		{
			for (final MovimentacaoPrestadorAnexo anexo : getEditEntity().getAnexosList())
			{
				if (anexo.getCodigoLogAuditoria().equals(logParam))
				{
					final File file = new File(findPathFile() + MovCadCooperadoConstants.SEPARADOR + nomeParam);
					file.delete();
					anexoDeletado = anexo;
				}
			}

			if (anexoDeletado != null)
			{
				getEditEntity().getAnexosList().remove(anexoDeletado);
				movimentacaoPrestadorAnexoList.remove(anexoDeletado);
			}
		}
	}

	/**
	 * Gerar um Dependente novo.
	 */
	public void novoDependente ()
	{
		movimentacaoPrestadorDependente = new MovimentacaoPrestadorDependente();
	}

	/**
	 * Edita um Dependente selecionado.
	 */
	public void editarItemDependente (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente)
	{
		if (getEditEntity().getItemDependente() != null)
		{
			this.movimentacaoPrestadorDependente = getEditEntity().getItemDependente();
		}
	}

	/**
	 * Exclui o dependente selecionado
	 */
	public void excluirItemDependente ()
	{
		final MovimentacaoPrestadorDependente movimentacaoPrestadorDependenteExcluido = getEditEntity().getItemDependente();

		if (movimentacaoPrestadorDependenteExcluido.getDependentePrestadorVO() != null)
		{
			movimentacaoPrestadorDependenteExcluido.setDataExclusao(CustomDate.getCurrentTimestamp());
			movimentacaoPrestadorDependenteList.remove(movimentacaoPrestadorDependenteExcluido);
		}
		else
		{
			movimentacaoPrestadorDependenteList.remove(movimentacaoPrestadorDependenteExcluido);
			getEditEntity().getDependentes().remove(movimentacaoPrestadorDependenteExcluido);
			getEditEntity().setItemDependente(new MovimentacaoPrestadorDependente());
		}
	}

	/**
	 * Gerar um Endereco de Atendimento novo.
	 */
	public void novoEnderecoAtendimento ()
	{
		this.movimentacaoPrestadorEnderecoAtendimento = new MovimentacaoPrestadorEndereco();
		logradourosList = new ArrayList<>();
		cepPesquisa = null;
		iniciarListaHorarioAtendimento();
	}

	/**
	 * Edita um Endereco de Atendimento selecionado.
	 *
	 * @param movimentacaoPrestadorCurriculo
	 */
	public void editarEnderecoAtendimento (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimento)
	{
		if (getEditEntity().getItemEnderecoAtendimento() != null)
		{
			this.movimentacaoPrestadorEnderecoAtendimento = getEditEntity().getItemEnderecoAtendimento();
			iniciarListaHorarioAtendimento();
			logradourosList = new ArrayList<>();
			cepPesquisa = null;
		}
	}

	/**
	 * Exclui o Endereco Atendimento selecionado
	 */
	public void excluirItemEnderecoAtendimento ()
	{
		// Se soh possui um endereco de atendimento, nao pode excluir
		if (getEditEntity().getEnderecosAtendimento().size() <= 1)
		{
			messages.addErrorMessage("informativo.endereco.atendimento.proibido.excluir");
		}
		else
		{
			// Se possui varios endereco de atendimento, verifica quantos tem data de exclusao preenchida
			int enderecosDataExclusao = 0;

			for (final MovimentacaoPrestadorEndereco enderecoAtendimento : getEditEntity().getEnderecosAtendimento())
			{
				if (enderecoAtendimento.getDataExclusao() != null)
				{
					enderecosDataExclusao++;
				}
			}

			// Se possui mais de um, exclusao Ok.
			if ((getEditEntity().getEnderecosAtendimento().size() - enderecosDataExclusao) > 1)
			{
				final MovimentacaoPrestadorEndereco movimentacaoPrestadorEnderecoAtendimentoExcluido = getEditEntity().getItemEnderecoAtendimento();

				// Jah informa a data de exclusao
				movimentacaoPrestadorEnderecoAtendimentoExcluido.setDataExclusao(CustomDate.getCurrentTimestamp());

				if (movimentacaoPrestadorEnderecoAtendimentoExcluido.getId() != null &&
				    movimentacaoPrestadorEnderecoAtendimentoExcluido.getPrestadorEnderecoVO() != null)
				{
					try
					{
						movimentacaoPrestadorEnderecoService.delete(movimentacaoPrestadorEnderecoAtendimentoExcluido);
						movimentacaoPrestadorEnderecoAtendimentoList.remove(movimentacaoPrestadorEnderecoAtendimentoExcluido);
					}
					catch (final DAOException e)
					{
						messages.addErrorMessage("error.atendimento.excluir");
					}
				}
				else
				{
					movimentacaoPrestadorEnderecoAtendimentoList.remove(movimentacaoPrestadorEnderecoAtendimentoExcluido);
					getEditEntity().setItemEnderecoAtendimento(new MovimentacaoPrestadorEndereco());
				}
			}
			else
			{
				messages.addErrorMessage("informativo.endereco.atendimento.proibido.excluir");
			}
		}
	}

	/**
	 * Gerar um Horario de Atendimento novo.
	 */
	public void novoHorarioAtendimento ()
	{
		if (movimentacaoPrestadorEnderecoAtendimento != null)
		{
			if (movimentacaoPrestadorEnderecoAtendimento.getPrestadorHorarios() == null)
			{
				movimentacaoPrestadorEnderecoAtendimento.setPrestadorHorarios(new ArrayList<MovimentacaoPrestadorHorario>());
			}
			this.movimentacaoPrestadorHorario = new MovimentacaoPrestadorHorario();
			renderizarCamposDialogHorarioAtendimentoInsercao();
			desabilitarDiaHorarioAtendimento = false;
		}
	}

	/**
	 * Edita um Horario de Atendimento selecionado.
	 *
	 * @param MovimentacaoPrestadorEndereco
	 */
	public void editarHorarioAtendimento ()
	{
		if (movimentacaoPrestadorEnderecoAtendimento != null)
		{
			this.movimentacaoPrestadorHorario = movimentacaoPrestadorEnderecoAtendimento.getItemMovimentacaoPrestadorHorario();
			renderizarCamposDialogHorarioAtendimentoEdicao(movimentacaoPrestadorHorario);
			desabilitarDiaHorarioAtendimento = true;
		}
	}

	/**
	 * Exclui o Horario Atendimento selecionado
	 */
	public void excluirItemHorarioAtendimento ()
	{
		final MovimentacaoPrestadorHorario movimentacaoPrestadorHorarioExcluido = getEditEntity().getItemEnderecoAtendimento().getItemMovimentacaoPrestadorHorario();

		// Jah informa a data de exclusao
		movimentacaoPrestadorHorarioExcluido.setDataExclusao(CustomDate.getCurrentTimestamp());

		if (movimentacaoPrestadorHorarioExcluido.getId() != null && movimentacaoPrestadorHorarioExcluido.getPrestadorHorarioVO() != null)
		{
			try
			{
				movimentacaoPrestadorHorarioService.delete(movimentacaoPrestadorHorarioExcluido);
				movimentacaoPrestadorHorarioList.remove(movimentacaoPrestadorHorarioExcluido);
			}
			catch (final Exception e)
			{
				messages.addErrorMessage("error.horario.excluir");
			}
		}
		else
		{
			movimentacaoPrestadorHorarioList.remove(movimentacaoPrestadorHorarioExcluido);
			getEditEntity().getItemEnderecoAtendimento().setItemMovimentacaoPrestadorHorario(new MovimentacaoPrestadorHorario());
		}
	}

	/**
	 * Pesquisa a existencia de um determinado log auditoria.
	 *
	 * @return
	 */
	public void hasLogAuditoriaResidencialCep ()
	{
		final RequestContext context = RequestContext.getCurrentInstance();

		if ((logAuditoriaService.verificaExistenciaLogAuditoria(getEditEntity().getPrestador(), "CEP", getEditEntity().getEnderecoResidencial())))
		{
			context.execute("PF('enderecoAlteradoHoje').show();");
		}
		else
		{
			context.execute("PF('itemLogradouros').show();");
		}
	}

	public void hasLogAuditoriaAtendimentoCep ()
	{
		final RequestContext context = RequestContext.getCurrentInstance();

		if ((logAuditoriaService.verificaExistenciaLogAuditoria(getEditEntity().getPrestador(), "CEP", movimentacaoPrestadorEnderecoAtendimento)))
		{
			context.execute("PF('enderecoAlteradoHoje').show();");
		}
		else
		{
			context.execute("PF('itemLogradouros').show();");
		}
	}

	/**
	 * Salva finalizando o cadastro
	 *
	 * @return
	 */
	public String concluir ()
	{
		try
		{
			// Valida se esta tudo certo com as Qualificacoe/Curriculos
			if (validarCurriculos(getEditEntity().getMovimentacaoPrestadorCurriculos()))
			{
				// Verifica se todos os Dependentes maiores de idade possuem CPF
				if (validateCPF(getEditEntity().getDependentes()))
				{
					// Verifica se exite ao menos um Endereco de Atendimento
					if ((getEditEntity().getEnderecosAtendimento() != null) && (getEditEntity().getEnderecosAtendimento().size() > 0))
					{
						// Valida se esta tudo certo com os Enderecos de Atendimento
						if (validarDadosAtendimentos(getEditEntity().getEnderecosAtendimento()))
						{
							// Valida se existe Cnes repetido
							if (validarCnes(getEditEntity().getEnderecosAtendimento()))
							{
								// Valida de existe algum Endereco marcado como de correspondencia
								if (validarEnderecoCorrespondencia())
								{
									// Valida se esta tudo certo com os Dados Financeiros
									if (validarDadosFinanceiros(getEditEntity().getDadosFinanceiros()))
									{
										atualizaOrgaoEmissor();

										// Salva Parcialmente e gera os Logs de Auditoria
										setEditEntity(movimentacaoPrestadorService.salvarParcialMovimentacaoPrestador(getEditEntity()));

										init();

										// Verificar se os campos alterados necessitam de anexo
										if (!this.verificarCamposComAnexo())
										{
											atualizaOrgaoEmissor();
											setEditEntity(movimentacaoPrestadorService.concluirMovimentacao(getEditEntity()));
											return SUCCESS_PAGE;
										}
										else
										{
											messages.addWarnMessage("informativo.anexo.campo");
										}
									}
								}
								else
								{
									messages.addErrorMessage("error.endereco.correspondencia.vazio");
								}
							}
							else
							{
								messages.addErrorMessage("error.endereco.cnes");
							}
						}
					}
					else
					{
						messages.addErrorMessage("informativo.endereco.atendimento.vazio");
					}
				}
				else
				{
					messages.addErrorMessage("error.dependente.cpf.vazio");
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error("Erro ao Concluir o cadastro de cooperados: " + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("errors.falha.salvar.dados");
		}
		return null;
	}

	/**
	 * Verifica se há algum Cnes repetido, Caso haja lança exceção
	 *
	 * @param enderecosAtendimento
	 * @return
	 */
	private boolean validarCnes (final List<MovimentacaoPrestadorEndereco> enderecosAtendimento)
	{
		final List<Integer> listContidos = new ArrayList<Integer>();

		for (final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco : enderecosAtendimento)
		{
			if (enderecosAtendimento.contains(movimentacaoPrestadorEndereco))
			{
				if (movimentacaoPrestadorEndereco.getCnes() != null)
				{
					if (validarRepeticaoCnes(movimentacaoPrestadorEndereco.getCnes()))
					{
						if (!listContidos.contains(movimentacaoPrestadorEndereco.getCnes()))
						{
							listContidos.add(movimentacaoPrestadorEndereco.getCnes());
						}
						else
						{
							return false;
						}
					}
				}
				else
				{
					return false;
				}

			}
		}
		return true;
	}

	private boolean validarRepeticaoCnes (final Integer cnes)
	{
		if (cnes.equals(0000000) || cnes.equals(1111111) || cnes.equals(2222222) || cnes.equals(3333333) || cnes.equals(4444444) ||
		    cnes.equals(5555555) || cnes.equals(6666666) || cnes.equals(7777777) || cnes.equals(8888888) || cnes.equals(9999999))
		{
			return false;
		}
		return true;
	}

	/**
	 * Salva parcialmente o cadastro
	 */
	public void salvarParcial ()
	{
		try
		{
			atualizaOrgaoEmissor();
			movimentacaoPrestadorService.salvarParcialMovimentacaoPrestador(getEditEntity());
			init();

			messages.addInfoMessage("informativo.salvo.sucesso");
		}
		catch (final Exception e)
		{
			messages.addErrorMessage("errors.falha.salvar.dados");
		}

	}

	/**
	 * Busca o arquivo .properties que contem o diretorio onde deverão ser salvos os anexos
	 *
	 * @param dir
	 * @return
	 */
	public String findPathFile ()
	{
		return MovCadCooperadoProperties.getAnexosPath();
	}

	/**
	 * Realiza o upload do arquivo selecionado e cria a MovimentacaoPrestadorAnexo.
	 *
	 * @param event
	 */
	public void fileUploadAction (final FileUploadEvent event)
	{
		try
		{

			final String campoParam = (String)event.getComponent().getAttributes().get("campoParam");
			final Long logParam = (Long)event.getComponent().getAttributes().get("logParam");

			String path = "";
			String extensao = "";

			movimentacaoPrestadorAnexo = new MovimentacaoPrestadorAnexo();
			movimentacaoPrestadorAnexo.setDataUpload(new Date());
			movimentacaoPrestadorAnexo.setCodigoLogAuditoria(logParam);
			movimentacaoPrestadorAnexo.setMovimentacaoPrestador(getEditEntity());

			// Busca o arquivo .properties que contem o diretorio onde deverão ser salvos os anexos
			path = findPathFile() + MovCadCooperadoConstants.SEPARADOR +
			    movimentacaoPrestadorAnexo.getMovimentacaoPrestador().getCodigoPrestador().toString();

			FileUtil.createDirectoryWithNotExist(path);

			// Aqui cria o diretorio caso não exista
			final byte[] arquivo = event.getFile().getContents();

			if (event.getFile().getFileName() != null)
			{
				extensao = FilenameUtils.getExtension(event.getFile().getFileName());
			}

			final String fileName = movimentacaoPrestadorAnexo.getMovimentacaoPrestador().getCodigoPrestador().toString() + "_" +
			    getEditEntity().getId() + "_" + logParam + "_" + campoParam + "." + extensao;

			final String caminho = path + File.separator + fileName;

			// esse trecho grava o arquivo no diretório
			final FileOutputStream fos = new FileOutputStream(caminho);
			fos.write(arquivo);
			fos.close();

			movimentacaoPrestadorAnexo.setNomeArquivo(fileName);

			if (movimentacaoPrestadorAnexo != null)
			{
				for (final MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo : movimentacaoPrestadorAnexoList)
				{
					if (logParam.equals(movimentacaoPrestadorAnexo.getCodigoLogAuditoria()))
					{
						movimentacaoPrestadorAnexo.setNomeArquivo(fileName);
						movimentacaoPrestadorAnexo.setDataUpload(new Date());
					}
				}
			}

			final RequestContext context = RequestContext.getCurrentInstance();
			context.update("anexo");
			messages.addInfoMessage("upload.success", new Object[]{event.getFile().getFileName()});
		}
		catch (final Exception e)
		{
			LOG.error("Erro ao anexar arquivo: " + VExceptions.getErrorMessage(e), e);
			messages.addErrorMessage("anexo.erro");
		}
	}

	/**
	 * Metodo para validacao dos dados financeiro.
	 *
	 * @param Dependente
	 * @return boolean
	 */
	protected boolean validarDadosFinanceiros (final MovimentacaoPrestadorFinanceiro financeiro)
	{
		if (financeiro != null)
		{
			final String tabelaNome = MovCadCooperadoConstants.TABELA_SFN_CONTAFIN;

			if ((verificarCampo(tabelaNome, "BANCO") && financeiro.getBanco() == null) ||
			    (verificarCampo(tabelaNome, "AGENCIA") && financeiro.getAgencia() == null) ||
			    (verificarCampo(tabelaNome, "CONTACORRENTE") && financeiro.getContaCorrente() == null) ||
			    (verificarCampo(tabelaNome, "DV") && financeiro.getDv() == null))
			{
				messages.addErrorMessage("error.financeiro.incompleto");
				return false;
			}

			if (financeiro.getContaCorrente().length() > 8)
			{
				messages.addErrorMessage("error.financeiro.contaCorrenteInvalida");
				return false;
			}

			if (financeiro.getDv() == null || financeiro.getDv().length() == 0)
			{
				messages.addErrorMessage("error.financeiro.dvInvalido");
				return false;
			}
		}
		return true;
	}

	/**
	 * Método para validação de imagem do currículo.
	 * Se estiver tudo OK a resposta será falsa.
	 *
	 * @param Currículo
	 * @return
	 */
	public boolean renderizaAlertaCurriculo (final MovimentacaoPrestadorCurriculo curriculo)
	{
		return !validarCurriculo(curriculo);
	}

	/**
	 * Método privado de validação do curriculos.
	 * Se estiver tudo OK a resposta será verdadeira.
	 *
	 * @param listaProcedimentos
	 * @return
	 */
	protected boolean validarCurriculos (final List<MovimentacaoPrestadorCurriculo> curriculos)
	{
		boolean validate = true;
		if (Validator.isNotEmpty(curriculos))
		{
			for (final MovimentacaoPrestadorCurriculo p : curriculos)
			{
				if (!validarCurriculo(p))
				{
					messages.addErrorMessage("error.curriculos.incompleto");
					validate = false;
					break;
				}
			}
		}
		return validate;
	}

	/**
	 * Metodo para validacao dos dados do curriculo.
	 *
	 * @param Curriculo/Qualificador
	 * @return boolean
	 */
	private boolean validarCurriculo (final MovimentacaoPrestadorCurriculo curriculo)
	{
		if (curriculo != null)
		{
			final String tabelaNome = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_CURRICULO;

			if ((verificarCampo(tabelaNome, "DATAINICIAL") && curriculo.getDataInicial() == null) ||
			    (verificarCampo(tabelaNome, "DATACONCLUSAO") && curriculo.getDataConclusao() == null) ||
			    (verificarCampo(tabelaNome, "AREACURSO") && curriculo.getAreaCurso() == null) ||
			    (verificarCampo(tabelaNome, "ENTIDADEENSINO") && curriculo.getEntidadeEnsino() == null) ||
			    (verificarCampo(tabelaNome, "ENTIDADEENSINO") && curriculo.getEntidadeEnsino() == null))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Método para validação de imagem do currículo.
	 * Se estiver tudo OK a resposta será falsa.
	 *
	 * @param Currículo
	 * @return
	 */
	public boolean renderizaAlertaDependente (final MovimentacaoPrestadorDependente dependente)
	{
		return !validarDependente(dependente);
	}

	/**
	 * Metodo para validacao dos dados do dependente.
	 *
	 * @param Dependente
	 * @return boolean
	 */
	private boolean validarDependente (final MovimentacaoPrestadorDependente dependente)
	{
		if (dependente != null)
		{
			final String tabelaNome = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_DEPENDENTE;

			// Verificacao dos campos
			if ((verificarCampo(tabelaNome, "TIPODEPENDENTE") && dependente.getTipoDependente() == null) ||
			    (verificarCampo(tabelaNome, "DATANASCIMENTO") && dependente.getDataNascimento() == null) ||
			    (verificarCampo(tabelaNome, "NOME") && dependente.getNome() == null))
			{
				return false;
			}

			// Verificacao exclusiva para cpf
			if (verificarCampo(tabelaNome, "CPF") && dependente.getCpf() == null &&
			    (Util.calculaIdade(new SimpleDateFormat("dd/MM/yyyy").format(dependente.getDataNascimento()), "dd/MM/yyyy") >= 14))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Metodo para validar se existe algum dependente maior de idade sem cpf no cadastro.
	 *
	 * @param dependentes
	 * @return
	 */
	private boolean validateCPF (final List<MovimentacaoPrestadorDependente> dependentes)
	{

		for (final MovimentacaoPrestadorDependente movimentacaoPrestadorDependente : dependentes)
		{
			if (movimentacaoPrestadorDependente.getDataNascimento() != null)
			{

				if (Util.calculaIdade(new SimpleDateFormat("dd/MM/yyyy").format(movimentacaoPrestadorDependente.getDataNascimento()),
				    "dd/MM/yyyy") >= 14)
				{
					if (movimentacaoPrestadorDependente.getCpf() == null)
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Metodo para rederizar a imagem de alerta no botao de edicao do endereco de atendimento.
	 * Se estiver tudo OK a resposta sera falsa.
	 *
	 * @param Endereco de Atendimento
	 * @return
	 */
	public boolean renderizaAlertaDadosAtendimento (final MovimentacaoPrestadorEndereco atendimento)
	{
		return !validarDadosAtendimento(atendimento);
	}

	/**
	 * Metodo privado de validacao dos dados/endereco de atendimeto.
	 * Se estiver tudo OK a resposta sera verdadeira.
	 *
	 * @param lista atendimentos
	 * @return
	 */
	protected boolean validarDadosAtendimentos (final List<MovimentacaoPrestadorEndereco> atendimentos)
	{
		boolean validate = true;
		if (Validator.isNotEmpty(atendimentos))
		{
			for (final MovimentacaoPrestadorEndereco p : atendimentos)
			{
				if (!validarDadosAtendimento(p))
				{
					messages.addErrorMessage("error.atendimento.incompleto");
					validate = false;
					break;
				}
			}
		}
		return validate;
	}

	/**
	 * Metodo para validacao dos dados de atendimento.
	 *
	 * @param Endereco de Atendimento
	 * @return boolean
	 */
	private boolean validarDadosAtendimento (final MovimentacaoPrestadorEndereco atendimento)
	{
		if (atendimento != null)
		{
			final String tabelaNome = MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO;

			if ((verificarCampo(tabelaNome, "CEP") && atendimento.getCep() == null) ||
			    (verificarCampo(tabelaNome, "CNES") && atendimento.getCnes() == null) ||
			    (verificarCampo(tabelaNome, "LOGRADOURO") && atendimento.getLogradouro() == null) ||
			    (verificarCampo(tabelaNome, "NUMERO") && atendimento.getNumero() == null) ||
			    (verificarCampo(tabelaNome, "COMPLEMENTO") && atendimento.getComplemento() == null) ||
			    (verificarCampo(tabelaNome, "BAIRRO") && atendimento.getBairro() == null) ||
			    (verificarCampo(tabelaNome, "MUNICIPIO") && atendimento.getMunicipio() == null) ||
			    (verificarCampo(tabelaNome, "ESTADO") && atendimento.getEstado() == null) ||
			    (verificarCampo(tabelaNome, "DDD1") && atendimento.getDdd1() == null) ||
			    (verificarCampo(tabelaNome, "PREFIXO1") && atendimento.getPrefixo1() == null) ||
			    (verificarCampo(tabelaNome, "NUMERO1") && atendimento.getNumero1() == null) ||
			    (verificarCampo(tabelaNome, "RAMAL1") && atendimento.getRamal1() == null) ||
			    (verificarCampo(tabelaNome, "DDD2") && atendimento.getDdd2() == null) ||
			    (verificarCampo(tabelaNome, "PREFIXO2") && atendimento.getPrefixo2() == null) ||
			    (verificarCampo(tabelaNome, "NUMERO2") && atendimento.getNumero2() == null) ||
			    (verificarCampo(tabelaNome, "RAMAL2") && atendimento.getRamal2() == null) ||
			    (verificarCampo(tabelaNome, "DDD3") && atendimento.getDdd3() == null) ||
			    (verificarCampo(tabelaNome, "PREFIXO3") && atendimento.getPrefixo3() == null) ||
			    (verificarCampo(tabelaNome, "NUMERO3") && atendimento.getNumero3() == null) ||
			    (verificarCampo(tabelaNome, "RAMAL3") && atendimento.getRamal3() == null) ||
			    (verificarCampo(tabelaNome, "DDDFAX") && atendimento.getDddFax() == null) ||
			    (verificarCampo(tabelaNome, "PREFIXOFAX") && atendimento.getPrefixoFax() == null) ||
			    (verificarCampo(tabelaNome, "NUMEROFAX") && atendimento.getNumeroFax() == null))
			{
				return false;
			}
			else
			{
				// verifica se o cnes do cooperado veio do banco com valor inválido ex:9999999
				if (atendimento.getCnes() != null && !validarRepeticaoCnes(atendimento.getCnes()))
				{
					return false;
				}

				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo para verificar a existencia de endereco
	 * assinalado como de correspondencia.
	 *
	 * @return
	 */
	private boolean validarEnderecoCorrespondencia ()
	{
		if (getEditEntity().getEnderecoResidencial().getCorrespondencia())
		{
			return true;
		}

		if (Validator.isNotEmpty(getEditEntity().getEnderecosAtendimento()))
		{
			for (final MovimentacaoPrestadorEndereco enderecoAtendimento : getEditEntity().getEnderecosAtendimento())
			{
				if (enderecoAtendimento.getCorrespondencia())
				{
					return true;
				}
			}
		}

		return false;
	}

	public void limiteCaracterValidar (final FacesContext fac, final UIComponent com, final Object obj)
	{
		if (obj != null)
		{
			Object campo = String.valueOf(obj);
			if (!Validator.isEmpty((String)campo))
			{

				if (Validator.isNotEmpty((String)campo))
				{
					campo = String.valueOf(campo).trim();
				}

				if (((String)campo).length() < 8 || !containsOnlyNumbers((String)campo))
				{
					throw new ValidatorException(messages.getErrorMessage("movimentacao.prestador.dadosFinanceiros.poupanca"));
				}
			}
		}
	}

	public boolean containsOnlyNumbers (final String str)
	{
		// verifica se é vazio ou nulo
		if (str == null || str.length() == 0)
		{
			return false;
		}
		for (int i = 0; i < str.length(); i++)
		{
			// Se o digito for diferente de um digito, retorna falso.
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Metodo para verificar se existe algum campo alterado,
	 * e que necessite ter documento anexado.
	 *
	 * @return boolean
	 */
	private boolean verificarCamposComAnexo () throws Exception
	{
		final List<MovimentacaoPrestadorAnexo> campoAnexoList = movimentacaoPrestadorService.carregarListaCamposAnexo(getEditEntity());

		// Se existir algum registro, limpa e adiciona.

		movimentacaoPrestadorAnexoList.clear();
		getEditEntity().getAnexosList().clear();

		movimentacaoPrestadorAnexoList.addAll(campoAnexoList);
		getEditEntity().getAnexosList().addAll(campoAnexoList);

		if (Validator.isNotEmpty(campoAnexoList))
		{
			for (final MovimentacaoPrestadorAnexo anexo : movimentacaoPrestadorAnexoList)
			{
				// Verifica se algum anexo nao teve o upload do arquivo realizado
				if (anexo.getNomeArquivo() == null || anexo.getNomeArquivo().equals(""))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Verifica se existe algum endereco jah marcado como de correspondencia
	 *
	 * @return
	 */
	public void verificarEnderecoDeCorrespondencia ()
	{
		final RequestContext context = RequestContext.getCurrentInstance();

		if (getEditEntity().getEnderecoResidencial().getCorrespondencia())
		{
			context.execute("PF('enderecoCorrespondencia').show();");
		}

		for (final MovimentacaoPrestadorEndereco enderecoAtendimento : getEditEntity().getEnderecosAtendimento())
		{
			if (!enderecoAtendimento.equals(movimentacaoPrestadorEnderecoAtendimento))
			{
				if (enderecoAtendimento.getCorrespondencia())
				{
					context.execute("PF('enderecoCorrespondencia').show();");
				}
			}
		}
	}

	public void mudarDigitoVerificador ()
	{
		getEditEntity().getDadosFinanceiros().setDv(null);
	}

	/**
	 * Altera endereco de atendimento como de correspondencia
	 *
	 * @return
	 */
	public void alterarEnderecoAtendimentoCorrespondencia ()
	{
		getEditEntity().getEnderecoResidencial().setCorrespondencia(false);

		for (final MovimentacaoPrestadorEndereco enderecoAtendimento : getEditEntity().getEnderecosAtendimento())
		{
			if (!enderecoAtendimento.equals(movimentacaoPrestadorEnderecoAtendimento))
			{
				enderecoAtendimento.setCorrespondencia(false);
			}
		}

		messages.addInfoMessage("informativo.endereco.correspondencia.alterado");
	}

	/**
	 * Nao altera endereco de atendimento como de correspondencia
	 *
	 * @return
	 */
	public void naoAlterarEnderecoAtendimentoCorrespondencia ()
	{
		movimentacaoPrestadorEnderecoAtendimento.setCorrespondencia(false);
	}

	/**
	 * Altera endereco residencial como de correspondencia
	 *
	 * @return
	 */
	public void alterarEnderecoResidencialCorrespondencia ()
	{
		getEditEntity().getEnderecoResidencial().setCorrespondencia(true);

		for (final MovimentacaoPrestadorEndereco enderecoAtendimento : getEditEntity().getEnderecosAtendimento())
		{
			enderecoAtendimento.setCorrespondencia(false);
		}
	}

	/**
	 * Metodo para carregar a lista de agencias conforme o banco selecionado
	 *
	 * @return
	 */
	public void carregarAgenciasList (final BancoVO banco)
	{
		agenciasList = new ArrayList<AgenciaVO>();

		if (banco != null)
		{
			agenciasList = agenciaDao.pesquisarAgenciaPorBanco(banco);

			agenciasList = SortTool.sort(agenciasList, new String[]{"agencia"});
		}

	}

	/**
	 * Pesquisa lista de agencias válidas pelo banco e valida agênciadigitada
	 *
	 * @param fac
	 * @param com
	 * @param obj
	 */
	public void agenciaValidator (final FacesContext fac, final UIComponent com, final Object obj)
	{
		agenciasList = new ArrayList<AgenciaVO>();
		if (getEditEntity() != null)
		{
			if (getEditEntity().getDadosFinanceiros() != null)
			{
				if (getEditEntity().getDadosFinanceiros().getBanco() != null)
				{

					agenciasList = agenciaDao.pesquisarAgenciaPorBanco(getEditEntity().getDadosFinanceiros().getBanco());

					agenciasList = SortTool.sort(agenciasList, new String[]{"agencia"});
				}
			}
		}

		if (obj != null)
		{
			Object campo = String.valueOf(obj);
			if (!Validator.isEmpty((String)campo))
			{

				if (Validator.isNotEmpty((String)campo))
				{
					campo = String.valueOf(campo).trim();
				}

				if (Validator.isNotEmpty(agenciasList) && campo != null)
				{
					if (getEditEntity() != null && getEditEntity().getDadosFinanceiros() != null)
					{
						getEditEntity().getDadosFinanceiros().setAgencia(null);

						for (final AgenciaVO agencia : agenciasList)
						{
							if (agencia.getAgencia().equals(campo))
							{
								getEditEntity().getDadosFinanceiros().setAgencia(agencia);
							}
						}

						if (getEditEntity().getDadosFinanceiros().getAgencia() == null ||
						    getEditEntity().getDadosFinanceiros().getAgencia().getAgencia() == null)
						{
							throw new ValidatorException(messages.getErrorMessage("movimentacao.prestador.dadosFinanceiros.agencia"));
						}
					}

				}

			}
		}
	}

	public LoginBean getLoginBean ()
	{
		return loginBean;
	}

	public void setLoginBean (final LoginBean loginBean)
	{
		this.loginBean = loginBean;
	}

	public List<RacaCorEnum> getRacaCorEnumList ()
	{
		return racaCorEnumList;
	}

	public void setRacaCorEnumList (final List<RacaCorEnum> racaCorEnumList)
	{
		this.racaCorEnumList = racaCorEnumList;
	}

	public List<RNECondicaoEnum> getRneCondicaoList ()
	{
		return rneCondicaoList;
	}

	public void setRneCondicaoList (final List<RNECondicaoEnum> rneCondicaoList)
	{
		this.rneCondicaoList = rneCondicaoList;
	}

	public List<MunicipiosVO> getMunicipiosList ()
	{
		return municipiosList;
	}

	public void setMunicipiosList (final List<MunicipiosVO> municipiosList)
	{
		this.municipiosList = municipiosList;
	}

	public List<SexoEnum> getSexoList ()
	{
		return sexoList;
	}

	public void setSexoList (final List<SexoEnum> sexoList)
	{
		this.sexoList = sexoList;
	}

	public List<CategoriaCnhEnum> getCategoriaCnhList ()
	{
		return categoriaCnhList;
	}

	public void setCategoriaCnhList (final List<CategoriaCnhEnum> categoriaCnhList)
	{
		this.categoriaCnhList = categoriaCnhList;
	}

	public List<TipoDependenteVO> getTipoDependenteList ()
	{
		return tipoDependenteList;
	}

	public void setTipoDependenteList (final List<TipoDependenteVO> tipoDependenteList)
	{
		this.tipoDependenteList = tipoDependenteList;
	}

	public void setEstadosList (final List<EstadosVO> estadosList)
	{
		this.estadosList = estadosList;
	}

	public void setEstadoCivilList (final List<EstadoCivilVO> estadoCivilList)
	{
		this.estadoCivilList = estadoCivilList;
	}

	public void setPaisesList (final List<PaisesVO> paisesList)
	{
		this.paisesList = paisesList;
	}

	public List<AreaCursoVO> getAreaCursoList ()
	{
		if (Validator.isEmpty(areaCursoList))
		{
			areaCursoList = areaCursoDao.listAllOrdered(new String[]{"descricao"});

		}

		return areaCursoList;
	}

	public List<EntidadeEnsinoVO> getEntidadeEnsinoList ()
	{
		if (Validator.isEmpty(entidadeEnsinoList))
		{
			entidadeEnsinoList = entidadeEnsinoDao.findAllActives();
		}
		return entidadeEnsinoList;
	}

	public List<MovimentacaoPrestadorDependente> getMovimentacaoPrestadorDependenteList ()
	{
		return movimentacaoPrestadorDependenteList;
	}

	public void setMovimentacaoPrestadorDependenteList (final List<MovimentacaoPrestadorDependente> movimentacaoPrestadorDependenteList)
	{
		this.movimentacaoPrestadorDependenteList = movimentacaoPrestadorDependenteList;
	}

	public List<MovimentacaoPrestadorEndereco> getMovimentacaoPrestadorEnderecoAtendimentoList ()
	{
		return movimentacaoPrestadorEnderecoAtendimentoList;
	}

	public void setMovimentacaoPrestadorEnderecoAtendimentoList (
	    final List<MovimentacaoPrestadorEndereco> movimentacaoPrestadorEnderecoAtendimentoList)
	{
		this.movimentacaoPrestadorEnderecoAtendimentoList = movimentacaoPrestadorEnderecoAtendimentoList;
	}

	public List<MovimentacaoPrestadorHorario> getMovimentacaoPrestadorHorarioList ()
	{
		return movimentacaoPrestadorHorarioList;
	}

	public List<BancoVO> getBancosList ()
	{
		bancosList = new ArrayList<BancoVO>();
		bancosList = bancoDao.pesquisarBancoPorCodigo(MovCadCooperadoConstants.CODIGO_BANCO_SANTANDER);

		// Necessario para carregar o banco do prestador que jah esta no sistema...
		if (getEditEntity().getDadosFinanceiros() != null && getEditEntity().getDadosFinanceiros().getBanco() != null)
		{
			bancosList.add(getEditEntity().getDadosFinanceiros().getBanco());
		}

		return bancosList;
	}

	public List<EspecialidadeVO> getEspecialidadeList ()
	{
		if (Validator.isEmpty(especialidadeList))
		{
			especialidadeList = especialidadeDao.findEspecialidade();
		}
		if (getEditEntity().getMovimentacaoPrestadorEspecialidade() != null &&
		    getEditEntity().getMovimentacaoPrestadorEspecialidade().getEspecialidade() != null)
		{
			if (!especialidadeList.contains(getEditEntity().getMovimentacaoPrestadorEspecialidade().getEspecialidade()))
			{
				especialidadeList.add(getEditEntity().getMovimentacaoPrestadorEspecialidade().getEspecialidade());
			}
		}
		return especialidadeList;
	}

	public void setEspecialidadeList (final List<EspecialidadeVO> especialidadeList)
	{
		this.especialidadeList = especialidadeList;
	}

	public boolean isDesabilitaItem ()
	{
		if (getEditEntity().getDadosFinanceiros() != null && getEditEntity().getDadosFinanceiros().getBanco() != null)
		{
			if (MovCadCooperadoConstants.NOME_BANCO.equals(getEditEntity().getDadosFinanceiros().getBanco().getNome()))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return true;
	}

	public void setBancosList (final List<BancoVO> bancosList)
	{
		this.bancosList = bancosList;
	}

	public List<AgenciaVO> getAgenciasList ()
	{
		return agenciasList;
	}

	public void setAgenciasList (final List<AgenciaVO> agenciasList)
	{
		this.agenciasList = agenciasList;
	}

	public void setMovimentacaoPrestadorHorarioList (final List<MovimentacaoPrestadorHorario> movimentacaoPrestadorHorarioList)
	{
		this.movimentacaoPrestadorHorarioList = movimentacaoPrestadorHorarioList;
	}

	public boolean isShowHiddenConcluir ()
	{
		return showHiddenConcluir;
	}

	public void setShowHiddenConcluir (final boolean showHiddenConcluir)
	{
		this.showHiddenConcluir = showHiddenConcluir;
	}

	public boolean isHabilitaEstadoEndereco ()
	{
		return habilitaEstadoEndereco;
	}

	public void setHabilitaEstadoEndereco (final boolean habilitaEstadoEndereco)
	{
		this.habilitaEstadoEndereco = habilitaEstadoEndereco;
	}

	public boolean isCepGeral ()
	{
		return cepGeral;
	}

	public void setCepGeral (final boolean cepGeral)
	{
		this.cepGeral = cepGeral;
	}

	public boolean isEnderecoAtendimento ()
	{
		return enderecoAtendimento;
	}

	public void setEnderecoAtendimento (final boolean enderecoAtendimento)
	{
		this.enderecoAtendimento = enderecoAtendimento;
	}

	public boolean isDesabilitarDiaHorarioAtendimento ()
	{
		return desabilitarDiaHorarioAtendimento;
	}

	public void setDesabilitarDiaHorarioAtendimento (final boolean desabilitarDiaHorarioAtendimento)
	{
		this.desabilitarDiaHorarioAtendimento = desabilitarDiaHorarioAtendimento;
	}

	public boolean isRenderizarCheckBoxDomingo ()
	{
		return renderizarCheckBoxDomingo;
	}

	public void setRenderizarCheckBoxDomingo (final boolean renderizarCheckBoxDomingo)
	{
		this.renderizarCheckBoxDomingo = renderizarCheckBoxDomingo;
	}

	public boolean isRenderizarCheckBoxSegunda ()
	{
		return renderizarCheckBoxSegunda;
	}

	public void setRenderizarCheckBoxSegunda (final boolean renderizarCheckBoxSegunda)
	{
		this.renderizarCheckBoxSegunda = renderizarCheckBoxSegunda;
	}

	public boolean isRenderizarCheckBoxTerca ()
	{
		return renderizarCheckBoxTerca;
	}

	public void setRenderizarCheckBoxTerca (final boolean renderizarCheckBoxTerca)
	{
		this.renderizarCheckBoxTerca = renderizarCheckBoxTerca;
	}

	public boolean isRenderizarCheckBoxQuarta ()
	{
		return renderizarCheckBoxQuarta;
	}

	public void setRenderizarCheckBoxQuarta (final boolean renderizarCheckBoxQuarta)
	{
		this.renderizarCheckBoxQuarta = renderizarCheckBoxQuarta;
	}

	public boolean isRenderizarCheckBoxQuinta ()
	{
		return renderizarCheckBoxQuinta;
	}

	public void setRenderizarCheckBoxQuinta (final boolean renderizarCheckBoxQuinta)
	{
		this.renderizarCheckBoxQuinta = renderizarCheckBoxQuinta;
	}

	public boolean isRenderizarCheckBoxSexta ()
	{
		return renderizarCheckBoxSexta;
	}

	public void setRenderizarCheckBoxSexta (final boolean renderizarCheckBoxSexta)
	{
		this.renderizarCheckBoxSexta = renderizarCheckBoxSexta;
	}

	public boolean isRenderizarCheckBoxSabado ()
	{
		return renderizarCheckBoxSabado;
	}

	public void setRenderizarCheckBoxSabado (final boolean renderizarCheckBoxSabado)
	{
		this.renderizarCheckBoxSabado = renderizarCheckBoxSabado;
	}

	public List<LogradouroVO> getLogradourosList ()
	{
		return logradourosList;
	}

	public void setLogradourosList (final List<LogradouroVO> logradourosList)
	{
		this.logradourosList = logradourosList;
	}

	public LogradouroVO getLogradouroItem ()
	{
		return logradouroItem;
	}

	public void setLogradouroItem (final LogradouroVO logradouroItem)
	{
		this.logradouroItem = logradouroItem;
	}

	public String getCepPesquisa ()
	{
		return cepPesquisa;
	}

	public void setCepPesquisa (final String cepPesquisa)
	{
		this.cepPesquisa = cepPesquisa;
	}

	public String getUltimaMovimentacao ()
	{
		return ultimaMovimentacao;
	}

	public void setUltimaMovimentacao (final String ultimaMovimentacao)
	{
		this.ultimaMovimentacao = ultimaMovimentacao;
	}

	public String getCurrentStep ()
	{
		return currentStep;
	}

	public void setCurrentStep (final String currentStep)
	{
		this.currentStep = currentStep;
	}

	public String getTextoMensagemGenerica ()
	{
		return textoMensagemGenerica;
	}

	public void setTextoMensagemGenerica (final String textoMensagemGenerica)
	{
		this.textoMensagemGenerica = textoMensagemGenerica;
	}

	public String getTextoIntrodutorio ()
	{
		return textoIntrodutorio;
	}

	public void setTextoIntrodutorio (final String textoIntrodutorio)
	{
		this.textoIntrodutorio = textoIntrodutorio;
	}

	public List<MovimentacaoPrestadorAnexo> getMovimentacaoPrestadorAnexoList ()
	{
		return movimentacaoPrestadorAnexoList;
	}

	public void setMovimentacaoPrestadorAnexoList (final List<MovimentacaoPrestadorAnexo> movimentacaoPrestadorAnexoList)
	{
		this.movimentacaoPrestadorAnexoList = movimentacaoPrestadorAnexoList;
	}

	public MovimentacaoPrestadorAnexo getMovimentacaoPrestadorAnexo ()
	{
		return movimentacaoPrestadorAnexo;
	}

	public void setMovimentacaoPrestadorAnexo (final MovimentacaoPrestadorAnexo movimentacaoPrestadorAnexo)
	{
		this.movimentacaoPrestadorAnexo = movimentacaoPrestadorAnexo;
	}

	public UploadedFile getFile ()
	{
		return file;
	}

	public void setFile (final UploadedFile file)
	{
		this.file = file;
	}

	public Map<String, Map<String, MovimentacaoPrestadorCampo>> getMapMovimentacaoPrestadorCampo ()
	{
		return mapMovimentacaoPrestadorCampo;
	}

	public void setMapMovimentacaoPrestadorCampo (final Map<String, Map<String, MovimentacaoPrestadorCampo>> mapMovimentacaoPrestadorCampo)
	{
		this.mapMovimentacaoPrestadorCampo = mapMovimentacaoPrestadorCampo;
	}

	public String getCpfDependente ()
	{
		return cpfDependente;
	}

	public void setCpfDependente (final String cpfDependente)
	{
		this.cpfDependente = cpfDependente;
	}

	public MovimentacaoPrestadorEspecialidade getMovimentacaoPrestadorEspecialidade ()
	{
		return movimentacaoPrestadorEspecialidade;
	}

	public void setMovimentacaoPrestadorEspecialidade (final MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade)
	{
		this.movimentacaoPrestadorEspecialidade = movimentacaoPrestadorEspecialidade;
	}

	public ResourceBundle getProperties ()
	{
		return properties;
	}

	public void setProperties (final ResourceBundle properties)
	{
		this.properties = properties;
	}

	public boolean isStatus ()
	{
		return status;
	}

	public void setStatus (final boolean status)
	{
		this.status = status;
	}

	public boolean isRemoveBanco ()
	{
		return removeBanco;
	}

	public String getAgenciaAux ()
	{
		return agenciaAux;
	}

	public void setAgenciaAux (final String agenciaAux)
	{
		this.agenciaAux = agenciaAux;
	}

}
