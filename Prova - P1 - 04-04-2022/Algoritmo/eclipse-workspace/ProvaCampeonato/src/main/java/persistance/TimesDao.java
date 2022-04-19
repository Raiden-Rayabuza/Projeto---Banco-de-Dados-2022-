package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistance.GenericDao;
import model.Jogos;
import model.Times;

public class TimesDao implements ITimesDao{
	private GenericDao gDao;
	
	public TimesDao(GenericDao gDao){
		this.gDao = gDao;
	}
	
	public List<Times> getTimes() throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		List<Times> times = new ArrayList<Times>();
		String sql_query = ("SELECT * FROM Times");
		PreparedStatement ps = c.prepareStatement(sql_query.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Times ti = new Times();
			ti.setCodigo(rs.getString("codigoTime"));
			ti.setNome(rs.getString("nomeTime"));
			ti.setCidade(rs.getString("cidade"));
			ti.setEstadio(rs.getString("estadio"));	
			times.add(ti);
		}
		rs.close();
		ps.close();
		c.close();
		return times;
	}
	public List<Object> atualizaNomesTabelas(List<Jogos> jogos, List<Times> times) throws SQLException, ClassNotFoundException{
		return null;
		
	}

}
