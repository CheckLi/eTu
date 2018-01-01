package com.yitu.etu.widget.chat;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yitu.etu.R;

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
        View leftImg=view.findViewById(R.id.img);
        View rightImg=view.findViewById(R.id.img2);
        LinearLayout group= (LinearLayout) view.findViewById(R.id.ll_content);
        if(uiMessage.getMessageDirection()== Message.MessageDirection.SEND){
            leftImg.setVisibility(View.GONE);
            rightImg.setVisibility(View.VISIBLE);
            group.setGravity(Gravity.RIGHT);
        }else{
            leftImg.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.GONE);
            group.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public Spannable getContentSummary(PacketMessage textMessage) {
        return new SpannableString(textMessage.getContent());
    }

    @Override
    public void onItemClick(View view, int i, PacketMessage textMessage, UIMessage uiMessage) {

    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item_message_packet,viewGroup,false);
    }
}
