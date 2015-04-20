package cl.edenprime.survivor;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import cl.edenprime.survivor.dao.WebServices;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.modelo.Blocks;
import cl.edenprime.survivor.obj.ThemeSwitcher;

public class AddCalendarOnlineActivity extends Activity {
	
	// Activity
	int activityID;
	Actividad actividad;
	
	// Elementos del Layout
	private static final int TIME_START_DIALOG_ID = 8888;
	private static final int TIME_END_DIALOG_ID = 9999;
	private static final int DAY_DIALOG_ID = 6969;
	private TextView title;
	private Button add;
	private Button editButton;
	private Button deleteButton;
	private TimePickerDialog.OnTimeSetListener pTimeStartSetListener;
	private TimePickerDialog.OnTimeSetListener pTimeEndSetListener;
	private DatePickerDialog.OnDateSetListener pDateSetListener;
	private EditText name;
	private EditText room;
	private EditText date;
	private EditText alarm;
	private EditText start;
	private EditText end;
	
	// Datos seleccionados
	private String nameS;
	private String roomS;
	private int dateS;
	private int dayS;
	private int monthS;
	private int yearS;
	private int alarmS;
	private int sHourS;
	private int sMinuteS;
	private int eHourS;
	private int eMinuteS;
	
	// Checkear antes de aceptar
	private boolean edit;
	private boolean isName;
	private boolean isRoom;
	private boolean isDate;
	private boolean isAlarm;
	private boolean isStart;
	private boolean isEnd;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.add_calendar_online);
        
        if (savedInstanceState != null) {
            return;
        }
        
        
        // Setear elementos del layout
        title = (TextView) findViewById(R.id.AddCalendarOnlineTitle);
        add = (Button) findViewById(R.id.AddCalendarOnlineAdd);
        editButton = (Button) findViewById(R.id.AddCalendarOnlineEdit);
        deleteButton = (Button) findViewById(R.id.AddCalendarOnlineDelete);
        name = (EditText) findViewById(R.id.AddCalendarOnlineName);
        room = (EditText) findViewById(R.id.AddCalendarOnlineRoom);
        date = (EditText) findViewById(R.id.AddCalendarOnlineDate);
        alarm = (EditText) findViewById(R.id.AddCalendarOnlineAlarm);
        start = (EditText) findViewById(R.id.AddCalendarOnlineStart);
        end = (EditText) findViewById(R.id.AddCalendarOnlineEnd);
        
        // Setear Tokens
        edit = false;
        add.setVisibility(View.GONE);
        isName = false;
        isRoom = false;
        isDate = false;
        isAlarm = false;
        isStart = false;
        isEnd = false;
        
        // Obtener parametros
        Bundle Params = getIntent().getExtras();
		
		if (Params.getInt("EDIT") == -1) {
			editButton.setVisibility(View.GONE);
			deleteButton.setVisibility(View.GONE);
			// Setear Valores Iniciales
	        room.setText("");
	        sHourS = 12;
	        eHourS = 12;
	        alarmS = -1;
	        final Calendar cal = Calendar.getInstance();
	        yearS = cal.get(Calendar.YEAR);
	        monthS = cal.get(Calendar.MONTH);
	        dayS = cal.get(Calendar.DAY_OF_MONTH);
		}
		else {
			activityID = Params.getInt("EDIT");
			edit = true;
			
			// Obtener Actividad
			WebServices web = new WebServices();
			ArrayList<Actividad> actividades = web.loadPersonalOnline();
			
			for (int i = 0; i < actividades.size(); i ++) {
				if (actividades.get(i).getId() == activityID) {
					actividad = actividades.get(i);
				}
			}
			
			// Setear Valores Iniciales
			nameS = actividad.getNombre();
			name.setText(nameS);
			title.setText(nameS);
			
            yearS = actividad.getAno();
            monthS = actividad.getMes();
            dayS = actividad.getDia();
			String aux0;
			if (dayS < 10) aux0 = "0";
			else aux0 = "";
			String aux1;
			if (monthS < 9) aux1 = "0";
			else aux1 = "";
			int auxMonth = monthS+1;
			date.setText(aux0 + dayS + " / " + aux1 + auxMonth + " / " + yearS);
			
			roomS = actividad.getLugar();
            room.setText(roomS);
			
            alarmS = actividad.getAlarma();
            if (alarmS == -1)
            	alarm.setText(getResources().getString(R.string.calendarAlarm) + " NO");
            else
				alarm.setText(getResources().getString(R.string.calendarAlarm) + " " + alarmS);
            
            sHourS = actividad.getBloque().getStarthour();
			sMinuteS = actividad.getBloque().getStartminute();
			String auxS;
			if (sMinuteS < 10) auxS = "0";
			else auxS = "";
			start.setText(sHourS + ":" + auxS + sMinuteS);
			
			eHourS = actividad.getBloque().getEndhour();
			eMinuteS = actividad.getBloque().getEndminute();
			String auxE;
			if (eMinuteS < 10) auxE = "0";
			else auxE = "";
			end.setText(eHourS + ":" + auxE + eMinuteS);
		}
    }
	
	public void goSelectName(View v) {
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.calendarNameSelect)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            nameS = input.getText().toString().trim();
		            name.setText(nameS);
		            if (nameS.equals(""))
		            	isName = false;
		            else
		            	isName = true;
		            addCalendar();
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
			.setTitle(R.string.calendarRoomSelect)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            roomS = input.getText().toString().trim();
		            room.setText(roomS);
		            if (roomS.equals(""))
		            	isRoom = false;
		            else
		            	isRoom = true;
		            addCalendar();
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectDay(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.calendarDateSelect)
			.setItems(R.array.days_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dateS = which;
					String[] days_array = getResources().getStringArray(R.array.days_array);
					date.setText(days_array[dateS]);
					isDate = true;
					addCalendar();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectStart(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.calendarTimeSelect)
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
			.setTitle(R.string.calendarTimeSelect)
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
			.setTitle(R.string.calendarStartSelect)
			.setItems(R.array.blockNumbers, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String[] blockStart = getResources().getStringArray(R.array.blockStart);
					sHourS = Integer.valueOf(blockStart[which].split(":")[0]);
					sMinuteS = Integer.valueOf(blockStart[which].split(":")[1]);
					start.setText(blockStart[which]);
					isStart = true;
					addCalendar();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectEndBlock() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.calendarEndSelect)
			.setItems(R.array.blockNumbers, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String[] blockEnd = getResources().getStringArray(R.array.blockEnd);
					eHourS = Integer.valueOf(blockEnd[which].split(":")[0]);
					eMinuteS = Integer.valueOf(blockEnd[which].split(":")[1]);
					end.setText(blockEnd[which]);
					isEnd = true;
					addCalendar();
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
					addCalendar();
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
					addCalendar();
				}
            };
		showDialog(TIME_END_DIALOG_ID);
	}
	
	public void goSelectDate(View v) {
		pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					yearS = year;
                    monthS = monthOfYear;
                    dayS = dayOfMonth;
					String aux0;
					if (dayS < 10)
						aux0 = "0";
					else
						aux0 = "";
					String aux1;
					if (monthS < 9)
						aux1 = "0";
					else
						aux1 = "";
					int auxMonth = monthS+1;
					date.setText(aux0 + dayS + " / " + aux1 + auxMonth + " / " + yearS);
					isDate = true;
					addCalendar();
				}
            };
		showDialog(DAY_DIALOG_ID);
	}
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case TIME_START_DIALOG_ID:
            return new TimePickerDialog(this, pTimeStartSetListener, sHourS, sMinuteS, true);
        case TIME_END_DIALOG_ID:
            return new TimePickerDialog(this, pTimeEndSetListener, eHourS, eMinuteS, true);
        case DAY_DIALOG_ID:
            return new DatePickerDialog(this, pDateSetListener, yearS, monthS, dayS);
        }
        return null;
    }
    
    public void goSelectAlarm(View v) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.calendarAlarmSelect1)
			.setItems(R.array.YesNo, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (which == 1) {
						alarm.setText(getResources().getString(R.string.calendarAlarm) + " NO");
						alarmS = -1;
						isAlarm = true;
						addCalendar();
					} else {
						goSelectAlarmMinutes();
					}
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
    
    public void goSelectAlarmMinutes() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setTitle(R.string.calendarAlarmSelect2)
			.setItems(R.array.alarmMinutes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String[] alarmMinutes = getResources().getStringArray(R.array.alarmMinutes);
					alarmS = Integer.valueOf(alarmMinutes[which]);
					alarm.setText(getResources().getString(R.string.calendarAlarm) + " " + alarmS);
					isAlarm = true;
					addCalendar();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
    }
    
    public void goAdd(View v) {
    	Blocks bloque = new Blocks(sHourS, sMinuteS, eHourS, eMinuteS);
    	Actividad actividad = new Actividad(dayS, monthS, yearS, roomS, bloque, nameS, alarmS);
    	WebServices web = new WebServices();
    	web.addPersonalOnline(actividad);
    	finish();
    }
    
    public void goEdit(View v) {
    	Blocks bloque = new Blocks(sHourS, sMinuteS, eHourS, eMinuteS);
    	Actividad actividad = new Actividad(dayS, monthS, yearS, roomS, bloque, nameS, alarmS);
    	actividad.setId(this.activityID);
    	WebServices web = new WebServices();
    	web.updatePersonalOnline(actividad);
    	finish();
    }
    
    public void goDelete(View v) {
    	WebServices web = new WebServices();
    	web.deletePersonalOnline(this.activityID);
    	finish();
    }
    
    public void addCalendar() {
    	if (edit == false) {
			if (isName && isDate && isRoom && isAlarm && isStart && isEnd) {
				add.setVisibility(View.VISIBLE);
			}
			else {
				add.setVisibility(View.GONE);
			}
    	}
	}
	
}
