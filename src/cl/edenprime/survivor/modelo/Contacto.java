package cl.edenprime.survivor.modelo;

public class Contacto {
	
	private int id;
	private String nombre;
	private String data;
	
	
	public Contacto(int id, String nombre, String data) {
		setId(id);
		setNombre(nombre);
		setData(data);
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

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
	
}
