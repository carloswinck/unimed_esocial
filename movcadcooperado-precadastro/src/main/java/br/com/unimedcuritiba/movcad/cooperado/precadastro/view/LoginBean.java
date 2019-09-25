
package br.com.unimedcuritiba.movcad.cooperado.precadastro.view;

import java.util.Map;
/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.unimedcuritiba.benner.dao.PrestadorDao;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.LoginDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.jsf.util.FacesUtil;
import br.com.visionnaire.core.view.Login;

/**
 * Login do Pre-Cadastro do Prestador
 *
 * @author Tiago Henrique Gomes da Silva Balduino
 * @since 25 de jan de 2017
 */
@ManagedBean
@SessionScoped
public class LoginBean
    extends Login
{

	private static final long serialVersionUID = 2069253541929805065L;

	@EJB
	private PrestadorDao prestadorDao;

	@EJB
	private MovimentacaoPrestadorPreCadastroDao prestadorPreCadastroDao;

	private PrestadorVO prestador;

	private MovimentacaoPrestadorPreCadastro prestadorPreCadastro;

	@EJB
	private LoginDao loginDao;

	private MovimentacaoPrestadorPreCadastroBean movimentacaoPrestadorPreCadastroBean;

	/**
	 * Chave criptografada para encaminhar prestador a funcionalidade desejada que foi enviada por email ou
	 * sms
	 */
	private String key;

	// Email usado para logar no pre cadastro
	private String email;

	// CPF usado para logar no pre cadastro
	private String cpf;

	/**
	 * Faz autenticação do usuário no banco conforme email e cpf cadastrados na carga do benner saúde
	 *
	 * @return
	 */
	public String login ()
	{
		setPrestador(null);

		movimentacaoPrestadorPreCadastroBean = (MovimentacaoPrestadorPreCadastroBean)FacesUtil.getManagedBean("movimentacaoPrestadorPreCadastroBean");

		String page = movimentacaoPrestadorPreCadastroBean.getReturnEdit();

		final Map<String, Object> sessionMap = FacesUtil.getSessionMap();

		if (email != null && cpf != null)
		{
			try
			{
				cpf = cpf.replace(".", "").replace("-", "");
				prestadorPreCadastro = prestadorPreCadastroDao.pesquisarPrestadorPreCadastro(email.toUpperCase(), cpf);

				// Limpa o bean, para nao ficar com dados de outros logins.
				movimentacaoPrestadorPreCadastroBean.clear();

				if (prestadorPreCadastro == null)
				{
					messages.addErrorMessage("login_invalido");

					page = doLogout();

					if (movimentacaoPrestadorPreCadastroBean.getEditEntity() != null)
					{
						movimentacaoPrestadorPreCadastroBean.setEditEntity(null);
					}
				}
				else
				{
					sessionMap.put("cpf", prestadorPreCadastro.getCpfPrestador());
					page = movimentacaoPrestadorPreCadastroBean.clear();
				}
			}
			catch (final Exception e)
			{
				exceptionLog("Erro ao realizar o login do cpf : " + cpf, e);

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

	public String getEmail ()
	{
		return email;
	}

	public void setEmail (final String email)
	{
		this.email = email;
	}

	public String getCpf ()
	{
		return cpf;
	}

	public void setCpf (final String cpf)
	{
		this.cpf = cpf;
	}

	public MovimentacaoPrestadorPreCadastro getPrestadorPreCadastro ()
	{
		return prestadorPreCadastro;
	}

	public void setPrestadorPreCadastro (final MovimentacaoPrestadorPreCadastro prestadorPreCadastro)
	{
		this.prestadorPreCadastro = prestadorPreCadastro;
	}

}
