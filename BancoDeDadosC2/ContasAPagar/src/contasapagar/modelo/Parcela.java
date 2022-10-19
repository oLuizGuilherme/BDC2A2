package contasapagar.modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class Parcela {

	private int id;
	private int idConta;
	private Date dataVencimento;
	private Date dataPagamento;
	private int numeroParcela;
	private BigDecimal valor;
	
	
	public Parcela() {
	}

	

	public Parcela(int id, int idConta, Date dataVencimento, Date dataPagamento, int numeroParcela, BigDecimal valor) {
		this.id = id;
		this.idConta = idConta;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.numeroParcela = numeroParcela;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public int getIdConta() {
		return idConta;
	}
	
	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}



	@Override
	public String toString() {
		return "Parcela [id=" + id + ", idConta=" + idConta + ", dataVencimento=" + dataVencimento + ", dataPagamento="
				+ dataPagamento + ", numeroParcela=" + numeroParcela + ", valor=" + valor + "]";
	}
	
	

}
