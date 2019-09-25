/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.vo.TipoPrestadorVO;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class TipoPrestadorDao
    extends UnibenDao<TipoPrestadorVO, Long>
{

	private static final long serialVersionUID = -5118889169221197060L;

}
