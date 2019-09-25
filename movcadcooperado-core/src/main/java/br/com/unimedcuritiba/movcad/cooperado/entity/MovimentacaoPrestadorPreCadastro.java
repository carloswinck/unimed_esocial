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
import br.com.unimedcuritiba.benner.util.TipoPessoa;
import br.com.unimedcuritiba.benner.vo.CategoriaVO;
import br.com.unimedcuritiba.benner.vo.ConselhoVO;
import br.com.unimedcuritiba.benner.vo.EspecialidadeVO;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.unimedcuritiba.benner.vo.TipoISSVO;
import br.com.unimedcuritiba.benner.vo.TipoPrestadorVO;
import br.com.unimedcuritiba.movcad.cooperado.enums.SituacaoEnumPreCad;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.CustomDate;

@Entity
@Table(name = "K_MCC_PRESTADOR_PES_PRE_CAD")
public class MovimentacaoPrestadorPreCadastro
    extends VEntity<Long>
{

	private static final long serialVersionUID = 7324848169131061341L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC010", sequenceName = "SEQ_MCC010", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC010")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "CPF")
	private String cpfPrestador;

	@Column(name = "NOME", length = 60)
	private String nomePrestador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORIA")
	private CategoriaVO categoria;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "SOLICITANTE", length = 1)
	private Boolean solicitante;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EXECUTANTE", length = 1)
	private Boolean executante;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "RECEBEDOR", length = 1)
	private Boolean recebedor;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "LOCALEXECUCAO", length = 1)
	private Boolean localExecucao;

	@Column(name = "EMAIL", length = 60)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAINCLUSAO")
	private Date dataInclusao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATAATUALIZACAO")
	private Date dataAtualizacao;

	@Column(name = "CODIGO")
	private Long codigoPrestador;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "SITUACAO")
	private SituacaoEnumPreCad situacao;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "OBRIGATORIOINFORMARSOLICITAN", length = 1)
	private Boolean obrigarInformarProfissionalSolicitan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MUNICIPIOALVARA")
	private MunicipiosVO municipioAlvara;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADOALVARA")
	private EstadosVO estadoAlvara;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ISS")
	private TipoISSVO iss;

	@Column(name = "PRAZORECURSO")
	private Long prazoRecurso;

	@Column(name = "NUMERORECURSOPERMITIDO")
	private Long numeroRecusosPermitidos;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "PAGAVALORAPOSENTADOZERADO", length = 1)
	private Boolean pagaValorApresentadoZerado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPOPRESTADOR")
	private TipoPrestadorVO tipoPrestador;

	@Column(name = "FISICAJURIDICA")
	private TipoPessoa pessoa;

	@Column(name = "MOTIVORECUSA")
	private String motivoRecusa;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EMAILPRECAD", length = 1)
	private Boolean emailPreCadastro;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EMAILPRECADRECUSADO", length = 1)
	private Boolean emailPreCadastroRecusado;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EMAILPRECADAPROVADO", length = 1)
	private Boolean emailPreCadastroAprovado;

	@Column(name = "REGISTRO")
	private String registro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONSELHOREGIONAL")
	private ConselhoVO conselhoRegional;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESPECIALIDADE")
	private EspecialidadeVO especialidade;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECORESIDENCIALPRECADASTRO")
	private MovimentacaoPrestadorEnderecoPreCadastro movimentacaoPrestadorEnderecoPreCadastro;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DADOSFINANCEIROS")
	private MovimentacaoPrestadorFinanceiroPreCadastro movimentacaoPrestadorFinanceiroPreCadastro;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PARCELASCOTASPARTES")
	private MovimentacaoPrestadorCotasPartesPreCadastro movimentacaoPrestadorCotasPartesPreCadastro;

	@OneToMany(mappedBy = "movimentacaoPrestadorPreCadastro", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MovimentacaoPrestadorAnexoPreCadastro> anexosPreCadastroList;

	public MovimentacaoPrestadorPreCadastro ()
	{
	}

	@PrePersist
	public void prePersist ()
	{
		this.dataInclusao = CustomDate.getCurrentDate();
	}

	@PreUpdate
	public void preUpdate ()
	{
		this.dataAtualizacao = CustomDate.getCurrentDate();
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long id)
	{
		this.id = id;
	}

	public String getCpfPrestador ()
	{
		return cpfPrestador;
	}

	public void setCpfPrestador (final String cpfPrestador)
	{
		this.cpfPrestador = cpfPrestador;
	}

	public String getNomePrestador ()
	{
		return nomePrestador.toUpperCase();
	}

	public void setNomePrestador (final String nomePrestador)
	{
		this.nomePrestador = nomePrestador.toUpperCase();
	}

	public String getEmail ()
	{
		return email;
	}

	public void setEmail (final String email)
	{
		this.email = email;
	}

	public Boolean getSolicitante ()
	{
		return solicitante;
	}

	public void setSolicitante (final Boolean solicitante)
	{
		this.solicitante = solicitante;
	}

	public Boolean getExecutante ()
	{
		return executante;
	}

	public void setExecutante (final Boolean executante)
	{
		this.executante = executante;
	}

	public Boolean getRecebedor ()
	{
		return recebedor;
	}

	public void setRecebedor (final Boolean recebedor)
	{
		this.recebedor = recebedor;
	}

	public Boolean getLocalExecucao ()
	{
		return localExecucao;
	}

	public void setLocalExecucao (final Boolean localExecucao)
	{
		this.localExecucao = localExecucao;
	}

	public String getRegistro ()
	{
		return registro;
	}

	public void setRegistro (final String registro)
	{
		this.registro = registro;
	}

	public ConselhoVO getConselhoRegional ()
	{
		return conselhoRegional;
	}

	public void setConselhoRegional (final ConselhoVO conselhoRegional)
	{
		this.conselhoRegional = conselhoRegional;
	}

	public EspecialidadeVO getEspecialidade ()
	{
		return especialidade;
	}

	public void setEspecialidade (final EspecialidadeVO especialidade)
	{
		this.especialidade = especialidade;
	}

	public MovimentacaoPrestadorEnderecoPreCadastro getMovimentacaoPrestadorEnderecoPreCadastro ()
	{
		return movimentacaoPrestadorEnderecoPreCadastro;
	}

	public void setMovimentacaoPrestadorEnderecoPreCadastro (final MovimentacaoPrestadorEnderecoPreCadastro movimentacaoPrestadorEnderecoPreCadastro)
	{
		this.movimentacaoPrestadorEnderecoPreCadastro = movimentacaoPrestadorEnderecoPreCadastro;
	}

	public Date getDataInclusao ()
	{
		return dataInclusao;
	}

	public void setDataInclusao (final Date dataInclusao)
	{
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAtualizacao ()
	{
		return dataAtualizacao;
	}

	public void setDataAtualizacao (final Date dataAtualizacao)
	{
		this.dataAtualizacao = dataAtualizacao;
	}

	public CategoriaVO getCategoria ()
	{
		return categoria;
	}

	public void setCategoria (final CategoriaVO categoria)
	{
		this.categoria = categoria;
	}

	public SituacaoEnumPreCad getSituacao ()
	{
		return situacao;
	}

	public void setSituacao (final SituacaoEnumPreCad situacao)
	{
		this.situacao = situacao;
	}

	public Long getCodigoPrestador ()
	{
		return codigoPrestador;
	}

	public void setCodigoPrestador (final Long codigoPrestador)
	{
		this.codigoPrestador = codigoPrestador;
	}

	public Boolean getObrigarInformarProfissionalSolicitan ()
	{
		return obrigarInformarProfissionalSolicitan;
	}

	public void setObrigarInformarProfissionalSolicitan (final Boolean obrigarInformarProfissionalSolicitan)
	{
		this.obrigarInformarProfissionalSolicitan = obrigarInformarProfissionalSolicitan;
	}

	public MunicipiosVO getMunicipioAlvara ()
	{
		return municipioAlvara;
	}

	public void setMunicipioAlvara (final MunicipiosVO municipioAlvara)
	{
		this.municipioAlvara = municipioAlvara;
	}

	public EstadosVO getEstadoAlvara ()
	{
		return estadoAlvara;
	}

	public void setEstadoAlvara (final EstadosVO estadoAlvara)
	{
		this.estadoAlvara = estadoAlvara;
	}

	public Long getPrazoRecurso ()
	{
		return prazoRecurso;
	}

	public void setPrazoRecurso (final Long prazoRecurso)
	{
		this.prazoRecurso = prazoRecurso;
	}

	public Long getNumeroRecusosPermitidos ()
	{
		return numeroRecusosPermitidos;
	}

	public void setNumeroRecusosPermitidos (final Long numeroRecusosPermitidos)
	{
		this.numeroRecusosPermitidos = numeroRecusosPermitidos;
	}

	public Boolean getPagaValorApresentadoZerado ()
	{
		return pagaValorApresentadoZerado;
	}

	public void setPagaValorApresentadoZerado (final Boolean pagaValorApresentadoZerado)
	{
		this.pagaValorApresentadoZerado = pagaValorApresentadoZerado;
	}

	public TipoPrestadorVO getTipoPrestador ()
	{
		return tipoPrestador;
	}

	public void setTipoPrestador (final TipoPrestadorVO tipoPrestador)
	{
		this.tipoPrestador = tipoPrestador;
	}

	public TipoPessoa getPessoa ()
	{
		return pessoa;
	}

	public void setPessoa (final TipoPessoa pessoa)
	{
		this.pessoa = pessoa;
	}

	public MovimentacaoPrestadorFinanceiroPreCadastro getMovimentacaoPrestadorFinanceiroPreCadastro ()
	{
		return movimentacaoPrestadorFinanceiroPreCadastro;
	}

	public void setMovimentacaoPrestadorFinanceiroPreCadastro (
	    final MovimentacaoPrestadorFinanceiroPreCadastro movimentacaoPrestadorFinanceiroPreCadastro)
	{
		this.movimentacaoPrestadorFinanceiroPreCadastro = movimentacaoPrestadorFinanceiroPreCadastro;
	}

	public MovimentacaoPrestadorCotasPartesPreCadastro getMovimentacaoPrestadorCotasPartesPreCadastro ()
	{
		return movimentacaoPrestadorCotasPartesPreCadastro;
	}

	public void setMovimentacaoPrestadorCotasPartesPreCadastro (
	    final MovimentacaoPrestadorCotasPartesPreCadastro movimentacaoPrestadorCotasPartesPreCadastro)
	{
		this.movimentacaoPrestadorCotasPartesPreCadastro = movimentacaoPrestadorCotasPartesPreCadastro;
	}

	public TipoISSVO getIss ()
	{
		return iss;
	}

	public void setIss (final TipoISSVO iss)
	{
		this.iss = iss;
	}

	public List<MovimentacaoPrestadorAnexoPreCadastro> getAnexosPreCadastroList ()
	{
		return anexosPreCadastroList;
	}

	public void setAnexosPreCadastroList (final List<MovimentacaoPrestadorAnexoPreCadastro> anexosPreCadastroList)
	{
		this.anexosPreCadastroList = anexosPreCadastroList;
	}

	public String getMotivoRecusa ()
	{
		return motivoRecusa;
	}

	public void setMotivoRecusa (final String motivoRecusa)
	{
		this.motivoRecusa = motivoRecusa;
	}

	public Boolean getEmailPreCadastro ()
	{
		return emailPreCadastro;
	}

	public void setEmailPreCadastro (final Boolean emailPreCadastro)
	{
		this.emailPreCadastro = emailPreCadastro;
	}

	public Boolean getEmailPreCadastroRecusado ()
	{
		return emailPreCadastroRecusado;
	}

	public void setEmailPreCadastroRecusado (final Boolean emailPreCadastroRecusado)
	{
		this.emailPreCadastroRecusado = emailPreCadastroRecusado;
	}

	public Boolean getEmailPreCadastroAprovado ()
	{
		return emailPreCadastroAprovado;
	}

	public void setEmailPreCadastroAprovado (final Boolean emailPreCadastroAprovado)
	{
		this.emailPreCadastroAprovado = emailPreCadastroAprovado;
	}

	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub
	}
}
