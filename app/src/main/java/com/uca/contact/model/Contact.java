package com.uca.contact.model;

public class Contact {
    private String tel;

    private String name;

    private String address;

    private byte[] photo;

    public Contact() {
    }

    public Contact(String tel, String name, String address) {
        this.tel = tel;
        this.name = name;
        this.address = address;
        //this.photo = photo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    */
}
