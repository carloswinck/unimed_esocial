/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.precadastro.service;

import java.io.Serializable;
import java.net.URL;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.enums.TipoEmailPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.precadastro.util.XslProcessor;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.util.VReflections;

/**
 * Classe de servico para envio de emails ao cooperado
 *
 * @author Eloi Bilek
 * @since 16 de fev de 2017
 */
@Stateless
public class EmailService
    implements Serializable
{

	private static final long serialVersionUID = 6118353138757433962L;

	private static final String DEFAULT_CHARSET = "UTF-8";

	@Inject
	private transient Logger log;

	@Resource(mappedName = "java:jboss/mail/Default")
	private transient Session mailSession;

	@Inject
	private Messages messages;

	private Session getEmailSession ()
	{
		return mailSession;
	}

	public boolean enviarEmail (final MovimentacaoPrestadorPreCadastro preCadastro, final TipoEmailPreCadastro tipoEmailPreCadastro) throws Exception
	{
		try
		{
			final Session emailSession = this.getEmailSession();

			final MimeMessage email = new MimeMessage(emailSession);

			final URL templateURL = VReflections.getResourceAsURL("xsl/templateEmail.xsl");

			String titulo = "";

			String msg = "";

			String msg2 = "";

			if (log.isDebugEnabled())
			{
				log.debug("Obtendo URL do Template XSL de Email:" + templateURL);
			}

			Validator.notNull(templateURL, "Template Email");

			final String templatePath = templateURL.getPath();

			String xmlMsg = "";

			if (tipoEmailPreCadastro.equals(TipoEmailPreCadastro.PRECADASTRO_LIBERADO))
			{
				titulo = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_LIBERADO.getPrecadTituloEmail());
				msg = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_LIBERADO.getPrecadInformacaoEmail());
				msg2 = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_LIBERADO.getPrecadInformacao2Email());
				xmlMsg = mountXml(tipoEmailPreCadastro, preCadastro.getNomePrestador(), msg, msg2);
			}
			else if (tipoEmailPreCadastro.equals(TipoEmailPreCadastro.PRECADASTRO_RECUSADO))
			{
				titulo = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_RECUSADO.getPrecadTituloEmail());
				msg = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_RECUSADO.getPrecadInformacaoEmail());
				msg2 = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_RECUSADO.getPrecadInformacao2Email());
				msg += preCadastro.getMotivoRecusa() != null ? preCadastro.getMotivoRecusa() : "";
				xmlMsg = mountXml(tipoEmailPreCadastro, preCadastro.getNomePrestador(), msg, msg2);
			}
			else if (tipoEmailPreCadastro.equals(TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO))
			{
				titulo = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO.getPrecadTituloEmail());
				msg = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO.getPrecadInformacaoEmail());
				msg2 = messages.getMessageFromBundle(TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO.getPrecadInformacao2Email());
				xmlMsg = mountXml(tipoEmailPreCadastro, preCadastro.getNomePrestador(), msg, msg2);
			}

			final String msgHtml = XslProcessor.processXml(xmlMsg, templatePath);

			// Criando corpo da mensagem (com texto e html)
			final Multipart mpCorpoPrincipal = new MimeMultipart("Related");
			final MimeMultipart mpContent = new MimeMultipart("alternative");

			// A raiz para agrupar os dois tipos, texto/html
			final MimeBodyPart corpoRaiz = new MimeBodyPart();
			corpoRaiz.setContent(mpContent);

			// Adiciona a raiz à mensagem
			mpCorpoPrincipal.addBodyPart(corpoRaiz);

			// Mensagem Html
			final MimeBodyPart mbpHtml = new MimeBodyPart();
			mbpHtml.setText(msgHtml, DEFAULT_CHARSET, "html");
			mpContent.addBodyPart(mbpHtml);

			String mailTo = null;
			String mailFrom;

			if (this.isDesenv())
			{
				mailTo = this.getEmailTo();
			}
			else
			{
				mailTo = preCadastro.getEmail();
			}

			mailFrom = this.getEmailFrom();

			email.setFrom(new InternetAddress(mailFrom, messages.getMessageFromBundle("mail.from.name"), DEFAULT_CHARSET));

			if (Validator.isNotEmpty(mailTo))
			{
				if (mailTo.contains(";"))
				{
					final String[] destinatarios = mailTo.split(";");

					final InternetAddress[] address = new InternetAddress[destinatarios.length];
					for (int i = 0; i < destinatarios.length; i++)
					{
						final String destinatario = destinatarios[i];
						try
						{
							if (Validator.isNotEmpty(destinatario))
							{
								address[i] = new InternetAddress(destinatario);
							}
						}
						catch (final AddressException e)
						{
							log.error("Email inválido: " + destinatario, e);
						}
					}
					email.addRecipients(Message.RecipientType.TO, address);
				}
				else
				{
					email.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
				}
			}

			email.setSentDate(new java.util.Date());
			email.setSubject(titulo, DEFAULT_CHARSET);
			email.setContent(mpCorpoPrincipal);

			log.debug("Enviando Email para: " + mailTo);
			Transport.send(email);
			log.info("Email enviado -> " + mailTo);

			return true;
		}
		catch (final javax.mail.MessagingException e)
		{
			log.error("Error in Sending Mail: " + e, e);
			VExceptions.handleToRuntimeException(e);
		}
		finally
		{
			this.mailSession = null;
		}
		return false;
	}

	private String mountXml (final TipoEmailPreCadastro tipoEmailPreCadastro, String prestador, final String msg, final String msg2)
	{

		final StringBuilder sb = new StringBuilder();
		String nomeCooperado = messages.getMessageFromBundle("precad.head.cooperado.email");

		if (tipoEmailPreCadastro.equals(TipoEmailPreCadastro.PRECADASTRO_LIBERADO) ||
		    tipoEmailPreCadastro.equals(TipoEmailPreCadastro.PRECADASTRO_RECUSADO))
		{
			sb.append(
			    XslProcessor.createTag("url_cadastro", MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.LINK_ACESSO_PRECADASTRO)));
		}
		else if (tipoEmailPreCadastro.equals(TipoEmailPreCadastro.PRECADASTRO_CONCLUIDO))
		{
			sb.append(XslProcessor.createTag("url_cadastro",
			    MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.LINK_ACESSO_PRECADASTRO_CONCLUIDO)));
			nomeCooperado += " " + prestador;
			prestador = nomeCooperado;
		}

		sb.append(XslProcessor.createTag("url_img", MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.LINK_ACESSO_IMAGENS)));
		sb.append(XslProcessor.createTag("url", MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.LINK_ACESSO_COOPERADO)));
		sb.append("<prestador>");
		sb.append(XslProcessor.createTag("nome", prestador));
		sb.append("<msg><![CDATA[" + msg + "]]></msg>");
		sb.append("<msg2><![CDATA[" + msg2 + "]]></msg2>");
		sb.append("</prestador>");

		return XslProcessor.addRootTag(sb.toString());
	}

	private boolean isDesenv ()
	{
		return MovCadCooperadoProperties.isDesenv() && !MovCadCooperadoProperties.isPilotoMode();
	}

	private String getEmailFrom ()
	{
		return MovCadCooperadoProperties.getEmailFromPreCad();
	}

	private String getEmailTo ()
	{
		return MovCadCooperadoProperties.getEmailToPreCad();
	}

}
