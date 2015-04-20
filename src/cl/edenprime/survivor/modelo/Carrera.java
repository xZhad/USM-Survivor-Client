package cl.edenprime.survivor.modelo;

public class Carrera {
	
	private int id;
	private String nombre;
	private int id_departamento;
	
	public Carrera (int id, String nombre, int id_departamento) {
		setId(id);
		setNombre(nombre);
		setId_departamento(id_departamento);
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId_departamento(int id_departamento) {
		this.id_departamento = id_departamento;
	}

	public int getId_departamento() {
		return id_departamento;
	}
	
}
