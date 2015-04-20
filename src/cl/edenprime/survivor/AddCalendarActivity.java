package cl.edenprime.survivor;

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
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.modelo.Blocks;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class AddCalendarActivity extends Activity {
	
	// Subject
	int subjectID;
	int subjectPOS;
	
	// Activity
	int activityID;
	int activityPOS;
	
	// Elementos del Layout
	private static final int TIME_START_DIALOG_ID = 8888;
	private static final int TIME_END_DIALOG_ID = 9999;
	private static final int DAY_DIALOG_ID = 6969;
	private Button add;
	private Button editButton;
	private Button deleteButton;
	private TimePickerDialog.OnTimeSetListener pTimeStartSetListener;
	private TimePickerDialog.OnTimeSetListener pTimeEndSetListener;
	private DatePickerDialog.OnDateSetListener pDateSetListener;
	private TextView title;
	private EditText subject;
	private EditText type;
	private EditText percent;
	private EditText date;
	private EditText room;
	private EditText alarm;
	private EditText start;
	private EditText end;
	private EditText grade;
	
	// Datos seleccionados
	private int typeS;
	private int dateS;
	private String roomS;
	private int dayS;
	private int monthS;
	private int yearS;
	private int alarmS;
	private int percentS;
	private int sHourS;
	private int sMinuteS;
	private int eHourS;
	private int eMinuteS;
	private int gradeS;
	
	// Checkear antes de aceptar
	private boolean edit;
	private boolean isType;
	private boolean isDate;
	private boolean isRoom;
	private boolean isAlarm;
	private boolean isStart;
	private boolean isEnd;
	private boolean isPercent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.add_calendar);
        
        if (savedInstanceState != null) {
            return;
        }
        
        
        // Setear elementos del layout
        add = (Button) findViewById(R.id.AddCalendarAdd);
        editButton = (Button) findViewById(R.id.AddCalendarEdit);
        deleteButton = (Button) findViewById(R.id.AddCalendarDelete);
        title = (TextView) findViewById(R.id.AddCalendarTitle);
        subject = (EditText) findViewById(R.id.AddCalendarSubject);
        type = (EditText) findViewById(R.id.AddCalendarType);
        percent = (EditText) findViewById(R.id.AddCalendarPercent);
        date = (EditText) findViewById(R.id.AddCalendarDate);
        room = (EditText) findViewById(R.id.AddCalendarRoom);
        alarm = (EditText) findViewById(R.id.AddCalendarAlarm);
        start = (EditText) findViewById(R.id.AddCalendarStart);
        end = (EditText) findViewById(R.id.AddCalendarEnd);
        grade = (EditText) findViewById(R.id.AddCalendarGrade);
        
        // Setear Tokens
        edit = false;
        add.setVisibility(View.GONE);
        isType = false;
        isDate = false;
        isRoom = false;
        isAlarm = false;
        isStart = false;
        isEnd = false;
        isPercent = false;
        
        // Obtener parametros
        Bundle Params = getIntent().getExtras();
        
		subjectPOS = Params.getInt("SUBJECT");
		if (subjectPOS != -1) {
			subjectID = dataS.getRamosINS().get(subjectPOS).getId();
			subject.setText(dataS.getRamosINS().get(subjectPOS).getNombre());
			subject.setEnabled(false);
		}
		
		if (Params.getInt("EDIT") == -1) {
			editButton.setVisibility(View.GONE);
			deleteButton.setVisibility(View.GONE);
			// Setear Valores Iniciales
			title.setText(dataS.getRamosINS().get(subjectPOS).getSigla());
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
			activityPOS = Params.getInt("EDIT");
			activityID = dataS.getActividades().get(activityPOS).getId();
			edit = true;
			// Setear Valores Iniciales
			title.setText(dataS.getRamosINS().get(subjectPOS).getSigla());
			
			typeS = dataS.getActividades().get(activityPOS).getTipo();
			String[] calendar_type_array = getResources().getStringArray(R.array.calendar_type_array);
			type.setText(calendar_type_array[typeS]);
			
			percentS = dataS.getActividades().get(activityPOS).getPorcentaje();
            percent.setText(percentS+" %");
			
            yearS = dataS.getActividades().get(activityPOS).getAno();
            monthS = dataS.getActividades().get(activityPOS).getMes();
            dayS = dataS.getActividades().get(activityPOS).getDia();
			String aux0;
			if (dayS < 10) aux0 = "0";
			else aux0 = "";
			String aux1;
			if (monthS < 9) aux1 = "0";
			else aux1 = "";
			int auxMonth = monthS+1;
			date.setText(aux0 + dayS + " / " + aux1 + auxMonth + " / " + yearS);
			
			roomS = dataS.getActividades().get(activityPOS).getLugar();
            room.setText(roomS);
			
            alarmS = dataS.getActividades().get(activityPOS).getAlarma();
            if (alarmS == -1)
            	alarm.setText(getResources().getString(R.string.calendarAlarm) + " NO");
            else
				alarm.setText(getResources().getString(R.string.calendarAlarm) + " " + alarmS);
            
            sHourS = dataS.getActividades().get(activityPOS).getBloque().getStarthour();
			sMinuteS = dataS.getActividades().get(activityPOS).getBloque().getStartminute();
			String auxS;
			if (sMinuteS < 10) auxS = "0";
			else auxS = "";
			start.setText(sHourS + ":" + auxS + sMinuteS);
			
			eHourS = dataS.getActividades().get(activityPOS).getBloque().getEndhour();
			eMinuteS = dataS.getActividades().get(activityPOS).getBloque().getEndminute();
			String auxE;
			if (sMinuteS < 10) auxE = "0";
			else auxE = "";
			end.setText(eHourS + ":" + auxE + eMinuteS);
			
			grade.setVisibility(View.VISIBLE);
			gradeS = dataS.getActividades().get(activityPOS).getNota();
			if (gradeS != -1)
				grade.setText(getResources().getString(R.string.calendarGrade) + " " + gradeS);
		}
    }
	
	public void goSelectSubject(View v) {
		
	}
	
	public void goSelectType(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_show_list_pressed)
			.setTitle(R.string.calendarTypeSelect)
			.setItems(R.array.calendar_type_array, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					typeS = which;
					String[] calendar_type_array = getResources().getStringArray(R.array.calendar_type_array);
					type.setText(calendar_type_array[typeS]);
					isType = true;
					addCalendar();
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectPercent(View v) {
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_mapmode_pressed)
			.setTitle(R.string.calendarPercentSelect)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            percentS = Integer.valueOf(input.getText().toString().trim());
		            percent.setText(percentS+" %");
		            isPercent = true;
		            addCalendar();
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectGrade(View v) {
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_mapmode_pressed)
			.setTitle(R.string.calendarGradeSelect)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            gradeS = Integer.valueOf(input.getText().toString().trim());
		            grade.setText(getResources().getString(R.string.calendarGrade) + " " + gradeS);
		            addCalendar();
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void goSelectDay(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_week_pressed)
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
	
	public void goSelectRoom(View v) {
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_mapmode_pressed)
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
	
	public void goSelectStart(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder
			.setIcon(R.drawable.ic_menu_show_list_pressed)
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
			.setIcon(R.drawable.ic_menu_show_list_pressed)
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
			.setIcon(R.drawable.ic_menu_duration_pressed)
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
			.setIcon(R.drawable.ic_menu_duration_pressed)
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
			.setIcon(R.drawable.ic_menu_show_list_pressed)
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
			.setIcon(R.drawable.ic_menu_duration_pressed)
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
    	Actividad actividad = new Actividad(subjectID, typeS, dayS, monthS, yearS, roomS, bloque, alarmS, percentS);
    	actividad.setId(dataS.getServiciosDatos().agregarActividad(actividad));
    	dataS.getActividades().add(actividad);
    	finish();
    }
    
    public void goEdit(View v) {
    	Blocks bloque = new Blocks(sHourS, sMinuteS, eHourS, eMinuteS);
    	Actividad actividad = new Actividad(subjectID, typeS, dayS, monthS, yearS, roomS, bloque, alarmS, percentS);
    	actividad.setNota(gradeS);
    	dataS.getActividades().set(activityPOS, actividad);
    	dataS.getServiciosDatos().actualizarActividad(activityID, activityPOS);
    	finish();
    }
    
    public void goDelete(View v) {
    	dataS.getActividades().remove(activityPOS);
    	dataS.getServiciosDatos().eliminarActividad(activityID);
    	finish();
    }
    
    public void addCalendar() {
    	if (edit == false) {
			if (isType && isDate && isRoom && isAlarm && isStart && isEnd && isPercent) {
				add.setVisibility(View.VISIBLE);
			}
			else {
				add.setVisibility(View.GONE);
			}
    	}
	}
	
}
