package persistance;

import java.sql.SQLException;
import java.util.List;

import model.Tabela;

public interface ITabelasDao {
	public List<Tabela> getTabela_Grupos(String grupo) throws SQLException, ClassNotFoundException;
	public List<Tabela> getTabela_Campeonato() throws SQLException, ClassNotFoundException;
}
