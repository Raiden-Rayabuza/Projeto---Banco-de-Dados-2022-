package br.com.fateczl.p3_siga2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.fateczl.p3_siga2.dto.NotasDTO;
import br.com.fateczl.p3_siga2.model.Notas;

public class NotasController implements INotasController {

    @Override
    public ResponseEntity<String> atualizaFalta(Notas notas) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<NotasDTO> consultarFalta(String ra, String cod_disciplina) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<String> excluirFalta(Notas notas) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<String> insereFalta(Notas notas) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NotasDTO> listarFaltas() {
        // TODO Auto-generated method stub
        return null;
    }

    private List<NotasDTO> notasToDtos(List<Notas> faltas) {
		List<NotasDTO> notasDTO = new ArrayList<NotasDTO>();
		for (Notas n : faltas) {
			NotasDTO notaDTO = new NotasDTO();
			notaDTO = notaToDto(n);
			
			notasDTO.add(notaDTO);
		}
		return notasDTO;
	}

	private FaltaDTO notaToDto(Falta falta) {
		FaltaDTO faltaDTO = new FaltaDTO();
		faltaDTO.setAluno(falta.getAluno());
		faltaDTO.setNome(falta.getNome());
		
		return faltaDTO;
	}
}
