/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum com as chaves para carregar as informacoes do E-mail no arquivo de propriedades.
 *
 * @author Eloi Bilek
 * @since 16 de fev de 2017
 */
public enum TipoEmailPreCadastro
{
	GENERICA(0, "", "", ""),
	PRECADASTRO_LIBERADO(1, "precad.liberado.titulo.email", "precad.liberado.informacao.email", "precad.liberado.informacao2.email"),
	PRECADASTRO_RECUSADO(2, "precad.recusado.titulo.email", "precad.recusado.informacao.email", "precad.recusado.informacao2.email"),
	PRECADASTRO_CONCLUIDO(3, "precad.concluido.titulo.email", "precad.concluido.informacao.email", "precad.concluido.informacao2.email");

	private Integer codigo;

	/*
	 * Esses atributos sao carregados do AppMessages.
	 */
	private String precadTituloEmail;
	private String precadInformacaoEmail;
	private String precadInformacao2Email;

	private TipoEmailPreCadastro (final Integer codigo, final String precadTituloEmail, final String precadInformacaoEmail,
	    final String precadInformacao2Email)
	{
		this.codigo = codigo;
		this.precadTituloEmail = precadTituloEmail;
		this.precadInformacaoEmail = precadInformacaoEmail;
		this.precadInformacao2Email = precadInformacao2Email;
	}

	private static final Map<Integer, TipoEmailPreCadastro> MAP = new HashMap<Integer, TipoEmailPreCadastro>();

	static
	{
		for (final TipoEmailPreCadastro e : EnumSet.allOf(TipoEmailPreCadastro.class))
		{
			MAP.put(e.getCodigo(), e);
		}
	}

	public static TipoEmailPreCadastro getById (final Integer id)
	{
		if (MAP.containsKey(id))
		{
			return MAP.get(id);
		}

		return null;
	}

	public static Map<Integer, TipoEmailPreCadastro> getMap ()
	{
		return MAP;
	}

	public Integer getCodigo ()
	{
		return codigo;
	}

	public void setCodigo (final Integer codigo)
	{
	}

	public String getPrecadTituloEmail ()
	{
		return precadTituloEmail;
	}

	public void setPrecadTituloEmail (final String precadTituloEmail)
	{
		this.precadTituloEmail = precadTituloEmail;
	}

	public String getPrecadInformacaoEmail ()
	{
		return precadInformacaoEmail;
	}

	public void setPrecadInformacaoEmail (final String precadInformacaoEmail)
	{
		this.precadInformacaoEmail = precadInformacaoEmail;
	}

	public String getPrecadInformacao2Email ()
	{
		return precadInformacao2Email;
	}

	public void setPrecadInformacao2Email (final String precadInformacao2Email)
	{
		this.precadInformacao2Email = precadInformacao2Email;
	}

}
