package com.yitu.etu.widget.chat;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.dialog.LoadingDialog;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.eventBusItem.EventPlayYanHua;
import com.yitu.etu.eventBusItem.EventRefresh;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.MainActivity;
import com.yitu.etu.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;
import okhttp3.Call;

/**
 * @className:RedPacketMessageItem
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 14:18
 */
@ProviderTag(
        messageContent = PacketMessage.class,
        showReadState = true
)
public class RedPacketMessageItem extends IContainerItemProvider.MessageProvider<PacketMessage> {
    @Override
    public void bindView(View view, int i, PacketMessage textMessage, UIMessage uiMessage) {
        View leftImg = view.findViewById(R.id.img);
        View rightImg = view.findViewById(R.id.img2);
        TextView content = (TextView) view.findViewById(R.id.text);
        content.setText(textMessage.getContent());
        LinearLayout frame = (LinearLayout) view.findViewById(R.id.fl_content);
        LinearLayout group = (LinearLayout) view.findViewById(R.id.ll_content);
        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {
            leftImg.setVisibility(View.GONE);
            rightImg.setVisibility(View.VISIBLE);
            group.setGravity(Gravity.RIGHT);
            frame.setGravity(Gravity.RIGHT);
        } else {
            leftImg.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.GONE);
            group.setGravity(Gravity.LEFT);
            frame.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public Spannable getContentSummary(PacketMessage textMessage) {
        return new SpannableString(textMessage.getContent());
    }

    @Override
    public void onItemClick(View view, int i, PacketMessage textMessage, UIMessage uiMessage) {
        post(textMessage.getPinId(), view);
    }

    private void post(String id, final View view) {
        final LoadingDialog dialog = new LoadingDialog(view.getContext(), "获取中...");
        dialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        Http.post(Urls.URL_PACKET_GET, params, new GsonCallback<ObjectBaseEntity<Object>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showMessage("领取失败");
                dialog.hideDialog();
            }

            @Override
            public void onResponse(ObjectBaseEntity<Object> response, int id) {
                dialog.hideDialog();
                if (response.success()) {
                    EventBus.getDefault().post(new EventRefresh(MainActivity.class.getSimpleName()));
                    EventBus.getDefault().post(new EventPlayYanHua(true));
                } else {
                    ToastUtil.showMessage(response.getMessage());
                }
            }
        });
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item_message_packet, viewGroup, false);
    }
}
