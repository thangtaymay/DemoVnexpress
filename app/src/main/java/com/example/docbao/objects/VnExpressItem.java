package com.example.docbao.objects;

public class VnExpressItem {
    String title;
    String desciption;
    String img;
    String link;
    String time;
    String contentHtml;

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    String link_the_loai;

    public String getLink_the_loai() {
        return link_the_loai;
    }

    public void setLink_the_loai(String link_the_loai) {
        this.link_the_loai = link_the_loai;
    }

    public VnExpressItem(String title, String desciption, String img, String link, String time) {
        this.title = title;
        this.desciption = desciption;
        this.img = img;
        this.link = link;
        this.time = time;
    }

    @Override
    public String toString() {
        return
                "title='" + title + '\n' +
                ", desciption='" + desciption + '\n' +
                ", img='" + img + '\n' +
                ", link='" + link + '\n' +
                ", time='" + time + '\n' +
                ", contentHtml='" + contentHtml + '\n' +
                ", link_the_loai='" + link_the_loai ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
