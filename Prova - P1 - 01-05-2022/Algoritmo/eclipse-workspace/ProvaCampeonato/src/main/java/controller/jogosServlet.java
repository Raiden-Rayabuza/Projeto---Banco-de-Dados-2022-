package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Jogos;
import persistance.GenericDao;
import persistance.JogosDao;

@WebServlet("/Jogos")
public class jogosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public jogosServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Jogos j = new Jogos();
		GenericDao gDao = new GenericDao();
		JogosDao jogoDao = new JogosDao(gDao);
		String dt = request.getParameter("dt");
		String btn = request.getParameter("btn");
		
		String saida = "";
		String erro = "";
		
		List<Jogos>jogos_fase_grupo = new ArrayList<Jogos>();
		
		j = validaJogos(dt,btn);
		
		try {
			if(btn.equals("Gerar Jogos")) {
				jogoDao.sorteiaJogos();
				jogos_fase_grupo = jogoDao.getJogos();
			}
			else if(btn.equals("Consultar Campeonato")){
				if(j != null) {
					jogos_fase_grupo = jogoDao.getRodadas(j);
				}
				else {
					jogos_fase_grupo = jogoDao.getJogos();
				}
			}
		}catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("formacaoJogos.jsp");
			request.setAttribute("jogos", jogos_fase_grupo);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}
	
	private Jogos validaJogos(String data, String botao) {
		Jogos j = new Jogos();
		if(botao.equals("Consultar Campeonato") || botao.equals("Gerar Jogos")) {
			if(data == "") {
				j = null;
			}
			else {
				j.setData(LocalDate.parse(data));
			}
		}
		return j;
	}
}
