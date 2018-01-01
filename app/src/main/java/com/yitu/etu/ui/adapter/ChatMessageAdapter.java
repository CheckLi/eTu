package com.yitu.etu.ui.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitu.etu.R;

import io.rong.common.RLog;
import io.rong.imkit.RongContext;
import io.rong.imkit.mention.RongMentionManager;
import io.rong.imkit.model.ConversationKey;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.utilities.RongUtils;
import io.rong.imkit.utils.RongDateUtils;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.DebouncedOnClickListener;
import io.rong.imkit.widget.ProviderContainerView;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.ReadReceiptInfo;
import io.rong.imlib.model.UnknownMessage;
import io.rong.imlib.model.UserInfo;

/**
 * @className:ChatMessageAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 12:22
 */
public class ChatMessageAdapter extends MessageListAdapter{
    private static long readReceiptRequestInterval = 120L;
    private MessageListAdapter.OnItemHandlerListener mOnItemHandlerListener;
    public ChatMessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        View result =  LayoutInflater.from(group.getContext()).inflate(R.layout.rc_item_message, group,false);
        ViewHolder holder = new ViewHolder();
        holder.leftIconView = (AsyncImageView)this.findViewById(result, R.id.rc_left);
        holder.rightIconView = (AsyncImageView)this.findViewById(result, R.id.rc_right);
        holder.nameView = (TextView)this.findViewById(result, R.id.rc_title);
        holder.contentView = (ProviderContainerView)this.findViewById(result, R.id.rc_content);
        holder.layout = (ViewGroup)this.findViewById(result, R.id.rc_layout);
        holder.progressBar = (ProgressBar)this.findViewById(result, R.id.rc_progress);
        holder.warning = (ImageView)this.findViewById(result, R.id.rc_warning);
        holder.readReceipt = (TextView)this.findViewById(result, R.id.rc_read_receipt);
        holder.readReceiptRequest = (TextView)this.findViewById(result, R.id.rc_read_receipt_request);
        holder.readReceiptStatus = (TextView)this.findViewById(result, R.id.rc_read_receipt_status);
        holder.message_check = (CheckBox)this.findViewById(result, R.id.message_check);
        holder.time = (TextView)this.findViewById(result, R.id.rc_time);
        holder.sentStatus = (TextView)this.findViewById(result, R.id.rc_sent_status);
        holder.layoutItem = (RelativeLayout)this.findViewById(result, R.id.rc_layout_item_message);
        if(holder.time.getVisibility() == View.GONE) {
            this.timeGone = true;
        } else {
            this.timeGone = false;
        }

        result.setTag(holder);
        return result;
    }

    /**
     * 检查是不是平安
     * @param position
     * @return
     */
    private boolean checkRed(int position){
        return getItem(position).getMessage().getObjectName().equals("RCD:ZXJPacket");
    }

    @Override
    protected void bindView(View v, final int position, final UIMessage data) {
        if(data != null) {
            /*if(checkRed(position)){
                data.setContent(TextMessage.obtain("我是红包"));
            }*/
            final ViewHolder holder = (ViewHolder)v.getTag();
            if(holder == null) {
                RLog.e("MessageListAdapter", "view holder is null !");
            } else {
                Object provider;
                ProviderTag tag;
                if(this.getNeedEvaluate(data)) {
                    provider = RongContext.getInstance().getEvaluateProvider();
                    tag = RongContext.getInstance().getMessageProviderTag(data.getContent().getClass());
                } else {
                    if(RongContext.getInstance() == null || data == null || data.getContent() == null) {
                        RLog.e("MessageListAdapter", "Message is null !");
                        return;
                    }
                   /* if(checkRed(position)){
                        provider=new RedPacketMessageItem();
                    }else {
                    }*/
                    provider = RongContext.getInstance().getMessageTemplate(data.getContent().getClass());
                    if(provider == null) {
                        provider = RongContext.getInstance().getMessageTemplate(UnknownMessage.class);
                        tag = RongContext.getInstance().getMessageProviderTag(UnknownMessage.class);
                    } else {
                        tag = RongContext.getInstance().getMessageProviderTag(data.getContent().getClass());
                    }

                    if(provider == null) {
                        RLog.e("MessageListAdapter", data.getObjectName() + " message provider not found !");
                        return;
                    }
                }

                final View view = holder.contentView.inflate((IContainerItemProvider)provider);
                ((IContainerItemProvider)provider).bindView(view, position, data);
                if(tag == null) {
                    RLog.e("MessageListAdapter", "Can not find ProviderTag for " + data.getObjectName());
                } else {
                    if(tag.hide()) {
                        holder.contentView.setVisibility(View.GONE);
                        holder.time.setVisibility(View.GONE);
                        holder.nameView.setVisibility(View.GONE);
                        holder.leftIconView.setVisibility(View.GONE);
                        holder.rightIconView.setVisibility(View.GONE);
                        holder.layoutItem.setVisibility(View.GONE);
                        holder.layoutItem.setPadding(0, 0, 0, 0);
                    } else {
                        holder.contentView.setVisibility(View.VISIBLE);
                        holder.layoutItem.setVisibility(View.VISIBLE);
                        holder.layoutItem.setPadding(RongUtils.dip2px(8.0F), RongUtils.dip2px(6.0F), RongUtils.dip2px(8.0F), RongUtils.dip2px(6.0F));
                    }

                    UserInfo userInfo;
                    GroupUserInfo portrait;
                    if(data.getMessageDirection() == Message.MessageDirection.SEND) {
                        if(tag.showPortrait()) {
                            holder.rightIconView.setVisibility(View.VISIBLE);
                            holder.leftIconView.setVisibility(View.GONE);
                        } else {
                            holder.leftIconView.setVisibility(View.GONE);
                            holder.rightIconView.setVisibility(View.GONE);
                        }

                        if(!tag.centerInHorizontal()) {
                            this.setGravity(holder.layout, 5);
                            holder.contentView.containerViewRight();
                            holder.nameView.setGravity(5);
                        } else {
                            this.setGravity(holder.layout, 17);
                            holder.contentView.containerViewCenter();
                            holder.nameView.setGravity(1);
                            holder.contentView.setBackgroundColor(View.VISIBLE);
                        }

                        boolean readRec = false;

                        try {
                            readRec = v.getContext().getResources().getBoolean(io.rong.imkit.R.bool.rc_read_receipt);
                        } catch (Resources.NotFoundException var12) {
                            RLog.e("MessageListAdapter", "rc_read_receipt not configure in rc_config.xml");
                            var12.printStackTrace();
                        }

                        if(data.getSentStatus() == Message.SentStatus.SENDING) {
                            if(tag.showProgress()) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                            } else {
                                holder.progressBar.setVisibility(View.GONE);
                            }

                            holder.warning.setVisibility(View.GONE);
                            holder.readReceipt.setVisibility(View.GONE);
                        } else if(data.getSentStatus() == Message.SentStatus.FAILED) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.VISIBLE);
                            holder.readReceipt.setVisibility(View.GONE);
                        } else if(data.getSentStatus() == Message.SentStatus.SENT) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.GONE);
                            holder.readReceipt.setVisibility(View.GONE);
                        } else if(readRec && data.getSentStatus() == Message.SentStatus.READ) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.GONE);
                            if(data.getConversationType().equals(Conversation.ConversationType.PRIVATE) && tag.showReadState()) {
                                holder.readReceipt.setVisibility(View.VISIBLE);
                            } else {
                                holder.readReceipt.setVisibility(View.GONE);
                            }
                        } else {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.GONE);
                            holder.readReceipt.setVisibility(View.GONE);
                        }

                        holder.readReceiptRequest.setVisibility(View.GONE);
                        holder.readReceiptStatus.setVisibility(View.GONE);
                        if(readRec && RongContext.getInstance().isReadReceiptConversationType(data.getConversationType()) && (data.getConversationType().equals(Conversation.ConversationType.GROUP) || data.getConversationType().equals(Conversation.ConversationType.DISCUSSION))) {
                            if(this.allowReadReceiptRequest(data.getMessage()) && !TextUtils.isEmpty(data.getUId())) {
                                boolean isLastSentMessage = true;

                                for(int i = position + 1; i < this.getCount(); ++i) {
                                    if(((UIMessage)this.getItem(i)).getMessageDirection() == Message.MessageDirection.SEND) {
                                        isLastSentMessage = false;
                                        break;
                                    }
                                }

                                long serverTime = System.currentTimeMillis() - RongIMClient.getInstance().getDeltaTime();
                                if(serverTime - data.getSentTime() < readReceiptRequestInterval * 1000L && isLastSentMessage && (data.getReadReceiptInfo() == null || !data.getReadReceiptInfo().isReadReceiptMessage())) {
                                    holder.readReceiptRequest.setVisibility(View.VISIBLE);
                                }
                            }

                            if(this.allowReadReceiptRequest(data.getMessage()) && data.getReadReceiptInfo() != null && data.getReadReceiptInfo().isReadReceiptMessage()) {
                                if(data.getReadReceiptInfo().getRespondUserIdList() != null) {
                                    holder.readReceiptStatus.setText(String.format(view.getResources().getString(io.rong.imkit.R.string.rc_read_receipt_status), new Object[]{Integer.valueOf(data.getReadReceiptInfo().getRespondUserIdList().size())}));
                                } else {
                                    holder.readReceiptStatus.setText(String.format(view.getResources().getString(io.rong.imkit.R.string.rc_read_receipt_status), new Object[]{Integer.valueOf(View.VISIBLE)}));
                                }

                                holder.readReceiptStatus.setVisibility(View.VISIBLE);
                            }
                        }

                        holder.nameView.setVisibility(View.GONE);
                        holder.readReceiptRequest.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                RongIMClient.getInstance().sendReadReceiptRequest(data.getMessage(), new RongIMClient.OperationCallback() {
                                    public void onSuccess() {
                                        ReadReceiptInfo readReceiptInfo = data.getReadReceiptInfo();
                                        if(readReceiptInfo == null) {
                                            readReceiptInfo = new ReadReceiptInfo();
                                            data.setReadReceiptInfo(readReceiptInfo);
                                        }

                                        readReceiptInfo.setIsReadReceiptMessage(true);
                                        holder.readReceiptStatus.setText(String.format(view.getResources().getString(io.rong.imkit.R.string.rc_read_receipt_status), new Object[]{Integer.valueOf(View.VISIBLE)}));
                                        holder.readReceiptRequest.setVisibility(View.GONE);
                                        holder.readReceiptStatus.setVisibility(View.VISIBLE);
                                    }

                                    public void onError(RongIMClient.ErrorCode errorCode) {
                                        RLog.e("MessageListAdapter", "sendReadReceiptRequest failed, errorCode = " + errorCode);
                                    }
                                });
                            }
                        });
                        holder.readReceiptStatus.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(mOnItemHandlerListener != null) {
                                    mOnItemHandlerListener.onReadReceiptStateClick(data.getMessage());
                                }

                            }
                        });
                        holder.rightIconView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(RongContext.getInstance().getConversationBehaviorListener() != null) {
                                    UserInfo userInfo = null;
                                    if(!TextUtils.isEmpty(data.getSenderUserId())) {
                                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                        userInfo = userInfo == null?new UserInfo(data.getSenderUserId(), (String)null, (Uri)null):userInfo;
                                    }

                                    RongContext.getInstance().getConversationBehaviorListener().onUserPortraitClick(v.getContext(), data.getConversationType(), userInfo);
                                }

                            }
                        });
                        holder.rightIconView.setOnLongClickListener(new View.OnLongClickListener() {
                            public boolean onLongClick(View v) {
                                if(RongContext.getInstance().getConversationBehaviorListener() != null) {
                                    UserInfo userInfo = null;
                                    if(!TextUtils.isEmpty(data.getSenderUserId())) {
                                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                        userInfo = userInfo == null?new UserInfo(data.getSenderUserId(), (String)null, (Uri)null):userInfo;
                                    }

                                    return RongContext.getInstance().getConversationBehaviorListener().onUserPortraitLongClick(v.getContext(), data.getConversationType(), userInfo);
                                } else {
                                    return true;
                                }
                            }
                        });
                        if(!tag.showWarning()) {
                            holder.warning.setVisibility(View.GONE);
                        }
                    } else {
                        if(tag.showPortrait()) {
                            holder.rightIconView.setVisibility(View.GONE);
                            holder.leftIconView.setVisibility(View.VISIBLE);
                        } else {
                            holder.leftIconView.setVisibility(View.GONE);
                            holder.rightIconView.setVisibility(View.GONE);
                        }

                        if(!tag.centerInHorizontal()) {
                            this.setGravity(holder.layout, 3);
                            holder.contentView.containerViewLeft();
                            holder.nameView.setGravity(3);
                        } else {
                            this.setGravity(holder.layout, 17);
                            holder.contentView.containerViewCenter();
                            holder.nameView.setGravity(1);
                            holder.contentView.setBackgroundColor(View.VISIBLE);
                        }

                        holder.progressBar.setVisibility(View.GONE);
                        holder.warning.setVisibility(View.GONE);
                        holder.readReceipt.setVisibility(View.GONE);
                        holder.readReceiptRequest.setVisibility(View.GONE);
                        holder.readReceiptStatus.setVisibility(View.GONE);
                        holder.nameView.setVisibility(View.VISIBLE);
                        if(data.getConversationType() != Conversation.ConversationType.PRIVATE && tag.showSummaryWithName() && data.getConversationType() != Conversation.ConversationType.PUBLIC_SERVICE && data.getConversationType() != Conversation.ConversationType.APP_PUBLIC_SERVICE) {
                            userInfo = null;
                            if(data.getConversationType().equals(Conversation.ConversationType.CUSTOMER_SERVICE) && data.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                                if(data.getUserInfo() != null) {
                                    userInfo = data.getUserInfo();
                                } else if(data.getMessage() != null && data.getMessage().getContent() != null) {
                                    userInfo = data.getMessage().getContent().getUserInfo();
                                }

                                if(userInfo != null) {
                                    holder.nameView.setText(userInfo.getName());
                                } else {
                                    holder.nameView.setText(data.getSenderUserId());
                                }
                            } else if(data.getConversationType() == Conversation.ConversationType.GROUP) {
                                portrait = RongUserInfoManager.getInstance().getGroupUserInfo(data.getTargetId(), data.getSenderUserId());
                                if(portrait != null) {
                                    holder.nameView.setText(portrait.getNickname());
                                } else {
                                    userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                    if(userInfo == null) {
                                        holder.nameView.setText(data.getSenderUserId());
                                    } else {
                                        holder.nameView.setText(userInfo.getName());
                                    }
                                }
                            } else {
                                userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                if(userInfo == null) {
                                    holder.nameView.setText(data.getSenderUserId());
                                } else {
                                    holder.nameView.setText(userInfo.getName());
                                }
                            }
                        } else {
                            holder.nameView.setVisibility(View.GONE);
                        }

                        holder.leftIconView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(RongContext.getInstance().getConversationBehaviorListener() != null) {
                                    UserInfo userInfo = null;
                                    if(!TextUtils.isEmpty(data.getSenderUserId())) {
                                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                        userInfo = userInfo == null?new UserInfo(data.getSenderUserId(), (String)null, (Uri)null):userInfo;
                                    }

                                    RongContext.getInstance().getConversationBehaviorListener().onUserPortraitClick(v.getContext(), data.getConversationType(), userInfo);
                                }

                            }
                        });
                    }

                    holder.leftIconView.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            UserInfo userInfo = null;
                            if(!TextUtils.isEmpty(data.getSenderUserId())) {
                                userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                userInfo = userInfo == null?new UserInfo(data.getSenderUserId(), (String)null, (Uri)null):userInfo;
                            }

                            if(RongContext.getInstance().getConversationBehaviorListener() != null && RongContext.getInstance().getConversationBehaviorListener().onUserPortraitLongClick(v.getContext(), data.getConversationType(), userInfo)) {
                                return false;
                            } else if(!RongContext.getInstance().getResources().getBoolean(io.rong.imkit.R.bool.rc_enable_mentioned_message) || !data.getConversationType().equals(Conversation.ConversationType.GROUP) && !data.getConversationType().equals(Conversation.ConversationType.DISCUSSION)) {
                                return false;
                            } else {
                                RongMentionManager.getInstance().mentionMember(data.getConversationType(), data.getTargetId(), data.getSenderUserId());
                                return true;
                            }
                        }
                    });
                    ConversationKey mKey;
                    Uri portrait1;
                    PublicServiceProfile publicServiceProfile;
                    if(holder.rightIconView.getVisibility() == View.VISIBLE) {
                        if(data.getConversationType().equals(Conversation.ConversationType.CUSTOMER_SERVICE) && data.getUserInfo() != null && data.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                            userInfo = data.getUserInfo();
                            portrait1 = userInfo.getPortraitUri();
                            holder.rightIconView.setAvatar(portrait1 != null?portrait1.toString():null, View.VISIBLE);
                        } else if((data.getConversationType().equals(Conversation.ConversationType.PUBLIC_SERVICE) || data.getConversationType().equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)) && data.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                            userInfo = data.getUserInfo();
                            if(userInfo != null) {
                                portrait1 = userInfo.getPortraitUri();
                                holder.rightIconView.setAvatar(portrait1 != null?portrait1.toString():null, View.VISIBLE);
                            } else {
                                mKey = ConversationKey.obtain(data.getTargetId(), data.getConversationType());
                                publicServiceProfile = RongContext.getInstance().getPublicServiceInfoFromCache(mKey.getKey());
                                portrait1 = publicServiceProfile.getPortraitUri();
                                holder.rightIconView.setAvatar(portrait1 != null?portrait1.toString():null, View.VISIBLE);
                            }
                        } else if(!TextUtils.isEmpty(data.getSenderUserId())) {
                            userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                            if(userInfo != null && userInfo.getPortraitUri() != null) {
                                holder.rightIconView.setAvatar(userInfo.getPortraitUri().toString(), View.VISIBLE);
                            } else {
                                holder.rightIconView.setAvatar((String)null, View.VISIBLE);
                            }
                        }
                    } else if(holder.leftIconView.getVisibility() == View.VISIBLE) {
                        userInfo = null;
                        portrait1 = null;
                        if(data.getConversationType().equals(Conversation.ConversationType.CUSTOMER_SERVICE) && data.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                            if(data.getUserInfo() != null) {
                                userInfo = data.getUserInfo();
                            } else if(data.getMessage() != null && data.getMessage().getContent() != null) {
                                userInfo = data.getMessage().getContent().getUserInfo();
                            }

                            if(userInfo != null) {
                                portrait1 = userInfo.getPortraitUri();
                                holder.leftIconView.setAvatar(portrait1 != null?portrait1.toString():null, io.rong.imkit.R.drawable.rc_cs_default_portrait);
                            }
                        } else if((data.getConversationType().equals(Conversation.ConversationType.PUBLIC_SERVICE) || data.getConversationType().equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)) && data.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                            userInfo = data.getUserInfo();
                            if(userInfo != null) {
                                portrait1 = userInfo.getPortraitUri();
                                holder.leftIconView.setAvatar(portrait1 != null?portrait1.toString():null, View.VISIBLE);
                            } else {
                                mKey = ConversationKey.obtain(data.getTargetId(), data.getConversationType());
                                publicServiceProfile = RongContext.getInstance().getPublicServiceInfoFromCache(mKey.getKey());
                                if(publicServiceProfile != null && publicServiceProfile.getPortraitUri() != null) {
                                    holder.leftIconView.setAvatar(publicServiceProfile.getPortraitUri().toString(), View.VISIBLE);
                                } else {
                                    holder.leftIconView.setAvatar((String)null, View.VISIBLE);
                                }
                            }
                        } else if(!TextUtils.isEmpty(data.getSenderUserId())) {
                            userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                            if(userInfo != null && userInfo.getPortraitUri() != null) {
                                holder.leftIconView.setAvatar(userInfo.getPortraitUri().toString(), View.VISIBLE);
                            } else {
                                holder.leftIconView.setAvatar((String)null, View.VISIBLE);
                            }
                        }
                    }

                    if(view != null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(RongContext.getInstance().getConversationBehaviorListener() == null || !RongContext.getInstance().getConversationBehaviorListener().onMessageClick(v.getContext(), v, data.getMessage())) {
                                    Object provider;
                                    if(getNeedEvaluate(data)) {
                                        provider = RongContext.getInstance().getEvaluateProvider();
                                    } else {
                                        provider = RongContext.getInstance().getMessageTemplate(data.getContent().getClass());
                                    }

                                    if(provider != null) {
                                        ((IContainerItemProvider.MessageProvider)provider).onItemClick(v, position, data.getContent(), data);
                                    }

                                }
                            }
                        });
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            public boolean onLongClick(View v) {
                                if(RongContext.getInstance().getConversationBehaviorListener() != null && RongContext.getInstance().getConversationBehaviorListener().onMessageLongClick(v.getContext(), v, data.getMessage())) {
                                    return true;
                                } else {
                                    Object provider;
                                    if(getNeedEvaluate(data)) {
                                        provider = RongContext.getInstance().getEvaluateProvider();
                                    } else {
                                        provider = RongContext.getInstance().getMessageTemplate(data.getContent().getClass());
                                    }

                                    if(provider != null) {
                                        ((IContainerItemProvider.MessageProvider)provider).onItemLongClick(v, position, data.getContent(), data);
                                    }

                                    return true;
                                }
                            }
                        });
                    }

                    holder.warning.setOnClickListener(new DebouncedOnClickListener() {
                        public void onDebouncedClick(View view) {
                            if(mOnItemHandlerListener != null) {
                                mOnItemHandlerListener.onWarningViewClick(position, data.getMessage(), view);
                            }

                        }
                    });
                    if(tag.hide()) {
                        holder.time.setVisibility(View.GONE);
                    } else {
                        if(!this.timeGone) {
                            String time = RongDateUtils.getConversationFormatDate(data.getSentTime(), view.getContext());
                            holder.time.setText(time);
                            if(position == View.VISIBLE) {
                                holder.time.setVisibility(View.VISIBLE);
                            } else {
                                UIMessage pre = (UIMessage)this.getItem(position - 1);
                                if(RongDateUtils.isShowChatTime(data.getSentTime(), pre.getSentTime(), 180)) {
                                    holder.time.setVisibility(View.VISIBLE);
                                } else {
                                    holder.time.setVisibility(View.GONE);
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    protected class ViewHolder {
        public AsyncImageView leftIconView;
        public AsyncImageView rightIconView;
        public TextView nameView;
        public ProviderContainerView contentView;
        public ProgressBar progressBar;
        public ImageView warning;
        public TextView readReceipt;
        public TextView readReceiptRequest;
        public TextView readReceiptStatus;
        public ViewGroup layout;
        public TextView time;
        public TextView sentStatus;
        public RelativeLayout layoutItem;
        public CheckBox message_check;

        protected ViewHolder() {
        }
    }

    public void setOnItemHandlerListener(MessageListAdapter.OnItemHandlerListener onItemHandlerListener) {
        this.mOnItemHandlerListener = onItemHandlerListener;
    }

    protected MessageListAdapter.OnItemHandlerListener getItemHandlerListener() {
        return this.mOnItemHandlerListener;
    }
}
