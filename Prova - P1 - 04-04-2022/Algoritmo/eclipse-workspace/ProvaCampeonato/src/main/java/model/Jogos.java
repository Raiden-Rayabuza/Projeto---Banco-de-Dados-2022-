package model;

import java.time.LocalDate;

public class Jogos {
	private String timeA;
	private String timeB;
	private int gols_timeA;
	private int gols_timeB;
	private LocalDate data;
	
	public String getTimeA() {
		return timeA;
	}
	public void setTimeA(String timeA) {
		this.timeA = timeA;
	}
	public String getTimeB() {
		return timeB;
	}
	public void setTimeB(String timeB) {
		this.timeB = timeB;
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
