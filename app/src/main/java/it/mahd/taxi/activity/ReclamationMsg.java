package it.mahd.taxi.activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Random;

import it.mahd.taxi.R;

/**
 * Created by salem on 2/19/16.
 */
public class ReclamationMsg extends ListFragment {
    private EditText msg_edittext;
    private String user1 = "khushi", user2 = "khushi1";
    private Random random;
    ListView msgListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reclamation_msg, container, false);


        return rootView;
    }
}
