package persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import model.Grupos;
import model.Jogos;
import persistance.GenericDao;

public class JogosDao implements IJogosDao {
	
	private GenericDao gDao;
	
	public JogosDao(GenericDao gDao){
		this.gDao = gDao;
	}
	
	private void atualizaJogos() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql_query = "{CALL sp_checar_jogos}";
		CallableStatement cs = c.prepareCall(sql_query);
		cs.execute();
		cs.close();
		c.close();
	}
	
	public void sorteiaJogos() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		List<Grupos> grupoA = new ArrayList<Grupos>();
		List<Grupos> grupoB = new ArrayList<Grupos>();
		List<Grupos> grupoC = new ArrayList<Grupos>();
		List<Grupos> grupoD = new ArrayList<Grupos>();
		atualizaJogos();
		Random aleatorio = new Random();
		List<LocalDate> datas_jogos =  new ArrayList<LocalDate>();
		int cont1 = 0, cont2 = 0;
		datas_jogos = gerarDatas();
		String sql_query = "INSERT INTO Jogos(codigoTimeA,codigoTimeB,data) VALUES (?,?,?)";
		String sql_query2 = "SELECT COUNT(*) AS linhas FROM Jogos WHERE (codigoTimeA = ? OR codigoTimeB = ?)";
		String sql_query3 = "SELECT * FROM Jogos WHERE ((codigoTimeA = ? AND codigoTimeB = ?) OR (codigoTimeA = ? AND codigoTimeB = ?))";
		String sqlgrupoA = "SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime WHERE grupo = 'A';";
		String sqlgrupoB = "SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime WHERE grupo = 'B';";
		String sqlgrupoC = "SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime WHERE grupo = 'C';";
		String sqlgrupoD = "SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime WHERE grupo = 'D';";
		PreparedStatement ps = c.prepareStatement(sql_query);
		PreparedStatement ps2 = c.prepareStatement(sql_query2);
		PreparedStatement ps3 = c.prepareStatement(sql_query3);
		PreparedStatement ps4 = c.prepareStatement(sqlgrupoA);
		ResultSet rs = ps4.executeQuery();
		while(rs.next()) {
			Grupos grupo = new Grupos();
			grupo.setGrupo(rs.getString("grupo"));
			grupo.setGrupo(rs.getString("codigoTime"));
			grupo.setGrupo(rs.getString("nomeTime"));
			grupoA.add(grupo);
		}
		ps4 = c.prepareStatement(sqlgrupoB);
		rs = ps4.executeQuery();
		while(rs.next()) {
			Grupos grupo = new Grupos();
			grupo.setGrupo(rs.getString("grupo"));
			grupo.setGrupo(rs.getString("codigoTime"));
			grupo.setGrupo(rs.getString("nomeTime"));
			grupoA.add(grupo);
		}
		ps4 = c.prepareStatement(sqlgrupoC);
		rs = ps4.executeQuery();
		while(rs.next()) {
			Grupos grupo = new Grupos();
			grupo.setGrupo(rs.getString("grupo"));
			grupo.setGrupo(rs.getString("codigoTime"));
			grupo.setGrupo(rs.getString("nomeTime"));
			grupoA.add(grupo);
		}
		ps4 = c.prepareStatement(sqlgrupoD);
		rs = ps4.executeQuery();
		while(rs.next()) {
			Grupos grupo = new Grupos();
			grupo.setGrupo(rs.getString("grupo"));
			grupo.setGrupo(rs.getString("codigoTime"));
			grupo.setGrupo(rs.getString("nomeTime"));
			grupoA.add(grupo);
		}
		while(cont1 < 12) {
			while(cont2 < 8) {
				String grupoA = g.get(jogos.get(index)[0] - 1).getGrupo();
				String grupoB = g.get(jogos.get(index)[1] - 1).getGrupo();
				if(!grupoA.equals(grupoB)) {
					ps2.setInt(1, jogos.get(index- 1)[0]);
					ps2.setInt(2, jogos.get(index- 1)[0]);
					ps2.setInt(3, jogos.get(index- 1)[1]);
					ps2.setInt(4, jogos.get(index- 1)[1]);
					ps2.setDate(5, Date.valueOf(datas_jogos.get(cont1)));
					ResultSet rs = ps2.executeQuery();
					
					ps3.setInt(1, jogos.get(index- 1)[0]);
					ps3.setInt(2, jogos.get(index- 1)[1]);
					ps3.setInt(3, jogos.get(index- 1)[1]);
					ps3.setInt(4, jogos.get(index- 1)[0]);
					ResultSet rs2 = ps3.executeQuery();
					if(rs.next() == false) {
						if(rs2.next() == false) {
							ps.setInt(1, jogos.get(index- 1)[0]);
							ps.setInt(2, jogos.get(index- 1)[1]);
							ps.setDate(3, Date.valueOf(datas_jogos.get(cont1)));
							ps.execute();
							cont2++;
							jogos_marcados.add(jogos.get(index- 1));
							jogos.removeAll(jogos_marcados);
						}
					}
				}
			}
			cont2 = 0;
			cont1++;
		}
		
	}
	private List<LocalDate> gerarDatas() throws SQLException, ClassNotFoundException{
		LocalDate data_atual = LocalDate.now();
		LocalDate data_seguinte = LocalDate.now();
		DayOfWeek semana = data_atual.getDayOfWeek();
		List<LocalDate> datas_jogos = new ArrayList<LocalDate>();
		while(datas_jogos.size() < 16) {
			if(semana.getValue() == 3) {
				datas_jogos.add(data_seguinte);
				data_seguinte = data_atual.plusDays(4);
			}
			else if(semana.getValue() == 7) {
				datas_jogos.add(data_seguinte);
				data_seguinte = data_atual.plusDays(3);
			}
			else if(semana.getValue() > 3 && semana.getValue() < 7) {
				data_seguinte = data_atual.plusDays((7 - semana.getValue()));
				datas_jogos.add(data_seguinte);
			}
			else if(semana.getValue() < 3) {
				data_seguinte = data_atual.plusDays((3 - semana.getValue()));
				datas_jogos.add(data_seguinte);
			}
			data_atual = data_seguinte;
			semana = data_atual.getDayOfWeek();
		}
		return datas_jogos;	
	}
	
	/*private void sorteiaTimes(List<int[]> combinations, int data[], int start, int end, int index) throws SQLException, ClassNotFoundException{
		 if (index == data.length) {
	        int[] combination = data.clone();
	        combinations.add(combination);
	    } else if (start <= end) {
	        data[index] = start;
	        sorteiaTimes(combinations, data, start + 1, end, index + 1);
	        sorteiaTimes(combinations, data, start + 1, end, index);
	    }
	}
	private List<int[]> gerarJogos(int n, int r) throws ClassNotFoundException, SQLException {
	    List<int[]> combinations = new ArrayList<>();
	    sorteiaTimes(combinations, new int[r], 1, n, 0);
	    return combinations;
	}
	
	public List<Jogos> getJogos() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		List<Jogos> jogos = new ArrayList<Jogos>();
		String sql_query = ("SELECT t.nomeTime AS timeA, (SELECT nomeTime FROM Times WHERE j1.codigoTimeB = codigoTime) AS timeB, j1.data "
				+ "FROM Times t INNER JOIN Jogos j1 ON j1.codigoTimeA = t.codigoTime");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Jogos j = new Jogos();
			j.setTimeA(rs.getString("timeA"));
			j.setTimeB(rs.getString("timeB"));
			j.setData(rs.getDate("data").toLocalDate());
			jogos.add(j);
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
*/
	@Override
	public List<Jogos> getRodadas(Jogos j) throws SQLException, ClassNotFoundException {
Connection c = gDao.getConnection();
		
		List<Jogos> jogos = new ArrayList<Jogos>();
		String sql_query = ("SELECT t.nomeTime AS timeA, (SELECT nomeTime FROM Times WHERE j1.codigoTimeB = codigoTime) AS timeB, j1.data "
				+ "FROM Times t INNER JOIN Jogos j1 ON j1.codigoTimeA = t.codigoTime WHERE j1.data = ?");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ps.setDate(1, Date.valueOf(j.getData()));
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Jogos jogo = new Jogos();
			jogo.setTimeA(rs.getString("timeA"));
			jogo.setTimeB(rs.getString("timeB"));
			jogo.setData(rs.getDate("data").toLocalDate());
			jogos.add(jogo);
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
	public List<Jogos> getDatas() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();		
		List<Jogos> jogos = new ArrayList<Jogos>();
		String sql_query = ("SELECT DISTINCT data FROM Jogos");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Jogos j = new Jogos();
			j.setData(rs.getDate("data").toLocalDate());
			jogos.add(j);
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
}
