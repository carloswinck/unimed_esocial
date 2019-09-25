/*
 * %W% %E%
 *
 * Copyright (c) 2013, CAIXA Econômica Federal
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.core.util;

import org.apache.commons.lang.StringUtils;
import br.com.visionnaire.core.common.validations.Validator;

/**
 * Classe utilitária para Formatação.
 *
 * @author Paulo Roberto Schwertner
 * @since 1.0.0
 * @see org.apache.camel.dataformat.bindy.Format
 */
public final class FormatUtil
{

	private FormatUtil ()
	{
		super();
	}

	/**
	 * Char 0 para padding de campos numericos
	 */
	public static final char NUMERIC_PAD_CHAR = '0';

	/**
	 * Char espaço para padding de campos alfanumericos
	 */
	public static final char TEXT_PAD_CHAR = ' ';

	/**
	 * Geracao do padding a esquerda
	 */
	public static final char LEFT_ALIGN = 'L';

	/**
	 * Geracao do padding a direita
	 */
	public static final char RIGHT_ALIGN = 'R';

	/**
	 * @param value
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static String leftPad (final String value, final int size, final char padChar)
	{
		if (Validator.isEmpty(value))
		{
			return StringUtils.leftPad("", size, padChar);
		}
		else
		{
			return StringUtils.leftPad(value, size, padChar);
		}
	}

	/**
	 * @param value
	 * @param size
	 * @return
	 */
	public static String padText (final String value, final int size)
	{
		return padText(value, size, false);
	}

	/**
	 * @param value
	 * @param size
	 * @param replaceInvalidChars
	 * @return
	 */
	public static String padText (final String value, final int size, final boolean replaceInvalidChars)
	{
		return padText(value, size, replaceInvalidChars, null);
	}

	/**
	 * @param value
	 * @param size
	 * @param replaceInvalidChars
	 * @param emptyValue
	 * @return
	 */
	public static String padText (final String value, final int size, final boolean replaceInvalidChars, final String emptyValue)
	{
		String newValue = null;

		if (replaceInvalidChars)
		{
			newValue = replaceInvalidChars(value);
		}
		else
		{
			newValue = value;
		}

		if (Validator.isEmpty(newValue) && !Validator.isEmpty(emptyValue))
		{
			newValue = emptyValue;
		}

		if (!Validator.isEmpty(newValue) && newValue.length() > size)
		{
			newValue = substring(newValue, 0, size, true);
		}

		return rightPad(newValue, size, TEXT_PAD_CHAR);
	}

	/**
	 * @param value
	 * @param size
	 * @return
	 */
	public static String padNumber (final String value, final int size)
	{
		String newValue = value;

		if (!Validator.isEmpty(newValue) && newValue.length() > size)
		{
			newValue = substring(newValue, 0, size, true);
		}

		return leftPad(newValue, size, NUMERIC_PAD_CHAR);
	}

	/**
	 * @param value
	 * @param size
	 * @return
	 */
	public static String padNumber (final Number number, final int size)
	{
		String value = null;

		if (number != null)
		{
			value = number.toString();
		}

		return padNumber(value, size);
	}

	/**
	 * @param number
	 * @param size
	 * @param nullValuePadChar Char de padding caso o valor seja nulo
	 * @return
	 */
	public static String padNumber (final Number number, final int size, final char nullValuePadChar)
	{
		String value = null;
		if (number != null)
		{
			value = number.toString();

			if (!Validator.isEmpty(value) && value.length() > size)
			{
				value = substring(value, 0, size, true);
			}

		}
		return leftPad(value, size, (value != null ? NUMERIC_PAD_CHAR : nullValuePadChar));
	}

	/**
	 * @param value
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static String rightPad (final String value, final int size, final char padChar)
	{
		if (Validator.isEmpty(value))
		{
			return StringUtils.rightPad("", size, padChar);
		}
		else
		{
			return StringUtils.rightPad(value, size, padChar);
		}
	}

	/**
	 * @param linha
	 * @param beginIndex
	 * @param size
	 * @param trim
	 * @param replaceInvalidChars
	 * @return
	 */
	public static String substring (final String linha, final int beginIndex, final int size, final boolean trim, final boolean replaceInvalidChars)
	{
		if (!Validator.isEmpty(linha))
		{
			String record = linha.substring(beginIndex, beginIndex + size);

			if (trim)
			{
				record = record.trim();
			}

			if (replaceInvalidChars)
			{
				record = replaceInvalidChars(record);
			}

			return record;
		}
		return null;
	}

	/**
	 * @param linha
	 * @param beginIndex
	 * @param size
	 * @param trim
	 * @return
	 */
	public static String substring (final String linha, final int beginIndex, final int size, final boolean trim)
	{
		return substring(linha, beginIndex, size, trim, false);
	}

	/**
	 * Substitui todos os caracteres acentuados e ç para caracteres normais
	 *
	 * @param value
	 * @return
	 */
	public static String replaceInvalidChars (final String value)
	{
		String newValue = value;
		if (!Validator.isEmpty(newValue))
		{
			newValue = newValue.trim();
			newValue = newValue.toLowerCase();
			newValue = newValue.replaceAll("ç", "c");
			newValue = newValue.replaceAll("ñ", "n");
			newValue = newValue.replaceAll("â", "a");
			newValue = newValue.replaceAll("ã", "a");
			newValue = newValue.replaceAll("á", "a");
			newValue = newValue.replaceAll("à", "a");
			newValue = newValue.replaceAll("å", "a");
			newValue = newValue.replaceAll("Å", "o");
			newValue = newValue.replaceAll("€", "c");
			newValue = newValue.replaceAll(String.valueOf((char)128), "c");
			newValue = newValue.replaceAll("¢", "c");
			newValue = newValue.replaceAll("é", "e");
			newValue = newValue.replaceAll("ê", "e");
			newValue = newValue.replaceAll("è", "e");
			newValue = newValue.replaceAll("ë", "e");
			newValue = newValue.replaceAll("í", "i");
			newValue = newValue.replaceAll("ì", "i");
			newValue = newValue.replaceAll("î", "i");
			newValue = newValue.replaceAll("ï", "i");
			newValue = newValue.replaceAll("ó", "o");
			newValue = newValue.replaceAll("ô", "o");
			newValue = newValue.replaceAll("õ", "o");
			newValue = newValue.replaceAll("ò", "o");
			newValue = newValue.replaceAll("ö", "o");
			newValue = newValue.replaceAll("ú", "u");
			newValue = newValue.replaceAll("ù", "u");
			newValue = newValue.replaceAll("û", "u");
			newValue = newValue.replaceAll("ü", "u");
			newValue = newValue.replaceAll("&#924;", "a");
			newValue = newValue.replaceAll("&#8218;", "e");

			final String invalidchars = "!\"#$%&\\'()*+,-./:;<=>?@[]\\^_`{|}~";

			// final char[] invalidCharsArray = invalidchars.toCharArray();

			// final int[] invalidArray = ]) invalidCharsArray;

			// isto deve resolver todos os caracteres inválidos
			final char[] charArray = newValue.toCharArray();

			final StringBuffer sb = new StringBuffer();

			for (final char c : charArray)
			{
				final int nv = c;
				if (nv > 30 && nv < 126)
				{
					sb.append(c);
				}
				else
				{
					sb.append(" ");
				}
			}
			newValue = sb.toString();

			for (int i = 0; i < invalidchars.length(); i++)
			{
				if (newValue.indexOf(invalidchars.charAt(i)) != -1)
				{
					final int index = newValue.indexOf(invalidchars.charAt(i));
					newValue = newValue.replace(newValue.charAt(index), ' ');
				}
			}
			newValue = newValue.toUpperCase().trim();

		}
		return newValue;
	}

	/**
	 * Converte para Integer parte de um string
	 *
	 * @param value
	 * @param beginIndex
	 * @param size
	 * @return
	 */
	public static Integer substringToInteger (final String value, final int beginIndex, final int size)
	{
		return substringToInteger(value, beginIndex, size, false);
	}

	/**
	 * Converte para Long parte de um string
	 *
	 * @param value
	 * @param beginIndex
	 * @param size
	 * @return
	 */
	public static Long substringToLong (final String value, final int beginIndex, final int size)
	{
		return substringToLong(value, beginIndex, size, false);
	}

	/**
	 * Converte para Integer parte de um string
	 *
	 * @param value
	 * @param beginIndex
	 * @param size
	 * @param zeroToNull Se valor convertido for 0 devolve nulo
	 * @return
	 */
	public static Integer substringToInteger (final String value, final int beginIndex, final int size, final boolean zeroToNull)
	{
		final Integer number = Validator.valueOfInteger(substring(value, beginIndex, size, true));

		if (zeroToNull && Validator.isEmpty(number))
		{
			return null;
		}

		return number;
	}

	/**
	 * Converte para Long parte de um string
	 *
	 * @param value
	 * @param beginIndex
	 * @param size
	 * @param zeroToNull Se valor convertido for 0 devolve nulo
	 * @return
	 */
	public static Long substringToLong (final String value, final int beginIndex, final int size, final boolean zeroToNull)
	{
		final Long number = Validator.valueOfLong(substring(value, beginIndex, size, true));

		if (zeroToNull && Validator.isEmpty(number))
		{
			return null;
		}

		return number;
	}
}
