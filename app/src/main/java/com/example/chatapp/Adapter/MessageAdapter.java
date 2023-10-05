package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.HelperClass.MessageHelperClass;

import com.example.chatapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<MessageHelperClass> messageList;
    Context context;

    public MessageAdapter(List<MessageHelperClass> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        MessageHelperClass messageHelperClass = messageList.get(position);
        if(!messageHelperClass.getProfilePic().isEmpty()){
            Picasso.get().load(messageHelperClass.getProfilePic()).into(holder.profilePic);
        }
        holder.name.setText(messageHelperClass.getName());
        holder.lastMessage.setText(messageHelperClass.getLastMessage());
        if (messageHelperClass.getUnseenMessage() ==0){
            holder.unseenMessage.setVisibility(View.GONE);
        }else {
            holder.unseenMessage.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView profilePic;
        TextView name,lastMessage,unseenMessage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            unseenMessage = itemView.findViewById(R.id.unseenMessage);
        }
    }
}
