package it.mahd.taxi.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import it.mahd.taxi.Main;
import it.mahd.taxi.R;

/**
 * Created by salem on 2/19/16.
 */
public class ReclamationMsg extends ListFragment {
    //private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter adapter;
    EditText msgEdit;
    FloatingActionButton AddReclamation_btn;
    private ArrayAdapter chatArrayAdapter;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reclamation_msg, container, false);

        chatArrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.right);
        //listView.setAdapter(chatArrayAdapter);
        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.chat_list_item,
                null,
                new String[]{"hi", "hello"},
                new int[]{R.id.text1, R.id.text2},
                0);
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch (view.getId()) {
                    case R.id.text1:
                        LinearLayout root = (LinearLayout) view.getParent().getParent();
                        if (cursor.getString(cursor.getColumnIndex("sousse")) == null) {
                            root.setGravity(Gravity.RIGHT);
                            root.setPadding(50, 10, 10, 10);
                        } else {
                            root.setGravity(Gravity.LEFT);
                            root.setPadding(10, 10, 50, 10);
                        }
                        break;
                }
                return false;
            }
        });
        setListAdapter(adapter);

        msgEdit = (EditText) rootView.findViewById(R.id.message_etxt);
        AddReclamation_btn = (FloatingActionButton) rootView.findViewById(R.id.send_btn);
        AddReclamation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues(2);
                values.put("hi", msgEdit.getText().toString());
                values.put("hello", "seen");
                getActivity().getContentResolver().insert(Uri.parse("xxxx"), values);
            }
        });
        return rootView;
    }
}
