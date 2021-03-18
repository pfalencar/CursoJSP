package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Respons�vel por fazer a conex�o com o BD
 * @author Patricia
 *
 */
public class SingleConnection {
	
	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnet=true";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	//chamada est�tica para o m�todo conectar.
	//Pq a partir do momento que essa classe for invocada, de qlqr forma ele vai invocar o conectar, 
	//para garantir que a conex�o com o BD sempre esteja ativa.
	static { 
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao conectar com o BD.");
		}
	}
	
	
	public static Connection getConnection () {
		return connection;		
	}

}
