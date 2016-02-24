package it.mahd.taxi.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.mahd.taxi.Main;
import it.mahd.taxi.R;
import it.mahd.taxi.util.Controllers;
import it.mahd.taxi.util.ServerRequest;

/**
 * Created by salem on 2/13/16.
 */
public class Reclamation extends ListFragment {
    SharedPreferences pref;
    Controllers conf = new Controllers();
    ServerRequest sr = new ServerRequest();
    ArrayList<HashMap<String, String>> ReclamationList;
    JSONArray loads = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reclamation, container, false);

        pref = getActivity().getSharedPreferences("AppTaxi", Context.MODE_PRIVATE);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(conf.tag_token, pref.getString(conf.tag_token, "")));
        ReclamationList = new ArrayList<HashMap<String, String>>();
        JSONObject json = sr.getJSON(conf.url_getAllReclamation, params);
        if(json != null){
            try{
                if(json.getBoolean("res")) {
                    loads = json.getJSONArray("data");
                    for (int i = 0; i < loads.length(); i++) {
                        JSONObject c = loads.getJSONObject(i);
                        String id = c.getString(conf.tag_id);
                        String subject = c.getString(conf.tag_subject);
                        String date = c.getString(conf.tag_date);
                        Boolean status = c.getBoolean(conf.tag_status);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(conf.tag_id, id);
                        map.put(conf.tag_subject, subject);
                        map.put(conf.tag_date, date);
                        map.put(conf.tag_status, (status) ? "Now" : "");
                        ReclamationList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ListAdapter adapter = new SimpleAdapter(getActivity(), ReclamationList, R.layout.reclamation_list,
                new String[] { conf.tag_subject, conf.tag_status, conf.tag_date, conf.tag_id },
                new int[] { R.id.subject_txt, R.id.status_txt, R.id.date_txt, R.id.idRec });
        setListAdapter(adapter);

        FloatingActionButton AddReclamation_btn = (FloatingActionButton) rootView.findViewById(R.id.add_btn);
        AddReclamation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, new ReclamationAdd());
                ft.commit();
                ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.reclamation_new));
            }
        });
        return rootView;
    }

    public void onListItemClick(ListView l, View view, int position, long id){
        ViewGroup viewg = (ViewGroup)view;
        TextView tv = (TextView) viewg.findViewById(R.id.idRec);
        Fragment fr = new ReclamationMsg();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString(conf.tag_id, tv.getText().toString());
        fr.setArguments(args);
        ft.replace(R.id.container_body, fr);
        ft.commit();
        ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.reclamation));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
