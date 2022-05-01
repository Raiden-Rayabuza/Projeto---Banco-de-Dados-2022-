package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Jogos;
import model.Tabela;
import persistance.GenericDao;
import persistance.TabelasDao;

@WebServlet("/Tabelas")
public class tabelasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public tabelasServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GenericDao gDao = new GenericDao();
		TabelasDao tDao = new TabelasDao(gDao);
		
		String grupos = request.getParameter("cbb_tabelas");
		String btn = request.getParameter("btn");
		
		String saida = "";
		String erro = "";
		
		List<Tabela> tabelas = new ArrayList<Tabela>();
		List<Jogos> quartas = new ArrayList<Jogos>();
		
		try {
			if(btn.equals("Consultar Tabela")) {
				if(grupos != "") {
					tabelas = tDao.getTabela_Grupos(grupos);
					btn = "tabela";
				}
				else {
					tabelas = tDao.getTabela_Campeonato();
					btn = "tabela";
				}
			}
			else if(btn.equals("Consultar Quartas de Final")) {
				quartas = tDao.getQuartas_de_final();
				btn = null;	
			}
		}catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("consultarTabelas.jsp");
			if(btn == null) {
				request.setAttribute("tabelas", quartas);
			}
			else {
				request.setAttribute("tabelas", tabelas);
			}
			request.setAttribute("btn", btn);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}
}