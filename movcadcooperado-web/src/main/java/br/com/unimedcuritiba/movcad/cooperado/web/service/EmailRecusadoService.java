/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.service;

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
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;
import br.com.unimedcuritiba.movcad.cooperado.web.util.XslProcessor;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.util.VReflections;

/**
 * @author Tiago Henrique Gomes da Silva Balduino
 * @since 26 de abr de 2017
 */
@Stateless
public class EmailRecusadoService
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

	public boolean enviarEmail (final MovimentacaoPrestador cadastroRecusado) throws Exception
	{
		try
		{
			final Session emailSession = this.getEmailSession();

			final MimeMessage email = new MimeMessage(emailSession);

			final URL templateURL = VReflections.getResourceAsURL("xsl/templateEmail.xsl");

			String titulo = "";

			String msg = "";

			if (log.isDebugEnabled())
			{
				log.debug("Obtendo URL do Template XSL de Email:" + templateURL);
			}

			Validator.notNull(templateURL, "Template Email");

			final String templatePath = templateURL.getPath();

			String xmlMsg = "";

			titulo = messages.getMessageFromBundle(MovCadCooperadoConstants.CADASTRO_RECUSADO_MSG_TITULO);
			msg = messages.getMessageFromBundle(MovCadCooperadoConstants.CADASTRO_RECUSADO_MSG_INICIO);
			msg += cadastroRecusado.getMotivoRecusa();
			msg += ". " + messages.getMessageFromBundle(MovCadCooperadoConstants.CADASTRO_RECUSADO_MSG_FIM);
			xmlMsg = mountXml(cadastroRecusado.getNomePrestador(), msg);

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
				mailTo = cadastroRecusado.getEmail();
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

	private String mountXml (final String prestador, final String msg)
	{

		final StringBuilder sb = new StringBuilder();

		sb.append(XslProcessor.createTag("url_cadastro", MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.LINK_ACESSO_CADASTRO)));
		sb.append("<prestador>");
		sb.append(XslProcessor.createTag("nome", prestador));
		sb.append("<msg><![CDATA[" + msg + "]]></msg>");
		sb.append("</prestador>");

		return XslProcessor.addRootTag(sb.toString());
	}

	private boolean isDesenv ()
	{
		return MovCadCooperadoProperties.isDesenv() && !MovCadCooperadoProperties.isPilotoMode();
	}

	private String getEmailFrom ()
	{
		return MovCadCooperadoProperties.getEmailFrom();
	}

	private String getEmailTo ()
	{
		return MovCadCooperadoProperties.getEmailTo();
	}
}
