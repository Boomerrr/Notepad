package com.example.think.notepad.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.R;

import java.util.List;

public class NotepadeAdapter extends RecyclerView.Adapter<NotepadeAdapter.ViewHolder>{
    private List<NotePad> notePadList;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView text;
        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            text = (TextView) view.findViewById(R.id.text);
        }
    }
    public NotepadeAdapter(List<NotePad> notePadList){
        this.notePadList = notePadList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notepad,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"短按",Toast.LENGTH_SHORT).show();
            }
        });
        holder.text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "长按", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotePad notePad = notePadList.get(i);
        viewHolder.title.setText(notePad.getText()+"  "+notePad.getOrderTime());
        viewHolder.text.setText(notePad.getText());
    }


    @Override
    public int getItemCount() {
        return notePadList.size();
    }
    public interface OnClickListener {
        void onClick(View view, int position);
    }

}
