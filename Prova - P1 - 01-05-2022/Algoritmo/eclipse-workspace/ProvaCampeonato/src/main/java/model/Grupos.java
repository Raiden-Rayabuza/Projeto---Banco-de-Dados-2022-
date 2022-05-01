package model;
public class Grupos {
	private String grupo;
	private int timeCod;
	private String timeNome;
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getTimeNome() {
		return timeNome;
	}
	public void setTimeNome(String time) {
		this.timeNome = time;
	}
	public int getTimeCod() {
		return timeCod;
	}
	public void setTimeCod(int timeCod) {
		this.timeCod = timeCod;
	}
	
	@Override
	public String toString() {
		return "Grupos [grupo=" + grupo + ", timeCod=" + timeCod + ", timeNome=" + timeNome + "]";
	}
	
	
}
