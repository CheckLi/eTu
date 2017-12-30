package com.yitu.etu.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/27.
 */
public class SceneEntity implements Serializable{

    /**
     * spot : {"id":105,"user_id":0,"title":"三义庙","text":"三义庙位于成都武侯祠内，初名三义祠，位于成都市提督街，清康熙初年由四川提督郑蛟麟始建。1998年，因城建需要三义庙迁建到武侯祠内。三义庙现为一进四合院，由拜殿、大殿和两侧的廊房组成。\r\n拜殿内有两组2.2×2.3米的黑色大理石画像石刻，东墙南首为《桃园三结义》，西墙南首为《三英战吕布》。大理石画像石刻之后，各有四通青石画像碑，白描。东边依次为《张翼德怒鞭督邮》、《云长延津诛文丑》、《关云长义释曹操》、《玄德进位汉中王》；西边依次为《关云长刮骨疗毒》、《关云长单刀赴会》、《玄德智娶孙夫人》、《张翼德义释严颜》。正殿五开间，深8.5米，抬梁式屋架，天花板不作彩绘，花雕饰檐撑。大殿中部三间靠后墙处设神龛，内有塑像：刘备居中，高2.8米；东为关羽、西为张飞，均高2.6米。三像造型根据《三国演义》描写而作，形象较年轻，生气勃勃，着单色布衣，是结义后创业时期的形象，很符合\u201c三义庙\u201d民间风俗庙宇的特点。","images":["/assets/data/20170329/14907538721160jpg","/assets/data/20170329/14907538728263jpg"],"feature":["历史"],"good":74,"bad":200,"sort":0,"tcount":0,"weburl":"","phone":"","price":"0.00","address":"四川省成都市武侯区浆洗街街道三义庙成都武侯祠博物馆","address_lat":"30.646753","address_lng":"104.049534","map_id":2141,"xjdes":"","dzdes":"","hddes":"","status":1,"created":1490753872,"updated":1500176266,"created_time":"2017-03-29 10:17:52"}
     * titlelist : []
     * comment : []
     */

    public SpotBean spot;
    public List<TitlelistBean> titlelist;

    public List<TitlelistBean> getTitlelist() {
        return titlelist;
    }

    public void setTitlelist(List<TitlelistBean> titlelist) {
        this.titlelist = titlelist;
    }

    public SpotBean getSpot() {
        return spot;
    }

    public void setSpot(SpotBean spot) {
        this.spot = spot;
    }
    public static class TitlelistBean implements Serializable{

        /**
         * id : 1
         * type : 1
         * spot_id : 121
         * user_id : 10
         * title : 水街小吃
         * images :
         * text : 吃的东西很多，性价比还可以，晚上打烊的时间比较早，基本上都是10点左右，路旁不能停车容易罚单！<img src="http://api.91eto.com/assets/data/20171204/15123655282257.jpg" width="100%">里面樟茶鸭不错，个人习惯甜皮鸭，附近川菜馆很多味道也很地道，不建议在这个地方吃海鲜，你懂的
         * is_spot : 0
         * address :
         * address_lat :
         * address_lng :
         * good : 0
         * bad : 0
         * sort : 99
         * status : 1
         * created : 1512365661
         * updated : 1512441988
         * user : {"id":10,"name":"逍遥游","wxopenid":"","wbopenid":"","type":0,"usertype":0,"intro":"来呀，快活呀\u2026\u2026","phone":"15982039158","sex":0,"header":"/assets/data/20171031/15094280299667.jpg","salt":"hB20XbD9","password":"a744dc3f9c2f1e67c6308f595cd0246b","paypwd":"","address":"","lat":"0","lng":"0","last_lat":"25.384540","last_lng":"119.005563","map_id":45,"balance":"0.00","safecount":16,"status":1,"is_circle":0,"is_del":0,"nomap":0,"created":1496896834,"updated":1512650922}
         */

        @SerializedName("id")
        public int id;
        @SerializedName("type")
        public int type;
        @SerializedName("spot_id")
        public int spotId;
        @SerializedName("user_id")
        public int userId;
        @SerializedName("title")
        public String title;
        @SerializedName("images")
        public String images;
        @SerializedName("text")
        public String text;
        @SerializedName("is_spot")
        public int isSpot;
        @SerializedName("address")
        public String address;
        @SerializedName("address_lat")
        public String addressLat;
        @SerializedName("address_lng")
        public String addressLng;
        @SerializedName("good")
        public int good;
        @SerializedName("bad")
        public int bad;
        @SerializedName("sort")
        public int sort;
        @SerializedName("status")
        public int status;
        @SerializedName("created")
        public int created;
        @SerializedName("updated")
        public int updated;
        @SerializedName("user")
        public UserInfo user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSpotId() {
            return spotId;
        }

        public void setSpotId(int spotId) {
            this.spotId = spotId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImages() {
            return images;
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

        public int getIsSpot() {
            return isSpot;
        }

        public void setIsSpot(int isSpot) {
            this.isSpot = isSpot;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressLat() {
            return addressLat;
        }

        public void setAddressLat(String addressLat) {
            this.addressLat = addressLat;
        }

        public String getAddressLng() {
            return addressLng;
        }

        public void setAddressLng(String addressLng) {
            this.addressLng = addressLng;
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public UserInfo getUser() {
            return user;
        }

        public void setUser(UserInfo user) {
            this.user = user;
        }
    }
    public static class SpotBean implements Serializable{
        /**
         * id : 105
         * user_id : 0
         * title : 三义庙
         * text : 三义庙位于成都武侯祠内，初名三义祠，位于成都市提督街，清康熙初年由四川提督郑蛟麟始建。1998年，因城建需要三义庙迁建到武侯祠内。三义庙现为一进四合院，由拜殿、大殿和两侧的廊房组成。
         拜殿内有两组2.2×2.3米的黑色大理石画像石刻，东墙南首为《桃园三结义》，西墙南首为《三英战吕布》。大理石画像石刻之后，各有四通青石画像碑，白描。东边依次为《张翼德怒鞭督邮》、《云长延津诛文丑》、《关云长义释曹操》、《玄德进位汉中王》；西边依次为《关云长刮骨疗毒》、《关云长单刀赴会》、《玄德智娶孙夫人》、《张翼德义释严颜》。正殿五开间，深8.5米，抬梁式屋架，天花板不作彩绘，花雕饰檐撑。大殿中部三间靠后墙处设神龛，内有塑像：刘备居中，高2.8米；东为关羽、西为张飞，均高2.6米。三像造型根据《三国演义》描写而作，形象较年轻，生气勃勃，着单色布衣，是结义后创业时期的形象，很符合“三义庙”民间风俗庙宇的特点。
         * images : ["/assets/data/20170329/14907538721160jpg","/assets/data/20170329/14907538728263jpg"]
         * feature : ["历史"]
         * good : 74
         * bad : 200
         * sort : 0
         * tcount : 0
         * weburl :
         * phone :
         * price : 0.00
         * address : 四川省成都市武侯区浆洗街街道三义庙成都武侯祠博物馆
         * address_lat : 30.646753
         * address_lng : 104.049534
         * map_id : 2141
         * xjdes :
         * dzdes :
         * hddes :
         * status : 1
         * created : 1490753872
         * updated : 1500176266
         * created_time : 2017-03-29 10:17:52
         */

        public int id;
        public int user_id;
        public String title;
        public String text;
        public int good;
        public int bad;
        public int sort;
        public int tcount;
        public String weburl;
        public String phone;
        public String price;
        public String address;
        public String address_lat;
        public String address_lng;
        public int map_id;
        public String xjdes;
        public String dzdes;
        public String hddes;
        public int status;
        public int created;
        public int updated;
        public String created_time;
        public List<String> images;
        public List<String> feature;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getWeburl() {
            return weburl;
        }

        public void setWeburl(String weburl) {
            this.weburl = weburl;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public int getMap_id() {
            return map_id;
        }

        public void setMap_id(int map_id) {
            this.map_id = map_id;
        }

        public String getXjdes() {
            return xjdes;
        }

        public void setXjdes(String xjdes) {
            this.xjdes = xjdes;
        }

        public String getDzdes() {
            return dzdes;
        }

        public void setDzdes(String dzdes) {
            this.dzdes = dzdes;
        }

        public String getHddes() {
            return hddes;
        }

        public void setHddes(String hddes) {
            this.hddes = hddes;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<String> getFeature() {
            return feature;
        }

        public void setFeature(List<String> feature) {
            this.feature = feature;
        }
    }
}

