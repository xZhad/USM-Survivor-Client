package cl.edenprime.survivor.modelo;

public class Academico {
	
	private int id;
	private String nombre;
	private String oficina;
	private String anexo;
	private String correo;
	private String pagina;
	private String area;
	private String campus;
	
	
	public Academico(int id, String nombre, String oficina, String anexo, String correo, String pagina, String area, String campus) {
		setId(id);
		setNombre(nombre);
		setOficina(oficina);
		setAnexo(anexo);
		setCorreo(correo);
		setPagina(pagina);
		setArea(area);
		setCampus(campus);
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

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public String getOficina() {
		return oficina;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getPagina() {
		return pagina;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getCampus() {
		return campus;
	}
	
}
