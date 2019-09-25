package br.com.unimedcuritiba.benner.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.vo.AreaCursoVO;

/**
 * @author Eloi Bilek
 * @since 27 de jun de 2016
 */
@Stateless
public class AreaCursoDao
    extends UnibenDao<AreaCursoVO, Long>
{

	private static final long serialVersionUID = 708787074759364250L;

}
