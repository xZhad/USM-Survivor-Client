package cl.edenprime.survivor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import cl.edenprime.survivor.modelo.Blocks;
import cl.edenprime.survivor.modelo.Clase;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class AddTimetableActivity extends Activity {
	
	// Subject
	int subjectID;
	int subjectPOS;
	
	// Elementos del Layout
	private static final int TIME_START_DIALOG_ID = 6666;
	private static final int TIME_END_DIALOG_ID = 7777;
	private Button add;
	private TimePickerDialog.OnTimeSetListener pTimeStartSetListener;
	private TimePickerDialog.OnTimeSetListener pTimeEndSetListener;
	private TextView title;
	private TextView subject;
	private TextView type;
	private TextView day;
	private TextView room;
	private TextView start;
	private TextView end;
	
	// Datos seleccionados
	private int typeS;
	private int dayS;
	private String roomS;
	private int sHourS;
	private int sMinuteS;
	private int eHourS;
	private int eMinuteS;
	
	// Checkear antes de aceptar
	private boolean isType;
	private boolean isDay;
	private boolean isRoom;
	private boolean isStart;
	private boolean isEnd;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.add_timetable);
        
        if (savedInstanceState != null) {
            return;
        }
        
        // Setear elementos del layout
        add = (Button) findViewById(R.id.AddTimetableAdd);
        title = (TextView) findViewById(R.id.AddTimetableTitle);
        subject = (TextView) findViewById(R.id.AddTimetableSubject);
        type = (TextView) findViewById(R.id.AddTimetableType);
        day = (TextView) findViewById(R.id.AddTimetableDay);
        room = (TextView) findViewById(R.id.AddTimetableRoom);
        start = (TextView) findViewById(R.id.AddTimetableStart);
        end = (TextView) findViewById(R.id.AddTimetableEnd);
        
        // Obtener parametros
        Bundle Params = getIntent().getExtras();
		subjectPOS = Params.getInt("SUBJECT");
		if (subjectPOS != -1) {
			subjectID = dataS.getRamosINS().get(subjectPOS).getId();
			subject.setText(dataS.getRamosINS().get(subjectPOS).getNombre());
			subject.setEnabled(false);
		}
		
		// Setear Valores
		title.setText(dataS.getRamosINS().get(subjectPOS).getSigla());
        room.setText("");
        sHourS = 12;
        eHourS = 12;
        
        add.setEnabled(false);
        isType = false;
        isDay = false;
        isRoom = false;
        isStart = false;
        isEnd = false;
    }
	
	public void goSelectType(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_show_list_pressed)
			.setTitle(R.string.timetableTypeSelect)
			.setItems(R.array.timetable_type_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					typeS = which;
					String[] timetable_type_array = getResources().getStringArray(R.array.timetable_type_array);
					type.setText(timetable_type_array[typeS]);
					isType = true;
					addTimetable();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectDay(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_week_pressed)
			.setTitle(R.string.timetableDaySelect)
			.setItems(R.array.days_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dayS = which;
					String[] days_array = getResources().getStringArray(R.array.days_array);
					day.setText(days_array[dayS]);
					isDay = true;
					addTimetable();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectRoom(View v) {
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_mapmode_pressed)
			.setTitle(R.string.timetableRoomSelect)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            roomS = input.getText().toString().trim();
		            room.setText(roomS);
		            if (roomS.equals(""))
		            	isRoom = false;
		            else
		            	isRoom = true;
		            addTimetable();
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectStart(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_show_list_pressed)
			.setTitle(R.string.timetableTimeSelect)
			.setItems(R.array.time_type_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (which == 1) {
						goSelectStartBlock();
					} else {
						goSelectStartHour();
					}
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectEnd(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_show_list_pressed)
			.setTitle(R.string.timetableTimeSelect)
			.setItems(R.array.time_type_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (which == 1) {
						goSelectEndBlock();
					} else {
						goSelectEndHour();
					}
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectStartBlock() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_duration_pressed)
			.setTitle(R.string.timetableStartSelect)
			.setItems(R.array.blockNumbers, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String[] blockStart = getResources().getStringArray(R.array.blockStart);
					sHourS = Integer.valueOf(blockStart[which].split(":")[0]);
					sMinuteS = Integer.valueOf(blockStart[which].split(":")[1]);
					start.setText(blockStart[which]);
					isStart = true;
					addTimetable();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectEndBlock() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_duration_pressed)
			.setTitle(R.string.timetableEndSelect)
			.setItems(R.array.blockNumbers, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String[] blockEnd = getResources().getStringArray(R.array.blockEnd);
					eHourS = Integer.valueOf(blockEnd[which].split(":")[0]);
					eMinuteS = Integer.valueOf(blockEnd[which].split(":")[1]);
					end.setText(blockEnd[which]);
					isEnd = true;
					addTimetable();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectStartHour() {
		pTimeStartSetListener =
            new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					sHourS = hourOfDay;
					sMinuteS = minute;
					String aux;
					if (sMinuteS < 10)
						aux = "0";
					else
						aux = "";
					start.setText(sHourS + ":" + aux + sMinuteS);
					isStart = true;
					addTimetable();
				}
            };
		showDialog(TIME_START_DIALOG_ID);
	}
	
	public void goSelectEndHour() {
		pTimeEndSetListener =
            new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					eHourS = hourOfDay;
					eMinuteS = minute;
					String aux;
					if (sMinuteS < 10)
						aux = "0";
					else
						aux = "";
					end.setText(eHourS + ":" + aux + eMinuteS);
					isEnd = true;
					addTimetable();
				}
            };
		showDialog(TIME_END_DIALOG_ID);
	}
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_START_DIALOG_ID:
            return new TimePickerDialog(this, pTimeStartSetListener, sHourS, sMinuteS, true);
        case TIME_END_DIALOG_ID:
            return new TimePickerDialog(this, pTimeEndSetListener, eHourS, eMinuteS, true);
        }
        return null;
    }
    
    public void goAdd(View v) {
    	Blocks bloque = new Blocks(sHourS, sMinuteS, eHourS, eMinuteS);
    	Clase clase = new Clase(subjectID, typeS, dayS, roomS, bloque);
    	clase.setId(dataS.getServiciosDatos().agregarClase(clase));
    	dataS.getClases().add(clase);
    	finish();
    }
    
    public void addTimetable() {
		if (isType && isDay && isRoom && isStart && isEnd) {
			add.setEnabled(true);
		}
		else {
			add.setEnabled(false);
		}
	}
	
}
