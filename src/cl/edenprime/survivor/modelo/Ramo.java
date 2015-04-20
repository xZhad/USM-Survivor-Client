package cl.edenprime.survivor.modelo;

public class Ramo {
	
	private int id;
	private String sigla;
	private String nombre;
	private int creditos;
	private int estado;
	
	public Ramo (int id, String sigla, String nombre, int creditos, int estado) {
		setId(id);
		setSigla(sigla);
		setNombre(nombre);
		setCreditos(creditos);
		setEstado(estado);
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
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
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
	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	/**
	 * @return the creditos
	 */
	public int getCreditos() {
		return creditos;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}
	
}
