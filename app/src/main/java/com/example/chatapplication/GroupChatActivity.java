package com.example.chatapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GroupChatActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1; // Request code for image selection
    private DatabaseReference databaseReference;
    private String groupName;
    private String userEmailId;
    private String userName;
    private ArrayList<Message> messageList;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private TextView et_groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        groupName = getIntent().getStringExtra("GROUP_NAME");
        userEmailId = getIntent().getStringExtra("USER_EMAIL");
        userName = getIntent().getStringExtra("USER_NAME");
        databaseReference = FirebaseDatabase.getInstance().getReference("groupChats").child(groupName).child("messages");

        et_groupName = findViewById(R.id.topBandTextView);
        et_groupName.setText(groupName.toUpperCase());

        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        EditText messageEditText = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);
        Button imageButton = findViewById(R.id.imageButton);

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message, null); // Send text message only
                messageEditText.setText("");
            } else {
                Toast.makeText(GroupChatActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        imageButton.setOnClickListener(v -> selectImage()); // Trigger image selection

        loadMessages();
    }

    private void loadMessages() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear(); // Clear the list before adding new messages
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    messageList.add(message);
                }
                messageAdapter.notifyDataSetChanged(); // Notify the adapter to refresh the UI
                recyclerView.scrollToPosition(messageList.size() - 1); // Scroll to the bottom
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GroupChatActivity.this, "Failed to load messages: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage(String message, String imageUrl) {
        String messageId = databaseReference.push().getKey();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(new Date());
        Message newMessage = new Message(userEmailId, userName, message, timestamp, imageUrl); // Include image URL

        databaseReference.child(messageId).setValue(newMessage)
                .addOnSuccessListener(aVoid -> Toast.makeText(GroupChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(GroupChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            sendMessage("", imageUri.toString()); // Send empty text and the image URL
        }
    }

    public static class Message {
        public String senderEmail;
        public String senderName;
        public String text;
        public String timestamp;
        public String imageUrl; // Field for the image URL

        public Message() {
            // Default constructor required for calls to DataSnapshot.getValue(Message.class)
        }

        public Message(String senderEmail, String senderName, String text, String timestamp, String imageUrl) {
            this.senderEmail = senderEmail;
            this.senderName = senderName;
            this.text = text;
            this.timestamp = timestamp;
            this.imageUrl = imageUrl; // Initialize the image URL
        }
    }
}
