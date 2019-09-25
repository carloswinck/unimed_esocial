/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import java.text.Collator;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 24 de jun de 2016
 */
@Entity
@Table(name = "V_PAISES")
public class PaisesVO
    extends VEntity<Long>
    implements Comparable<PaisesVO>
{

	private static final long serialVersionUID = -6456434401263318666L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "nome", insertable = false, updatable = false)
	private String nome;

	@Column(name = "sigla", insertable = false, updatable = false)
	private String sigla;

	public PaisesVO ()
	{
		// TODO Auto-generated constructor stub
	}

	public Long getHandle ()
	{
		return handle;
	}

	public void setHandle (final Long handle)
	{
		this.handle = handle;
	}

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public String getSigla ()
	{
		return sigla;
	}

	public void setSigla (final String sigla)
	{
		this.sigla = sigla;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo (final PaisesVO pais)
	{
		final Collator cot = Collator.getInstance(new Locale("pt", "BR"));
		if (pais != null)
		{
			return cot.compare(this.getNome(), pais.getNome());
		}
		else
		{
			return 0;
		}
	}

}
