package cl.edenprime.survivor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class DashActivity extends Activity {
	
	FrameLayout working;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        if (dataS.isFirstTime())
        	dataS.setFirstTime(false);
        setContentView(R.layout.dashboard);
        working = (FrameLayout) findViewById(R.id.DashWorking);
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		if (ThemeSwitcher.switched) {
        	ThemeSwitcher.switched = false;
        	ThemeSwitcher.restartActivity(this);
        }
		working.setVisibility(View.GONE);
	}
	
	public void launchUniversity(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, SubjectsActivity.class);
		this.startActivity(intent);
	}
	
	public void launchPersonal(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, ViewPersonalActivity.class);
		this.startActivity(intent);
	}
	
	public void launchTimeTable(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, TimeActivity.class);
		this.startActivity(intent);
	}
	
	public void launchCalendar(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, CalendarActivity.class);
		this.startActivity(intent);
	}
	
	public void launchMap(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, WebMapActivity.class);
		this.startActivity(intent);
	}
	
	public void launchInternet(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, WebActivity.class);
		this.startActivity(intent);
	}
	
	public void launchScanner(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, ScannerActivity.class);
		this.startActivity(intent);
	}
	
	public void launchShare(View v) {
		working.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, BluetoothChat.class);
		this.startActivity(intent);
	}
	
	public void launchOptions(View v) {
		startActivity(new Intent(this, DashPreferences.class));
	}
	
	public void goSearch(View v) {
		onSearchRequested();
	}
	
	public void goBack(View v) {
		finish();
	}
	
}
