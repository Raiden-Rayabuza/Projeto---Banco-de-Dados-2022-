package br.com.fateczl.p3_siga2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.fateczl.p3_siga2.dto.AlunoDTO;
import br.com.fateczl.p3_siga2.dto.FaltaDTO;
import br.com.fateczl.p3_siga2.model.Falta;

public interface IFaltaController{
    public List<AlunoDTO> listarFaltas();
	public ResponseEntity<FaltaDTO> consultarFalta(String ra, String cod_disciplina);
	public ResponseEntity<String> insereFalta(Falta aluno);
	public ResponseEntity<String> atualizaFalta(Falta aluno);
	public ResponseEntity<String> excluirFalta(Falta aluno);
}