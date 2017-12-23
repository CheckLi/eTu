package com.yitu.etu.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.yitu.etu.R;

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

    public static PopupWindow getPopupWindow(Context context, String[] strings, AdapterView.OnItemClickListener onItemClick, String bgType) {
        ArrayList list = new ArrayList();

        TextPaint newPaint = new TextPaint();
        float textSize = context.getResources().getDisplayMetrics().scaledDensity * 18;
        newPaint.setTextSize(textSize);
        float newPaintWidth = newPaint.measureText("我");
        int maxLength=0;
        for (String str :
                strings) {
            Map<String, String> map = new HashMap<>();
            map.put("data", str);
            maxLength=Math.max((int)newPaint.measureText(str),maxLength);
            list.add(map);
        }
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pop, null);
        if("right".equals(bgType)){
            view.setBackgroundResource(R.color.black);
        }
        else{
            view.setBackgroundResource(R.color.black);
        }
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setLayoutParams(new LinearLayout.LayoutParams(maxLength+dp2px(context,3),LinearLayout.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new SimpleAdapter(context, list, R.layout.item_pop, new String[]{"data"}, new int[]{R.id.text}));
        listView.setOnItemClickListener(onItemClick);
        popupWindow.setContentView(view);
        return popupWindow;

    }
}
