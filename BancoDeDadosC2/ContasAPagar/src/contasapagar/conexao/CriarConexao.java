package contasapagar.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CriarConexao {
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static final String USER = "labdatabase";
	private static final String PASS = "labDatabase2022";

	public static Connection criarConexao() {
		try {
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
