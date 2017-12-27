package com.yitu.etu.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.yitu.etu.ui.fragment.MapsFragment;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/14.
 */
public class MapFriendEntity extends MerchantBaseEntity implements Parcelable{
   public MapFriendEntity() {
        setType(MapsFragment.type_friend);
    }
    public String title;
    public String address;
    public String sex;
    public String user_id;

    protected MapFriendEntity(Parcel in) {
        title = in.readString();
        address = in.readString();
        sex = in.readString();
        user_id = in.readString();
    }

    public static final Creator<MapFriendEntity> CREATOR = new Creator<MapFriendEntity>() {
        @Override
        public MapFriendEntity createFromParcel(Parcel in) {
            return new MapFriendEntity(in);
        }

        @Override
        public MapFriendEntity[] newArray(int size) {
            return new MapFriendEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(sex);
        dest.writeString(user_id);
    }
}
