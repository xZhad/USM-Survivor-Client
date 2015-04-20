package cl.edenprime.survivor.first;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import cl.edenprime.survivor.FirstTimeActivity;
import cl.edenprime.survivor.R;
import cl.edenprime.survivor.modelo.Ramo;
import cl.edenprime.survivor.obj.dataS;

public class EnrolledFragment extends Fragment {
	
	// Elementos del Layout
	private LinearLayout content;
    
    /** Lista de Ramos */
	private ArrayList<Ramo> ramos;
	private ArrayList<CheckBox> checkboxRamos;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.firsttime4, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		content = (LinearLayout) getView().findViewById(R.id.FirstCheckBoxesIns);
		content.removeAllViews();
		
		checkboxRamos = new ArrayList<CheckBox>();
		
		ramos = dataS.getServiciosDatos().obtenerRamosFaltantes();
		for (int i = 0; i < ramos.size(); i++) {
			CheckBox box = new CheckBox(getActivity());
			box.setId(ramos.get(i).getId());
			box.setText(ramos.get(i).getNombre());
			box.setTextColor(R.attr.pageText);
			box.setButtonDrawable(R.drawable.check_box);
			checkboxRamos.add(box);
			content.addView(box);
		}

		FirstTimeActivity.working.setVisibility(View.GONE);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveEnrolledRamos();
	}
	
	public void saveEnrolledRamos() {
		ArrayList<Ramo> inscritos = new ArrayList<Ramo>();
		for (int i = 0; i < checkboxRamos.size(); i++) {
			if (checkboxRamos.get(i).isChecked()) {
				inscritos.add(ramos.get(i));
			}
		}
		dataS.getServiciosDatos().inscribirRamos(inscritos);
	}
	
}
