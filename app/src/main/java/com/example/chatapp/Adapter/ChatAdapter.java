package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.HelperClass.ChatHelperClass;
import com.example.chatapp.MemoryData;
import com.example.chatapp.R;


import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    List<ChatHelperClass> chatList;
    Context context;
    String userMobile;

    public ChatAdapter(List<ChatHelperClass> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
        this.userMobile = MemoryData.getData(context);
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        ChatHelperClass Lists = chatList.get(position);
        if (Lists.getMobile().equals(userMobile)){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.opLayout.setVisibility(View.GONE);

            holder.myMessage.setText(Lists.getMessage());
            holder.myTime.setText(Lists.getDate()+""+Lists.getTime());
        }else {
            holder.myLayout.setVisibility(View.GONE);
            holder.opLayout.setVisibility(View.VISIBLE);

            holder.opMessage.setText(Lists.getMessage());
            holder.opTime.setText(Lists.getDate()+""+Lists.getTime());

        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
    public void updateChatList(List<ChatHelperClass> chatList){
        this.chatList = chatList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout opLayout,myLayout;
        TextView opMessage,myMessage;
        TextView opTime,myTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            opLayout = itemView.findViewById(R.id.opLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            opMessage = itemView.findViewById(R.id.opMessage);
            myMessage = itemView.findViewById(R.id.myMessage);
            opTime = itemView.findViewById(R.id.opMsgTime);
            myTime = itemView.findViewById(R.id.myMsgTime);
        }
    }
}
