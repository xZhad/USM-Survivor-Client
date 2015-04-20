package cl.edenprime.survivor;

import java.util.ArrayList;

import com.google.gson.Gson;

import cl.edenprime.survivor.modelo.Academico;
import cl.edenprime.survivor.obj.dataS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchableActivity extends Activity 
{
	ListView list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        list = (ListView)findViewById(R.id.searchlist);
        
        Intent intent = getIntent();
        
        // if the activity is created from search
    	if(intent.getAction().equals(Intent.ACTION_SEARCH))
    	{   		
    		// get search query
    		String query = intent.getStringExtra(SearchManager.QUERY);
    		
    		ArrayList<String> items = dataS.getServiciosDatos().buscarNombresAcademicos(query);
    		
    		//bind the list
    		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items));  		
            
            list.setOnItemClickListener(new OnItemClickListener()
            {
            	public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
            	{
            		AlertDialog.Builder adb = new AlertDialog.Builder(SearchableActivity.this);
            		//String nombre = dataS.getServiciosDatos().obtenerAcademicos().get(position).getNombre();
            		
            		adb.setTitle((String) arg0.getItemAtPosition(position));
            		//adb.setTitle(nombre);
            		
            		String oficina  = dataS.getServiciosDatos().obtenerAcademicos().get(position).getOficina();
            		String anexo = dataS.getServiciosDatos().obtenerAcademicos().get(position).getAnexo();
            		String correo = dataS.getServiciosDatos().obtenerAcademicos().get(position).getCorreo();
            		String area = dataS.getServiciosDatos().obtenerAcademicos().get(position).getArea();
            		String campus = dataS.getServiciosDatos().obtenerAcademicos().get(position).getCampus();
            		
            		adb.setMessage("Campus:\t" + campus + "\n" +
            					"Area:\t" + area + "\n" + 
            					"Correo:" + correo + "\n" + 
            					"Anexo:\t" + anexo + "\n" + 
            					"Oficina:\t" + oficina);
            		
            		adb.setPositiveButton("Ok", null);
            		adb.show();                   
                }
            });
            
    	}
    	
        //activity created normally
        else{
        	ArrayList items = dataS.getServiciosDatos().obtenerNombresAcademicos();
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,items);
            list.setAdapter(adapter);
        }
  
	}	//final onCreate	
}	//final SearchableActivity
