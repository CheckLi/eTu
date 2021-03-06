package com.yitu.etu.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.dialog.TipsDialog;
import com.yitu.etu.entity.CircleFirendEntity;
import com.yitu.etu.entity.HttpStateEntity;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.InputContentActivity;
import com.yitu.etu.util.DateUtil;
import com.yitu.etu.util.TextUtils;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.Tools;
import com.yitu.etu.util.activityUtil;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.widget.MGridView;

import java.util.ArrayList;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Call;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class CircleFirendAdapter extends BaseAdapter<CircleFirendEntity.CircleBean, CircleFirendAdapter.ViewHolder> {
    boolean isOther;
    int myId = -1;

    public CircleFirendAdapter(Context context, boolean isOther) {
        super(context);
        this.isOther = isOther;
        if (EtuApplication.getInstance().isLogin()) {
            myId = EtuApplication.getInstance().getUserInfo().getId();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(int position) {
        return new ViewHolder();
    }

    @Override
    public void initItemView(int position, View convertView, ViewHolder viewHolder) {
        viewHolder.imageGridView = (MGridView) convertView.findViewById(R.id.gridView);
        viewHolder.chartGridView = (ListView) convertView.findViewById(R.id.chartGridView);
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        viewHolder.tv_good = (TextView) convertView.findViewById(R.id.tv_good);
        viewHolder.text = (TextView) convertView.findViewById(R.id.text);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
        viewHolder.img_pl = (ImageView) convertView.findViewById(R.id.img_pl);
        viewHolder.img_dz = (ImageView) convertView.findViewById(R.id.img_dz);
        viewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);


    }


    public void sendMsg(String text) {
        final CircleFirendEntity.CircleBean data = getItem(plPosition);
        HashMap<String, String> params = new HashMap<>();
        params.put("circle_id", data.getId() + "");
        params.put("puser_id", myId + "");
        params.put("text", text);
        Http.post(Urls.CIRCLE_ADD_COMMENT, params, new GsonCallback<ObjectBaseEntity<CircleFirendEntity.CommentBean>>() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(ObjectBaseEntity<CircleFirendEntity.CommentBean> response, int i) {
                if (response.success()) {
                    CircleFirendEntity.UserBean user = new CircleFirendEntity.UserBean();
                    user.setName(EtuApplication.getInstance().getUserInfo().getName());
                    response.getData().setUser(user);
                    if (data.getComment() == null) {
                        data.setComment(new ArrayList<CircleFirendEntity.CommentBean>());
                    }
                    data.getComment().add(response.getData());
                    notifyDataSetChanged();
                } else {
                    ToastUtil.showMessage(response.getMessage());
                }

            }
        });

    }

    public int plPosition = -1;

    @Override
    public void bindData(final int position, View convertView, ViewHolder viewHolder) {
        final CircleFirendEntity.CircleBean data = getItem(position);
        ArrayList<String> imageUrl = new ArrayList<String>();
        if (data.getUser().getId() == myId) {
            viewHolder.tv_delete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_delete.setVisibility(View.INVISIBLE);
        }
        for (String img : data.getImages()) {
            imageUrl.add(img);
        }
        if (imageUrl.size() > 0) {
            viewHolder.imageGridView.setPadding(0, Tools.dp2px(getContext(), 5), 0, 0);
            viewHolder.imageGridView.setAdapter(new ImageAdapter(getContext(), imageUrl));
            viewHolder.imageGridView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageGridView.setVisibility(View.GONE);
        }
        viewHolder.tv_good.setText("赞(" + data.getGood() + ")");
        viewHolder.tv_name.setText(data.getUser().getName());
        if (TextUtils.isEmpty(data.getText())) {
            viewHolder.text.setVisibility(View.GONE);
        } else {
            viewHolder.text.setText(data.getText());
            viewHolder.text.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_time.setText(DateUtil.getTime(data.getCreated() + "", "yyyy-MM-dd"));
        ImageLoadUtil.getInstance().loadImage(viewHolder.image, Urls.address + data.getUser().getHeader(), R.drawable.default_head, 200, 200);
        ArrayList<String> imageUrl2 = new ArrayList<String>();
//        imageUrl2.add("dsads");
//        imageUrl2.add("dsads");
        final ChartAdapter chartAdapter = new ChartAdapter(getContext(), data.getComment());
        viewHolder.chartGridView.setAdapter(chartAdapter);
        if (isOther) {
            viewHolder.tv_delete.setVisibility(View.INVISIBLE);
            viewHolder.img_dz.setVisibility(View.INVISIBLE);
            viewHolder.img_pl.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TipsDialog dialog = new TipsDialog(v.getContext(), "删除朋友圈");
                    dialog.setMessage("确认要删除这条朋友圈？");
                    dialog.setRightBtn("删除", new Function1<View, Unit>() {
                        @Override
                        public Unit invoke(View view) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("id", data.getId() + "");
                            Http.post(Urls.CIRCLE_DELETE, params, new GsonCallback<HttpStateEntity>() {
                                @Override
                                public void onError(Call call, Exception e, int i) {

                                }

                                @Override
                                public void onResponse(HttpStateEntity response, int i) {
                                    if (response.success()) {
                                        removeByPosition(position);
                                    } else {
                                        ToastUtil.showMessage(response.getMessage());
                                    }

                                }
                            }, true);
                            return null;
                        }
                    });
                    dialog.show();
                }
            });
            viewHolder.img_dz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("circle_id", data.getId() + "");
                    Http.post(Urls.CIRCLE_ADD_GOOD, params, new GsonCallback<HttpStateEntity>() {
                        @Override
                        public void onError(Call call, Exception e, int i) {

                        }

                        @Override
                        public void onResponse(HttpStateEntity response, int i) {
                            if (response.success()) {
                                data.setGood(data.getGood() + 1);
                                notifyDataSetChanged();
                            } else {
                            }
                            ToastUtil.showMessage(response.getMessage());

                        }
                    }, true);

                }
            });
            viewHolder.img_pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plPosition = position;
                    activityUtil.nextActivity(getContext(), InputContentActivity.class, null, 10001, false, R.anim.actionsheet_dialog_in, R.anim.actionsheet_dialog_out);
                }
            });
        }
    }

    class ViewHolder extends BaseAdapter.abstractViewHodler {
        MGridView imageGridView;
        ListView chartGridView;
        TextView tv_name, tv_time, tv_good, text, tv_delete;
        ImageView image, img_pl, img_dz;

        @Override
        int getItemLayoutID(int type) {
            return R.layout.item_circle_firend;
        }
    }
}
