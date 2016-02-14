package it.mahd.taxi.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.mahd.taxi.R;

/**
 * Created by salem on 2/14/16.
 */
public class Settings extends Fragment {
    SharedPreferences pref;

    public Settings() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings, container, false);

        pref = getActivity().getSharedPreferences("AppTaxi", Context.MODE_PRIVATE);
        return rootView;
    }
}
