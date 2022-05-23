package br.com.fateczl.p3_siga2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fateczl.p3_siga2.dto.AlunoDTO;
import br.com.fateczl.p3_siga2.dto.FaltaDTO;
import br.com.fateczl.p3_siga2.model.Falta;
import br.com.fateczl.p3_siga2.repository.FaltaRepository;

@RestController
@RequestMapping("/siga")
public class FaltaController implements IFaltaController {
	@Autowired
	FaltaRepository fRep;

	@Override
	@PutMapping("/falta")
	public ResponseEntity<String> atualizaFalta(@Valid @RequestBody Falta aluno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping("/falta/{ra}/{cod_disciplina}")
	public ResponseEntity<FaltaDTO> consultarFalta(@PathVariable(value="ra")String ra, @PathVariable(value="cod_disciplina")String cod_disciplina) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@DeleteMapping("/falta")
	public ResponseEntity<String> excluirFalta(@Valid @RequestBody Falta aluno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PostMapping("/falta")
	public ResponseEntity<String> insereFalta(@Valid @RequestBody Falta aluno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping("/falta")
	public List<AlunoDTO> listarFaltas() {
		// TODO Auto-generated method stub
		return null;
	}
	private List<FaltaDTO> faltasToDtos(List<Falta> faltas) {
		List<FaltaDTO> faltasDTO = new ArrayList<FaltaDTO>();
		for (Falta f : faltas) {
			FaltaDTO faltaDTO = new FaltaDTO();
			faltaDTO = faltaToDto(f);
			
			faltasDTO.add(faltaDTO);
		}
		return alunosDTO;
	}

	private FaltaDTO faltaToDto(Falta falta) {
		FaltaDTO faltaDTO = new FaltaDTO();
		faltaDTO.setAluno(falta.getAluno());
		faltaDTO.setNome(falta.getNome());
		
		return faltaDTO;
	}
}
