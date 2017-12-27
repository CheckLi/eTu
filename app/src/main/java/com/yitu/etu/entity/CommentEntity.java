package com.yitu.etu.entity;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/27.
 */
public class CommentEntity {

    /**
     * id : 89
     * spot_id : 105
     * pcomment_id : 0
     * puser_id : 0
     * user_id : 42
     * text : dsa88888
     * good : 0
     * bad : 0
     * created : 1514361107
     * user : {"id":42,"name":"139****5041","wxopenid":"","wbopenid":"","type":0,"usertype":0,"intro":"这家伙很懒，什么都没留下","phone":"13990135041","sex":0,"header":"/assets/data/sys/userico.jpg","salt":"d6HXshV1","password":"c1466c53fe5b760acf3adb5db09caba8","paypwd":"","address":"","lat":"0","lng":"0","last_lat":"0","last_lng":"0","map_id":0,"balance":"0.00","safecount":0,"status":1,"is_circle":0,"is_del":0,"nomap":0,"created":1514359130,"updated":0}
     * child : []
     * created_time : 2017-12-27 15:51:47
     */

    public int id;
    public int spot_id;
    public int pcomment_id;
    public int puser_id;
    public int user_id;
    public String text;
    public int good;
    public int bad;
    public int created;
    public UserBean user;
    public String created_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpot_id() {
        return spot_id;
    }

    public void setSpot_id(int spot_id) {
        this.spot_id = spot_id;
    }

    public int getPcomment_id() {
        return pcomment_id;
    }

    public void setPcomment_id(int pcomment_id) {
        this.pcomment_id = pcomment_id;
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

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
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

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public static class UserBean {
        /**
         * id : 42
         * name : 139****5041
         * wxopenid :
         * wbopenid :
         * type : 0
         * usertype : 0
         * intro : 这家伙很懒，什么都没留下
         * phone : 13990135041
         * sex : 0
         * header : /assets/data/sys/userico.jpg
         * salt : d6HXshV1
         * password : c1466c53fe5b760acf3adb5db09caba8
         * paypwd :
         * address :
         * lat : 0
         * lng : 0
         * last_lat : 0
         * last_lng : 0
         * map_id : 0
         * balance : 0.00
         * safecount : 0
         * status : 1
         * is_circle : 0
         * is_del : 0
         * nomap : 0
         * created : 1514359130
         * updated : 0
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
