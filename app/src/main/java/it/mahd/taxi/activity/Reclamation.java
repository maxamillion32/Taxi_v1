package it.mahd.taxi.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

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
import it.mahd.taxi.util.ServerRequest;

/**
 * Created by salem on 2/13/16.
 */
public class Reclamation extends ListFragment {
    private static final String Tag_url = "url";
    private static final String Tag_getAllReclamation = "/getAllReclamation";
    private static final String Tag_token = "token";
    private static final String Tag_id = "_id";
    private static final String Tag_subject = "subject";
    private static final String Tag_date = "date";
    private static final String Tag_status = "status";
    ArrayList<HashMap<String, String>> ReclamationList;
    JSONArray loads = null;
    SharedPreferences pref;
    ServerRequest sr = new ServerRequest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reclamation, container, false);

        pref = getActivity().getSharedPreferences("AppTaxi", Context.MODE_PRIVATE);
        int[] image = new int[] { R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Tag_token, pref.getString(Tag_token, "")));
        ReclamationList = new ArrayList<HashMap<String, String>>();
        JSONObject json = sr.getJSON(pref.getString(Tag_url, "") + Tag_getAllReclamation, params);
        if(json != null){
            try{
                if(json.getBoolean("res")) {
                    loads = json.getJSONArray("data");
                    for (int i = 0; i < loads.length(); i++) {
                        JSONObject c = loads.getJSONObject(i);
                        String id = c.getString(Tag_id);
                        String subject = c.getString(Tag_subject);
                        String date = c.getString(Tag_date);
                        Boolean status = c.getBoolean(Tag_status);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(Tag_id, id);
                        map.put(Tag_subject, subject);
                        map.put(Tag_date, date);
                        map.put(Tag_status, (status) ? Integer.toString(image[0]) : Integer.toString(image[1]));
                        ReclamationList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ListAdapter adapter = new SimpleAdapter(getActivity(), ReclamationList, R.layout.reclamation_list,
                new String[] { Tag_status, Tag_subject, Tag_date, Tag_id }, new int[] { R.id.picture_iv, R.id.txt_name, R.id.txt_date, R.id._id });
        setListAdapter(adapter);

        FloatingActionButton AddReclamation_btn = (FloatingActionButton) rootView.findViewById(R.id.add_btn);
        AddReclamation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, new ReclamationMsg());
                ft.commit();
                ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.reclamation));
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
