package com.example.baristachoise.model;

public class Cookie {

    int imgCookie;
    String cookieName;
    String cookiePrice;

    public Cookie(int imgCookie, String cookieName, String cookiePrice) {
        this.imgCookie = imgCookie;
        this.cookieName = cookieName;
        this.cookiePrice = cookiePrice;
    }

    public int getImgCookie() {
        return imgCookie;
    }


    public String getCookieName() {
        return cookieName;
    }


    public String getCookiePrice() {
        return cookiePrice;
    }

}
