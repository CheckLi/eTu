package com.yitu.etu.widget.chat;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yitu.etu.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * @className:RedRealTimeLocationMessageItem
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 14:18
 */
@ProviderTag(
        messageContent = RealTimeLocationEndMessage.class,
        showReadState = true,centerInHorizontal = true,showPortrait = false
)
public class RealTimeLocationMessageEndItem extends IContainerItemProvider.MessageProvider<RealTimeLocationEndMessage> {
    @Override
    public void bindView(View view, int i, RealTimeLocationEndMessage textMessage, UIMessage uiMessage) {

    }

    @Override
    public Spannable getContentSummary(RealTimeLocationEndMessage textMessage) {
        return new SpannableString(textMessage.getContent());
    }

    @Override
    public void onItemClick(View view, int i, RealTimeLocationEndMessage textMessage, UIMessage uiMessage) {

    }



    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item_message_realtime_end, viewGroup, false);
    }
}
