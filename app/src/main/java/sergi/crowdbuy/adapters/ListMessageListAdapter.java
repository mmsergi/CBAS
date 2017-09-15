package sergi.crowdbuy.adapters;


import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sergi.crowdbuy.R;
import sergi.crowdbuy.objects.ChatMessage;

public class ListMessageListAdapter extends ArrayAdapter<ChatMessage> {

    private Context context;
    private ArrayList<ChatMessage> arrayMessages;

    public ListMessageListAdapter(Context context, ArrayList<ChatMessage> arrayMessages) {
        super(context, R.layout.activity_main);
        this.context = context;
        this.arrayMessages = arrayMessages;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;

        v = inflater.inflate(R.layout.message, parent, false);

        TextView messageText = (TextView)v.findViewById(R.id.message_text);
        TextView messageUser = (TextView)v.findViewById(R.id.message_user);
        TextView messageTime = (TextView)v.findViewById(R.id.message_time);

        // Set their text
        messageText.setText(arrayMessages.get(position).getMessageText());
        messageUser.setText(arrayMessages.get(position).getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", arrayMessages.get(position).getMessageTime()));
        return v;
    }

    public int getCount() {
        return arrayMessages.size();
    }
}