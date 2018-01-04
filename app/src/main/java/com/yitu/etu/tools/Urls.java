package com.yitu.etu.tools;

/**
 * @className:Urls
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月16日 16:41
 */
public class Urls {
    public static final String address = "http://api.91eto.com";
    public static final String login = address + "/user/login";//⽤用户户登
    public static final String updateUserInfo = address + "/user/save";//修改用户信息
    public static final String sendCode = address + "/user/registersms";//发送验证码
    public static final String sendForgetCode = address + "/user/forgetpwdsms";//发送验证码
    public static final String regist = address + "/user/register";//⽤户注册
    public static final String regist_xy = address + "/user/getShopProcotol";//注册协议
    public static final String foregetPassword = address + "/user/forgetpwd";//忘记密码提交新密
    public static final String chatToken = address + "/user/getIMToken";//获取融云toke
    public static final String loginPasswordChange = address + "/user/savePassword";//登陆密码修改
    public static final String putRenz = address + "/user/uprenz";//上传认证信息
    public static final String getRenz = address + "/user/renz";//获取认证信息
    public static final String setPayPassword = address + "/user/setPaypwd";//设置支付密码
    public static final String paySms = address + "/user/getPaypwdSms";//获取支付密码验证码
    public static final String reSetPayPassword = address + "/user/forgetPaypwd";//重新设置支付密码
    public static final String URL_GET_USER_INFO = address + "/user/getinfo";//获取用户信息，包含钱包信息
    public static final String URL_SEND_PIN_A_FU = address + "/chat/sendPacket";//发送平安符
    public static final String URL_GET_ID_USER_INFO = address + "/user/infobyid";//获取指定用户信息
    public static final String URL_GET_USER_SHOP_DETAIL = address + "/shop/getShopInfo";//获取店铺信息
    public static final String URL_SAVE_USER_SHOP_DETAIL = address + "/shop/saveShopInfo";//保存店铺信息
    public static final String URL_CREATE_LOCATION = address + "/chat/createLocation";//创建位置共享
    public static final String URL_GET_CHAT_ID = address + "/chat/getChatId";//获取聊天id
    public static final String URL_GET_GETLOCATION = address + "/chat/getLocation";//获取聊天id

    /**
     * 个人中心相关
     */
    public static final String URL_ORDER_LIST_NOT_USE = address + "/shop/get_my_orderlist";//获取订单列表未使用
    public static final String URL_ORDER_LIST_USE = address + "/shop/get_my_order";//获取订单列表按类型type分 1未用，2使用
    public static final String URL_ORDER_DETAIL = address + "/shop/get_order_info";//获取订单详情
    public static final String URL_MY_PUBLISH_ROUTE = address + "/action/getYdataList";//我发起的行程
    public static final String URL_MY_PUBLISH_ROUTE_DEL = address + "/action/delAction";//我发起的行程删除
    public static final String URL_MY_ADD_ROUTE = address + "/action/getDataList";//我参与的行程
    public static final String URL_MY_TRAVELS = address + "/title/getMyList";//我的游记
    public static final String URL_MY_TRAVELS_DEL = address + "/title/delete";//我的游记删除
    public static final String URL_MY_TRAVELS_DETAIL = address + "/title/info";//游记详情
    public static final String URL_MY_COLLECT = address + "/collect/getlist";//我的收藏列表
    public static final String URL_MY_COLLECT_DEL = address + "/collect/delete";//我的收藏列表删除
    public static final String URL_MY_BOON_LIST = address + "/ticket/get_list";//福利列表
    public static final String URL_MY_BOON_CENTER_LIST = address + "/ticket/get_my_order";//我的福利中心
    public static final String URL_MY_BOON_CREATE = address + "/ticket/create_order";//购买福利
    public static final String URL_MY_BOON_ORDER_DETAIL = address + "/ticket/get_order_info";//我的福利中心
    public static final String URL_PRODUCT_LIST = address + "/product/getlist";//产品列表
    public static final String URL_HISTORY_YU_E = address + "/user/accountLog";//余额记录
    public static final String URL_HISTORY_P_AN = address + "/product/safe_orderlist";//平安符记录
    public static final String URL_SEARCH_USER=address+"/user/search";//搜索用户
    public static final String URL_ADD_FRIEND=address+"/chat/applyFrend";//添加好友
    public static final String URL_CHANG_BEIZHU=address+"/chat/saveName";//修改好友备注
    public static final String URL_FRIEND_LIST=address+"/chat/get_frend_list";//获取好友列表
    /**
     * 类型rechargetype（0为余额充
     值、1为平安符购买、2为活动参
     与）、⽀付类型paytype（0为余额
     ⽀付、1为⽀付宝、2为微信）、⽀
     付密码paypwd（当为余额⽀付时
     传⼊）、活动ID action_id（当为
     活动参与时传⼊）、⽀付⾦额
     money
     */
    public static final String URL_BUY_P_AN = address + "/action/join";
    public static final String ACTION_COLLECT = address + "/action/collect";//活动收藏
    public static final String ACTION_INFO=address + "/action/info";//活动详情
    public static final String CIRCLE_USER_INDEX=address + "/circle/userindex";
    public static final String CIRCLE_GET_LIST=address + "/circle/getList";
    public static final String CIRCLE_DELETE=address+"/circle/delCircle";
    public static final String CIRCLE_ADD_GOOD=address+"/circle/addGood";
    public static final String CIRCLE_ADD_COMMENT=address+"/circle/addComment";
    public static final String CIRCLE_ADD=address+"/circle/upCircle";

    public static final String SPOT_INFO=address+"/spot/info";
    public static final String SPOT_GET_COMMENT=address+"/spot/getComment";
    public static final String SPOT_ADD_GOOD=address+"/spot/addGood";
    public static final String SPOT_ADD_COMMENT=address+"/spot/addComment";
    public static final String SPOT_COLLECT=address+"/spot/collect";


    public static final String ACTION_ADD=address+"/action/addAction";
    public static final String SPOT_UP_ERROR=address+"/spot/uperror";
    public static final String USER_LOCATION=address+"/user/location";
    public static final String CIRCLE_ALLOW_CIRCLE=address+"/circle/allowcircle";
    public static final String SHOP_GET_LIST=address+"/shop/get_list";
    public static final String SHOP_GET_SHOP_ORDER_LIST=address+"/shop/get_shop_order";
    public static final String GET_SHOP_PRODUCT=address+"/shop/getShopProduct";
    public static final String SHOP_SAVE_PRODUCT=address+"/shop/saveProductInfo";
    public static final String CHECK_SHOP_ORDER=address+"/shop/checkShopOrder";

    public static final String RELEASE_My_Travel=address+"/title/addTitle";
    public static final String SHOP_GET_PRODUCT=address+"/shop/get_product";
    public static final String SHOP_GET_SHOP_INFO=address+"/shop/get_shop_info";
    public static final String SHOP_COLLECT=address+"/shop/collect";

    public static final String SHOP_ADD_GOOD=address+"/shop/addGood";
    public static final String SHOP_DEL_PRODUCT=address+"/shop/delProductInfo";

    /**
     * 支付
     * 购买数量count、商品ID
     product_id（多个⽤英⽂,隔开）、
     ⽀付类型paytype（0为余额⽀付；
     1为⽀付宝⽀付；2为微信⽀付）、
     ⽀付密码paypwd（当为余额⽀付
     时传⼊）
     */
    public static final String URL_SHOP_PROJECT=address+"/shop/create_order";
    /**
     * 抢平安符
     */
    public static final String URL_PACKET_GET=address+"/chat/getPacket";
}
