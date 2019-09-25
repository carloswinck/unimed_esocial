/*
 * %W% %E%
 *
 * Copyrith (c) 2015, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 */
package br.com.unimedcuritiba.benner.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import br.com.visionnaire.core.dao.CrudService;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.exception.DAOException;

/**
 * DAO abstrato do SCE
 *
 * @author Paulo Roberto Schwertner
 * @since 08/04/2015
 */
public abstract class UnibenDao<T extends VEntity<K>, K extends Serializable>
    extends CrudService<T, K>
{

	private static final long serialVersionUID = 3155042780929786554L;

	@PersistenceContext(unitName = "UnibenPersistenceUnit", type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager ()
	{
		return entityManager;
	}

	/**
	 * Método que realiza o salvar da entidade.
	 * Utilizado para manter a mesma nomenclatura da arquitetura anterior.
	 *
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public T save (final T entity) throws DAOException
	{
		return update(entity);
	}

}
