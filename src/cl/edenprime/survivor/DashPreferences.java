package cl.edenprime.survivor;

import android.app.Activity;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import cl.edenprime.survivor.obj.ThemeSwitcher;
import cl.edenprime.survivor.obj.dataS;

public class DashPreferences extends PreferenceActivity {
	
	public Activity activity = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		addPreferencesFromResource(R.xml.dashprefs);
		final ListPreference style = (ListPreference) findPreference("appStyle");
		style.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String val = newValue.toString();
				int index = style.findIndexOfValue(val);
				ThemeSwitcher.setSelectedTheme(index);
				return true;
			}
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
		dataS.loadPrefs(this);
	}
	
}
