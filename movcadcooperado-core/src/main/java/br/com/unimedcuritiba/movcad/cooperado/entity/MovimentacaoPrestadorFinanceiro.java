package br.com.unimedcuritiba.movcad.cooperado.entity;

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
import br.com.unimedcuritiba.benner.vo.AgenciaVO;
import br.com.unimedcuritiba.benner.vo.BancoVO;
import br.com.unimedcuritiba.benner.vo.ContaFinVO;
import br.com.visionnaire.core.dao.converter.BooleanToSimNaoConverter;
import br.com.visionnaire.core.entity.VEntity;
import br.com.visionnaire.core.util.beancomparator.Compare;
import br.com.visionnaire.core.util.beancomparator.CompareJoin;

@Entity
@Table(name = "K_MCC_PRESTADOR_FINANCEIRO")
public class MovimentacaoPrestadorFinanceiro
    extends VEntity<Long>
{

	private static final long serialVersionUID = 1581257916698487190L;

	@Id
	@SequenceGenerator(name = "SEQ_MCC009", sequenceName = "SEQ_MCC009", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MCC009")
	@Column(name = "handle", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIMENTACAOPRESTADOR")
	private MovimentacaoPrestador movimentacaoPrestador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTAFIN")
	private ContaFinVO contaFin;

	@CompareJoin(key = "movimentacaoPrestadorFinanceiro.banco", field = "nome")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANCO")
	private BancoVO banco;

	@CompareJoin(key = "movimentacaoPrestadorFinanceiro.agencia", field = "nome")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AGENCIA")
	private AgenciaVO agencia;

	@Compare(key = "movimentacaoPrestadorFinanceiro.conta")
	@Column(name = "CONTACORRENTE")
	private String contaCorrente;

	@Compare(key = "movimentacaoPrestadorFinanceiro.dv")
	@Column(name = "DV")
	private String dv;

	@Compare(key = "movimentacaoPrestadorFinanceiro.poupanca")
	@Convert(converter = BooleanToSimNaoConverter.class)
	@Column(name = "EHCONTAPOUPANCA")
	private Boolean contaPoupanca;

	@OneToMany(mappedBy = "prestadorFinanceiro", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<LogAuditoria> logsAuditoria;

	public MovimentacaoPrestadorFinanceiro ()
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

	public ContaFinVO getContaFin ()
	{
		return contaFin;
	}

	public void setContaFin (final ContaFinVO contaFin)
	{
		this.contaFin = contaFin;
	}

	public BancoVO getBanco ()
	{
		return banco;
	}

	public void setBanco (final BancoVO banco)
	{
		this.banco = banco;
	}

	public AgenciaVO getAgencia ()
	{
		return agencia;
	}

	public void setAgencia (final AgenciaVO agencia)
	{
		this.agencia = agencia;
	}

	public String getContaCorrente ()
	{
		return contaCorrente;
	}

	public void setContaCorrente (final String contaCorrente)
	{
		this.contaCorrente = contaCorrente;
	}

	public String getDv ()
	{
		return dv;
	}

	public void setDv (final String dv)
	{
		this.dv = dv;
	}

	public Boolean getContaPoupanca ()
	{
		return contaPoupanca;
	}

	public void setContaPoupanca (final Boolean contaPoupanca)
	{
		this.contaPoupanca = contaPoupanca;
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
