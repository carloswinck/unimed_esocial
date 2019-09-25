/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.util;

import br.com.unimedcuritiba.core.util.FormatUtil;
import br.com.visionnaire.core.util.CustomDate;

/**
 * Métodos Utilitarios do sistema de Movimentação Cadastral de Cooperados
 *
 * @author Tiago Henrique Gomes da Silva Balduino
 * @since 22/06/2016
 */
public final class MovCadCooperadoUtil
{

	private MovCadCooperadoUtil ()
	{

	}

	/**
	 * Gera o numero da guia do prestador
	 *
	 * @return
	 */
	public static String createNumeroGuiaPrestador ()
	{
		final String guiaPrestador = CustomDate.formatDate(CustomDate.getCurrentDate(), "MMddHHmmss");

		return guiaPrestador;
	}

	/**
	 * Valida do digito verificar do cartao do beneficiario
	 *
	 * @param cartao
	 * @return
	 */
	public static boolean validaNumeroCartao (final String cartao)
	{
		boolean valido = false;

		if (cartao != null && cartao.length() == 17)
		{
			final String dv = cartao.substring(cartao.length() - 1);

			int soma = 0;
			int resto = 0;
			final char[] cartaoArray = cartao.toCharArray();

			soma += Integer.valueOf(cartaoArray[0] + "") * 9;
			soma += Integer.valueOf(cartaoArray[1] + "") * 8;
			soma += Integer.valueOf(cartaoArray[2] + "") * 7;
			soma += Integer.valueOf(cartaoArray[3] + "") * 6;
			soma += Integer.valueOf(cartaoArray[4] + "") * 5;
			soma += Integer.valueOf(cartaoArray[5] + "") * 4;
			soma += Integer.valueOf(cartaoArray[6] + "") * 3;
			soma += Integer.valueOf(cartaoArray[7] + "") * 2;
			soma += Integer.valueOf(cartaoArray[8] + "") * 9;
			soma += Integer.valueOf(cartaoArray[9] + "") * 8;
			soma += Integer.valueOf(cartaoArray[10] + "") * 7;
			soma += Integer.valueOf(cartaoArray[11] + "") * 6;
			soma += Integer.valueOf(cartaoArray[12] + "") * 5;
			soma += Integer.valueOf(cartaoArray[13] + "") * 4;
			soma += Integer.valueOf(cartaoArray[14] + "") * 3;
			soma += Integer.valueOf(cartaoArray[15] + "") * 2;

			resto = soma % 11;
			resto = 11 - resto;

			if (resto >= 10)
			{
				resto = 0;
			}

			if (dv.equals(resto + ""))
			{
				valido = true;
			}
		}

		return valido;
	}

	public static String gerarCodigoCarteira (final Long codigo)
	{
		final String codigoCarteira = MovCadCooperadoConstants.CODIGO_UNIMED + FormatUtil.leftPad(String.valueOf(codigo), 13, '0');
		return codigoCarteira;
	}
}
