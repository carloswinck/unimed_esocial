package br.com.unimedcuritiba.movcad.cooperado.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;

public final class Util
{

	public static boolean validarCpf (String CPF)
	{

		if (CPF != null && !CPF.equals(""))
		{
			CPF = CPF.trim().replace(".", "").replace("-", "");
		}

		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") ||
		    CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11))
		{
			return (false);
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try
		{
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++)
			{
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
			{
				dig10 = '0';
			}
			else
			{
				dig10 = (char)(r + 48); // converte no respectivo caractere numerico
			}

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++)
			{
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
			{
				dig11 = '0';
			}
			else
			{
				dig11 = (char)(r + 48);
			}

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
			{
				return (true);
			}
			else
			{
				return (false);
			}
		}
		catch (final InputMismatchException erro)
		{
			return (false);
		}
	}

	// Calcula a Idade baseado em String. Exemplo: calculaIdade("20/08/1977","dd/MM/yyyy");
	public static int calculaIdade (final String dataNasc, String formatoData)
	{

		if (formatoData == null || formatoData.equals(""))
		{
			formatoData = "dd/MM/yyyy";
		}

		final SimpleDateFormat format = new SimpleDateFormat(formatoData);
		Date dataNascInput = null;

		try
		{
			dataNascInput = format.parse(dataNasc);
		}
		catch (final Exception e)
		{
		}

		final Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(dataNascInput);
		final Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

		dateOfBirth.add(Calendar.YEAR, age);

		if (today.before(dateOfBirth))
		{
			age--;
		}

		return age;

	}

}
