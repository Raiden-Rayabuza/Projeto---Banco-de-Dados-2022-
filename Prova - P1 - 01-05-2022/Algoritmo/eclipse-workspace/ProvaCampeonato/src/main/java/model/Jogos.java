package model;

import java.time.LocalDate;

public class Jogos {
	private int codTimeA;
	private int codTimeB;
	private String nomeTimeA;
	private String nomeTimeB;
	private int gols_timeA;
	private int gols_timeB;
	private LocalDate data;
	
	public int getCodTimeA() {
		return codTimeA;
	}
	public void setCodTimeA(int codTimeA) {
		this.codTimeA = codTimeA;
	}
	public int getCodTimeB() {
		return codTimeB;
	}
	public void setCodTimeB(int codTimeB) {
		this.codTimeB = codTimeB;
	}
	public String getNomeTimeA() {
		return nomeTimeA;
	}
	public void setNomeTimeA(String nomeTimeA) {
		this.nomeTimeA = nomeTimeA;
	}
	public String getNomeTimeB() {
		return nomeTimeB;
	}
	public void setNomeTimeB(String nomeTimeB) {
		this.nomeTimeB = nomeTimeB;
	}
	public int getGols_timeA() {
		return gols_timeA;
	}
	public void setGols_timeA(int gols_timeA) {
		this.gols_timeA = gols_timeA;
	}
	public int getGols_timeB() {
		return gols_timeB;
	}
	public void setGols_timeB(int gols_timeB) {
		this.gols_timeB = gols_timeB;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
}
