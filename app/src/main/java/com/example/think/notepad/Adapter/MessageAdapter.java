package com.example.think.notepad.Adapter;

import android.media.MediaPlayer;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.think.notepad.Bean.message;
import com.example.think.notepad.Contracts;
import com.example.think.notepad.R;

import java.io.IOException;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> implements View.OnClickListener{
    private List<message> messageList;
    private OnItemClickListener mItemClickListener;
    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView user;
        TextView text;
        public ViewHolder(View view){
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            user = (TextView) view.findViewById(R.id.username);
            text = (TextView) view.findViewById(R.id.text);
        }
    }
    public MessageAdapter(List<message> messageList){
        this.messageList = messageList;
    }
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message,viewGroup,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        message msg = messageList.get(i);
        viewHolder.time.setText(msg.getTime());
        viewHolder.user.setText(msg.getUser());
        viewHolder.text.setText(msg.getText());
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
