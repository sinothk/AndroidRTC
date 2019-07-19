package com.dds.webrtclib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dds.webrtclib.R;
import com.dds.webrtclib.bean.DataCache;
import com.dds.webrtclib.bean.MeetingContent;
import com.dds.webrtclib.bean.MeetingUserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  创建:  梁玉涛 2019/7/18 on 16:55
 *  项目: AndroidRTCLib
 *  描述:
 *  更新:
 * <pre>
 */

/**
 * GirdView 数据适配器
 */
public class MeetingUserAdapter extends BaseAdapter {

    Context context;
    List<MeetingUserEntity> list;

    public MeetingUserAdapter(Context _context) {
        this.list = new ArrayList<>();
        this.context = _context;
    }

    public MeetingUserAdapter(Context _context, List<MeetingUserEntity> _list) {
        this.list = _list;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.meeting_user_list_item, null);

        TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
        TextView tvCode = (TextView) convertView.findViewById(R.id.tvCode);
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.itemImage);

        MeetingUserEntity city = list.get(position);
        MeetingContent user = DataCache.getOnlineUserInfo(city.getCityCode());
        tvCity.setText(user.getUserName());

        if (user.getUserPhoto() != null && user.getUserPhoto().length() > 0) {
            Glide.with(context).load(user.getUserPhoto()).into(itemImage);
        }else{
            itemImage.setImageResource(R.drawable.icon_hangup);
        }
        return convertView;
    }
}