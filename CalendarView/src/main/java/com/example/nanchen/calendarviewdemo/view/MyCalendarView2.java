package com.example.nanchen.calendarviewdemo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nanchen.calendarviewdemo.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 自定义的日历控件
 *
 * @author nanchen
 * @date 2016-08-11 09:44:27
 */
public class MyCalendarView2 extends LinearLayout {

    private final String TAG = MyCalendarView2.class.getSimpleName();
    private int year_c = 0;// 今天的年份
    private int month_c = 0;// 今天的月份
    private int day_c = 0;// 今天的日期
    private String currentDate = "";//当前日期
    private Context mContext;
    private int gvFlag = 0;
    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private ClickDataListener clickDataListener;
    List<CalendarAdapter> calendarAdapters = new ArrayList<>();
    private Date start, end;

    public MyCalendarView2(Context context) {
        this(context, null);
    }

    public MyCalendarView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    /**
     * 初始化布局
     */
    int startMonth, startYear, startDay;
    int endMonth, endYear, endDay;

    private void initView() {
        setOrientation(VERTICAL);
        setCurrentDay();
        //加载布局，一个头布局，一个横向的星期布局，下面一个ViewFlipper用于滑动
//        View view = View.inflate(mContext, R.layout.calen_calendar, this);
//        TextView currentMonth = (TextView) view.findViewById(R.id.currentMonth);
        for (int i = 0; i < 13; i++) {
            CalendarAdapter adapter = new CalendarAdapter(mContext, getResources(), jumpMonth + i,
                    jumpYear, year_c, month_c, day_c);
            MGridView gridView = addGridView();
            gridView.setAdapter(adapter);
            calendarAdapters.add(adapter);
            addTextToTopTextView(adapter);
            addView(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CalendarAdapter calendarAdapter = ((CalendarAdapter) parent.getAdapter());
                    if (calendarAdapter.setColorDataPosition(position)) {
                        int scheduleDay = Integer.parseInt(calendarAdapter.getDateByClickItem(position)
                                .split("\\.")[0]); // 这一天的阳历
                        int scheduleYear = Integer.parseInt(calendarAdapter.getShowYear());
                        int scheduleMonth = Integer.parseInt(calendarAdapter.getShowMonth());
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = formatter.parse(scheduleYear + "-" + scheduleMonth + "-" + scheduleDay);
                            if (start == null) {
                                start = date;
                            } else if (start != null && end != null) {
                                start = date;
                                end = null;
                            } else if (date.getTime() > start.getTime()) {
                                end = date;
                            } else if (date.getTime() < start.getTime()) {
                                start = date;
                                end = null;
                            }
                            for (CalendarAdapter mcalendarAdapter :
                                    calendarAdapters) {
                                mcalendarAdapter.setSelectDate(start, end);
                            }
                        } catch (ParseException e) {
                        }
                    }

                }
            });
        }
    }


    private void setCurrentDay() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FormatDate.DATE_FORMAT, Locale.CHINA);
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
    }


    private void addTextToTopTextView(CalendarAdapter adapter) {
        View head = LayoutInflater.from(getContext()).inflate(R.layout.head_item, null);
        TextView textView = (TextView) head.findViewById(R.id.tv_title);
        StringBuffer textDate = new StringBuffer();
        // draw = getResources().getDrawable(R.drawable.top_day);
        // view.setBackgroundDrawable(draw);
        textDate.append(adapter.getShowYear()).append("年")
                .append(adapter.getShowMonth()).append("月").append("\t");
        textView.setText(textDate);
        addView(head);
    }

    private MGridView addGridView() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();

        MGridView gridView = new MGridView(mContext);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        // gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        if (Width == 720 && Height == 1280) {
            gridView.setColumnWidth(40);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        gridView.setVerticalSpacing(dp2px(getContext(), 2f));
        gridView.setHorizontalSpacing(dp2px(getContext(), 2f));
        gridView.setLayoutParams(params);
        return gridView;
    }

    public int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
