package br.com.fateczl.p3_siga2.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Entity
@Table(name="falta")
@IdClass(FaltaPK.class)
public class Falta {
	@Id
	@ManyToOne(targetEntity = Aluno.class)
	@JoinColumn(name = "ra")
	@NonNull
	private Aluno aluno;
	@Id
	@ManyToOne(targetEntity = Disciplina.class)
	@JoinColumn(name = "cod_disciplina")
	@NonNull
	private Disciplina disciplina;
	@Column(name = "data_falta")
	@NonNull
	private LocalDate data;
	@Column(name = "presenca")
	@NonNull
	private int presenca;
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
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
