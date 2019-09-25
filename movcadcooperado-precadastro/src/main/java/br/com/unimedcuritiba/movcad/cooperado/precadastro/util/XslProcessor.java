package br.com.unimedcuritiba.movcad.cooperado.precadastro.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.VExceptions;

/**
 * A classe XslProcessor é responsável pelo processamento e parser de templates XSL com XML para geracao de
 * HTMLs.
 */
public class XslProcessor
{

	private static final Logger LOG = Logger.getLogger(XslProcessor.class);

	/** Contém o XML gerado */
	private final String xml;
	/** Contém o caminho para o XSL */
	private final String pathXsl;
	/**
	 * Contém o caminho/nome do Arquivo para o processador gravar o resultado do
	 * processamento
	 */
	private String pathHtml;

	private Writer writer;

	/**
	 * Construtor que apenas delega a super classe
	 *
	 * @param xml String XML
	 * @param pathXsl String Caminho para o XSL
	 * @param writer writer
	 */
	public XslProcessor (final String xml, final String pathXsl, final Writer writer)
	{
		this.xml = xml;
		this.pathXsl = pathXsl;
		this.writer = writer;
	}

	/**
	 * Construtor que apenas delega a super classe
	 *
	 * @param xml String XML
	 * @param pathXsl String Caminho para o XSL
	 * @param pathHtml String Caminho para o arquivo que será criado
	 */
	public XslProcessor (final String xml, final String pathXsl, final String pathHtml)
	{
		this.xml = xml;
		this.pathXsl = pathXsl;
		this.pathHtml = pathHtml;
	}

	/**
	 * Método responsável pelo processamento parser e pela criação do novo HTML.
	 */
	public void processXml () throws Exception
	{
		try
		{
			final Source xmlSource = new StreamSource(new StringReader(xml.toString()));

			final Source xslSource = new StreamSource(new FileReader(pathXsl.toString()));

			final Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);

			if (writer == null)
			{
				final FileWriter out = new FileWriter(pathHtml);

				transformer.transform(xmlSource, new StreamResult(out));
			}
			else
			{
				transformer.transform(xmlSource, new StreamResult(writer));
			}
		}
		catch (final Exception e)
		{
			LOG.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	/**
	 * Processa o XML
	 *
	 * @param xmlBuffer Buffer do xml
	 * @param xsl String xml
	 * @return XML do objeto
	 * @throws Exception
	 */
	public static String processXml (final String xmlBuffer, final String xsl)
	{
		try
		{
			final StringWriter writer = new StringWriter();

			final XslProcessor xslProcessor = new XslProcessor(xmlBuffer, xsl, writer);

			xslProcessor.processXml();

			return writer.toString();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			VExceptions.handleToRuntimeException(e);
		}
		return null;
	}

	/**
	 * Cria Tag
	 *
	 * @param tag tag
	 * @param value valor
	 * @return tag XML
	 */
	public static String createTag (final String tag, final String value)
	{
		if (!Validator.isEmpty(tag))
		{
			final StringBuilder builder = new StringBuilder();

			builder.append("<").append(tag).append(">");

			builder.append((value == null ? "" : value));

			builder.append("</").append(tag).append(">");

			return builder.toString();

		}

		return null;
	}

	/**
	 * Adiciona a tag root no xml
	 *
	 * @param xml String xml
	 * @return root tag
	 */
	public static String addRootTag (final String xml)
	{
		return createTag("root", xml);
	}

}
