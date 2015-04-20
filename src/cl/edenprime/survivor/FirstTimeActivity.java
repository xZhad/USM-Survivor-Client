package cl.edenprime.survivor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import cl.edenprime.survivor.first.ApprovedFragment;
import cl.edenprime.survivor.first.CareerFragment;
import cl.edenprime.survivor.first.EnrolledFragment;
import cl.edenprime.survivor.first.RegisterFragment;
import cl.edenprime.survivor.first.WelcomeFragment;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class FirstTimeActivity extends FragmentActivity {
	
	/**	Pagina Actual */
	private int page;
	
	// Elementos del Layout
	private Button back;
	private Button next;
	public static FrameLayout working;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.firsttime);
        
        if (savedInstanceState != null) {
            return;
        }
        
        // Setear elementos del layout
        back = (Button) findViewById(R.id.FirstBack);
        next = (Button) findViewById(R.id.FirstNext);
        working = (FrameLayout) findViewById(R.id.FirstWorking);
        back.setVisibility(View.GONE);
		next.setVisibility(View.VISIBLE);
        page = 1;
        
        WelcomeFragment step1 = new WelcomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.FirstLayoutContainer, step1).commit();
    }
	
	public void setPage1() {
		back.setVisibility(View.GONE);
		next.setVisibility(View.VISIBLE);
		
		WelcomeFragment step1 = new WelcomeFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.FirstLayoutContainer, step1);
		transaction.commit();
	}
	
	public void setPage2() {
		back.setVisibility(View.VISIBLE);
		next.setVisibility(View.VISIBLE);
		
		CareerFragment step2 = new CareerFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.FirstLayoutContainer, step2);
		transaction.commit();
	}
	
	public void setPage3() {
		back.setVisibility(View.VISIBLE);
		next.setVisibility(View.VISIBLE);
		
		ApprovedFragment step3 = new ApprovedFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.FirstLayoutContainer, step3);
		transaction.commit();
	}
	
	public void setPage4() {
		back.setVisibility(View.VISIBLE);
		next.setVisibility(View.VISIBLE);
		
		EnrolledFragment step4 = new EnrolledFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.FirstLayoutContainer, step4);
		transaction.commit();
	}
	
	public void setPage5() {
		back.setVisibility(View.VISIBLE);
		next.setVisibility(View.VISIBLE);
		
		RegisterFragment step5 = new RegisterFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.FirstLayoutContainer, step5);
		transaction.commit();
	}
	
	public void setPage6() {
		dataS.setFirstTime(false);
		dataS.loadPrefs(this);
		dataS.loadData(this);
		finish();
		Intent mainIntent = new Intent(this, SplashActivity.class);
		this.startActivity(mainIntent);
	}
	
	public void goBack(View v) {
		working.setVisibility(View.VISIBLE);
		page--;
		goTo();
	}
	
	public void goNext(View v) {
		working.setVisibility(View.VISIBLE);
		page++;
		goTo();
	}
	
	public void goTo() {
		switch (page) {
			case 1: setPage1(); break;
			case 2: setPage2(); break;
			case 3: setPage3(); break;
			case 4: setPage4(); break;
			case 5: setPage5(); break;
			case 6: setPage6(); break;
		}
	}
	
}
