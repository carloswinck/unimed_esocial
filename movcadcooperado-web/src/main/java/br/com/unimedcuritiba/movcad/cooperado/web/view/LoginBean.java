
package br.com.unimedcuritiba.movcad.cooperado.web.view;

/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import br.com.unimedcuritiba.benner.dao.PrestadorDao;
import br.com.unimedcuritiba.benner.util.TipoPessoa;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.jsf.util.FacesUtil;
import br.com.visionnaire.core.view.Login;

/**
 * Bean de Login no sistema
 *
 * @author Paulo Roberto Schwertner
 * @since 13/05/2015
 */
@ManagedBean
@SessionScoped
public class LoginBean
    extends Login
{

	private static final long serialVersionUID = 2069253541929805065L;

	@EJB
	private PrestadorDao prestadorDao;

	private PrestadorVO prestador;

	@Inject
	private MovimentacaoPrestadorBean movimentacaoPrestadorBean;

	/**
	 * Chave criptografada para encaminhar prestador a funcionalidade desejada que foi enviada por email ou
	 * sms
	 */
	private String key;

	public String login ()
	{
		setPrestador(null);

		super.perform();

		String page = movimentacaoPrestadorBean.getReturnEdit();

		if (getPrincipal() != null)
		{
			final String username = getUsername().trim();
			try
			{
				final Long codPrestador = Validator.parseLong(username);

				prestador = prestadorDao.findByCodigoTipoPessoa(codPrestador, TipoPessoa.Física, null);

				if (prestador == null)
				{
					messages.addErrorMessage("login_invalido");

					page = doLogout();
				}
				else
				{
					// Implementação para forçar recarregar os dados do cooperado quando loga com cooperados
					// diferentes sem fazer logoff
					final MovimentacaoPrestadorBean mb = (MovimentacaoPrestadorBean)FacesUtil.getManagedBean("movimentacaoPrestadorBean");

					// Implementado para sempre limpar o bean, forcando um nova sessao do movcadcooperado
					mb.clear();

					// if (mb != null && mb.getEditEntity() != null &&
					// !codPrestador.equals(mb.getEditEntity().getCodigoPrestador()))
					// {
					// mb.clear();
					// }

					// Verifica se veio a chave de autenticacao e envia para pagina de encaminhamento
					if (Validator.isNotEmpty(key))
					{
						return "/pages/forward.jsf?faces-redirect=true&key=" + key;
					}
				}
			}
			catch (final Exception e)
			{
				exceptionLog("Erro ao realizar o login de : " + username, e);

				e.printStackTrace();

				// TODO: verificar se retorna para a pagina de login ou para tela de erro
				VExceptions.handleToRuntimeException(e);

				return LOGIN_PAGE;
			}
		}

		return page;
	}

	public PrestadorVO getPrestador ()
	{
		return prestador;
	}

	public void setPrestador (final PrestadorVO prestadorUniben)
	{
		this.prestador = prestadorUniben;
	}

	public String getKey ()
	{
		return key;
	}

	public void setKey (final String key)
	{
		this.key = key;
	}

}
