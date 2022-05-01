<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="StyleSheets/index.css" media="screen">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="StyleSheets/normalize.css" media="screen">
<title>Copa Brazucas 2022</title>
</head>
<body>
<header id="navbar">
<a href="index.jsp"><img id="logo_centro" src="Imgs/logo_paulistao2021.png" alt="Logo Paulistão"></a>
</header>
<main>
	<!-- skills inicio -->
    <section id="skills-section">
      <div class="container">
        <div class="skills-container">
          <img src="Imgs/Grupos_icone.png" alt="Tabela de Grupo">
          <h3>Grupos</h3>
          <p>Definir os grupos dos times do campeonato</p>
          <a href="formacaoGrupos.jsp"><button>Gerenciar Grupos</button></a>
        </div>
        <div class="skills-container">
          <img src="Imgs/Jogos_icone.jpg" alt="Jogador fazendo gol">
          <h3>Jogos</h3>
           <p>Definir os jogos do campeonato</p>
           <a href="formacaoJogos.jsp"><button>Gerenciar Jogos</button></a>
        </div>
        <div class="skills-container">
          <img src="Imgs/Partidas_icone.png" alt="Jogador fazendo gol">
          <h3>Simular Partidas</h3>
           <p>Definir os resultados de cada partida</p>
           <a href="simulacaoJogos.jsp"><button>Simular Partidas</button></a>
        </div>
        <div class="skills-container">
          <img src="Imgs/Tabela_icone.jpg" alt="Jogador fazendo gol">
          <h3>Consultar Tabela</h3>
           <p>Checar o estado atual do campeonato</p>
           <a href="consultarTabelas.jsp"><button>Consultar Tabela</button></a>
        </div>
      </div>
    </section>
    <!-- skills fim -->
</main>
<footer>
</footer>
</body>
</html>