package cl.edenprime.survivor.first;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import cl.edenprime.survivor.FirstTimeActivity;
import cl.edenprime.survivor.R;
import cl.edenprime.survivor.dao.WebServices;

public class RegisterFragment extends Fragment {
	
	// Elementos del Layout
	private EditText user;
	private EditText pass;
	
	// Seleccionados
	private String Suser;
	private String Spass;
	
	// Tokens
	private boolean isUser;
	private boolean isPass;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.firsttime5, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user = (EditText) getView().findViewById(R.id.FirstTimeUser);
        pass = (EditText) getView().findViewById(R.id.FirstTimePass);
        
        isUser = false;
        isPass = false;
        
        user.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setUser();
			}
		});
        
        pass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPass();
			}
		});
        
        FirstTimeActivity.working.setVisibility(View.GONE);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saveUser();
	}
	
	public void setUser() {
		final EditText input = new EditText(getActivity());
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder
			.setTitle(R.string.user)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            Suser = input.getText().toString().trim();
		            user.setText(Suser);
		            if (Suser.equals(""))
		            	isUser = false;
		            else
		            	isUser = true;
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void setPass() {
		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder
			.setTitle(R.string.pass)
			.setView(input)
			.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            Spass = input.getText().toString().trim();
		            pass.setText(Spass);
		            if (Spass.equals(""))
		            	isPass = false;
		            else
		            	isPass = true;
		        }
		    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void saveUser() {
		if (isUser && isPass) {
			WebServices web = new WebServices();
			web.register(Suser, Spass);
		}
	}
	
}
