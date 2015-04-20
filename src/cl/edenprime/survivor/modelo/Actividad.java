package cl.edenprime.survivor.modelo;

public class Actividad {
	
	private int id;
	private int idRamo;
	private int tipo;
	private int dia;
	private int mes; // 0 - 11
	private int ano;
	private String lugar;
	private Blocks bloque;
	private String nombre;
	private int alarma; // 0 - 1
	private int porcentaje;
	private int nota;
	
	public Actividad (int id, int idRamo, int tipo, int dia, int mes, int ano, String lugar, Blocks bloque, String nombre, int alarma, int porcentaje, int nota) {
		setId(id);
		setIdRamo(idRamo);
		setTipo(tipo);
		setDia(dia);
		setMes(mes);
		setAno(ano);
		setLugar(lugar);
		setBloque(bloque);
		setNombre(nombre);
		setAlarma(alarma);
		setPorcentaje(porcentaje);
		setNota(nota);
	}
	
	/**
	 * Crea la Actividad con Nombre
	 * @param tipo
	 * @param dia
	 * @param mes
	 * @param ano
	 * @param lugar
	 * @param bloque
	 * @param nombre
	 * @param alarma
	 */
	public Actividad (int dia, int mes, int ano, String lugar, Blocks bloque, String nombre, int alarma) {
		setId(0);
		setIdRamo(0);
		setTipo(0);
		setDia(dia);
		setMes(mes);
		setAno(ano);
		setLugar(lugar);
		setBloque(bloque);
		setNombre(nombre);
		setAlarma(alarma);
		setPorcentaje(-1);
		setNota(-1);
	}
	
	/**
	 * Crea la Actividad con Ramo
	 * @param idRamo
	 * @param tipo
	 * @param dia
	 * @param mes
	 * @param ano
	 * @param lugar
	 * @param bloque
	 * @param alarma
	 */
	public Actividad (int idRamo, int tipo, int dia, int mes, int ano, String lugar, Blocks bloque, int alarma, int porcentaje) {
		setId(0);
		setIdRamo(idRamo);
		setTipo(tipo);
		setDia(dia);
		setMes(mes);
		setAno(ano);
		setLugar(lugar);
		setBloque(bloque);
		setNombre("");
		setAlarma(alarma);
		setPorcentaje(porcentaje);
		setNota(-1);
	}
	
	public String formatDate() {
		String year = "";
		String month = "";
		String day = "";
		String aux0 = "";
		String aux1 = "";
		if (getDia() < 10)
			aux0 = "0";
		if (getMes() < 9)
			aux1 = "0";
		year = String.valueOf(getAno());
		month = aux1 + String.valueOf(getMes()+1);
		day = aux0 + String.valueOf(getDia());
		String result = day + " / " + month + " / " + year;
		return result;
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

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getMes() {
		return mes;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getAno() {
		return ano;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getLugar() {
		return lugar;
	}

	public void setBloque(Blocks bloque) {
		this.bloque = bloque;
	}

	public Blocks getBloque() {
		return bloque;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setAlarma(int alarma) {
		this.alarma = alarma;
	}

	public int getAlarma() {
		return alarma;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public int getNota() {
		return nota;
	}
	
}
