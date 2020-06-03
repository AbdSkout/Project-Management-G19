package com.example.b7sport;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logic {
    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public boolean PasswordIsEmpty(String Password){
        if(Password.equals("")){
           // mPassword.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }

    public boolean PasswordLength(String Password){
        if(Password.length()<=6){
           // mPassword.setError("על הסיסמה להיות לפחות 7 אותיות");
            return true;
        }
        return false;
    }
    public boolean EmailRequired(String Email){
        if(Email.equals("")){
           // mEmail.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }

    public boolean EmailRegex(String Email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);
        if(!matcher.matches()){
            return true;
        }
        return false;
    }
    public boolean CheckName(String name){
        if(name.equals("")){
            // mFullName.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }
}
