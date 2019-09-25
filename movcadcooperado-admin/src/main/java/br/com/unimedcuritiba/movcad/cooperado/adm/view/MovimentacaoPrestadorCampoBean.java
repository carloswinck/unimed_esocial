package br.com.unimedcuritiba.movcad.cooperado.adm.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import br.com.unimedcuritiba.movcad.cooperado.adm.service.MovimentacaoPrestadorCampoService;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorCampo;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.visionnaire.core.dao.Crud;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.view.AbstractCrudView;

/**
 * @author Diego Messias
 */
@ManagedBean
@SessionScoped
public class MovimentacaoPrestadorCampoBean
    extends AbstractCrudView<MovimentacaoPrestadorCampo, Long>
{

	private static final long serialVersionUID = 8765925419483970785L;

	@EJB
	private MovimentacaoPrestadorCampoService movimentacaoPrestadorCampoService;

	private MovimentacaoPrestadorCampo movimentacaoPrestadorCampo;

	private List<MovimentacaoPrestadorCampo> camposMovimentacaoPrestadorList;

	private List<MovimentacaoPrestadorCampo> camposPrestadorHorarioList;

	private List<MovimentacaoPrestadorCampo> camposPrestadorQualificadoresList;

	private List<MovimentacaoPrestadorCampo> camposPrestadorDependentesList;

	private List<MovimentacaoPrestadorCampo> camposPrestadorAtendimentoEnderecoList;

	private List<MovimentacaoPrestadorCampo> camposPrestadorFinanceiroList;

	private List<MovimentacaoPrestadorCampo> camposPrestadorEspecialidadeList;

	private String currentStep;

	private UploadedFile file;

	@Inject
	private Messages messages;

	@PostConstruct
	@Override
	public void init ()
	{
		super.init();

		currentStep = "dadosPessoais";

		movimentacaoPrestadorCampoService.verificarNovosCampos();

		carregarListas();
	}

	@Override
	protected Crud<MovimentacaoPrestadorCampo, Long> getCrudService ()
	{
		return movimentacaoPrestadorCampoService;
	}

	/**
	 * Carga incial das listas dos dataTables.
	 */
	private void carregarListas ()
	{
		camposMovimentacaoPrestadorList = new ArrayList<MovimentacaoPrestadorCampo>();
		camposPrestadorAtendimentoEnderecoList = new ArrayList<MovimentacaoPrestadorCampo>();
		camposPrestadorQualificadoresList = new ArrayList<MovimentacaoPrestadorCampo>();
		camposPrestadorDependentesList = new ArrayList<MovimentacaoPrestadorCampo>();
		camposPrestadorHorarioList = new ArrayList<MovimentacaoPrestadorCampo>();
		camposPrestadorFinanceiroList = new ArrayList<MovimentacaoPrestadorCampo>();
		camposPrestadorEspecialidadeList = new ArrayList<MovimentacaoPrestadorCampo>();

		camposMovimentacaoPrestadorList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SAM_PRESTADOR);
		camposPrestadorAtendimentoEnderecoList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ENDERECO);
		camposPrestadorQualificadoresList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_CURRICULO);
		camposPrestadorDependentesList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_DEPENDENTE);
		camposPrestadorHorarioList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_HORARIO);
		camposPrestadorFinanceiroList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SFN_CONTAFIN);
		camposPrestadorEspecialidadeList = movimentacaoPrestadorCampoService.pesquisarMovimentacaoPrestadorCampoPorTabela(
		    MovCadCooperadoConstants.TABELA_SAM_PRESTADOR_ESPECIALIDADE);
	}

	public void upload ()
	{
		if (file != null)
		{
			final FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	@Override
	public String clear ()
	{
		super.clear();
		return getReturnEdit();
	}

	/**
	 * Controla o fluxo de Avancar/Voltar do Wizard.
	 *
	 * @param event
	 * @return
	 */
	public String onFlowProcess (final FlowEvent event)
	{
		final String newStep = event.getNewStep();

		currentStep = newStep;

		return newStep;
	}

	/**
	 * Faz o update do campo
	 */
	public void atualizarCampo ()
	{
		if (movimentacaoPrestadorCampo != null)
		{
			try
			{
				movimentacaoPrestadorCampoService.update(movimentacaoPrestadorCampo);
				messages.addInfoMessage("informativo.campo.atualizado");
			}
			catch (final DAOException e)
			{
				log.error(e);
				messages.addErrorMessage("informativo.campo.atualizado.erro");
			}
		}
	}

	/**
	 * Gets e Sets
	 */

	public MovimentacaoPrestadorCampo getMovimentacaoPrestadorCampo ()
	{
		return movimentacaoPrestadorCampo;
	}

	public void setMovimentacaoPrestadorCampo (final MovimentacaoPrestadorCampo movimentacaoPrestadorCampo)
	{
		this.movimentacaoPrestadorCampo = movimentacaoPrestadorCampo;
	}

	public List<MovimentacaoPrestadorCampo> getCamposMovimentacaoPrestadorList ()
	{
		return camposMovimentacaoPrestadorList;
	}

	public void setCamposMovimentacaoPrestadorList (final List<MovimentacaoPrestadorCampo> camposMovimentacaoPrestadorList)
	{
		this.camposMovimentacaoPrestadorList = camposMovimentacaoPrestadorList;
	}

	public MovimentacaoPrestadorCampoService getMovimentacaoPrestadorCampoService ()
	{
		return movimentacaoPrestadorCampoService;
	}

	public void setMovimentacaoPrestadorCampoService (final MovimentacaoPrestadorCampoService movimentacaoPrestadorCampoService)
	{
		this.movimentacaoPrestadorCampoService = movimentacaoPrestadorCampoService;
	}

	public List<MovimentacaoPrestadorCampo> getCamposPrestadorHorarioList ()
	{
		return camposPrestadorHorarioList;
	}

	public void setCamposPrestadorHorarioList (final List<MovimentacaoPrestadorCampo> camposPrestadorHorarioList)
	{
		this.camposPrestadorHorarioList = camposPrestadorHorarioList;
	}

	public List<MovimentacaoPrestadorCampo> getCamposPrestadorQualificadoresList ()
	{
		return camposPrestadorQualificadoresList;
	}

	public void setCamposPrestadorQualificadoresList (final List<MovimentacaoPrestadorCampo> camposPrestadorQualificadoresList)
	{
		this.camposPrestadorQualificadoresList = camposPrestadorQualificadoresList;
	}

	public List<MovimentacaoPrestadorCampo> getCamposPrestadorDependentesList ()
	{
		return camposPrestadorDependentesList;
	}

	public void setCamposPrestadorDependentesList (final List<MovimentacaoPrestadorCampo> camposPrestadorDependentesList)
	{
		this.camposPrestadorDependentesList = camposPrestadorDependentesList;
	}

	public List<MovimentacaoPrestadorCampo> getCamposPrestadorFinanceiroList ()
	{
		return camposPrestadorFinanceiroList;
	}

	public void setCamposPrestadorFinanceiroList (final List<MovimentacaoPrestadorCampo> camposPrestadorFinanceiroList)
	{
		this.camposPrestadorFinanceiroList = camposPrestadorFinanceiroList;
	}

	public List<MovimentacaoPrestadorCampo> getCamposPrestadorEspecialidadeList ()
	{
		return camposPrestadorEspecialidadeList;
	}

	public void setCamposPrestadorEspecialidadeList (final List<MovimentacaoPrestadorCampo> camposPrestadorEspecialidadeList)
	{
		this.camposPrestadorEspecialidadeList = camposPrestadorEspecialidadeList;
	}

	public List<MovimentacaoPrestadorCampo> getCamposPrestadorAtendimentoEnderecoList ()
	{
		return camposPrestadorAtendimentoEnderecoList;
	}

	public void setCamposPrestadorAtendimentoEnderecoList (final List<MovimentacaoPrestadorCampo> camposPrestadorAtendimentoEnderecoList)
	{
		this.camposPrestadorAtendimentoEnderecoList = camposPrestadorAtendimentoEnderecoList;
	}

	public String getCurrentStep ()
	{
		return currentStep;
	}

	public void setCurrentStep (final String currentStep)
	{
		this.currentStep = currentStep;
	}

	public UploadedFile getFile ()
	{
		return file;
	}

	public void setFile (final UploadedFile file)
	{
		this.file = file;
	}

}
