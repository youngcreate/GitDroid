package com.youngcreate.gitdroid.gank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.gank.model.GankItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 16-8-8.
 */
public class GankAdapter extends BaseAdapter {

    private final List<GankItem> datas;

    public GankAdapter() {
        datas = new ArrayList<GankItem>();
    }

    public void setDatas(List<GankItem> gankItems) {
        datas.clear();
        datas.addAll(gankItems);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public GankItem getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_gank, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        GankItem gankItem = getItem(position);

        viewHolder.tvGank.setText(gankItem.getDesc());
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.tvGank)
        TextView tvGank;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
