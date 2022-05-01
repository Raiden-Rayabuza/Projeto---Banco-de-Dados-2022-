<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="model.Jogos"%>
<%@ page import="persistance.GenericDao"%>
<%@ page import="persistance.JogosDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.LocalDate"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="StyleSheets/index.css" media="screen">
<link rel="stylesheet" type="text/css" href="StyleSheets/table.css" media="screen">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="StyleSheets/normalize.css" media="screen">
	</head>
	<body>
	<header id="navbar">
		<a href="index.jsp"><img src="Imgs/seta_voltar.png" alt="Logo Paulistão"></a>
		<p>Voltar</p>
	</header>
		<section id="opt-section">
	      <div class="caixa-options">
	        <div class="opt-container">
	          <form action="Jogos" method="post">
				<h3><strong>Tabela de Jogos (Fase de Grupos)</strong></h3>
				<table>
					<tr>
						<td>
						<label for="dt">Datas de Jogos: </label>
							<select name="dt" id="dt">
								<option value="">Todas as rodadas</option>
								<%
									List<Jogos> j = new ArrayList<Jogos>();
									GenericDao gDao = new GenericDao();
									JogosDao jogoDao = new JogosDao(gDao);
									j.addAll(jogoDao.getDatas());
									for(Jogos data : j){
								%>
								<option value=<%=data.getData() %>><%=data.getData() %></option>
								<%
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" id="btn" name="btn" value="Consultar Campeonato">
						</td>
						<td>
							<input type="submit" id="btn" name="btn" value="Gerar Jogos">
						</td>
					</tr>
				</table>
			</form>
	        </div>
	      </div>
	      <div class="table-wrapper">
			<h2>Jogos:</h2>
			<c:if test="${not empty jogos }">
				<table class="fl-table">
					<thead>
						<tr>
							<th>Jogos</th>
							<th>Data do Jogo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="j" items="${jogos }">
						<tr>
							<td><c:out value="${j.timeA }"/> VS <c:out value="${j.timeB }"/></td>
							<td><c:out value="${j.data }"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	    </section>
	<br />
	<br />
		<div align="center">
			<h2><c:out value="${erro}"/></h2>
		</div>
		<div align="center">
			<h3><c:out value="${saida}"/></h3>
		</div>
	<footer>
	</footer>
	</body>
</html>