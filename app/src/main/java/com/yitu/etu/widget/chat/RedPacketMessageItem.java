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
import com.yitu.etu.dialog.SingleTipsDialog;
import com.yitu.etu.eventBusItem.EventPlayYanHua;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

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
        SingleTipsDialog dialog = new SingleTipsDialog(view.getContext(), "平安符领取");
        dialog.setMessage("恭喜成功领取平安符,参数不详，无法进行下一步");
        dialog.showDialog();
        EventBus.getDefault().post(new EventPlayYanHua(true));
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item_message_packet, viewGroup, false);
    }
}
