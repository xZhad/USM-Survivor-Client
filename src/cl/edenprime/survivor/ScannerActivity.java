package cl.edenprime.survivor;

import android.os.Bundle;
import android.view.View;
import cl.edenprime.survivor.obj.ThemeSwitcher;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;

public class ScannerActivity extends CaptureActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.scannerqr);
    }
	
	public void goBack(View v) {
		finish();
	}
	
}
