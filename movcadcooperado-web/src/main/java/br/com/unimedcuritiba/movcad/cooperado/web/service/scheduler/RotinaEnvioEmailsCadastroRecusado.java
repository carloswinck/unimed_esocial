package br.com.unimedcuritiba.movcad.cooperado.web.service.scheduler;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import org.apache.log4j.Logger;
import br.com.unimedcuritiba.movcad.cooperado.dao.MovimentacaoPrestadorDao;
import br.com.unimedcuritiba.movcad.cooperado.entity.MovimentacaoPrestador;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;
import br.com.unimedcuritiba.movcad.cooperado.web.service.EmailRecusadoService;
import br.com.visionnaire.core.common.validations.Validator;

@Singleton
@Startup
public class RotinaEnvioEmailsCadastroRecusado
    implements TimedObject
{

	private static final transient Logger LOG = Logger.getLogger(RotinaEnvioEmailsCadastroRecusado.class);

	@Resource
	private TimerService timerService;

	@EJB
	private MovimentacaoPrestadorDao movCadDao;

	@EJB
	private EmailRecusadoService emailRecusadoService;

	@PostConstruct
	public void schedule ()
	{
		LOG.info("begin -> Schedule Rotina Emails Cadastro Recusado ");

		final ScheduleExpression expression = new ScheduleExpression();

		expression.hour(MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ROTINA_EMAIL_RECUSADO_HORA));

		expression.minute(MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ROTINA_EMAIL_RECUSADO_MINUTO_));

		expression.dayOfWeek(MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ROTINA_EMAIL_RECUSADO_DIAS_SEMANA));

		LOG.info("schedule -> Rotina Emails Cadastro Recusado: " + expression);

		final TimerConfig timerConfig = new TimerConfig();

		timerConfig.setPersistent(false);

		this.timerService.createCalendarTimer(expression, timerConfig);

		LOG.info("end <- Schedule Rotina Emails Cadastro Recusado ");
	}

	@Override
	public void ejbTimeout (final Timer timer)
	{
		LOG.info("_______>_>_>_>_>_>_>_>_> RotinaEnvioEmailsCadastroRecusado ejbTimeout INICIO");
		try
		{
			final List<MovimentacaoPrestador> movimentacaoRecusadaList = movCadDao.verificaRecusaMovimentacaoPrestador();

			if (Validator.isNotEmpty(movimentacaoRecusadaList))
			{
				for (final MovimentacaoPrestador movimentacaoPrestador : movimentacaoRecusadaList)
				{
					final boolean enviarEmail = emailRecusadoService.enviarEmail(movimentacaoPrestador);

					if (enviarEmail)
					{
						movimentacaoPrestador.setEnviaEmailCadastroRecusado(false);
						movCadDao.update(movimentacaoPrestador);
					}
					else
					{
						LOG.error("Nao foi possivel enviar Email para Cadastro Recusado, Email: " + movimentacaoPrestador.getEmail());
					}
				}
			}
		}
		catch (final Throwable t)
		{
			LOG.error("Nao foi possivel executar a Rotina Emails Cadastro Recusado: ", t);
		}
		LOG.info("_______>_>_>_>_>_>_>_>_> RotinaEnvioEmailsCadastroRecusado ejbTimeout FIM");
	}
}
