package cl.edenprime.survivor.modelo;

public class Clase {
	
	private int id;
	private int idRamo;
	private int tipo;
	private int dia; // 0 - 6
	private String sala;
	private Blocks bloque;
	
	public Clase (int id, int idRamo, int tipo, int dia, String sala, Blocks bloque) {
		setId(id);
		setIdRamo(idRamo);
		setTipo(tipo);
		setDia(dia);
		setSala(sala);
		setBloque(bloque);
	}
	
	public Clase (int idRamo, int tipo, int dia, String sala, Blocks bloque) {
		setId(0);
		setIdRamo(idRamo);
		setTipo(tipo);
		setDia(dia);
		setSala(sala);
		setBloque(bloque);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setIdRamo(int idRamo) {
		this.idRamo = idRamo;
	}

	public int getIdRamo() {
		return idRamo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getDia() {
		return dia;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getSala() {
		return sala;
	}

	public void setBloque(Blocks bloque) {
		this.bloque = bloque;
	}

	public Blocks getBloque() {
		return bloque;
	}
	
}
