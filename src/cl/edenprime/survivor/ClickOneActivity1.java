package cl.edenprime.survivor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cl.edenprime.survivor.obj.dataS;

public class ClickOneActivity1 extends Activity{

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		finish();
		Intent intent = new Intent(this, AddCalendarActivity.class);
		intent.putExtra("SUBJECT", obtenerPosRamoPorId(dataS.getActividadesCercanas().get(0).getIdRamo()));
		intent.putExtra("EDIT", obtenerPosActividadPorId(dataS.getActividadesCercanas().get(0).getId()));
		this.startActivity(intent);

	}
	

	
	public int obtenerPosRamoPorId(int id) {
		for (int i = 0; i < dataS.getRamosINS().size(); i ++) {
			if (dataS.getRamosINS().get(i).getId() == id) {
				return i;
			}
		}
		return 0;
	}
	
	public int obtenerPosActividadPorId(int id) {
		for (int i = 0; i < dataS.getActividades().size(); i ++) {
			if (dataS.getActividades().get(i).getId() == id) {
				return i;
			}
		}
		return 0;
	}
	
	
}
