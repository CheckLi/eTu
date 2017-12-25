package com.yitu.etu.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.yitu.etu.R;
import com.yitu.etu.ui.activity.BaseActivity;
import com.yitu.etu.ui.activity.ImageShowActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class Tools {
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void showImage(Context context, String path, ImageView imageView) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        intent.putExtra("path", path);//非必须
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);//必须
        intent.putExtra("locationY", location[1]);//必须

        intent.putExtra("width", imageView.getWidth());//必须
        intent.putExtra("height", imageView.getHeight());//必须
        context.startActivity(intent);
        ((BaseActivity)context).overridePendingTransition(0, 0);
    }

    public static PopupWindow getPopupWindow(Context context, String[] strings, AdapterView.OnItemClickListener onItemClick, String bgType) {
        ArrayList list = new ArrayList();

        TextPaint newPaint = new TextPaint();
        float textSize = context.getResources().getDisplayMetrics().scaledDensity * 18;
        newPaint.setTextSize(textSize);
        int maxLength = 0;
        for (String str :
                strings) {
            Map<String, String> map = new HashMap<>();
            map.put("data", str);
            maxLength = Math.max((int) newPaint.measureText(str), maxLength);
            list.add(map);
        }
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pop, null);
        if ("left".equals(bgType)) {
            view.setBackgroundResource(R.drawable.t1);
        } else {
            view.setBackgroundResource(R.drawable.t2);
        }
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setLayoutParams(new LinearLayout.LayoutParams(maxLength + dp2px(context, 3), LinearLayout.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new SimpleAdapter(context, list, R.layout.item_pop, new String[]{"data"}, new int[]{R.id.text}));
        listView.setOnItemClickListener(onItemClick);
        popupWindow.setContentView(view);
        return popupWindow;

    }

    public static void navi(Context context, Poi start, Poi end) {
        AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
            @Override
            public void onInitNaviFailure() {

            }

            @Override
            public void onGetNavigationText(String s) {

            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

            }

            @Override
            public void onArriveDestination(boolean b) {

            }

            @Override
            public void onStartNavi(int i) {

            }

            @Override
            public void onCalculateRouteSuccess(int[] ints) {

            }

            @Override
            public void onCalculateRouteFailure(int i) {

            }

            @Override
            public void onStopSpeaking() {

            }
        });
    }
}
