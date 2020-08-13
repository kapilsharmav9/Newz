package com.example.newz.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newz.Model.Article;
import com.example.newz.R;
import com.example.newz.Utils;

import java.util.List;



public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {
    private List<Article> articleList;
    Context context;
    private OnItemClickListener onItemClickListener;

    public RecycleAdapter (Context context,List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public RecycleAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemm, parent, false);
        return new Holder(view, onItemClickListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull final RecycleAdapter.Holder holder, int position) {
        Article article = articleList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(article.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.title.setText(article.getTitle());
        holder.desc.setText(article.getDescription());
        holder.source.setText(article.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(article.getPublishedAt()));
        holder.published.setText(Utils.DateFormat(article.getPublishedAt()));
        holder.author.setText(article.getAuthor());


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, author, desc, published, source, time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public Holder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.Title);
            author = itemView.findViewById(R.id.author);
            desc = itemView.findViewById(R.id.desc);
            imageView=itemView.findViewById(R.id.imagee);
            published = itemView.findViewById(R.id.published_At);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.Timee);
            progressBar=itemView.findViewById(R.id.progress_bar);
            this.onItemClickListener = onItemClickListener;
        }



        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }


}
