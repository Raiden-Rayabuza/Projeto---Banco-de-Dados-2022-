package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Jogos;
import model.Tabela;

public class TabelasDao implements ITabelasDao {

	private GenericDao gDao;

	public TabelasDao(GenericDao gDao){
		this.gDao = gDao;
	}
	public List<Tabela> getTabela_Grupos(String grupo) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		int cont = 1;  
		String sql_query = "SELECT * FROM dbo.fn_tabela_grupo(?) ORDER BY pontos DESC, vitorias DESC, gols_marcados DESC, saldo_gols DESC";
		PreparedStatement ps = c.prepareStatement(sql_query);
		ps.setString(1, grupo);
		ResultSet rs = ps.executeQuery();
		List<Tabela> tabela_grupo = new ArrayList<Tabela>();
		List<Tabela> rebaixados = getTabela_Campeonato();
		while(rs.next()) {
			Tabela t = new Tabela();

			t.setNome_time(rs.getString("nome_time"));
			t.setPosicao(cont);
			t.setNum_jogos_disputados(rs.getInt("num_jogos_disputados"));
			t.setVitorias(rs.getInt("vitorias"));
			t.setEmpates(rs.getInt("empates"));
			t.setDerrotas(rs.getInt("derrotas"));
			t.setGols_marcados(rs.getInt("gols_marcados"));
			t.setGols_sofridos(rs.getInt("gols_sofridos"));
			t.setSaldo_gols(rs.getInt("saldo_gols"));
			t.setPontos(rs.getInt("pontos"));
			if(cont == 1 || cont == 2) {
				t.setCor("green");
			}
			else if(rebaixados.get(14).getNome_time().equals(rs.getString("nome_time")) || rebaixados.get(15).getNome_time().equals(rs.getString("nome_time"))) {
				t.setCor("red");
			}
			else {
				t.setCor("white");
			}
			cont++;
			tabela_grupo.add(t);
		}
		rs.close();
		ps.close();
		c.close();
		return tabela_grupo;
	}
	
	public List<Tabela> getTabela_Campeonato() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		int cont = 1;  
		String sql_query = "SELECT * FROM dbo.fn_tabela_campeonato() ORDER BY pontos DESC, vitorias DESC, gols_marcados DESC, saldo_gols DESC";
		PreparedStatement ps = c.prepareStatement(sql_query);
		ResultSet rs = ps.executeQuery();
		List<Tabela> tabela_grupo = new ArrayList<Tabela>();
		while(rs.next()) {
			Tabela t = new Tabela();

			t.setNome_time(rs.getString("nome_time"));
			t.setPosicao(cont);
			t.setNum_jogos_disputados(rs.getInt("num_jogos_disputados"));
			t.setVitorias(rs.getInt("vitorias"));
			t.setEmpates(rs.getInt("empates"));
			t.setDerrotas(rs.getInt("derrotas"));
			t.setGols_marcados(rs.getInt("gols_marcados"));
			t.setGols_sofridos(rs.getInt("gols_sofridos"));
			t.setSaldo_gols(rs.getInt("saldo_gols"));
			t.setPontos(rs.getInt("pontos"));
			if(cont == 1) {
				t.setCor("green");
			}
			else if(cont == 15 || cont == 16) {
				t.setCor("red");
			}
			else {
				t.setCor("white");
			}
			cont++;
			tabela_grupo.add(t);
		}
		rs.close();
		ps.close();
		c.close();
		return tabela_grupo;
	}
	public List<Jogos> getQuartas_de_final() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		int cont = 1, cont2 = 0;
		String sql_query = "SELECT * FROM dbo.fn_quartas_de_final()";
		PreparedStatement ps = c.prepareStatement(sql_query);
		ResultSet rs = ps.executeQuery();
		List<Jogos> tabela_grupo = new ArrayList<Jogos>();
		String times[] = new String[2];
		while(rs.next()) {
			Jogos t = new Jogos();
			times[cont2] = rs.getString("nome_time");
			cont2++;
			if(cont % 2 == 0) {
				t.setNomeTimeA(times[0]);
				t.setNomeTimeB(times[1]);
				tabela_grupo.add(t);
				cont2 = 0;
			}
			cont++;
		}
		rs.close();
		ps.close();
		c.close();
		return tabela_grupo;
	}
}