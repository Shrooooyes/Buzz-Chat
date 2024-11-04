package com.example.chatapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private ArrayList<GroupChatActivity.Message> messages;
    private Context context;

    public MessageAdapter(ArrayList<GroupChatActivity.Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        GroupChatActivity.Message message = messages.get(position);
        holder.senderNameTextView.setText(message.senderName);
        holder.messageTextView.setText(message.text);

        // Check if the message has an image
        if (message.imageUrl != null && !message.imageUrl.isEmpty()) {
            holder.messageTextView.setVisibility(View.GONE); // Hide text view if there's an image
            holder.imageView.setVisibility(View.VISIBLE); // Show image view

            // Use Glide to load the image from the local storage
            Uri imageUri = Uri.parse(message.imageUrl);
            Glide.with(context)
                    .load(imageUri)
                    .into(holder.imageView);
        } else {
            holder.messageTextView.setVisibility(View.VISIBLE); // Show text view if no image
            holder.imageView.setVisibility(View.GONE); // Hide image view
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderNameTextView;
        TextView messageTextView;
        ImageView imageView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderNameTextView = itemView.findViewById(R.id.senderNameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            imageView = itemView.findViewById(R.id.imageView); // Reference to the image view
        }
    }
}
