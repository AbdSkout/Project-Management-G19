package com.example.b7sport;

import android.net.Uri;

import java.net.URI;

public class FriendsInfo {

    private String Email,PhoneNumber,FullName,Address;
    Uri mProfilePic;
    public FriendsInfo(){}
    public FriendsInfo(String email, String phoneNumber, String fullName, String address, Uri ProfilePic) {
        this.Email = email;
        this.PhoneNumber = phoneNumber;
        this.FullName = fullName;
        this.Address = address;
        this.mProfilePic = ProfilePic;
    }

    public Uri getmProfilePic(){return mProfilePic;}

    public void setmProfilePic(Uri email){mProfilePic = email;}

    public String getEmail() {
        return Email;
    }

    public String getAddress(){return Address;}

    public void setAddress(String address){Address = address;}

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}
