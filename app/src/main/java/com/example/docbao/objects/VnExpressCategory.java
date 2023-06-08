package com.example.docbao.objects;

import java.io.Serializable;

public class VnExpressCategory implements Serializable {

//    khai báo 2 thuộc tính tên, link
    private String name;
    private String link;

//khởi tạo hàm contracter
    public VnExpressCategory(String name, String link) {
        this.name = name;
        this.link = link;
    }

//    khởi tạo geter, seter
    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }


}
