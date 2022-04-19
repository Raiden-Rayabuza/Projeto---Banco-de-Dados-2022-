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

import model.Grupos;
import persistance.GenericDao;
import persistance.GrupoDao;

@WebServlet("/grupos")
public class grupoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public grupoServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Grupos g = new Grupos();
		GenericDao genDao = new GenericDao();
		GrupoDao gruDao = new GrupoDao(genDao);
		
		String btn = request.getParameter("btn");
		String grupos = request.getParameter("cbb_grupo");
		
		String saida = "";
		String erro = "";
		List<Grupos> grupos_disponiveis = new ArrayList<Grupos>();
		
		g = validaGrupo(grupos, btn);
		
		try {
			if(btn.equals("Sortear")) {
				gruDao.sorteiaGrupos();
				grupos_disponiveis = gruDao.getGrupos();
			}
			else if(btn.equals("Consultar")){
				if(g != null) {
					grupos_disponiveis = gruDao.getGrupo(g);
				}
				else {
					grupos_disponiveis = gruDao.getGrupos();
				}
			}
		}catch(SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("formacaoGrupos.jsp");
			System.out.println(grupos_disponiveis);
			request.setAttribute("grupos", grupos_disponiveis);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}
	
	private Grupos validaGrupo(String grupo, String botao) {
		Grupos g = new Grupos();
		if(botao.equals("Consultar") || botao.equals("Sortear")) {
			if(grupo.equals("")) {
				g = null;
			}
			else {
				g.setGrupo(grupo);
			}
		}
		return g;
	}
}
