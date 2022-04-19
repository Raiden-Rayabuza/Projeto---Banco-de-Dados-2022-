package persistance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Grupos;

public class GrupoDao implements IGrupoDao  {
	private GenericDao gDao;
	
	public GrupoDao(GenericDao gDao){
		this.gDao = gDao;
	}

	public void sorteiaGrupos() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql_query = "{CALL sp_gerar_grupos}";
		CallableStatement cs = c.prepareCall(sql_query);
		cs.execute();
		cs.close();
		c.close();
	}

	@Override
	public List<Grupos> getGrupos() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		List<Grupos> grupos = new ArrayList<Grupos>();
		String sql_query = ("SELECT g.grupo, t.nomeTime, t.codigoTime FROM Grupos g INNER JOIN Times t ON g.codigoTime = t.codigoTime");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Grupos gru = new Grupos();
			
			gru.setTimeCod(rs.getInt("codigoTime"));
			gru.setTimeNome(rs.getString("nomeTime"));
			gru.setGrupo(rs.getString("grupo"));
			
			grupos.add(gru);
		}
		rs.close();
		ps.close();
		c.close();
		return grupos;
	}

	@Override
	public List<Grupos> getGrupo(Grupos g) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		List<Grupos> grupos = new ArrayList<Grupos>();
		String sql_query = ("SELECT g.grupo, t.nomeTime FROM Grupos g INNER JOIN Times t ON g.codigoTime = t.codigoTime WHERE g.grupo = ?");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ps.setString(1, g.getGrupo());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Grupos gru = new Grupos();
			gru.setGrupo(rs.getString("grupo"));
			gru.setTimeNome(rs.getString("nomeTime"));
			
			grupos.add(gru);
		}
		rs.close();
		ps.close();
		c.close();
		return grupos;
	}

}
