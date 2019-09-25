package br.com.unimedcuritiba.core.xml;

import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import br.com.unimedcuritiba.core.exception.BusinessException;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.util.CustomDate;

public class XmlUtil
{

	/**
	 * @param schemaLocation
	 * @return
	 * @throws SAXException
	 */
	public synchronized static Schema createSchema (final String schemaLocation) throws SAXException
	{

		if (Validator.isEmpty(schemaLocation))
		{
			return null;
		}

		final File schemaFile = new File(schemaLocation);
		final SchemaFactory schemaFactory = createSchemaFactory();
		final Schema schema = schemaFactory.newSchema(schemaFile);
		return schema;
	}

	/**
	 * @param schemaLocation
	 * @return
	 * @throws SAXException
	 */
	public synchronized static Schema createSchema (final URL schemaLocation) throws SAXException
	{

		if (schemaLocation == null)
		{
			return null;
		}

		final SchemaFactory schemaFactory = createSchemaFactory();
		final Schema schema = schemaFactory.newSchema(schemaLocation);
		return schema;
	}

	/**
	 * @return
	 */
	private synchronized static SchemaFactory createSchemaFactory ()
	{
		return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	}

	/**
	 * @param date
	 * @return a data convertida para XMLGregorianCalendar - time
	 * @throws BusinessException
	 */
	public synchronized static XMLGregorianCalendar toXmlHour (final Date date) throws BusinessException
	{
		try
		{
			final Calendar cal = CustomDate.getCalendar(date);
			final int hora = cal.get(Calendar.HOUR_OF_DAY);
			final int min = cal.get(Calendar.MINUTE);
			final int sec = cal.get(Calendar.SECOND);

			final DatatypeFactory df = DatatypeFactory.newInstance();
			return df.newXMLGregorianCalendarTime(hora, min, sec, DatatypeConstants.FIELD_UNDEFINED);
		}
		catch (final DatatypeConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param date
	 * @return a data convertida para XMLGregorianCalendar - dateTime
	 * @throws BusinessException
	 */
	public synchronized static XMLGregorianCalendar toXmlDateTime (final Date date) throws BusinessException
	{
		try
		{
			final Calendar cal = CustomDate.getCalendar(date);
			final int ano = cal.get(Calendar.YEAR);
			final int mes = cal.get(Calendar.MONTH) + 1;
			final int dia = cal.get(Calendar.DAY_OF_MONTH);
			final int hora = cal.get(Calendar.HOUR_OF_DAY);
			final int min = cal.get(Calendar.MINUTE);
			final int sec = cal.get(Calendar.SECOND);

			final DatatypeFactory df = DatatypeFactory.newInstance();
			return df.newXMLGregorianCalendar(ano, mes, dia, hora, min, sec, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
		}
		catch (final DatatypeConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param date
	 * @return a data convertida para XMLGregorianCalendar - date
	 * @throws BusinessException
	 */
	public synchronized static XMLGregorianCalendar toXmlDate (final Date date) throws BusinessException
	{
		try
		{
			final Calendar cal = CustomDate.getCalendar(date);
			final int ano = cal.get(Calendar.YEAR);
			final int mes = cal.get(Calendar.MONTH) + 1;
			final int dia = cal.get(Calendar.DAY_OF_MONTH);

			final DatatypeFactory df = DatatypeFactory.newInstance();
			return df.newXMLGregorianCalendarDate(ano, mes, dia, DatatypeConstants.FIELD_UNDEFINED);
		}
		catch (final DatatypeConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Realiza o parse de um outputstream para um document
	 *
	 * @param stream
	 * @throws BusinessException
	 */
	public synchronized static Document parseStream (final OutputStream stream) throws BusinessException
	{
		if (stream != null)
		{
			try
			{
				final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setValidating(false);
				factory.setNamespaceAware(true);
				final DocumentBuilder builder = factory.newDocumentBuilder();
				return builder.parse(new InputSource(new StringReader(stream.toString())));

			}
			catch (final Exception e)
			{
				throw new BusinessException("Erro ao realizar parse do arquivo. " + e.getLocalizedMessage());
			}
		}
		return null;
	}

	/**
	 * Cria document vazio
	 *
	 * @return
	 * @throws BusinessException
	 */
	public synchronized static Document createDocument () throws BusinessException
	{
		try
		{
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.newDocument();

			return document;
		}
		catch (final Exception e)
		{
			throw new BusinessException("Erro ao criar document: " + e.getLocalizedMessage());
		}
	}

}
