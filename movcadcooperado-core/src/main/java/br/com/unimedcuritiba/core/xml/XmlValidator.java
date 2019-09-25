package br.com.unimedcuritiba.core.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;

public class XmlValidator
    extends XmlUtil
{

	private static final Logger LOG = Logger.getLogger(XmlValidator.class);

	/**
	 * Valida o XML com o schema XSD.
	 *
	 * @param xml
	 *        a ser validado
	 * @param xsd
	 *        URL para o schema (arquivo principal do schema) da ANS.
	 * @return lista de erros encontrados.
	 */
	public static List<String> validate (final InputStream xml, final URL xsd)
	{

		final List<String> erros = new ArrayList<String>();
		try
		{
			final Schema schema = createSchema(xsd);
			erros.addAll(validate(xml, schema));
		}
		catch (final Throwable e)
		{
			erros.add(ErrorTranslator.translate(e));
			LOG.debug("Erro Valida��o: " + e.getMessage());
		}
		return erros;
	}

	/**
	 * Valida o XML com o schema XSD.
	 *
	 * @param xml
	 * @param schema
	 * @return
	 */
	public static List<String> validate (final InputStream xml, final Schema schema)
	{
		final StreamSource source = new StreamSource(xml);
		return validate(source, schema);
	}

	/**
	 * Valida o XML com o schema XSD.
	 *
	 * @param xml
	 *        a ser validado
	 * @param schema
	 *        Schema
	 * @return lista de erros encontrados.
	 */
	public static List<String> validate (final Source xml, final Schema schema)
	{

		final ErrorChecker checker = new ErrorChecker();
		final List<String> erros = new ArrayList<String>();
		try
		{
			final Validator validator = schema.newValidator();
			validator.setErrorHandler(checker);
			validator.validate(xml);

		}
		catch (final Throwable e)
		{
			erros.add(ErrorTranslator.translate(e));
			LOG.debug("Erro Valida��o: " + e.getMessage());
		}
		finally
		{
			// haja o que houver... pegue os erros!!!
			final List<String> erros2 = checker.getErros();
			if (erros2 != null)
			{
				for (final String me : erros2)
				{
					erros.add(ErrorTranslator.translate(me));
				}
			}
			// erros.addAll(erros2);
		}
		return erros;
	}

	/**
	 * Valida o XML com o schema XSD.
	 *
	 * @param xml
	 *        arquivo a ser validado
	 * @param schema
	 *        Schema
	 * @return lista de erros encontrados.
	 * @throws FileNotFoundException
	 */
	public static List<String> validate (final File xml, final Schema schema) throws FileNotFoundException
	{
		return validate(new FileInputStream(xml), schema);
	}

	/**
	 * Valida o object JAXB com o schema XSD.
	 *
	 * @param jaxbElement
	 * @param schema
	 * @return
	 */
	public static List<String> validate (final Object jaxbElement, final Schema schema)
	{

		try
		{
			if (jaxbElement != null)
			{
				final JAXBContext context = JAXBContext.newInstance(jaxbElement.getClass());

				final JAXBSource source = new JAXBSource(context, jaxbElement);

				return validate(source, schema);
			}

		}
		catch (final JAXBException e)
		{
			LOG.error(e.getLocalizedMessage(), e);
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * Valida object JAXB com o schema XSD.
	 *
	 * @param jaxbElement
	 *        a ser validado
	 * @param xsd
	 *        URL para o schema (arquivo principal do schema) da ANS.
	 * @return lista de erros encontrados.
	 */
	public static List<String> validate (final Object jaxbElement, final URL xsd)
	{

		final List<String> erros = new ArrayList<String>();
		Schema schema = null;
		try
		{
			schema = createSchema(xsd);
			// erros.addAll(validate(jaxbElement, schema));
		}
		catch (final Throwable e)
		{
			erros.add("149: " + ErrorTranslator.translate(e));
			LOG.debug("Erro Valida��o: " + e.getMessage());
		}
		if (schema != null)
		{
			try
			{
				// final Schema schema = createSchema(xsd);
				erros.addAll(validate(jaxbElement, schema));
				if (erros.size() > 0)
				{
					erros.add("157: validate");
				}
			}
			catch (final Throwable e)
			{
				erros.add("160: " + ErrorTranslator.translate(e));
				LOG.debug("Erro Valida��o: " + e.getMessage());
			}
		}
		return erros;
	}
}
