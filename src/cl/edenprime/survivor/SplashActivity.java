package cl.edenprime.survivor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class SplashActivity extends Activity {
	
	private final int SPLASH_DISPLAY_LENGHT = 2000;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        dataS.loadPrefs(this);
        
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.splash);
		
		if (dataS.isSplashEnabled()) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					if (dataS.isFirstTime()) {
						finish();
						Intent mainIntent = new Intent(SplashActivity.this, FirstTimeActivity.class);
						SplashActivity.this.startActivity(mainIntent);
					} else {
						dataS.loadData(SplashActivity.this);
						finish();
						Intent mainIntent = new Intent(SplashActivity.this, DashActivity.class);
						SplashActivity.this.startActivity(mainIntent);
					}
				}
			} , SPLASH_DISPLAY_LENGHT);
		} else {
			dataS.loadData(this);
			finish();
			Intent mainIntent = new Intent(SplashActivity.this, DashActivity.class);
			SplashActivity.this.startActivity(mainIntent);
		}
	}
	
	
	
	@Override
	public void onBackPressed() {
		//TODO
	}
	
}
