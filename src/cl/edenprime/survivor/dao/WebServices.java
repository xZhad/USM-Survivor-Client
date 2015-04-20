package cl.edenprime.survivor.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import cl.edenprime.survivor.modelo.Actividad;
import cl.edenprime.survivor.modelo.ActividadArray;
import cl.edenprime.survivor.modelo.Usuario;
import cl.edenprime.survivor.obj.dataS;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class WebServices {

	Gson gson = new Gson();
	HttpClient client = new DefaultHttpClient();
	HttpPost post = new HttpPost("URL");
	HttpResponse response;
	InputStream data = null;

	public void register (String user, String pass) {
		dataS.setUser(new Usuario(-1, user, pass));
		String json = gson.toJson(dataS.getUser());

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("method", "register"));
		postParameters.add(new BasicNameValuePair("userID", String.valueOf(-1)));
		postParameters.add(new BasicNameValuePair("json", json));
		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(post);
			data = response.getEntity().getContent();
			Reader r = new InputStreamReader(data);
			dataS.setUser(gson.fromJson(r, Usuario.class));
		} catch (ClientProtocolException e) {
			Log.i("PRE REGISTER", "Error 1");
		} catch (IOException e) {
			Log.i("PRE REGISTER", "Error 2");
		} catch (Exception e) {
			Log.i("PRE REGISTER", "Error 3");
		}
	}

	public void addPersonalOnline (Actividad actividad) {
		String json = gson.toJson(actividad);

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("method", "add_actividad"));
		postParameters.add(new BasicNameValuePair("userID", String.valueOf(dataS.getUser().getId())));
		postParameters.add(new BasicNameValuePair("json", json));
		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			Log.i("PRE ADD", "Error 1");
		} catch (IOException e) {
			Log.i("PRE ADD", "Error 2");
		} catch (Exception e) {
			Log.i("PRE ADD", "Error 3");
		}
	}

	public ArrayList<Actividad> loadPersonalOnline () {
		ActividadArray act;
		ArrayList<Actividad> actividades = new ArrayList<Actividad>();
		String json = gson.toJson(dataS.getUser());

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("method", "load_actividad"));
		postParameters.add(new BasicNameValuePair("userID", String.valueOf(dataS.getUser().getId())));
		postParameters.add(new BasicNameValuePair("json", json));

		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(post);
			data = response.getEntity().getContent();
			Reader r = new InputStreamReader(data);

			act = (gson.fromJson(r, ActividadArray.class));
			for (int j=0; j<act.getCount(); j++)
				actividades.add(act.getActividades()[j]);

		} catch (ClientProtocolException e) {
			Log.i("ClientProtocolException", e.getMessage());
		} catch (JsonSyntaxException e) {
			Log.i("JsonSyntaxException", e.getMessage());
		} catch (JsonIOException e) {
			Log.i("JsonIOException", e.getMessage());
		} catch (IOException e) {
			Log.i("IOException", e.getMessage());
		} catch (Exception e) {
			Log.i("Exception", e.getMessage());
		}
		return actividades;
	}

	public void updatePersonalOnline (Actividad actividad) {
		String json = gson.toJson(actividad);

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("method", "update_actividad"));
		postParameters.add(new BasicNameValuePair("userID", String.valueOf(dataS.getUser().getId())));
		postParameters.add(new BasicNameValuePair("activityID", String.valueOf(actividad.getId())));
		postParameters.add(new BasicNameValuePair("json", json));
		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			Log.i("PRE UPDATE", "Error 1");
		} catch (IOException e) {
			Log.i("PRE UPDATE", "Error 2");
		} catch (Exception e) {
			Log.i("PRE UPDATE", "Error 3");
		}
	}

	public void deletePersonalOnline (int activityID) {

		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("method", "delete_actividad"));
		postParameters.add(new BasicNameValuePair("userID", String.valueOf(dataS.getUser().getId())));
		postParameters.add(new BasicNameValuePair("activityID", String.valueOf(activityID)));
		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			Log.i("PRE DELETE", "Error 1");
		} catch (IOException e) {
			Log.i("PRE DELETE", "Error 2");
		} catch (Exception e) {
			Log.i("PRE DELETE", "Error 3");
		}
	}

	public void sharePersonalOnline (int id, String share) {
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("method", "share_actividad"));
		postParameters.add(new BasicNameValuePair("userID", String.valueOf(dataS.getUser().getId())));
		postParameters.add(new BasicNameValuePair("activityID", String.valueOf(id)));
		postParameters.add(new BasicNameValuePair("share", share));
		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			Log.i("PRE SHARE", "Error 1");
		} catch (IOException e) {
			Log.i("PRE SHARE", "Error 2");
		} catch (Exception e) {
			Log.i("PRE SHARE", "Error 3");
		}
	}

}