package com.example.admin.livewallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.livewallpaper.ListWallpaperActivity;
import com.example.admin.livewallpaper.R;
import com.example.admin.livewallpaper.ViewWallpaperActivity;
import com.example.admin.livewallpaper.database.RecentsImages;
import com.example.admin.livewallpaper.interfaces.ItemClickListener;
import com.example.admin.livewallpaper.model.WallpaperItem;
import com.example.admin.livewallpaper.utils.Common;
import com.example.admin.livewallpaper.viewholder.ListWallpaperViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecentAdapter extends RecyclerView.Adapter<ListWallpaperViewHolder> {

    private Context context;
    private List<RecentsImages> recents;

    public MyRecentAdapter(Context context, List<RecentsImages> recents) {
        this.context = context;
        this.recents = recents;
    }

    @Override
    public ListWallpaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_list_row_item, parent, false);

        int height = parent.getMeasuredHeight()/2;
        view.setMinimumHeight(height);
        return new ListWallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListWallpaperViewHolder holder, final int position) {
        Picasso.with(context)
                .load(recents.get(position).getImageLink())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.wallpaper, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(recents.get(position).getImageLink())
                                .error(R.drawable.ic_terrain_black_24dp)
                                .into(holder.wallpaper, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("ERROR", "Could not fetch image");
                                    }
                                });
                    }
                });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ViewWallpaperActivity.class);
                WallpaperItem wallpaperItem = new WallpaperItem();
                wallpaperItem.setCategoryId(recents.get(position).getCategoryId());
                wallpaperItem.setImageLink(recents.get(position).getImageLink());
                Common.select_background = wallpaperItem;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }
}
