package model;

public class Tabela {
	private String nome_time;
	private int posicao;
	private int num_jogos_disputados;
	private int vitorias;
	private int derrotas;
	private int empates;
	private int gols_marcados;
	private int gols_sofridos;
	private int saldo_gols;
	private int pontos;
	private String cor;
	
	public String getNome_time() {
		return nome_time;
	}
	public void setNome_time(String nome_time) {
		this.nome_time = nome_time;
	}
	public int getPosicao() {
		return posicao;
	}
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
	public int getNum_jogos_disputados() {
		return num_jogos_disputados;
	}
	public void setNum_jogos_disputados(int num_jogos_disputados) {
		this.num_jogos_disputados = num_jogos_disputados;
	}
	public int getVitorias() {
		return vitorias;
	}
	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}
	public int getDerrotas() {
		return derrotas;
	}
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
	public int getEmpates() {
		return empates;
	}
	public void setEmpates(int empates) {
		this.empates = empates;
	}
	public int getGols_marcados() {
		return gols_marcados;
	}
	public void setGols_marcados(int gols_marcados) {
		this.gols_marcados = gols_marcados;
	}
	public int getGols_sofridos() {
		return gols_sofridos;
	}
	public void setGols_sofridos(int gols_sofridos) {
		this.gols_sofridos = gols_sofridos;
	}
	public int getSaldo_gols() {
		return saldo_gols;
	}
	public void setSaldo_gols(int saldo_gols) {
		this.saldo_gols = saldo_gols;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
}
