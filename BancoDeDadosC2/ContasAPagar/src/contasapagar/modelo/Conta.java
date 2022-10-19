package contasapagar.modelo;

import java.sql.Date;

public class Conta {
	
	private int id;
	
	/**
	 * Assume 2 valores:
	 * 	1 para Conta a Pagar
	 * 	2 para Conta a Receber
	 */
	private int tipo; 
	
	private Date dataLiquidacao;
	
	public Conta() {
	}
	
	public Conta(int id, int tipo, Date dataLiquidacao) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.dataLiquidacao = dataLiquidacao;
	}

	public int getId() {
		return id;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public Date getDataLiquidacao() {
		return dataLiquidacao;
	}
	
	public void setDataLiquidacao(Date dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}

	@Override
	public String toString() {
		return "Conta [id=" + id + ", tipo=" + tipo + ", dataLiquidacao=" + dataLiquidacao + "]";
	}
	
	
	
}
