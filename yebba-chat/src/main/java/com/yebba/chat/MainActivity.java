package com.yebba.chat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ChatAdapter adapter;
    private List<MessageModel> messageList;
    private EditText editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        messageList = new ArrayList<>();
        editMessage = findViewById(R.id.edit_message);
        
        RecyclerView recyclerView = findViewById(R.id.chat_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(messageList);
        recyclerView.setAdapter(adapter);

        // Listen for new messages
        db.collection("chats")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener((value, error) -> {
                if (value != null) {
                    messageList.clear();
                    messageList.addAll(value.toObjects(MessageModel.class));
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageList.size() - 1);
                }
            });

        findViewById(R.id.btn_send).setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String msg = editMessage.getText().toString();
        if (!msg.isEmpty()) {
            MessageModel model = new MessageModel("User_Redmi", msg, System.currentTimeMillis());
            db.collection("chats").add(model);
            editMessage.setText("");
        }
    }
}
