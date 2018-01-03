package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;

import com.example.nanchen.calendarviewdemo.view.MyCalendarView2;
import com.yitu.etu.R;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/3.
 */
public class ChooseTimeActivity extends BaseActivity {
    private MyCalendarView2 main_calendar;

    @Override
    public int getLayout() {
        return com.example.nanchen.calendarviewdemo.R.layout.activity_choose_time;
    }

    @Override
    public void initActionBar() {
        setTitle("入住时间");
        setRightText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_calendar.getStart() == null) {
                    showToast("请选择入住时间");
                } else if (main_calendar.getEnd() == null) {
                    showToast("请选择离店时间");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("start", main_calendar.getStart().getTime());
                    intent.putExtra("end", main_calendar.getEnd().getTime());
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void initView() {
        main_calendar = (MyCalendarView2) findViewById(R.id.main_calendar);

    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }
}
