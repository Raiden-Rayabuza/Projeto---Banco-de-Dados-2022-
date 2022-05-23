CREATE DATABASE Fatec
GO
USE Fatec

CREATE TABLE disciplina(
cod_disciplina			CHAR(8)			NOT NULL,
nome					VARCHAR(50)		NOT NULL,
sigla					CHAR(6)			NOT NULL,
turno					CHAR(5)			NOT NULL,
num_aulas				INT				NOT NULL,
PRIMARY KEY(cod_disciplina),
);

CREATE TABLE avaliacao(
cod_avaliacao	INT		IDENTITY(1,1)	NOT NULL,
tipo			VARCHAR(20)				NOT NULL,
PRIMARY KEY(cod_avaliacao),
);

CREATE TABLE aluno(
ra		CHAR(13)		NOT NULL,
nome	VARCHAR(50)		NOT NULL,
PRIMARY KEY(ra),
);

CREATE TABLE falta(
ra				CHAR(13)	NOT NULL,
cod_disciplina	CHAR(8)		NOT NULL,
data_falta		DATETIME	NOT NULL,
presenca		INT			NOT NULL,
PRIMARY KEY(ra,cod_disciplina,data_falta),
FOREIGN KEY(cod_disciplina) REFERENCES disciplina(cod_disciplina),
FOREIGN KEY(ra) REFERENCES aluno(ra),
);

CREATE TABLE notas(
ra				CHAR(13)	NOT NULL,
cod_disciplina	CHAR(8)		NOT NULL,
cod_avaliacao	INT			NOT NULL,
nota			INT			NOT NULL,
PRIMARY KEY(ra,cod_disciplina,cod_avaliacao),
FOREIGN KEY(cod_disciplina) REFERENCES disciplina(cod_disciplina),
FOREIGN KEY(ra) REFERENCES aluno(ra),
FOREIGN KEY(cod_avaliacao) REFERENCES avaliacao(cod_avaliacao),
);

SELECT al.ra, al.nome, n1.nota  FROM notas n1 INNER JOIN aluno al ON n1.ra = al.ra INNER JOIN disciplina dis ON n1.cod_disciplina = dis.cod_disciplina