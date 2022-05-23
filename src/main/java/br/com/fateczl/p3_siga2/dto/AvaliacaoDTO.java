package br.com.fateczl.p3_siga2.dto;

public class AvaliacaoDTO {
	private int cod;
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
