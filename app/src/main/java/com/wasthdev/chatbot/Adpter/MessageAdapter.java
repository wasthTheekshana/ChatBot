package com.wasthdev.chatbot.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasthdev.chatbot.Model.Message;
import com.wasthdev.chatbot.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    ArrayList<Message> messageArrayList ;
    public Context context;

    public MessageAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType){
            case 0 :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_user,parent,false);
                return new UserViewHolder(view);
            case 1 :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_bot,parent,false);
                return new BotViewHolder(view);
        }

        return null;
    }

    //bind the view to layout file
    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {

        Message messageModel = messageArrayList.get(position);

        switch (messageModel.getSender()){
            case "user" :
                ((UserViewHolder)holder).userText.setText(messageModel.getMessage());
                break;
            case "bot" :
                ((BotViewHolder)holder).botText.setText(messageModel.getMessage());
                break;

        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (messageArrayList.get(position).getSender()){
            case "user" :
                return 0;
            case "bot" :
                return 1;
            default:
                return -1;
        }
    }

    //get user style text
    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.userTest);
        }
    }

    //get bot style text
    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView botText;


        public BotViewHolder(@NonNull  View itemView) {
            super(itemView);
            botText = itemView.findViewById(R.id.botText);
        }
    }
}
