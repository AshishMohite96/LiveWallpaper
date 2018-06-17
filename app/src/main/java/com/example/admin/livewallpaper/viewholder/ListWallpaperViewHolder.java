package com.example.admin.livewallpaper.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.livewallpaper.R;
import com.example.admin.livewallpaper.interfaces.ItemClickListener;

public class ListWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    ItemClickListener itemClickListener;

    public ImageView wallpaper;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListWallpaperViewHolder(View itemView) {
        super(itemView);

        wallpaper = (ImageView) itemView.findViewById(R.id.wallpaper_image_view);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
