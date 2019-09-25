/*
 * %W% %E%
 *
 * Copyright (c) 2017, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.vo.TipoDependenteVO;

/**
 * @author Eloi Bilek
 * @since 10 de jan de 2017
 */
@Stateless
public class TipoDependenteDao
    extends UnibenDao<TipoDependenteVO, Long>
{

	private static final long serialVersionUID = -1183075941780350280L;

}
