package cl.edenprime.survivor.obj;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import cl.edenprime.survivor.R;
import cl.edenprime.survivor.dao.DataBaseHelper;
import cl.edenprime.survivor.dao.ServiciosDeDatos;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.modelo.Blocks;
import cl.edenprime.survivor.modelo.Campus;
import cl.edenprime.survivor.modelo.Carrera;
import cl.edenprime.survivor.modelo.Clase;
import cl.edenprime.survivor.modelo.Departamento;
import cl.edenprime.survivor.modelo.Ramo;
import cl.edenprime.survivor.modelo.Usuario;

public class dataS {
	
	// Preferences
	private static SharedPreferences prefs;
	private static boolean firstTime;
	private static boolean isSplashEnabled;
	private static int appStyle;
	
	// Location
	private static int id_campus;
	private static int id_departamento;
	private static int id_carrera;
	
	// User
	private static Usuario user;
	
	// Data
	/** Servicio de datos. */
    private static ServiciosDeDatos serviciosDatos;
	private static Campus campus;
	private static Departamento departamento;
	private static Carrera carrera;
	private static ArrayList<Ramo> ramosINS;
	private static ArrayList<Ramo> ramosFAL;
	private static ArrayList<Clase> clases;
	private static ArrayList<Actividad> actividades;
	private static ArrayList<Blocks> bloques;
	
	/** Widget */
	private static boolean widgetON = false;
	private static ArrayList<Actividad> actividadesCercanas;
	
	public static void loadPrefs(Context context) {
		
		// Obtener servicio de datos
        setServiciosDatos(DataBaseHelper.getInstance(context));
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		setSplashEnabled(prefs.getBoolean("splash", true));
		setAppStyle(Integer.valueOf(prefs.getString("appStyle", "0")));
		ThemeSwitcher.setSelectedTheme(appStyle);
		
		setPrefs(context.getSharedPreferences("Survivor", Context.MODE_PRIVATE));
		setFirstTime(getPrefs().getBoolean("firstTime", true));
		setId_campus(getPrefs().getInt("idCampus", 0));
		setId_departamento(getPrefs().getInt("idDepartamento", 0));
		setId_carrera(getPrefs().getInt("idCarrera", 0));
		
		setUser(new Usuario(getPrefs().getInt("idUser", -1), getPrefs().getString("User", ""), getPrefs().getString("Pass", "")));
	}
	
	public static void loadData(Context context) {
		setCampus(getServiciosDatos().obtenerCampus(getId_campus()));
		setDepartamento(getServiciosDatos().obtenerDepartamento(getId_departamento()));
		setCarrera(getServiciosDatos().obtenerCarrera(getId_carrera()));
		setRamosINS(getServiciosDatos().obtenerRamosInscritos());
		setRamosFAL(getServiciosDatos().obtenerRamosFaltantes());
		setClases(getServiciosDatos().obtenerClases());
		setActividades(getServiciosDatos().obtenerActividades());
		setBlocks(context);
	}
	
	public static void loadAct(Context context) {
		setRamosINS(getServiciosDatos().obtenerRamosInscritos());
		setActividadesCercanas(getServiciosDatos().obtenerActividadesCercanas());
	}
	
	public static void setBlocks(Context context) {
		String[] start = context.getResources().getStringArray(R.array.blockStart);
		String[] end = context.getResources().getStringArray(R.array.blockEnd);
		
		if (bloques == null)
			bloques = new ArrayList<Blocks>();
		else
			bloques.clear();
		
		for (int i = 0; i < start.length; i ++) {
			bloques.add(
					new Blocks(i+1,
							Integer.valueOf(start[i].split(":")[0]),
							Integer.valueOf(start[i].split(":")[1]),
							Integer.valueOf(end[i].split(":")[0]),
							Integer.valueOf(end[i].split(":")[1]))
					);
		}
	}
	
	
	// Getters & Setters
	
	public static boolean isSplashEnabled() {
		return isSplashEnabled;
	}

	public static void setSplashEnabled(boolean isSplashEnabled) {
		dataS.isSplashEnabled = isSplashEnabled;
	}

	public static int getAppStyle() {
		return appStyle;
	}

	public static void setAppStyle(int appStyle) {
		dataS.appStyle = appStyle;
	}

	public static void setFirstTime(boolean firstTime) {
		Log.i("FIRSTTIME", String.valueOf(firstTime));
		SharedPreferences.Editor editor = getPrefs().edit();
		editor.putBoolean("firstTime", firstTime);
		editor.commit();
		dataS.firstTime = firstTime;
	}

	public static boolean isFirstTime() {
		return firstTime;
	}

	public static void setServiciosDatos(ServiciosDeDatos serviciosDatos) {
		dataS.serviciosDatos = serviciosDatos;
	}

	public static ServiciosDeDatos getServiciosDatos() {
		return serviciosDatos;
	}

	public static void setId_campus(int id_campus) {
		Log.i("IDCAMPUS", String.valueOf(id_campus));
		SharedPreferences.Editor editor = getPrefs().edit();
		editor.putInt("idCampus", id_campus);
		editor.commit();
		dataS.id_campus = id_campus;
	}

	public static int getId_campus() {
		return id_campus;
	}

	public static void setId_departamento(int id_departamento) {
		Log.i("IDDEPARTAMENTO", String.valueOf(id_departamento));
		SharedPreferences.Editor editor = getPrefs().edit();
		editor.putInt("idDepartamento", id_departamento);
		editor.commit();
		dataS.id_departamento = id_departamento;
	}

	public static int getId_departamento() {
		return id_departamento;
	}

	public static void setId_carrera(int id_carrera) {
		Log.i("IDCARRERA", String.valueOf(id_carrera));
		SharedPreferences.Editor editor = getPrefs().edit();
		editor.putInt("idCarrera", id_carrera);
		editor.commit();
		dataS.id_carrera = id_carrera;
	}

	public static int getId_carrera() {
		return id_carrera;
	}

	public static void setPrefs(SharedPreferences prefs) {
		dataS.prefs = prefs;
	}

	public static SharedPreferences getPrefs() {
		return prefs;
	}

	public static void setBloques(ArrayList<Blocks> bloques) {
		dataS.bloques = bloques;
	}

	public static ArrayList<Blocks> getBloques() {
		return bloques;
	}

	public static void setCampus(Campus campus) {
		dataS.campus = campus;
	}

	public static Campus getCampus() {
		return campus;
	}

	public static void setDepartamento(Departamento departamento) {
		dataS.departamento = departamento;
	}

	public static Departamento getDepartamento() {
		return departamento;
	}

	public static void setCarrera(Carrera carrera) {
		dataS.carrera = carrera;
	}

	public static Carrera getCarrera() {
		return carrera;
	}

	public static void setRamosINS(ArrayList<Ramo> ramosINS) {
		dataS.ramosINS = ramosINS;
	}

	public static ArrayList<Ramo> getRamosINS() {
		return ramosINS;
	}

	public static void setRamosFAL(ArrayList<Ramo> ramosFAL) {
		dataS.ramosFAL = ramosFAL;
	}

	public static ArrayList<Ramo> getRamosFAL() {
		return ramosFAL;
	}

	public static void setClases(ArrayList<Clase> clases) {
		dataS.clases = clases;
	}

	public static ArrayList<Clase> getClases() {
		return clases;
	}

	public static void setActividades(ArrayList<Actividad> actividades) {
		dataS.actividades = actividades;
	}

	public static ArrayList<Actividad> getActividades() {
		return actividades;
	}

	public static void setWidgetON(boolean widgetON) {
		dataS.widgetON = widgetON;
	}

	public static boolean isWidgetON() {
		return widgetON;
	}

	public static void setActividadesCercanas(ArrayList<Actividad> actividadesCercanas) {
		dataS.actividadesCercanas = actividadesCercanas;
	}

	public static ArrayList<Actividad> getActividadesCercanas() {
		return actividadesCercanas;
	}

	public static void setUser(Usuario user) {
		Log.i("idUser", String.valueOf(user.getId()));
		
		SharedPreferences.Editor editor = getPrefs().edit();
		editor.putInt("idUser", user.getId());
		editor.putString("User", user.getUser());
		editor.putString("Pass", user.getPass());
		editor.commit();
		dataS.user = user;
	}

	public static Usuario getUser() {
		return user;
	}
}
