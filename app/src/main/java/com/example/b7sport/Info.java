package com.example.b7sport;

import com.google.firebase.firestore.auth.User;

public class Info {

    String Email,PhoneNumber,Name,Password,UserID;

    public String getEmail() {
        return Email;
    }
    public String getUserID(){
        return UserID;
    }
    public void setUserID(String userID){
        UserID = userID;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public Info() {
    }
    public Info(String Email,String PhoneNumber,String Name,String Password,String UserID) {
        this.Email = Email;
        this.Name = Name;
        this.Password = Password;
        this.PhoneNumber = PhoneNumber;
        this.UserID = UserID;
    }
}
