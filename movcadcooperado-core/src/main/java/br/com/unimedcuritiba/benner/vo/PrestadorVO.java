package br.com.unimedcuritiba.benner.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.unimedcuritiba.benner.util.TipoPessoa;
import br.com.unimedcuritiba.movcad.cooperado.enums.RNECondicaoEnum;
import br.com.unimedcuritiba.movcad.cooperado.enums.RacaCorEnum;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;

/**
 * The view class for the V_MCC_SAM_PRESTADOR database table.
 *
 * @author Eloi Bilek
 */
@Entity
@Table(name = "V_MCC_SAM_PRESTADOR")
public class PrestadorVO extends br.com.visionnaire.core.entity.VEntity<Long> {

	private static final long serialVersionUID = -1650999598129268484L;

	@Id
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "prestador", precision = 10, insertable = false, updatable = false)
	private Long codigoPrestador;

	@Column(name = "cpfcnpj", insertable = false, updatable = false)
	private String cpfPrestador;

	@Column(name = "nome", length = 60, insertable = false, updatable = false)
	private String nomePrestador;

	@Column(name = "nomesocial", length = 60, insertable = false, updatable = false)
	private String nomeSocial;

	@Column(name = "rg", length = 14, insertable = false, updatable = false)
	private String numeroRG;

	@Column(name = "orgaoemissor", length = 3, insertable = false, updatable = false)
	private String orgaoEmissorRG;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataexpedicaorg", insertable = false, updatable = false)
	private Date dataExpedicaoRG;

	@Temporal(TemporalType.DATE)
	@Column(name = "datanascimento", insertable = false, updatable = false)
	private Date dataNascimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "naturalidade", insertable = false, updatable = false)
	private MunicipiosVO naturalidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado", insertable = false, updatable = false)
	private EstadosVO estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nacionalidade", insertable = false, updatable = false)
	private PaisesVO nacionalidade;

	@Column(name = "nomemae", length = 60, insertable = false, updatable = false)
	private String nomeMae;

	@Column(name = "nomepai", length = 60, insertable = false, updatable = false)
	private String nomePai;

	@Column(name = "inscricaocr", insertable = false, updatable = false)
	private String inscricaoCRM;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conselhoregional", insertable = false, updatable = false)
	private ConselhoVO conselhoRegional;

	@Temporal(TemporalType.DATE)
	@Column(name = "datainscricaocr", insertable = false, updatable = false)
	private Date dataInscricaoCRM;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadocivil", insertable = false, updatable = false)
	private EstadoCivilVO estadoCivil;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "racaecor", insertable = false, updatable = false)
	private RacaCorEnum racaCor;

	@Column(name = "inscricaoinss", insertable = false, updatable = false)
	private String inscricaoINSS;

	@Column(name = "inscricaomunicipal", insertable = false, updatable = false)
	private String inscricaoMunicipal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipiopagamento", insertable = false, updatable = false)
	private MunicipiosVO municipioPagamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estadopagamento", insertable = false, updatable = false)
	private EstadosVO estadoPagamento;

	@Column(name = "sexo", length = 2, insertable = false, updatable = false)
	private String sexo;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "deffisica", length = 1, insertable = false, updatable = false)
	private Boolean defFisica;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defvisual", length = 1, insertable = false, updatable = false)
	private Boolean defVisual;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defauditiva", length = 1, insertable = false, updatable = false)
	private Boolean defAuditiva;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defmental", length = 1, insertable = false, updatable = false)
	private Boolean defMental;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defintelectual", length = 1, insertable = false, updatable = false)
	private Boolean defIntelectual;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "defreabreadap", length = 1, insertable = false, updatable = false)
	private Boolean defReabilitado;
	
	@Column(name = "obsdeficiencia", length = 255, insertable = false, updatable = false)
	private String obsDeficiencia;

	@Column(name = "email", length = 60, insertable = false, updatable = false)
	private String email;

	@Column(name = "rnenumero", insertable = false, updatable = false)
	private String numeroRNE;

	@Column(name = "rneorgao", insertable = false, updatable = false)
	private String orgaoEmissorRNE;

	@Temporal(TemporalType.DATE)
	@Column(name = "rnedataexpedicao", insertable = false, updatable = false)
	private Date dataExpedicaoRNE;

	@Temporal(TemporalType.DATE)
	@Column(name = "rnedatachegada", insertable = false, updatable = false)
	private Date dataChegadaRNE;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "rnecasadocombras", insertable = false, updatable = false)
	private Boolean casadoComBrasileiro;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "rnefilhosbras", insertable = false, updatable = false)
	private Boolean filhosBrasileiros;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "rnecondicao", insertable = false, updatable = false)
	private RNECondicaoEnum condicaoRNE;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "notivisa", insertable = false, updatable = false)
	private Boolean notivisa;

	@Column(name = "dddfonecelular", insertable = false, updatable = false)
	private String dddFoneCelular;

	@Column(name = "prefixofonecelular", insertable = false, updatable = false)
	private String prefixoFoneCelular;

	@Column(name = "numerofonecelular", insertable = false, updatable = false)
	private String numeroFoneCelular;

	@Column(name = "cnhnumero", insertable = false, updatable = false)
	private String numeroCNH;

	@Temporal(TemporalType.DATE)
	@Column(name = "cnhdataexpedicao", insertable = false, updatable = false)
	private Date dataExpedicaoCNH;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cnhuf", insertable = false, updatable = false)
	private EstadosVO ufCNH;

	@Temporal(TemporalType.DATE)
	@Column(name = "cnhdatavalidade", insertable = false, updatable = false)
	private Date dataValidadeCNH;

	@Temporal(TemporalType.DATE)
	@Column(name = "cnhdatahabilitacao1", insertable = false, updatable = false)
	private Date dataPrimeiraCNH;

	@Column(name = "cnhcategoria", insertable = false, updatable = false)
	private String categoriaCNH;

	@Column(name = "ctpsnumero", insertable = false, updatable = false)
	private String numeroCTPS;

	@Column(name = "ctpsserie", insertable = false, updatable = false)
	private String numeroSerieCTPS;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctpsuf", insertable = false, updatable = false)
	private EstadosVO ufCTPS;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoprestador", insertable = false, updatable = false)
	private TipoPrestadorVO tipoPrestador;

	@Column(name = "fisicajuridica", precision = 10, insertable = false, updatable = false)
	private Integer fisicaJuridica; // 1 = Fisica | 2 = Juridica

	@Temporal(TemporalType.DATE)
	@Column(name = "datacredenciamento", insertable = false, updatable = false)
	private Date dataCredenciamento;

	@Temporal(TemporalType.DATE)
	@Column(name = "datadescredenciamento", insertable = false, updatable = false)
	private Date dataDescredenciamento;

	@Column(name = "AUTORIZAFORNECEREMAIL")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean autorizaFornecerEmail;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datavalidadecrm")
	private Date dataValidadeCRM;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ufemissao", insertable = false, updatable = false)
	private EstadosVO ufEmissao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ufemissaoRNE", insertable = false, updatable = false)
	private EstadosVO ufEmissaoRNE;
	
	@Column(name = "numeroDocumentoDNI", insertable = false, updatable = false)
	private String numeroDocumentoDNI;
	
	@Column(name = "orgaoemissordni", length = 3, insertable = false, updatable = false)
	private String orgaoEmissorDNI;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UFEMISSORDNI", insertable = false, updatable = false)
	private EstadosVO ufEmissorDNI;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataexpedicaodni")
	private Date dataExpedicaoDNI;

	public PrestadorVO() {
	}

	public Long getHandle() {
		return this.handle;
	}

	public void setHandle(final Long handle) {
		this.handle = handle;
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

	public TipoPrestadorVO getTipoPrestador() {
		return this.tipoPrestador;
	}

	public void setTipoPrestador(final TipoPrestadorVO tipoprestador) {
		this.tipoPrestador = tipoprestador;
	}

	/**
	 * Tipo pessoa prestador
	 *
	 * @return 1 = Fisica | 2 = Juridica
	 */
	public Integer getFisicaJuridica() {
		return this.fisicaJuridica;
	}

	public void setFisicaJuridica(final Integer fisicajuridica) {
		this.fisicaJuridica = fisicajuridica;
	}

	public TipoPessoa getTipoPessoa() {
		if (fisicaJuridica != null) {
			return TipoPessoa.get(fisicaJuridica);
		}
		return null;
	}

	public Date getDataCredenciamento() {
		return dataCredenciamento;
	}

	public void setDataCredenciamento(final Date dataCredenciamento) {
		this.dataCredenciamento = dataCredenciamento;
	}

	public Date getDataDescredenciamento() {
		return dataDescredenciamento;
	}

	public void setDataDescredenciamento(final Date dataDescredenciamento) {
		this.dataDescredenciamento = dataDescredenciamento;
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

	public PaisesVO getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(final PaisesVO nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(final String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(final String nomePai) {
		this.nomePai = nomePai;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(final String sexo) {
		this.sexo = sexo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
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

	public Boolean getFilhosBrasileiros() {
		return filhosBrasileiros;
	}

	public void setFilhosBrasileiros(final Boolean filhosBrasileiros) {
		this.filhosBrasileiros = filhosBrasileiros;
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

	public String getNumeroCNH() {
		return numeroCNH;
	}

	public void setNumeroCNH(final String numeroCNH) {
		this.numeroCNH = numeroCNH;
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

	public String getCategoriaCNH() {
		return categoriaCNH;
	}

	public void setCategoriaCNH(final String categoriaCNH) {
		this.categoriaCNH = categoriaCNH;
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

	public void setNumeroSerieCTPS(final String serieCTPS) {
		this.numeroSerieCTPS = serieCTPS;
	}

	public EstadosVO getUfCTPS() {
		return ufCTPS;
	}

	public void setUfCTPS(final EstadosVO ufCTPS) {
		this.ufCTPS = ufCTPS;
	}

	@Override
	public void setPkValue(final Long arg0) {
		// TODO Auto-generated method stub

	}

	public Boolean getAutorizaFornecerEmail() {
		return autorizaFornecerEmail;
	}

	public void setAutorizaFornecerEmail(final Boolean autorizaFornecerEmail) {
		this.autorizaFornecerEmail = autorizaFornecerEmail;
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

	public EstadosVO getUfEmissorDNI() {
		return ufEmissorDNI;
	}

	public void setUfEmissorDNI(EstadosVO ufEmissorDNI) {
		this.ufEmissorDNI = ufEmissorDNI;
	}

	public Date getDataExpedicaoDNI() {
		return dataExpedicaoDNI;
	}

	public void setDataExpedicaoDNI(Date dataExpedicaoDNI) {
		this.dataExpedicaoDNI = dataExpedicaoDNI;
	}

	
}
