package com.example.b7sport;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class adminGroupAdapterTest {
  adminGroupAdapter ad;


    @Test
    public  void inDatabasetest1()
    {
        String name="Group";
        int result=1;
        assertThat(result, is(ad.DataBase(name)));


    }

    @Test
    public  void inDatabasetest3()
    {
        String name="EDMIT_FIRE";
        int result=0;
        assertThat(result, is(ad.DataBase(name)));


    }
@Test
    public  void inDatabasetest2()
    {
        String name="notpass";
        int result=0;
        assertThat(result, is(ad.DataBase(name)));


    }


    @Test
    public  void SumTEst1()
    {
        int num=10;
        int result=1;
        assertThat(result, is(ad.sum(num)));


    }

    @Test
    public  void SumTEst2()
    {
        int num=2;
        int result=0;
        assertThat(result, is(ad.sum(num)));


    }


    @Test
    public  void SumTEst3()
    {
        int num=4;
        int result=1;
        assertThat(result, is(ad.sum(num)));


    }

}