package cl.edenprime.survivor.obj;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import cl.edenprime.survivor.R;

public class ThemeSwitcher {
	
	private static int sTheme;
	public static boolean switched = false;
	
	public static int getSelectedTheme() {
		return sTheme;
	}
	public static void setSelectedTheme(int sTheme) {
		ThemeSwitcher.sTheme = sTheme;
		switched = true;
	}
	public static final int DI_STYLE = 0;
	public static final int DARK_STYLE = 1;
	public static final int LIGHT_STYLE = 2;
	
	/**
	 * Reinicia el activity
	 */
	public static void restartActivity(Activity activity) {
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}
	
	/**
	 * Cambia el tema y reinicia el activity
	 */
	public static void switchToTheme(Activity activity, int theme) {
		setSelectedTheme(theme);
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}
	
	/**
	 * Setea el tema segun la configuracion
	 */
	public static void setTheme(Activity activity) {
		switch (getSelectedTheme()) {
			default:
			case DI_STYLE:
				activity.setTheme(R.style.styleDI);
				break;
			case DARK_STYLE:
				activity.setTheme(R.style.styleDARK);
				break;
			case LIGHT_STYLE:
				activity.setTheme(R.style.styleLIGHT);
				break;
		}
	}
	
	/**
	 * Setea el tema segun la configuracion
	 */
	public static void setTheme(FragmentActivity activity) {
		switch (getSelectedTheme()) {
			default:
			case DI_STYLE:
				activity.setTheme(R.style.styleDI);
				break;
			case DARK_STYLE:
				activity.setTheme(R.style.styleDARK);
				break;
			case LIGHT_STYLE:
				activity.setTheme(R.style.styleLIGHT);
				break;
		}
	}
}
