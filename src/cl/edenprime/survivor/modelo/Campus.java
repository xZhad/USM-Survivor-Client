package cl.edenprime.survivor.modelo;

public class Campus {
	
	private int id;
	private String sigla;
	private String nombre;
	
	public Campus (int id, String sigla, String nombre) {
		setId(id);
		setSigla(sigla);
		setNombre(nombre);
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
}
