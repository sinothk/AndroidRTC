package com.dds.webrtclib.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.dds.webrtclib.R;
import com.dds.webrtclib.bean.MeetingUserEntity;
import com.dds.webrtclib.ui.adapter.MeetingUserAdapter;

import java.util.ArrayList;

/**
 * <pre>
 *  创建:  梁玉涛 2019/7/18 on 16:25
 *  项目: AndroidRTCLib
 *  描述:
 *  更新:
 * <pre>
 */
public abstract class UserListViewActivity extends AppCompatActivity {

    MeetingUserAdapter meetingUserAdapter;
    GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wr_activity_chat_room);
        gridView = findViewById(R.id.grid);
    }

    /**
     * 设置GirdView参数，绑定数据
     */
    protected void setGridView(ArrayList<MeetingUserEntity> cityList) {
        int size = cityList.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridViewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        meetingUserAdapter = new MeetingUserAdapter(getApplicationContext(), cityList);
        gridView.setAdapter(meetingUserAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> setItemClick(cityList.get(position)));
    }

    public abstract void setItemClick(MeetingUserEntity meetingUser);
}
