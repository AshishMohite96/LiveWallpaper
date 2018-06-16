package com.example.admin.livewallpaper.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.livewallpaper.R;
import com.example.admin.livewallpaper.interfaces.ItemClickListener;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView categoryName;
    public ImageView backgroundImage;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);

        backgroundImage = (ImageView) itemView.findViewById(R.id.category_background_image);
        categoryName = (TextView) itemView.findViewById(R.id.category_name);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
