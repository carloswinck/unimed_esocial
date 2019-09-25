/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.dao;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.UnibenDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.BusinessException;

/**
 * @author Tiago Henrique Gomes da Silva Balduino
 * @since 13 de jan de 2017
 */
@Stateless
public class LoginDao
    extends UnibenDao<MovimentacaoPrestadorPreCadastro, Long>
{

	private static final long serialVersionUID = -7531809969276580247L;

	private static final transient Logger LOG = Logger.getLogger(LoginDao.class);

	/**
	 * Realiza autenticação do usuário no sistema
	 *
	 * @param handlePrestador
	 * @param password
	 * @return
	 * @throws BusinessException
	 */
	public boolean verificaUsuario (final Long handlePrestador, final String password) throws BusinessException
	{
		boolean autenticado = false;

		try
		{

			if (Validator.isEmpty(handlePrestador) || Validator.isEmpty(password))
			{
				return false;
			}
			final StringBuilder sb = new StringBuilder();

			sb.append(" select a.usuarioweb from V_SAM_PRESTADOR_USUARIOSWEB a where a.prestador = ? ");

			final Query q = getEntityManager().createNativeQuery(sb.toString());

			// Primeiro Select
			q.setParameter(1, handlePrestador);

			final BigDecimal usuarioweb = (BigDecimal)q.getSingleResult();

			if (usuarioweb != null)
			{

				final StringBuilder sb1 = new StringBuilder();

				sb1.append(" select b.handle from V_Z_GRUPOUSUARIOS b where b.handle = ? and b.senha = ?");

				final Query q1 = getEntityManager().createNativeQuery(sb1.toString());
				// Segundo Select
				q1.setParameter(1, handlePrestador);
				q1.setParameter(2, password);

				final Long handleEncontrado = (Long)q1.getSingleResult();

				if (handleEncontrado != null)
				{
					autenticado = true;
				}
			}

			return autenticado;

		}
		catch (final Exception e)
		{
			LOG.error(e.getLocalizedMessage(), e.getCause());
			throw new BusinessException(e);
		}
	}

}
