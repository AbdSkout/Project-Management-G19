package com.example.b7sport;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class LoginDatabaseIntrface {

    @Test
    public  void CheckIfBlock()
    {
        //test
        Login.isblock("Ro2jer@gmail.com");
        boolean Result= Login.flag==0;
        assertThat(false,is(Result));
    }
    @Test

    public   void CheckifBlock1()
    {
        Login.isblock("abd@gmail.com");
       boolean result= 1==Login.flag;
        assertThat(false,is(result));

    }

}
