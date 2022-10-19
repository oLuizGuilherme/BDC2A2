package contasapagar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import contasapagar.conexao.CriarConexao;
import contasapagar.modelo.Conta;

public class ContaDAO {

	private static final String INSERIR = "INSERT INTO CONTAS (ID, TIPO, DATA_QUITACAO) VALUES (?, ?, TO_DATE(?))";
	private static final String ALTERAR = "UPDATE CONTAS SET TIPO = ?, DATA_QUITACAO = TO_DATE(?) WHERE ID = ?";
	private static final String REMOVER = "DELETE FROM CONTAS WHERE ID = ?";
	private static final String LISTAR = "SELECT * FROM CONTAS";
	private static final String BUSCAR = "SELECT * FROM CONTAS WHERE ID = ?";
	private static final String BUSCA_PROX_ID = "SELECT MAX(id) as id FROM CONTAS";

	public String listar() {

		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(LISTAR);

			ResultSet rs = stm.executeQuery();
			String contas = "";
			while (rs.next()) {
				Conta conta = new Conta(rs.getInt("id"), rs.getInt("TIPO"), rs.getDate("DATA_QUITACAO"));
				if (conta != null)
					contas += conta.toString() + "\n";
			}

			stm.close();
			con.close();
			return contas;
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

	public Conta buscar(Integer id) {
		if (id == null)
			return null;

		Connection con = CriarConexao.criarConexao();
		Conta conta = null;
		try {
			PreparedStatement stm = con.prepareStatement(BUSCAR);

			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				conta = new Conta(rs.getInt("id"), rs.getInt("TIPO"), rs.getDate("DATA_QUITACAO"));
			}

			stm.close();
			con.close();

			return conta;
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

	public void inserir(Conta conta) {
		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(INSERIR);

			int id = buscaProxIdDisponivel();

			stm.setInt(1, id);
			stm.setInt(2, conta.getTipo());
			stm.setDate(3, conta.getDataLiquidacao());

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

	public void alterar(Conta conta) {

		Connection con = CriarConexao.criarConexao();
		try {
			PreparedStatement stm = con.prepareStatement(ALTERAR);

			stm.setInt(1, conta.getTipo());
			stm.setDate(2, conta.getDataLiquidacao());
			stm.setInt(3, conta.getId());

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

}
