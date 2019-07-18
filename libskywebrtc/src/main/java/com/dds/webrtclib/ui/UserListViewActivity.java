package com.dds.webrtclib.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dds.webrtclib.R;
import com.dds.webrtclib.bean.MeetingUserEntity;
import com.dds.webrtclib.ui.adapter.MeetingUserAdapter;

import org.webrtc.SurfaceViewRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    List<MeetingUserEntity> cityList;
    RelativeLayout itmel;
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

//        setGridView();
    }

    protected void setUserRendererData(Map<String, SurfaceViewRenderer> videoViews) {

        ArrayList<MeetingUserEntity> meetingUserList = new ArrayList<MeetingUserEntity>();

        for (int i = 0; i < videoViews.size(); i++) {
            MeetingUserEntity item = new MeetingUserEntity();
            item.setCityName("深圳" + i);
            item.setCityCode("0755");
            meetingUserList.add(item);
        }

//        MeetingUserEntity item = new MeetingUserEntity();
//        item.setCityName("深圳");
//        item.setCityCode("0755");
//        meetingUserList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("上海");
//        item.setCityCode("021");
//        meetingUserList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("广州");
//        item.setCityCode("020");
//        meetingUserList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("北京");
//        item.setCityCode("010");
//        meetingUserList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("武汉");
//        item.setCityCode("027");
//        meetingUserList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("孝感");
//        item.setCityCode("0712");
//        meetingUserList.add(item);

        setGridView(meetingUserList);
    }

//    /**
//     * 设置数据
//     */
//    private void setData() {
//        cityList = new ArrayList<MeetingUserEntity>();
//        MeetingUserEntity item = new MeetingUserEntity();
//        item.setCityName("深圳");
//        item.setCityCode("0755");
//        cityList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("上海");
//        item.setCityCode("021");
//        cityList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("广州");
//        item.setCityCode("020");
//        cityList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("北京");
//        item.setCityCode("010");
//        cityList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("武汉");
//        item.setCityCode("027");
//        cityList.add(item);
//        item = new MeetingUserEntity();
//        item.setCityName("孝感");
//        item.setCityCode("0712");
//        cityList.add(item);
//        cityList.addAll(cityList);
//    }

    /**
     * 设置GirdView参数，绑定数据
     */
    private void setGridView(ArrayList<MeetingUserEntity> cityList) {
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

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            setItemClick(position);
        });
    }

    public abstract void setItemClick(int position);

//    /**
//     * GirdView 数据适配器
//     */
//    public class GridViewAdapter extends BaseAdapter {
//        Context context;
//        List<MeetingUserEntity> list;
//
//        public GridViewAdapter(Context _context, List<MeetingUserEntity> _list) {
//            this.list = _list;
//            this.context = _context;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            convertView = layoutInflater.inflate(R.layout.meeting_user_list_item, null);
//            TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
//            TextView tvCode = (TextView) convertView.findViewById(R.id.tvCode);
//            MeetingUserEntity city = list.get(position);
//
//            tvCity.setText(city.getCityName());
//            tvCode.setText(city.getCityCode());
//            return convertView;
//        }
//    }

//    public class MeetingUserEntity {
//        private String cityName;
//        private String cityCode;
//
//        public String getCityName() {
//            return cityName;
//        }
//
//        public void setCityName(String cityName) {
//            this.cityName = cityName;
//        }
//
//        public String getCityCode() {
//            return cityCode;
//        }
//
//        public void setCityCode(String cityCode) {
//            this.cityCode = cityCode;
//        }
//    }
}
