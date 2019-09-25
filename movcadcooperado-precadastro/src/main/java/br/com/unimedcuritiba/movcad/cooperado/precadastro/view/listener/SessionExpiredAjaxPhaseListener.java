/*
 * %W% %E%
 *
 * Copyrith (c) 2013, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.precadastro.view.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.log4j.Logger;
import br.com.visionnaire.core.logger.LoggerFactory;

/**
 * Adicionar no aquivo faces-config.xml em caso de necessidade para redirecionamento de
 * sessão de expirada em requisições AJAX via JSF. Projeto deve conter um outcome chamado
 * expired_session.
 *
 * <pre>
 * &lt;lifecycle&gt;
 *     	<phase-listener>br.com.visionnaire.core.jsf.listener.SessionExpiredAjaxPhaseListener</phase-listener>
 * &lt;/lifecycle&gt;
 * </pre>
 *
 * @author Paulo Roberto Schwertner
 * @since VCore 0.0.4
 */
public class SessionExpiredAjaxPhaseListener
    implements PhaseListener
{

	private static final String EXPIRED_SESSION = "expired_session";

	private static final long serialVersionUID = -7143748479751449961L;

	private final Logger log = LoggerFactory.createLogger(getClass());

	@Override
	public void afterPhase (final PhaseEvent event)
	{
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void beforePhase (final PhaseEvent event)
	{
		final FacesContext currentInstance = FacesContext.getCurrentInstance();

		if (currentInstance != null && currentInstance.getPartialViewContext() != null && currentInstance.getPartialViewContext().isAjaxRequest() &&
		    !currentInstance.getExternalContext().getSessionMap().containsKey("cpf"))
		{
			log.info("AJAX - Sessao Expirada - Redirecionando para a tela de Erro.");

			try
			{
				final NavigationHandler navigationHandler = event.getFacesContext().getApplication().getNavigationHandler();

				navigationHandler.handleNavigation(event.getFacesContext(), null, EXPIRED_SESSION);
			}
			catch (final Throwable t)
			{
				log.error("Erro ao redirecionar para tela de Sessao Expirada: ", t);
			}
		}
	}

	@Override
	public PhaseId getPhaseId ()
	{
		return PhaseId.RESTORE_VIEW;
	}

}
