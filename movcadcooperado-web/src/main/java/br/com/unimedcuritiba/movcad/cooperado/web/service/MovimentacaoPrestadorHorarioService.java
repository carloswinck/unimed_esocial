package br.com.unimedcuritiba.movcad.cooperado.web.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.benner.dao.PrestadorHorarioDao;
import br.com.unimedcuritiba.benner.vo.PrestadorHorarioVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorHorarioDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorEndereco;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestadorHorario;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.exception.DAOException;
import br.com.visionnaire.core.exception.VExceptions;
import br.com.visionnaire.core.messages.Messages;
import br.com.visionnaire.core.service.DelegateCrud;

@Stateless
public class MovimentacaoPrestadorHorarioService
    extends DelegateCrud<MovimentacaoPrestadorHorario, Long, MovimentacaoPrestadorHorarioDao>
{

	private static final long serialVersionUID = 8283327186578815950L;

	private static final transient Logger LOG = Logger.getLogger(MovimentacaoPrestadorHorarioService.class);

	@Inject
	private Messages messages;

	@EJB
	private PrestadorHorarioDao prestadorHorarioDao;

	@EJB
	private MovimentacaoPrestadorHorarioDao movimentacaoPrestadorHorarioDao;

	@Override
	protected MovimentacaoPrestadorHorarioDao getDelegate ()
	{
		return movimentacaoPrestadorHorarioDao;
	}

	/**
	 * Inicia a carga da lista de Horarios dos Enderecos de Atendimento do Prestador.
	 * 1 - Pesquisa na tabela view (V_SAM_PRESTADOR_HORARIO).
	 * 2 - Converte o VO para MovimentacaoPrestadorHorario.
	 * 3 - Retorna a lista de MovimentacaoPrestadorHorario.
	 */
	public List<MovimentacaoPrestadorHorario> createHorariosAtendimento (final PrestadorVO prestador,
	    final MovimentacaoPrestadorEndereco movimentacaoPrestadorEndereco)
	{
		if (prestador != null && movimentacaoPrestadorEndereco != null)
		{
			LOG.info("createHorariosAtendimento -> begin: " + prestador.getCodigoPrestador());

			final List<MovimentacaoPrestadorHorario> movimentacaoPrestadorHorarios = new ArrayList<MovimentacaoPrestadorHorario>();

			final List<PrestadorHorarioVO> prestadorHorarioVOs = prestadorHorarioDao.findByPrestadorEnderecoFullLoad(prestador,
			    movimentacaoPrestadorEndereco.getPrestadorEnderecoVO());

			if (Validator.isNotEmpty(prestadorHorarioVOs))
			{
				MovimentacaoPrestadorHorario movimentacaoPrestadorHorario;
				for (final PrestadorHorarioVO prestadorHorarioVO : prestadorHorarioVOs)
				{
					movimentacaoPrestadorHorario = new MovimentacaoPrestadorHorario();

					copyHorario(movimentacaoPrestadorHorario, prestadorHorarioVO);

					movimentacaoPrestadorHorario.setPrestadorHorarioVO(prestadorHorarioVO);
					movimentacaoPrestadorHorario.setMovimentacaoPrestadorEndereco(movimentacaoPrestadorEndereco);

					movimentacaoPrestadorHorarios.add(movimentacaoPrestadorHorario);
				}
			}

			LOG.info("createHorariosAtendimento <- end: " + prestador.getCodigoPrestador());

			return movimentacaoPrestadorHorarios;

		}
		return null;
	}

	/**
	 * @param movimentacaoPrestadorHorario
	 * @param prestadorHorarioVO
	 */
	private void copyHorario (final MovimentacaoPrestadorHorario movimentacaoPrestadorHorario, final PrestadorHorarioVO prestadorHorarioVO)
	{
		if (movimentacaoPrestadorHorario != null && prestadorHorarioVO != null)
		{
			try
			{
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				BeanUtils.copyProperties(movimentacaoPrestadorHorario, prestadorHorarioVO);
				movimentacaoPrestadorHorario.setPrestadorHorarioVO(prestadorHorarioVO);
			}
			catch (final Exception e)
			{
				LOG.error("createHorariosAtendimento - error BeanUtils:" + VExceptions.getErrorMessage(e), e);
				messages.addErrorMessage("movimentacao.prestador.horario.find.erro");
			}
		}
	}

	@Override
	public void delete (final MovimentacaoPrestadorHorario entity) throws DAOException
	{
		if (entity != null)
		{
			if (entity.getPrestadorHorarioVO() != null)
			{
				getDelegate().excluirLogicamente(entity);
			}
		}
	}
}
