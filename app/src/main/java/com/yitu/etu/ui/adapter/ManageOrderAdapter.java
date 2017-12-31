package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.dialog.InputPriceDialog;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ShopOrderEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.widget.GlideApp;

import java.util.HashMap;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Call;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/29.
 */
public class ManageOrderAdapter extends BaseAdapter<ShopOrderEntity, ManageOrderAdapter.ViewHolder> {

    private final int px;
    int type;

    public ManageOrderAdapter(Context context, int type) {
        super(context);
        px = Tools.dp2px(context, 7);
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
        viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);

    }

    @Override
    public void bindData(final int position, View convertView, ViewHolder viewHolder) {
        final ShopOrderEntity data = getItem(position);
        viewHolder.tv_name.setText(data.getUser().getName());
        viewHolder.tv_time.setText(DateUtil.getTime(data.getUpdated() + "", null));
        viewHolder.tv_price.setText("付款:" + data.getPrice());
        viewHolder.tv_state.setText(data.getStatus());
        GlideApp.with(getContext())
                .load(Urls.address + data.getUser().getHeader())
                .error(R.drawable.etu_default)
                .placeholder(R.drawable.etu_default).into(viewHolder.image);
        if (type ==1) {
            viewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) {
                    InputPriceDialog inputPriceDialog = new InputPriceDialog(getContext(), "订单核销");
                    inputPriceDialog.setHint("已到核销期限，可直接核销",false);
                    inputPriceDialog.setRightBtn("取消",null);
                    inputPriceDialog.setLeftBtn("确认", new Function1<View, Unit>() {
                        @Override
                        public Unit invoke(View view) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("order_id", data.getId() + "");
                            Http.post(Urls.CHECK_SHOP_ORDER, hashMap, new GsonCallback<HttpStateEntity>() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(HttpStateEntity response, int id) {
                                    if (response.success()) {
                                        removeByPosition(position);
                                    }

                                }
                            });
                            return null;
                        }
                    });
                    inputPriceDialog.showDialog();

                }
            });

        }
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        TextView tv_time, tv_name, tv_price, tv_state;
        ImageView image;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_shop_order;
        }
    }
}
