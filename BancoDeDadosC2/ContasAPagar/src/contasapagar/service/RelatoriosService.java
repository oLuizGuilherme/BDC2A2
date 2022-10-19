package contasapagar.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import contasapagar.conexao.CriarConexao;
import contasapagar.dao.ParcelaDAO;
import contasapagar.modelo.Parcela;

public class RelatoriosService {

	private static final String VALOR_TOTAL_A_RECEBER = "select sum(p.valor) as valor_a_receber from contas c inner join parcelas p on c.id = p.id_conta where c.tipo = 2 and c.data_quitacao is null and p.data_pagamento is null";

	private static final String PARCELAS_EM_ABERTO = "select p.* from contas c inner join parcelas p on c.id = p.id_conta where c.tipo = 1 and p.data_pagamento is null and c.id = ?";

	public BigDecimal valorTotalAReceber() {
		Connection con = CriarConexao.criarConexao();
		BigDecimal valor = null;
		try {
			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery(VALOR_TOTAL_A_RECEBER);

			if (rs.next()) {
				valor = rs.getBigDecimal("valor_a_receber");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return valor;
	}

	public String parcelasEmAberto(Integer idConta) {
		Connection con = CriarConexao.criarConexao();
		StringBuilder parcelas = new StringBuilder();
		try {
			PreparedStatement stm = con.prepareStatement(PARCELAS_EM_ABERTO);

			stm.setInt(1, idConta);

			ResultSet rs = stm.executeQuery();

			ParcelaDAO parcelaDAO = new ParcelaDAO();

			while (rs.next()) {
				Parcela parcela = parcelaDAO.rsToObject(rs);
				parcelas.append(parcela.toString()).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parcelas.toString();
	}

}
