package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pet> mData;
    private Dialog mDialog;

    RecyclerViewAdapter(Context mContext, List<Pet> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_pet,parent,false);
        final MyViewHolder viewHolder=new MyViewHolder(v);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_pet);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.item_pet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                TextView dialog_name_tv = mDialog.findViewById(R.id.dialog_name_id);
                TextView dialog_available_tv = mDialog.findViewById(R.id.dialog_available_id);
                TextView dialog_desc_tv = mDialog.findViewById(R.id.dialog_desc_call);
                ImageView dialog_pet_img = mDialog.findViewById(R.id.dialog_img);
                Button btn = mDialog.findViewById(R.id.dialog_desc_mail);

                dialog_desc_tv.setText("Description : "+mData.get(viewHolder.getAdapterPosition()).getDescription());
                dialog_name_tv.setText("Name : "+mData.get(viewHolder.getAdapterPosition()).getName());
                dialog_available_tv.setText("Quantity available : "+mData.get(viewHolder.getAdapterPosition()).getAvailable());


                Picasso.get()
                        .load(mData.get(viewHolder.getAdapterPosition()).getPhoto())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .into(dialog_pet_img);

                mDialog.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("IntentReset")
                    @Override
                    public void onClick(View view) {
                        String number = mContext.getResources().getString(R.string.number);
                        String url="https://wa.me/"+number+"/?text=I want to order one "+mData.get(viewHolder.getAdapterPosition()).getName();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                        i.setType("text/plain");
                        i.setData(Uri.parse(url));
                        mContext.startActivity(i);
                    }
                });

            }
        });
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_name.setText("Name : "+mData.get(position).getName());
        holder.tv_available.setText("Quantity available : "+mData.get(position).getAvailable());

        Picasso.get()
                .load(mData.get(position).getPhoto())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
//                .resize(50, 50)
//                .centerCrop()
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends  RecyclerView.ViewHolder{

        private LinearLayout item_pet;
        private TextView tv_name;
        private TextView tv_available;
        private ImageView img;

        MyViewHolder(View itemView) {
            super(itemView);

            item_pet = itemView.findViewById(R.id.pet_item_id);
            tv_name = itemView.findViewById(R.id.pet_name);
            tv_available = itemView.findViewById(R.id.pet_available);
            img = itemView.findViewById(R.id.img_pet);

        }
    }
}
