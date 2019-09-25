package br.com.unimedcuritiba.movcad.cooperado.precadastro.view;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import br.com.unimedcuritiba.benner.dao.ConselhoDao;
import br.com.unimedcuritiba.benner.dao.EstadosDao;
import br.com.unimedcuritiba.benner.dao.LogradouroDao;
import br.com.unimedcuritiba.benner.dao.MunicipiosDao;
import br.com.unimedcuritiba.benner.vo.ConselhoVO;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.LogradouroVO;
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorCotasPartesParametrosDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorAnexoPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCotasPartesPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEnderecoPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnumPreCad;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoDocumentoAnexoPreCadEnum;
import br.com.unimedcuritiba.movcad.cooperado.precadastro.service.MovimentacaoPrestadorPreCadastroService;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.Crud;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.util.FileUtil;
import br.com.visionnaire.core.view.AbstractCrudView;

@ManagedBean
@SessionScoped
public class MovimentacaoPrestadorPreCadastroBean
    extends AbstractCrudView<MovimentacaoPrestadorPreCadastro, Long>
{

	private static final long serialVersionUID = -4110133590621673949L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorPreCadastroBean.class);

	public static final String SUCCESS_PAGE = "/pages/success.xhtml";

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	@Inject
	private Messages messages;

	@EJB
	private MovimentacaoPrestadorPreCadastroService movimentacaoPrestadorPreCadastroService;

	@EJB
	private MovimentacaoPrestadorPreCadastroDao movimentacaoPrestadorPreCadastroDao;

	@EJB
	private MovimentacaoPrestadorCotasPartesParametrosDao movimentacaoPrestadorCotasPartesParametrosDao;

	@EJB
	private LogradouroDao logradouroDao;

	@EJB
	private EstadosDao estadosDao;

	@EJB
	private MunicipiosDao municipiosDao;

	@EJB
	private ConselhoDao conselhoDao;

	private List<EstadosVO> estadosList;

	private List<MunicipiosVO> municipiosList;

	private List<LogradouroVO> logradourosList;

	private List<ConselhoVO> conselhoList;

	private List<MovimentacaoPrestadorAnexoPreCadastro> movimentacaoPrestadorAnexoPreCadastroList;

	private boolean avancar;

	private String currentStep;

	private String cepPesquisa;

	private String textoIntrodutorio;

	private LogradouroVO logradouroItem;

	private EstadosVO estadoVerificador;

	private MovimentacaoPrestadorAnexoPreCadastro movimentacaoPrestadorAnexoPreCadastro;

	private ResourceBundle properties;

	private Double valorPorParcela;

	private boolean aVista;

	private Long parcelas;

	@Override
	public String clear ()
	{
		RequestContext.getCurrentInstance().execute("PF('wiz').loadStep('dadosPessoais',true)");
		super.clear();
		currentStep = "dadosPessoais";
		return getReturnEdit();
	}

	@Override
	@PostConstruct
	public void init ()
	{
		super.init();

		if (loginBean != null)
		{
			setEditEntity(loginBean.getPrestadorPreCadastro());

			if (getEditEntity() != null)
			{
				// Valida a existencia do Endereco
				if (getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro() == null)
				{
					getEditEntity().setMovimentacaoPrestadorEnderecoPreCadastro(new MovimentacaoPrestadorEnderecoPreCadastro());
					getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setMovimentacaoPrestadorPreCadastro(getEditEntity());
				}

				// Valida a existencia do registro das Cotas Partes
				if (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro() == null)
				{
					getEditEntity().setMovimentacaoPrestadorCotasPartesPreCadastro(new MovimentacaoPrestadorCotasPartesPreCadastro());
					getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().setPagamentoAVista(null);
				}

				// Seta os paramentros das Cotas Partes.
				// Esta fora do if para sempre buscas os paramentros, e te-los atualizados.
				getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().setMovimentacaoPrestadorCotasPartesParametros(
				    movimentacaoPrestadorCotasPartesParametrosDao.findById(1L));
				getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().setMovimentacaoPrestadorPreCadastro(getEditEntity());

				if (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro() != null &&
				    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros() != null &&
				    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getValorCotasPartes() != null &&
				    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getParcelasCotasPartes() != null)
				{
					valorPorParcela = Double.valueOf(
					    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getValorCotasPartes() /
					        getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getParcelasCotasPartes());
				}

				// Seta o CRM como Conselho padrao (UCWBACCF3-14).
				getEditEntity().setConselhoRegional(conselhoDao.findById(1L));
			}
		}

		// Carrega arquivo de propriedades
		properties = ResourceBundle.getBundle("AppMessages");

		// Inicia listas
		iniciarListaAnexos();
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

		// Validacao para nao proseguir navegacao se o endereco nao foi informado.
		if (newStep.equals("dadosEspecialidade") && oldStep.equals("enderecoResidencial"))
		{
			if (getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro() == null ||
			    getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().getCep() == null)
			{
				newStep = oldStep;
				messages.addErrorMessage("informativo.endereco.residencial.vazio");
			}
		}

		// Validacao para nao proceguir navegacao se a forma de pagamento nao foi informada.
		if (newStep.equals("anexarArquivos") && oldStep.equals("dadosCotasPartes"))
		{
			if (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getPagamentoAVista() == null &&
			    (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getParcelasPagamento() == null ||
			        getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getParcelasPagamento().equals(0L)))
			{
				newStep = oldStep;
				messages.addErrorMessage("informativo.cotasPartes.vazio");
			}
		}

		currentStep = newStep;
		return newStep;
	}

	/**
	 * Verifica a situacao do cadastro, e gera a mesangem incial do sitema conforme seu valor.
	 */
	public String verificarMensagemTextoIntrodutorio ()
	{
		if (getEditEntity() != null)
		{
			final RequestContext context = RequestContext.getCurrentInstance();

			textoIntrodutorio = MovCadCooperadoConstants.CARREGANDO;

			// Verificando situacao
			if (getEditEntity().getSituacao() != null)
			{
				if (getEditEntity().getSituacao().equals(SituacaoEnumPreCad.DIGITACAO))
				{
					if (getEditEntity().getMotivoRecusa() != null)
					{
						textoIntrodutorio = getEditEntity().getMotivoRecusa();
					}
					else
					{
						context.execute("PF('msgIntrodutoriaDialog').hide();");
						return textoIntrodutorio;
					}
				}
				else if (getEditEntity().getSituacao().equals(SituacaoEnumPreCad.ANALISE))
				{
					textoIntrodutorio = properties.getString("texto.precad.analise.body");
				}
				else if (getEditEntity().getSituacao().equals(SituacaoEnumPreCad.CONCLUIDO_PENDENTE_PAGAMENTO))
				{
					textoIntrodutorio = properties.getString("texto.precad.pendente.pagamento.body");
				}
				else if (getEditEntity().getSituacao().equals(SituacaoEnumPreCad.CONCLUIDO_PAGAMENTO_EFETUADO))
				{
					textoIntrodutorio = properties.getString("texto.precad.pagamento.efetuado.body");
				}
			}
			context.execute("PF('msgIntrodutoriaDialog').show();");
			context.update("msgIntrodutoriaDialog");
		}

		return textoIntrodutorio;
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
	 * Carrega a lista manipulavel do dataTable de anexos dos documentos do Prestador.
	 */
	private void iniciarListaAnexos ()
	{
		if (getEditEntity() != null)
		{
			movimentacaoPrestadorAnexoPreCadastroList = new ArrayList<MovimentacaoPrestadorAnexoPreCadastro>();

			// Carrega a lista jah existente
			if (Validator.isNotEmpty(getEditEntity().getAnexosPreCadastroList()))
			{
				for (final MovimentacaoPrestadorAnexoPreCadastro movimentacaoPrestadorAnexoPreCadastro : getEditEntity().getAnexosPreCadastroList())
				{
					movimentacaoPrestadorAnexoPreCadastroList.add(movimentacaoPrestadorAnexoPreCadastro);
				}
			}
			// Monta a lista conforme o tipo de documento definido no Enum TipoDocumentoAnexoPreCadEnum
			else
			{
				MovimentacaoPrestadorAnexoPreCadastro movimentacaoPrestadorAnexoPreCadastro;
				for (final Map.Entry<Integer, TipoDocumentoAnexoPreCadEnum> tipoAnexo : TipoDocumentoAnexoPreCadEnum.getMap().entrySet())
				{
					if (tipoAnexo.getValue().getCodigo() != 0)
					{
						movimentacaoPrestadorAnexoPreCadastro = new MovimentacaoPrestadorAnexoPreCadastro();
						movimentacaoPrestadorAnexoPreCadastro.setCodigoTipoAnexo(tipoAnexo.getValue().getCodigo());
						movimentacaoPrestadorAnexoPreCadastro.setInformacaoCampoAnexo(tipoAnexo.getValue().getDescricao());
						movimentacaoPrestadorAnexoPreCadastro.setMovimentacaoPrestadorPreCadastro(getEditEntity());
						movimentacaoPrestadorAnexoPreCadastroList.add(movimentacaoPrestadorAnexoPreCadastro);
					}
				}
				getEditEntity().setAnexosPreCadastroList(movimentacaoPrestadorAnexoPreCadastroList);
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

	public void limpaEnderecoResidencial ()
	{
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setLogradouro(null);
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setBairro(null);
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setEstado(null);
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setMunicipio(null);
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setTipoLogradouro(null);
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setNumero(null);
		getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setComplemento(null);
	}

	/**
	 * Salva parcialmente o cadastro
	 */
	public String salvar ()
	{
		try
		{
			movimentacaoPrestadorPreCadastroService.salvar(getEditEntity());
			messages.addInfoMessage("informativo.salvo.sucesso");
			return SUCCESS_PAGE;
		}
		catch (final Exception e)
		{
			messages.addErrorMessage("errors.falha.salvar.dados");
		}
		return null;
	}

	public void adicionarEndereco ()
	{
		final RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('itemLogradouros').show();");
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
	 * Inclui os dados do logradouro selecionado no endereco de residencial.
	 */
	public void adicionaItemLogradouro ()
	{
		if (logradouroItem != null)
		{
			if (getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro() == null)
			{
				getEditEntity().setMovimentacaoPrestadorEnderecoPreCadastro(new MovimentacaoPrestadorEnderecoPreCadastro());
			}

			if (logradouroItem.getCep() != null)
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setCep(cepPesquisa);
			}
			if (logradouroItem.getLogradouro() != null)
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setLogradouro(logradouroItem.getLogradouro());
			}
			else
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setLogradouro(null);
			}
			if (logradouroItem.getBairro() != null)
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setBairro(logradouroItem.getBairro());
			}
			else
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setBairro(null);
			}
			if (logradouroItem.getEstado() != null)
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setEstado(logradouroItem.getEstado());
			}
			else
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setEstado(null);
			}
			if (logradouroItem.getMunicipio() != null)
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setMunicipio(logradouroItem.getMunicipio());
			}
			else
			{
				getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setMunicipio(null);
			}

			getEditEntity().getMovimentacaoPrestadorEnderecoPreCadastro().setTipoLogradouro(logradouroItem.getTipoLogradouro());

			messages.addInfoMessage("informativo.endereco.informado");

			logradouroItem = new LogradouroVO();
		}
	}

	public List<EstadosVO> getEstadosList ()
	{
		if (Validator.isEmpty(estadosList))
		{
			estadosList = estadosDao.findEstados();
		}
		return estadosList;
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

	public String findPathFile ()
	{
		return MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ANEXOS_PRECAD_PATH);
	}

	/**
	 * Realiza o upload do arquivo selecionado e cria a MovimentacaoPrestadorPreCadastroAnexo.
	 *
	 * @param event
	 */
	@SuppressWarnings("unused")
	public void uploadArquivoAction (final FileUploadEvent event)
	{
		try
		{
			final Integer codigoTipoAnexo = (Integer)event.getComponent().getAttributes().get("codigoParam");

			String path = "";
			String extensao = "";

			for (final MovimentacaoPrestadorAnexoPreCadastro anexo : movimentacaoPrestadorAnexoPreCadastroList)
			{
				if (anexo.getCodigoTipoAnexo().equals(codigoTipoAnexo))
				{
					movimentacaoPrestadorAnexoPreCadastro = anexo;
				}
			}

			if (loginBean != null)
			{
				path = findPathFile() + MovCadCooperadoConstants.SEPARADOR + MovCadCooperadoConstants.PASTA_PRE_CADASTRO +
				    MovCadCooperadoConstants.SEPARADOR + loginBean.getCpf().replace(".", "").replace("-", "");
				FileUtil.createDirectoryWithNotExist(path);
			}

			// Aqui cria o diretorio caso não exista
			final byte[] arquivo = event.getFile().getContents();

			if (event.getFile().getFileName() != null)
			{
				extensao = FilenameUtils.getExtension(event.getFile().getFileName());
			}

			final String fileName = loginBean.getCpf().replace(".", "").replace("-", "") + "_" + event.getFile().getFileName();

			final String caminho = path + File.separator + fileName;

			// Esse trecho grava o arquivo no diretório
			final FileOutputStream fos = new FileOutputStream(caminho);
			fos.write(arquivo);
			fos.close();

			if (movimentacaoPrestadorAnexoPreCadastro != null)
			{
				movimentacaoPrestadorAnexoPreCadastro.setNomeArquivo(fileName);
				movimentacaoPrestadorAnexoPreCadastro.setDataUpload(new Date());
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
	 * Exclui o arquivo selecionado
	 */
	public void excluirArquivo (final MovimentacaoPrestadorAnexoPreCadastro anexo)
	{
		if (anexo != null)
		{
			MovimentacaoPrestadorAnexoPreCadastro anexoDeletado = null;
			for (final MovimentacaoPrestadorAnexoPreCadastro anx : getEditEntity().getAnexosPreCadastroList())
			{
				if (anexo.equals(anx))
				{
					final File file = new File(findPathFile() + MovCadCooperadoConstants.SEPARADOR + MovCadCooperadoConstants.PASTA_PRE_CADASTRO +
					    MovCadCooperadoConstants.SEPARADOR + loginBean.getCpf().replace(".", "").replace("-", "") +
					    MovCadCooperadoConstants.SEPARADOR + anexo.getNomeArquivo());
					file.delete();
					anexoDeletado = anx;
				}
			}

			if (anexoDeletado != null)
			{
				anexo.setDataUpload(null);
				anexo.setNomeArquivo("");
			}
		}
	}

	/**
	 * Calcula o valor de cada parcela das Cotas Partes e seta forma de pagamento.
	 */
	public void calcularValorParcelasCotasPartes ()
	{
		if (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro() != null &&
		    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros() != null)
		{
			if (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getPagamentoAVista() != null &&
			    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getPagamentoAVista() == true)
			{
				getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().setParcelasPagamento(1);
				valorPorParcela = Double.valueOf(
				    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getValorCotasPartes());
			}
			else if (getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getPagamentoAVista() != null &&
			    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getPagamentoAVista() == false)
			{
				getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().setParcelasPagamento(
				    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getParcelasCotasPartes().intValue());
				valorPorParcela = Double.valueOf(
				    getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getValorCotasPartes() /
				        getEditEntity().getMovimentacaoPrestadorCotasPartesPreCadastro().getMovimentacaoPrestadorCotasPartesParametros().getParcelasCotasPartes());
			}
		}
		else
		{
			messages.addErrorMessage("error.cotasPartes.nulas");
		}
	}

	/**
	 * Realiza o logoff do Cooperado.
	 */
	public void cadastroConcluido ()
	{
		clear();
		try
		{
			loginBean.doLogout();
		}
		catch (final Exception e)
		{
			log.error("msg.erro.logoff");
		}
	}

	public List<LogradouroVO> getLogradourosList ()
	{
		return logradourosList;
	}

	public void setLogradourosList (final List<LogradouroVO> logradourosList)
	{
		this.logradourosList = logradourosList;
	}

	public List<MunicipiosVO> getMunicipiosList ()
	{
		return municipiosList;
	}

	public void setMunicipiosList (final List<MunicipiosVO> municipiosList)
	{
		this.municipiosList = municipiosList;
	}

	public List<ConselhoVO> getConselhoList ()
	{
		return conselhoList;
	}

	public void setConselhoList (final List<ConselhoVO> conselhoList)
	{
		this.conselhoList = conselhoList;
	}

	public void setEstadosList (final List<EstadosVO> estadosList)
	{
		this.estadosList = estadosList;
	}

	public String getCepPesquisa ()
	{
		return cepPesquisa;
	}

	public void setCepPesquisa (final String cepPesquisa)
	{
		this.cepPesquisa = cepPesquisa;
	}

	public String getTextoIntrodutorio ()
	{
		return textoIntrodutorio;
	}

	public void setTextoIntrodutorio (final String textoIntrodutorio)
	{
		this.textoIntrodutorio = textoIntrodutorio;
	}

	public ResourceBundle getProperties ()
	{
		return properties;
	}

	public void setProperties (final ResourceBundle properties)
	{
		this.properties = properties;
	}

	public boolean isAvancar ()
	{
		return avancar;
	}

	public void setAvancar (final boolean avancar)
	{
		this.avancar = avancar;
	}

	public String getCurrentStep ()
	{
		return currentStep;
	}

	public void setCurrentStep (final String currentStep)
	{
		this.currentStep = currentStep;
	}

	public LoginBean getLoginBean ()
	{
		return loginBean;
	}

	public void setLoginBean (final LoginBean loginBean)
	{
		this.loginBean = loginBean;
	}

	public LogradouroVO getLogradouroItem ()
	{
		return logradouroItem;
	}

	public void setLogradouroItem (final LogradouroVO logradouroItem)
	{
		this.logradouroItem = logradouroItem;
	}

	public List<MovimentacaoPrestadorAnexoPreCadastro> getMovimentacaoPrestadorAnexoPreCadastroList ()
	{
		return movimentacaoPrestadorAnexoPreCadastroList;
	}

	public void setMovimentacaoPrestadorAnexoPreCadastroList (
	    final List<MovimentacaoPrestadorAnexoPreCadastro> movimentacaoPrestadorAnexoPreCadastroList)
	{
		this.movimentacaoPrestadorAnexoPreCadastroList = movimentacaoPrestadorAnexoPreCadastroList;
	}

	public Double getValorPorParcela ()
	{
		return valorPorParcela;
	}

	public void setValorPorParcela (final Double valorPorParcela)
	{
		this.valorPorParcela = valorPorParcela;
	}

	public boolean isaVista ()
	{
		return aVista;
	}

	public void setaVista (final boolean aVista)
	{
		this.aVista = aVista;
	}

	public Long getParcelas ()
	{
		return parcelas;
	}

	public void setParcelas (final Long parcelas)
	{
		this.parcelas = parcelas;
	}

	@Override
	protected Crud<MovimentacaoPrestadorPreCadastro, Long> getCrudService ()
	{
		return movimentacaoPrestadorPreCadastroDao;
	}

}
