package cl.edenprime.survivor.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cl.edenprime.survivor.modelo.Academico;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.modelo.Blocks;
import cl.edenprime.survivor.modelo.Campus;
import cl.edenprime.survivor.modelo.Carrera;
import cl.edenprime.survivor.modelo.Clase;
import cl.edenprime.survivor.modelo.Contacto;
import cl.edenprime.survivor.modelo.Departamento;
import cl.edenprime.survivor.modelo.Ramo;
import cl.edenprime.survivor.obj.dataS;

public class DataBaseHelper extends SQLiteOpenHelper implements ServiciosDeDatos {
	 
    private static final String TAG_DB = "DATABASE";
    private static final int TAM_BUFFER = 1024;
    
	//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/cl.edenprime.survivor/databases/";
 
    private static String DB_NAME = "USMDATA.db";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    
    private static DataBaseHelper instance;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }
    
    /**
     * Sincronizacion de la instancia de la propia clase. Si no ha sido
     * instanciada, crea la instancia y la inicia, hechando a andar la
     * maquinaria, de checkeo, creacion de DB, etc.
     * @param context
     *            Contexto de la clase que instancia la clase por primera vez.
     * @return Retorna la instancia de DataBaseHelper.
     */
    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
            instance.iniciar();
        }
        return instance;
    }
 
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try {
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	} catch (SQLiteException e){
    		//database does't exist yet.
    	}
 
    	if(checkDB != null) {
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	AssetManager am = myContext.getAssets();
        String outFileName = DB_PATH + DB_NAME;
        OutputStream os = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[TAM_BUFFER];
        int r;
        	InputStream is = am.open(DB_NAME);
            while ((r = is.read(buffer)) != -1)
                os.write(buffer, 0, r);
            is.close();
        os.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
       // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	
	@Override
    public void iniciar() {
        try {
            createDataBase();
            openDataBase();
        } catch (IOException e) {
            Log.e(TAG_DB, e.getMessage());
        }
    }
	
	@Override
	public ArrayList<Campus> obtenerCampus() {
		ArrayList<Campus> campus = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery("SELECT Campus._id, Campus.sigla, Campus.nombre FROM Campus ", null);
			cursor.moveToFirst();
			campus = new ArrayList<Campus>();
			while (!cursor.isAfterLast()) {
				Campus camp = new Campus(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				campus.add(camp);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return campus;
	}
	
	@Override
	public Campus obtenerCampus(int id_campus) {
		Campus campus = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Campus._id, Campus.sigla, Campus.nombre FROM Campus " +
					"WHERE Campus._id = " + id_campus + " "
					, null);
			cursor.moveToFirst();
			campus = new Campus(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return campus;
	}

	@Override
	public ArrayList<Departamento> obtenerDepartamentos() {
		ArrayList<Departamento> departamentos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery("SELECT Departamentos._id, Departamentos.nombre FROM Departamentos ", null);
			cursor.moveToFirst();
			departamentos = new ArrayList<Departamento>();
			while (!cursor.isAfterLast()) {
				Departamento depto = new Departamento(cursor.getInt(0), cursor.getString(1));
				departamentos.add(depto);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return departamentos;
	}
	
	@Override
	public Departamento obtenerDepartamento(int id_departamento) {
		Departamento departamento = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Departamentos._id, Departamentos.nombre FROM Departamentos " +
					"WHERE Departamentos._id = " + id_departamento + " "
					, null);
			cursor.moveToFirst();
			departamento = new Departamento(cursor.getInt(0), cursor.getString(1));
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return departamento;
	}

	@Override
	public ArrayList<Carrera> obtenerCarreras(int id_campus, int id_departamento) {
		ArrayList<Carrera> carreras = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Carreras._id, Carreras.nombre, Carreras.idDepartamento FROM Carreras " +
					"INNER JOIN CarreraPorCampus ON (Carreras._id = CarreraPorCampus.idCarrera) " +
					"WHERE Carreras.idDepartamento = " + id_departamento + " " +
					"AND CarreraPorCampus.idCampus = " + id_campus + " "
					, null);
			cursor.moveToFirst();
			carreras = new ArrayList<Carrera>();
			while (!cursor.isAfterLast()) {
				Carrera carr = new Carrera(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
				carreras.add(carr);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return carreras;
	}
	
	@Override
	public Carrera obtenerCarrera(int id_carrera) {
		Carrera carrera = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Carreras._id, Carreras.nombre, Carreras.idDepartamento FROM Carreras " +
					"WHERE Carreras._id = " + id_carrera + " "
					, null);
			cursor.moveToFirst();
			carrera = new Carrera(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return carrera;
	}

	@Override
	public Ramo obtenerRamo(int id_ramo) {
		Ramo ramo = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Ramos._id, Ramos.sigla, Ramos.nombre, Ramos.creditos, Ramos.estado FROM Ramos " +
					"WHERE Ramos._id = " + id_ramo + " "
					, null);
			cursor.moveToFirst();
			ramo = new Ramo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return ramo;
	}

	@Override
	public ArrayList<Ramo> obtenerRamos() {
		ArrayList<Ramo> ramos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Ramos._id, Ramos.sigla, Ramos.nombre, Ramos.creditos, Ramos.estado FROM Ramos " +
					"INNER JOIN RamosPorCarrera ON (Ramos._id = RamosPorCarrera.idRamo) " +
					"WHERE RamosPorCarrera.idCarrera = " + dataS.getId_carrera() + " "
					, null);
			cursor.moveToFirst();
			ramos = new ArrayList<Ramo>();
			while (!cursor.isAfterLast()) {
				Ramo ram = new Ramo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				ramos.add(ram);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return ramos;
	}

	@Override
	public ArrayList<Ramo> obtenerRamosAprobados() {
		ArrayList<Ramo> ramos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Ramos._id, Ramos.sigla, Ramos.nombre, Ramos.creditos, Ramos.estado FROM Ramos " +
					"INNER JOIN RamosPorCarrera ON (Ramos._id = RamosPorCarrera.idRamo) " +
					"WHERE RamosPorCarrera.idCarrera = " + dataS.getId_carrera() + " " +
					"AND Ramos.estado = 1 "
					, null);
			cursor.moveToFirst();
			ramos = new ArrayList<Ramo>();
			while (!cursor.isAfterLast()) {
				Ramo ram = new Ramo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				ramos.add(ram);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return ramos;
	}
	
	@Override
	public ArrayList<Ramo> obtenerRamosInscritos() {
		ArrayList<Ramo> ramos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Ramos._id, Ramos.sigla, Ramos.nombre, Ramos.creditos, Ramos.estado FROM Ramos " +
					"INNER JOIN RamosPorCarrera ON (Ramos._id = RamosPorCarrera.idRamo) " +
					"WHERE RamosPorCarrera.idCarrera = " + dataS.getId_carrera() + " " +
					"AND Ramos.estado = 0 "
					, null);
			cursor.moveToFirst();
			ramos = new ArrayList<Ramo>();
			while (!cursor.isAfterLast()) {
				Ramo ram = new Ramo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				ramos.add(ram);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return ramos;
	}

	@Override
	public ArrayList<Ramo> obtenerRamosFaltantes() {
		ArrayList<Ramo> ramos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Ramos._id, Ramos.sigla, Ramos.nombre, Ramos.creditos, Ramos.estado FROM Ramos " +
					"INNER JOIN RamosPorCarrera ON (Ramos._id = RamosPorCarrera.idRamo) " +
					"WHERE RamosPorCarrera.idCarrera = " + dataS.getId_carrera() + " " +
					"AND Ramos.estado = -1 "
					, null);
			cursor.moveToFirst();
			ramos = new ArrayList<Ramo>();
			while (!cursor.isAfterLast()) {
				Ramo ram = new Ramo(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
				ramos.add(ram);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return ramos;
	}

	@Override
	public void aprobarRamos(ArrayList<Ramo> aprobados) {
		Cursor cursor = null;
		try {
			for (int i = 0; i < aprobados.size(); i++) {
				myDataBase.execSQL(
						"UPDATE Ramos " +
						"SET estado = 1 " +
						"WHERE Ramos._id = " + aprobados.get(i).getId() + " ");
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public void inscribirRamos(ArrayList<Ramo> inscritos) {
		Cursor cursor = null;
		try {
			for (int i = 0; i < inscritos.size(); i++) {
				myDataBase.execSQL(
						"UPDATE Ramos " +
						"SET estado = 0 " +
						"WHERE Ramos._id = " + inscritos.get(i).getId() + " ");
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public void desinscribirRamos(ArrayList<Ramo> desinscritos) {
		Cursor cursor = null;
		try {
			for (int i = 0; i < desinscritos.size(); i++) {
				myDataBase.execSQL(
						"UPDATE Ramos " +
						"SET estado = -1 " +
						"WHERE Ramos._id = " + desinscritos.get(i).getId() + " ");
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public int agregarClase(Clase clase) {
		Cursor cursor = null;
		int returnID = 0;
		try {
				myDataBase.execSQL(
						"INSERT INTO Clases " +
						"(idRamo, idTipo, dia, sala, startHour, startMinute, endHour, endMinute) " +
						"VALUES (" +
						clase.getIdRamo() + ", " +
						clase.getTipo() + ", " +
						clase.getDia() + ", " +
						"'" + clase.getSala() + "'" + ", " +
						clase.getBloque().getStarthour() + ", " +
						clase.getBloque().getStartminute() + ", " +
						clase.getBloque().getEndhour() + ", " +
						clase.getBloque().getEndminute() + ") ");
				
				cursor = myDataBase.rawQuery(
						"SELECT MAX(Clases._id) AS currentID FROM Clases "
						, null);
				cursor.moveToFirst();
				returnID = cursor.getInt(0);
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return returnID;
	}

	@Override
	public ArrayList<Clase> obtenerClases() {
		ArrayList<Clase> clases = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Clases._id, Clases.idRamo, Clases.idTipo, Clases.dia, Clases.sala, Clases.startHour, Clases.startMinute, Clases.endHour, Clases.endMinute FROM Clases "
					, null);
			cursor.moveToFirst();
			clases = new ArrayList<Clase>();
			while (!cursor.isAfterLast()) {
				Blocks bloque = new Blocks(cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8));
				Clase clas = new Clase(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), bloque);
				clases.add(clas);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return clases;
	}

	@Override
	public int agregarActividad(Actividad actividad) {
		Cursor cursor = null;
		int returnID = 0;
		try {
				myDataBase.execSQL(
						"INSERT INTO Actividades " +
						"(idRamo, idTipo, dia, mes, ano, lugar, startHour, startMinute, endHour, endMinute, nombre, alarma, porcentaje, nota) " +
						"VALUES (" +
						actividad.getIdRamo() + ", " +
						actividad.getTipo() + ", " +
						actividad.getDia() + ", " +
						actividad.getMes() + ", " +
						actividad.getAno() + ", " +
						"'" + actividad.getLugar() + "'" + ", " +
						actividad.getBloque().getStarthour() + ", " +
						actividad.getBloque().getStartminute() + ", " +
						actividad.getBloque().getEndhour() + ", " +
						actividad.getBloque().getEndminute() + ", " +
						"'" + actividad.getNombre() + "'" + ", " +
						actividad.getAlarma() + ", " +
						actividad.getPorcentaje() + ", " +
						actividad.getNota() + ") ");
				
				cursor = myDataBase.rawQuery(
						"SELECT MAX(Clases._id) AS currentID FROM Clases "
						, null);
				cursor.moveToFirst();
				returnID = cursor.getInt(0);
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return returnID;
	}

	@Override
	public ArrayList<Actividad> obtenerActividades() {
		ArrayList<Actividad> actividades = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Actividades._id, Actividades.idRamo, Actividades.idTipo, Actividades.dia, Actividades.mes, Actividades.ano, Actividades.lugar, Actividades.startHour, Actividades.startMinute, Actividades.endHour, Actividades.endMinute, Actividades.nombre, Actividades.alarma, Actividades.porcentaje, Actividades.nota FROM Actividades "
					, null);
			cursor.moveToFirst();
			actividades = new ArrayList<Actividad>();
			while (!cursor.isAfterLast()) {
				Blocks bloque = new Blocks(cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10));
				Actividad act = new Actividad(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6), bloque, cursor.getString(11), cursor.getInt(12), cursor.getInt(13), cursor.getInt(14));
				actividades.add(act);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return actividades;
	}

	@Override
	public ArrayList<Actividad> obtenerActividadesCercanas() {
		final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
		ArrayList<Actividad> actividades = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Actividades._id, Actividades.idRamo, Actividades.idTipo, Actividades.dia, Actividades.mes, Actividades.ano, Actividades.lugar, Actividades.startHour, Actividades.startMinute, Actividades.endHour, Actividades.endMinute, Actividades.nombre, Actividades.alarma, Actividades.porcentaje, Actividades.nota FROM Actividades " +
					"ORDER BY Actividades.ano, Actividades.mes, Actividades.dia "
					, null);
			cursor.moveToFirst();
			actividades = new ArrayList<Actividad>();
			while (!cursor.isAfterLast()) {
				Blocks bloque = new Blocks(cursor.getInt(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10));
				Actividad act = new Actividad(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6), bloque, cursor.getString(11), cursor.getInt(12), cursor.getInt(13), cursor.getInt(14));
				if (act.getAno() >= year) {
					if ( act.getMes() >= month || ( act.getMes() < month && act.getAno() > year ) ) {
						if ( act.getDia() >= day || ( ( act.getDia() < day && act.getMes() > month ) || ( act.getDia() < day && act.getMes() < month && act.getAno() > year ) ) ) {
							actividades.add(act);
						}
					}
				}
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return actividades;
	}

	@Override
	public void eliminarActividad(int id) {
		Cursor cursor = null;
		try {
				myDataBase.execSQL(
						"DELETE FROM Actividades " +
						"WHERE Actividades._id = " + id + " ");
				
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public void actualizarActividad(int id, int pos) {
		Cursor cursor = null;
		try {
				myDataBase.execSQL(
						"UPDATE Actividades SET " +
						"idTipo = " + dataS.getActividades().get(pos).getTipo() + ", " +
						"dia = " + dataS.getActividades().get(pos).getDia() + ", " +
						"mes = " + dataS.getActividades().get(pos).getMes() + ", " +
						"ano = " + dataS.getActividades().get(pos).getAno() + ", " +
						"lugar = '" + dataS.getActividades().get(pos).getLugar() + "', " +
						"startHour = " + dataS.getActividades().get(pos).getBloque().getStarthour() + ", " +
						"startMinute = " + dataS.getActividades().get(pos).getBloque().getStartminute() + ", " +
						"endHour = " + dataS.getActividades().get(pos).getBloque().getEndhour() + ", " +
						"endMinute = " + dataS.getActividades().get(pos).getBloque().getEndminute() + ", " +
						"alarma = " + dataS.getActividades().get(pos).getAlarma() + ", " +
						"porcentaje = " + dataS.getActividades().get(pos).getPorcentaje() + ", " +
						"nota = " + dataS.getActividades().get(pos).getNota() + " " +
						"WHERE Actividades._id = " + id + " ");
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	@Override
	public ArrayList<Academico> obtenerAcademicos() {
		ArrayList<Academico> academicos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Academicos._id, Academicos.nombre, Academicos.oficina, Academicos.anexo, Academicos.correo, Academicos.pagina, Area.nombre, Campus.nombre FROM Academicos " +
					"INNER JOIN Area ON (Area._id = Academicos.idArea) " +
					"INNER JOIN Campus ON (Campus._id = Area.idCampus) "
					, null);
			cursor.moveToFirst();
			academicos = new ArrayList<Academico>();
			while (!cursor.isAfterLast()) {
				Academico acad = new Academico(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
				academicos.add(acad);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return academicos;
	}

	@Override
	public ArrayList<String> obtenerNombresAcademicos() {
		ArrayList<String> academicos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Academicos.nombre FROM Academicos "
					, null);
			cursor.moveToFirst();
			academicos = new ArrayList<String>();
			while (!cursor.isAfterLast()) {
				String acad = cursor.getString(0);
				academicos.add(acad);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return academicos;
	}

	@Override
	public ArrayList<String> buscarNombresAcademicos(String query) {
		ArrayList<String> academicos = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Academicos.nombre FROM Academicos " +
					"WHERE Academicos.nombre LIKE '%" + query + "%' "
					, null);
			cursor.moveToFirst();
			academicos = new ArrayList<String>();
			while (!cursor.isAfterLast()) {
				String acad = cursor.getString(0);
				academicos.add(acad);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return academicos;
	}

	@Override
	public ArrayList<Contacto> obtenerContactos() {
		ArrayList<Contacto> contacts = null;
		Cursor cursor = null;
		try {
			cursor = myDataBase.rawQuery(
					"SELECT Social._id, Social.nombre, Social.data FROM Social "
					, null);
			cursor.moveToFirst();
			contacts = new ArrayList<Contacto>();
			while (!cursor.isAfterLast()) {
				Contacto cont = new Contacto(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				contacts.add(cont);
				cursor.moveToNext();
			}
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return contacts;
	}

	@Override
	public int agregarContacto(Contacto contact) {
		Cursor cursor = null;
		int returnID = 0;
		try {
				myDataBase.execSQL(
						"INSERT INTO Social " +
						"(nombre, data) " +
						"VALUES (" +
						"'" + contact.getNombre() + "'" + ", " +
						"'" + contact.getData() + "'" + ") ");
				
				cursor = myDataBase.rawQuery(
						"SELECT MAX(Social._id) AS currentID FROM Social "
						, null);
				cursor.moveToFirst();
				returnID = cursor.getInt(0);
		} catch (SQLException e) {
			Log.e(TAG_DB, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return returnID;
	}

}