package br.com.unimedcuritiba.movcad.cooperado.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorDependente;

@Stateless
public class MovimentacaoPrestadorDependenteDao
    extends UnibenDao<MovimentacaoPrestadorDependente, Long>
{

	/**
	 *
	 */
	private static final long serialVersionUID = 991729823893669564L;

	/**
	 * Exclui logicamente o Endereco
	 *
	 * @param pk
	 * @return
	 */
	public boolean excluirLogicamente (final MovimentacaoPrestadorDependente t)
	{
		if (t != null)
		{
			final MovimentacaoPrestadorDependente entity = getEntityManager().getReference(getEntityClass(), t.getId());

			if (entity != null)
			{
				update(entity);
				return true;
			}
		}
		return false;
	}

}
