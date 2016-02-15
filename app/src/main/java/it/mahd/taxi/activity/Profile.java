package it.mahd.taxi.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.mahd.taxi.R;
import it.mahd.taxi.util.Encrypt;
import it.mahd.taxi.util.ServerRequest;

/**
 * Created by salem on 2/13/16.
 */
public class Profile extends Fragment {
    private static final String Tag_url = "url";
    private static final String Tag_profile = "/profile";
    private static final String Tag_token = "token";
    private static final String Tag_fname = "fname";
    private static final String Tag_lname = "lname";
    private static final String Tag_gender = "gender";
    private static final String Tag_dateN = "dateN";
    private static final String Tag_country = "country";
    private static final String Tag_city = "city";
    private static final String Tag_email = "email";
    private static final String Tag_phone = "phone";
    private static final String Tag_picture = "picture";
    private static final String Tag_key = "key";
    private String fname, lname, gender, dateN, country, city, email, phone, picture;
    TextView Username_txt, City_txt, Age_txt, Email_txt, Phone_txt;
    ImageView Picture_iv;
    ServerRequest sr = new ServerRequest();
    SharedPreferences pref;
    JSONArray loads = null;

    public Profile() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);

        pref = getActivity().getSharedPreferences("AppTaxi", Context.MODE_PRIVATE);
        Encrypt algo = new Encrypt();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(Tag_token, pref.getString(Tag_token, "")));
        ServerRequest sr = new ServerRequest();
        JSONObject json = sr.getJSON(pref.getString(Tag_url, "") + Tag_profile, params);
        if(json != null){
            try{
                if(json.getBoolean("res")){
                    loads = json.getJSONArray("data");
                    if(loads.length() != 0){
                        JSONObject c = loads.getJSONObject(0);
                        int keyVirtual = Integer.parseInt(json.getString(Tag_key));
                        String newKey = algo.key(keyVirtual);
                        fname = algo.enc2dec(c.getString(Tag_fname), newKey);
                        lname = algo.enc2dec(c.getString(Tag_lname), newKey);
                        gender = algo.enc2dec(c.getString(Tag_gender), newKey);
                        dateN = algo.enc2dec(c.getString(Tag_dateN), newKey);
                        country = algo.enc2dec(c.getString(Tag_country), newKey);
                        city = algo.enc2dec(c.getString(Tag_city), newKey);
                        email = algo.enc2dec(c.getString(Tag_email), newKey);
                        phone = algo.enc2dec(c.getString(Tag_phone), newKey);
                        picture = c.getString(Tag_picture);
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Username_txt = (TextView) rootView.findViewById(R.id.username_txt);
        Username_txt.setText(fname + " " + lname);
        Username_txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        City_txt = (TextView) rootView.findViewById(R.id.city_txt);
        City_txt.setText(fname + " " + lname);
        City_txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        Age_txt = (TextView) rootView.findViewById(R.id.age_txt);
        Age_txt.setText(fname + " " + lname);
        Age_txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        Email_txt = (TextView) rootView.findViewById(R.id.email_txt);
        Email_txt.setText(" " + email);
        Email_txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        Phone_txt = (TextView) rootView.findViewById(R.id.phone_txt);
        Phone_txt.setText(" " + phone);
        Phone_txt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        Picture_iv = (ImageView) rootView.findViewById(R.id.picture_iv);
        byte[] imageAsBytes = Base64.decode(picture.getBytes(), Base64.DEFAULT);
        Picture_iv.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        return rootView;
    }
}
