package com.yitu.etu.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.LatLonPoint;
import com.yitu.etu.ui.fragment.MapsFragment;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapSceneEntity extends MerchantBaseEntity implements Parcelable{
    {
        setType(MapsFragment.type_scene);
    }

    public String spot_id;
    public String xjdes;
    public String yjcount;
    public String price;
    public String title;
    public String address;

    public MapSceneEntity() {
    }

    protected MapSceneEntity(Parcel in) {
        spot_id = in.readString();
        xjdes = in.readString();
        yjcount = in.readString();
        price = in.readString();
        title = in.readString();
        address = in.readString();
        latLonPoint = in.readParcelable(LatLonPoint.class.getClassLoader());
    }

    public static final Creator<MapSceneEntity> CREATOR = new Creator<MapSceneEntity>() {
        @Override
        public MapSceneEntity createFromParcel(Parcel in) {
            return new MapSceneEntity(in);
        }

        @Override
        public MapSceneEntity[] newArray(int size) {
            return new MapSceneEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(spot_id);
        dest.writeString(xjdes);
        dest.writeString(yjcount);
        dest.writeString(price);
        dest.writeString(title);
        dest.writeString(address);
        dest.writeParcelable(latLonPoint,flags);
    }
}
