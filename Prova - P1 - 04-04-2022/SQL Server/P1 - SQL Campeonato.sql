CREATE DATABASE CAMPEONATO
GO
USE CAMPEONATO

CREATE TABLE Times(
	codigoTime		INT			NOT NULL  IDENTITY(1,1),
	nomeTime		VARCHAR(50) NOT NULL,
	cidade			VARCHAR(50) NOT NULL,
	estadio			VARCHAR(50) NOT NULL,
	PRIMARY KEY (codigoTime)
);

CREATE TABLE Grupos(
	grupo		CHAR(1)		NOT NULL CHECK(grupo = UPPER('A') OR grupo = UPPER('B') OR grupo = UPPER('C') OR grupo = UPPER('D')),
	codigoTime	INT			NOT NULL,
	FOREIGN KEY (codigoTime) REFERENCES Times(codigoTime)
);

CREATE TABLE Jogos(
	codigoTimeA		INT		NOT NULL,
	codigoTimeB		INT		NOT NULL,
	golsTimeA		INT,
	golsTimeB		INT,
	data			DATE	NOT NULL CHECK(DATEPART(WEEKDAY,data) = 1 OR DATEPART(WEEKDAY,data) = 4),
	PRIMARY KEY (codigoTimeA, codigoTimeB),
	FOREIGN KEY (codigoTimeA) REFERENCES Times(codigoTime),
	FOREIGN KEY (codigoTimeB) REFERENCES Times(codigoTime)
);
SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime WHERE grupo = 'A';

CREATE PROCEDURE sp_gerar_grupos
AS
DECLARE @time  INT,
		@cont1 INT,
		@cont2 INT,
		@sql_query INT
DECLARE @grupo CHAR(4)
SET @time = 1
SET @cont1 = 1
SET @cont2 = 1
SET @grupo = 'ABCD'
SET @sql_query = (SELECT COUNT(*) FROM Grupos)
IF((@sql_query > 0 AND @sql_query < 16) OR (@sql_query = 16))
	BEGIN
		PRINT('Grupos Atualizados')
		DELETE FROM Grupos
	END
WHILE @cont1 <= 4
	BEGIN 
		SET @cont2 = 1
		WHILE @cont2 <= 4 
			BEGIN
				IF(@cont2 = 1)
					BEGIN 
						SET @time = (SELECT TOP 1 Times.codigoTime FROM Times 
						WHERE (cidade = 'São Paulo' OR cidade = 'Santos') AND codigoTime NOT IN (SELECT Grupos.codigoTime FROM Grupos) ORDER BY NEWID())
					END
				ELSE
					BEGIN
						SET @time = (SELECT TOP 1 Times.codigoTime FROM Times 
						WHERE (cidade != 'São Paulo' AND cidade != 'Santos') AND codigoTime NOT IN (SELECT Grupos.codigoTime FROM Grupos) ORDER BY NEWID())
					END
				INSERT INTO Grupos (codigoTime,grupo) VALUES (@time, SUBSTRING(@grupo,@cont1,1))
				SET @cont2 += 1
			END
		SET @cont1 += 1
	END
	SELECT g.codigoTime FROM Grupos g WHERE g.codigoTime NOT IN (SELECT j.codigoTimeA FROM Jogos j)
	EXEC sp_gerar_grupos

CREATE PROCEDURE sp_checar_jogos
AS
DECLARE @sql_query INT;
SET @sql_query = (SELECT COUNT(*) FROM Jogos)
IF((@sql_query > 0 AND @sql_query < 96) OR (@sql_query = 96))
	BEGIN
		PRINT('Jogos Atualizados')
		DELETE FROM Jogos
	END