/*
 * %W% %E%
 *
 * Copyright (c) 2016, Visionnaire Informática S.A.
 * Todos os direitos reservados.
 *
 */
package br.com.unimedcuritiba.benner.vo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;

@Entity
@Table(name = "V_SAM_PRESTADOR_ESPECIALIDADE")
public class PrestadorEspecialidadeVO
    extends VEntity<Long>
{

	private static final long serialVersionUID = -5187012323681580009L;

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Long handle;

	@Column(name = "DATAINICIAL", insertable = false, updatable = false)
	private Date dataInicial;

	@Column(name = "DATAFINAL", insertable = false, updatable = false)
	private Date dataFinal;

	@Column(name = "REGISTRO", insertable = false, updatable = false)
	private Long registro;

	@Column(name = "PRINCIPAL", insertable = false, updatable = false)
	private String principal;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "TEMPORARIO", length = 1, insertable = false, updatable = false)
	private Boolean temporario;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "PUBLICARNOLIVRO", length = 1, insertable = false, updatable = false)
	private Boolean publicarLivro;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "PUBLICARINTERNET", length = 1, insertable = false, updatable = false)
	private Boolean publicarInternet;

	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "VISUALIZARCENTRAL", length = 1, insertable = false, updatable = false)
	private Boolean visualizarCentral;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADOR", insertable = false, updatable = false)
	private PrestadorVO prestadorVO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESPECIALIDADE", insertable = false, updatable = false)
	private EspecialidadeVO especialidade;

	public PrestadorEspecialidadeVO ()
	{
	}

	public Long getHandle ()
	{
		return handle;
	}

	public void setHandle (final Long handle)
	{
		this.handle = handle;
	}

	public Date getDataInicial ()
	{
		return dataInicial;
	}

	public void setDataInicial (final Date dataInicial)
	{
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal ()
	{
		return dataFinal;
	}

	public void setDataFinal (final Date dataFinal)
	{
		this.dataFinal = dataFinal;
	}

	public Boolean getTemporario ()
	{
		return temporario;
	}

	public void setTemporario (final Boolean temporario)
	{
		this.temporario = temporario;
	}

	public Boolean getPublicarLivro ()
	{
		return publicarLivro;
	}

	public void setPublicarLivro (final Boolean publicarLivro)
	{
		this.publicarLivro = publicarLivro;
	}

	public Boolean getPublicarInternet ()
	{
		return publicarInternet;
	}

	public void setPublicarInternet (final Boolean publicarInternet)
	{
		this.publicarInternet = publicarInternet;
	}

	public Boolean getVisualizarCentral ()
	{
		return visualizarCentral;
	}

	public void setVisualizarCentral (final Boolean visualizarCentral)
	{
		this.visualizarCentral = visualizarCentral;
	}

	public PrestadorVO getPrestadorVO ()
	{
		return prestadorVO;
	}

	public void setPrestadorVO (final PrestadorVO prestadorVO)
	{
		this.prestadorVO = prestadorVO;
	}

	public String getPrincipal ()
	{
		return principal;
	}

	public void setPrincipal (final String principal)
	{
		this.principal = principal;
	}

	public EspecialidadeVO getEspecialidade ()
	{
		return especialidade;
	}

	public void setEspecialidade (final EspecialidadeVO especialidade)
	{
		this.especialidade = especialidade;
	}

	public Long getRegistro ()
	{
		return registro;
	}

	public void setRegistro (final Long registro)
	{
		this.registro = registro;
	}

	public void setPkValue (final Long value)
	{
	}
}
