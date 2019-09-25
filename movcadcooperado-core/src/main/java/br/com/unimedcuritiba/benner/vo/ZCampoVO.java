package br.com.unimedcuritiba.benner.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_Z_CAMPOS")
public class ZCampoVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = 6074773598229029201L;

	@Id
	@Column(name = "HANDLE", unique = true, nullable = false, precision = 10)
	private Long handle;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tabela", insertable = false, updatable = false)
	private ZTabelaVO tabela;

	@Column(name = "NOME", insertable = false, updatable = false)
	private String nome;

	@Column(name = "LEGENDAFORMULARIO", insertable = false, updatable = false)
	private String legendaFormulario;

	@Column(name = "LEGENDAGRADE", insertable = false, updatable = false)
	private String legendaGrade;

	@Column(name = "ORDEM", insertable = false, updatable = false)
	private Integer ordem;

	@Column(name = "DICA", insertable = false, updatable = false)
	private String dica;

	@Column(name = "CLASSE", insertable = false, updatable = false)
	private Integer classe;

	@Column(name = "OPCIONAL", insertable = false, updatable = false)
	private String opcional;

	public ZCampoVO ()
	{
		super();
	}

	public Long getHandle ()
	{
		return handle;
	}

	public void setHandle (final Long handle)
	{
		this.handle = handle;
	}

	public ZTabelaVO getTabela ()
	{
		return tabela;
	}

	public void setTabela (final ZTabelaVO tabela)
	{
		this.tabela = tabela;
	}

	public String getNome ()
	{
		return nome;
	}

	public void setNome (final String nome)
	{
		this.nome = nome;
	}

	public String getLegendaFormulario ()
	{
		return legendaFormulario;
	}

	public void setLegendaFormulario (final String legendaFormulario)
	{
		this.legendaFormulario = legendaFormulario;
	}

	public String getLegendaGrade ()
	{
		return legendaGrade;
	}

	public void setLegendaGrade (final String legendaGrade)
	{
		this.legendaGrade = legendaGrade;
	}

	public Integer getOrdem ()
	{
		return ordem;
	}

	public void setOrdem (final Integer ordem)
	{
		this.ordem = ordem;
	}

	public String getDica ()
	{
		return dica;
	}

	public void setDica (final String dica)
	{
		this.dica = dica;
	}

	public Integer getClasse ()
	{
		return classe;
	}

	public void setClasse (final Integer classe)
	{
		this.classe = classe;
	}

	public String getOpcional ()
	{
		return opcional;
	}

	public void setOpcional (final String opcional)
	{
		this.opcional = opcional;
	}

	public static long getSerialversionuid ()
	{
		return serialVersionUID;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}

}
