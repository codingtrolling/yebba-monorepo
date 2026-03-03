package com.yebba.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ME = 1;
    private static final int TYPE_THEM = 2;
    private List<MessageModel> messages;
    private String currentUserId = "User_Redmi"; // This would come from YEBBA Services later

    public ChatAdapter(List<MessageModel> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).sender.equals(currentUserId)) {
            return TYPE_ME;
        } else {
            return TYPE_THEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_me, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_them, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messages.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).text.setText(message.message);
        } else {
            ((ReceivedViewHolder) holder).text.setText(message.message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        SentViewHolder(View v) { super(v); text = v.findViewById(R.id.text_message); }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ReceivedViewHolder(View v) { super(v); text = v.findViewById(R.id.text_message); }
    }
}
