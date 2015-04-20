package cl.edenprime.survivor;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import cl.edenprime.survivor.dao.WebServices;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.obj.ThemeSwitcher;

public class ViewPersonalActivity extends Activity {
	
	// Elementos del Layout
	private LinearLayout calendarContent;
	
	private ArrayList<Actividad> actividades;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.view_personal);
        
        if (savedInstanceState != null) {
            return;
        }
        
        calendarContent = (LinearLayout) findViewById(R.id.ViewPersonalOnlineCalendarList2);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		calendarContent.removeAllViews();
		loadCalendar();
	}
	
	public void loadCalendar() {
		WebServices web = new WebServices();
		actividades = web.loadPersonalOnline();
		if (actividades == null)
			actividades = new ArrayList<Actividad>();
		
		for (int i = 0; i < actividades.size(); i++) {
			
			String aux;
			String aux0;
			String aux1;
			View vista = View.inflate(this, R.layout.calendar_personal_list_boxes, null);

			TextView name = (TextView) vista.findViewById(R.id.CalendarPersonalListName);
			TextView room = (TextView) vista.findViewById(R.id.CalendarPersonalListRoom);
			TextView start = (TextView) vista.findViewById(R.id.CalendarPersonalListStart);
			TextView date = (TextView) vista.findViewById(R.id.CalendarPersonalListDate);
			TextView end = (TextView) vista.findViewById(R.id.CalendarPersonalListEnd);
			TableLayout layout = (TableLayout) vista.findViewById(R.id.CalendarPersonalListClick);
			
			name.setText(actividades.get(i).getNombre());
			
			room.setText(actividades.get(i).getLugar());
			
			if (actividades.get(i).getBloque().getStartminute() < 10) aux = "0";
			else aux = "";
			start.setText(actividades.get(i).getBloque().getStarthour() + ":" + aux + actividades.get(i).getBloque().getStartminute());
			
			if (actividades.get(i).getDia() < 10) aux0 = "0";
			else aux0 = "";
			if (actividades.get(i).getMes() < 9) aux1 = "0";
			else aux1 = "";
			int auxMonth = actividades.get(i).getMes()+1;
			date.setText(aux0 + actividades.get(i).getDia() + " / " + aux1 + auxMonth + " / " + actividades.get(i).getAno());
			
			if (actividades.get(i).getBloque().getEndminute() < 10) aux = "0";
			else aux = "";
			end.setText(actividades.get(i).getBloque().getEndhour() + ":" + aux + actividades.get(i).getBloque().getEndminute());
			
			layout.setTag(actividades.get(i).getId());
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					goSelectAction(v);
				}
			});
			calendarContent.addView(vista);
			
		}
	}
	
	public void goSelectAction(final View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setItems(R.array.online_action_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
			        case 0:
			            goEdit(v);
			            break;
			        case 1:
			            goShare(v);
			            break;
			        }
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goEdit(View v) {
		goEditOnlineCalendar(Integer.valueOf(v.getTag().toString()));
	}
	
	public void goShare(final View v) {
		final EditText input = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.personalOnlineShare)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            String share = input.getText().toString().trim();
		            int id = Integer.valueOf(v.getTag().toString());
		            WebServices web = new WebServices();
		            web.sharePersonalOnline(id, share);
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goAddOnlineCalendar(View v) {
		Intent intent = new Intent(this, AddCalendarOnlineActivity.class);
		intent.putExtra("EDIT", -1);
		startActivity(intent);
	}
	
	public void goEditOnlineCalendar(int activityID) {
		Intent intent = new Intent(this, AddCalendarOnlineActivity.class);
		intent.putExtra("EDIT", activityID);
		startActivity(intent);
	}
	
}
