<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="StyleSheets/index.css" media="screen">
<link rel="stylesheet" type="text/css" href="StyleSheets/table.css" media="screen">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="StyleSheets/normalize.css" media="screen">
<title>Copa Brazucas 2022</title>
</head>
	<body>
	<header id="navbar">
		<a href="index.jsp"><img src="Imgs/seta_voltar.png" alt="Logo Paulistão"></a>
		<p>Voltar</p>
	</header>
		<section id="opt-section">
	      <div class="caixa-options">
	        <div class="opt-container">
	          <form action="grupos" method="post">
				<h3><strong>Selecione os grupos</strong></h3>
				<label for="cbb_grupo">Grupos: </label>
				<select name="cbb_grupo" id="cbb_grupo">
					<option value="" selected>Todos os Grupos</option>
					<option value="A">A</option>
					<option value="B">B</option>
					<option value="C">C</option>
					<option value="D">D</option>
				</select>
				<div class="opt-container-button">
					<input type="submit" id="btn" name="btn" value="Consultar">
					<input type="submit" id="btn" name="btn" value="Sortear">
				</div>
			</form>
	        </div>
	      </div>
	      <div class="table-wrapper">
			<h2>Grupos:</h2>
			<c:if test="${not empty grupos }">
				<table class="fl-table">
					<thead>
						<tr>
							<th>Grupo</th>
							<th>Nome do Time</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="g" items="${grupos }">
						<tr>
							<td><c:out value="${g.grupo }"/></td>
							<td><c:out value="${g.timeNome }"/></td>
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