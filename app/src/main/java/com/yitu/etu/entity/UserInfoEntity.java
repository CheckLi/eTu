package com.yitu.etu.entity;

/**
 * @className:UserInfoEntity
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月16日 16:15
 */
public class UserInfoEntity {
//    {"status":1,"data":{"id":31,"name":"\u674e\u4f73\u660e2222","type":0,"usertype":0,"intro":"\u8fd9\u5bb6\u4f19\u5f88\u61d2\uff0c\u4ec0\u4e48\u90fd\u6ca1\u7559\u4e0b","phone":"18281619229","sex":0,"header":"\/assets\/data\/sys\/userico.jpg","salt":"9zvUsBTe","password":"be64e7e18decd33bae11dec86209e94d","address":"","lat":"0","lng":"0","last_lat":"30.654762","last_lng":"104.098471","map_id":0,"balance":"0.00","safecount":0,"status":1,"is_circle":0,"is_del":0,"created":1512573048,"updated":1512573139,"ispaypwd":0,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkuOTFldG8uY29tIiwiaWF0IjoxNTEzNDE2MTgwLCJpZCI6MzF9.zyUkMA2AEKnjnq7c_9G4HrgcYuLrJfbeBect040wZis"},"message":"\u767b\u5f55\u6210\u529f","time":"2017-12-16 17:23:00"}
    /**
     * status : 1
     * data : {"id":31,"name":"182****9229","type":0,"usertype":0,"intro":"这家伙很懒，什么都没留下","phone":"18281619229","sex":0,"header":"/assets/data/sys/userico.jpg","salt":"9zvUsBTe","password":"be64e7e18decd33bae11dec86209e94d","address":"","lat":"0","lng":"0","last_lat":"30.654762","last_lng":"104.098471","map_id":0,"balance":"0.00","safecount":0,"status":1,"is_circle":0,"is_del":0,"created":1512573048,"updated":1512573139,"ispaypwd":0,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkuOTFldG8uY29tIiwiaWF0IjoxNTEzNDExODc0LCJpZCI6MzF9.s-UtStiEgghabcAN8idmWrlC2EqXwN5puKUPyL9uQUI"}
     * message : 登录成功
     * time : 2017-12-16 16:11:14
     */

    public int status;
    public DataBean data;
    public String message;
    public String time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class DataBean {
        /**
         * id : 31
         * name : 182****9229
         * type : 0
         * usertype : 0
         * intro : 这家伙很懒，什么都没留下
         * phone : 18281619229
         * sex : 0
         * header : /assets/data/sys/userico.jpg
         * salt : 9zvUsBTe
         * password : be64e7e18decd33bae11dec86209e94d
         * address :
         * lat : 0
         * lng : 0
         * last_lat : 30.654762
         * last_lng : 104.098471
         * map_id : 0
         * balance : 0.00
         * safecount : 0
         * status : 1
         * is_circle : 0
         * is_del : 0
         * created : 1512573048
         * updated : 1512573139
         * ispaypwd : 0
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9hcGkuOTFldG8uY29tIiwiaWF0IjoxNTEzNDExODc0LCJpZCI6MzF9.s-UtStiEgghabcAN8idmWrlC2EqXwN5puKUPyL9uQUI
         */


        public int id;
        public String name;
        public int type;
        public int usertype;
        public String intro;
        public String phone;
        public int sex;
        public String header;
        public String salt;
        public String password;
        public String address;
        public String lat;
        public String lng;
        public String lastLat;
        public String lastLng;
        public int mapId;
        public String balance;
        public int safecount;
        public int status;
         public int isCircle;
       public int isDel;
        public int created;
        public int updated;
        public int ispaypwd;
        public String token;

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

        public String getLastLat() {
            return lastLat;
        }

        public void setLastLat(String lastLat) {
            this.lastLat = lastLat;
        }

        public String getLastLng() {
            return lastLng;
        }

        public void setLastLng(String lastLng) {
            this.lastLng = lastLng;
        }

        public int getMapId() {
            return mapId;
        }

        public void setMapId(int mapId) {
            this.mapId = mapId;
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

        public int getIsCircle() {
            return isCircle;
        }

        public void setIsCircle(int isCircle) {
            this.isCircle = isCircle;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
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

        public int getIspaypwd() {
            return ispaypwd;
        }

        public void setIspaypwd(int ispaypwd) {
            this.ispaypwd = ispaypwd;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
