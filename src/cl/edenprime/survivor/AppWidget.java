package cl.edenprime.survivor;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import cl.edenprime.survivor.modelo.Ramo;
import cl.edenprime.survivor.obj.dataS;

public class AppWidget extends AppWidgetProvider {

	public static String ACTION_WIDGET_CONFIGURE = "ConfigureWidget";
	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		dataS.loadPrefs(context);
		dataS.loadData(context);
		dataS.loadAct(context);
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_1);
		
		String[] calendar_type_array = context.getResources().getStringArray(R.array.calendar_type_array);
		
		// SETEAR EN VACIO
		remoteViews.setTextViewText(R.id.WidgetSigla1, "-");
        remoteViews.setTextViewText(R.id.WidgetTipo1, "-");
        remoteViews.setTextViewText(R.id.WidgetFecha1, "-");
        remoteViews.setTextViewText(R.id.WidgetSigla2, "-");
        remoteViews.setTextViewText(R.id.WidgetTipo2, "-");
        remoteViews.setTextViewText(R.id.WidgetFecha2, "-");
        remoteViews.setTextViewText(R.id.WidgetSigla3, "-");
        remoteViews.setTextViewText(R.id.WidgetTipo3, "-");
        remoteViews.setTextViewText(R.id.WidgetFecha3, "-");
        
        // Setear clickers
        Intent configIntent1 = new Intent(context, ClickOneActivity1.class);
        Intent configIntent2 = new Intent(context, ClickOneActivity2.class);
        Intent configIntent3 = new Intent(context, ClickOneActivity3.class);
        
		
		if (dataS.getActividadesCercanas().size() > 0) {
			remoteViews.setTextViewText(R.id.WidgetSigla1, obtenerRamoPorId(dataS.getActividadesCercanas().get(0).getIdRamo()).getSigla());
	        remoteViews.setTextViewText(R.id.WidgetTipo1, calendar_type_array[dataS.getActividadesCercanas().get(0).getTipo()]);
	        remoteViews.setTextViewText(R.id.WidgetFecha1, dataS.getActividadesCercanas().get(0).formatDate());
			
	        configIntent1.putExtra("SUBJECT", obtenerPosRamoPorId(dataS.getActividadesCercanas().get(0).getIdRamo()));
			configIntent1.putExtra("EDIT", obtenerPosActividadPorId(dataS.getActividadesCercanas().get(0).getId()));
			configIntent1.setAction(ACTION_WIDGET_CONFIGURE);
			PendingIntent configPendingIntent1 = PendingIntent.getActivity(context, 0, configIntent1, 0);
			remoteViews.setOnClickPendingIntent(R.id.WidgetClick1, configPendingIntent1);
			
			if (dataS.getActividadesCercanas().size() > 1) {
				remoteViews.setTextViewText(R.id.WidgetSigla2, obtenerRamoPorId(dataS.getActividadesCercanas().get(1).getIdRamo()).getSigla());
		        remoteViews.setTextViewText(R.id.WidgetTipo2, calendar_type_array[dataS.getActividadesCercanas().get(1).getTipo()]);
		        remoteViews.setTextViewText(R.id.WidgetFecha2,  dataS.getActividadesCercanas().get(1).formatDate());
				
		        configIntent2.putExtra("SUBJECT", obtenerPosRamoPorId(dataS.getActividadesCercanas().get(1).getIdRamo()));
		        configIntent2.putExtra("EDIT", obtenerPosActividadPorId(dataS.getActividadesCercanas().get(1).getId()));
		        configIntent2.setAction(ACTION_WIDGET_CONFIGURE);
		        PendingIntent configPendingIntent2 = PendingIntent.getActivity(context, 0, configIntent2, 0);
		        remoteViews.setOnClickPendingIntent(R.id.WidgetClick2, configPendingIntent2);
				
				if (dataS.getActividadesCercanas().size() > 2) {
					remoteViews.setTextViewText(R.id.WidgetSigla3, obtenerRamoPorId(dataS.getActividadesCercanas().get(2).getIdRamo()).getSigla());
			        remoteViews.setTextViewText(R.id.WidgetTipo3, calendar_type_array[dataS.getActividadesCercanas().get(2).getTipo()]);
			        remoteViews.setTextViewText(R.id.WidgetFecha3,  dataS.getActividadesCercanas().get(2).formatDate());
			        
			        configIntent3.putExtra("SUBJECT", obtenerPosRamoPorId(dataS.getActividadesCercanas().get(2).getIdRamo()));
			        configIntent3.putExtra("EDIT", obtenerPosActividadPorId(dataS.getActividadesCercanas().get(2).getId()));
			        configIntent3.setAction(ACTION_WIDGET_CONFIGURE);
			        PendingIntent configPendingIntent3 = PendingIntent.getActivity(context, 0, configIntent3, 0);
			        remoteViews.setOnClickPendingIntent(R.id.WidgetClick3, configPendingIntent3);
					
				}
			}
		}
		
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
	
	public Ramo obtenerRamoPorId(int id) {
		Ramo ramo = null;
		for (int i = 0; i < dataS.getRamosINS().size(); i ++) {
			if (dataS.getRamosINS().get(i).getId() == id) {
				ramo = dataS.getRamosINS().get(i);
			}
		}
		return ramo;
	}
	
	public int obtenerPosRamoPorId(int id) {
		for (int i = 0; i < dataS.getRamosINS().size(); i ++) {
			if (dataS.getRamosINS().get(i).getId() == id) {
				return i;
			}
		}
		return 0;
	}
	
	public int obtenerPosActividadPorId(int id) {
		for (int i = 0; i < dataS.getActividades().size(); i ++) {
			if (dataS.getActividades().get(i).getId() == id) {
				return i;
			}
		}
		return 0;
	}
	
}