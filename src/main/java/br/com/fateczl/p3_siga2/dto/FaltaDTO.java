package br.com.fateczl.p3_siga2.dto;
import java.time.LocalDate;
public class FaltaDTO {
	private AlunoDTO aluno;
	private DisciplinaDTO disciplina;
	private LocalDate data;
	private int presenca;
	public AlunoDTO getAluno() {
		return aluno;
	}
	public void setAluno(AlunoDTO aluno) {
		this.aluno = aluno;
	}
	public DisciplinaDTO getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(DisciplinaDTO disciplina) {
		this.disciplina = disciplina;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public int getPresenca() {
		return presenca;
	}
	public void setPresenca(int presenca) {
		this.presenca = presenca;
	}
	@Override
	public String toString() {
		return "Falta [aluno=" + aluno + ", data=" + data + ", disciplina=" + disciplina + ", presenca=" + presenca
				+ "]";
	}

	
}
