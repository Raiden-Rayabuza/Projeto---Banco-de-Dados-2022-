package br.com.fateczl.p3_siga2.model;

import java.io.Serializable;

public class FaltaPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Aluno aluno;
    private Disciplina disciplina;
    
    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public String toString() {
        return "FaltaPK [aluno=" + aluno + ", disciplina=" + disciplina + "]";
    }

}
