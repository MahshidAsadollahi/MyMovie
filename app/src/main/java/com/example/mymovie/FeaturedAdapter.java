package com.example.mymovie;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder> {
    private List<FeatureModel>dataModels ;

    public FeaturedAdapter(List<FeatureModel> dataModels) {
        this.dataModels = dataModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        holder.textView.setText(dataModels.get(position).getFtitle());
        Glide.with(holder.itemView.getContext()).load(dataModels.get(position).getFthumb()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user clicks on movie item we will send data to details activity
                Intent sendDataToDetailsActivity=new Intent(holder.itemView.getContext(),DetailsActivity.class);
                sendDataToDetailsActivity.putExtra("title",dataModels.get(holder.getAdapterPosition()).getFtitle());
                sendDataToDetailsActivity.putExtra("link",dataModels.get(holder.getAdapterPosition()).getFlink());
                sendDataToDetailsActivity.putExtra("cover",dataModels.get(holder.getAdapterPosition()).getFcover());
                sendDataToDetailsActivity.putExtra("thumb",dataModels.get(holder.getAdapterPosition()).getFthumb());
                sendDataToDetailsActivity.putExtra("desc",dataModels.get(holder.getAdapterPosition()).getFdes());
                sendDataToDetailsActivity.putExtra("cast",dataModels.get(holder.getAdapterPosition()).getFcast());
                sendDataToDetailsActivity.putExtra("t_link",dataModels.get(holder.getAdapterPosition()).getTlink());


                //transition animation

                ActivityOptionsCompat optionsCompat= ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity)holder.
                                        itemView.getContext(),holder.imageView,
                                "ImageMain");

                holder.itemView.getContext().startActivity(sendDataToDetailsActivity,optionsCompat.toBundle());



            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.movie_title);
        }
    }
}
