/*
 * %W% %E% Copyright (c) 2015, Unimed Curitiba Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.util;

import org.apache.log4j.Logger;

/**
 * Classe com as propriedades do SCE Honorários
 *
 * @author Paulo Roberto Schwertner
 * @since 1.0.0
 */
public final class MovCadCooperadoProperties
    implements MovCadCooperadoConstants
{

	private static final transient Logger LOG = Logger.getLogger(MovCadCooperadoProperties.class);

	private MovCadCooperadoProperties ()
	{
		super();
	}

	public static String getProperty (final String key)
	{
		if (key != null)
		{
			final String property = System.getProperty(key);

			if (property == null)
			{
				LOG.warn("Propriedade " + key + " não está configurada no sistema");
			}

			return property;
		}
		return null;
	}

	/**
	 * Sistema em desenvolvimento
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static boolean isDesenv ()
	{
		final String property = getProperty(DESENV_MODE);

		return Boolean.parseBoolean(property);
	}

	/**
	 * Sistema em modo piloto
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static boolean isPilotoMode ()
	{
		final String property = getProperty(PILOTO_MODE);

		return Boolean.parseBoolean(property);
	}

	/**
	 * Caminho dos anexos
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static String getAnexosPath ()
	{
		return getProperty(ANEXOS_PATH);
	}

	/**
	 * Email do Destinatário (utilizado para testes)
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static String getEmailTo ()
	{
		return getProperty(EMAIL_TO);
	}

	/**
	 * Email do Remetente
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static String getEmailFrom ()
	{
		return getProperty(EMAIL_FROM);
	}

	/**
	 * Email do Destinatário (utilizado para testes)
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static String getEmailToPreCad ()
	{
		return getProperty(EMAIL_TO_PRECAD);
	}

	/**
	 * Email do Remetente
	 *
	 * @see {@link MovCadCooperadoConstants}
	 * @return
	 */
	public static String getEmailFromPreCad ()
	{
		return getProperty(EMAIL_FROM_PRECAD);
	}

}
