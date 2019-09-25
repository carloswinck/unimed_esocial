/*
 * %W% %E%
 *
 * Copyright (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;

@Stateless
public class MovimentacaoPrestadorEnderecoPreCadastroDao
    extends UnibenDao<MovimentacaoPrestadorPreCadastro, Long>
{
	private static final long serialVersionUID = 7473418060205991399L;
}
