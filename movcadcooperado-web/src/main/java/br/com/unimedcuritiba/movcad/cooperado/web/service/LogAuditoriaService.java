/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.web.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.LogAuditoriaDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.LogAuditoria;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

/**
 * @author Eloi Bilek
 * @since 28 de jun de 2016
 */
@Stateless
public class LogAuditoriaService
    extends DelegateCrud<LogAuditoria, Long, LogAuditoriaDao>
{

	private static final long serialVersionUID = 2808784409558051626L;

	private static final transient Logger LOG = Logger.getLogger(LogAuditoriaService.class);

	@Inject
	private Messages messages;

	@EJB
	private LogAuditoriaDao logAuditoriaDao;

	public void initLogAuditoriaService ()
	{
		LOG.debug("Iniciando Auditoria");
		messages.addInfoMessage("");
	}

	/**
	 * Pesquisa a existencia do log auditoria para o campo cep.
	 */
	public boolean verificaExistenciaLogAuditoria (final PrestadorVO prestadorVO, final String nomeCampo,
	    final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco)
	{
		final long existeLogAuditoria = logAuditoriaDao.verificaExistenciaLogAuditoriaIntegrada(prestadorVO, nomeCampo,
		    movimentacaoPrestadorEndereco);

		if (existeLogAuditoria == 0)
		{
			return false;
		}

		return true;
	}

	@Override
	protected LogAuditoriaDao getDelegate ()
	{
		return logAuditoriaDao;
	}

}
