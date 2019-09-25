/*
 * %W% %E% Copyright (c) 2015, Unimed Curitiba Todos os direitos reservados.
 */
package br.com.unimedcuritiba.movcad.cooperado.util;

/**
 * Interface com as constantes do SCE Honorários
 *
 * @author Paulo Roberto Schwertner
 * @since 1.0.0
 */
public interface MovCadCooperadoConstants
{

	/**
	 * Registro ANS Unimed
	 */
	String REGISTRO_ANS = "304701";

	/**
	 * Código da Unimed Curitiba
	 */
	String CODIGO_UNIMED = "0032";

	/**
	 * Alias das propriedades dos sistema
	 */
	String SYSTEM_ALIAS = "movcadcooperado.";

	/**
	 * Alias das propriedades do sub sistema
	 */
	String MODULO_PRECADASTRO = "precadastro.";

	/**
	 * Constante do Orgão Emissor
	 */
	String ORGAO_EMISSOR_RG = "SSP";

	/**
	 * Constante do Orgão Emissor
	 */
	String BRASIL = "Brasil";

	/**
	 * Constante de desenvolvimento do sistema
	 */
	String DESENV_MODE = SYSTEM_ALIAS + "desenv";

	/**
	 * Constante de modo piloto do sistema
	 */
	String PILOTO_MODE = SYSTEM_ALIAS + "piloto";

	/**
	 * Constante do caminho dos anexos
	 */
	String ANEXOS_PATH = SYSTEM_ALIAS + "anexos.path";

	/**
	 * Constante do caminho dos anexos
	 */
	String ANEXOS_PRECAD_PATH = SYSTEM_ALIAS + MODULO_PRECADASTRO + "anexos.path";

	/**
	 * Constante do separador de pastas
	 */
	String SEPARADOR = "\\";

	String PASTA_PRE_CADASTRO = "precadastro";

	/**
	 * Constantes para mensagem de situacao do cadastro
	 */
	public static final String ANALISE = "Analise";
	public static final String SALVO_PARCIALMENTE = "Digitação";
	public static final String CAD_ANALISE = "Cadastro em Analise";
	public static final String CAD_CONCLUIDO = "Cadastro Concluído";
	public static final String CAD_NAO_CONCLUIDO = "Cadastro não Concluído";
	public static final String ATUALIZACAO = "Atualização";

	/**
	 * Constante dos nomes das tabelas SAM
	 */
	public static final String TABELA_SAM_PRESTADOR = "SAM_PRESTADOR";
	public static final String TABELA_SAM_PRESTADOR_ENDERECO = "SAM_PRESTADOR_ENDERECO";
	public static final String TABELA_SAM_PRESTADOR_CURRICULO = "SAM_PRESTADOR_CURRICULO";
	public static final String TABELA_SAM_PRESTADOR_DEPENDENTE = "SAM_PRESTADOR_DEPENDENTE";
	public static final String TABELA_SAM_PRESTADOR_HORARIO = "SAM_PRESTADOR_HORARIO";
	public static final String TABELA_SAM_PRESTADOR_ESPECIALIDADE = "SAM_PRESTADOR_ESPECIALIDADE";
	public static final String TABELA_SFN_CONTAFIN = "SFN_CONTAFIN";

	/**
	 * Constante dos nomes das tabelas MCC
	 */
	public static final String TABELA_MOVIMENTACAO_PRESTADOR = "K_MCC_MOVIMENTACAO_PRESTADOR";
	public static final String TABELA_PRESTADOR_ENDERECO = "K_MCC_PRESTADOR_ENDERECO";
	public static final String TABELA_PRESTADOR_CURRICULO = "K_MCC_PRESTADOR_CURRICULO";
	public static final String TABELA_PRESTADOR_DEPENDENTE = "K_MCC_PRESTADOR_DEPENDENTE";
	public static final String TABELA_PRESTADOR_HORARIO = "K_MCC_PRESTADOR_HORARIO";
	public static final String TABELA_PRESTADOR_ESPECIALIDADE = "K_MCC_PRESTADOR_ESPECIALIDADE";
	public static final String TABELA_PRESTADOR_FINANCEIRO = "K_MCC_PRESTADOR_FINANCEIRO";

	/**
	 * Constantes de campo
	 */
	public static final String HORA_INICIO = "HORAINICIAL";
	public static final String HORA_FIM = "HORAFINAL";
	public static final String CNES = "CNES";
	public static final String TIPO_CURSO = "TIPOCURSO";
	public static final String AGENCIA = "AGENCIA";

	/**
	 * Constante para a quantidade de dias da semana
	 */
	public static final Integer DIAS_DA_SEMANA = 7;

	/**
	 * Codigo de tipo prestador cooperado
	 */
	public static final Long TIPO_PRESTADOR_COOPERADO = 1L;

	/**
	 * Constante de mensagem de inicio
	 */
	public static final String CARREGANDO = "Carregando...";

	/**
	 * Constante de codigo de bancos
	 */
	public static final Long CODIGO_BANCO_SANTANDER = 33L;
	public static final String NOME_BANCO = "SANTANDER";

	/**
	 * Constante do maximo de caracteres
	 */
	public static final Integer CONTA_CORRENTE_TAMANHO = 8;

	/**
	 * Constante zero
	 */
	public static final String ZERO = "0";

	/**
	 * Constante da propriedade que determina a hora de execução da rotina de envio de emails para cadastro
	 * recusado
	 * dos cooperados
	 */
	public static final String ROTINA_EMAIL_RECUSADO_HORA = SYSTEM_ALIAS + "rotina.emailRecusado.hora";

	/**
	 * Constante da propriedade que determina o minuto de execução da rotina de envio de emails para
	 * cadastro recusado dos cooperados
	 */
	public static final String ROTINA_EMAIL_RECUSADO_MINUTO_ = SYSTEM_ALIAS + "rotina.emailRecusado.minuto";

	/**
	 * Constante da propriedade que determina os dias da semanas de execução da rotina de envio de emails de
	 * cadastro recusado dos cooperados
	 */
	public static final String ROTINA_EMAIL_RECUSADO_DIAS_SEMANA = SYSTEM_ALIAS + "rotina.emailRecusado.dias_semana";

	/**
	 * Constante com a propriedade do link de acesso ao Cadastro
	 */
	public static final String LINK_ACESSO_CADASTRO = SYSTEM_ALIAS + "cadastro.url";

	/**
	 * Email do Destinatário (utilizado para testes)
	 */
	public static final String EMAIL_TO = SYSTEM_ALIAS + "email.to";

	/**
	 * Email do Remetente
	 */
	public static final String EMAIL_FROM = SYSTEM_ALIAS + "email.from";

	/**
	 * mensagem para Cadastro recusado
	 */
	public static final String CADASTRO_RECUSADO_MSG_INICIO = "cadastro.recusado.msg.inicio";
	/**
	 * mensagem para Cadastro recusado
	 */
	public static final String CADASTRO_RECUSADO_MSG_FIM = "cadastro.recusado.msg.fim";

	/**
	 * mensagem para Cadastro recusado
	 */
	public static final String CADASTRO_RECUSADO_MSG_TITULO = "cadastro.recusado.msg.titulo";

	/**
	 * Parametro fixo pelo sistema ao realizar o pre cadastro
	 */
	public static final String TIPO_ISS = "ISENTO-FÍSICA";
	public static final String TIPO_DOCUMENTO = "120_REMESSA BCO- SANTANDER QUOTAS";

	/**
	 * Email do Destinatário (utilizado para testes)
	 */
	public static final String EMAIL_TO_PRECAD = SYSTEM_ALIAS + MODULO_PRECADASTRO + "email.to";

	/**
	 * Email do Remetente
	 */
	public static final String EMAIL_FROM_PRECAD = SYSTEM_ALIAS + MODULO_PRECADASTRO + "email.from";

	/**
	 * Constante da propriedade que determina a hora de execução da rotina de envio de emails do pre cadastro
	 * dos cooperados
	 */
	public static final String ROTINA_EMAIL_HORA = SYSTEM_ALIAS + MODULO_PRECADASTRO + "rotina.email.hora";

	/**
	 * Constante da propriedade que determina o minuto de execução da rotina de envio de emails do pre
	 * cadastro dos cooperados
	 */
	public static final String ROTINA_EMAIL_MINUTO = SYSTEM_ALIAS + MODULO_PRECADASTRO + "rotina.email.minuto";

	/**
	 * Constante da propriedade que determina os dias da semanas de execução da rotina de envio de emails do
	 * pre cadastro dos cooperados
	 */
	public static final String ROTINA_EMAIL_DIAS_SEMANA = SYSTEM_ALIAS + MODULO_PRECADASTRO + "rotina.email.dias_semana";

	/**
	 * Constante com o a propriedade do link de acesso ao Pre Cadastro
	 */
	public static final String LINK_ACESSO_COOPERADO = SYSTEM_ALIAS + MODULO_PRECADASTRO + "url";

	/**
	 * Constante com o a propriedade do link de acesso ao Pre Cadastro
	 */
	public static final String LINK_ACESSO_IMAGENS = SYSTEM_ALIAS + MODULO_PRECADASTRO + "image.url";

	/**
	 * Constante com o a propriedade do link de acesso ao Pre Cadastro
	 */
	public static final String LINK_ACESSO_PRECADASTRO = SYSTEM_ALIAS + MODULO_PRECADASTRO + "digitacao.url";

	/**
	 * Constante com o a propriedade do link de acesso a Movimentacao Cadastro Cooperados
	 */
	public static final String LINK_ACESSO_PRECADASTRO_CONCLUIDO = SYSTEM_ALIAS + MODULO_PRECADASTRO + "concluido.url";

}
