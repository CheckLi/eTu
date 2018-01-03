package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.entity.ShopProductEntity;
import com.yitu.etu.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/2.
 */
public class ChooseReservation extends BaseActivity {
    private TextView tv_order;
    private TextView tv_price;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView end_time;
    private TextView start_time;
    private TextView time_num;
    private TextView room_num;
    private LinearLayout li_time;
    private LinearLayout li_room_num;
    private ShopProductEntity data;

    @Override
    public int getLayout() {
        return R.layout.activity_choose_reservation;
    }

    @Override
    public void initActionBar() {
        setTitle("订单填写");
    }

    @Override
    public void initView() {
        li_time = (LinearLayout) findViewById(R.id.li_time);
        li_room_num = (LinearLayout) findViewById(R.id.li_room_num);

        tv_order = (TextView) findViewById(R.id.tv_order);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        room_num = (TextView) findViewById(R.id.room_num);
        time_num = (TextView) findViewById(R.id.time_num);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        li_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, ChooseTimeActivity.class), 111);
            }
        });
    }

    @Override
    public void getData() {
        data=(ShopProductEntity)getIntent().getSerializableExtra("data");
        tv_price.setText("￥"+data.getPrice());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+ 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH) ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            start = formatter.parse(year + "-" + month + "-" + day+ " 12:00:00" ).getTime()/1000L;
            end = formatter.parse(year + "-" + month + "-" + (day + 1)+ " 12:00:00").getTime()/1000L;
        } catch (ParseException e) {
        }
        setTimeText();
    }

    @Override
    public void initListener() {

    }
    public  void setTimeText(){
        start_time.setText(DateUtil.getTime(start + "", "MM-dd"));
        end_time.setText(DateUtil.getTime(end + "", "MM-dd"));
        time_num.setText(((int)(end-start)/(60*60*24))+"晚");
    }

    long start;
    long end;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            start = data.getLongExtra("start", 0)/1000L+43200;
            end = data.getLongExtra("end", 0)/1000+43200L;
            setTimeText();
        }
    }
}
