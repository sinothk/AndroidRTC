package com.dds.webrtclib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dds.webrtclib.R;
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
        MeetingUserEntity city = list.get(position);

        tvCity.setText(city.getCityName());
        tvCode.setText(city.getCityCode());
        return convertView;
    }

//    public void setData(ArrayList<MeetingUserEntity> meetingUserList) {
//
//        if (meetingUserList == null) {
//            meetingUserList = new ArrayList<>();
//        }
//
//        if (list != null) {
//            list.clear();
//        }
//
//        list = meetingUserList;
//        notifyDataSetChanged();
//    }
}