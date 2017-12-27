package com.sitp2017.hujiepingtai6;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gameben on 2017/5/9.
 */
public class PopupWindowAdapter extends RecyclerView.Adapter<PopupWindowAdapter.ViewHolder> {
    //弹出菜单中的RecyclerView的适配器

    private Context mContext;

    private List<PopupWindowItem> mItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView itemImage;
        TextView itemName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            itemImage = (ImageView) itemView.findViewById(R.id.popup_window_item_image);
            itemName = (TextView) itemView.findViewById(R.id.popup_window_item_name);
        }
    }

    public PopupWindowAdapter(List<PopupWindowItem> itemList) {
        super();
        mItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_window_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PopupWindowItem item = mItemList.get(position);
        holder.itemName.setText(item.getName());

        //用Glide加载图片可以自动压缩处理图片，有复杂的逻辑，不会出现内容溢出的现象
        Glide.with(mContext).load(item.getImageId()).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

}
