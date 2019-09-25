/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

/**
 * @author Eloi Bilek
 * @since 1 de jul de 2016
 */
@Entity
@Table(name = "V_MCC_SAM_PRESTADOR_ENDERECO")
public class PrestadorEnderecoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 5443814620068023070L;

	@Id
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long handle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestador", insertable = false, updatable = false)
	private PrestadorVO prestadorVO;

	@Column(name = "cep", insertable = false, updatable = false)
	private String cep;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipologradouro", insertable = false, updatable = false)
	private TipoLogradouroVO tipoLogradouro;

	@Column(name = "logradouro", insertable = false, updatable = false)
	private String logradouro;

	@Column(name = "numero", insertable = false, updatable = false)
	private Integer numero;

	@Column(name = "complemento", insertable = false, updatable = false)
	private String complemento;

	@Column(name = "bairro", insertable = false, updatable = false)
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio", insertable = false, updatable = false)
	private MunicipiosVO municipio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado", insertable = false, updatable = false)
	private EstadosVO estado;

	@Column(name = "ddd1", insertable = false, updatable = false)
	private String ddd1;

	@Column(name = "prefixo1", insertable = false, updatable = false)
	private String prefixo1;

	@Column(name = "numero1", insertable = false, updatable = false)
	private String numero1;

	@Column(name = "ramal1", insertable = false, updatable = false)
	private String ramal1;

	@Column(name = "ddd2", insertable = false, updatable = false)
	private String ddd2;

	@Column(name = "prefixo2", insertable = false, updatable = false)
	private String prefixo2;

	@Column(name = "numero2", insertable = false, updatable = false)
	private String numero2;

	@Column(name = "ramal2", insertable = false, updatable = false)
	private String ramal2;

	@Column(name = "ddd3", insertable = false, updatable = false)
	private String ddd3;

	@Column(name = "prefixo3", insertable = false, updatable = false)
	private String prefixo3;

	@Column(name = "numero3", insertable = false, updatable = false)
	private String numero3;

	@Column(name = "ramal3", insertable = false, updatable = false)
	private String ramal3;

	@Column(name = "dddfax", insertable = false, updatable = false)
	private String dddFax;

	@Column(name = "prefixofax", insertable = false, updatable = false)
	private String prefixoFax;

	@Column(name = "numerofax", insertable = false, updatable = false)
	private String numeroFax;

	@Temporal(TemporalType.DATE)
	@Column(name = "datainicial", insertable = false, updatable = false)
	private Date dataInicial;

	@Temporal(TemporalType.DATE)
	@Column(name = "datacancelamento", insertable = false, updatable = false)
	private Date dataCancelamento;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "correspondencia")
	private Boolean correspondencia;

	@Column(name = "atendimento", insertable = false, updatable = false)
	private String atendimento;

	@Column(name = "cnes", insertable = false, updatable = false)
	private Integer cnes;

	@Column(name = "pessoal", insertable = false, updatable = false)
	private String pessoal;

	@OneToMany(mappedBy = "prestadorEndereco", fetch = FetchType.LAZY)
	private List<PrestadorHorarioVO> horarios;

	public String getPessoal ()
	{
		return pessoal;
	}

	public void setPessoal (final String pessoal)
	{
		this.pessoal = pessoal;
	}

	public Long getHandle ()
	{
		return handle;
	}

	public void setHandle (final Long handle)
	{
		this.handle = handle;
	}

	public PrestadorVO getPrestadorVO ()
	{
		return prestadorVO;
	}

	public void setPrestadorVO (final PrestadorVO prestadorVO)
	{
		this.prestadorVO = prestadorVO;
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

	public Date getDataInicial ()
	{
		return dataInicial;
	}

	public void setDataInicial (final Date dataInicial)
	{
		this.dataInicial = dataInicial;
	}

	public Date getDataCancelamento ()
	{
		return dataCancelamento;
	}

	public void setDataCancelamento (final Date dataCancelamento)
	{
		this.dataCancelamento = dataCancelamento;
	}

	public Boolean getCorrespondencia ()
	{
		return correspondencia;
	}

	public void setCorrespondencia (final Boolean correspondencia)
	{
		this.correspondencia = correspondencia;
	}

	public String getAtendimento ()
	{
		return atendimento;
	}

	public void setAtendimento (final String atendimento)
	{
		this.atendimento = atendimento;
	}

	public Integer getCnes ()
	{
		return cnes;
	}

	public void setCnes (final Integer cnes)
	{
		this.cnes = cnes;
	}

	public List<PrestadorHorarioVO> getHorarios ()
	{
		return horarios;
	}

	public void setHorarios (final List<PrestadorHorarioVO> horarios)
	{
		this.horarios = horarios;
	}

	@Override
	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
