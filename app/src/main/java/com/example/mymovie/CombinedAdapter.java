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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CombinedAdapter extends RecyclerView.Adapter<CombinedAdapter.MyViewHolder> {

    private List<CombinedModel>combinedModels;

    public CombinedAdapter(List<CombinedModel> combinedModels) {
        this.combinedModels = combinedModels;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view;
        if (viewType == 0) {
            // movies
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            lp.width = (int) (parent.getWidth() / 2.5); // set the width of the item to half of the parent width
            lp.height = (int) (lp.width * 1.3);
            view.setLayoutParams(lp);
        } else {
            // series
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
            GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            lp.width = (int) (parent.getWidth() / 2.5);
            lp.height = (int) (lp.width * 1.3); // set the height to a 2:3 aspect ratio
            view.setLayoutParams(lp);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CombinedModel currentItem=combinedModels.get(position);

        if(currentItem.isMovie()){
            //movies
            holder.textView.setText(combinedModels.get(position).getFtitle());
            Glide.with(holder.itemView.getContext()).load(combinedModels.get(position).getFthumb()).into(holder.imageView);


        }else{


        holder.title.setText(combinedModels.get(position).getStitle());
        Glide.with(holder.imageView.getContext()).load(combinedModels.
                get(position).getSthumb()).into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user clicks on movie item we will send data to details activity
                Intent sendDataToDetailsActivity=new Intent(holder.itemView.getContext(),DetailsActivity.class);
                if(currentItem.isMovie()) {

                    //movies
                    sendDataToDetailsActivity.putExtra("title", combinedModels.get(holder.getAdapterPosition()).getFtitle());
                    sendDataToDetailsActivity.putExtra("link", combinedModels.get(holder.getAdapterPosition()).getFlink());
                    sendDataToDetailsActivity.putExtra("cover", combinedModels.get(holder.getAdapterPosition()).getFcover());
                    sendDataToDetailsActivity.putExtra("thumb", combinedModels.get(holder.getAdapterPosition()).getFthumb());
                    sendDataToDetailsActivity.putExtra("desc", combinedModels.get(holder.getAdapterPosition()).getFdes());
                    sendDataToDetailsActivity.putExtra("cast", combinedModels.get(holder.getAdapterPosition()).getFcast());
                    sendDataToDetailsActivity.putExtra("t_link", combinedModels.get(holder.getAdapterPosition()).getTlink());

                }else {
                //series
                    sendDataToDetailsActivity.putExtra("title", combinedModels.get(holder.getAdapterPosition()).getStitle());
                    sendDataToDetailsActivity.putExtra("link", combinedModels.get(holder.getAdapterPosition()).getSlink());
                    sendDataToDetailsActivity.putExtra("cover", combinedModels.get(holder.getAdapterPosition()).getScover());
                    sendDataToDetailsActivity.putExtra("thumb", combinedModels.get(holder.getAdapterPosition()).getSthumb());
                    sendDataToDetailsActivity.putExtra("desc", combinedModels.get(holder.getAdapterPosition()).getSdesc());
                    sendDataToDetailsActivity.putExtra("cast", combinedModels.get(holder.getAdapterPosition()).getScast());
                    sendDataToDetailsActivity.putExtra("st_link", combinedModels.get(holder.getAdapterPosition()).getSTlink());


                }
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
    public int getItemViewType(int position) {
        return combinedModels.get(position).isMovie() ? 0 : 1;
    }


    @Override
    public int getItemCount() {
        return combinedModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView imageView2;
        TextView title;
        TextView textView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2=itemView.findViewById(R.id.imageView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.movie_title);
            title=itemView.findViewById(R.id.movie_title);

        }
    }
}

