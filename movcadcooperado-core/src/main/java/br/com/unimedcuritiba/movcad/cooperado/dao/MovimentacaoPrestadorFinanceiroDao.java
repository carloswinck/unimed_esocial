/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorFinanceiro;

/**
 * @author Eloi Bilek
 * @since 26 de dez de 2016
 */
@Stateless
public class MovimentacaoPrestadorFinanceiroDao
    extends UnibenDao<MovimentacaoPrestadorFinanceiro, Long>
{

	private static final long serialVersionUID = -2970107940360052928L;

}
