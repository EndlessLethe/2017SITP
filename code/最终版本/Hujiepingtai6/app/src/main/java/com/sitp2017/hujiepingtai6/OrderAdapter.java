package com.sitp2017.hujiepingtai6;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gameben on 2017/8/18.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> mOrderList;

    //继承RecyclerView中的ViewHolder，并根据自己的情况加上内容
    //ViewHolder中存放了布局子项中的各个控件的实例 ，但是没有对每个子项的控件的内容赋具体值实例化
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderTime;
        TextView orderStatus;
        TextView orderGoodType;

        public ViewHolder(View itemView) {
            super(itemView);//初始化存放view的ViewHolder,让每个view只被加载一次
            orderTime = (TextView) itemView.findViewById(R.id.order_time);
            orderStatus = (TextView) itemView.findViewById(R.id.order_status);
            orderGoodType = (TextView) itemView.findViewById(R.id.order_good_type);
        }
    }

    public OrderAdapter(List<Order> orderList) {
        mOrderList = orderList;
    }

    //必须重写下列3个方法
    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //对每个子项赋值，在每个子项滚动到屏幕内时执行
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrderList.get(position); //获取实例
        holder.orderTime.setText(order.getTimeString());
        holder.orderStatus.setText(order.getStatusString());
        holder.orderGoodType.setText(order.getGoodTypeString());

        switch (order.getStatus()) {
            case Order.SUCCESS:
                holder.orderStatus.setTextColor(Color.GREEN);
                break;
            case Order.PROGRESS:
                holder.orderStatus.setTextColor(Color.YELLOW);
                break;
            case Order.CANCELED:
                holder.orderStatus.setTextColor(Color.RED);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
