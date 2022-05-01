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
	          <form action="Partidas" method="post">
				<h3><strong>Simular Partidas</strong></h3>
				<table>
					<tr>
						<td>
						<label for="dt">Datas de Jogos: </label>
							<select name="dt" id="dt">
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
							<input type="submit" id="btn" name="btn" value="Buscar Rodada">
						</td>
						<td>
							<input type="submit" id="btn" name="btn" value="Simular Partidas">
						</td>
					</tr>
				</table>
			</form>
	        </div>
	      </div>
	      <div class="table-wrapper">
			<c:if test="${not empty partidas }">
				<h2>Rodada <c:out value="${p.data }"/>:</h2>
				<form action="Partidas" method="post">
					<table class="fl-table">
						<thead>
							<tr>
								<th>Jogos</th>
								<th>Data do Jogo</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${partidas }">
							<tr>
								<input type="hidden" id="codA" name="codA" value="<c:out value="${p.codTimeA }"/>"/>
								<input type="hidden" id="codB" name="codB" value="<c:out value="${p.codTimeB }"/>"/>
								<input type="hidden" id="datas_partida" name="datas_partida" value="<c:out value="${p.data }"/>"/>
								<td><c:out value="${p.nomeTimeA }"/> <input type="number" max="10" name="golsA" id="golsA" value="<c:out value="${p.gols_timeA }"/>"/> VS <input type="number" max="10" name="golsB" id="golsB" value="<c:out value="${p.gols_timeB }"/>"/> <c:out value="${p.nomeTimeB }"/></td>
								<td><c:out value="${p.data }"/></td>
							</tr>
							</c:forEach>
						</tbody>
						<input type="submit" id="btn" name="btn" value="Atualizar Rodada">
					</table>
				</form>
			</c:if>
			<c:if test="${not empty jogos }">
				<h2>Rodada <c:out value="${j.data }"/>:</h2>
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
							<td><c:out value="${j.nomeTimeA }"/> <c:out value="${j.gols_timeA }"/> VS <c:out value="${j.gols_timeB }"/> <c:out value="${j.nomeTimeB }"/></td>
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