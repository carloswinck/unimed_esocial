/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.Filter;
import br.com.unimedcuritiba.benner.vo.ConselhoVO;
import br.com.unimedcuritiba.benner.vo.EstadoCivilVO;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.unimedcuritiba.benner.vo.PaisesVO;
import br.com.unimedcuritiba.benner.vo.PrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.enums.RNECondicaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.RacaCorEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnum;
import br.com.visionnaire.core.common.annotation.IgnoreToString;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.entity.annotation.IgnoreHash;
import br.com.visionnaire.core.util.CustomDate;
import br.com.visionnaire.core.util.beancomparator.Compare;
import br.com.visionnaire.core.util.beancomparator.CompareJoin;

/**
 * The persistent class for the K_MCC_MOVIMENTACAO_PRESTADOR database table.
 *
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */
@Entity
@Table(name = "K_MCC_MOVIMENTACAO_PRESTADOR")
public class MovimentacaoPrestador extends VEntity<Long> {

	private static final long serialVersionUID = 8003038241697441657L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC001", sequenceName = "SEQ_MCC001", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC001")
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long id;

	// Campo para visualização do código da movimentação dentro das interfaces do
	// Benner
	@Column(name = "CODIGOMOVIMENTACAO")
	private Long codigoMovimentacao;

	@JoinColumn(name = "prestador")
	@ManyToOne(fetch = FetchType.LAZY)
	private PrestadorVO prestador;

	@Column(name = "codigo")
	private Long codigoPrestador;

	@Compare(key = "movimentacaoPrestador.cpfPrestador")
	@Column(name = "cpf")
	private String cpfPrestador;

	@Compare(key = "movimentacaoPrestador.nomePrestador")
	@Column(name = "nome", length = 60)
	private String nomePrestador;

	@Compare(key = "movimentacaoPrestador.nomeSocial")
	@Column(name = "nomesocial", length = 60)
	private String nomeSocial;

	@Compare(key = "movimentacaoPrestador.numeroRG")
	@Column(name = "rg", length = 14)
	private String numeroRG;

	@Compare(key = "movimentacaoPrestador.orgaoEmissorRG")
	@Column(name = "orgaoemissor", length = 3)
	private String orgaoEmissorRG;

	@Column(name = "estadoemissor", length = 2)
	private String estadoOrgaoEmissorRG;

	@Compare(key = "movimentacaoPrestador.dataExpedicaoRG")
	@Temporal(TemporalType.DATE)
	@Column(name = "dataexpedicaorg")
	private Date dataExpedicaoRG;

	@Compare(key = "movimentacaoPrestador.dataNascimento")
	@Temporal(TemporalType.DATE)
	@Column(name = "datanascimento")
	private Date dataNascimento;

	@CompareJoin(key = "movimentacaoPrestador.naturalidade", field = "nome")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "naturalidade")
	private MunicipiosVO naturalidade;

	@CompareJoin(key = "movimentacaoPrestador.estado", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado")
	private EstadosVO estado;

	@Compare(key = "movimentacaoPrestador.nomeMae")
	@Column(name = "nomemae", length = 60)
	private String nomeMae;

	@Compare(key = "movimentacaoPrestador.nomePai")
	@Column(name = "nomepai", length = 60)
	private String nomePai;

	@CompareJoin(key = "movimentacaoPrestador.nacionalidade", field = "nome")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nacionalidade")
	private PaisesVO nacionalidade;

	@Compare(key = "movimentacaoPrestador.inscricaoCRM")
	@Column(name = "inscricaocr")
	private String inscricaoCRM;

	@CompareJoin(key = "movimentacaoPrestador.conselhoRegional", field = "descricao")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conselhoregional")
	private ConselhoVO conselhoRegional;

	@Compare(key = "movimentacaoPrestador.dataInscricaoCRM")
	@Temporal(TemporalType.DATE)
	@Column(name = "datainscricaocr")
	private Date dataInscricaoCRM;

	@CompareJoin(key = "movimentacaoPrestador.estadoCivil", field = "descricao")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadocivil")
	private EstadoCivilVO estadoCivil;

	@Compare(key = "movimentacaoPrestador.racaCor")
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "racaecor")
	private RacaCorEnum racaCor;

	@Compare(key = "movimentacaoPrestador.inscricaoINSS")
	@Column(name = "inscricaoinss")
	private String inscricaoINSS;

	@Compare(key = "movimentacaoPrestador.inscricaoMunicipal")
	@Column(name = "inscricaomunicipal")
	private String inscricaoMunicipal;

	@CompareJoin(key = "movimentacaoPrestador.municipioPagamento", field = "nome")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipiopagamento")
	private MunicipiosVO municipioPagamento;

	@CompareJoin(key = "movimentacaoPrestador.estadoPagamento", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadopagamento")
	private EstadosVO estadoPagamento;

	@Compare(key = "movimentacaoPrestador.sexo")
	@Column(name = "sexo", length = 2)
	private String sexo;

	@Compare(key = "movimentacaoPrestador.email")
	@Column(name = "email", length = 60)
	private String email;

	@Compare(key = "movimentacaoPrestador.defFisica")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "deffisica", length = 1)
	private Boolean defFisica;

	@Compare(key = "movimentacaoPrestador.defVisual")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defvisual", length = 1)
	private Boolean defVisual;

	@Compare(key = "movimentacaoPrestador.defAuditiva")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defauditiva", length = 1)
	private Boolean defAuditiva;

	@Compare(key = "movimentacaoPrestador.defMental")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defmental", length = 1)
	private Boolean defMental;

	@Compare(key = "movimentacaoPrestador.defIntelectual")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defintelectual", length = 1)
	private Boolean defIntelectual;

	@Compare(key = "movimentacaoPrestador.defReabilitado")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defreabreadap", length = 1)
	private Boolean defReabilitado;
	
	@Compare(key = "movimentacaoPrestador.obsDeficiencia")
	@Column(name = "obsdeficiencia", length = 255)
	private String obsDeficiencia;
	
	@Compare(key = "movimentacaoPrestador.numeroCTPS")
	@Column(name = "ctpsnumero")
	private String numeroCTPS;

	@Compare(key = "movimentacaoPrestador.numeroSerieCTPS")
	@Column(name = "ctpsserie")
	private String numeroSerieCTPS;

	@CompareJoin(key = "movimentacaoPrestador.ufCTPS", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctpsuf")
	private EstadosVO ufCTPS;

	@Compare(key = "movimentacaoPrestador.numeroCNH")
	@Column(name = "cnhnumero")
	private String numeroCNH;

	@Compare(key = "movimentacaoPrestador.dataExpedicaoCNH")
	@Temporal(TemporalType.DATE)
	@Column(name = "cnhdataexpedicao")
	private Date dataExpedicaoCNH;

	@CompareJoin(key = "movimentacaoPrestador.ufCNH", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cnhuf")
	private EstadosVO ufCNH;

	@Compare(key = "movimentacaoPrestador.categoriaCNH")
	@Column(name = "cnhcategoria")
	private String categoriaCNH;

	@Compare(key = "movimentacaoPrestador.dataValidadeCNH")
	@Temporal(TemporalType.DATE)
	@Column(name = "cnhdatavalidade")
	private Date dataValidadeCNH;

	@Compare(key = "movimentacaoPrestador.dataPrimeiraCNH")
	@Temporal(TemporalType.DATE)
	@Column(name = "cnhdatahabilitacao1")
	private Date dataPrimeiraCNH;

	@Compare(key = "movimentacaoPrestador.numeroRNE")
	@Column(name = "rnenumero")
	private String numeroRNE;

	@Compare(key = "movimentacaoPrestador.orgaoEmissorRNE")
	@Column(name = "rneorgao")
	private String orgaoEmissorRNE;

	@Compare(key = "movimentacaoPrestador.dataExpedicaoRNE")
	@Temporal(TemporalType.DATE)
	@Column(name = "rnedataexpedicao")
	private Date dataExpedicaoRNE;

	@Compare(key = "movimentacaoPrestador.dataChegadaRNE")
	@Temporal(TemporalType.DATE)
	@Column(name = "rnedatachegada")
	private Date dataChegadaRNE;

	@Compare(key = "movimentacaoPrestador.casadoComBrasileiro")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "rnecasadocombras")
	private Boolean casadoComBrasileiro;

	@Compare(key = "movimentacaoPrestador.filhosBrasileiros")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "rnefilhosbras")
	private Boolean filhosBrasileiros;

	@Compare(key = "movimentacaoPrestador.condicaoRNE")
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "rnecondicao")
	private RNECondicaoEnum condicaoRNE;

	@Compare(key = "movimentacaoPrestador.notivisa")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "notivisa")
	private Boolean notivisa;

	@Compare(key = "movimentacaoPrestador.dddFoneCelular")
	@Column(name = "dddfonecelular")
	private String dddFoneCelular;

	@Compare(key = "movimentacaoPrestador.prefixoFoneCelular")
	@Column(name = "prefixofonecelular")
	private String prefixoFoneCelular;

	@Compare(key = "movimentacaoPrestador.numeroFoneCelular")
	@Column(name = "numerofonecelular")
	private String numeroFoneCelular;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datainclusao")
	private Date dataInclusao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataatualizacao")
	private Date dataAtualizacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataintegracao")
	private Date dataIntegracao;

	@Compare(key = "movimentacaoPrestador.situacao", ignoreNull = true)
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "situacao")
	private SituacaoEnum situacao;

	@Temporal(TemporalType.DATE)
	@Column(name = "datavalidadecrm")
	private Date dataValidadeCRM;
	
	@CompareJoin(key = "movimentacaoPrestador.ufEmissao", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ufemissao")
	private EstadosVO ufEmissao;
	
	@CompareJoin(key = "movimentacaoPrestador.ufEmissaoRNE", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ufemissaorne")
	private EstadosVO ufEmissaoRNE;
	
	@CompareJoin(key = "movimentacaoPrestador.ufEmissorDNI", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UFEMISSORDNI")
	private EstadosVO ufEmissorDNI;
	
	@Compare(key = "movimentacaoPrestador.dataExpedicaoDNI")
	@Temporal(TemporalType.DATE)
	@Column(name = "dataexpedicaodni")
	private Date dataExpedicaoDNI;

	@IgnoreToString
	@IgnoreHash
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "especialidade")
	private MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade;

	@IgnoreToString
	@IgnoreHash
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "dadosfinanceiros")
	private MovimentacaoPrestadorFinanceiro dadosFinanceiros;

	@IgnoreToString
	@IgnoreHash
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "enderecoresidencial")
	private MovimentacaoPrestadorEndereco enderecoResidencial;
	
	@Compare(key = "movimentacaoPrestador.numeroDocumentoDNI")
	@Column(name = "numerodocumentodni")
	private String numeroDocumentoDNI;
	
	@Compare(key = "movimentacaoPrestador.orgaoEmissorDNI")
	@Column(name = "orgaoemissordni", length = 3)
	private String orgaoEmissorDNI;

	@OneToMany(mappedBy = "movimentacaoPrestador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MovimentacaoPrestadorCurriculo> movimentacaoPrestadorCurriculos;

	@Filter(name = "atendimento")
	@OneToMany(mappedBy = "movimentacaoPrestador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MovimentacaoPrestadorEndereco> enderecosAtendimento;

	@OneToMany(mappedBy = "movimentacaoPrestador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LogAuditoria> logsAuditoria;

	@OneToMany(mappedBy = "movimentacaoPrestador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MovimentacaoPrestadorDependente> dependentes;

	@OneToMany(mappedBy = "movimentacaoPrestador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MovimentacaoPrestadorAnexo> anexosList;

	@Transient
	private MovimentacaoPrestadorCurriculo itemCurriculo = new MovimentacaoPrestadorCurriculo();

	@Transient
	private MovimentacaoPrestadorEndereco itemEnderecoAtendimento = new MovimentacaoPrestadorEndereco();

	@Transient
	private MovimentacaoPrestadorDependente itemDependente = new MovimentacaoPrestadorDependente();

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EHPRECADASTRO", length = 1)
	private Boolean preCadastro;

	@Column(name = "MOTIVORECUSA")
	private String motivoRecusa;

	@Column(name = "AUTORIZAFORNECEREMAIL")
	@Compare(key = "movimentacaoPrestador.autorizaFornecerEmailLabel")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean autorizaFornecerEmail;

	@Column(name = "ENVIAEMAILCADASTRORECUSADO")
	@Compare(key = "movimentacaoPrestador.enviaEmailCadastroRecusado")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean enviaEmailCadastroRecusado;

	public MovimentacaoPrestador() {
	}

	@PrePersist
	public void prePersist() {
		this.dataInclusao = CustomDate.getCurrentDate();
		this.codigoMovimentacao = this.id;
	}

	@PreUpdate
	public void preUpdate() {
		this.dataAtualizacao = CustomDate.getCurrentDate();
		this.codigoMovimentacao = this.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public PrestadorVO getPrestador() {
		return prestador;
	}

	public void setPrestador(final PrestadorVO prestador) {
		this.prestador = prestador;
	}

	public Long getCodigoPrestador() {
		return codigoPrestador;
	}

	public void setCodigoPrestador(final Long codigoPrestador) {
		this.codigoPrestador = codigoPrestador;
	}

	public String getCpfPrestador() {
		return cpfPrestador;
	}

	public void setCpfPrestador(final String cpfPrestador) {
		this.cpfPrestador = cpfPrestador;
	}

	public String getNomePrestador() {
		return nomePrestador;
	}

	public void setNomePrestador(final String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public String getNumeroRG() {
		return numeroRG;
	}

	public void setNumeroRG(final String numeroRG) {
		this.numeroRG = numeroRG;
	}

	public String getOrgaoEmissorRG() {
		return orgaoEmissorRG;
	}

	public void setOrgaoEmissorRG(final String orgaoEmissorRG) {
		this.orgaoEmissorRG = orgaoEmissorRG;
	}

	public Date getDataExpedicaoRG() {
		return dataExpedicaoRG;
	}

	public void setDataExpedicaoRG(final Date dataExpedicaoRG) {
		this.dataExpedicaoRG = dataExpedicaoRG;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(final Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public MunicipiosVO getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(final MunicipiosVO naturalidade) {
		this.naturalidade = naturalidade;
	}

	public EstadosVO getEstado() {
		return estado;
	}

	public void setEstado(final EstadosVO estado) {
		this.estado = estado;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(final String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public PaisesVO getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(final PaisesVO nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getInscricaoCRM() {
		return inscricaoCRM;
	}

	public void setInscricaoCRM(final String inscricaoCRM) {
		this.inscricaoCRM = inscricaoCRM;
	}

	public ConselhoVO getConselhoRegional() {
		return conselhoRegional;
	}

	public void setConselhoRegional(final ConselhoVO conselhoRegional) {
		this.conselhoRegional = conselhoRegional;
	}

	public Date getDataInscricaoCRM() {
		return dataInscricaoCRM;
	}

	public void setDataInscricaoCRM(final Date dataInscricaoCRM) {
		this.dataInscricaoCRM = dataInscricaoCRM;
	}

	public EstadoCivilVO getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(final EstadoCivilVO estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public RacaCorEnum getRacaCor() {
		return racaCor;
	}

	public void setRacaCor(final RacaCorEnum racaCor) {
		this.racaCor = racaCor;
	}

	public String getInscricaoINSS() {
		return inscricaoINSS;
	}

	public void setInscricaoINSS(final String inscricaoINSS) {
		this.inscricaoINSS = inscricaoINSS;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(final String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public MunicipiosVO getMunicipioPagamento() {
		return municipioPagamento;
	}

	public void setMunicipioPagamento(final MunicipiosVO municipioPagamento) {
		this.municipioPagamento = municipioPagamento;
	}

	public EstadosVO getEstadoPagamento() {
		return estadoPagamento;
	}

	public void setEstadoPagamento(final EstadosVO estadoPagamento) {
		this.estadoPagamento = estadoPagamento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Boolean getDefFisica() {
		return defFisica;
	}

	public void setDefFisica(final Boolean defFisica) {
		this.defFisica = defFisica;
	}

	public Boolean getDefVisual() {
		return defVisual;
	}

	public void setDefVisual(final Boolean defVisual) {
		this.defVisual = defVisual;
	}

	public Boolean getDefAuditiva() {
		return defAuditiva;
	}

	public void setDefAuditiva(final Boolean defAuditiva) {
		this.defAuditiva = defAuditiva;
	}

	public Boolean getDefMental() {
		return defMental;
	}

	public void setDefMental(final Boolean defMental) {
		this.defMental = defMental;
	}

	public Boolean getDefIntelectual() {
		return defIntelectual;
	}

	public void setDefIntelectual(final Boolean defIntelectual) {
		this.defIntelectual = defIntelectual;
	}

	public Boolean getDefReabilitado() {
		return defReabilitado;
	}

	public void setDefReabilitado(final Boolean defReabilitado) {
		this.defReabilitado = defReabilitado;
	}
	
	public String getObsDeficiencia() {
		return obsDeficiencia;
	}

	public void setObsDeficiencia(String obsDeficiencia) {
		this.obsDeficiencia = obsDeficiencia;
	}

	public String getNumeroCTPS() {
		return numeroCTPS;
	}

	public void setNumeroCTPS(final String numeroCTPS) {
		this.numeroCTPS = numeroCTPS;
	}

	public String getNumeroSerieCTPS() {
		return numeroSerieCTPS;
	}

	public void setNumeroSerieCTPS(final String numeroSerieCTPS) {
		this.numeroSerieCTPS = numeroSerieCTPS;
	}

	public void setNumeroCNH(final String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}

	public EstadosVO getUfCTPS() {
		return ufCTPS;
	}

	public void setUfCTPS(final EstadosVO ufCTPS) {
		this.ufCTPS = ufCTPS;
	}

	public String getNumeroCNH() {
		return numeroCNH;
	}

	public Date getDataExpedicaoCNH() {
		return dataExpedicaoCNH;
	}

	public void setDataExpedicaoCNH(final Date dataExpedicaoCNH) {
		this.dataExpedicaoCNH = dataExpedicaoCNH;
	}

	public EstadosVO getUfCNH() {
		return ufCNH;
	}

	public void setUfCNH(final EstadosVO ufCNH) {
		this.ufCNH = ufCNH;
	}

	public String getCategoriaCNH() {
		return categoriaCNH;
	}

	public void setCategoriaCNH(final String categoriaCNH) {
		this.categoriaCNH = categoriaCNH;
	}

	public Date getDataValidadeCNH() {
		return dataValidadeCNH;
	}

	public void setDataValidadeCNH(final Date dataValidadeCNH) {
		this.dataValidadeCNH = dataValidadeCNH;
	}

	public Date getDataPrimeiraCNH() {
		return dataPrimeiraCNH;
	}

	public void setDataPrimeiraCNH(final Date dataPrimeiraCNH) {
		this.dataPrimeiraCNH = dataPrimeiraCNH;
	}

	public String getNumeroRNE() {
		return numeroRNE;
	}

	public void setNumeroRNE(final String numeroRNE) {
		this.numeroRNE = numeroRNE;
	}

	public String getOrgaoEmissorRNE() {
		return orgaoEmissorRNE;
	}

	public void setOrgaoEmissorRNE(final String orgaoEmissorRNE) {
		this.orgaoEmissorRNE = orgaoEmissorRNE;
	}

	public Date getDataExpedicaoRNE() {
		return dataExpedicaoRNE;
	}

	public void setDataExpedicaoRNE(final Date dataExpedicaoRNE) {
		this.dataExpedicaoRNE = dataExpedicaoRNE;
	}

	public Date getDataChegadaRNE() {
		return dataChegadaRNE;
	}

	public void setDataChegadaRNE(final Date dataChegada) {
		this.dataChegadaRNE = dataChegada;
	}

	public Boolean getCasadoComBrasileiro() {
		return casadoComBrasileiro;
	}

	public void setCasadoComBrasileiro(final Boolean casadoComBrasileiro) {
		this.casadoComBrasileiro = casadoComBrasileiro;
	}

	public RNECondicaoEnum getCondicaoRNE() {
		return condicaoRNE;
	}

	public void setCondicaoRNE(final RNECondicaoEnum condicaoRNE) {
		this.condicaoRNE = condicaoRNE;
	}

	public Boolean getNotivisa() {
		return notivisa;
	}

	public void setNotivisa(final Boolean notivisa) {
		this.notivisa = notivisa;
	}

	public String getDddFoneCelular() {
		return dddFoneCelular;
	}

	public Integer getDddFoneCelularT() {
		return Validator.valueOfInteger(dddFoneCelular);

	}

	public void setDddFoneCelularT(final Integer dddFoneCelular) {
		this.dddFoneCelular = (dddFoneCelular != null ? dddFoneCelular.toString() : null);
	}

	public void setDddFoneCelular(final String dddFoneCelular) {
		this.dddFoneCelular = dddFoneCelular;
	}

	public String getPrefixoFoneCelular() {
		return prefixoFoneCelular;
	}

	public void setPrefixoFoneCelular(final String prefixoFoneCelular) {
		this.prefixoFoneCelular = prefixoFoneCelular;
	}

	public String getNumeroFoneCelular() {
		return numeroFoneCelular;
	}

	public void setNumeroFoneCelular(final String numeroFoneCelular) {
		this.numeroFoneCelular = numeroFoneCelular;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(final Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(final Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Date getDataIntegracao() {
		return dataIntegracao;
	}

	public void setDataIntegracao(final Date dataIntegracao) {
		this.dataIntegracao = dataIntegracao;
	}

	public SituacaoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(final SituacaoEnum situacao) {
		this.situacao = situacao;
	}

	public String getEstadoOrgaoEmissorRG() {
		return estadoOrgaoEmissorRG;
	}

	public void setEstadoOrgaoEmissorRG(final String estadoOrgaoEmissorRG) {
		this.estadoOrgaoEmissorRG = estadoOrgaoEmissorRG;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(final String sexo) {
		this.sexo = sexo;
	}

	public Boolean getFilhosBrasileiros() {
		return filhosBrasileiros;
	}

	public void setFilhosBrasileiros(final Boolean filhosBrasileiros) {
		this.filhosBrasileiros = filhosBrasileiros;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(final String nomePai) {
		this.nomePai = nomePai;
	}

	public MovimentacaoPrestadorFinanceiro getDadosFinanceiros() {
		return dadosFinanceiros;
	}

	public void setDadosFinanceiros(final MovimentacaoPrestadorFinanceiro dadosFinanceiros) {
		this.dadosFinanceiros = dadosFinanceiros;
	}

	public MovimentacaoPrestadorEndereco getEnderecoResidencial() {
		return enderecoResidencial;
	}

	public void setEnderecoResidencial(final MovimentacaoPrestadorEndereco enderecoResidencial) {
		this.enderecoResidencial = enderecoResidencial;
	}

	public String getNumeroDocumentoDNI() {
		return numeroDocumentoDNI;
	}

	public void setNumeroDocumentoDNI(String numeroDocumentoDNI) {
		this.numeroDocumentoDNI = numeroDocumentoDNI;
	}
	
	public String getOrgaoEmissorDNI() {
		return orgaoEmissorDNI;
	}

	public void setOrgaoEmissorDNI(String orgaoEmissorDNI) {
		this.orgaoEmissorDNI = orgaoEmissorDNI;
	}

	public Date getDataExpedicaoDNI() {
		return dataExpedicaoDNI;
	}

	public void setDataExpedicaoDNI(Date dataExpedicaoDNI) {
		this.dataExpedicaoDNI = dataExpedicaoDNI;
	}

	public EstadosVO getUfEmissorDNI() {
		return ufEmissorDNI;
	}

	public void setUfEmissorDNI(EstadosVO ufEmissorDNI) {
		this.ufEmissorDNI = ufEmissorDNI;
	}

	public List<MovimentacaoPrestadorCurriculo> getMovimentacaoPrestadorCurriculos() {
		return movimentacaoPrestadorCurriculos;
	}

	public void setMovimentacaoPrestadorCurriculos(
			final List<MovimentacaoPrestadorCurriculo> movimentacaoPrestadorCurriculos) {
		this.movimentacaoPrestadorCurriculos = movimentacaoPrestadorCurriculos;
	}

	public List<MovimentacaoPrestadorEndereco> getEnderecosAtendimento() {
		return enderecosAtendimento;
	}

	public void setEnderecosAtendimento(
			final List<MovimentacaoPrestadorEndereco> movimentacaoPrestadorEnderecosAtendimento) {
		this.enderecosAtendimento = movimentacaoPrestadorEnderecosAtendimento;
	}

	public List<LogAuditoria> getLogsAuditoria() {
		return logsAuditoria;
	}

	public void setLogsAuditoria(final List<LogAuditoria> logsAuditoria) {
		this.logsAuditoria = logsAuditoria;
	}

	public MovimentacaoPrestadorCurriculo getItemCurriculo() {
		return itemCurriculo;
	}

	public void setItemCurriculo(final MovimentacaoPrestadorCurriculo itemCurriculo) {
		this.itemCurriculo = itemCurriculo;
	}

	public MovimentacaoPrestadorEndereco getItemEnderecoAtendimento() {
		return itemEnderecoAtendimento;
	}

	public void setItemEnderecoAtendimento(final MovimentacaoPrestadorEndereco itemEnderecoAtendimento) {
		this.itemEnderecoAtendimento = itemEnderecoAtendimento;
	}

	public List<MovimentacaoPrestadorDependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(final List<MovimentacaoPrestadorDependente> dependentes) {
		this.dependentes = dependentes;
	}

	public MovimentacaoPrestadorDependente getItemDependente() {
		return itemDependente;
	}

	public void setItemDependente(final MovimentacaoPrestadorDependente itemDependente) {
		this.itemDependente = itemDependente;
	}

	public List<MovimentacaoPrestadorAnexo> getAnexosList() {
		return anexosList;
	}

	public void setAnexosList(final List<MovimentacaoPrestadorAnexo> anexosList) {
		this.anexosList = anexosList;
	}
	
	public Date getDataValidadeCRM() {
		return dataValidadeCRM;
	}

	public void setDataValidadeCRM(Date dataValidadeCRM) {
		this.dataValidadeCRM = dataValidadeCRM;
	}

	public EstadosVO getUfEmissao() {
		return ufEmissao;
	}

	public void setUfEmissao(EstadosVO ufEmissao) {
		this.ufEmissao = ufEmissao;
	}

	public EstadosVO getUfEmissaoRNE() {
		return ufEmissaoRNE;
	}

	public void setUfEmissaoRNE(EstadosVO ufEmissaoRNE) {
		this.ufEmissaoRNE = ufEmissaoRNE;
	}

	public String getMotivoRecusa() {
		return motivoRecusa;
	}

	public void setMotivoRecusa(final String motivoRecusa) {
		this.motivoRecusa = motivoRecusa;
	}

	public Long getCodigoMovimentacao() {
		return codigoMovimentacao;
	}

	public void setCodigoMovimentacao(final Long codigoMovimentacao) {
		this.codigoMovimentacao = codigoMovimentacao;
	}

	public MovimentacaoPrestadorEspecialidade getMovimentacaoPrestadorEspecialidade() {
		return movimentacaoPrestadorEspecialidade;
	}

	public void setMovimentacaoPrestadorEspecialidade(
			final MovimentacaoPrestadorEspecialidade movimentacaoPrestadorEspecialidade) {
		this.movimentacaoPrestadorEspecialidade = movimentacaoPrestadorEspecialidade;
	}

	public Boolean getPreCadastro() {
		return preCadastro;
	}

	public void setPreCadastro(final Boolean preCadastro) {
		this.preCadastro = preCadastro;
	}

	public void setPkValue(final Long value) {
		// TODO Auto-generated method stub
	}

	public Boolean getAutorizaFornecerEmail() {
		return autorizaFornecerEmail;
	}

	public void setAutorizaFornecerEmail(final Boolean autorizaFornecerEmail) {
		this.autorizaFornecerEmail = autorizaFornecerEmail;
	}

	public Boolean getEnviaEmailCadastroRecusado() {
		return enviaEmailCadastroRecusado;
	}

	public void setEnviaEmailCadastroRecusado(final Boolean enviaEmailCadastroRecusado) {
		this.enviaEmailCadastroRecusado = enviaEmailCadastroRecusado;
	}
}
