package com.youngcreate.gitdroid.github.hotuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youngcreate.gitdroid.R;
import com.youngcreate.gitdroid.github.login.model.User;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 16-8-3.
 */
public class HotUserAdapter extends BaseAdapter {

    private final ArrayList<User> datas;


    public HotUserAdapter() {
        datas = new ArrayList<User>();
    }

    public void addAll(Collection<User> users){
        datas.addAll(users);
        notifyDataSetChanged();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public User getItem(int position) {
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
            convertView = inflater.inflate(R.layout.layout_item_user, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();
        User user=getItem(position);
        viewHolder.tvLoginName.setText(user.getLogin());
        ImageLoader.getInstance().displayImage(user.getAvatarUrl(),viewHolder.ivIcon);

        return convertView;
    }

    static class ViewHolder {


        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvLoginName)
        TextView tvLoginName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
