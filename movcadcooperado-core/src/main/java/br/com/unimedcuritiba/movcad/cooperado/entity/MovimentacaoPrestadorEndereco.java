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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.FilterDef;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.unimedcuritiba.benner.vo.PrestadorEnderecoVO;
import br.com.unimedcuritiba.benner.vo.TipoLogradouroVO;
import br.com.visionnaire.core.common.validations.Validator;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;
import br.com.visionnaire.core.util.beancomparator.CompareJoin;

/**
 * The persistent class for the K_MCC_PRESTADOR_ENDERECO database table.
 *
 * @author Eloi Bilek
 * @since 23 de jun de 2016
 */
@Entity
@Table(name = "K_MCC_PRESTADOR_ENDERECO")
@FilterDef(name = "atendimento", defaultCondition = "atendimento = 'S' AND mcctipoatendimento = 'S' AND dataExclusao IS NULL")
public class MovimentacaoPrestadorEndereco
    extends VEntity<Long>
{

	private static final long serialVersionUID = -3856205738912488293L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC003", sequenceName = "SEQ_MCC003", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC003")
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movimentacaoprestador")
	private MovimentacaoPrestador movimentacaoPrestador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enderecoprestador")
	private PrestadorEnderecoVO prestadorEnderecoVO;

	@Compare(key = "movimentacaoPrestadorEndereco.cep")
	@Column(name = "cep")
	private String cep;

	@CompareJoin(key = "movimentacaoPrestadorEndereco.tipoLogradouro", field = "descricao")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipologradouro")
	private TipoLogradouroVO tipoLogradouro;

	@Compare(key = "movimentacaoPrestadorEndereco.logradouro")
	@Column(name = "logradouro")
	private String logradouro;

	@Compare(key = "movimentacaoPrestadorEndereco.numero")
	@Column(name = "numero")
	private Integer numero;

	@Compare(key = "movimentacaoPrestadorEndereco.complemento")
	@Column(name = "complemento")
	private String complemento;

	@Compare(key = "movimentacaoPrestadorEndereco.bairro")
	@Column(name = "bairro")
	private String bairro;

	@CompareJoin(key = "movimentacaoPrestadorEndereco.municipio", field = "nome")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio")
	private MunicipiosVO municipio;

	@CompareJoin(key = "movimentacaoPrestadorEndereco.estado", field = "sigla")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado")
	private EstadosVO estado;

	@Compare(key = "movimentacaoPrestadorEndereco.ddd1")
	@Column(name = "ddd1")
	private String ddd1;

	@Compare(key = "movimentacaoPrestadorEndereco.prefixo1")
	@Column(name = "prefixo1")
	private String prefixo1;

	@Compare(key = "movimentacaoPrestadorEndereco.numero1")
	@Column(name = "numero1")
	private String numero1;

	@Compare(key = "movimentacaoPrestadorEndereco.ramal1")
	@Column(name = "ramal1")
	private String ramal1;

	@Compare(key = "movimentacaoPrestadorEndereco.ddd2")
	@Column(name = "ddd2")
	private String ddd2;

	@Compare(key = "movimentacaoPrestadorEndereco.prefixo2")
	@Column(name = "prefixo2")
	private String prefixo2;

	@Compare(key = "movimentacaoPrestadorEndereco.numero2")
	@Column(name = "numero2")
	private String numero2;

	@Compare(key = "movimentacaoPrestadorEndereco.ramal2")
	@Column(name = "ramal2")
	private String ramal2;

	@Compare(key = "movimentacaoPrestadorEndereco.ddd3")
	@Column(name = "ddd3")
	private String ddd3;

	@Compare(key = "movimentacaoPrestadorEndereco.prefixo3")
	@Column(name = "prefixo3")
	private String prefixo3;

	@Compare(key = "movimentacaoPrestadorEndereco.numero3")
	@Column(name = "numero3")
	private String numero3;

	@Compare(key = "movimentacaoPrestadorEndereco.ramal3")
	@Column(name = "ramal3")
	private String ramal3;

	@Compare(key = "movimentacaoPrestadorEndereco.dddFax")
	@Column(name = "dddfax")
	private String dddFax;

	@Compare(key = "movimentacaoPrestadorEndereco.prefixoFax")
	@Column(name = "prefixofax")
	private String prefixoFax;

	@Compare(key = "movimentacaoPrestadorEndereco.numeroFax")
	@Column(name = "numerofax")
	private String numeroFax;

	@Compare(key = "movimentacaoPrestadorEndereco.correspondencia")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "correspondencia")
	private Boolean correspondencia;

	@Compare(key = "movimentacaoPrestadorEndereco.cnes")
	@Column(name = "cnes")
	private Integer cnes;

	@Compare(key = "movimentacaoPrestadorEndereco.atendimento")
	@Column(name = "atendimento")
	private String atendimento;

	@Compare(key = "movimentacaoPrestadorEndereco.pessoal")
	@Column(name = "pessoal")
	private String pessoal;

	@Compare(key = "movimentacaoPrestadorEndereco.dataExclusao")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataexclusao")
	private Date dataExclusao;

	@OneToMany(mappedBy = "movimentacaoPrestadorEndereco", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MovimentacaoPrestadorHorario> prestadorHorarios;

	@OneToMany(mappedBy = "prestadorEndereco", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LogAuditoria> logsAuditoria;

	@Column(name = "mcctipoatendimento")
	private String tipoAtendimento;

	@Transient
	private MovimentacaoPrestadorHorario itemMovimentacaoPrestadorHorario = new MovimentacaoPrestadorHorario();

	public MovimentacaoPrestadorEndereco ()
	{
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long id)
	{
		this.id = id;
	}

	public MovimentacaoPrestador getMovimentacaoPrestador ()
	{
		return movimentacaoPrestador;
	}

	public void setMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		this.movimentacaoPrestador = movimentacaoPrestador;
	}

	public PrestadorEnderecoVO getPrestadorEnderecoVO ()
	{
		return prestadorEnderecoVO;
	}

	public void setPrestadorEnderecoVO (final PrestadorEnderecoVO prestadorEnderecoVO)
	{
		this.prestadorEnderecoVO = prestadorEnderecoVO;
	}

	public String getCep ()
	{
		return cep;
	}

	public void setCep (final String cep)
	{
		this.cep = cep;
	}

	public TipoLogradouroVO getTipoLogradouro ()
	{
		return tipoLogradouro;
	}

	public void setTipoLogradouro (final TipoLogradouroVO tipoLogradouro)
	{
		this.tipoLogradouro = tipoLogradouro;
	}

	public String getLogradouro ()
	{
		return logradouro;
	}

	public void setLogradouro (final String logradouro)
	{
		this.logradouro = logradouro;
	}

	public Integer getNumero ()
	{
		return numero;
	}

	public void setNumero (final Integer numero)
	{
		this.numero = numero;
	}

	public String getComplemento ()
	{
		return complemento;
	}

	public void setComplemento (final String complemento)
	{
		this.complemento = complemento;
	}

	public String getBairro ()
	{
		return bairro;
	}

	public void setBairro (final String bairro)
	{
		this.bairro = bairro;
	}

	public MunicipiosVO getMunicipio ()
	{
		return municipio;
	}

	public void setMunicipio (final MunicipiosVO municipio)
	{
		this.municipio = municipio;
	}

	public EstadosVO getEstado ()
	{
		return estado;
	}

	public void setEstado (final EstadosVO estado)
	{
		this.estado = estado;
	}

	public String getDdd1 ()
	{
		return ddd1;
	}

	public void setDdd1 (final String ddd1)
	{
		this.ddd1 = ddd1;
	}

	public Integer getDdd1T ()
	{
		return Validator.valueOfInteger(ddd1);

	}

	public void setDdd1T (final Integer ddd1)
	{
		this.ddd1 = (ddd1 != null ? ddd1.toString() : null);
	}

	public String getPrefixo1 ()
	{
		return prefixo1;
	}

	public void setPrefixo1 (final String prefixo1)
	{
		this.prefixo1 = prefixo1;
	}

	public String getNumero1 ()
	{
		return numero1;
	}

	public void setNumero1 (final String numero1)
	{
		this.numero1 = numero1;
	}

	public String getRamal1 ()
	{
		return ramal1;
	}

	public void setRamal1 (final String ramal1)
	{
		this.ramal1 = ramal1;
	}

	public String getDdd2 ()
	{
		return ddd2;
	}

	public void setDdd2 (final String ddd2)
	{
		this.ddd2 = ddd2;
	}

	public String getPrefixo2 ()
	{
		return prefixo2;
	}

	public void setPrefixo2 (final String prefixo2)
	{
		this.prefixo2 = prefixo2;
	}

	public String getNumero2 ()
	{
		return numero2;
	}

	public void setNumero2 (final String numero2)
	{
		this.numero2 = numero2;
	}

	public String getRamal2 ()
	{
		return ramal2;
	}

	public void setRamal2 (final String ramal2)
	{
		this.ramal2 = ramal2;
	}

	public String getDdd3 ()
	{
		return ddd3;
	}

	public void setDdd3 (final String ddd3)
	{
		this.ddd3 = ddd3;
	}

	public String getPrefixo3 ()
	{
		return prefixo3;
	}

	public void setPrefixo3 (final String prefixo3)
	{
		this.prefixo3 = prefixo3;
	}

	public String getNumero3 ()
	{
		return numero3;
	}

	public void setNumero3 (final String numero3)
	{
		this.numero3 = numero3;
	}

	public String getRamal3 ()
	{
		return ramal3;
	}

	public void setRamal3 (final String ramal3)
	{
		this.ramal3 = ramal3;
	}

	public String getDddFax ()
	{
		return dddFax;
	}

	public void setDddFax (final String dddFax)
	{
		this.dddFax = dddFax;
	}

	public String getPrefixoFax ()
	{
		return prefixoFax;
	}

	public void setPrefixoFax (final String prefixoFax)
	{
		this.prefixoFax = prefixoFax;
	}

	public String getNumeroFax ()
	{
		return numeroFax;
	}

	public void setNumeroFax (final String numeroFax)
	{
		this.numeroFax = numeroFax;
	}

	public Boolean getCorrespondencia ()
	{
		return correspondencia;
	}

	public void setCorrespondencia (final Boolean correspondencia)
	{
		this.correspondencia = correspondencia;
	}

	public Integer getCnes ()
	{
		return cnes;
	}

	public void setCnes (final Integer cnes)
	{
		this.cnes = cnes;
	}

	public String getAtendimento ()
	{
		return atendimento;
	}

	public void setAtendimento (final String atendimento)
	{
		this.atendimento = atendimento;
	}

	public String getPessoal ()
	{
		return pessoal;
	}

	public void setPessoal (final String pessoal)
	{
		this.pessoal = pessoal;
	}

	public Date getDataExclusao ()
	{
		return dataExclusao;
	}

	public void setDataExclusao (final Date dataExclusao)
	{
		this.dataExclusao = dataExclusao;
	}

	public List<MovimentacaoPrestadorHorario> getPrestadorHorarios ()
	{
		return prestadorHorarios;
	}

	public void setPrestadorHorarios (final List<MovimentacaoPrestadorHorario> prestadorHorarios)
	{
		this.prestadorHorarios = prestadorHorarios;
	}

	public MovimentacaoPrestadorHorario getItemMovimentacaoPrestadorHorario ()
	{
		return itemMovimentacaoPrestadorHorario;
	}

	public void setItemMovimentacaoPrestadorHorario (final MovimentacaoPrestadorHorario itemMovimentacaoPrestadorHorario)
	{
		this.itemMovimentacaoPrestadorHorario = itemMovimentacaoPrestadorHorario;
	}

	public List<LogAuditoria> getLogsAuditoria ()
	{
		return logsAuditoria;
	}

	public void setLogsAuditoria (final List<LogAuditoria> logsAuditoria)
	{
		this.logsAuditoria = logsAuditoria;
	}

	public String getTipoAtendimento ()
	{
		return tipoAtendimento;
	}

	public void setTipoAtendimento (final String tipoAtendimento)
	{
		this.tipoAtendimento = tipoAtendimento;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}
}
