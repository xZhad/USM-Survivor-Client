package cl.edenprime.survivor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import cl.edenprime.survivor.dao.WebServices;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class CalendarActivity extends Activity {
	
	int initPos;
	boolean firstAdded;
	
	// Elementos del Layout
	LayoutInflater inflater;
	LinearLayout monContent;
	LinearLayout tueContent;
	LinearLayout wedContent;
	LinearLayout thuContent;
	LinearLayout friContent;
	LinearLayout satContent;
	LinearLayout sunContent;
	TextView monthTitle;
	
	MonthDisplayHelper mdh;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.calendar);
        
        // Setear elementos del layout
        inflater = getLayoutInflater();
        monContent = (LinearLayout) findViewById(R.id.CalendarMonContent);
        tueContent = (LinearLayout) findViewById(R.id.CalendarTueContent);
        wedContent = (LinearLayout) findViewById(R.id.CalendarWedContent);
        thuContent = (LinearLayout) findViewById(R.id.CalendarThuContent);
        friContent = (LinearLayout) findViewById(R.id.CalendarFriContent);
        satContent = (LinearLayout) findViewById(R.id.CalendarSatContent);
        sunContent = (LinearLayout) findViewById(R.id.CalendarSunContent);
        monthTitle = (TextView) findViewById(R.id.CalendarMonthTitle);
        
        mdh = new MonthDisplayHelper(getCurrentYear(), getCurrentMonth(), Calendar.MONDAY);
        
        setCalendar();
    }
	
	public void setCalendar() {
		
		monthTitle.setText(getResources().getStringArray(R.array.month_array)[mdh.getMonth()]);
		
		View[][] calendar = new View[(mdh.getRowOf(mdh.getNumberOfDaysInMonth()))+1][7];
		
		boolean thisMonth = false;
		
		// Setear Dias
		for (int i = 0; i < calendar.length; i ++) { // i = row
			for (int j = 0; j < calendar[0].length; j ++) { // j = col
				
				if (mdh.getDayAt(i, j) == 1 && i < 1) {
					thisMonth = true;
				}
				
				if (thisMonth) {
					calendar[i][j] = inflater.inflate(R.layout.calendar_boxes, null);
					TextView day = (TextView) calendar[i][j].findViewById(R.id.calendarBoxDay);
					day.setText(String.valueOf(mdh.getDayAt(i, j)));
				}
				else {
					calendar[i][j] = inflater.inflate(R.layout.calendar_boxes, null);
					FrameLayout click = (FrameLayout) calendar[i][j].findViewById(R.id.calendarBoxClick);
					click.setVisibility(View.INVISIBLE);
				}
				
				if (mdh.getDayAt(i, j) == mdh.getNumberOfDaysInMonth()) {
					thisMonth = false;
				}
			}
		}
		
		// Setea Actividades
		for (int i = 0; i < dataS.getActividades().size(); i ++) {
			if (dataS.getActividades().get(i).getAno() == mdh.getYear() && dataS.getActividades().get(i).getMes() == mdh.getMonth()) {
				int row = mdh.getRowOf(dataS.getActividades().get(i).getDia());
				int col = mdh.getColumnOf(dataS.getActividades().get(i).getDia());
				
				calendar[row][col] = inflater.inflate(R.layout.calendar_boxes, null);
				TextView day = (TextView) calendar[row][col].findViewById(R.id.calendarBoxDay);
				day.setVisibility(View.GONE);
				day = (TextView) calendar[row][col].findViewById(R.id.calendarBoxDayActivity);
				day.setVisibility(View.VISIBLE);
				day.setText(String.valueOf(mdh.getDayAt(row, col)));
				TextView alert = (TextView) calendar[row][col].findViewById(R.id.calendarBoxAlert);
				alert.setText("!");
				FrameLayout click = (FrameLayout) calendar[row][col].findViewById(R.id.calendarBoxClick);
				click.setBackgroundResource(getRandomBackground());
			}
		}
		
		// Setea Actividades Online
		WebServices web = new WebServices();
		ArrayList<Actividad> actividades = web.loadPersonalOnline();
		if (actividades == null)
			actividades = new ArrayList<Actividad>();
		
		for (int i = 0; i < actividades.size(); i ++) {
			if (actividades.get(i).getAno() == mdh.getYear() && actividades.get(i).getMes() == mdh.getMonth()) {
				int row = mdh.getRowOf(actividades.get(i).getDia());
				int col = mdh.getColumnOf(actividades.get(i).getDia());
				
				calendar[row][col] = inflater.inflate(R.layout.calendar_boxes, null);
				TextView day = (TextView) calendar[row][col].findViewById(R.id.calendarBoxDay);
				day.setVisibility(View.GONE);
				day = (TextView) calendar[row][col].findViewById(R.id.calendarBoxDayActivity);
				day.setVisibility(View.VISIBLE);
				day.setText(String.valueOf(mdh.getDayAt(row, col)));
				TextView alert = (TextView) calendar[row][col].findViewById(R.id.calendarBoxAlert);
				alert.setText("!");
				FrameLayout click = (FrameLayout) calendar[row][col].findViewById(R.id.calendarBoxClick);
				click.setBackgroundResource(getRandomBackground());
			}
		}
		
		// Agregar al Calendario
		for (int i = 0; i < calendar.length; i ++) { // i = row
			for (int j = 0; j < calendar[0].length; j ++) { // j = col
				switch(j) {
				    case 0: monContent.addView(calendar[i][j]); break;
				    case 1: tueContent.addView(calendar[i][j]); break;
				    case 2: wedContent.addView(calendar[i][j]); break;
				    case 3: thuContent.addView(calendar[i][j]); break;
				    case 4: friContent.addView(calendar[i][j]); break;
				    case 5: satContent.addView(calendar[i][j]); break;
				    case 6: sunContent.addView(calendar[i][j]); break;
				    default: break;
				}
			}
		}
	}
	
	public int getRandomBackground() {
		int min = 0;
		int max = 5;
		
		Random r = new Random();
		int i1 = r.nextInt(max - min + 1) + min;
		
		switch(i1) {
		    case 0: return R.drawable.roundcontainerazul;
		    case 1: return R.drawable.roundcontainermorado;
		    case 2: return R.drawable.roundcontainernaranjo;
		    case 3: return R.drawable.roundcontainerrojo;
		    case 4: return R.drawable.roundcontainerverde;
		    default: return R.drawable.roundcontainerazul;
		}
	}
	
	public String getRamoById(int id) {
		for (int i = 0; i < dataS.getRamosINS().size(); i ++) {
			if (dataS.getRamosINS().get(i).getId() == id) {
				return dataS.getRamosINS().get(i).getSigla();
			}
		}
		return "";
	}
	
	public int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        day = day+5;
        day = day%7;
        return day;
	}
	
	public int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        return month;
	}
	
	public int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
	}
	
	public int getPXfromDP(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}
	
	public void goBack(View v) {
		monContent.removeAllViews();
        tueContent.removeAllViews();
        wedContent.removeAllViews();
        thuContent.removeAllViews();
        friContent.removeAllViews();
        satContent.removeAllViews();
        sunContent.removeAllViews();
        
        mdh.previousMonth();
        setCalendar();
	}
	
	public void goNext(View v) {
		monContent.removeAllViews();
        tueContent.removeAllViews();
        wedContent.removeAllViews();
        thuContent.removeAllViews();
        friContent.removeAllViews();
        satContent.removeAllViews();
        sunContent.removeAllViews();
        
        mdh.nextMonth();
        setCalendar();
	}
	
}
