package cl.edenprime.survivor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class ViewSubjectActivity extends Activity {
	
	// Subject
	int subjectID;
	int subjectPOS;
	
	// Elementos del Layout
	private TextView title;
	private TextView nombre;
	private Button addTimetable;
	private Button addCalendar;
	private LinearLayout timetableContent;
	private LinearLayout calendarContent;
	private LinearLayout calculatorContent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.view_subject);
        
        if (savedInstanceState != null) {
            return;
        }
        
        // Setear elementos del layout
        title = (TextView) findViewById(R.id.ViewSubjectTitle);
        nombre = (TextView) findViewById(R.id.ViewSubjectNombre);
        addTimetable = (Button) findViewById(R.id.ViewSubjectAddTimetable);
        addCalendar = (Button) findViewById(R.id.ViewSubjectAddCalendar);
        timetableContent = (LinearLayout) findViewById(R.id.ViewSubjectTimetableList2);
        calendarContent = (LinearLayout) findViewById(R.id.ViewSubjectCalendarList2);
        calculatorContent = (LinearLayout) findViewById(R.id.ViewSubjectCalculatorList2);
        
        // Obtener parametros
        Bundle Params = getIntent().getExtras();
		subjectPOS = Params.getInt("SUBJECT");
		subjectID = dataS.getRamosINS().get(subjectPOS).getId();
		
		// Setear Valores
		title.setText(dataS.getRamosINS().get(subjectPOS).getSigla());
		nombre.setText(dataS.getRamosINS().get(subjectPOS).getNombre());
		addTimetable.setTag(subjectPOS);
		addCalendar.setTag(subjectPOS);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		timetableContent.removeAllViews();
		calendarContent.removeAllViews();
		calculatorContent.removeAllViews();
		loadTimetable();
		loadCalendar();
		loadCalculator();
	}
	
	public void loadTimetable() {
		for (int i = 0; i < dataS.getClases().size(); i++) {
			if (dataS.getClases().get(i).getIdRamo() == subjectID) {
				String aux;
				View vista = View.inflate(this, R.layout.times_list_boxes, null);
				TextView start = (TextView) vista.findViewById(R.id.TimeListStart);
				TextView day = (TextView) vista.findViewById(R.id.TimeListDay);
				TextView end = (TextView) vista.findViewById(R.id.TimeListEnd);
				TextView room = (TextView) vista.findViewById(R.id.TimeListRoom);
				TextView type = (TextView) vista.findViewById(R.id.TimeListType);
				TextView subject = (TextView) vista.findViewById(R.id.TimeListSubject);
				TableLayout layout = (TableLayout) vista.findViewById(R.id.TimeListClick);
				
				if (dataS.getClases().get(i).getBloque().getStartminute() < 10) aux = "0";
				else aux = "";
				start.setText(dataS.getClases().get(i).getBloque().getStarthour() + ":" + aux + dataS.getClases().get(i).getBloque().getStartminute());
				
				String[] days_array = getResources().getStringArray(R.array.days_array);
				day.setText(days_array[dataS.getClases().get(i).getDia()]);
				
				if (dataS.getClases().get(i).getBloque().getEndminute() < 10) aux = "0";
				else aux = "";
				end.setText(dataS.getClases().get(i).getBloque().getEndhour() + ":" + aux + dataS.getClases().get(i).getBloque().getEndminute());
				
				room.setText(dataS.getClases().get(i).getSala());
				
				String[] timetable_type_array = getResources().getStringArray(R.array.timetable_type_array);
				type.setText(timetable_type_array[dataS.getClases().get(i).getTipo()]);
				
				subject.setText(dataS.getRamosINS().get(subjectPOS).getSigla());
				
				layout.setTag(i);
				layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
					}
				});
				timetableContent.addView(vista);
			}
		}
	}
	
	public void loadCalendar() {
		for (int i = 0; i < dataS.getActividades().size(); i++) {
			if (dataS.getActividades().get(i).getIdRamo() == subjectID) {
				String aux;
				String aux0;
				String aux1;
				View vista = View.inflate(this, R.layout.calendar_list_boxes, null);
				TextView start = (TextView) vista.findViewById(R.id.CalendarListStart);
				TextView date = (TextView) vista.findViewById(R.id.CalendarListDate);
				TextView end = (TextView) vista.findViewById(R.id.CalendarListEnd);
				TextView room = (TextView) vista.findViewById(R.id.CalendarListRoom);
				TextView type = (TextView) vista.findViewById(R.id.CalendarListType);
				TextView subject = (TextView) vista.findViewById(R.id.CalendarListSubject);
				TextView percent = (TextView) vista.findViewById(R.id.CalendarListPercent);
				TextView grade = (TextView) vista.findViewById(R.id.CalendarListGrade);
				TableLayout layout = (TableLayout) vista.findViewById(R.id.CalendarListClick);
				
				if (dataS.getActividades().get(i).getBloque().getStartminute() < 10) aux = "0";
				else aux = "";
				start.setText(dataS.getActividades().get(i).getBloque().getStarthour() + ":" + aux + dataS.getActividades().get(i).getBloque().getStartminute());
				
				if (dataS.getActividades().get(i).getDia() < 10) aux0 = "0";
				else aux0 = "";
				if (dataS.getActividades().get(i).getMes() < 9) aux1 = "0";
				else aux1 = "";
				int auxMonth = dataS.getActividades().get(i).getMes()+1;
				date.setText(aux0 + dataS.getActividades().get(i).getDia() + " / " + aux1 + auxMonth + " / " + dataS.getActividades().get(i).getAno());
				
				if (dataS.getActividades().get(i).getBloque().getEndminute() < 10) aux = "0";
				else aux = "";
				end.setText(dataS.getActividades().get(i).getBloque().getEndhour() + ":" + aux + dataS.getActividades().get(i).getBloque().getEndminute());
				
				room.setText(dataS.getActividades().get(i).getLugar());
				
				String[] calendar_type_array = getResources().getStringArray(R.array.calendar_type_array);
				type.setText(calendar_type_array[dataS.getActividades().get(i).getTipo()]);
				
				subject.setText(dataS.getRamosINS().get(subjectPOS).getSigla());
				
				percent.setText(dataS.getActividades().get(i).getPorcentaje() + " %");
				
				if (dataS.getActividades().get(i).getNota() == -1)
					grade.setText("?");
				else
					grade.setText(dataS.getActividades().get(i).getNota() + "");
				
				layout.setTag(i);
				layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						goEditCalendar(Integer.valueOf(v.getTag().toString()));
					}
				});
				calendarContent.addView(vista);
			}
		}
	}
	
	public void loadCalculator() {
		String formulaAux = "";
		float gradeParcial = 0;
		float gradeFinal = 0;
		float totalPercent = 0;
		for (int i = 0; i < dataS.getActividades().size(); i++) {
			if (dataS.getActividades().get(i).getIdRamo() == subjectID) {
				int grade = dataS.getActividades().get(i).getNota();
				float percent = dataS.getActividades().get(i).getPorcentaje();
				if (grade > -1) {
					gradeParcial = gradeParcial + ( (grade*percent)/100 );
					totalPercent = totalPercent + percent;
					if (formulaAux.equals(""))
						formulaAux = "(" + grade + "*" + percent/100 + ")";
					else
						formulaAux = formulaAux + " (" + grade + "*" + percent/100 + ")";
				}
			}
		}
		gradeFinal = ( ( (100 - totalPercent)/100 ) + 1 ) * gradeParcial;
		
		View vista = View.inflate(this, R.layout.calculator_box, null);
		TextView formula = (TextView) vista.findViewById(R.id.CalculatorFormula);
		TextView parcialGrade = (TextView) vista.findViewById(R.id.CalculatorParcial);
		TextView finalGrade = (TextView) vista.findViewById(R.id.CalculatorFinal);
		
		formula.setText(""+formulaAux);
		parcialGrade.setText(""+gradeParcial);
		finalGrade.setText(""+gradeFinal);
		
		calculatorContent.addView(vista);
	}
	
	public void goAddTimetable(View v) {
		Intent intent = new Intent(this, AddTimetableActivity.class);
		intent.putExtra("SUBJECT", Integer.valueOf(v.getTag().toString()));
		startActivity(intent);
	}
	
	public void goAddCalendar(View v) {
		Intent intent = new Intent(this, AddCalendarActivity.class);
		intent.putExtra("SUBJECT", Integer.valueOf(v.getTag().toString()));
		intent.putExtra("EDIT", -1);
		startActivity(intent);
	}
	
	public void goEditCalendar(int activityPOS) {
		Intent intent = new Intent(this, AddCalendarActivity.class);
		intent.putExtra("SUBJECT", subjectPOS);
		intent.putExtra("EDIT", activityPOS);
		startActivity(intent);
	}
	
}
