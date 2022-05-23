package br.com.fateczl.p3_siga2.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {
	@Id
	@Column(name="cod_avaliacao")
	@NonNull
	private int cod;

	@Id
	@Column(name="tipo", length = 20)
	@NonNull
	private String tipo;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "avaliacao [cod=" + cod + ", tipo=" + tipo + "]";
	}
	
}
