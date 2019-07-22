package com.dds.webrtclib.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dds.webrtclib.R;
import com.dds.webrtclib.bean.MeetingUserEntity;
import com.dds.webrtclib.glide.CropCircleTransformation;

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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.meeting_user_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MeetingUserEntity userEntity = list.get(position);

        holder.itemTitle.setText(TextUtils.isEmpty(userEntity.getUserName()) ? userEntity.getUserId() : userEntity.getUserName());

        if (userEntity.getUserPhoto() != null && userEntity.getUserPhoto().length() > 0) {
            Glide.with(context).load(userEntity.getUserPhoto())
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(holder.itemImage);
        } else {
            holder.itemImage.setImageResource(R.drawable.person);
        }

        if (userEntity.isCurrLiving()) {
            holder.livingFlag.setBackgroundResource(R.drawable.shape_red);
        }else{
            holder.livingFlag.setBackgroundResource(R.drawable.shape_gray);
        }

        return convertView;
    }

    public void setData(ArrayList<MeetingUserEntity> userList) {
        if (this.list != null) {
            this.list.clear();
        }
        if (userList == null) {
            userList = new ArrayList<>();
        }

        this.list = userList;

        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView itemImage;
        TextView itemTitle, livingFlag;

        ViewHolder(View currView) {
            itemImage = currView.findViewById(R.id.itemImage);
            itemTitle = currView.findViewById(R.id.itemTitleTv);
            livingFlag = currView.findViewById(R.id.livingFlag);
        }
    }
}