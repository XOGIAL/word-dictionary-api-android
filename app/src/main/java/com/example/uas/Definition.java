package com.example.uas;

import java.io.Serializable;

public class Definition implements Serializable {
    String imgurl;
    String type;
    String definition;

    public Definition(String imgurl,String type, String definition)
    {
        this.imgurl = imgurl;
        this.type = type;
        this.definition = definition;
    }
}
