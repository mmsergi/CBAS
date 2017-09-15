package sergi.crowdbuy;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import sergi.crowdbuy.adapters.ListMessageListAdapter;
import sergi.crowdbuy.objects.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    ListView listView;
    ListMessageListAdapter listViewAdapter;

    ArrayList<ChatMessage> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.list_of_messages);
        listViewAdapter = new ListMessageListAdapter(this, chatList);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chats")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail())
                        );

                // Clear the input
                input.setText("");
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chats");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                chatList.clear();

                for (DataSnapshot item:dataSnapshot.getChildren()) {

                    String key = item.getKey().toString();

                    Map<String, Object> data = (Map<String, Object>) item.getValue();

                    String text = "", user = "";
                    Long time = 0L;

                    if (data.containsKey("messageText")) text = (String) data.get("messageText");
                    if (data.containsKey("messageTime")) time = (Long) data.get("messageTime");
                    if (data.containsKey("messageUser")) user = (String) data.get("messageUser");

                    Log.e("TEST", key);

                    ChatMessage msg = new ChatMessage();
                    msg.setMessageText(text);
                    msg.setMessageTime(time);
                    msg.setMessageUser(user);

                    chatList.add(msg);
                }

                listViewAdapter.notifyDataSetChanged();
                listView.setAdapter(listViewAdapter);
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        listView.setSelection(listViewAdapter.getCount() - 1);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
