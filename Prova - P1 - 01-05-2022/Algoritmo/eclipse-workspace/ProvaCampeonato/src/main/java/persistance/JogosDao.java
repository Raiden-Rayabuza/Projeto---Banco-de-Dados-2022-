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
import java.util.List;
import java.util.Random;
import java.util.HashMap;

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
		HashMap<Integer,List<int[]>> jogos = gerarJogos();
		List<LocalDate> datas_jogos = gerarDatas();
		int cont1 = 1, cont2 = 1, datas = 0, tam_max = jogos.size();
		atualizaJogos();
		Random ale = new Random();
		String sql_query_insert = "INSERT INTO Jogos (codigoTimeA, codigoTimeB, golsTimeA, golsTimeB, data_jogo) VALUES(?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql_query_insert);
		String sql_query_consult = "SELECT * FROM Jogos WHERE (codigoTimeA = ? OR codigoTimeB = ?) AND data_jogo = ?";
		PreparedStatement ps2 = c.prepareStatement(sql_query_consult);
		String sql_query_consult2 = "SELECT * FROM Jogos WHERE (codigoTimeA = ? OR codigoTimeB = ?) AND data_jogo = ?";
		PreparedStatement ps3 = c.prepareStatement(sql_query_consult2);
		while(cont1 <= tam_max) {
			int tam_max2 = jogos.get(cont1).size();
			while(cont2 <= tam_max2) {
				int times[] = jogos.get(cont1).get(ale.nextInt(jogos.get(cont1).size())), ordem = ale.nextInt(2);
				if(datas > 11) {
					datas = 0;
				}
				ps2.setInt(1, times[0]);
				ps2.setInt(2, times[1]);
				ps2.setDate(3, Date.valueOf(datas_jogos.get(datas)));
				ResultSet rs1 = ps2.executeQuery();

				if(!(rs1.next())) {
					if(ordem == 1) {
						ps.setInt(1, times[1]);
						ps.setInt(2, times[0]);
					}
					else {
						ps.setInt(1, times[0]);
						ps.setInt(2, times[1]);
					}
					ps.setInt(3, 0);
					ps.setInt(4, 0);
					ps.setDate(5, Date.valueOf(datas_jogos.get(datas)));
					ps.execute();
					jogos.get(cont1).remove(times);
					datas++;
					cont2++;
				}else {
					datas++;
				}
			}
			if(datas > 11) {
				datas = 0;
			}
			cont2 = 1;
			cont1++;
		}
	}
	private List<LocalDate> gerarDatas() throws SQLException, ClassNotFoundException{
		LocalDate data_atual = LocalDate.now();
		LocalDate data_seguinte = LocalDate.now();
		DayOfWeek semana = data_atual.getDayOfWeek();
		List<LocalDate> datas_jogos = new ArrayList<LocalDate>();
		if(semana.getValue() > 3 && semana.getValue() < 7) {
			data_seguinte = data_atual.plusDays((7 - semana.getValue()));
		}
		else if(semana.getValue() < 3) {
			data_seguinte = data_atual.plusDays((3 - semana.getValue()));
		}
		while(datas_jogos.size() < 16) {
			if(semana.getValue() == 3) {
				datas_jogos.add(data_seguinte);
				data_seguinte = data_atual.plusDays(4);
			}
			else if(semana.getValue() == 7) {
				datas_jogos.add(data_seguinte);
				data_seguinte = data_atual.plusDays(3);
			}
			data_atual = data_seguinte;
			semana = data_atual.getDayOfWeek();
		}
		return datas_jogos;	
	}
	private HashMap<Integer,List<int[]>> gerarJogos() throws SQLException, ClassNotFoundException{
		Connection c = gDao.getConnection();
		HashMap<Integer,List<int[]>> jogos = new HashMap<Integer,List<int[]>>();
		List<Grupos> grupos = new ArrayList<Grupos>();
		String sql_query = "SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime ORDER BY Grupos.codigoTime;";
		PreparedStatement ps = c.prepareStatement(sql_query);
		ResultSet rs = ps.executeQuery();
		List<int[]> jogos_marcados = new ArrayList<int[]>();
		while(rs.next()) {
			Grupos g = new Grupos();
			g.setGrupo(rs.getString("grupo"));
			g.setTimeCod(rs.getInt("codigoTime"));
			g.setTimeNome(rs.getString("nomeTime"));
			grupos.add(g);
		}
		int cont1 = 1, cont2 = 1;
		while(cont1 <= 15) {
			List<int[]> temp = new ArrayList<int[]>();
			while(cont2 <= 16) {
				String grupoA = grupos.get(cont1 - 1).getGrupo();
				String grupoB = grupos.get(cont2 - 1).getGrupo();
				if(grupoA.equals(grupoB)) {
					cont2++;
				}
				else {
					int times[] = new int [2];

					times[0] = cont1;
					times[1] = cont2;

					temp.add(times);
					jogos_marcados.add(times);
					cont2++;
				}
			}
			jogos.put(cont1, temp);
			cont1++;
			cont2 = cont1 + 1;
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
	/*private void sorteiaTimes(List<int[]> combinations, int data[], int start, int end, int index) throws SQLException, ClassNotFoundException{
		 if (index == data.length) {1
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
	}*/

	public List<Jogos> getJogos() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();

		List<Jogos> jogos = new ArrayList<Jogos>();
		String sql_query = ("SELECT j1.codigoTimeA, j1.golsTimeA, j1.golsTimeB, j1.codigoTimeB, t.nomeTime AS timeA, (SELECT nomeTime FROM Times WHERE j1.codigoTimeB = codigoTime) AS timeB, j1.data_jogo "
				+ "FROM Times t INNER JOIN Jogos j1 ON j1.codigoTimeA = t.codigoTime ORDER BY j1.data_jogo");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Jogos j = new Jogos();
			j.setNomeTimeA(rs.getString("timeA"));
			j.setNomeTimeB(rs.getString("timeB"));
			j.setCodTimeA(rs.getInt("codigotimeA"));
			j.setCodTimeB(rs.getInt("codigotimeB"));
			j.setGols_timeA(rs.getInt("golsTimeA"));
			j.setGols_timeB(rs.getInt("golsTimeB"));
			j.setData(rs.getDate("data_jogo").toLocalDate());
			jogos.add(j);
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
	
	public List<Jogos> getRodadas(Jogos j) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();		
		List<Jogos> jogos = new ArrayList<Jogos>();
		String sql_query = ("SELECT j1.codigoTimeA, j1.golsTimeA, j1.golsTimeB, j1.codigoTimeB, t.nomeTime AS timeA, (SELECT nomeTime FROM Times WHERE j1.codigoTimeB = codigoTime) AS timeB, j1.data_jogo "
				+ "FROM Times t INNER JOIN Jogos j1 ON j1.codigoTimeA = t.codigoTime WHERE j1.data_jogo = ?");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ps.setDate(1, Date.valueOf(j.getData()));
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Jogos jogo = new Jogos();
			jogo.setNomeTimeA(rs.getString("timeA"));
			jogo.setNomeTimeB(rs.getString("timeB"));
			jogo.setCodTimeA(rs.getInt("codigotimeA"));
			jogo.setCodTimeB(rs.getInt("codigotimeB"));
			jogo.setGols_timeA(rs.getInt("golsTimeA"));
			jogo.setGols_timeB(rs.getInt("golsTimeB"));
			jogo.setData(rs.getDate("data_jogo").toLocalDate());
			jogos.add(jogo);
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
	
	public void gerarResultados() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		List<Jogos> jogos = getJogos();
		int cont1 = 0;
		Random ale = new Random();
		String sql_query = ("UPDATE Jogos SET golsTimeA = ?, golsTimeB = ? WHERE codigoTimeA = ? AND codigoTimeB =  ?");
		PreparedStatement ps = c.prepareStatement(sql_query);
		while(cont1 < 96) {
			ps.setInt(1, ale.nextInt(11));
			ps.setInt(2, ale.nextInt(11));
			ps.setInt(3, jogos.get(cont1).getCodTimeA());
			ps.setInt(4, jogos.get(cont1).getCodTimeB());
			ps.execute();
			cont1++;
		}
		ps.close();
		c.close();
	}
	
	public void gerarRodada_Resultados(List<Jogos> j) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		int cont1 = 0;
		String sql_query = ("UPDATE Jogos SET golsTimeA = ?, golsTimeB = ? WHERE (codigoTimeA = ? AND codigoTimeB =  ?) AND data_jogo = ?");
		PreparedStatement ps = c.prepareStatement(sql_query);
		while(cont1 < j.size()) {
			ps.setInt(1, j.get(cont1).getGols_timeA());
			ps.setInt(2, j.get(cont1).getGols_timeB());
			ps.setInt(3, j.get(cont1).getCodTimeA());
			ps.setInt(4, j.get(cont1).getCodTimeB());
			ps.setDate(5, Date.valueOf(j.get(cont1).getData()));
			ps.execute();
			cont1++;
		}
		ps.close();
		c.close();
	}

	public List<Jogos> getDatas() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();		
		List<Jogos> jogos = new ArrayList<Jogos>();
		String sql_query = ("SELECT DISTINCT data_jogo FROM Jogos");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Jogos j = new Jogos();
			j.setData(rs.getDate("data_jogo").toLocalDate());
			jogos.add(j);
		}
		rs.close();
		ps.close();
		c.close();
		return jogos;
	}
}
