package cl.edenprime.survivor;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import cl.edenprime.survivor.obj.ThemeSwitcher;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
 
public class WebMapActivity extends MapActivity {
	
    private WebView mapBrowser;
    private MapView mapa;
    private Button posicion;
    private Button usmPosicion;
    private MapController controlMapa;
    private List<Overlay> mapOverlays;
    private GeoPoint CCPoint;
    private GeoPoint CVPoint;
    private GeoPoint CSJPoint;
    private GeoPoint AuxPoint;
    private GeoPoint p;
    private Drawable drawable;
    private Geolocalizar itemizedOverlay;
    private OverlayItem overlayitem,overlayitem2,overlayitem3;
    
    private boolean pFinder;

 
    
//    private ProgressBar progressBar;

 
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ThemeSwitcher.setTheme(this);
        
        setContentView(R.layout.map);
        
        pFinder = false;
 
        mapBrowser = (WebView) findViewById(R.id.webview);
        mapBrowser.getSettings().setJavaScriptEnabled(true);
        mapBrowser.loadUrl("file:///android_asset/casa_central/CasaCentral.html");
        mapBrowser.getSettings().setBuiltInZoomControls(true);
        mapBrowser.getSettings().setSupportZoom(true);
        mapBrowser.getSettings().setDefaultZoom(ZoomDensity.FAR);
        mapBrowser.setWebViewClient(new WebBrowserClient());
        
        mapa = (MapView) findViewById(R.id.mapview);
        posicion = (Button) findViewById(R.id.botonPos);
        usmPosicion = (Button) findViewById(R.id.botonUsmPos);
        mapa.setBuiltInZoomControls(true);

        mapOverlays = mapa.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.marcador_mapa2);
        itemizedOverlay = new Geolocalizar(drawable, this);
        
        controlMapa = mapa.getController();
        
        CCPoint = new GeoPoint(-33035626,-71595235);
        CVPoint = new GeoPoint(-33378903,-70578471);
        CSJPoint = new GeoPoint(-33491305,-70618194);
        
        
        overlayitem = new OverlayItem(CCPoint,"USM Casa Central", "Avenida España 1680, Valparaíso");
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        controlMapa.setZoom(16);
//		controlMapa.animateTo(CCPoint);
		AuxPoint = CCPoint;
        
        overlayitem2= new OverlayItem(CSJPoint,"USM Campus San Joaquin", "Av. Vicuña Makenna 3939, Comuna San Joaquín");
        itemizedOverlay.addOverlay(overlayitem2);
        mapOverlays.add(itemizedOverlay);
        
        overlayitem3= new OverlayItem(CVPoint,"USM Campus Vitacura", "Av. Santa María de Manquehue N° 6400, Vitacura");
        itemizedOverlay.addOverlay(overlayitem3);
        mapOverlays.add(itemizedOverlay);
        
        LocationManager locMgr;
        MyLocationListener locLstnr;
        
        locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
      
        Criteria req = new Criteria();
        req.setAccuracy(Criteria.ACCURACY_FINE);
        req.setAltitudeRequired(true);
      
        locLstnr = new MyLocationListener();
        if (!locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locLstnr);

        }else{
        locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locLstnr);
        }
        
        
      //  progressBar = (ProgressBar) findViewById(R.id.progressbar);           
        
//        browser.setWebChromeClient(new WebChromeClient(){
//        	@Override
//        	public void onProgressChanged(WebView view, int progress){
//        		progressBar.setProgress(0);
//        		progressBar.setVisibility(View.VISIBLE);
//        		WebMapActivity.this.setProgress(progress * 1000);
//        		progressBar.incrementProgressBy(progress);
//                if(progress == 100){
//                	progressBar.setVisibility(View.GONE);
//                }
//        	}
//        });
        
    }
    
    private class WebBrowserClient extends WebViewClient {
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView browser, String url){
    		if (url.startsWith("tel:")) { 
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse(url)); 
                startActivity(intent); 
        }else {
            browser.loadUrl(url);
        }
        return true;
    	}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if((keyCode == KeyEvent.KEYCODE_BACK) && mapBrowser.canGoBack()){
    		mapBrowser.goBack();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }
   
    public void ShowUsmSede(View v) {
    	openUsmMapDialog();
	}
    
    private void openUsmMapDialog() {
		new AlertDialog.Builder(this)
		.setTitle("Seleccione Campus")
		.setItems(R.array.campus,
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialoginterface,int opt) {
			switch (opt){
				case 0:	{
					mapBrowser.loadUrl("file:///android_asset/casa_central/CasaCentral.html");
					mapBrowser.setVisibility(View.VISIBLE);
					mapa.setVisibility(View.GONE);
					posicion.setVisibility(View.GONE);
					usmPosicion.setVisibility(View.GONE);
					controlMapa.setZoom(16);
					controlMapa.animateTo(CCPoint);
					AuxPoint = CCPoint;
					}
					break;	
				case 1:	{
					mapBrowser.loadUrl("file:///android_asset/campus_sanjoaquin/CampusSanJoaquin.html");
					mapBrowser.setVisibility(View.VISIBLE);
					mapa.setVisibility(View.GONE);
					posicion.setVisibility(View.GONE);
					usmPosicion.setVisibility(View.GONE);
					controlMapa.setZoom(16);
					controlMapa.animateTo(CSJPoint);
					AuxPoint = CSJPoint;
					}
				break;
				case 2:	{
					mapBrowser.loadUrl("file:///android_asset/campus_vitacura/CampusVitacura.html");
					mapBrowser.setVisibility(View.VISIBLE);
					mapa.setVisibility(View.GONE);
					posicion.setVisibility(View.GONE);
					usmPosicion.setVisibility(View.GONE);
					controlMapa.setZoom(16);
					controlMapa.animateTo(CVPoint);
					AuxPoint = CVPoint;
				}
				break;
			}
		}
		})
		.show();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void googleMap(View V){
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		new AlertDialog.Builder(this)
		.setTitle("Seleccione mapa")
		.setItems(R.array.maps,
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialoginterface,int opt) {
			switch (opt){
				case 0:	{
					mapBrowser.setVisibility(View.VISIBLE);
					mapa.setVisibility(View.GONE);
					posicion.setVisibility(View.GONE);
					usmPosicion.setVisibility(View.GONE);
				}		
				break;
				case 1: {
					mapa.setVisibility(View.VISIBLE);
					posicion.setVisibility(View.VISIBLE);
					usmPosicion.setVisibility(View.VISIBLE);
					mapBrowser.setVisibility(View.GONE);
					controlMapa.animateTo(AuxPoint);
				}
			}
		}
		})
		.show();		
	}
	
	public void myPos (View V){
		if (pFinder)
			controlMapa.animateTo(p);
	}
	
	public void usmPos (View V){
		controlMapa.animateTo(AuxPoint);
	}
	
	public class MyLocationListener implements LocationListener
	{
		
	@Override
	public void onLocationChanged(Location loc)
	{
	loc.getLatitude();
	loc.getLongitude();
	
//	String Text = "My current location is: " +
//	"Latitud = " + loc.getLatitude() +
//	"Longitud = " + loc.getLongitude();
//	Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	
	String coordinates[] = {""+loc.getLatitude(), ""+loc.getLongitude()};
	 double lat = Double.parseDouble(coordinates[0]);
	 double lng = Double.parseDouble(coordinates[1]);

	 p = new GeoPoint(
	 (int) (lat * 1E6),
	 (int) (lng * 1E6));
	 pFinder = true;

//	 controlMapa.animateTo(p);
	 controlMapa.setZoom(16);
	 MyMapOverlays marker = new MyMapOverlays(p,WebMapActivity.this) ;
	 List<Overlay> listOfOverLays = mapa.getOverlays();
	 listOfOverLays.clear();
	 listOfOverLays.add(marker);
	 
	 mapOverlays.add(itemizedOverlay);

	 mapa.invalidate();

	}

	@Override
	public void onProviderDisabled(String provider)
	{
	Toast.makeText( getApplicationContext(),
	"Gps Desabilitado",
	Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onProviderEnabled(String provider)
	{
	Toast.makeText( getApplicationContext(),
	"Gps Habilitado",
	Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

	}

	}

	
}