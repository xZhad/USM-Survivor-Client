package cl.edenprime.survivor.dao;

import java.util.ArrayList;

import cl.edenprime.survivor.modelo.Academico;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.modelo.Campus;
import cl.edenprime.survivor.modelo.Carrera;
import cl.edenprime.survivor.modelo.Clase;
import cl.edenprime.survivor.modelo.Contacto;
import cl.edenprime.survivor.modelo.Departamento;
import cl.edenprime.survivor.modelo.Ramo;


public interface ServiciosDeDatos {
	
	/**
     * Obtencion de Academicos.
     * @return Retorna una lista con todos los academicos.
     */
    ArrayList<Academico> obtenerAcademicos();
    
    /**
     * Obtencion de Nombres de Academicos.
     * @return Retorna una lista con todos los nombres de academicos.
     */
    ArrayList<String> obtenerNombresAcademicos();
    
    /**
     * Búsqueda de Nombres de Academicos.
     * @param query texto a buscar.
     * @return Retorna una lista con todos los nombres de academicos encontrados.
     */
    ArrayList<String> buscarNombresAcademicos(String query);
    
	/**
     * Obtencion de Campus.
     * @return Retorna una lista con todos los Campus.
     */
    ArrayList<Campus> obtenerCampus();
    
    /**
     * Obtencion de Campus.
     * @param id_campus el id del campus a buscar.
     * @return Retorna el Campus con id_campus.
     */
    Campus obtenerCampus(int id_campus);
    
    /**
     * Obtencion de Departamentos.
     * @return Retorna una lista con todos los Departamentos del campus.
     */
    ArrayList<Departamento> obtenerDepartamentos();
    
    /**
     * Obtencion de Departamento.
     * @param id_departamento el id del campus a buscar.
     * @return Retorna el Departamento con id_departamento.
     */
    Departamento obtenerDepartamento(int id_departamento);
    
    /**
     * Obtencion de Carreras.
     * @param id_campus el id del campus.
     * @param id_departamento el id del departamento.
     * @return Retorna una lista con todos las Carreras del departamento del campus.
     */
    ArrayList<Carrera> obtenerCarreras(int id_campus, int id_departamento);
    
    /**
     * Obtencion de Carrera.
     * @param id_carrera el id de la carrera a buscar.
     * @return Retorna la Carrera con id_carrera.
     */
    Carrera obtenerCarrera(int id_carrera);
    
    /**
     * Obtencion de Ramo.
     * @return Retorna un Ramo.
     */
    Ramo obtenerRamo(int id_ramo);
    
    /**
     * Obtencion de Ramos.
     * @return Retorna una lista con todos los Ramos de la carrera.
     */
    ArrayList<Ramo> obtenerRamos();
    
    /**
     * Obtencion de Ramos Aprobados.
     * @return Retorna una lista con todos los Ramos Aprobados.
     */
    ArrayList<Ramo> obtenerRamosAprobados();
    
    /**
     * Obtencion de Ramos Inscritos.
     * @return Retorna una lista con todos los Ramos Inscritos.
     */
    ArrayList<Ramo> obtenerRamosInscritos();
    
    /**
     * Obtencion de Ramos Faltantes.
     * @return Retorna una lista con todos los Ramos Faltantes para terminar la carrera.
     */
    ArrayList<Ramo> obtenerRamosFaltantes();
    
    /**
     * Marcar Ramos como Aprobados.
     * @param aprobados lista de ramos aprobados.
     */
    void aprobarRamos(ArrayList<Ramo> aprobados);
    
    /**
     * Marcar Ramos como Inscritos.
     * @param inscritos lista de ramos inscritos.
     */
    void inscribirRamos(ArrayList<Ramo> inscritos);
    
    /**
     * Marcar Ramos como Desinscritos.
     * @param desinscritos lista de ramos desinscritos.
     */
    void desinscribirRamos(ArrayList<Ramo> desinscritos);
    
    /**
     * Agrega una Clase al horario.
     * @param clase clase a agregar al horario.
     */
    int agregarClase(Clase clase);
    /**
     * Obtencion de Clases del horario.
     * @return Retorna una lista con todas las Clases del horario.
     */
    ArrayList<Clase> obtenerClases();
    
    /**
     * Agrega una Actividad al calendario.
     * @param actividad actividad a agregar al calendario.
     */
    int agregarActividad(Actividad actividad);
    
    /**
     * Obtencion de Actividades del calendario.
     * @return Retorna una lista con todas las Actividades del calendario.
     */
    ArrayList<Actividad> obtenerActividades();
    
    /**
     * Obtencion de 3 Actividades cercanas del calendario.
     * @return Retorna una lista con 3 Actividades cercanas del calendario.
     */
    ArrayList<Actividad> obtenerActividadesCercanas();
    
    /**
     * Elimina la Actividad.
     * @param id Id de la actividad a eliminar.
     */
    void eliminarActividad(int id);
    
    /**
     * Actualiza la Actividad.
     * @param id Id de la actividad a actualizar.
     * @param pos Posicion en el Array de la actividad a actualizar.
     */
    void actualizarActividad(int id, int pos);
    
    /**
     * Obtiene todos los contactos.
     * @return Retorna una lista con todos los contactos.
     */
    ArrayList<Contacto> obtenerContactos();
    
    /**
     * Agrega un contacto.
     * @param contacto a agregar.
     * @return Retorna el id del contacto agregado.
     */
    int agregarContacto(Contacto contact);
    
    /**
     * Inicio del servicio de datos.
     */
    void iniciar();

}
