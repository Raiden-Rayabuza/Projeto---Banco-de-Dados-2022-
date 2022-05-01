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
	data_jogo			DATE	NOT NULL CHECK(DATEPART(WEEKDAY,data_jogo) = 1 OR DATEPART(WEEKDAY,data_jogo) = 4),
	PRIMARY KEY (codigoTimeA, codigoTimeB),
	FOREIGN KEY (codigoTimeA) REFERENCES Times(codigoTime),
	FOREIGN KEY (codigoTimeB) REFERENCES Times(codigoTime)
);
SELECT Grupos.grupo, Grupos.codigoTime, Times.nomeTime FROM Grupos INNER JOIN Times ON Grupos.codigoTime = Times.codigoTime ORDER BY Grupos.codigoTime;
SELECT * FROM Jogos ORDER BY data_jogo
SELECT * FROM Jogos WHERE (codigoTimeA = 13 OR codigoTimeB = 13) ORDER BY data
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

SELECT t.nomeTime AS timeA, (SELECT nomeTime FROM Times WHERE j1.codigoTimeB = codigoTime) AS timeB, j1.data_jogo FROM Times t INNER JOIN Jogos j1 ON j1.codigoTimeA = t.codigoTime WHERE j1.data_jogo =  ORDER BY j1.data_jogo
--Triggers para tabela Times
CREATE TRIGGER trigger_times_read_only ON Times
FOR INSERT, UPDATE, DELETE AS 
BEGIN 
  
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de INSERT não são permitidas na tabela "Times"', 15, 1); 
        RETURN;
    END 
  
  
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de DELETE não são permitidas na tabela "Times"', 15, 1); 
        RETURN;
    END 
  
  
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de UPDATE não são permitidas na tabela "Times"', 15, 1); 
        RETURN;
    END 
END; 
--Triggers para tabela Grupos
CREATE TRIGGER trigger_grupos_read_only ON Grupos
FOR INSERT, UPDATE, DELETE AS 
BEGIN 
  
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de INSERT não são permitidas na tabela "Grupos"', 15, 1); 
        RETURN;
    END 
  
  
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de DELETE não são permitidas na tabela "Grupos"', 15, 1); 
        RETURN;
    END 
  
  
    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de UPDATE não são permitidas na tabela "Grupos"', 15, 1); 
        RETURN;
    END 
END;
--Triggers para tabela Jogos
CREATE TRIGGER trigger_jogos_read_only ON Jogos
FOR INSERT, UPDATE, DELETE AS 
BEGIN 
  
    IF EXISTS (SELECT * FROM inserted) AND NOT EXISTS (SELECT * FROM deleted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de INSERT não são permitidas na tabela "Jogos"', 15, 1); 
        RETURN;
    END 
  
  
    IF EXISTS (SELECT * FROM deleted) AND NOT EXISTS (SELECT * FROM inserted) 
    BEGIN 
        ROLLBACK TRANSACTION; 
        RAISERROR ('Operações de DELETE não são permitidas na tabela "Jogos"', 15, 1); 
        RETURN;
    END 
END;

--UDF para grupos
CREATE FUNCTION fn_tabela_grupo(@grupo CHAR(1))
RETURNS @tabela TABLE(
nome_time				VARCHAR(50),
num_jogos_disputados	INT, 
vitorias				INT, 
empates					INT, 
derrotas				INT, 
gols_marcados			INT,
gols_sofridos			INT, 
saldo_gols				INT,
pontos					INT
)
AS
BEGIN
DECLARE @nome_time		VARCHAR(50)
DECLARE @codigo			INT,
		@num_jogos		INT,
		@gols_timeA		INT,
		@gols_timeB		INT, 
		@vitorias		INT, 
		@empates		INT, 
		@derrotas		INT, 
		@gols_marcados	INT,
		@gols_sofridos	INT, 
		@saldo_gols		INT,
		@pontos			INT,
		@cont			INT
SET @cont = 0
WHILE @cont < 4
	BEGIN 
		SET @codigo = (SELECT TOP 1 t.codigoTime FROM Times t INNER JOIN Grupos g ON t.codigoTime = g.codigoTime WHERE g.grupo = @grupo AND t.nomeTime NOT IN (SELECT nome_time FROM @tabela))
		
		SET @nome_time = (SELECT nomeTime FROM Times WHERE codigoTime = @codigo)		

		SET @num_jogos = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo OR codigoTimeB = @codigo)
		
		SET @gols_timeA = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo AND golsTimeA > golsTimeB)
		SET @gols_timeB = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeB = @codigo AND golsTimeB > golsTimeA)
		SET @vitorias = @gols_timeA + @gols_timeB

		SET @gols_timeA = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo AND golsTimeA = golsTimeB)
		SET @gols_timeB = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeB = @codigo AND golsTimeB = golsTimeA)
		SET @empates = @gols_timeA + @gols_timeB
		 
		SET @gols_timeA = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo AND golsTimeA < golsTimeB)
		SET @gols_timeB = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeB = @codigo AND golsTimeB < golsTimeA)
		SET @derrotas = @gols_timeA + @gols_timeB

		SET @gols_timeA = (SELECT SUM(golsTimeA) FROM Jogos WHERE codigoTimeA = @codigo)
		SET @gols_timeB = (SELECT SUM(golsTimeB) FROM Jogos WHERE codigoTimeB = @codigo)
		SET @gols_marcados = @gols_timeA + @gols_timeB

		SET @gols_timeA = (SELECT SUM(golsTimeA) FROM Jogos WHERE codigoTimeB = @codigo)
		SET @gols_timeB = (SELECT SUM(golsTimeB) FROM Jogos WHERE codigoTimeA = @codigo)
		SET @gols_sofridos = @gols_timeA + @gols_timeB
		 
		SET @saldo_gols = @gols_marcados - @gols_sofridos
		
		SET @pontos = (@vitorias * 3) + (@empates)

		INSERT INTO @tabela(nome_time,num_jogos_disputados,vitorias,empates,derrotas,gols_marcados,gols_sofridos,saldo_gols,pontos) 
		VALUES(@nome_time,@num_jogos,@vitorias,@empates,@derrotas,@gols_marcados,@gols_sofridos,@saldo_gols,@pontos) 
		
		SET @cont = @cont + 1
	END
	
	RETURN 
END

SELECT * FROM dbo.fn_tabela_grupo('A') AS tabela_grupo ORDER BY pontos DESC, saldo_gols DESC

--UDF para campeonato
CREATE FUNCTION fn_tabela_campeonato()
RETURNS @tabela TABLE(
nome_time				VARCHAR(50),
num_jogos_disputados	INT, 
vitorias				INT, 
empates					INT, 
derrotas				INT, 
gols_marcados			INT,
gols_sofridos			INT, 
saldo_gols				INT,
pontos					INT
)
AS
BEGIN
DECLARE @nome_time		VARCHAR(50)
DECLARE @codigo			INT,
		@num_jogos		INT,
		@gols_timeA		INT,
		@gols_timeB		INT, 
		@vitorias		INT, 
		@empates		INT, 
		@derrotas		INT, 
		@gols_marcados	INT,
		@gols_sofridos	INT, 
		@saldo_gols		INT,
		@pontos			INT,
		@cont			INT
SET @cont = 0
WHILE @cont < 16
	BEGIN 
		SET @codigo = (SELECT TOP 1 t.codigoTime FROM Times t INNER JOIN Grupos g ON t.codigoTime = g.codigoTime WHERE t.nomeTime NOT IN (SELECT nome_time FROM @tabela))
		
		SET @nome_time = (SELECT nomeTime FROM Times WHERE codigoTime = @codigo)		

		SET @num_jogos = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo OR codigoTimeB = @codigo)
		
		SET @gols_timeA = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo AND golsTimeA > golsTimeB)
		SET @gols_timeB = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeB = @codigo AND golsTimeB > golsTimeA)
		SET @vitorias = @gols_timeA + @gols_timeB

		SET @gols_timeA = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo AND golsTimeA = golsTimeB)
		SET @gols_timeB = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeB = @codigo AND golsTimeB = golsTimeA)
		SET @empates = @gols_timeA + @gols_timeB
		 
		SET @gols_timeA = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeA = @codigo AND golsTimeA < golsTimeB)
		SET @gols_timeB = (SELECT COUNT(*) FROM Jogos WHERE codigoTimeB = @codigo AND golsTimeB < golsTimeA)
		SET @derrotas = @gols_timeA + @gols_timeB

		SET @gols_timeA = (SELECT SUM(golsTimeA) FROM Jogos WHERE codigoTimeA = @codigo)
		SET @gols_timeB = (SELECT SUM(golsTimeB) FROM Jogos WHERE codigoTimeB = @codigo)
		SET @gols_marcados = @gols_timeA + @gols_timeB

		SET @gols_timeA = (SELECT SUM(golsTimeA) FROM Jogos WHERE codigoTimeB = @codigo)
		SET @gols_timeB = (SELECT SUM(golsTimeB) FROM Jogos WHERE codigoTimeA = @codigo)
		SET @gols_sofridos = @gols_timeA + @gols_timeB
		 
		SET @saldo_gols = @gols_marcados - @gols_sofridos
		
		SET @pontos = (@vitorias * 3) + (@empates)

		INSERT INTO @tabela(nome_time,num_jogos_disputados,vitorias,empates,derrotas,gols_marcados,gols_sofridos,saldo_gols,pontos) 
		VALUES(@nome_time,@num_jogos,@vitorias,@empates,@derrotas,@gols_marcados,@gols_sofridos,@saldo_gols,@pontos) 
		
		SET @cont = @cont + 1
	END
	
	RETURN 
END

SELECT * FROM dbo.fn_tabela_campeonato() AS tabela_grupo ORDER BY pontos DESC, vitorias DESC, gols_marcados DESC, saldo_gols DESC
 --UDF para Quartas de Final
CREATE FUNCTION fn_quartas_de_final()
RETURNS @tabela TABLE(
	nome_time				VARCHAR(50)
)
AS
BEGIN
DECLARE @tabela_campeonato TABLE(
nome_time				VARCHAR(50),
num_jogos_disputados	INT, 
vitorias				INT, 
empates					INT, 
derrotas				INT, 
gols_marcados			INT,
gols_sofridos			INT, 
saldo_gols				INT,
pontos					INT
)
DECLARE @nome_time		VARCHAR(50)
DECLARE	@cont			INT,
		@cont2			INT
DECLARE @grupos			CHAR(4)
SET @grupos = 'ABCD'
SET @cont = 0
SET @cont2 = 1
WHILE @cont < 4
	BEGIN
		INSERT INTO @tabela_campeonato (nome_time,num_jogos_disputados,vitorias,empates,derrotas,gols_marcados,gols_sofridos,saldo_gols,pontos) 
		SELECT * FROM dbo.fn_tabela_grupo(SUBSTRING(@grupos,@cont2,1)) ORDER BY pontos DESC, vitorias DESC, gols_marcados DESC, saldo_gols DESC

		INSERT INTO @tabela(nome_time) 
		SELECT TOP 2 nome_time FROM @tabela_campeonato WHERE nome_time NOT IN(SELECT nome_time FROM @tabela) ORDER BY pontos DESC, vitorias DESC, gols_marcados DESC, saldo_gols DESC

		DELETE FROM @tabela_campeonato
		SET @cont2 = @cont2 + 1
		SET @cont = @cont + 1
	END
	RETURN 
END

SELECT * FROM dbo.fn_quartas_de_final() AS tabela_grupo