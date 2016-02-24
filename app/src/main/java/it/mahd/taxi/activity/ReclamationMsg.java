package it.mahd.taxi.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.mahd.taxi.R;
import it.mahd.taxi.util.ChatAdapter;
import it.mahd.taxi.util.ChatMessage;
import it.mahd.taxi.util.Controllers;
import it.mahd.taxi.util.Encrypt;
import it.mahd.taxi.util.ServerRequest;

/**
 * Created by salem on 2/19/16.
 */
public class ReclamationMsg extends Fragment implements OnClickListener {
    SharedPreferences pref;
    Controllers conf = new Controllers();
    ServerRequest sr = new ServerRequest();
    Encrypt algo = new Encrypt();
    private EditText Message_etxt;
    private FloatingActionButton sendButton;
    private String idRec;
    public static ArrayList chatlist;
    public static ChatAdapter chatAdapter;
    ListView Message_lv;
    JSONArray loads = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reclamation_msg, container, false);

        idRec = getArguments().getString(conf.tag_id);
        Message_etxt = (EditText) rootView.findViewById(R.id.message_etxt);
        Message_lv = (ListView) rootView.findViewById(R.id.msg_lv);
        sendButton = (FloatingActionButton) rootView.findViewById(R.id.send_btn);
        sendButton.setOnClickListener(this);

        Message_lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        Message_lv.setStackFromBottom(true);

        chatlist = new ArrayList();
        chatAdapter = new ChatAdapter(getActivity(), chatlist);
        Message_lv.setAdapter(chatAdapter);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(conf.tag_id, idRec));
        JSONObject json = sr.getJSON(conf.url_getMessage, params);
        if(json != null) {
            try {
                if (json.getBoolean("res")) {
                    loads = json.getJSONArray("data");
                    for (int i = 0; i < loads.length(); i++) {
                        JSONObject c = loads.getJSONObject(i);
                        Boolean sender = c.getBoolean(conf.tag_sender);
                        String message = c.getString(conf.tag_message);
                        String date = c.getString(conf.tag_date);
                        final ChatMessage chatMessage = new ChatMessage(message, date, sender);
                        chatAdapter.add(chatMessage);
                        chatAdapter.notifyDataSetChanged();
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = Message_etxt.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            int x = algo.keyVirtual();
            String key = algo.key(x);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(conf.tag_id, idRec));
            params.add(new BasicNameValuePair(conf.tag_message, algo.dec2enc(message, key)));
            params.add(new BasicNameValuePair(conf.tag_key, x + ""));
            JSONObject json = sr.getJSON(conf.url_addMessage, params);
            if(json != null){
                try{
                    if(json.getBoolean("res")){
                        int keyVirtual = Integer.parseInt(json.getString(conf.tag_key));
                        String newKey = algo.key(keyVirtual);
                        String date = algo.enc2dec(json.getString(conf.tag_date), newKey);
                        final ChatMessage chatMessage = new ChatMessage(message, date, true);
                        Message_etxt.setText("");
                        chatAdapter.add(chatMessage);
                        chatAdapter.notifyDataSetChanged();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                sendTextMessage(v);
        }
    }
}
