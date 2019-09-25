/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.web.view;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;
import br.com.unimedcuritiba.movcad.cooperado.web.service.LdapService;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.view.AbstractView;

/**
 * @author Paulo Roberto Schwertner
 * @since 15/05/2015
 */
@ManagedBean
@SessionScoped
public class LoginPortalBean
    extends AbstractView
{

	private static final String ACCESS_DENIED = "access_denied";

	/**
	 * ID do Perfil do usuario Cooperado no Portal Restrito Unimed
	 */
	private static final Integer PERFIL_COOPERADO = 3;

	private static final long serialVersionUID = -30070274296393585L;

	private String username;

	private String perfil;

	private String sessionId;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@EJB
	private LdapService ldapService;

	@Override
	public String perform ()
	{
		if (Validator.isNotEmpty(username) && Validator.isInt(perfil) && Validator.isNotEmpty(sessionId))
		{
			final String password = ldapService.getUserPassword(username);

			if (Validator.isNotEmpty(password))
			{
				loginBean.setUsername(username);
				loginBean.setPassword(password);

				final String page = loginBean.login();

				if (loginBean.getPrestador() == null)
				{
					return ACCESS_DENIED;
				}

				return page;
			}
			else
			{
				messages.addErrorMessage("login_invalido");
				return null;
			}
		}

		return ACCESS_DENIED;
	}

	/**
	 * @return
	 */
	public String simulate ()
	{
		if (MovCadCooperadoProperties.isDesenv())
		{
			setPerfil(PERFIL_COOPERADO.toString());
			setSessionId("teste");
			return this.perform();
		}

		return ACCESS_DENIED;
	}

	public String getPerfil ()
	{
		return perfil;
	}

	public void setPerfil (final String perfil)
	{
		this.perfil = perfil;
	}

	public String getSessionId ()
	{
		return sessionId;
	}

	public void setSessionId (final String sessionId)
	{
		this.sessionId = sessionId;
	}

	public LoginBean getLoginBean ()
	{
		return loginBean;
	}

	public void setLoginBean (final LoginBean loginBean)
	{
		this.loginBean = loginBean;
	}

	public String getUsername ()
	{
		return username;
	}

	public void setUsername (final String username)
	{
		this.username = username;
	}

}
