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

import br.com.fateczl.p3_siga2.dto.DisciplinaDTO;
import br.com.fateczl.p3_siga2.model.Disciplina;
import br.com.fateczl.p3_siga2.repository.DisciplinaRepository;
@RestController
@RequestMapping("/siga ")
public class DisciplinaController implements IDisciplinaController{
    @Autowired
    DisciplinaRepository dRep;

    @Override
    @PutMapping("/disciplina")
    public ResponseEntity<String> atualizaDisciplina(@Valid @RequestBody Disciplina disciplina) {
        dRep.save(disciplina);   
        String saida = "Disciplina Atualizada com Sucesso"; 
        return ResponseEntity.ok().body(saida);
    }

    @Override
    @GetMapping("/disciplina/{cod_disciplina}")
    public ResponseEntity<DisciplinaDTO> consultarDisciplina(@PathVariable(value="cod_disciplina")String cod_disciplina) {
        Disciplina disciplina = dRep.findById(cod_disciplina).orElseThrow();
        DisciplinaDTO disciplinaDTO = disciplinaToDTO(disciplina);

        return ResponseEntity.ok().body(disciplinaDTO);
    }

    @Override
    @DeleteMapping("/disciplina")
    public ResponseEntity<String> excluiDisciplina(Disciplina disciplina) {
        dRep.delete(disciplina);
        String saida = "Disciplina removida com sucesso";
        return ResponseEntity.ok().body(saida);
    }

    @Override
    @PostMapping("/disciplina")
    public ResponseEntity<String> insereDisciplina(Disciplina disciplina) {
        dRep.save(disciplina);
        String saida = "Disciplina adicionada com sucesso";
        return ResponseEntity.ok().body(saida);
    }

    @Override
    public List<DisciplinaDTO> listarDisciplinas() {
        List<Disciplina> disciplinas = dRep.findAll();
        List<DisciplinaDTO> disciplinasDTO = disciplinasToDTOS(disciplinas); 
        return disciplinasDTO;
    }
    private List<DisciplinaDTO> disciplinasToDTOS(List<Disciplina> disciplinas) {
		List<DisciplinaDTO> disciplinasDTO = new ArrayList<DisciplinaDTO>();
		for (Disciplina d : disciplinas) {
			DisciplinaDTO disciplinaDTO = new DisciplinaDTO();
			disciplinaDTO = disciplinaToDTO(d);
			
			disciplinasDTO.add(disciplinaDTO);
		}
		return disciplinasDTO;
	}

	private DisciplinaDTO disciplinaToDTO(Disciplina disciplina) {
		DisciplinaDTO disciplinaDTO = new DisciplinaDTO();
		disciplinaDTO.setCod_disciplina(disciplina.getCod_disciplina());
		disciplinaDTO.setNome(disciplina.getNome());
		disciplinaDTO.setNum_aulas(disciplina.getNum_aulas());
        disciplinaDTO.setSigla(disciplina.getSigla());
        disciplinaDTO.setTurno(disciplina.getTurno());
		return disciplinaDTO;
	}
}
