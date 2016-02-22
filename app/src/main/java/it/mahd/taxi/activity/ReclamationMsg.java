package it.mahd.taxi.activity;

import java.util.ArrayList;
import java.util.Random;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import it.mahd.taxi.R;
import it.mahd.taxi.util.Calculator;
import it.mahd.taxi.util.ChatAdapter;
import it.mahd.taxi.util.ChatMessage;

/**
 * Created by salem on 2/19/16.
 */
public class ReclamationMsg extends Fragment implements OnClickListener {
    private EditText msg_edittext;
    private String user1 = "khushi", user2 = "khushi1";
    private Random random;
    public static ArrayList chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reclamation_msg, container, false);

        String x = getArguments().getString("id");
        Toast.makeText(getActivity(), x, Toast.LENGTH_LONG).show();
        random = new Random();
        msg_edittext = (EditText) rootView.findViewById(R.id.message_etxt);
        msgListView = (ListView) rootView.findViewById(R.id.msgListView);
        FloatingActionButton sendButton = (FloatingActionButton) rootView.findViewById(R.id.send_btn);
        sendButton.setOnClickListener(this);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList();
        chatAdapter = new ChatAdapter(getActivity(), chatlist);
        msgListView.setAdapter(chatAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final ChatMessage chatMessage = new ChatMessage(user1, user2, message, "" + random.nextInt(1000), true);
            chatMessage.setMsgID();
            chatMessage.body = message;
            chatMessage.Date = Calculator.getCurrentDate();
            chatMessage.Time = Calculator.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
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
