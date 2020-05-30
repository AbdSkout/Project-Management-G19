package com.example.b7sport;

<<<<<<< HEAD
=======
import org.junit.Before;
>>>>>>> 449b1d3bca7ac48fc53cb5499df19523384bec25
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

<<<<<<< HEAD
public class CreatePublicGroupActivityTest {

    CreatePublicGroupActivity STU=new CreatePublicGroupActivity();

@Test
    public  void asadasd()
{
    boolean result=STU.CheckGrName("abd");
    assertThat(result,is(true));


}

=======

public class CreatePublicGroupActivityTest {
    CreatePublicGroupActivity cr;

    @Before
    public  void setup()
    {
        cr=new CreatePublicGroupActivity();

    }

    @Test
    public void checkname1() {
        boolean result = cr.CheckGrName("Arena");
        assertThat(result,is(true));
    }
    @Test
    public void checkname2() {
        boolean result = cr.CheckGrName("");
        assertThat(result,is(false));
    }
    @Test
    public void checkname3() {
        boolean result = cr.CheckGrName("113");
        assertThat(result,is(true));
    }
    @Test
    public  void checknumber1() {
        boolean result = cr.CheckNumber("12");
        assertThat(result,is(true));
    }
    @Test
    public  void checknumber2() {
        boolean result = cr.CheckNumber("");
        assertThat(result,is(false));
    }
    @Test
    public void checknumber3() {
        boolean result = cr.CheckNumber("-1");
        assertThat(result,is(false));
    }
>>>>>>> 449b1d3bca7ac48fc53cb5499df19523384bec25

}