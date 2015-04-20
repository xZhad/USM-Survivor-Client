package cl.edenprime.survivor;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

	class MyMapOverlays extends com.google.android.maps.Overlay {

	GeoPoint location = null;
	Context contexto;
	

	public MyMapOverlays(GeoPoint location,Context contexto)
	 {
	 super();
	 this.location = location;
	 this.contexto = contexto;
	 }

	@Override
	 public void draw(Canvas canvas, MapView mapView, boolean shadow)
	 {

	 super.draw(canvas, mapView, shadow);
	 //translate the screen pixels
	 Point screenPoint = new Point();
	 mapView.getProjection().toPixels(this.location, screenPoint);

	 //add the image
	 canvas.drawBitmap(BitmapFactory.decodeResource(contexto.getResources(), R.drawable.marcador_mapa2),
	 screenPoint.x, screenPoint.y , null); //Setting the image  location on the screen (x,y).
	 }

}
