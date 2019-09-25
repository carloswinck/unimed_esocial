/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.vo.CategoriaVO;

@Stateless
public class CategoriaDao
    extends UnibenDao<CategoriaVO, Long>
{

	private static final long serialVersionUID = -3186813966178224409L;

}
