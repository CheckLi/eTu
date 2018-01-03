package com.yitu.etu.widget.chat;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.SceneShopProductActivity;
import com.yitu.etu.ui.activity.SceneShopYwDetailActivity;
import com.yitu.etu.ui.activity.SearchResultOrderSceneActivity;
import com.yitu.etu.ui.activity.SearchResultSceneActivity;
import com.yitu.etu.ui.activity.TravelsDetailActivity;
import com.yitu.etu.util.activityUtil;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * @className:RedShareMessageItem
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 14:18
 */
@ProviderTag(
        messageContent = ShareMessage.class,
        showReadState = true
)
public class ShareImageMessageItem extends IContainerItemProvider.MessageProvider<ShareMessage> {
    @Override
    public void bindView(View view, int i, ShareMessage textMessage, UIMessage uiMessage) {
        View leftImg = view.findViewById(R.id.img);
        View rightImg = view.findViewById(R.id.img2);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView content = (TextView) view.findViewById(R.id.tv_content);
        title.setText(textMessage.getTitle());
        content.setText(textMessage.getContent());
        ImageLoadUtil.getInstance().loadImage(img, Urls.address + textMessage.getLimage(), 50, 50);
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
    public Spannable getContentSummary(ShareMessage textMessage) {
        return new SpannableString(textMessage.getContent());
    }

    //type类型（1景点，3文章游记，4出行活动，5美食商铺，6住宿商铺，7游玩商铺）
    @Override
    public void onItemClick(View view, int i, ShareMessage textMessage, UIMessage uiMessage) {
        Bundle bundle = new Bundle();
        bundle.putString("id", textMessage.getBindid());
        bundle.putString("travels_id", textMessage.getBindid());
        bundle.putString("title", textMessage.getTitle());
        bundle.putString("type", textMessage.getType());
        switch (textMessage.getType()) {
            case "1":
                activityUtil.nextActivity(view.getContext(), SearchResultSceneActivity.class, bundle, false);
                break;

            case "3":
                activityUtil.nextActivity(view.getContext(), TravelsDetailActivity.class, bundle, false);
                break;

            case "4":
                activityUtil.nextActivity(view.getContext(), SearchResultOrderSceneActivity.class, bundle, false);
                break;

            case "5":
                activityUtil.nextActivity(view.getContext(), SceneShopProductActivity.class, bundle, false);
                break;

            case "6":
                activityUtil.nextActivity(view.getContext(), SceneShopProductActivity.class, bundle, false);
                break;

            case "7":
                activityUtil.nextActivity(view.getContext(), SceneShopYwDetailActivity.class, bundle, false);
                break;

            default:
                break;
        }
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_item_message_share, viewGroup, false);
    }
}
