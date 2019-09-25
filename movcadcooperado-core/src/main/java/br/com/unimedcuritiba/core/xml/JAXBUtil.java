package br.com.unimedcuritiba.core.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Classe utilit�ria para realiza��o de opera��es de marshall e unmarshall de
 * xml via JAXB
 *
 * @author Paulo Roberto Schwertner
 */
public class JAXBUtil
    extends XmlUtil
{

	private static Logger LOG = Logger.getLogger(JAXBUtil.class);

	public JAXBUtil ()
	{
		super();
	}

	/**
	 * @param xml
	 * @param schema
	 * @param contextPath
	 * @return
	 * @throws JAXBException
	 */
	public static Object unmarshal (final FileInputStream xml, final Schema schema, final String contextPath) throws JAXBException
	{
		try
		{
			final Unmarshaller unmarshaller = createUnmarshaller(contextPath);
			unmarshaller.setSchema(schema);
			return unmarshaller.unmarshal(xml);

		}
		catch (final JAXBException e)
		{
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * @param xml
	 * @param schema
	 * @param klass
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal (final FileInputStream xml, final Schema schema, final Class<T> klass) throws JAXBException
	{
		try
		{
			final Unmarshaller unmarshaller = createUnmarshaller(klass);
			unmarshaller.setSchema(schema);
			return (T)unmarshaller.unmarshal(xml);

		}
		catch (final JAXBException e)
		{
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * @param xml
	 * @param schema
	 * @param contextPath
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static Object unmarshal (final File xml, final Schema schema, final String contextPath) throws JAXBException, FileNotFoundException
	{
		return unmarshal(new FileInputStream(xml), schema, contextPath);
	}

	/**
	 * @param xml
	 * @param schema
	 * @param klass
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static <T> T unmarshal (final File xml, final Schema schema, final Class<T> klass) throws JAXBException, FileNotFoundException
	{
		return unmarshal(new FileInputStream(xml), schema, klass);
	}

	/**
	 * @param contextPath
	 * @return
	 * @throws JAXBException
	 */
	private static Unmarshaller createUnmarshaller (final String contextPath) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(contextPath);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller;
	}

	/**
	 * @param klass
	 * @return
	 * @throws JAXBException
	 */
	protected static Unmarshaller createUnmarshaller (final Class<?> klass) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(klass);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller;
	}

	/**
	 * @param jabxElement
	 * @param contextPath
	 * @param xsd
	 * @param xml
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static FileOutputStream marshal (final Object jabxElement, final String contextPath, final URL xsd, final File xml)
	    throws FileNotFoundException, JAXBException, SAXException
	{
		try
		{
			final JAXBContext context = JAXBContext.newInstance(jabxElement.getClass());
			final Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "iso-8859-1");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ans.gov.br/padroes/tiss/schemas");

			// m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new
			// com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper() {
			// public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
			// {
			// return "ans";
			// }
			// });

			final FileOutputStream fileOutputStream = new FileOutputStream(xml);
			m.marshal(jabxElement, fileOutputStream);

			return fileOutputStream;

		}
		catch (final JAXBException e)
		{
			LOG.error("", e);
			throw e;
		}
		catch (final FileNotFoundException e)
		{
			LOG.error("XML n�o encontrado", e);
			throw e;
		}
	}

	/**
	 * @param jabxElement
	 * @param contextPath
	 * @param xsd
	 * @param xml
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static OutputStream marshal (final Object jabxElement, final String contextPath, final URL xsd) throws JAXBException, SAXException
	{
		try
		{
			final Schema schema = createSchema(xsd);
			final Marshaller marshaller = createMarshaller(contextPath);
			marshaller.setSchema(schema);
			/*
			 * try {
			 * //xxxx continuar a testar!!!!!
			 * NamespacePrefixMapper npmi = new NamespacePrefixMapperImpl();
			 * marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", npmi);
			 * } catch( javax.xml.bind.PropertyException e ) {
			 * // if the JAXB provider doesn't recognize the prefix mapper,
			 * // it will throw this exception. Since being unable to specify
			 * // a human friendly prefix is not really a fatal problem,
			 * // you can just continue marshalling without failing
			 * }
			 */
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "iso-8859-1");
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ans.gov.br/padroes/tiss/schemas file:tissV2_02_01.xsd");

			final OutputStream out = new ByteArrayOutputStream();
			marshaller.marshal(jabxElement, out);

			return out;

		}
		catch (final JAXBException e)
		{
			LOG.error(e.getLocalizedMessage(), e);
			throw e;
		}
		catch (final SAXException e)
		{
			LOG.error("Error realizar parser do schema", e);
			throw e;
		}
	}

	/**
	 * @param contextPath
	 * @return
	 * @throws JAXBException
	 */
	private static Marshaller createMarshaller (final String contextPath) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(contextPath);
		final Marshaller marshaller = context.createMarshaller();
		return marshaller;
	}

	/**
	 * @param klass
	 * @return
	 * @throws JAXBException
	 */
	protected static Marshaller createMarshaller (final Class<?> klass) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(klass);
		final Marshaller marshaller = context.createMarshaller();
		return marshaller;
	}

	/**
	 * @param jabxElement
	 * @param contextPath
	 * @param xsd
	 * @param xml
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static Document marshal (final Object jabxElement, final Document document) throws JAXBException, SAXException
	{
		try
		{
			final Marshaller marshaller = createMarshaller(jabxElement.getClass());

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "iso-8859-1");
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.ans.gov.br/padroes/tiss/schemas file:tissV2_02_01.xsd");

			marshaller.marshal(jabxElement, document);

			return document;

		}
		catch (final JAXBException e)
		{
			LOG.error(e.getLocalizedMessage(), e);
			throw e;
		}
	}
}
