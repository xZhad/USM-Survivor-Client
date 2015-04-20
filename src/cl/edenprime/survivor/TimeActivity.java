package cl.edenprime.survivor;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class TimeActivity extends Activity {
	
	int initPos;
	boolean firstAdded;
	
	// Elementos del Layout
	LayoutInflater inflater;
	TextView blockTitle;
	FrameLayout blockGuide;
	TextView hourTitle;
	LinearLayout hourGuide;
	TextView todayTitle;
	FrameLayout todayContent;
	FrameLayout monContent;
	FrameLayout tueContent;
	FrameLayout wedContent;
	FrameLayout thuContent;
	FrameLayout friContent;
	ScrollView scroller;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.timetable);
        
        // Setear elementos del layout
        inflater = getLayoutInflater();
        blockTitle= (TextView) findViewById(R.id.TimetableBlockGuideTitle);
        blockGuide = (FrameLayout) findViewById(R.id.TimetableBlockGuide);
        hourTitle = (TextView) findViewById(R.id.TimetableHourGuideTitle);
        hourGuide = (LinearLayout) findViewById(R.id.TimetableHourGuide);
        todayTitle = (TextView) findViewById(R.id.TimetableTodayTitle);
        todayContent = (FrameLayout) findViewById(R.id.TimetableTodayContent);
        monContent = (FrameLayout) findViewById(R.id.TimetableMonContent);
        tueContent = (FrameLayout) findViewById(R.id.TimetableTueContent);
        wedContent = (FrameLayout) findViewById(R.id.TimetableWedContent);
        thuContent = (FrameLayout) findViewById(R.id.TimetableThuContent);
        friContent = (FrameLayout) findViewById(R.id.TimetableFriContent);
        scroller = (ScrollView) findViewById(R.id.TimetableScroller);
        
        // Setear valores iniciales
        blockTitle.setVisibility(View.GONE);
        blockGuide.setVisibility(View.GONE);
        initPos = 0;
        firstAdded = false;
        todayTitle.setText(getResources().getStringArray(R.array.days_array)[getCurrentDay()]);
        
        setDay();
        setHorario();
    }
	
	public void setHorario() {
		for (int i = 0; i < dataS.getClases().size(); i ++) {
			
			View vista = inflater.inflate(R.layout.timetable_boxes, null);
			
			TextView start = (TextView) vista.findViewById(R.id.timetableBoxStart);
			TextView subject = (TextView) vista.findViewById(R.id.timetableBoxSubject);
			TextView type = (TextView) vista.findViewById(R.id.timetableBoxType);
			TextView end = (TextView) vista.findViewById(R.id.timetableBoxEnd);
			FrameLayout click = (FrameLayout) vista.findViewById(R.id.timetableBoxClick);
			
			start.setText(dataS.getClases().get(i).getBloque().formatStart());
			subject.setText(getRamoById(dataS.getClases().get(i).getIdRamo()));
			type.setText(getResources().getStringArray(R.array.timetable_type_array)[dataS.getClases().get(i).getTipo()]);
			end.setText(dataS.getClases().get(i).getBloque().formatEnd());
			click.setTag(dataS.getClases().get(i).getId());
			click.setBackgroundResource(getRandomBackground());
			
			int top = dataS.getClases().get(i).getBloque().getStarthour() * 60
					+ dataS.getClases().get(i).getBloque().getStartminute();
			
			int bot = dataS.getClases().get(i).getBloque().getEndhour() * 60
					+ dataS.getClases().get(i).getBloque().getEndminute();
			
			int height = bot-top;
			
			top = getPXfromDP(top);
			bot = getPXfromDP(bot);
			height = getPXfromDP(height);
			
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
			params.gravity = Gravity.TOP;
			
			params.setMargins(0, top, 0, 0);
			
			if (!firstAdded) {
				initPos = top;
				firstAdded = true;
			}
			else {
				if (top < initPos)
					initPos = top;
			}
			
			switch(dataS.getClases().get(i).getDia()) {
			    case 0: monContent.addView(vista, params); break;
			    case 1: tueContent.addView(vista, params); break;
			    case 2: wedContent.addView(vista, params); break;
			    case 3: thuContent.addView(vista, params); break;
			    case 4: friContent.addView(vista, params); break;
			    default: break;
			}
			
		}
		if (initPos > 16)
			initPos = initPos - 16;
		scroller.post(new Runnable() { 
	        public void run() { 
	        	scroller.scrollTo(0, initPos);
	        } 
		});
	}
	
	public void setDay() {
		for (int i = 0; i < dataS.getClases().size(); i ++) {
			if (dataS.getClases().get(i).getDia() == getCurrentDay()) {
				View vista = inflater.inflate(R.layout.timetable_boxes, null);
				
				TextView start = (TextView) vista.findViewById(R.id.timetableBoxStart);
				TextView subject = (TextView) vista.findViewById(R.id.timetableBoxSubject);
				TextView type = (TextView) vista.findViewById(R.id.timetableBoxType);
				TextView end = (TextView) vista.findViewById(R.id.timetableBoxEnd);
				FrameLayout click = (FrameLayout) vista.findViewById(R.id.timetableBoxClick);
				
				start.setText(dataS.getClases().get(i).getBloque().formatStart());
				subject.setText(getRamoById(dataS.getClases().get(i).getIdRamo()));
				type.setText(getResources().getStringArray(R.array.timetable_type_array)[dataS.getClases().get(i).getTipo()]);
				end.setText(dataS.getClases().get(i).getBloque().formatEnd());
				click.setTag(dataS.getClases().get(i).getId());
				click.setBackgroundResource(getRandomBackground());
				
				int top = dataS.getClases().get(i).getBloque().getStarthour() * 60
						+ dataS.getClases().get(i).getBloque().getStartminute();
				
				int bot = dataS.getClases().get(i).getBloque().getEndhour() * 60
						+ dataS.getClases().get(i).getBloque().getEndminute();
				
				int height = bot-top;
				
				top = getPXfromDP(top);
				bot = getPXfromDP(bot);
				height = getPXfromDP(height);
				
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
				params.gravity = Gravity.TOP;
				
				params.setMargins(0, top, 0, 0);
				
				if (!firstAdded) {
					initPos = top;
					firstAdded = true;
				}
				else {
					if (top < initPos)
						initPos = top;
				}
				todayContent.addView(vista, params);
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
	
	public int getPXfromDP(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
	}
	
	public void goHour(View v) {
		blockTitle.setVisibility(View.GONE);
        blockGuide.setVisibility(View.GONE);
        hourTitle.setVisibility(View.VISIBLE);
        hourGuide.setVisibility(View.VISIBLE);
	}
	
	public void goBlock(View v) {
		blockTitle.setVisibility(View.VISIBLE);
        blockGuide.setVisibility(View.VISIBLE);
        hourTitle.setVisibility(View.GONE);
        hourGuide.setVisibility(View.GONE);
	}
	
	public void goBack(View v) {
		finish();
	}
	
}
