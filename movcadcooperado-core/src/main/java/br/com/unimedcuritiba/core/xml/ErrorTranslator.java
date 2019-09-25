package br.com.unimedcuritiba.core.xml;

import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ErrorTranslator
{
	private static Logger logger = Logger.getLogger(ErrorTranslator.class);
	static XPath xpath;
	static Document doc;
	static DocumentBuilder parser;
	static boolean useCache = false;
	private static final Logger LOG = Logger.getLogger(ErrorTranslator.class);

	static
	{
		try
		{
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			xpath = XPathFactory.newInstance().newXPath();
		}
		catch (final ParserConfigurationException e)
		{
			logger.error("Problemas ao utilizar Infra XML da JVM");
		}
		if (useCache)
		{
			reloadDocument();
		}
	}

	private static void reloadDocument ()
	{
		try
		{
			doc = parser.parse(ErrorTranslator.class.getResourceAsStream("/ErrorTranslator.xml"));
		}
		catch (final Exception e)
		{
			logger.error("Problemas ao Recuperar XML de tradu��o para valida��es de Schema");
		}
	}

	public static String translate (final Throwable ex)
	{
		return translate(ex.getMessage(), Locale.getDefault());
	}

	public static String translate (final String ex)
	{
		return translate(ex, Locale.getDefault());
	}

	public static String translate (final String ex, final Locale locale)
	{

		if (!useCache)
		{
			reloadDocument();
		}

		NodeList shows = null;
		try
		{
			final String tmp[] = locale.toString().split("_");

			// pega language+country
			shows = (NodeList)xpath.evaluate("//Locale[ @language='" + tmp[0] + "' AND " + "@country='" + tmp[0] + "']/Translate", doc,
			    XPathConstants.NODESET);
			// pega s� language
			if (shows.getLength() == 0)
			{
				shows = (NodeList)xpath.evaluate("//Locale[ @language='" + tmp[0] + "']/Translate", doc, XPathConstants.NODESET);
			}

			if (shows == null)
			{
				throw new Exception("fail1");
			}
		}
		catch (final Exception e1)
		{
			// pega qualquer translate
			try
			{
				shows = (NodeList)xpath.evaluate("//Translate", doc, XPathConstants.NODESET);
				if (shows == null)
				{
					throw new Exception("fail2");
				}
			}
			catch (final Exception e2)
			{
				LOG.warn("Nao foi possivel recuperar dicionario de express�es. ", e2);
				return ex;
			}
			// Em caso de erro, mantem a mensagem como est�
		}

		String msg = ex;
		if (shows != null && msg != null)
		{
			for (int i = 0; i < shows.getLength(); i++)
			{
				final Element t = (Element)shows.item(i);
				msg = msg.replaceAll(t.getAttribute("key"), t.getAttribute("to"));
			}
		}
		return msg;
	}

	public static boolean useCache ()
	{
		return useCache;
	}

	public static void setUseCache (final boolean useCache)
	{
		ErrorTranslator.useCache = useCache;
	}
}
