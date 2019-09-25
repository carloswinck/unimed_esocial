package br.com.unimedcuritiba.movcad.cooperado.dao;

import javax.ejb.Stateless;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorHorario;

@Stateless
public class MovimentacaoPrestadorHorarioDao
    extends UnibenDao<MovimentacaoPrestadorHorario, Long>
{

	private static final long serialVersionUID = -3078309463004590751L;

	/**
	 * Exclui logicamente o Endereco
	 *
	 * @param pk
	 * @return
	 */
	public boolean excluirLogicamente (final MovimentacaoPrestadorHorario t)
	{
		if (t != null)
		{
			final MovimentacaoPrestadorHorario entity = getEntityManager().getReference(getEntityClass(), t.getId());

			if (entity != null)
			{
				update(entity);
				return true;
			}
		}
		return false;
	}

}
