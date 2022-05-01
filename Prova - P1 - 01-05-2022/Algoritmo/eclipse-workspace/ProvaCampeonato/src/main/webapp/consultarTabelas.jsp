<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="StyleSheets/index.css"
	media="screen">
<link rel="stylesheet" type="text/css" href="StyleSheets/table.css"
	media="screen">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="StyleSheets/normalize.css"
	media="screen">
<title>Copa Brazucas 2022</title>
</head>
<body>
	<header id="navbar">
		<a href="index.jsp"><img src="Imgs/seta_voltar.png"
			alt="Logo Paulistão"></a>
		<p>Voltar</p>
	</header>
	<section id="opt-section">
		<div class="caixa-options">
			<div class="opt-container">
				<form action="Tabelas" method="post">
					<h3>
						<strong>Selecione os grupos</strong>
					</h3>
					<label for="cbb_grupo">Grupos: </label> <select name="cbb_tabelas"
						id="cbb_tabelas">
						<option value="" selected>Todos os Grupos</option>
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
					</select>
					<div class="opt-container-button">
						<input type="submit" id="btn" name="btn" value="Consultar Tabela">
						<input type="submit" id="btn" name="btn"
							value="Consultar Quartas de Final">
					</div>
				</form>
			</div>
		</div>
		<div class="table-wrapper">
			<h2>Tabela:</h2>
			<c:if test="${not empty tabelas }">
				<c:if test="${not empty btn }">
					<label for="legenda1">Classificados/Melhor Time</label>
					<input type="color" disabled value="#008000" name="legenda1" />
					<label for="legenda2">Eliminados</label>
					<input type="color" disabled value="#FFFFFF" name="legenda2" />
					<label for="legenda3">Rebaixados</label>
					<input type="color" disabled value="#FF0000" name="legenda3" />
					<table class="fl-table">
						<thead>
							<tr>
								<th>Posição</th>
								<th>Time</th>
								<th>Jogos Disputados</th>
								<th>Vitorias</th>
								<th>Derrotas</th>
								<th>Empates</th>
								<th>Gols Marcados</th>
								<th>Gols Sofridos</th>
								<th>Saldo de Gols</th>
								<th>Pontos</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="t" items="${tabelas }">
								<tr style="background-color:<c:out value="${t.cor }"/>">
									<td><c:out value="${t.posicao }" /></td>
									<td><c:out value="${t.nome_time }" /></td>
									<td><c:out value="${t.num_jogos_disputados }" /></td>
									<td><c:out value="${t.vitorias }" /></td>
									<td><c:out value="${t.derrotas }" /></td>
									<td><c:out value="${t.empates }" /></td>
									<td><c:out value="${t.gols_marcados }" /></td>
									<td><c:out value="${t.gols_sofridos }" /></td>
									<td><c:out value="${t.saldo_gols }" /></td>
									<td><c:out value="${t.pontos }" /></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${empty btn }">
					<table class="fl-table">
						<thead>
							<tr>
								<th>Jogos das Quartas de Final</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="t" items="${tabelas }">
								<tr>
									<td><c:out value="${t.nomeTimeA }" /> VS <c:out
											value="${t.nomeTimeB }" /></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:if>
		</div>
	</section>
	<br />
	<br />
	<div align="center">
		<h2>
			<c:out value="${erro}" />
		</h2>
	</div>
	<div align="center">
		<h3>
			<c:out value="${saida}" />
		</h3>
	</div>
	<footer> </footer>
</body>
</html>