/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.precadastro.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.CategoriaDao;
import br.com.unimedcuritiba.benner.dao.EstadosDao;
import br.com.unimedcuritiba.benner.dao.MunicipiosDao;
import br.com.unimedcuritiba.benner.dao.PrestadorDao;
import br.com.unimedcuritiba.benner.dao.TipoDocumentoFinanceiroDao;
import br.com.unimedcuritiba.benner.dao.TipoISSDao;
import br.com.unimedcuritiba.benner.dao.TipoPrestadorDao;
import br.com.unimedcuritiba.benner.util.TipoPessoa;
import br.com.unimedcuritiba.movcad.cooperado.dao.LogAuditoriaDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorEnderecoDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorEnderecoPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorPreCadastroDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorFinanceiroPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorPreCadastro;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnumPreCad;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.service.DelegateCrud;
import br.com.visionnaire.core.util.CustomDate;

@Stateless
public class MovimentacaoPrestadorPreCadastroService
    extends DelegateCrud<MovimentacaoPrestadorPreCadastro, Long, MovimentacaoPrestadorPreCadastroDao>
{

	private static final long serialVersionUID = -881422620340688458L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorPreCadastroService.class);

	@EJB
	private PrestadorDao prestadorDao;

	@EJB
	private LogAuditoriaDao logAuditoriaDao;

	@EJB
	private MovimentacaoPrestadorPreCadastroDao movimentacaoPrestadorPreCadastroDao;

	@EJB
	private MovimentacaoPrestadorEnderecoPreCadastroDao movimentacaoPrestadorEnderecoPreCadastroDao;

	@EJB
	private MovimentacaoPrestadorEnderecoDao movimentacaoPrestadorEnderecoDao;

	@EJB
	private TipoPrestadorDao tipoPrestadorDao;

	@EJB
	private TipoDocumentoFinanceiroDao tipoDocumentoDao;

	@EJB
	private CategoriaDao categoriaDao;

	@EJB
	private MunicipiosDao municipiosDao;

	@EJB
	private EstadosDao estadosDao;

	@EJB
	private TipoISSDao tipoISSDao;

	@Override
	protected MovimentacaoPrestadorPreCadastroDao getDelegate ()
	{
		return movimentacaoPrestadorPreCadastroDao;
	}

	public void salvar (MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro) throws DAOException
	{
		try
		{
			// Padronizar Financeiro
			final MovimentacaoPrestadorFinanceiroPreCadastro movimentacaoPrestadorFinanceiroPreCadastro = new MovimentacaoPrestadorFinanceiroPreCadastro();
			movimentacaoPrestadorFinanceiroPreCadastro.setNaoCobraTarifa(true);
			movimentacaoPrestadorFinanceiroPreCadastro.setTipoDocumentoFinanceiroVO(tipoDocumentoDao.findById(new Long(236)));
			movimentacaoPrestadorFinanceiroPreCadastro.setMovimentacaoPrestadorPreCadastro(movimentacaoPrestadorPreCadastro);

			// Padronizar Endereco
			movimentacaoPrestadorPreCadastro.getMovimentacaoPrestadorEnderecoPreCadastro().setEnderecoCorrespondencia(true);
			movimentacaoPrestadorPreCadastro.getMovimentacaoPrestadorEnderecoPreCadastro().setEnderecoAtendimento(true);
			movimentacaoPrestadorPreCadastro.getMovimentacaoPrestadorEnderecoPreCadastro().setEnderecoPessoal(true);
			movimentacaoPrestadorPreCadastro.getMovimentacaoPrestadorEnderecoPreCadastro().setEnderecoPagamento(false);

			// Padronizar Pre-cadastro
			movimentacaoPrestadorPreCadastro.setMovimentacaoPrestadorFinanceiroPreCadastro(movimentacaoPrestadorFinanceiroPreCadastro);
			movimentacaoPrestadorPreCadastro.setCategoria(categoriaDao.findById(new Long(1)));
			movimentacaoPrestadorPreCadastro.setTipoPrestador(tipoPrestadorDao.findById(new Long(1)));
			movimentacaoPrestadorPreCadastro.setSolicitante(true);
			movimentacaoPrestadorPreCadastro.setExecutante(true);
			movimentacaoPrestadorPreCadastro.setLocalExecucao(true);
			movimentacaoPrestadorPreCadastro.setRecebedor(false);
			movimentacaoPrestadorPreCadastro.setPessoa(TipoPessoa.get(2));
			movimentacaoPrestadorPreCadastro.setObrigarInformarProfissionalSolicitan(false);
			movimentacaoPrestadorPreCadastro.setMunicipioAlvara(municipiosDao.findById(new Long(5763)));
			movimentacaoPrestadorPreCadastro.setEstadoAlvara(estadosDao.findById(new Long(16)));
			movimentacaoPrestadorPreCadastro.setIss(tipoISSDao.findById(new Long(2)));
			movimentacaoPrestadorPreCadastro.setPrazoRecurso(new Long(30));
			movimentacaoPrestadorPreCadastro.setNumeroRecusosPermitidos(new Long(2));
			movimentacaoPrestadorPreCadastro.setPagaValorApresentadoZerado(false);
			movimentacaoPrestadorPreCadastro.setSituacao(SituacaoEnumPreCad.ANALISE);
			movimentacaoPrestadorPreCadastro.setDataInclusao(CustomDate.getCurrentTimestamp());
			movimentacaoPrestadorPreCadastro.setCpfPrestador(movimentacaoPrestadorPreCadastro.getCpfPrestador().replace(".", "").replace("-", ""));

			// SALVAR O TREM
			movimentacaoPrestadorPreCadastro = movimentacaoPrestadorPreCadastroDao.save(movimentacaoPrestadorPreCadastro);

		}
		catch (final DAOException e)
		{
			LOG.error("MovimentacaoPrestadorPreCadastroService.salvar() - error:" + VExceptions.getErrorMessage(e), e);
			e.printStackTrace();
			throw e;
		}
		catch (final Exception e)
		{
			LOG.error("MovimentacaoPrestadorPreCadastroService.salvar() - error:" + VExceptions.getErrorMessage(e), e);
			e.printStackTrace();
			throw e;
		}
	}

}
