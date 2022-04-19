package persistance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class GenericDao {
	private Connection c;
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		String hostNome = "127.0.0.1";
		String bdNome = "CAMPEONATO";
		String user = "sa";
		String senha = "sqlservererick";
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		c = DriverManager.getConnection(String.format("jdbc:jtds:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;instance=SQLEXPRESS;namedPipes=false",hostNome,bdNome,user,senha));
		return c;
	}
}
