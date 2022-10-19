package contasapagar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import contasapagar.conexao.CriarConexao;
import contasapagar.modelo.Parcela;

public class ParcelaDAO {

	private static final String INSERIR = "INSERT INTO PARCELAS (ID, ID_CONTA, DATA_VENCIMENTO, DATA_PAGAMENTO, NUMERO_PARCELA, VALOR) VALUES (?, ?, TO_DATE(?), TO_DATE(?), ?, ?)";
	private static final String ALTERAR = "UPDATE PARCELAS SET ID_CONTA = ?, DATA_VENCIMENTO = TO_DATE(?), DATA_PAGAMENTO = TO_DATE(?), NUMERO_PARCELA = ?, VALOR = ? WHERE ID = ?";
	private static final String REMOVER = "DELETE FROM PARCELAS WHERE ID = ?";
	private static final String LISTAR = "SELECT * FROM PARCELAS";
	private static final String BUSCAR = "SELECT * FROM PARCELAS WHERE ID = ?";
	private static final String BUSCAR_POR_CONTA = "SELECT * FROM PARCELAS WHERE ID_CONTA = ?";
	private static final String BUSCA_PROX_ID = "SELECT MAX(id) as id FROM PARCELAS";

	public String listar() {

		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(LISTAR);

			ResultSet rs = stm.executeQuery();

			String parcelas = "";

			while (rs.next()) {
				Parcela parcela = rsToObject(rs);
				if (parcela != null)
					parcelas += parcela.toString() + "\n";
			}

			stm.close();
			con.close();
			return parcelas;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public Parcela buscar(Integer id) {
		if (id == null)
			return null;

		Connection con = CriarConexao.criarConexao();
		Parcela parcela = null;
		try {
			PreparedStatement stm = con.prepareStatement(BUSCAR);

			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				parcela = new Parcela(rs.getInt("id"), rs.getInt("id_conta"), rs.getDate("data_vencimento"),
						rs.getDate("data_pagamento"), rs.getInt("numero_parcela"), rs.getBigDecimal("valor"));
			}

			stm.close();
			con.close();

			return parcela;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void inserir(Parcela parcela) {
		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(INSERIR);

			int id = buscaProxIdDisponivel();

			stm.setInt(1, id);
			stm.setInt(2, parcela.getIdConta());
			stm.setDate(3, parcela.getDataVencimento());
			stm.setDate(4, parcela.getDataPagamento());
			stm.setInt(5, parcela.getNumeroParcela());
			stm.setBigDecimal(6, parcela.getValor());

			stm.execute();

			stm.close();
			con.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void alterar(Parcela parcela) {

		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(ALTERAR);

			stm.setInt(1, parcela.getIdConta());
			stm.setDate(2, parcela.getDataVencimento());
			stm.setDate(3, parcela.getDataPagamento());
			stm.setInt(4, parcela.getNumeroParcela());
			stm.setBigDecimal(5, parcela.getValor());
			stm.setInt(6, parcela.getId());

			stm.execute();

			stm.close();
			con.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void remover(Integer id) {
		if (id == null)
			return;

		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(REMOVER);

			stm.setInt(1, id);

			stm.execute();

			stm.close();
			con.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Parcela> buscarPorConta(int idConta){

		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(BUSCAR_POR_CONTA);

			stm.setInt(1, idConta);

			ResultSet rs= stm.executeQuery();
			
			List<Parcela> parcelas = new ArrayList<>();
			
			while (rs.next()) {
				parcelas.add(rsToObject(rs));
			}

			stm.close();
			con.close();
			
			return parcelas;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Integer buscaProxIdDisponivel() throws SQLException {
		Connection con = CriarConexao.criarConexao();

		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(BUSCA_PROX_ID);
		Integer id = null;
		while (rs.next()) {
			id = rs.getInt("id") + 1;
		}
		stm.close();
		con.close();
		return id;
	}

	public Parcela rsToObject(ResultSet rs) throws SQLException {
		return new Parcela(rs.getInt("id"), rs.getInt("id_conta"), rs.getDate("data_vencimento"),
				rs.getDate("data_pagamento"), rs.getInt("numero_parcela"), rs.getBigDecimal("valor"));
	}

}
