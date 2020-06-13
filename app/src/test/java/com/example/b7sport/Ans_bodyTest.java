package com.example.b7sport;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Ans_bodyTest {
 Ans_body a;

    @Test
    public void TestAns1()
    {
        int result=1;
        assertThat(a.anS("12345678910"), is(1));

    }

    @Test
    public void TestAns2()
    {
        assertThat(a.anS("123456789"), is(0));

    }

    @Test
    public void TestAns3()
    {
        int result=1;
        assertThat(a.anS("111122233344"), is(1));

    }


}