package com.sitp2017.hujiepingtai6;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by gameben on 2017/5/5.
 */
public class IntroductionAdapter extends RecyclerView.Adapter<IntroductionAdapter.ViewHolder> {

    //AppIntroductionPage中RecyclerView的适配器

    private List<IntroductionItem> mIntroductionList;

    //继承RecyclerView中的ViewHolder，并根据自己的情况加上内容
    //ViewHolder中存放了布局子项中的各个控件的实例 ，但是没有对每个子项的控件的内容赋具体值实例化
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView introductionImage;
        TextView introductionName;
        TextView introductionBrief;

        public ViewHolder(View itemView) {
            super(itemView);//初始化存放view的ViewHolder,让每个view只被加载一次
            introductionImage = (ImageView) itemView.findViewById(R.id.introduction_image);
            introductionName = (TextView) itemView.findViewById(R.id.introduction_name);
            introductionBrief = (TextView) itemView.findViewById(R.id.introduction_brief);
        }
    }

    public IntroductionAdapter(List<IntroductionItem> introductionList) {
        mIntroductionList = introductionList;
    }

    //必须重写下列3个方法
    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.introduction_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //对每个子项赋值，在每个子项滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IntroductionItem mItem = mIntroductionList.get(position); //获取实例
        holder.introductionName.setText(mItem.getName());
        holder.introductionBrief.setText(mItem.getBrief());
        holder.introductionImage.setImageResource(mItem.getImageId());
    }

    @Override
    public int getItemCount() {
        return mIntroductionList.size();
    }
}
