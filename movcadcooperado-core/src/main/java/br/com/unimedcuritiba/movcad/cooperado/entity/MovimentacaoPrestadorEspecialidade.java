package br.com.unimedcuritiba.movcad.cooperado.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import br.com.unimedcuritiba.benner.vo.EspecialidadeVO;
import br.com.unimedcuritiba.benner.vo.PrestadorEspecialidadeVO;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;
import br.com.visionnaire.core.util.beancomparator.CompareJoin;

@Entity
@Table(name = "K_MCC_PRESTADOR_ESPECIALIDADE")
public class MovimentacaoPrestadorEspecialidade
    extends VEntity<Long>
{

	private static final long serialVersionUID = -1700019505349005781L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC014", sequenceName = "SEQ_MCC014", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC014")
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long id;

	@Compare(key = "movimentacaoPrestadorEspecialidade.descricao")
	@Column(name = "DESCRICAO")
	private String descricao;

	@Compare(key = "movimentacaoPrestadorEspecialidade.registroEspecialista")
	@Column(name = "REGISTRO")
	private String registro;

	@CompareJoin(key = "movimentacaoPrestadorEspecialidade.especialidade", field = "descricao")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESPECIALIDADE")
	private EspecialidadeVO especialidade;

	@CompareJoin(key = "movimentacaoPrestadorEspecialidade.prestadorEspecialidade", field = "principal")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRESTADORESPECIALIDADE")
	private PrestadorEspecialidadeVO prestadorEspecialidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIMENTACAOPRESTADOR")
	private MovimentacaoPrestador movimentacaoPrestador;

	@OneToMany(mappedBy = "prestadorEspecialidade", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LogAuditoria> logsAuditoria;

	public MovimentacaoPrestadorEspecialidade ()
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

	public String getDescricao ()
	{
		return descricao;
	}

	public void setDescricao (final String descricao)
	{
		this.descricao = descricao;
	}

	public String getRegistro ()
	{
		return registro;
	}

	public void setRegistro (final String registro)
	{
		this.registro = registro;
	}

	public EspecialidadeVO getEspecialidade ()
	{
		return especialidade;
	}

	public void setEspecialidade (final EspecialidadeVO especialidade)
	{
		this.especialidade = especialidade;
	}

	public PrestadorEspecialidadeVO getPrestadorEspecialidade ()
	{
		return prestadorEspecialidade;
	}

	public void setPrestadorEspecialidade (final PrestadorEspecialidadeVO prestadorEspecialidade)
	{
		this.prestadorEspecialidade = prestadorEspecialidade;
	}

	public MovimentacaoPrestador getMovimentacaoPrestador ()
	{
		return movimentacaoPrestador;
	}

	public void setMovimentacaoPrestador (final MovimentacaoPrestador movimentacaoPrestador)
	{
		this.movimentacaoPrestador = movimentacaoPrestador;
	}

	public List<LogAuditoria> getLogsAuditoria ()
	{
		return logsAuditoria;
	}

	public void setLogsAuditoria (final List<LogAuditoria> logsAuditoria)
	{
		this.logsAuditoria = logsAuditoria;
	}

	@Override
	public void setPkValue (final Long value)
	{
	}

}
