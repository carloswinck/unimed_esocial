/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.precadastro.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoEmailPreCadastro;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * Servico de envio de e-mails do pre cadastro.
 * -
 * - Pre cadastros recem imputados pela Unimed
 * - Pre cadastros recusados na analise
 * - Pre cadastros concluido e com pagamento efetuado
 * -
 *
 * @author Eloi Bilek
 * @since 16 de fev de 2017
 */
@Stateless
public class RotinaEnvioEmailsPreCadastroServices
    extends DelegateCrud<MovimentacaoPrestadorPreCadastro, Long, MovimentacaoPrestadorPreCadastroDao>
{

	private static final long serialVersionUID = 579060544768176562L;

	private static final transient Logger LOG = Logger.getLogger(RotinaEnvioEmailsPreCadastroServices.class);

	@EJB
	private EmailService emailService;

	@EJB
	private MovimentacaoPrestadorPreCadastroDao movimentacaoPrestadorPreCadastroDao;

	@Override
	protected MovimentacaoPrestadorPreCadastroDao getDelegate ()
	{
		return movimentacaoPrestadorPreCadastroDao;
	}

	/**
	 * Pesquisa os Pre Cadastros recem inseridos pela Unimed Curitiba, e envia E-mail avisando.
	 */
	public void enviarEmailPreCadastroNovo ()
	{
		final List<MovimentacaoPrestadorPreCadastro> preCadastros = movimentacaoPrestadorPreCadastroDao.pesquisarPrestadorPreCadastrosEmail(
		    TipoEmailPreCadastro.PRECADASTRO_LIBERADO);

		if (Validator.isNotEmpty(preCadastros))
		{
			for (final MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro : preCadastros)
			{
				try
				{
					final boolean emailEnviado = emailService.enviarEmail(movimentacaoPrestadorPreCadastro,
					    TipoEmailPreCadastro.PRECADASTRO_LIBERADO);
					if (emailEnviado)
					{
						movimentacaoPrestadorPreCadastro.setEmailPreCadastro(true);
						this.update(movimentacaoPrestadorPreCadastro);
					}
				}
				catch (final Exception e)
				{
					LOG.error("::::Erro - enviarEmailPreCadastroNovo :" + e);
				}
			}
		}
	}

	/**
	 * Pesquisa os Pre Cadastros recusados pela Unimed Curitiba, e envia E-mail avisando.
	 */
	public void enviarEmailPreCadastroRecusado ()
	{
		final List<MovimentacaoPrestadorPreCadastro> preCadastros = movimentacaoPrestadorPreCadastroDao.pesquisarPrestadorPreCadastrosEmail(
		    TipoEmailPreCadastro.PRECADASTRO_RECUSADO);

		if (Validator.isNotEmpty(preCadastros))
		{
			for (final MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro : preCadastros)
			{
				try
				{
					final boolean emailEnviado = emailService.enviarEmail(movimentacaoPrestadorPreCadastro,
					    TipoEmailPreCadastro.PRECADASTRO_RECUSADO);
					if (emailEnviado)
					{
						movimentacaoPrestadorPreCadastro.setEmailPreCadastroRecusado(true);
						this.update(movimentacaoPrestadorPreCadastro);
					}
				}
				catch (final Exception e)
				{
					LOG.error("::::Erro - enviarEmailPreCadastroRecusado :" + e);
				}
			}
		}
	}

	/**
	 * Pesquisa os Pre Cadastros aprovados pela Unimed Curitiba, e envia E-mail avisando.
	 */
	public void enviarEmailPreCadastroAprovado ()
	{
		final List<MovimentacaoPrestadorPreCadastro> preCadastros = movimentacaoPrestadorPreCadastroDao.pesquisarPrestadorPreCadastrosEmail(
		    TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO);

		if (Validator.isNotEmpty(preCadastros))
		{
			for (final MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro : preCadastros)
			{
				try
				{
					final boolean emailEnviado = emailService.enviarEmail(movimentacaoPrestadorPreCadastro,
					    TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO);
					if (emailEnviado)
					{
						movimentacaoPrestadorPreCadastro.setEmailPreCadastroAprovado(true);
						this.update(movimentacaoPrestadorPreCadastro);
					}
				}
				catch (final Exception e)
				{
					LOG.error("::::Erro - enviarEmailPreCadastroAprovado :" + e);
				}
			}
		}
	}
}
