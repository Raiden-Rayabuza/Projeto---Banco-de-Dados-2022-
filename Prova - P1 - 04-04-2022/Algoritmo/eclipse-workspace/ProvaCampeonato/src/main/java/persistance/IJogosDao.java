package persistance;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import model.Jogos;

public interface IJogosDao {
	public void sorteiaJogos() throws SQLException, ClassNotFoundException;
	public List<Jogos> getJogos() throws SQLException, ClassNotFoundException;
	public List<Jogos> getRodadas(Jogos j) throws SQLException, ClassNotFoundException;
}
