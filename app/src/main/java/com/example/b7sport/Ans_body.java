package com.example.b7sport;

public class Ans_body {
    public String email;
    public  String ans;
    public  Ans_body(String email,String ans)
    {
        this.email=email;
        this.ans=ans;

    }



    public  int  anS(String e) {

         if (e.length()>10)
             return  1;
         else
             return 0;

    }



}
