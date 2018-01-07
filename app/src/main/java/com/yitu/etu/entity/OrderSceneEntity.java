package com.yitu.etu.entity;

import com.yitu.etu.util.TextUtils;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/26.
 */
public class OrderSceneEntity {

    /**
     * id : 2
     * user_id : 13
     * chat_id :
     * name : 外婆家新创意川菜酒楼
     * intro : 周末聚餐
     * images : ["/assets/data/20171208/15127061495148.jpg"]
     * text : 陈麻婆豆腐，尝鲜
     * address : 四川省成都市武侯区桂溪街道天鹅湖公寓·北岛世纪新城国际展馆
     * address_lat : 30.555874
     * address_lng : 104.076006
     * number : 20
     * hasnumber : 0
     * money : 0.00
     * hasmoney : 0.00
     * sort : 99
     * is_recommend : 0
     * status : 1
     * join_starttime : -28800
     * join_endtime : -28800
     * start_time : 1512662400
     * end_time : -28800
     * start_type : 0
     * is_spot : 0
     * map_id : 15
     * created : 1512706149
     * updated : 1512706243
     * user : {"id":13,"name":"行走的攻略","wxopenid":"","wbopenid":"","type":0,"usertype":0,"intro":"这家伙很懒，什么都没留下","phone":"15708429594","sex":0,"header":"/assets/data/20171208/15127189878455.jpg","salt":"6WNaMBDh","password":"03a605786748890ace06d7a532e100f2","paypwd":"","address":"","lat":"0","lng":"0","last_lat":"30.554267","last_lng":"104.065023","map_id":39,"balance":"0.00","safecount":1,"status":1,"is_circle":0,"is_del":0,"nomap":0,"created":1500954876,"updated":1511861850}
     * created_time : 2017-12-08 12:09:00
     * start_time_time : 2017-12-08 00:00:00
     * end_time_time : 1970-01-01 00:00:00
     * join_starttime_time : 1970-01-01 00:00:00
     * join_endtime_time : 1970-01-01 00:00:00
     * can_join : 0
     * status_name : 已结束
     * is_join : 0
     * userlist : []
     * orderlist : []
     */

    public int id;
    public int user_id;
    public String chat_id;
    public String name;
    public String intro;
    public String text;
    public String address;
    public String address_lat;
    public String address_lng;
    public int number;
    public int hasnumber;
    public String money;
    public String hasmoney;
    public int sort;
    public int is_recommend;
    public int status;
    public int join_starttime;
    public int join_endtime;
    public int start_time;
    public int end_time;
    public int start_type;
    public int is_spot;
    public int map_id;
    public int created;
    public int updated;
    public UserBean user;
    public String created_time;
    public String start_time_time;
    public String end_time_time;
    public String join_starttime_time;
    public String join_endtime_time;
    public int can_join;
    public String status_name;
    public int is_join;
    public List<String> images;
    public List<UserInfo> userlist;
//    public List<?> orderlist;


    public List<UserInfo> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<UserInfo> userlist) {
        this.userlist = userlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_lat() {
        return address_lat;
    }

    public void setAddress_lat(String address_lat) {
        this.address_lat = address_lat;
    }

    public String getAddress_lng() {
        return address_lng;
    }

    public void setAddress_lng(String address_lng) {
        this.address_lng = address_lng;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHasnumber() {
        return hasnumber;
    }

    public void setHasnumber(int hasnumber) {
        this.hasnumber = hasnumber;
    }

    public String getMoney() {
        return money;
    }

    public Float getPrice(){
        return Float.parseFloat(TextUtils.getText(money,"0.00"));
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getHasmoney() {
        return hasmoney;
    }

    public void setHasmoney(String hasmoney) {
        this.hasmoney = hasmoney;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(int is_recommend) {
        this.is_recommend = is_recommend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getJoin_starttime() {
        return join_starttime;
    }

    public void setJoin_starttime(int join_starttime) {
        this.join_starttime = join_starttime;
    }

    public int getJoin_endtime() {
        return join_endtime;
    }

    public void setJoin_endtime(int join_endtime) {
        this.join_endtime = join_endtime;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getStart_type() {
        return start_type;
    }

    public void setStart_type(int start_type) {
        this.start_type = start_type;
    }

    public int getIs_spot() {
        return is_spot;
    }

    public void setIs_spot(int is_spot) {
        this.is_spot = is_spot;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getStart_time_time() {
        return start_time_time;
    }

    public void setStart_time_time(String start_time_time) {
        this.start_time_time = start_time_time;
    }

    public String getEnd_time_time() {
        return end_time_time;
    }

    public void setEnd_time_time(String end_time_time) {
        this.end_time_time = end_time_time;
    }

    public String getJoin_starttime_time() {
        return join_starttime_time;
    }

    public void setJoin_starttime_time(String join_starttime_time) {
        this.join_starttime_time = join_starttime_time;
    }

    public String getJoin_endtime_time() {
        return join_endtime_time;
    }

    public void setJoin_endtime_time(String join_endtime_time) {
        this.join_endtime_time = join_endtime_time;
    }

    public int getCan_join() {
        return can_join;
    }

    public void setCan_join(int can_join) {
        this.can_join = can_join;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public int getIs_join() {
        return is_join;
    }

    public void setIs_join(int is_join) {
        this.is_join = is_join;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public static class UserBean {
        /**
         * id : 13
         * name : 行走的攻略
         * wxopenid :
         * wbopenid :
         * type : 0
         * usertype : 0
         * intro : 这家伙很懒，什么都没留下
         * phone : 15708429594
         * sex : 0
         * header : /assets/data/20171208/15127189878455.jpg
         * salt : 6WNaMBDh
         * password : 03a605786748890ace06d7a532e100f2
         * paypwd :
         * address :
         * lat : 0
         * lng : 0
         * last_lat : 30.554267
         * last_lng : 104.065023
         * map_id : 39
         * balance : 0.00
         * safecount : 1
         * status : 1
         * is_circle : 0
         * is_del : 0
         * nomap : 0
         * created : 1500954876
         * updated : 1511861850
         */

        public int id;
        public String name;
        public String wxopenid;
        public String wbopenid;
        public int type;
        public int usertype;
        public String intro;
        public String phone;
        public int sex;
        public String header;
        public String salt;
        public String password;
        public String paypwd;
        public String address;
        public String lat;
        public String lng;
        public String last_lat;
        public String last_lng;
        public int map_id;
        public String balance;
        public int safecount;
        public int status;
        public int is_circle;
        public int is_del;
        public int nomap;
        public int created;
        public int updated;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWxopenid() {
            return wxopenid;
        }

        public void setWxopenid(String wxopenid) {
            this.wxopenid = wxopenid;
        }

        public String getWbopenid() {
            return wbopenid;
        }

        public void setWbopenid(String wbopenid) {
            this.wbopenid = wbopenid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPaypwd() {
            return paypwd;
        }

        public void setPaypwd(String paypwd) {
            this.paypwd = paypwd;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLast_lat() {
            return last_lat;
        }

        public void setLast_lat(String last_lat) {
            this.last_lat = last_lat;
        }

        public String getLast_lng() {
            return last_lng;
        }

        public void setLast_lng(String last_lng) {
            this.last_lng = last_lng;
        }

        public int getMap_id() {
            return map_id;
        }

        public void setMap_id(int map_id) {
            this.map_id = map_id;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getSafecount() {
            return safecount;
        }

        public void setSafecount(int safecount) {
            this.safecount = safecount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_circle() {
            return is_circle;
        }

        public void setIs_circle(int is_circle) {
            this.is_circle = is_circle;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getNomap() {
            return nomap;
        }

        public void setNomap(int nomap) {
            this.nomap = nomap;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public int getUpdated() {
            return updated;
        }

        public void setUpdated(int updated) {
            this.updated = updated;
        }
    }
}
