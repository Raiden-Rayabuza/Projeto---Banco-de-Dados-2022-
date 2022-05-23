package br.com.fateczl.p3_siga2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.fateczl.p3_siga2.dto.AvaliacaoDTO;
import br.com.fateczl.p3_siga2.model.Avaliacao;

public interface IAvaliacaoController {
    public List<AvaliacaoDTO> listarAvaliacoes();
	public ResponseEntity<AvaliacaoDTO> consultarAvaliacao(int cod_avaliacao);
	public ResponseEntity<String> insereAvaliacao(Avaliacao avaliacao);
	public ResponseEntity<String> atualizaAvaliacao(Avaliacao avaliacao);
	public ResponseEntity<String> excluiAvaliacao(Avaliacao avaliacao);
}
