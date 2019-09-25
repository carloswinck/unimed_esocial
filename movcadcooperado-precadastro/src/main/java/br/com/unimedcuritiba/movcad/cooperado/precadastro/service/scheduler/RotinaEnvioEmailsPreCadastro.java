package br.com.unimedcuritiba.movcad.cooperado.precadastro.service.scheduler;

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
import br.com.unimedcuritiba.movcad.cooperado.precadastro.service.RotinaEnvioEmailsPreCadastroServices;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoConstants;
import br.com.unimedcuritiba.movcad.cooperado.util.MovCadCooperadoProperties;

/**
 * Rotinas para envio de e-mail do Pre Cadastro
 * - Email para notificar da liberacao do acesso ao Pre Cadastro
 * - Email para notificar correcao no Pre Cadastro recusado
 * - Email para notificar do pagamento da Cotas Partes e acesso ao sistema MCC
 *
 * @author Eloi Bilek
 * @since 10 de fev de 2017
 */
@Singleton
@Startup
public class RotinaEnvioEmailsPreCadastro
    implements TimedObject
{

	private static final transient Logger LOG = Logger.getLogger(RotinaEnvioEmailsPreCadastro.class);

	@Resource
	private TimerService timerService;

	@EJB
	RotinaEnvioEmailsPreCadastroServices rotinaEnvioEmailsPreCadastroServices;

	@PostConstruct
	public void schedule ()
	{
		LOG.info("begin -> Schedule Rotina Emails Pre Cadastro Cooperado");

		final ScheduleExpression expression = new ScheduleExpression();

		expression.hour(MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ROTINA_EMAIL_HORA));

		expression.minute(MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ROTINA_EMAIL_MINUTO));

		expression.dayOfWeek(MovCadCooperadoProperties.getProperty(MovCadCooperadoConstants.ROTINA_EMAIL_DIAS_SEMANA));

		LOG.info("schedule -> Rotina Emails Pre Cadastro Cooperado: " + expression);

		final TimerConfig timerConfig = new TimerConfig();

		timerConfig.setPersistent(false);

		this.timerService.createCalendarTimer(expression, timerConfig);

		LOG.info("end <- Schedule Rotina Emails Pre Cadastro Cooperado");
	}

	@Override
	public void ejbTimeout (final Timer timer)
	{
		LOG.info("_______>_>_>_>_>_>_>_>_> ejbTimeout INICIO");
		try
		{
			this.rotinaEnvioEmailsPreCadastroServices.enviarEmailPreCadastroNovo();
			this.rotinaEnvioEmailsPreCadastroServices.enviarEmailPreCadastroRecusado();
			this.rotinaEnvioEmailsPreCadastroServices.enviarEmailPreCadastroAprovado();
		}
		catch (final Throwable t)
		{
			LOG.error("Nao foi possivel executar a Rotina Emails Pre Cadastro Cooperado: ", t);
		}
		LOG.info("_______>_>_>_>_>_>_>_>_> ejbTimeout FIM");
	}
}
