package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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

@WebServlet("/Partidas")
public class partidasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public partidasServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Jogos p = new Jogos();
		GenericDao gDao = new GenericDao();
		JogosDao jogoDao = new JogosDao(gDao);
		String dt = request.getParameter("dt");
		String btn = request.getParameter("btn");
		String golsA[] = request.getParameterValues("golsA");
		String golsB[] = request.getParameterValues("golsB");
		String codA[] = request.getParameterValues("codA");
		String codB[] = request.getParameterValues("codB");
		String datas_partida[] = request.getParameterValues("datas_partida");
				
		String saida = "";
		String erro = "";
		
		List<Jogos>jogos = new ArrayList<Jogos>();
		List<Jogos>partidas = new ArrayList<Jogos>();
		List<Jogos>jogos_rodada = new ArrayList<Jogos>();
		
		try {
			if(btn.equals("Buscar Rodada")){
				p.setData(LocalDate.parse(dt));
				partidas = jogoDao.getRodadas(p);
			}
			else if(btn.equals("Simular Partidas")){
				jogoDao.gerarResultados();
				jogos = jogoDao.getJogos();
			}
			else if(btn.equals("Atualizar Rodada")){
				int tam_tmp = 0;
				p.setData(LocalDate.parse(datas_partida[0]));
				while(tam_tmp < golsA.length) {
					Jogos tmp = new Jogos();
					tmp.setGols_timeA(Integer.parseInt(golsA[tam_tmp]));
					tmp.setGols_timeB(Integer.parseInt(golsB[tam_tmp]));
					tmp.setCodTimeA(Integer.parseInt(codA[tam_tmp]));
					tmp.setCodTimeB(Integer.parseInt(codB[tam_tmp]));
					tmp.setData(LocalDate.parse(datas_partida[tam_tmp]));
					jogos_rodada.add(tmp);
					tam_tmp++;
				}
				jogoDao.gerarRodada_Resultados(jogos_rodada);
				partidas = jogoDao.getRodadas(p);
			}
		}catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("simulacaoJogos.jsp");
			request.setAttribute("jogos", jogos);
			request.setAttribute("partidas", partidas);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}
}
