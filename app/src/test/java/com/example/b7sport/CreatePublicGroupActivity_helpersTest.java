package com.example.b7sport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CreatePublicGroupActivity_helpersTest {
    CreatePublicGroupActivity cr;

    @Before
    public  void setup() throws InterruptedException {
        cr=new CreatePublicGroupActivity();


    }

    @Test
    public void parsehour1() {
        int []  a = new int[2];
        int []  b = new int[2];
         b=cr.parsehour("12:33",a);
         assertThat(a[0]==b[0]&&a[1]==b[1],is(true));
    }
    @Test
    public void parsehour2() {
        int []  a = new int[2];
        int []  b = new int[2];
        cr.parsehour("12:33",a);
        assertThat(a[0]==12&&a[1]==33,is(true));
    }

    @Test
    public void parsehour3() {
        int []  a = new int[2];
        cr.parsehour("01:15",a);
        assertThat(a[0]==1&&a[1]==15,is(true));
    }

    @Test
    public void calcminutes1() {
        int []  a = {1,2};
        int totalminutes=cr.calcminutes(a);
        boolean result = totalminutes==62;
        assertThat(result,is(true));
    }

    @Test
    public void calcminutes2() {
        int []  a = {2,13};
        int totalminutes=cr.calcminutes(a);
        boolean result = totalminutes==133;
        assertThat(result,is(true));
    }
    @Test
    public void calcminutes3() {
        int []  a = {6,13};
        int totalminutes=cr.calcminutes(a);
        boolean result = totalminutes==123;
        assertThat(result,is(false));
    }
//    @Test
//    public void checkhour1() {
//        boolean result =cr.checkhour("12:00","22:00");
//        assertThat(result,is(true));
//    }
//    @Test
//    public void checkhour2() {
//        boolean result =cr.checkhour("10:00","12:00");
//        assertThat(result,is(true));
//    }
}