package cl.edenprime.survivor.subjects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import cl.edenprime.survivor.R;
import cl.edenprime.survivor.ViewSubjectActivity;
import cl.edenprime.survivor.obj.dataS;

public class SubjectsListFragment extends Fragment {
	
	// Elementos del Layout
	private LinearLayout content;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.subjects_list_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		content = (LinearLayout) getView().findViewById(R.id.SubjectsList);
		content.removeAllViews();
		
		for (int i = 0; i < dataS.getRamosINS().size(); i++) {
			View vista = View.inflate(getActivity(), R.layout.subjects_list_boxes, null);
			TextView sigla = (TextView) vista.findViewById(R.id.SubjectListSigla);
			TextView nombre = (TextView) vista.findViewById(R.id.SubjectListName);
			LinearLayout layout = (LinearLayout) vista.findViewById(R.id.SubjectListClick);
			sigla.setText(dataS.getRamosINS().get(i).getSigla());
			nombre.setText(dataS.getRamosINS().get(i).getNombre());
			layout.setTag(i);
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					launchSubject(v);
				}
			});
			content.addView(vista);
		}
	}
	
	public void launchSubject(View v) {
		Intent subject = new Intent(getActivity(), ViewSubjectActivity.class);
		subject.putExtra("SUBJECT", Integer.valueOf(v.getTag().toString()));
		startActivity(subject);
	}
	
}
