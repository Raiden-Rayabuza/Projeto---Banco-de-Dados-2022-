package persistance;

import java.sql.SQLException;
import java.util.List;

import model.Grupos;

public interface IGrupoDao {
	public void sorteiaGrupos() throws SQLException, ClassNotFoundException;
	public List<Grupos> getGrupo(Grupos g) throws SQLException, ClassNotFoundException;
	public List<Grupos> getGrupos() throws SQLException, ClassNotFoundException;
}
