package com.example.sherd.rentfield;

/**
 * Created by Maks on 25.02.2017.
 */

public class Field_list {
    int id;
    int img;
    String name;
    String price;
    String tel;
    String address;
    int lng;
    int lang;
    public Field_list(int id, int img, String name, String price, String tel, String address, int lng, int lang) {
        this.address = address;
        this.id = id;
        this.img = img;
        this.lang = lang;
        this.lng = lng;
        this.name = name;
        this.price = price;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }
}
