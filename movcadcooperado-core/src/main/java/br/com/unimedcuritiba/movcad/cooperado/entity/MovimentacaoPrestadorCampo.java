package br.com.unimedcuritiba.movcad.cooperado.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import br.com.unimedcuritiba.benner.vo.ZCampoVO;
import br.com.unimedcuritiba.benner.vo.ZTabelaVO;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.CustomDate;

@Entity
@Table(name = "K_MCC_PRESTADOR_CAMPO")
public class MovimentacaoPrestadorCampo
    extends VEntity<Long>
{

	private static final long serialVersionUID = 6845347156341461630L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC007", sequenceName = "SEQ_MCC007", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC007")
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "CAMPO")
	private String campoNome;

	@Column(name = "TABELAMCCNOME")
	private String tabelaMccNome;

	@Column(name = "LEGENDA")
	private String legenda;

	@Column(name = "ORDEM")
	private Integer ordem;

	@JoinColumn(name = "ZTABELA")
	@ManyToOne(fetch = FetchType.LAZY)
	private ZTabelaVO tabela;

	@JoinColumn(name = "ZCAMPO")
	@ManyToOne(fetch = FetchType.LAZY)
	private ZCampoVO campo;

	@Column(name = "ANEXO")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean anexo;

	@Column(name = "ANALISE")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean analise;

	@Column(name = "OBRIGATORIO")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean obrigatorio;

	@Column(name = "DESATIVAR")
	@Convert(converter = BooleanToSimNaoConverter.class)
	private Boolean disable;

	@Column(name = "DATAPARAMETRIZACAO")
	private Date dataParametrizacao;

	@Column(name = "USUARIOPARAMETRIZACAO")
	private Long usuarioParametrizacao;

	public MovimentacaoPrestadorCampo ()
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

	public String getCampoNome ()
	{
		return campoNome;
	}

	public void setCampoNome (final String campoNome)
	{
		this.campoNome = campoNome;
	}

	public String getTabelaMccNome ()
	{
		return tabelaMccNome;
	}

	public void setTabelaMccNome (final String tabelaMccNome)
	{
		this.tabelaMccNome = tabelaMccNome;
	}

	public String getLegenda ()
	{
		return legenda;
	}

	public void setLegenda (final String legenda)
	{
		this.legenda = legenda;
	}

	public Integer getOrdem ()
	{
		return ordem;
	}

	public void setOrdem (final Integer ordem)
	{
		this.ordem = ordem;
	}

	public ZTabelaVO getTabela ()
	{
		return tabela;
	}

	public void setTabela (final ZTabelaVO tabela)
	{
		this.tabela = tabela;
	}

	public ZCampoVO getCampo ()
	{
		return campo;
	}

	public void setCampo (final ZCampoVO campo)
	{
		this.campo = campo;
	}

	public Boolean getAnexo ()
	{
		return anexo;
	}

	public void setAnexo (final Boolean anexo)
	{
		this.anexo = anexo;
	}

	public Boolean getAnalise ()
	{
		return analise;
	}

	public void setAnalise (final Boolean analise)
	{
		this.analise = analise;
	}

	public Boolean getObrigatorio ()
	{
		return obrigatorio;
	}

	public void setObrigatorio (final Boolean obrigatorio)
	{
		this.obrigatorio = obrigatorio;
	}

	public Boolean getDisable ()
	{
		return disable;
	}

	public void setDisable (final Boolean disable)
	{
		this.disable = disable;
	}

	public Date getDataParametrizacao ()
	{
		return dataParametrizacao;
	}

	public void setDataParametrizacao (final Date dataParametrizacao)
	{
		this.dataParametrizacao = dataParametrizacao;
	}

	public Long getUsuarioParametrizacao ()
	{
		return usuarioParametrizacao;
	}

	public void setUsuarioParametrizacao (final Long usuarioParametrizacao)
	{
		this.usuarioParametrizacao = usuarioParametrizacao;
	}

	@PrePersist
	public void prePersist ()
	{
		this.dataParametrizacao = CustomDate.getCurrentDate();
	}

	@PreUpdate
	public void preUpdate ()
	{
		this.dataParametrizacao = CustomDate.getCurrentDate();
	}

	@Override
	public void setPkValue (final Long value)
	{
	}

}
