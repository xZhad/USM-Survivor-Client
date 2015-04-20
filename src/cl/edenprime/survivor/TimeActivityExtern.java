package cl.edenprime.survivor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cl.edenprime.survivor.modelo.Clase;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TimeActivityExtern extends Activity {
	
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
	TextView titulo;
	private String horario;
	private String horarioName;
	ArrayList<Clase> horarioRecibido;
	Gson gson = new Gson();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        horario=bundle.getString("data");
        horarioName=bundle.getString("horarioName");
        Type collectionType = new TypeToken<ArrayList<Clase>>(){}.getType();
        gson = new Gson();
        horarioRecibido = gson.fromJson(horario, collectionType );
        
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
        titulo = (TextView) findViewById(R.id.horariotitle);
        
        
        // Setear valores iniciales
        blockTitle.setVisibility(View.GONE);
        blockGuide.setVisibility(View.GONE);
        initPos = 0;
        firstAdded = false;
        todayTitle.setText(getResources().getStringArray(R.array.days_array)[getCurrentDay()]);
        titulo.setText("Horario de: "+horarioName);
        setDay();
        setHorario();
        //Toast.makeText(this, horario, Toast.LENGTH_LONG).show();
    }
	
	public void setHorario() {
		for (int i = 0; i < horarioRecibido.size(); i ++) {
			
			View vista = inflater.inflate(R.layout.timetable_boxes, null);
			
			TextView start = (TextView) vista.findViewById(R.id.timetableBoxStart);
			TextView subject = (TextView) vista.findViewById(R.id.timetableBoxSubject);
			TextView type = (TextView) vista.findViewById(R.id.timetableBoxType);
			TextView end = (TextView) vista.findViewById(R.id.timetableBoxEnd);
			FrameLayout click = (FrameLayout) vista.findViewById(R.id.timetableBoxClick);
			
			start.setText(horarioRecibido.get(i).getBloque().formatStart());
			subject.setText(getRamoById(horarioRecibido.get(i).getIdRamo()));
			type.setText(getResources().getStringArray(R.array.timetable_type_array)[horarioRecibido.get(i).getTipo()]);
			end.setText(horarioRecibido.get(i).getBloque().formatEnd());
			click.setTag(horarioRecibido.get(i).getId());
			click.setBackgroundResource(getRandomBackground());
			
			int top = horarioRecibido.get(i).getBloque().getStarthour() * 60
					+ horarioRecibido.get(i).getBloque().getStartminute();
			
			int bot = horarioRecibido.get(i).getBloque().getEndhour() * 60
					+ horarioRecibido.get(i).getBloque().getEndminute();
			
			int height = bot-top;
			
			top = getPXfromDP(top);
			bot = getPXfromDP(bot);
			height = getPXfromDP(height);
			
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
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
			
			switch(horarioRecibido.get(i).getDia()) {
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
	        @Override
			public void run() { 
	        	scroller.scrollTo(0, initPos);
	        } 
		});
	}
	
	public void setDay() {
		for (int i = 0; i < horarioRecibido.size(); i ++) {
			if (horarioRecibido.get(i).getDia() == getCurrentDay()) {
				View vista = inflater.inflate(R.layout.timetable_boxes, null);
				
				TextView start = (TextView) vista.findViewById(R.id.timetableBoxStart);
				TextView subject = (TextView) vista.findViewById(R.id.timetableBoxSubject);
				TextView type = (TextView) vista.findViewById(R.id.timetableBoxType);
				TextView end = (TextView) vista.findViewById(R.id.timetableBoxEnd);
				FrameLayout click = (FrameLayout) vista.findViewById(R.id.timetableBoxClick);
				
				start.setText(horarioRecibido.get(i).getBloque().formatStart());
				subject.setText(getRamoById(horarioRecibido.get(i).getIdRamo()));
				type.setText(getResources().getStringArray(R.array.timetable_type_array)[horarioRecibido.get(i).getTipo()]);
				end.setText(horarioRecibido.get(i).getBloque().formatEnd());
				click.setTag(horarioRecibido.get(i).getId());
				click.setBackgroundResource(getRandomBackground());
				
				int top = horarioRecibido.get(i).getBloque().getStarthour() * 60
						+ horarioRecibido.get(i).getBloque().getStartminute();
				
				int bot = horarioRecibido.get(i).getBloque().getEndhour() * 60
						+ horarioRecibido.get(i).getBloque().getEndminute();
				
				int height = bot-top;
				
				top = getPXfromDP(top);
				bot = getPXfromDP(bot);
				height = getPXfromDP(height);
				
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
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
