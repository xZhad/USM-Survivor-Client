package cl.edenprime.survivor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.subjects.SubjectsListFragment;

public class SubjectsActivity extends FragmentActivity {
	
	// Elementos del Layout
	private Button edit;
	SubjectsListFragment listfragment;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.subjects);
        
        if (savedInstanceState != null) {
            return;
        }
        
        // Setear elementos del layout
        edit = (Button) findViewById(R.id.SubjectsEdit);
        
        listfragment = new SubjectsListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.SubjectsLayoutContainer, listfragment).commit();
    }
	
	public void goEdit(View v) {
		edit.setEnabled(false);
	}
	
}
