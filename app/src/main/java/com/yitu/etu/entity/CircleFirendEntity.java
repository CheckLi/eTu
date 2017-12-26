package com.yitu.etu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class CircleFirendEntity {
    public UserBean user;
    public List<CircleBean> circle;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<CircleBean> getCircle() {
        return circle;
    }

    public void setCircle(List<CircleBean> circle) {
        this.circle = circle;
    }

    public static class UserBean implements Serializable{
        /**
         * id : 25
         * name : 创新动力
         * wxopenid :
         * wbopenid :
         * type : 0
         * usertype : 0
         * intro : 欢乐e途，愉快旅行
         * phone : 13408570875
         * sex : 1
         * header : /assets/data/20171128/15118462276464.jpg
         * salt : xP9tjkv8
         * password : 49ace43d951898b85eee93e71ecc3069
         * paypwd : 891228
         * address :
         * lat : 0
         * lng : 0
         * last_lat : 30.554210
         * last_lng : 104.065113
         * map_id : 30
         * balance : 6.00
         * safecount : 0
         * status : 1
         * is_circle : 1
         * is_del : 0
         * nomap : 0
         * created : 1510541595
         * updated : 1513920039
         * isfrend : false
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
        public boolean isfrend;

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

        public boolean isIsfrend() {
            return isfrend;
        }

        public void setIsfrend(boolean isfrend) {
            this.isfrend = isfrend;
        }
    }

    public static class CircleBean implements Serializable{
        /**
         * id : 8
         * user_id : 25
         * images : /assets/data/20171208/15127413674732.jpg|/assets/data/20171208/15127413676415.jpg
         * text : 好久没有坐地铁了，居然有点晕，这什么节奏呢？还不如小盆友(地铁上写作业)
         * good : 1
         * created : 1512741367
         * time : 2017-12-08
         * user : {"id":25,"name":"创新动力","wxopenid":"","wbopenid":"","type":0,"usertype":0,"intro":"欢乐e途，愉快旅行","phone":"13408570875","sex":1,"header":"/assets/data/20171128/15118462276464.jpg","salt":"xP9tjkv8","password":"49ace43d951898b85eee93e71ecc3069","paypwd":"891228","address":"","lat":"0","lng":"0","last_lat":"30.554210","last_lng":"104.065113","map_id":30,"balance":"6.00","safecount":0,"status":1,"is_circle":1,"is_del":0,"nomap":0,"created":1510541595,"updated":1513920039}
         * comment : []
         */

        public int id;
        public int user_id;
        public String images;
        public String text;
        public int good;
        public int created;
        public String time;
        public UserBean user;
        public List<CommentBean> comment;

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

        public String[] getImages() {
            return (images==null||images.trim().equals(""))?new String[]{}:images.split("\\|");
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }
    }
    public  static  class CommentBean{

        /**
         * id : 5
         * circle_id : 11
         * puser_id : 0
         * user_id : 31
         * text : 2233
         * created : 1513863266
         * user : {"id":31,"name":"李佳明2222"}
         * puser : {}
         */

        public int id;
        public int circle_id;
        public int puser_id;
        public int user_id;
        public String text;
        public int created;
        public UserBean user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public int getPuser_id() {
            return puser_id;
        }

        public void setPuser_id(int puser_id) {
            this.puser_id = puser_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }
    }
}

