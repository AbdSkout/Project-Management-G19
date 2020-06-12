package com.example.b7sport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class Reply_to_messagesTest {

    Reply_to_messages R;


    @Test
    public  void TestNameAT()
    {
        String name="heelo";
        int x=3;
        boolean result=true;
        assertThat(result,is(R.check(name,x)));


    }

    @Test
    public  void TestNameAT2()
    {
        String name="hexxa";
        int x=2;
        boolean result=true;
        assertThat(result,is(R.check(name,x)));


    }

    @Test
    public  void TestNameAT4()
    {
        String name="hello this will pass";
        int x=4;
        boolean result=true;
        assertThat(result,is(R.check(name,x)));


    }



}