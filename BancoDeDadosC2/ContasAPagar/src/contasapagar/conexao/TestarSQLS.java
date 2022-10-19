package contasapagar.conexao;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import contasapagar.dao.ContaDAO;
import contasapagar.dao.ParcelaDAO;
import contasapagar.modelo.Conta;
import contasapagar.modelo.Parcela;

public class TestarSQLS {

	public static void main(String[] args) {

		testarInserirParcela();

	}
	
	public static void testarInserirConta() {
		Conta conta = new Conta();
		conta.setTipo(2);
		conta.setDataLiquidacao(null);
		
		var contaDAO = new ContaDAO();
		contaDAO.inserir(conta);
		
		contaDAO.listar();
	}
	
	public static void testarAlterarConta() {
		Conta conta = new Conta(5, 0, null);
		conta.setTipo(2);
		conta.setDataLiquidacao(Date.valueOf(LocalDate.now().plusDays(3)));
		
		var contaDAO = new ContaDAO();
		contaDAO.alterar(conta);
		
		contaDAO.listar();
	}
	
	public static void testarRemoverConta() {
		var contaDAO = new ContaDAO();
		contaDAO.remover(1);
		
		contaDAO.listar();
	}
	
	public static void testarInserirParcela() {
		Parcela parcelasAPagar = new Parcela();
		parcelasAPagar.setDataVencimento(Date.valueOf(LocalDate.now()));
		parcelasAPagar.setNumeroParcela(1);
		parcelasAPagar.setValor(BigDecimal.valueOf(100));
		parcelasAPagar.setIdConta(2);

		ParcelaDAO parcelaAPagarDAO = new ParcelaDAO();
		parcelaAPagarDAO.inserir(parcelasAPagar);
		
		parcelaAPagarDAO.listar();
	}

	public static void testarAlterarParcela() {
		Parcela parcelasAPagar = new Parcela(11, 6, null, null, 1, BigDecimal.valueOf(182));
		parcelasAPagar.setDataVencimento(Date.valueOf(LocalDate.now()));

		ParcelaDAO parcelaAPagarDAO = new ParcelaDAO();
		parcelaAPagarDAO.alterar(parcelasAPagar);
		
		parcelaAPagarDAO.listar();
	}
	
	public static void testarRemoverParcela() {
		ParcelaDAO parcelaAPagarDAO = new ParcelaDAO();
		parcelaAPagarDAO.remover(10);
		
		parcelaAPagarDAO.listar();
	}

}
