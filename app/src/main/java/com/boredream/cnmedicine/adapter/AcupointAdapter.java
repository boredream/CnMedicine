package com.boredream.cnmedicine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boredream.cnmedicine.R;
import com.boredream.cnmedicine.entity.Acupoint;

import java.util.List;
import java.util.Locale;

public class AcupointAdapter extends RecyclerView.Adapter<AcupointAdapter.ViewHolder> {

    private Context context;
    private List<Acupoint> datas;

    public AcupointAdapter(Context context, List<Acupoint> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_jingluo;
        public TextView tv_location;
        public TextView tv_function_type;

        public ViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_jingluo = (TextView) itemView.findViewById(R.id.tv_jingluo);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);
            tv_function_type = (TextView) itemView.findViewById(R.id.tv_function_type);
        }
    }

    @Override
    public AcupointAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_acupoint, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Acupoint data = datas.get(position);
        String name = data.getName();
        if(!data.getWuShuType().equals("空")) {
            name = String.format(Locale.CHINESE, "%s (%s)", name, data.getWuShuType());
        }
        holder.tv_name.setText(name);

        holder.tv_jingluo.setText(data.getJingLuo());

        String location = String.format(Locale.CHINESE, "[%s - 第%d个穴位] %s",
                data.getJingLuoIndexDirection(),
                data.getJingLuoIndex() + 1,
                data.getLocation());
        holder.tv_location.setText(location);

        holder.tv_function_type.setText(data.getFunctionType());
    }

}
