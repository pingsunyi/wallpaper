package com.example.pingsunyi.wallpaper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pingsunyi.wallpaper.Models.Collection;
import com.example.pingsunyi.wallpaper.R;
import com.example.pingsunyi.wallpaper.Utils.GlideApp;
import com.example.pingsunyi.wallpaper.Utils.SquareImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionsAdapter extends BaseAdapter {
    private Context context;
    private List<Collection> collections;
    public CollectionsAdapter(Context context, List<Collection> collections) {
        this.context = context;
        this.collections = collections;
    }
    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int i) {
        return collections.get(i);
    }

    @Override
    public long getItemId(int i) {
        return collections.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collection, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ButterKnife.bind(this, view);
        Collection collection = collections.get(i);
        if (collection.getTitle() != null) {
            holder.title.setText(collection.getTitle());
        }
        holder.totalPhotos.setText(String.valueOf(collection.getTotalPhotos()) + " photos");
        GlideApp
                .with(context)
                .load(collection.getCoverPhoto().getUrl().getRegular())
                .into(holder.collectionPhoto);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.item_collection_title)
        TextView title;
        @BindView(R.id.item_collection_total_photos)
        TextView totalPhotos;
        @BindView(R.id.item_collection_photo)
        SquareImage collectionPhoto;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
