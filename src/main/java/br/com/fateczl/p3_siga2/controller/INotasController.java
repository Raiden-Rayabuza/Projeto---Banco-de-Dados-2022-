package br.com.fateczl.p3_siga2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.fateczl.p3_siga2.dto.NotasDTO;
import br.com.fateczl.p3_siga2.model.Notas;

public interface INotasController {
	public List<NotasDTO> listarFaltas();
	public ResponseEntity<NotasDTO> consultarFalta(String ra, String cod_disciplina);
	public ResponseEntity<String> insereFalta(Notas notas);
	public ResponseEntity<String> atualizaFalta(Notas notas);
	public ResponseEntity<String> excluirFalta(Notas notas);
}
