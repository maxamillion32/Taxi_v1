package it.mahd.taxi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.mahd.taxi.Main;
import it.mahd.taxi.R;
import it.mahd.taxi.model.FragmentDrawer;

/**
 * Created by salem on 2/13/16.
 */
public class Home extends Fragment {
    private static final String Tag_url = "url";
    private static final String Tag_token = "token";
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    SharedPreferences pref;
    public Home() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        pref = getActivity().getSharedPreferences("AppTaxi", Context.MODE_PRIVATE);

        Button Now_btn = (Button) rootView.findViewById(R.id.btn_now);
        Now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return commantre annd delete this
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_body, new BookNow());
                ft.commit();
                ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.now));
                /*if (pref.getString(Tag_token, "").equals("")){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new Login());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.login));
                }else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new BookNow());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.now));
                }*/
            }
        });

        Button Advance_btn = (Button) rootView.findViewById(R.id.btn_advance);
        Advance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getString(Tag_token, "").equals("")){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new Login());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.login));
                }else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new BookAdvance());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.advance));
                }
            }
        });

        Button Reclamation_btn = (Button) rootView.findViewById(R.id.btn_reclamation);
        Reclamation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getString(Tag_token, "").equals("")){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new Login());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.login));
                }else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new Reclamation());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.reclamation));
                }
            }
        });

        Button Profile_btn = (Button) rootView.findViewById(R.id.btn_profile);
        Profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref.getString(Tag_token, "").equals("")){
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new Login());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.login));
                }else{
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_body, new Profile());
                    ft.commit();
                    ((Main) getActivity()).getSupportActionBar().setTitle(getString(R.string.profile));
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
