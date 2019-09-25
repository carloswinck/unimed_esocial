/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.movcad.cooperado.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.com.unimedcuritiba.benner.vo.EstadosVO;
import br.com.unimedcuritiba.benner.vo.MunicipiosVO;
import br.com.unimedcuritiba.benner.vo.TipoLogradouroVO;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "K_MCC_PRESTADOR_END_PRE_CAD")
public class MovimentacaoPrestadorEnderecoPreCadastro
    extends VEntity<Long>
{

	private static final long serialVersionUID = 1241590247053911902L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC011", sequenceName = "SEQ_MCC011", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC011")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "CEP")
	private String cep;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPOLOGRADOURO")
	private TipoLogradouroVO tipoLogradouro;

	@Column(name = "LOGRADOURO")
	private String logradouro;

	@Column(name = "NUMERO")
	private Integer numero;

	@Column(name = "COMPLEMENTO")
	private String complemento;

	@Column(name = "BAIRRO")
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MUNICIPIO")
	private MunicipiosVO municipio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO")
	private EstadosVO estado;

	@Column(name = "DDD1")
	private String ddd1;

	@Column(name = "PREFIXO1")
	private String prefixo1;

	@Column(name = "NUMERO1")
	private String numero1;

	@Column(name = "RAMAL1")
	private String ramal1;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "ENDERECOCORRESPONDENCIA", length = 1)
	private Boolean enderecoCorrespondencia;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "ENDERECOATENDIMENTO", length = 1)
	private Boolean enderecoAtendimento;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "ENDERECOPESSOAL", length = 1)
	private Boolean enderecoPessoal;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "ENDERECOPAGAMENTO", length = 1)
	private Boolean enderecoPagamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVPRESTADORPRECADASTRO")
	private MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro;

	public MovimentacaoPrestadorEnderecoPreCadastro ()
	{
		// TODO Auto-generated constructor stub
	}

	public Long getId ()
	{
		return id;
	}

	public void setId (final Long id)
	{
		this.id = id;
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

	public MovimentacaoPrestadorPreCadastro getMovimentacaoPrestadorPreCadastro ()
	{
		return movimentacaoPrestadorPreCadastro;
	}

	public void setMovimentacaoPrestadorPreCadastro (final MovimentacaoPrestadorPreCadastro movimentacaoPrestadorPreCadastro)
	{
		this.movimentacaoPrestadorPreCadastro = movimentacaoPrestadorPreCadastro;
	}

	public Boolean getEnderecoCorrespondencia ()
	{
		return enderecoCorrespondencia;
	}

	public void setEnderecoCorrespondencia (final Boolean enderecoCorrespondencia)
	{
		this.enderecoCorrespondencia = enderecoCorrespondencia;
	}

	public Boolean getEnderecoAtendimento ()
	{
		return enderecoAtendimento;
	}

	public void setEnderecoAtendimento (final Boolean enderecoAtendimento)
	{
		this.enderecoAtendimento = enderecoAtendimento;
	}

	public Boolean getEnderecoPessoal ()
	{
		return enderecoPessoal;
	}

	public void setEnderecoPessoal (final Boolean enderecoPessoal)
	{
		this.enderecoPessoal = enderecoPessoal;
	}

	public Boolean getEnderecoPagamento ()
	{
		return enderecoPagamento;
	}

	public void setEnderecoPagamento (final Boolean enderecoPagamento)
	{
		this.enderecoPagamento = enderecoPagamento;
	}

	@Override
	public void setPkValue (final Long value)
	{
		// TODO Auto-generated method stub

	}

}
