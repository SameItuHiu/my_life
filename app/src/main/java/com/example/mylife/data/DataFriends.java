package com.example.mylife.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataFriends implements Parcelable {
    String id_friends;
    String id_hutang;
    String id_location;
    String name;
    String status;
    String birthday;
    String description;
    String path_photo;
    String no_handphone;
    String email;

    public DataFriends(){

    }

    protected DataFriends(Parcel in) {
        id_friends = in.readString();
        id_hutang = in.readString();
        id_location = in.readString();
        name = in.readString();
        status = in.readString();
        birthday = in.readString();
        description = in.readString();
        path_photo = in.readString();
        no_handphone = in.readString();
        email = in.readString();
    }

    public static final Creator<DataFriends> CREATOR = new Creator<DataFriends>() {
        @Override
        public DataFriends createFromParcel(Parcel in) {
            return new DataFriends(in);
        }

        @Override
        public DataFriends[] newArray(int size) {
            return new DataFriends[size];
        }
    };

    public String getId_friends() {
        return id_friends;
    }

    public void setId_friends(String id_friends) {
        this.id_friends = id_friends;
    }

    public String getId_hutang() {
        return id_hutang;
    }

    public void setId_hutang(String id_hutang) {
        this.id_hutang = id_hutang;
    }

    public String getId_location() {
        return id_location;
    }

    public void setId_location(String id_location) {
        this.id_location = id_location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath_photo() {
        return path_photo;
    }

    public void setPath_photo(String path_photo) {
        this.path_photo = path_photo;
    }

    public String getNo_handphone() {
        return no_handphone;
    }

    public void setNo_handphone(String no_handphone) {
        this.no_handphone = no_handphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_friends);
        parcel.writeString(id_hutang);
        parcel.writeString(id_location);
        parcel.writeString(name);
        parcel.writeString(status);
        parcel.writeString(birthday);
        parcel.writeString(description);
        parcel.writeString(path_photo);
        parcel.writeString(no_handphone);
        parcel.writeString(email);
    }
}
