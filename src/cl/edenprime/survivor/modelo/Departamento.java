package cl.edenprime.survivor.modelo;

public class Departamento {
	
	private int id;
	private String nombre;
	
	public Departamento (int id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
}
