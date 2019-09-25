package br.com.unimedcuritiba.core.dao;

import java.sql.Connection;
import javax.persistence.EntityManager;
import org.hibernate.Session;

/**
 * Hibernate helper class, manipula SessionFactory, Session e Transaction via
 * ThreadLocal API.
 */
public class HibernateUtil
{

	/**
	 * Obtendo a conexão via EntityManager JPA
	 * Método a ser utilizado temporariamente até a migração do SCE para JPA 2.1
	 *
	 * @param entityManager
	 * @return
	 */
	public final static Connection getConnection (final EntityManager entityManager)
	{
		if (entityManager != null)
		{
			final Session session = entityManager.unwrap(Session.class);
			return ((org.hibernate.internal.SessionImpl)session).connection();
		}
		return null;
	}

}
