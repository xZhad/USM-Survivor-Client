package cl.edenprime.survivor.first;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import cl.edenprime.survivor.FirstTimeActivity;
import cl.edenprime.survivor.R;
import cl.edenprime.survivor.modelo.Campus;
import cl.edenprime.survivor.modelo.Carrera;
import cl.edenprime.survivor.modelo.Departamento;
import cl.edenprime.survivor.obj.dataS;

public class CareerFragment extends Fragment {
    
    /** Lista de Campus existentes. */
	private ArrayList<Campus> campus;
	private Spinner spinnerCampus;
	
	/** Lista de Departamentos existentes. */
	private ArrayList<Departamento> departamentos;
	private Spinner spinnerDepartamentos;
	
	/** Lista de Carreas existentes. */
	private ArrayList<Carrera> carreras;
	private Spinner spinnerCarreras;
	
	// Seleccionados
	private int Scampus;
	private int Sdepartamento;
	private int Scarrera;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.firsttime2, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		spinnerCampus = (Spinner) getView().findViewById(R.id.SpinnerCampus);
        spinnerDepartamentos = (Spinner) getView().findViewById(R.id.SpinnerDepartamentos);
        spinnerCarreras = (Spinner) getView().findViewById(R.id.SpinnerCarreras);
        
        setCampus();
        
        FirstTimeActivity.working.setVisibility(View.GONE);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveCarrera();
	}
	
	public void setCampus() {
        campus = dataS.getServiciosDatos().obtenerCampus();
        
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        for (int i = 0; i < campus.size(); i++) {
        	adapter.add(campus.get(i).getNombre());
        }
        
        spinnerCampus.setAdapter(adapter);
        
        spinnerCampus.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	Scampus = campus.get(position).getId();
                setDepartamentos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        
	}
	
	public void setDepartamentos() {
		departamentos = dataS.getServiciosDatos().obtenerDepartamentos();
        
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        for (int i = 0; i < departamentos.size(); i++) {
        	adapter.add(departamentos.get(i).getNombre());
        }
        
        spinnerDepartamentos.setAdapter(adapter);
        
        spinnerDepartamentos.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	Sdepartamento = departamentos.get(position).getId();
                setCarreras();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
	}
	
	public void setCarreras() {
		carreras = dataS.getServiciosDatos().obtenerCarreras(Scampus, Sdepartamento);
        
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        for (int i = 0; i < carreras.size(); i++) {
        	adapter.add(carreras.get(i).getNombre());
        }
        
        spinnerCarreras.setAdapter(adapter);
        
        spinnerCarreras.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	Scarrera = carreras.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
	}
	
	public void saveCarrera() {
		dataS.setId_campus(Scampus);
		dataS.setId_departamento(Sdepartamento);
		dataS.setId_carrera(Scarrera);
	}
	
}
